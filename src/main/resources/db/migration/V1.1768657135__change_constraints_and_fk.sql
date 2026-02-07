

DO $$
    BEGIN
        IF NOT EXISTS (SELECT 1 FROM pg_constraint WHERE conname = 'uq_users_participant_id') THEN
            ALTER TABLE users
                ADD CONSTRAINT uq_users_participant_id
                    UNIQUE (participant_id);
        END IF;
    END $$;


ALTER TABLE payment_invoices DROP COLUMN IF EXISTS company_id;

ALTER TABLE payment_invoices ADD COLUMN IF NOT EXISTS participant_id bigint references participants(id);

ALTER TABLE declarations DROP COLUMN IF EXISTS company_id;
ALTER TABLE declarations DROP COLUMN IF EXISTS individual_id;

ALTER TABLE declarations ADD COLUMN IF NOT EXISTS participant_id bigint references participants(id);