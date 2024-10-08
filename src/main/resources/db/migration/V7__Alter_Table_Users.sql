-- Flyway migration script to alter the 'users' table

-- Adding the missing columns to the 'users' table
ALTER TABLE users
    ADD COLUMN user_name VARCHAR(255) DEFAULT NULL,
ADD COLUMN full_name VARCHAR(255) DEFAULT NULL,
ADD COLUMN account_non_expired BIT(1) DEFAULT NULL,
ADD COLUMN account_non_locked BIT(1) DEFAULT NULL,
ADD COLUMN credentials_non_expired BIT(1) DEFAULT NULL,
ADD COLUMN enabled BIT(1) DEFAULT NULL;

-- Adding unique constraint on 'user_name'
ALTER TABLE users
    ADD CONSTRAINT uk_user_name UNIQUE (user_name);


-- Atualizando registros já existentes na tabela `users` para preencher as novas colunas
UPDATE users
SET user_name = email,  -- Usando o campo `email` como `user_name`
    full_name = name,   -- Usando o campo `name` como `full_name`
    password = "19bbf735b27066f2f145e602624e1b24a3fbc54cd5dfd3143fc5feea6bdee9e139ca7332d4806b9f",
    account_non_expired = 1,
    account_non_locked = 1,
    credentials_non_expired = 1,
    enabled = 1;