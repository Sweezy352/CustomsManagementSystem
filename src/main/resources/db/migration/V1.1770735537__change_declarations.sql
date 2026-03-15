DROP TABLE invoice_descriptions;
ALTER TABLE payment_invoices DROP COLUMN IF EXISTS declaration_id;
DROP TABLE declaration_documents;
DROP TABLE declaration_product_documents;
DROP TABLE declaration_products;
DROP TABLE declarations_history;
DROP TABLE declarations;

CREATE TABLE IF NOT EXISTS contracts(
    id bigserial primary key,
    company_id bigint references companies(id),
    contract_numbers varchar not null,
    contract_date DATE not null,
    expiry_date DATE not null,
    currency_code varchar(10) not null,
    total_amount DECIMAL(19,2) not null,
    status varchar(20) default 'ACTIVE',
    file_name varchar not null unique,
    created_at timestamp with time zone default now()
);

CREATE TABLE IF NOT EXISTS declarations(
    id bigserial primary key,
    document_type varchar not null,
    status varchar(20) default 'DRAFT',
    created_at timestamp with time zone default now(),
    submitted_at timestamp with time zone default now(),
    currency_rate DECIMAL(19,2) not null
);

CREATE TABLE IF NOT EXISTS company_declarations(
    declaration_id bigint references declarations(id) primary key,
    company_id bigint references companies(id),
    contract_id bigint references contracts(id),
    invoice_number varchar not null,
    invoice_date DATE not null,
    incoterms_code varchar(10) not null,
    incoterms_place varchar not null,
    transport_type varchar(20) not null,
    transport_id varchar not null
);

CREATE TABLE IF NOT EXISTS user_declarations(
    declaration_id bigint references declarations(id) primary key,
    user_id bigint references users(id),
    arrival_way varchar not null,
    tracking_number varchar not null,
    is_personal boolean not null
);

CREATE TABLE IF NOT EXISTS declaration_products(
    id bigserial primary key,
    declaration_id bigint references declarations(id),
    product_name varchar not null,
    product_description varchar not null,
    product_materials varchar not null,
    tnved_code bigint references tnved_codes(id),
    quantity DECIMAL(15, 3) not null,
    unit_type varchar not null,
    weight_netto DECIMAL(15, 3) not null,
    weight_brutto DECIMAL(15, 3) not null,
    price_per_unit DECIMAL(19, 2) not null,
    total_price DECIMAL(19, 2) not null,
    nds DECIMAL(19, 2) not null,
    customs_duty DECIMAL(19, 2) not null,
    excise DECIMAL(19, 2),
    created_at timestamp with time zone default now()
);

CREATE TABLE IF NOT EXISTS car_declarations(
    id bigserial primary key,
    declaration_id bigint references declarations(id),
    vin_code varchar(20) not null unique,
    brand varchar(50) not null,
    model varchar(50) not null,
    color varchar(20) not null,
    manufacture_year int not null,
    engine_volume int, --Can be null because electro transport doesn't have engine volume
    engine_power_hp int, --Can be null because electro transport doesn't have engine power
    engine_power_kw int, --Very important for electro transports
    fuel_type varchar(20) not null,
    title_number varchar(20) not null, --In other words (Тех.паспорт)
    mileage int
);

ALTER TABLE payment_invoices DROP COLUMN IF EXISTS participant_id;
ALTER TABLE payment_invoices ADD COLUMN IF NOT EXISTS declaration_id bigint references declarations(id);
