-- ============================================================
-- SweezCustoms — Полная схема базы данных
-- Основана на текущих JPA-сущностях проекта
-- ============================================================

-- ------------------------------------------------------------
-- 1. ПОЛЬЗОВАТЕЛИ И РОЛИ
-- ------------------------------------------------------------

CREATE TABLE IF NOT EXISTS users (
    id               BIGSERIAL PRIMARY KEY,
    mail             VARCHAR NOT NULL UNIQUE,
    full_name        VARCHAR NOT NULL,
    pin              VARCHAR NOT NULL UNIQUE,
    passport_number  VARCHAR NOT NULL UNIQUE,
    birth_date       DATE    NOT NULL,
    phone            VARCHAR(20) NOT NULL UNIQUE,
    password         VARCHAR NOT NULL,
    photo_profile_s3 VARCHAR NOT NULL UNIQUE,
    signature_s3     VARCHAR NOT NULL UNIQUE,
    company_id       BIGINT,  -- FK добавляется после создания companies
    created_at       DATE DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS roles (
    id        BIGSERIAL PRIMARY KEY,
    role_name VARCHAR(30) NOT NULL UNIQUE
);

INSERT INTO roles(role_name)
VALUES ('ADMIN'),
       ('INSPECTOR'),
       ('USER'),
       ('OWNER'),
       ('MANAGER')
ON CONFLICT (role_name) DO NOTHING;


CREATE TABLE IF NOT EXISTS m2m_users_roles (
    role_id BIGINT NOT NULL REFERENCES roles(id) ON DELETE CASCADE,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    PRIMARY KEY (role_id, user_id)
);

-- ------------------------------------------------------------
-- 2. КОМПАНИИ
-- ------------------------------------------------------------

CREATE TABLE IF NOT EXISTS companies (
    id           BIGSERIAL PRIMARY KEY,
    name         VARCHAR NOT NULL,
    tin          VARCHAR NOT NULL UNIQUE,
    okpo         VARCHAR NOT NULL UNIQUE,
    customs_code VARCHAR NOT NULL UNIQUE,
    status       VARCHAR(20) DEFAULT 'PENDING',
    owner_id     BIGINT UNIQUE REFERENCES users(id) ON DELETE SET NULL,
    verified_by  BIGINT REFERENCES users(id) ON DELETE SET NULL,
    verified_at  TIMESTAMP WITH TIME ZONE,
    created_at   TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

-- Теперь добавляем FK company_id в users
DO $$
    BEGIN
        IF NOT EXISTS (
            SELECT 1
            FROM pg_constraint
            WHERE conname = 'fk_users_company'
        ) THEN
            ALTER TABLE users
                ADD CONSTRAINT fk_users_company
                    FOREIGN KEY (company_id) REFERENCES companies(id) ON DELETE SET NULL;
        END IF;
    END $$;

-- ------------------------------------------------------------
-- 3. ФИЛИАЛЫ КОМПАНИИ
-- ------------------------------------------------------------

CREATE TABLE IF NOT EXISTS branches_company (
    id          BIGSERIAL PRIMARY KEY,
    branch_name VARCHAR NOT NULL,
    address     VARCHAR NOT NULL,
    phone       VARCHAR NOT NULL UNIQUE,
    company_id  BIGINT NOT NULL REFERENCES companies(id) ON DELETE CASCADE
);

-- ------------------------------------------------------------
-- 4. ДОКУМЕНТЫ КОМПАНИИ
-- ------------------------------------------------------------

CREATE TABLE IF NOT EXISTS company_documents (
    id            BIGSERIAL PRIMARY KEY,
    company_id    BIGINT NOT NULL REFERENCES companies(id) ON DELETE CASCADE,
    document_type VARCHAR(50) NOT NULL,
    file_name     VARCHAR NOT NULL UNIQUE,
    language      VARCHAR(5) NOT NULL,
    status        VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    verified_by   BIGINT REFERENCES users(id) ON DELETE SET NULL,
    verified_at   TIMESTAMP WITH TIME ZONE,
    uploaded_at   TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

-- ------------------------------------------------------------
-- 5. ДОКУМЕНТЫ ФИЗИЧЕСКИХ ЛИЦ
-- ------------------------------------------------------------

CREATE TABLE IF NOT EXISTS individual_documents (
    id            BIGSERIAL PRIMARY KEY,
    user_id       BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    document_type VARCHAR(30) NOT NULL,
    status        VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    verified_by   BIGINT REFERENCES users(id) ON DELETE SET NULL,
    verified_at   TIMESTAMP WITH TIME ZONE,
    uploaded_at   TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

-- ------------------------------------------------------------
-- 6. ХРАНИЛИЩЕ ФАЙЛОВ
-- ------------------------------------------------------------

CREATE TABLE IF NOT EXISTS file_storage (
    id                 BIGSERIAL PRIMARY KEY,
    original_file_name VARCHAR NOT NULL,
    mime_type          VARCHAR NOT NULL,
    size               BIGINT  NOT NULL,
    path               VARCHAR NOT NULL,
    uploaded_by        BIGINT REFERENCES users(id) ON DELETE SET NULL,
    uploaded_at        TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

-- ------------------------------------------------------------
-- 7. КОНТРАКТЫ КОМПАНИЙ
-- ------------------------------------------------------------

CREATE TABLE IF NOT EXISTS contracts (
    id               BIGSERIAL PRIMARY KEY,
    company_id       BIGINT NOT NULL REFERENCES companies(id) ON DELETE CASCADE,
    contract_numbers VARCHAR NOT NULL,
    contract_date    DATE    NOT NULL,
    contract_expiry  DATE    NOT NULL,
    currency_code    VARCHAR(10) NOT NULL,
    total_amount     DECIMAL(19, 2) NOT NULL,
    status           VARCHAR(20) DEFAULT 'ACTIVE',
    file_name        VARCHAR NOT NULL UNIQUE,
    created_at       TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

-- ------------------------------------------------------------
-- 8. СПРАВОЧНИК КОДОВ ТН ВЭД
-- ------------------------------------------------------------

CREATE TABLE IF NOT EXISTS tnved_codes (
    id                        BIGSERIAL PRIMARY KEY,
    code                      VARCHAR NOT NULL UNIQUE,
    description               TEXT    NOT NULL,
    default_customs_duty_rate DECIMAL(8, 4) NOT NULL,
    default_excise_rate       DECIMAL(8, 4) NOT NULL,
    default_nds_rate          DECIMAL(8, 4) NOT NULL,
    user_id                   BIGINT REFERENCES users(id) ON DELETE SET NULL,
    date_created              TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

-- ------------------------------------------------------------
-- 9. ДЕКЛАРАЦИИ (базовая таблица — JOINED inheritance)
-- ------------------------------------------------------------

CREATE TABLE IF NOT EXISTS declarations (
    id            BIGSERIAL PRIMARY KEY,
    document_type VARCHAR(50),
    status        VARCHAR(20) DEFAULT 'DRAFT',
    currency      VARCHAR(10),
    currency_rate DECIMAL(19, 2),
    file_name     VARCHAR UNIQUE,
    created_at    TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    submitted_at  TIMESTAMP WITH TIME ZONE
);

-- ------------------------------------------------------------
-- 10. ДЕКЛАРАЦИИ КОМПАНИЙ
-- ------------------------------------------------------------

CREATE TABLE IF NOT EXISTS company_declarations (
    id             BIGINT PRIMARY KEY REFERENCES declarations(id) ON DELETE CASCADE,
    company_id     BIGINT REFERENCES companies(id) ON DELETE SET NULL,
    contract_id    BIGINT REFERENCES contracts(id) ON DELETE SET NULL,
    invoice_number VARCHAR,
    invoice_date   VARCHAR,
    incoterms_code VARCHAR,
    incoterms_place VARCHAR,
    transport_type VARCHAR,
    transport_id   VARCHAR
);

-- ------------------------------------------------------------
-- 11. ДЕКЛАРАЦИИ ФИЗИЧЕСКИХ ЛИЦ
-- ------------------------------------------------------------

CREATE TABLE IF NOT EXISTS user_declarations (
    id              BIGINT PRIMARY KEY REFERENCES declarations(id) ON DELETE CASCADE,
    user_id         BIGINT REFERENCES users(id) ON DELETE SET NULL,
    arrival_way     VARCHAR,
    tracking_number VARCHAR,
    is_personal     BOOLEAN NOT NULL DEFAULT FALSE
);

-- ------------------------------------------------------------
-- 12. ДЕКЛАРАЦИИ НА АВТОМОБИЛИ
-- ------------------------------------------------------------

CREATE TABLE IF NOT EXISTS car_declarations (
    id                   BIGINT PRIMARY KEY REFERENCES declarations(id) ON DELETE CASCADE,
    user_id              BIGINT REFERENCES users(id) ON DELETE SET NULL,
    company_id           BIGINT REFERENCES companies(id) ON DELETE SET NULL,
    vin_code             VARCHAR(20) NOT NULL UNIQUE,
    brand                VARCHAR(50) NOT NULL,
    model                VARCHAR(50) NOT NULL,
    color                VARCHAR(20) NOT NULL,
    manufacture_year     INT         NOT NULL,
    engine_volume        INT,
    engine_power_hp      INT,
    engine_power_kw      INT,
    fuel_type            VARCHAR(20) NOT NULL,
    title_number         VARCHAR(20) NOT NULL,
    mileage              INT,
    car_value            DECIMAL(19, 2),
    nds_amount           DECIMAL(19, 2),
    customs_duty_amount  DECIMAL(19, 2),
    excise_amount        DECIMAL(19, 2)
);

-- ------------------------------------------------------------
-- 13. ТОВАРЫ В ДЕКЛАРАЦИЯХ
-- ------------------------------------------------------------

CREATE TABLE IF NOT EXISTS declaration_products (
    id                  BIGSERIAL PRIMARY KEY,
    declaration_id      BIGINT NOT NULL REFERENCES declarations(id) ON DELETE CASCADE,
    product_name        VARCHAR NOT NULL,
    product_description VARCHAR,
    product_materials   VARCHAR,
    tnved_code          BIGINT REFERENCES tnved_codes(id) ON DELETE SET NULL,
    quantity            DECIMAL(15, 3) NOT NULL,
    unit_type           VARCHAR,
    weight_netto        DECIMAL(15, 3),
    weight_brutto       DECIMAL(15, 3),
    price_per_unit      DECIMAL(19, 2),
    total_price         DECIMAL(19, 2),
    nds                 DECIMAL(19, 2),
    customs_duty        DECIMAL(19, 2),
    excise              DECIMAL(19, 2),
    created_at          TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

-- ------------------------------------------------------------
-- 14. ПЛАТЁЖНЫЕ СЧЕТА
-- ------------------------------------------------------------

CREATE TABLE IF NOT EXISTS payment_invoices (
    id                        BIGSERIAL PRIMARY KEY,
    declaration_id            BIGINT REFERENCES declarations(id) ON DELETE CASCADE,
    status                    VARCHAR(20) DEFAULT 'ISSUED',
    total_invoice_nds         DECIMAL(19, 2) NOT NULL,
    total_invoice_customs_duty DECIMAL(19, 2) NOT NULL,
    total_invoice_excise      DECIMAL(19, 2) NOT NULL,
    invoice_total             DECIMAL(19, 2) NOT NULL,
    date_created              TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    date_to_pay               TIMESTAMP WITH TIME ZONE DEFAULT NOW() + INTERVAL '15 days',
    date_paid                 TIMESTAMP WITH TIME ZONE
);

-- ------------------------------------------------------------
-- 15. КЭШИ ОТЧЁТОВ
-- ------------------------------------------------------------

CREATE TABLE IF NOT EXISTS reported_caches (
    id               BIGSERIAL PRIMARY KEY,
    company_id       BIGINT REFERENCES companies(id) ON DELETE CASCADE,
    period_start     TIMESTAMP WITH TIME ZONE NOT NULL,
    period_end       TIMESTAMP WITH TIME ZONE NOT NULL,
    total_import     BIGINT        NOT NULL DEFAULT 0,
    total_export     BIGINT        NOT NULL DEFAULT 0,
    total_taxes_paid DECIMAL(19, 2) NOT NULL DEFAULT 0,
    total_rejected   BIGINT        NOT NULL DEFAULT 0,
    generated_at     TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

-- ------------------------------------------------------------
-- 16. НАЧАЛЬНЫЕ ДАННЫЕ — РОЛИ
-- ------------------------------------------------------------

INSERT INTO roles (role_name) VALUES
    ('ADMIN'),
    ('INSPECTOR'),
    ('OWNER'),
    ('MANAGER'),
    ('ACCOUNTANT'),
    ('ANALYTIC'),
    ('LOGISTICIAN'),
    ('USER')
ON CONFLICT (role_name) DO NOTHING;
