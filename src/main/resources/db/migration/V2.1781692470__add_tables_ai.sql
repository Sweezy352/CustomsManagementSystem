CREATE TABLE IF NOT EXISTS ai_chats(
    id uuid default gen_random_uuid() primary key,
    created_at timestamp with time zone default now(),
    updated_at timestamp with time zone default now()
);


CREATE TABLE IF NOT EXISTS ai_chat_messages(
    id uuid default gen_random_uuid() primary key,
    text varchar not null,
    role varchar not null,
    created_at timestamp with time zone default now(),
    ai_chat_id uuid references ai_chats(id)
);

ALTER TABLE users ADD COLUMN IF NOT EXISTS ai_chat_id uuid references ai_chats(id) unique;