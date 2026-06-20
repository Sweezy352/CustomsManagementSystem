-- ============================================================
-- SweezCustoms — Тестовые данные (V2.1)
-- Порядок вставки соблюдает FK-зависимости схемы V2.0
-- ============================================================

-- ------------------------------------------------------------
-- 1. ПОЛЬЗОВАТЕЛИ (company_id = NULL, заполним через UPDATE)
-- ------------------------------------------------------------

INSERT INTO users (mail, full_name, pin, passport_number, birth_date, phone, password, photo_profile_s3, signature_s3, created_at)
VALUES
    -- id=1  ADMIN
    ('admin@sweezcustoms.kg',
     'Айбек Токтосунов',
     '10101198500001',
     'AN0000001',
     '1985-03-12',
     '+996700000001',
     '$2a$10$hashedpassword000000000000000000000000000000000000000001',
     'photos/admin.jpg',
     'signatures/admin.png',
     NOW()),

    -- id=2  INSPECTOR
    ('inspector@sweezcustoms.kg',
     'Нурлан Бекматов',
     '10101198900002',
     'AN0000002',
     '1989-07-25',
     '+996700000002',
     '$2a$10$hashedpassword000000000000000000000000000000000000000002',
     'photos/inspector.jpg',
     'signatures/inspector.png',
     NOW()),

    -- id=3  OWNER компании 1
    ('owner1@techimport.kg',
     'Бакыт Джумабаев',
     '20201199100003',
     'AN0000003',
     '1991-11-05',
     '+996700000003',
     '$2a$10$hashedpassword000000000000000000000000000000000000000003',
     'photos/owner1.jpg',
     'signatures/owner1.png',
     NOW()),

    -- id=4  MANAGER компании 1
    ('manager1@techimport.kg',
     'Айгуль Омурова',
     '20201199200004',
     'AN0000004',
     '1992-06-18',
     '+996700000004',
     '$2a$10$hashedpassword000000000000000000000000000000000000000004',
     'photos/manager1.jpg',
     'signatures/manager1.png',
     NOW()),

    -- id=5  OWNER компании 2
    ('owner2@silkroad.kg',
     'Эрмек Асанов',
     '30301199400005',
     'AN0000005',
     '1994-02-28',
     '+996700000005',
     '$2a$10$hashedpassword000000000000000000000000000000000000000005',
     'photos/owner2.jpg',
     'signatures/owner2.png',
     NOW()),

    -- id=6  Физическое лицо 1
    ('user1@mail.kg',
     'Калысбек Иманов',
     '40401199600006',
     'AN0000006',
     '1996-09-14',
     '+996700000006',
     '$2a$10$hashedpassword000000000000000000000000000000000000000006',
     'photos/user1.jpg',
     'signatures/user1.png',
     NOW()),

    -- id=7  Физическое лицо 2
    ('user2@mail.kg',
     'Зарина Токтоматова',
     '40401199800007',
     'AN0000007',
     '1998-04-22',
     '+996700000007',
     '$2a$10$hashedpassword000000000000000000000000000000000000000007',
     'photos/user2.jpg',
     'signatures/user2.png',
     NOW());

-- ------------------------------------------------------------
-- 2. ПРИВЯЗКА РОЛЕЙ  (роли уже вставлены в V2.0)
-- ------------------------------------------------------------

INSERT INTO m2m_users_roles (role_id, user_id)
VALUES
    ((SELECT id FROM roles WHERE role_name = 'ADMIN'),     1),
    ((SELECT id FROM roles WHERE role_name = 'INSPECTOR'), 2),
    ((SELECT id FROM roles WHERE role_name = 'OWNER'),     3),
    ((SELECT id FROM roles WHERE role_name = 'MANAGER'),   4),
    ((SELECT id FROM roles WHERE role_name = 'OWNER'),     5),
    ((SELECT id FROM roles WHERE role_name = 'USER'),      6),
    ((SELECT id FROM roles WHERE role_name = 'USER'),      7);

-- ------------------------------------------------------------
-- 3. КОМПАНИИ
-- owner_id — уникальный FK на users(id)
-- verified_by — FK на users(id)
-- ------------------------------------------------------------

INSERT INTO companies (name, tin, okpo, customs_code, status, owner_id, verified_by, verified_at, created_at)
VALUES
    -- id=1
    ('TechImport LLC',
     '12345678901',
     '11223344',
     'TC-001',
     'APPROVED',
     3,
     2,
     NOW() - INTERVAL '10 days',
     NOW() - INTERVAL '30 days'),

    -- id=2
    ('SilkRoad Trade LLC',
     '98765432100',
     '55667788',
     'TC-002',
     'PENDING',
     5,
     NULL,
     NULL,
     NOW() - INTERVAL '5 days');

-- Привязываем сотрудников к компаниям через company_id в users
UPDATE users SET company_id = 1 WHERE id IN (3, 4);
UPDATE users SET company_id = 2 WHERE id = 5;

-- ------------------------------------------------------------
-- 4. ФИЛИАЛЫ КОМПАНИЙ
-- ------------------------------------------------------------

INSERT INTO branches_company (branch_name, address, phone, company_id)
VALUES
    ('Главный офис',   'Бишкек, пр. Чуй 155',         '+996312100001', 1),
    ('Склад Манас',    'Бишкек, ул. Аэропортная 10',   '+996312100002', 1),
    ('Офис Ош',        'Ош, ул. Ленина 45',             '+996322100001', 2);

-- ------------------------------------------------------------
-- 5. ДОКУМЕНТЫ КОМПАНИЙ
-- Колонки: company_id, document_type, file_name, language,
--          status, verified_by, verified_at, uploaded_at
-- ------------------------------------------------------------

INSERT INTO company_documents (company_id, document_type, file_name, language, status, verified_by, verified_at, uploaded_at)
VALUES
    (1, 'COMPANY_REG_CERTIFICATE', 'techimport_reg_cert.pdf',   'ru', 'APPROVED', 2, NOW() - INTERVAL '8 days',  NOW() - INTERVAL '25 days'),
    (1, 'COMPANY_INN_CERTIFICATE', 'techimport_inn_cert.pdf',   'ru', 'APPROVED', 2, NOW() - INTERVAL '8 days',  NOW() - INTERVAL '25 days'),
    (1, 'COMPANY_CHARTER',         'techimport_charter.pdf',    'ru', 'APPROVED', 2, NOW() - INTERVAL '8 days',  NOW() - INTERVAL '25 days'),
    (1, 'COMPANY_VAT_CERTIFICATE', 'techimport_vat_cert.pdf',   'ru', 'PENDING',  NULL, NULL,                    NOW() - INTERVAL '2 days'),
    (2, 'COMPANY_REG_CERTIFICATE', 'silkroad_reg_cert.pdf',     'ru', 'PENDING',  NULL, NULL,                    NOW() - INTERVAL '3 days');

-- ------------------------------------------------------------
-- 6. ДОКУМЕНТЫ ФИЗИЧЕСКИХ ЛИЦ
-- Колонки: user_id, document_type, status,
--          verified_by, verified_at, uploaded_at
-- ------------------------------------------------------------

INSERT INTO individual_documents (user_id, document_type, status, verified_by, verified_at, uploaded_at)
VALUES
    (6, 'PERSON_PASSPORT', 'APPROVED', 2, NOW() - INTERVAL '5 days',  NOW() - INTERVAL '10 days'),
    (6, 'PERSON_PHOTO_ID', 'APPROVED', 2, NOW() - INTERVAL '5 days',  NOW() - INTERVAL '10 days'),
    (7, 'PERSON_PASSPORT', 'PENDING',  NULL, NULL,                     NOW() - INTERVAL '2 days');

-- ------------------------------------------------------------
-- 7. ХРАНИЛИЩЕ ФАЙЛОВ
-- Колонки: original_file_name, mime_type, size, path,
--          uploaded_by, uploaded_at
-- ------------------------------------------------------------

INSERT INTO file_storage (original_file_name, mime_type, size, path, uploaded_by, uploaded_at)
VALUES
    ('techimport_reg_cert.pdf',    'application/pdf', 204800, 'company-docs/techimport_reg_cert.pdf',    3, NOW() - INTERVAL '25 days'),
    ('techimport_inn_cert.pdf',    'application/pdf', 102400, 'company-docs/techimport_inn_cert.pdf',    3, NOW() - INTERVAL '25 days'),
    ('techimport_charter.pdf',     'application/pdf', 512000, 'company-docs/techimport_charter.pdf',     3, NOW() - INTERVAL '25 days'),
    ('silkroad_reg_cert.pdf',      'application/pdf', 204800, 'company-docs/silkroad_reg_cert.pdf',      5, NOW() - INTERVAL '3 days'),
    ('passport_user1.pdf',         'application/pdf',  98304, 'individual-docs/passport_user1.pdf',      6, NOW() - INTERVAL '10 days'),
    ('photo_id_user1.pdf',         'application/pdf',  81920, 'individual-docs/photo_id_user1.pdf',      6, NOW() - INTERVAL '10 days'),
    ('passport_user2.pdf',         'application/pdf',  98304, 'individual-docs/passport_user2.pdf',      7, NOW() - INTERVAL '2 days'),
    ('contract_tech_001.pdf',      'application/pdf', 307200, 'contracts/contract_tech_001.pdf',         3, NOW() - INTERVAL '20 days'),
    ('contract_tech_002.pdf',      'application/pdf', 307200, 'contracts/contract_tech_002.pdf',         3, NOW() - INTERVAL '10 days');

-- ------------------------------------------------------------
-- 8. КОНТРАКТЫ
-- Колонки: company_id, contract_numbers, contract_date,
--          contract_expiry, currency_code, total_amount,
--          status, file_name, created_at
-- ------------------------------------------------------------

INSERT INTO contracts (company_id, contract_numbers, contract_date, contract_expiry, currency_code, total_amount, status, file_name, created_at)
VALUES
    -- id=1
    (1, 'CNT-2024-001', '2024-01-10', '2025-01-10', 'USD', 500000.00, 'ACTIVE',  'contract_tech_001.pdf', NOW() - INTERVAL '20 days'),
    -- id=2
    (1, 'CNT-2024-002', '2024-03-01', '2024-12-31', 'EUR', 250000.00, 'ACTIVE',  'contract_tech_002.pdf', NOW() - INTERVAL '10 days');

-- ------------------------------------------------------------
-- 9. КОДЫ ТН ВЭД
-- Колонки: code, description, default_customs_duty_rate,
--          default_excise_rate, default_nds_rate,
--          user_id, date_created
-- ------------------------------------------------------------

INSERT INTO tnved_codes (code, description, default_customs_duty_rate, default_excise_rate, default_nds_rate, user_id, date_created)
VALUES
    -- id=1
    ('8517120000', 'Телефоны для сотовых сетей',              5.0000,  0.0000, 12.0000, 1, NOW()),
    -- id=2
    ('8471300000', 'Портативные компьютеры (ноутбуки)',        0.0000,  0.0000, 12.0000, 1, NOW()),
    -- id=3
    ('6203420000', 'Брюки мужские, хлопок',                  10.0000,  0.0000, 12.0000, 1, NOW()),
    -- id=4
    ('8703231900', 'Автомобили с ДВС, объём 1500–3000 куб.см',20.0000, 15.0000, 12.0000, 1, NOW()),
    -- id=5
    ('2204210000', 'Вина натуральные',                        15.0000, 30.0000, 12.0000, 1, NOW());

-- ------------------------------------------------------------
-- 10. БАЗОВЫЕ ЗАПИСИ ДЕКЛАРАЦИЙ
-- Колонки: document_type, status, currency, currency_rate,
--          file_name, created_at, submitted_at
-- document_type — значения из DeclarationDocumentType
-- currency      — значения из CurrencyEnum: USD,EUR,CNY,KZT,RUB
-- status        — значения из CustomsStatusEnum
-- id=1,2  → company_declarations
-- id=3,4  → user_declarations
-- id=5,6  → car_declarations
-- ------------------------------------------------------------

INSERT INTO declarations (document_type, status, currency, currency_rate, file_name, created_at, submitted_at)
VALUES
    -- id=1  компания, подана и одобрена
    ('FIN_INVOICE',       'APPROVED',  'USD', 87.50, 'decl_company_001.pdf', NOW() - INTERVAL '15 days', NOW() - INTERVAL '14 days'),
    -- id=2  компания, черновик
    ('FIN_INVOICE',       'DRAFT',     'EUR', 95.20, NULL,                   NOW() - INTERVAL '3 days',  NULL),
    -- id=3  физлицо, одобрена
    ('FIN_INVOICE',       'APPROVED',  'USD', 87.50, 'decl_user_001.pdf',    NOW() - INTERVAL '20 days', NOW() - INTERVAL '19 days'),
    -- id=4  физлицо, на рассмотрении
    ('FIN_PACKING_LIST',  'IN_REVIEW', 'CNY', 12.30, 'decl_user_002.pdf',    NOW() - INTERVAL '5 days',  NOW() - INTERVAL '4 days'),
    -- id=5  авто, одобрена
    ('CUSTOMS_DECLARATION','APPROVED', 'USD', 87.50, 'decl_car_001.pdf',     NOW() - INTERVAL '30 days', NOW() - INTERVAL '29 days'),
    -- id=6  авто, подана
    ('CUSTOMS_DECLARATION','SUBMITTED','USD', 87.50, NULL,                   NOW() - INTERVAL '7 days',  NOW() - INTERVAL '6 days');

-- ------------------------------------------------------------
-- 11. ДЕКЛАРАЦИИ КОМПАНИЙ
-- Колонки: id, company_id, contract_id, invoice_number,
--          invoice_date, incoterms_code, incoterms_place,
--          transport_type, transport_id
-- ------------------------------------------------------------

INSERT INTO company_declarations (id, company_id, contract_id, invoice_number, invoice_date, incoterms_code, incoterms_place, transport_type, transport_id)
VALUES
    (1, 1, 1, 'INV-2024-001', '2024-01-12', 'FOB', 'Shanghai',  'SEA', 'COSCO-SHIP-001'),
    (2, 1, 2, 'INV-2024-002', '2024-03-05', 'CIF', 'Frankfurt', 'AIR', 'LH-CARGO-002');

-- ------------------------------------------------------------
-- 12. ДЕКЛАРАЦИИ ФИЗИЧЕСКИХ ЛИЦ
-- Колонки: id, user_id, arrival_way, tracking_number, is_personal
-- ------------------------------------------------------------

INSERT INTO user_declarations (id, user_id, arrival_way, tracking_number, is_personal)
VALUES
    (3, 6, 'AIR',  'TRK-2024-AIR-001', TRUE),
    (4, 7, 'ROAD', 'TRK-2024-RD-002',  FALSE);

-- ------------------------------------------------------------
-- 13. ДЕКЛАРАЦИИ НА АВТОМОБИЛИ
-- Колонки: id, user_id, company_id, vin_code, brand, model,
--          color, manufacture_year, engine_volume,
--          engine_power_hp, engine_power_kw, fuel_type,
--          title_number, mileage, car_value,
--          nds_amount, customs_duty_amount, excise_amount
-- fuel_type — значения из FuelTypeEnum
-- ------------------------------------------------------------

INSERT INTO car_declarations (id, user_id, company_id, vin_code, brand, model, color, manufacture_year, engine_volume, engine_power_hp, engine_power_kw, fuel_type, title_number, mileage, car_value, nds_amount, customs_duty_amount, excise_amount)
VALUES
    (5, 6, NULL, '1HGBH41JXMN109186', 'Toyota', 'Camry',    'Белый',  2021, 2500, 180, 132, 'PETROL', 'KG-TP-001234', 35000, 25000.00, 3000.00, 5000.00,  750.00),
    (6, 7, NULL, 'WBAFW31010C123456', 'BMW',    '3 Series', 'Чёрный', 2020, 2000, 156, 115, 'PETROL', 'KG-TP-005678', 52000, 32000.00, 3840.00, 6400.00,  960.00);

-- ------------------------------------------------------------
-- 14. ТОВАРЫ В ДЕКЛАРАЦИЯХ
-- Колонки: declaration_id, product_name, product_description,
--          product_materials, tnved_code (FK → tnved_codes.id),
--          quantity, unit_type, weight_netto, weight_brutto,
--          price_per_unit, total_price, nds, customs_duty,
--          excise, created_at
-- ------------------------------------------------------------

INSERT INTO declaration_products (declaration_id, product_name, product_description, product_materials, tnved_code, quantity, unit_type, weight_netto, weight_brutto, price_per_unit, total_price, nds, customs_duty, excise, created_at)
VALUES
    -- Декларация 1 (компания, APPROVED)
    (1, 'Смартфон Samsung Galaxy S24',  'Смартфон 256GB',         'Пластик, стекло, металл', 1, 100.000, 'PCS', 150.000, 165.000,  800.00,  80000.00, 9600.00, 4000.00,    0.00, NOW()),
    (1, 'Ноутбук Lenovo ThinkPad X1',   'Ноутбук 14", 16GB RAM',  'Алюминий, пластик',       2,  50.000, 'PCS', 125.000, 140.000, 1200.00,  60000.00, 7200.00,    0.00,    0.00, NOW()),

    -- Декларация 2 (компания, DRAFT)
    (2, 'Брюки мужские хлопок',         'Размеры S–XXL',          'Хлопок 100%',             3, 500.000, 'PCS', 250.000, 280.000,   15.00,   7500.00,  900.00,  750.00,    0.00, NOW()),

    -- Декларация 3 (физлицо, APPROVED)
    (3, 'Смартфон iPhone 15',           'iPhone 15 128GB',        'Алюминий, стекло',        1,   2.000, 'PCS',   0.500,   0.600,  900.00,   1800.00,  216.00,   90.00,    0.00, NOW()),

    -- Декларация 4 (физлицо, IN_REVIEW)
    (4, 'Платья женские летние',        'Лёгкие летние платья',   'Полиэстер 100%',          3,  10.000, 'PCS',   5.000,   6.000,   20.00,    200.00,   24.00,   20.00,    0.00, NOW());

-- ------------------------------------------------------------
-- 15. ПЛАТЁЖНЫЕ СЧЕТА
-- Колонки: declaration_id, status, total_invoice_nds,
--          total_invoice_customs_duty, total_invoice_excise,
--          invoice_total, date_created, date_to_pay, date_paid
-- status — значения из PaymentStatusEnum
-- ------------------------------------------------------------

INSERT INTO payment_invoices (declaration_id, status, total_invoice_nds, total_invoice_customs_duty, total_invoice_excise, invoice_total, date_created, date_to_pay, date_paid)
VALUES
    -- Декларация 1 — оплачен
    (1, 'PAID',    16800.00, 4000.00,   0.00, 20800.00, NOW() - INTERVAL '14 days', NOW() - INTERVAL '1 day',   NOW() - INTERVAL '3 days'),

    -- Декларация 3 — оплачен
    (3, 'PAID',      216.00,   90.00,   0.00,   306.00, NOW() - INTERVAL '19 days', NOW() - INTERVAL '5 days',  NOW() - INTERVAL '8 days'),

    -- Декларация 4 — выставлен, не оплачен
    (4, 'ISSUED',     24.00,   20.00,   0.00,    44.00, NOW() - INTERVAL '4 days',  NOW() + INTERVAL '11 days', NULL),

    -- Декларация 5 (авто) — оплачен
    (5, 'PAID',     3000.00, 5000.00, 750.00,  8750.00, NOW() - INTERVAL '29 days', NOW() - INTERVAL '15 days', NOW() - INTERVAL '20 days'),

    -- Декларация 6 (авто) — просрочен
    (6, 'OVERDUE',  3840.00, 6400.00, 960.00, 11200.00, NOW() - INTERVAL '6 days',  NOW() - INTERVAL '1 day',   NULL);

-- ------------------------------------------------------------
-- 16. КЭШИ ОТЧЁТОВ
-- Колонки: company_id, period_start, period_end,
--          total_import, total_export, total_taxes_paid,
--          total_rejected, generated_at
-- ------------------------------------------------------------

INSERT INTO reported_caches (company_id, period_start, period_end, total_import, total_export, total_taxes_paid, total_rejected, generated_at)
VALUES
    (1, NOW() - INTERVAL '30 days', NOW(), 2, 0, 20800.00, 0, NOW()),
    (2, NOW() - INTERVAL '30 days', NOW(), 0, 0,     0.00, 0, NOW());
