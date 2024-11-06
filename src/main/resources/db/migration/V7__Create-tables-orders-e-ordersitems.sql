CREATE TABLE orders (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGSERIAL NOT NULL,
    total_amount DECIMAL(15, 2) NOT NULL,
    date_order DATE NOT NULL,
    status VARCHAR(20) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
);
CREATE TABLE order_items (
    id BIGSERIAL PRIMARY KEY,
    order_id BIGSERIAL NOT NULL,
    product_id BIGSERIAL NOT NULL,
    quantity INT NOT NULL,
    price DECIMAL(15, 2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES produtos(id)
);
