CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE users (
                       id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                       username VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       email VARCHAR(255)
);

CREATE TABLE categories (
                            id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                            name VARCHAR(255) NOT NULL UNIQUE,
                            description TEXT
);

CREATE TABLE products (
                          id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                          name VARCHAR(255) NOT NULL,
                          description TEXT,
                          price NUMERIC(10,2) NOT NULL,
                          stock_quantity INTEGER,
                          sku VARCHAR(255) UNIQUE,
                          created_by_user_id UUID,
                          CONSTRAINT fk_products_created_by_user FOREIGN KEY (created_by_user_id)
                              REFERENCES users(id) ON DELETE SET NULL
);

CREATE TABLE product_categories (
                                    product_id UUID NOT NULL,
                                    category_id UUID NOT NULL,
                                    PRIMARY KEY (product_id, category_id),
                                    CONSTRAINT fk_product FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
                                    CONSTRAINT fk_category FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE CASCADE
);
