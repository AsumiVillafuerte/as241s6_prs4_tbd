# ms-medications

Microservicio de gestión de medicamentos del sistema PRS - Cáritas Cañete Yauyos.

## Tecnologías

- Java 17
- Spring Boot 3.3
- Spring WebFlux (reactivo)
- Spring Data MongoDB Reactive
- MongoDB Atlas
- Lombok
- Swagger / OpenAPI 3
- Docker

## Arquitectura

Arquitectura hexagonal (puertos y adaptadores):

```
domain/          → modelo de negocio, puertos (interfaces)
application/     → casos de uso, DTOs, mappers
infrastructure/  → controller REST, persistencia MongoDB, manejo de errores
```

## Requisitos

- Java 17+
- Maven 3.9+
- Docker (opcional)
- Cuenta en MongoDB Atlas

## Configuración

Edita `src/main/resources/application.yaml` y coloca tu URI de MongoDB:

```yaml
spring:
  data:
    mongodb:
      uri: mongodb+srv://luisrivas:Alexander1903@cluster0.gr4avuk.mongodb.net/ms-medications?appName=Cluster0
server:
  port: 8085
```

## Ejecución local

```bash
./mvnw spring-boot:run
```

## Ejecución con Docker

Construir imagen:
```bash
docker build -t luisrivas3/ms-medications:1.0.0 .
```

Correr contenedor:
```bash
docker run -p 8085:8085 -e SPRING_DATA_MONGODB_URI=mongodb+srv://luisrivas:Alexander1903@cluster0.gr4avuk.mongodb.net/ms-medications?appName=Cluster0" luisrivas3/ms-medications:1.0.0
```

## Documentación API

Con el proyecto corriendo, accede a:

- Swagger UI: http://localhost:8085/swagger-ui.html
- OpenAPI JSON: http://localhost:8085/api-docs

## Endpoints

| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | `/api/medications` | Listar medicamentos activos |
| GET | `/api/medications/{id}` | Obtener por ID |
| POST | `/api/medications` | Crear medicamento |
| PUT | `/api/medications/{id}` | Actualizar medicamento |
| DELETE | `/api/medications/{id}` | Desactivar (soft delete) |
| PATCH | `/api/medications/{id}/restore` | Restaurar medicamento |

## Ejemplo de body (POST)

```json
{
  "code": "MED-001",
  "name": "Paracetamol",
  "commercialName": "Tylenol",
  "category": "Analgésico",
  "form": "tableta",
  "concentration": "500mg",
  "unit": "mg",
  "stock": 100,
  "minStock": 10,
  "description": "Alivia el dolor y reduce la fiebre",
  "sideEffects": "Náuseas en dosis altas",
  "requiresPrescription": false,
  "supplierId": "proveedor-001"
}
```

## Integrantes

- Luis Rivas
- Lionel Huamani
