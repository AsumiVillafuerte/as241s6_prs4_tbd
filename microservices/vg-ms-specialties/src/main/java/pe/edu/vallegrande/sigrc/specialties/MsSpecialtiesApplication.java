package pe.edu.vallegrande.sigrc.specialties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableReactiveMongoAuditing;

@SpringBootApplication
@EnableReactiveMongoAuditing
public class MsSpecialtiesApplication {
    public static void main(String[] args) {
        SpringApplication.run(MsSpecialtiesApplication.class, args);
    }
}