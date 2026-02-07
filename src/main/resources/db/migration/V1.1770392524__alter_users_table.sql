ALTER TABLE users DROP COLUMN IF EXISTS username;
ALTER TABLE users ADD COLUMN IF NOT EXISTS first_name varchar not null ;
ALTER TABLE users ADD COLUMN IF NOT EXISTS last_name varchar not null ;
ALTER TABLE users ADD COLUMN IF NOT EXISTS pin varchar unique not null ;
ALTER TABLE users ADD COLUMN IF NOT EXISTS passport_number varchar unique not null ;