CREATE TABLE products (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    sku VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    quantity INT NOT NULL CHECK (quantity >= 0)
);

INSERT INTO products (sku, name, quantity)
VALUES ('LAP-001', 'Laptop', 10);

INSERT INTO products (sku, name, quantity)
VALUES ('MOU-001', 'Mouse', 25);

INSERT INTO products (sku, name, quantity)
VALUES ('KEY-001', 'Keyboard', 15);
