CREATE TABLE IF NOT EXISTS branches_company(
    id bigserial primary key,
    branch_name varchar not null,
    address varchar not null,
    phone varchar not null,
    company_id bigint references companies(id)
);

CREATE TABLE IF NOT EXISTS refresh_tokens(
    id bigserial primary key,
    token varchar not null,
    user_id bigint references users(id),
    expiry_date timestamp not null
);