# ms-suppliers-service

Microservicio de gestión del catálogo maestro de proveedores para **Cáritas Yauyos-Cañete**.
Desarrollado por el equipo PRS Team 04 — Instituto Valle Grande (AS241).

---

## Stack tecnológico

| Tecnología | Versión |
|---|---|
| Java | 17 |
| Spring Boot | 3.2.5 |
| Spring WebFlux | Reactivo (non-blocking) |
| Spring Data R2DBC | Reactivo |
| Base de datos | PostgreSQL (Neon Cloud) |
| Documentación | SpringDoc OpenAPI 2.5.0 |
| Arquitectura | Hexagonal (Ports & Adapters) + DDD |

---

## Requisitos previos

- Java 17+
- Maven 3.9+
- Docker (opcional)
- Acceso a la BD PostgreSQL en Neon

---

## Estructura del proyecto

```
src/main/java/pe/edu/vallegrande/sigrc/suppliers/
├── domain/
│   ├── models/          → Supplier, SupplierCategory, DocumentType (enum)
│   ├── ports/in/        → 7 interfaces de casos de uso
│   ├── ports/out/       → ISupplierRepository, ICategoryRepository
│   └── exceptions/      → DomainException, NotFoundException, DuplicateDocumentException
├── application/
│   ├── dto/             → Request, Response, Common (ApiResponse, ErrorResponse)
│   ├── mappers/         → SupplierMapper, CategoryMapper
│   └── usecases/        → 7 implementaciones
└── infrastructure/
    ├── adapters/in/rest/         → SupplierController, CategoryController
    ├── adapters/out/persistence/ → Entities, R2dbcRepositories, Adapters
    └── config/                   → BeanConfiguration, GlobalExceptionHandler, OpenApiConfiguration
```

---

## Reglas de negocio

### Tipos de documento
| Tipo | Dígitos | Prefijo válido |
|---|---|---|
| `DNI` | 8 dígitos numéricos | cualquiera |
| `RUC` | 11 dígitos numéricos | `10` (persona natural) o `20` (empresa) |

### Restricciones
- `documentNumber` y `email` son únicos en el sistema
- Un proveedor solo puede pertenecer a una categoría activa
- `status`, `createdAt` y `updatedAt` son gestionados automáticamente por el sistema

---

## Ejecutar local

```bash
mvn spring-boot:run
```

> Asegúrate de que `application.yml` tenga las credenciales correctas de Neon.

---

## Ejecutar con Docker

```bash
# Construir la imagen
docker build -t ms-suppliers-service .

# Ejecutar el contenedor
docker run -p 8082:8082 ms-suppliers-service
```

---

## Swagger UI

Disponible al levantar el servicio en:

```
http://localhost:8082/swagger-ui.html
```

---

## Endpoints — Proveedores `/api/v1/suppliers`

### `POST /api/v1/suppliers` — Crear proveedor

**Request:**
```json
{
  "businessName": "DISTRIBUIDORA FARMASUR EIRL",
  "documentType": "RUC",
  "documentNumber": "20521386551",
  "address": "CAL FIDEL OLIVOS ESCUDERO N°191 - SAN MIGUEL",
  "phone": "989575834",
  "email": "contacto@farmasur.com",
  "imageUrl": null,
  "categoryId": 1
}
```

**Response `201`:**
```json
{
  "success": true,
  "message": "Proveedor creado exitosamente",
  "data": {
    "supplierId": 1,
    "businessName": "DISTRIBUIDORA FARMASUR EIRL",
    "documentType": "RUC",
    "documentNumber": "20521386551",
    "address": "CAL FIDEL OLIVOS ESCUDERO N°191 - SAN MIGUEL",
    "phone": "989575834",
    "email": "contacto@farmasur.com",
    "imageUrl": null,
    "categoryId": 1,
    "status": true,
    "createdAt": "2026-05-11T08:00:00",
    "updatedAt": "2026-05-11T08:00:00"
  }
}
```

**Response `409` — documento o email duplicado:**
```json
{
  "success": false,
  "message": "Ya existe un proveedor con el documento 20521386551",
  "data": null,
  "timestamp": "2026-05-11T08:00:00"
}
```

---

### `GET /api/v1/suppliers` — Listar todos

**Response `200`:** `{ "success": true, "message": "...", "data": [ {...}, {...} ] }`

---

### `GET /api/v1/suppliers/{id}` — Obtener por ID

**Response `200`:** `{ "success": true, "data": { ... } }`

**Response `404`:**
```json
{ "success": false, "message": "Proveedor no encontrado con id 99", "data": null }
```

---

### `PUT /api/v1/suppliers/{id}` — Actualizar proveedor

Todos los campos son opcionales. Solo se actualizan los que se envíen.

**Request (ejemplo — corrección de documento):**
```json
{
  "documentType": "RUC",
  "documentNumber": "20521386551"
}
```

**Request (ejemplo — actualización de contacto):**
```json
{
  "phone": "999888777",
  "email": "nuevo@farmasur.com",
  "address": "Av. Nueva 456 - Lima"
}
```

**Campos disponibles:**

| Campo | Tipo | Validación |
|---|---|---|
| `businessName` | String | — |
| `address` | String | — |
| `phone` | String | — |
| `email` | String | formato email válido |
| `imageUrl` | String | — |
| `categoryId` | Long | — |
| `documentType` | String | `DNI` o `RUC` |
| `documentNumber` | String | 8 dígitos (DNI) o 11 dígitos (RUC) |

**Response `200`:** `{ "success": true, "message": "Proveedor actualizado exitosamente", "data": { ... } }`

---

### `PATCH /api/v1/suppliers/{id}/deactivate` — Desactivar

**Response `204`:** sin cuerpo

---

### `PATCH /api/v1/suppliers/{id}/restore` — Restaurar

**Response `204`:** sin cuerpo

---

### `GET /api/v1/suppliers/status/{status}` — Filtrar por estado

```
GET /api/v1/suppliers/status/true   → activos
GET /api/v1/suppliers/status/false  → inactivos
```

---

### `GET /api/v1/suppliers/category/{categoryId}` — Filtrar por categoría

```
GET /api/v1/suppliers/category/1
```

---

### `GET /api/v1/suppliers/document-type/{type}` — Filtrar por tipo de documento

```
GET /api/v1/suppliers/document-type/RUC
GET /api/v1/suppliers/document-type/DNI
```

---

## Endpoints — Categorías `/api/v1/supplier-categories`

### `GET /api/v1/supplier-categories` — Listar todas

**Response `200`:**
```json
{
  "success": true,
  "message": "Categorías obtenidas exitosamente",
  "data": [
    { "id": 1, "name": "MEDICAMENTOS", "description": "Proveedores de medicamentos y fármacos", "status": true },
    { "id": 2, "name": "PRODUCTOS", "description": "Proveedores de productos generales", "status": true },
    { "id": 3, "name": "AMBOS", "description": "Proveedores que suministran medicamentos y productos", "status": true }
  ]
}
```

---

### `POST /api/v1/supplier-categories` — Crear categoría

**Request:**
```json
{ "name": "NUEVA CATEGORIA", "description": "Descripción de la categoría" }
```

**Response `201`:** `{ "success": true, "message": "Categoría creada exitosamente", "data": { ... } }`

---

### `PATCH /api/v1/supplier-categories/{id}/deactivate` — Desactivar categoría

**Response `204`:** sin cuerpo

**Response `400` — categoría con proveedores activos:**
```json
{
  "success": false,
  "message": "No se puede desactivar la categoría porque tiene proveedores activos asociados",
  "data": null,
  "timestamp": "2026-05-11T08:00:00"
}
```

---

## Códigos de respuesta

| Código | Significado |
|---|---|
| `200` | Operación exitosa |
| `201` | Recurso creado |
| `204` | Sin contenido (deactivate / restore) |
| `400` | Datos inválidos o regla de negocio violada |
| `404` | Recurso no encontrado |
| `409` | Conflicto — documento o email duplicado |
| `500` | Error interno del servidor |
