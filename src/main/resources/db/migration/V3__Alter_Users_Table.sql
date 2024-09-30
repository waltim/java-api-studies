-- Renomear a coluna "id" para "key" na tabela "users"
ALTER TABLE users RENAME COLUMN id TO `key`;

-- Garantir que a coluna "key" continue como a chave primária
ALTER TABLE users DROP PRIMARY KEY, ADD PRIMARY KEY (`key`);

-- Atualizar o auto_increment para a nova coluna "key"
ALTER TABLE users MODIFY `key` bigint auto_increment;