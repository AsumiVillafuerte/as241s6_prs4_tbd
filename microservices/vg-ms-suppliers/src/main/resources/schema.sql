-- Commented out: Category module is temporarily disabled
-- CREATE TABLE IF NOT EXISTS supplier_category (
--     id          SERIAL          PRIMARY KEY,
--     name        VARCHAR(50)     NOT NULL UNIQUE,
--     description TEXT,
--     status      BOOLEAN         NOT NULL DEFAULT TRUE
-- );

CREATE TABLE IF NOT EXISTS supplier (
    supplier_id     BIGSERIAL       PRIMARY KEY,
    business_name   VARCHAR(200)    NOT NULL,
    document_type   VARCHAR(4)      NOT NULL
                        CONSTRAINT chk_document_type
                        CHECK (document_type IN ('RUC', 'DNI')),
    document_number VARCHAR(11)     NOT NULL UNIQUE,
    CONSTRAINT chk_document_number CHECK (
        (document_type = 'RUC' AND LENGTH(document_number) = 11) OR
        (document_type = 'DNI' AND LENGTH(document_number) = 8)
    ),
    address         VARCHAR(300)    NOT NULL,
    phone           VARCHAR(15)     NOT NULL,
    email           VARCHAR(100)    NOT NULL UNIQUE,
    image_url       VARCHAR(500),
    -- category_id  INTEGER         NOT NULL REFERENCES supplier_category(id),
    status          BOOLEAN         NOT NULL DEFAULT TRUE,
    created_at      TIMESTAMP       NOT NULL DEFAULT NOW(),
    updated_at      TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_supplier_document_number ON supplier(document_number);
CREATE INDEX IF NOT EXISTS idx_supplier_status          ON supplier(status);
CREATE INDEX IF NOT EXISTS idx_supplier_document_type   ON supplier(document_type);
-- CREATE INDEX IF NOT EXISTS idx_supplier_category     ON supplier(category_id);
