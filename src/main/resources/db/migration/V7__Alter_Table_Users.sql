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
    password = "2d0fc149d37b3b18cc9c6279f9d80ec2b6d96e6d9f24eeb359f5ebc1f92fad56327452b5a5ceed47f3aea20c957031cf",
    account_non_expired = 1,
    account_non_locked = 1,
    credentials_non_expired = 1,
    enabled = 1;