package pe.edu.vallegrande.sigrc.product.infrastructure.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    private final DatabaseClient databaseClient;

    public DatabaseInitializer(DatabaseClient databaseClient) {
        this.databaseClient = databaseClient;
    }

    @Override
    public void run(String... args) {
        databaseClient.sql("""
                CREATE TABLE IF NOT EXISTS brands (
                    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                    name VARCHAR(100) NOT NULL UNIQUE,
                    status CHAR(1) DEFAULT 'A'
                        CHECK (status IN ('A', 'I'))
                )
                """)
                .fetch()
                .rowsUpdated()
                .subscribe();

        databaseClient.sql("""
                CREATE TABLE IF NOT EXISTS products (
                    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                    commercial_name VARCHAR(150) NOT NULL,
                    brand_id UUID NOT NULL,
                    purchase_price DECIMAL(10, 2) NOT NULL
                        CHECK (purchase_price >= 0),
                    sale_price DECIMAL(10, 2) NOT NULL
                        CHECK (sale_price >= 0),
                    stock INTEGER DEFAULT 0
                        CHECK (stock >= 0),
                    location VARCHAR(50) NOT NULL,
                    status CHAR(1) DEFAULT 'A'
                        CHECK (status IN ('A', 'I')),
                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    CONSTRAINT fk_products_brand
                        FOREIGN KEY (brand_id)
                        REFERENCES brands(id)
                )
                """)
                .fetch()
                .rowsUpdated()
                .subscribe();
    }
}
