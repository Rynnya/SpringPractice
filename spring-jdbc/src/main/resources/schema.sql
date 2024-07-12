CREATE TABLE IF NOT EXISTS products
(
    id     INTEGER      AUTO_INCREMENT,
    name   VARCHAR(255) NOT NULL,
    price  NUMERIC      NOT NULL,
    amount INTEGER      NOT NULL,
    CONSTRAINT products_id_pk PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS carts
(
    id        INTEGER       auto_increment,
    promocode VARCHAR(255),
    CONSTRAINT carts_id_pk PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS clients
(
    id       INTEGER      auto_increment,
    name     VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email    VARCHAR(255),
    cart_id  INTEGER,
    CONSTRAINT clients_id_pk PRIMARY KEY (id),
    CONSTRAINT client_cart_id_fk FOREIGN KEY (cart_id) REFERENCES carts (id)
);

CREATE TABLE IF NOT EXISTS products_carts
(
    id          INTEGER auto_increment,
    id_product  INTEGER NOT NULL,
    amount      INTEGER NOT NULL,
    id_cart     INTEGER NOT NULL,
    CONSTRAINT products_carts_id_pk PRIMARY KEY (id),
    CONSTRAINT product_client_products_id_fk FOREIGN KEY (id_product) REFERENCES products (id),
    CONSTRAINT product_client_cart_id_fk FOREIGN KEY (id_cart) REFERENCES carts (id)
);