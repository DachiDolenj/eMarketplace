CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    username VARCHAR(20) NOT NULL,
    password VARCHAR(225) NOT NULL,
    email VARCHAR(225) NOT NULL,
    birthday DATE NOT NULL
);

ALTER TABLE market_item
ADD COLUMN user_id UUID,
ADD CONSTRAINT fk_market_item_user
    FOREIGN KEY (user_id)
    REFERENCES users(id);