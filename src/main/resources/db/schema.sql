CREATE TABLE IF NOT EXISTS products (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    sku VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    quantity INT NOT NULL CHECK (quantity >= 0)
);

CREATE TABLE IF NOT EXISTS stock_movements (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    type VARCHAR(20) NOT NULL,
    quantity INT NOT NULL CHECK (quantity > 0),
    created_at TIMESTAMP NOT NULL,
    product_id BIGINT NOT NULL,
    CONSTRAINT fk_stock_movements_product
        FOREIGN KEY (product_id) REFERENCES products(id)
);

MERGE INTO products (sku, name, quantity) KEY (sku)
VALUES ('LAP-001', 'Laptop', 10);

MERGE INTO products (sku, name, quantity) KEY (sku)
VALUES ('MOU-001', 'Mouse', 25);

MERGE INTO products (sku, name, quantity) KEY (sku)
VALUES ('KEY-001', 'Keyboard', 15);
