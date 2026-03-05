ALTER TABLE declarations ADD COLUMN IF NOT EXISTS file_name varchar unique;

DROP TABLE IF EXISTS participants;
ALTER TABLE declarations ADD COLUMN IF NOT EXISTS currency varchar not null;
DROP TABLE IF EXISTS signature_users;
DROP TABLE IF EXISTS photo_users;
ALTER TABLE users ADD COLUMN IF NOT EXISTS photo_profile_s3 varchar not null unique;
ALTER TABLE users ADD COLUMN IF NOT EXISTS signature_s3 varchar not null unique;
