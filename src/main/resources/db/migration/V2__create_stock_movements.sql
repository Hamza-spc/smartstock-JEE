CREATE TABLE stock_movements (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    type VARCHAR(20) NOT NULL,
    quantity INT NOT NULL CHECK (quantity > 0),
    created_at TIMESTAMP NOT NULL,
    product_id BIGINT NOT NULL,
    CONSTRAINT fk_stock_movements_product
        FOREIGN KEY (product_id) REFERENCES products(id)
);
