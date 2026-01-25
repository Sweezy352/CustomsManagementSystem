CREATE TABLE branches_company(
    id bigserial primary key,
    branch_name varchar not null,
    address varchar not null,
    phone varchar not null,
    company_id bigint references companies(id)
);