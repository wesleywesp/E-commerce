-- Tabela de Usuários
CREATE TABLE users (
    id BIGSERIAL NOT NULL,
    name VARCHAR(30) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    username VARCHAR(30) UNIQUE NOT NULL,
    password VARCHAR(500) NOT NULL,
    phone VARCHAR(15) UNIQUE NOT NULL,
    ativo BOOLEAN DEFAULT TRUE,
    perfil VARCHAR(10) DEFAULT 'CLIENT' CHECK (perfil IN ('CLIENT', 'ADMIN')),

    PRIMARY KEY (id)
);