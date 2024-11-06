CREATE TABLE category (
    id BIGSERIAL PRIMARY KEY,
    main_category VARCHAR(50) NOT NULL, -- Enum armazenado como string
    sub_category VARCHAR(50) NOT NULL, -- Enum armazenado como string
    description TEXT
);
