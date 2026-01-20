CREATE TABLE IF NOT EXISTS users (
    id bigserial primary key,
    mail varchar not null unique,
    phone varchar(20) not null unique,
    password varchar not null,
    created_at date default now()
);

CREATE TABLE IF NOT EXISTS companies (
    id bigserial primary key,
    name varchar not null,
    tin varchar not null unique, -- регистрационный номер
    address varchar not null,
    status varchar(20) default 'PENDING',
    created_at timestamp with time zone default now(),
    verified_by bigint references users(id), -- кто одобрил эту компанию
    verified_at timestamp with time zone
);

ALTER TABLE users add column IF NOT EXISTS company_id bigint references companies(id);

CREATE TABLE IF NOT EXISTS roles (
    id bigserial primary key,
    role_name varchar(20) unique not null
);

CREATE TABLE IF NOT EXISTS m2m_users_roles(
    user_id bigint references users(id),
    role_id bigint references roles(id)
);

CREATE TABLE IF NOT EXISTS individuals (
    id bigserial primary key,
    user_id bigint references users(id) unique,
    full_name varchar not null,
    passport_series varchar(20) not null unique,
    birth_date date not null,
    address varchar not null,
    tin varchar(20) not null unique,
    created_at timestamp with time zone default now(),
    status varchar(20) default 'PENDING',
    verified_by bigint references users(id),
    verified_at timestamp with time zone
);

CREATE TABLE IF NOT EXISTS tnved_codes (
    id bigserial primary key,
    code varchar not null unique,
    description text not null,
    default_customs_duty_rate DECIMAL(8, 4) not null,
    default_excise_rate DECIMAL(8,4) not null,
    default_nds_rate DECIMAL(8,4) not null
);



CREATE TABLE IF NOT EXISTS declarations (
    id bigserial primary key,
    company_id bigint references companies(id),
    individual_id bigint references individuals(id),
    type varchar(20) not null,
    status varchar(20) default 'DRAFT',
    created_at timestamp with time zone default now(),
    verified_at timestamp with time zone,
    verified_by bigint references users(id),
    check ( company_id notnull or individual_id notnull )
);

CREATE TABLE IF NOT EXISTS declaration_products (
    id bigserial primary key,
    declaration_id bigint references declarations(id),
    name varchar not null,
    tnved_code bigint references tnved_codes(id) not null,
    quantity bigint not null,
    weight DECIMAL(10, 3) not null,
    price_per_unit DECIMAL (19, 2) not null,
    default_nds_rate DECIMAL(8,4) not null,
    default_customs_duty_rate DECIMAL(8, 4) not null,
    default_excise_rate DECIMAL(8,4) not null,
    country_of_origin varchar(10) not null,
    status varchar(20) default 'DRAFT',
    created_at timestamp with time zone default now(),
    verified_by bigint references users(id),
    verified_at timestamp with time zone
);

CREATE TABLE IF NOT EXISTS file_storage (
    id bigserial primary key,
    original_file_name varchar not null,
    mime_type varchar not null,
    size bigint not null,
    path varchar not null,
    uploaded_by bigint references users(id),
    uploaded_at timestamp with time zone default now()
);

CREATE TABLE IF NOT EXISTS company_documents (
    id bigserial primary key,
    company_id bigint references companies(id),
    document_type varchar(25) not null,
    file_id bigint references file_storage(id) unique ,
    uploaded_at timestamp with time zone default now(),
    status varchar(10) default 'PENDING',
    verified_by bigint references users(id),
    verified_at timestamp with time zone
);

CREATE TABLE IF NOT EXISTS individual_documents (
    id bigserial primary key,
    individual_id bigint references individuals(id),
    document_type varchar not null,
    file_id bigint references file_storage(id),
    uploaded_at timestamp with time zone default now(),
    status varchar(10) default 'PENDING',
    verified_by bigint references users(id),
    verified_at timestamp with time zone
);

CREATE TABLE IF NOT EXISTS declaration_documents (
    id bigserial primary key,
    declaration_id bigint references declarations(id),
    product_id bigint references declaration_products(id),
    type varchar not null,
    file_id bigint references file_storage(id) unique,
    uploaded_at timestamp with time zone default now(),
    status varchar(20) default 'PENDING',
    verified_by bigint references users(id),
    verified_at timestamp with time zone
);

CREATE TABLE IF NOT EXISTS declarations_history (
    id bigserial primary key,
    declaration_id bigint references declarations(id),
    user_id bigint references users(id),
    action varchar(10) not null, -- будет enum
    comment varchar,
    date timestamp with time zone not null
);

CREATE TABLE IF NOT EXISTS reported_caches (
    id bigserial primary key,
    company_id bigint references companies(id),
    period_start timestamp with time zone not null,
    period_end timestamp with time zone not null,
    total_import bigint default 0,
    total_export bigint default 0,
    total_taxes_paid DECIMAL(19, 2) default 0,
    total_rejected bigint default 0,
    generated_at timestamp with time zone default now()
);

CREATE TABLE IF NOT EXISTS payment_invoices (
    id bigserial primary key,
    company_id bigint references companies(id),
    declaration_id bigint references declarations(id),
    status varchar(10) default 'ISSUED',
    total_invoice_nds DECIMAL(19, 2) not null,
    total_invoice_customs_duty DECIMAL(19, 2) not null,
    total_invoice_excise DECIMAL(19, 2) not null,
    invoice_total DECIMAL(19, 2) not null,
    date_created timestamp with time zone default now(),
    date_to_pay timestamp with time zone default now() + interval '15 days',
    date_paid timestamp with time zone
);

CREATE TABLE IF NOT EXISTS invoice_descriptions (
    id bigserial primary key,
    invoice_id bigint references payment_invoices(id),
    description varchar not null,
    amount DECIMAL(19, 2) not null,
    source_product_id bigint references declaration_products(id)
);

CREATE TABLE IF NOT EXISTS declaration_product_documents(
    id bigserial primary key,
    declaration_product_id bigint references declaration_products,
    type varchar not null,
    file_id bigint references file_storage(id),
    uploaded_at timestamp with time zone default now(),
    status varchar(20) default 'PENDING',
    verified_by bigint references users(id),
    verified_at timestamp with time zone default now()
);

ALTER TABLE tnved_codes ADD COLUMN IF NOT EXISTS user_id bigint references users(id), ADD COLUMN IF NOT EXISTS date_created timestamp with time zone default now();