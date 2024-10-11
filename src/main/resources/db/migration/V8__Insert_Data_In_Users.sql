-- Inserção com o novo formato de tabela
INSERT INTO users (id, city, complement, country, email, name, number, password, state, street, user_name, full_name,
                   account_non_expired, account_non_locked, credentials_non_expired, enabled)
VALUES
    (3, 'New York', 'Apt 5C', 'USA', 'joao@teste.com', 'João', '456', '2d0fc149d37b3b18cc9c6279f9d80ec2b6d96e6d9f24eeb359f5ebc1f92fad56327452b5a5ceed47f3aea20c957031cf', 'NY', '5th Avenue', 'joao@teste.com', 'João da Silva', 1, 1, 1, 1),
    (4, 'Chicago', 'Apt 2A', 'USA', 'maria@teste.com', 'Maria', '789', '2d0fc149d37b3b18cc9c6279f9d80ec2b6d96e6d9f24eeb359f5ebc1f92fad56327452b5a5ceed47f3aea20c957031cf', 'IL', 'Lake Shore Drive', 'maria@teste.com', 'Maria Souza', 1, 1, 1, 1);