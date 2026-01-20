CREATE TABLE IF NOT EXISTS participants(
    id bigserial primary key,
    participant_type VARCHAR(31),
    address TEXT,
    tin varchar(50)
);

ALTER TABLE companies ALTER COLUMN id DROP DEFAULT;

DROP SEQUENCE IF EXISTS companies_id_seq CASCADE;

ALTER TABLE companies ADD CONSTRAINT fk_companies_participants foreign key (id) references participants(id);

ALTER TABLE individuals ALTER COLUMN id DROP DEFAULT;

DROP SEQUENCE IF EXISTS individuals_id_seq CASCADE;

ALTER TABLE individuals ADD CONSTRAINT fk_individuals_participants foreign key (id) references participants(id);

ALTER TABLE users ADD COLUMN participant_id bigint references participants(id);
