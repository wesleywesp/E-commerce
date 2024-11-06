CREATE TABLE produtos (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    description TEXT,
    image_url VARCHAR(255),
    quantity INTEGER,
    category_id BIGINT, -- Chave estrangeira para a tabela category

    CONSTRAINT fk_category
        FOREIGN KEY (category_id)
        REFERENCES category(id)
        ON DELETE SET NULL -- Define ação para quando uma categoria é excluída
);
