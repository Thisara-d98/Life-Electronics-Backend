CREATE TABLE items (
    id SERIAL PRIMARY KEY,
    product_type INTEGER NOT NULL,
    brand VARCHAR(255) NOT NULL,
    price NUMERIC NOT NULL,
    noof_items INTEGER NOT NULL,
    description VARCHAR(255)
)