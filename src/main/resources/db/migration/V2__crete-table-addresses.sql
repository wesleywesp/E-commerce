-- Tabela de Endereços
CREATE TABLE addresses (
    id BIGSERIAL NOT NULL,
    state VARCHAR(255) NOT NULL,
    city VARCHAR(100) NOT NULL,
    street VARCHAR(50),
    district VARCHAR(50) NOT NULL,
    codepostal VARCHAR(10) NOT NULL,
    number INT NOT NULL,
    complement VARCHAR(50),
    user_id BIGINT NOT NULL, -- Chave estrangeira para associar ao usuário

    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);