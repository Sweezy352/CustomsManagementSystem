ALTER TABLE companies ADD COLUMN okpo varchar not null unique;
ALTER TABLE companies ADD COLUMN  customs_code varchar not null unique;
ALTER TABLE companies ADD CONSTRAINT uk_companies_name unique (name);