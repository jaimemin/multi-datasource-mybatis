DROP TABLE IF EXISTS TBL_PRODUCT;

CREATE TABLE TBL_PRODUCT (
    id BIGINT auto_increment NOT NULL,
    product_name varchar(100) NOT NULL,
    quantity INT NOT NULL,
    created_by varchar(100) NOT NULL,
    CONSTRAINT tbl_product_PK PRIMARY KEY (id)
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_general_ci;