CREATE TABLE users (
    id bigserial primary key,
    mail varchar not null unique,
    phone varchar(20) not null unique,
    password varchar not null,
    created_at date default now()
    --company_id bigint references companies(id)
);

CREATE TABLE companies (
    id bigserial primary key,
    name varchar not null,
    tin varchar not null unique, -- регистрационный номер
    address varchar not null,
    status varchar(20) default 'PENDING',
    created_at date default now(),
    verified_by bigint references users(id), -- кто одобрил эту компанию
    verified_at date default now()
);

ALTER TABLE users add column company_id bigint references companies(id);

CREATE TABLE roles (
    id bigserial primary key,
    role_name varchar(20) unique not null
);

CREATE TABLE m2m_users_roles(
    user_id bigint references users(id),
    role_id bigint references roles(id)
);

CREATE TABLE individuals (
    id bigserial primary key,
    user_id bigint references users(id),
    full_name varchar not null,
    passport_series varchar(20) not null unique,
    birth_date date not null,
    address varchar not null,
    tin varchar(20) not null unique,
    status varchar(20) default 'PENDING',
    verified_by bigint references users(id),
    verified_at date
);

CREATE TABLE tnved_codes (
    id bigserial primary key,
    code varchar not null unique,
    description text not null,
    default_customs_duty_rate DECIMAL(8, 4) not null,
    default_excise_rate DECIMAL(8,4) not null,
    default_nds_rate DECIMAL(8,4) not null
);



CREATE TABLE declarations (
    id bigserial primary key,
    company_id bigint references companies(id),
    individual_id bigint references individuals(id),
    type varchar(20) not null,
    status varchar(20) default 'IN_REVIEW',
    created_at timestamp with time zone default now(),
    submitted_at timestamp with time zone,
    reviewed_by bigint references users(id),
    reviewed_at timestamp with time zone,
    check ( company_id notnull or individual_id notnull )
);

CREATE TABLE declaration_products (
    id bigserial primary key,
    declaration_id bigint references declarations(id),
    name varchar not null,
    tnved_code bigint references tnved_codes(id) not null,
    quantity bigint not null,
    weight DECIMAL(10, 3) not null,
    price_per_unit DECIMAL (19, 2) not null,
    default_nds_rate DECIMAL(8,4) not null,
    country_of_origin varchar(10) not null
);

CREATE TABLE file_storage (
    id bigserial primary key,
    original_file_name varchar not null,
    mime_type varchar not null,
    size bigint not null,
    path varchar not null,
    uploaded_by bigint references users(id),
    uploaded_at timestamp with time zone default now()
);

CREATE TABLE company_documents (
    id bigserial primary key,
    company_id bigint references companies(id),
    document_type varchar(25) not null,
    file_id bigint references file_storage(id),
    uploaded_at timestamp with time zone default now(),
    status varchar(10) default 'PENDING',
    verified_by bigint references users(id),
    verified_at timestamp with time zone
);

CREATE TABLE individual_documents (
    id bigserial primary key,
    individual_id bigint references individuals(id),
    document_type varchar not null,
    file_id bigint references file_storage(id),
    uploaded_at timestamp with time zone default now()
);

CREATE TABLE declaration_documents (
    id bigserial primary key,
    declaration_id bigint references declarations(id),
    user_id bigint references users(id),
    type varchar(10) not null,
    file_id bigint references file_storage(id),
    uploaded_at timestamp with time zone default now()
);

CREATE TABLE declarations_history (
    id bigserial primary key,
    declaration_id bigint references declarations(id),
    user_id bigint references users(id),
    action varchar(10) not null, -- будет enum
    comment varchar,
    date timestamp with time zone not null
);

CREATE TABLE reported_caches (
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