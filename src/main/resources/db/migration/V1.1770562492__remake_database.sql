ALTER TABLE users ADD COLUMN IF NOT EXISTS birth_date DATE not null;

ALTER TABLE users DROP COLUMN IF EXISTS first_name;
ALTER TABLE users DROP COLUMN IF EXISTS last_name;

ALTER TABLE users ADD IF NOT EXISTS full_name varchar not null;

CREATE TABLE photo_users(
    id bigserial primary key,
    file_name varchar not null unique,
    user_id bigint references users(id) unique
);

CREATE TABLE signature_users(
    id bigserial primary key,
    signature_name varchar not null unique,
    user_id bigint references users(id) unique
);

ALTER TABLE individual_documents DROP COLUMN IF EXISTS individual_id;
ALTER TABLE individual_documents ADD COLUMN IF NOT EXISTS user_id bigint references users(id);
DROP TABLE IF EXISTS individuals;
ALTER TABLE declarations DROP COLUMN IF EXISTS participant_id;


ALTER TABLE users DROP COLUMN IF EXISTS participant_id;
ALTER TABLE companies ADD COLUMN IF NOT EXISTS owner_id bigint references users(id) unique;