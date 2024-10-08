-- Inserção com o novo formato de tabela
INSERT INTO users (id, city, complement, country, email, name, number, password, state, street, user_name, full_name,
                   account_non_expired, account_non_locked, credentials_non_expired, enabled)
VALUES
    (3, 'New York', 'Apt 5C', 'USA', 'joao@teste.com', 'João', '456', '19bbf735b27066f2f145e602624e1b24a3fbc54cd5dfd3143fc5feea6bdee9e139ca7332d4806b9f', 'NY', '5th Avenue', 'joao@teste.com', 'João da Silva', 1, 1, 1, 1),
    (4, 'Chicago', 'Apt 2A', 'USA', 'maria@teste.com', 'Maria', '789', '75ec349c1b0ef4ee7b249d0b83ae4861853f3aa77bce8c4b15f28cd43c6424ab4f29df431831bb0d', 'IL', 'Lake Shore Drive', 'maria@teste.com', 'Maria Souza', 1, 1, 1, 1);