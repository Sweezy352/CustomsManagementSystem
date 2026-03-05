CREATE TABLE IF NOT EXISTS participants(
    id bigserial primary key,
    participant_type VARCHAR(31),
    address TEXT,
    tin varchar(50)
);


ALTER TABLE individuals ALTER COLUMN id DROP DEFAULT;

DROP SEQUENCE IF EXISTS individuals_id_seq CASCADE;

DO $$
    BEGIN
        IF NOT EXISTS (SELECT 1 FROM pg_constraint WHERE conname = 'fk_individuals_participants') THEN
            ALTER TABLE individuals
                ADD CONSTRAINT fk_individuals_participants
                    FOREIGN KEY (id) REFERENCES participants(id);
        END IF;
    END $$;

ALTER TABLE users ADD COLUMN IF NOT EXISTS participant_id bigint references participants(id);
