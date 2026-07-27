CREATE TABLE IF NOT EXISTS compras (
    id                    BIGSERIAL PRIMARY KEY,
    numero_comprobante    VARCHAR(50)   NOT NULL UNIQUE,
    proveedor_id          BIGINT        NOT NULL,
    usuario_id            VARCHAR(24)   NOT NULL,
    tipo                  VARCHAR(30)   NOT NULL DEFAULT 'COMPRADO',
    estado                VARCHAR(20)   NOT NULL DEFAULT 'CONSIGNADO',
    precio_compra_total   NUMERIC(12,2) NOT NULL DEFAULT 0,
    precio_venta_total    NUMERIC(12,2) NOT NULL DEFAULT 0,
    fecha_compra          TIMESTAMP     NOT NULL DEFAULT now(),
    created_at            TIMESTAMP     NOT NULL DEFAULT now(),
    updated_at            TIMESTAMP     NOT NULL DEFAULT now(),
    CONSTRAINT chk_estado CHECK (estado IN ('CONSIGNADO', 'REVOCADO'))
);

CREATE TABLE IF NOT EXISTS detalle_compra_medicamento (
    id                      BIGSERIAL PRIMARY KEY,
    compra_id               BIGINT        NOT NULL REFERENCES compras(id) ON DELETE CASCADE,
    medicamento_id          VARCHAR(24)   NOT NULL,
    denominacion_comercial  VARCHAR(200)  NOT NULL,
    denominacion_generica   VARCHAR(200)  NOT NULL,
    laboratorio             VARCHAR(150)  NOT NULL,
    presentacion            VARCHAR(100)  NOT NULL,
    lote                    VARCHAR(50)   NOT NULL,
    vencimiento             DATE          NOT NULL,
    ubicacion               VARCHAR(20),
    precio_compra           NUMERIC(10,2) NOT NULL CHECK (precio_compra >= 0),
    precio_venta            NUMERIC(10,2) NOT NULL CHECK (precio_venta >= 0),
    cantidad                INTEGER       NOT NULL CHECK (cantidad > 0),
    min_stock               INTEGER       NOT NULL DEFAULT 1 CHECK (min_stock > 0),
    total                   NUMERIC(12,2) NOT NULL,
    subtotal_venta          NUMERIC(12,2) GENERATED ALWAYS AS (precio_venta * cantidad) STORED,
    markup_porcentaje       NUMERIC(5,2)  NOT NULL DEFAULT 0 CHECK (markup_porcentaje >= 0),
    lote_inventario_id      VARCHAR(24),
    created_at              TIMESTAMP     NOT NULL DEFAULT now()
);

CREATE INDEX IF NOT EXISTS idx_compras_proveedor   ON compras(proveedor_id);
CREATE INDEX IF NOT EXISTS idx_compras_usuario     ON compras(usuario_id);
CREATE INDEX IF NOT EXISTS idx_compras_estado      ON compras(estado);
CREATE INDEX IF NOT EXISTS idx_compras_fecha       ON compras(fecha_compra DESC);

CREATE INDEX IF NOT EXISTS idx_detalle_compra      ON detalle_compra_medicamento(compra_id);
CREATE INDEX IF NOT EXISTS idx_detalle_medicamento ON detalle_compra_medicamento(medicamento_id);
CREATE INDEX IF NOT EXISTS idx_detalle_vencimiento ON detalle_compra_medicamento(vencimiento ASC);
