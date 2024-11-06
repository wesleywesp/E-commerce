CREATE TABLE cart_items (
    id BIGSERIAL PRIMARY KEY,
    cart_id BIGSERIAL,
    product_id BIGSERIAL,
    quantity INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (cart_id) REFERENCES carts(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES produtos(id) ON DELETE CASCADE
);