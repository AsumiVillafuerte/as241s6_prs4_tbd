CREATE TABLE IF NOT EXISTS consultations (
    id VARCHAR(36) PRIMARY KEY,
    ticket VARCHAR(50) NOT NULL UNIQUE,
    specialty_id VARCHAR(36) NOT NULL,
    medic VARCHAR(36) NOT NULL,
    medic_name VARCHAR(200),
    patient VARCHAR(36) NOT NULL,
    patient_name VARCHAR(200),
    dni VARCHAR(20),
    register_by VARCHAR(36) NOT NULL,
    register_by_name VARCHAR(200),
    total DECIMAL(10, 2) NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    tarjeta VARCHAR(50),
    status VARCHAR(20) DEFAULT 'CONSIGNADO',
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    version BIGINT DEFAULT 0
);

CREATE TABLE IF NOT EXISTS consultation_prices (
    id VARCHAR(36) PRIMARY KEY,
    specialty_id VARCHAR(36) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    status VARCHAR(20) DEFAULT 'ACTIVE',
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    version BIGINT DEFAULT 0
);

CREATE TABLE IF NOT EXISTS ticket_sequences (
    date VARCHAR(20) PRIMARY KEY,
    sequence BIGINT NOT NULL,
    version BIGINT DEFAULT 0
);

ALTER TABLE consultations DROP COLUMN IF EXISTS diagnosis;
ALTER TABLE consultations DROP COLUMN IF EXISTS treatment;
ALTER TABLE consultations DROP COLUMN IF EXISTS consultation_price_id;
ALTER TABLE consultations DROP COLUMN IF EXISTS total_amount;

ALTER TABLE consultation_prices DROP COLUMN IF EXISTS active;
