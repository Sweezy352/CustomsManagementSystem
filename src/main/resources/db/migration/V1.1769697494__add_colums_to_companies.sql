ALTER TABLE companies ADD COLUMN IF NOT EXISTS okpo varchar not null unique;
ALTER TABLE companies ADD COLUMN IF NOT EXISTS customs_code varchar not null unique;

DO $$
    BEGIN
        IF NOT EXISTS (SELECT 1 FROM pg_constraint WHERE conname = 'uk_companies_name') THEN
            ALTER TABLE companies ADD CONSTRAINT uk_companies_name UNIQUE (name);
        END IF;
    END $$;

ALTER TABLE company_documents ADD COLUMN IF NOT EXISTS language varchar;
ALTER TABLE individual_documents ADD COLUMN IF NOT EXISTS language varchar;
ALTER TABLE declaration_documents ADD COLUMN IF NOT EXISTS language varchar;
ALTER TABLE declaration_product_documents ADD COLUMN IF NOT EXISTS language varchar;

ALTER TABLE company_documents ALTER COLUMN language SET NOT NULL;
ALTER TABLE individual_documents ALTER COLUMN language SET NOT NULL;
ALTER TABLE declaration_documents ALTER COLUMN language SET NOT NULL;
ALTER TABLE declaration_product_documents ALTER COLUMN language SET NOT NULL;


ALTER TABLE company_documents DROP COLUMN IF EXISTS file_id;
ALTER TABLE individual_documents DROP COLUMN IF EXISTS file_id;
ALTER TABLE declaration_documents DROP COLUMN IF EXISTS file_id;
ALTER TABLE declaration_product_documents DROP COLUMN IF EXISTS file_id;

ALTER TABLE company_documents ADD COLUMN IF NOT EXISTS file_name varchar not null unique;
ALTER TABLE individual_documents ADD COLUMN IF NOT EXISTS file_name varchar not null unique;
ALTER TABLE declaration_documents ADD COLUMN IF NOT EXISTS file_name varchar not null unique;
ALTER TABLE declaration_product_documents ADD COLUMN IF NOT EXISTS file_name varchar not null unique;

DROP TABLE IF EXISTS file_storage;