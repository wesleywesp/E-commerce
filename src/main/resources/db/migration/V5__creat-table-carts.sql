CREATE TABLE carts (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGSERIAL,
    total_price DECIMAL(10, 2),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
