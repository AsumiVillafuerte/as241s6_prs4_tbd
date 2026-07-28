# VG-MS-TREATMENTS - Microservicio de Gestión de Tratamientos

Microservicio REST para la gestión de tratamientos médicos implementado con **Arquitectura Hexagonal (Clean Architecture)**, desarrollado con **Spring WebFlux** y **MongoDB Atlas**. Se integra con los microservicios de pacientes, médicos y especialidades del sistema SIGRC de Cáritas Cañete.

---

## 🏗️ Arquitectura Hexagonal

### Estructura del Proyecto

```
vg-ms-treatments/
│
├── 🎯 domain/                              # LÓGICA DE NEGOCIO
│   ├── models/
│   │   ├── Tratamiento.java                # Entidad de dominio principal
│   │   └── TratamientoItem.java            # Entidad embebida (ítems del tratamiento)
│   │
│   ├── ports/
│   │   ├── in/                             # Casos de uso (lo que puede hacer)
│   │   │   ├── ICreateTratamientoUseCase.java
│   │   │   ├── IGetTratamientoUseCase.java
│   │   │   ├── IUpdateTratamientoUseCase.java
│   │   │   ├── IChangeEstadoUseCase.java
│   │   │   └── IChangeTipoUseCase.java
│   │   │
│   │   └── out/                            # Repositorios (cómo guardar)
│   │       └── ITratamientoRepository.java
│   │
│   └── exceptions/                         # Errores de negocio
│       ├── DomainException.java
│       └── NotFoundException.java
│
├── 🔄 application/                         # ORQUESTACIÓN
│   ├── usecases/
│   │   ├── CreateTratamientoUseCaseImpl.java
│   │   ├── GetTratamientoUseCaseImpl.java
│   │   ├── UpdateTratamientoUseCaseImpl.java
│   │   ├── ChangeEstadoUseCaseImpl.java
│   │   └── ChangeTipoUseCaseImpl.java
│   │
│   ├── dto/
│   │   ├── request/
│   │   │   ├── CreateTratamientoRequest.java
│   │   │   ├── UpdateTratamientoRequest.java
│   │   │   ├── TratamientoItemRequest.java
│   │   │   ├── ChangeEstadoRequest.java
│   │   │   └── ChangeTipoRequest.java
│   │   │
│   │   ├── response/
│   │   │   ├── TratamientoResponse.java
│   │   │   └── TratamientoItemResponse.java
│   │   │
│   │   └── common/
│   │       ├── ApiResponse.java
│   │       └── ErrorResponse.java
│   │
│   └── mappers/
│       └── TratamientoMapper.java
│
└── ⚙️ infrastructure/                      # TECNOLOGÍA
    ├── adapters/
    │   ├── in/
    │   │   └── rest/
    │   │       └── TratamientoController.java
    │   │
    │   └── out/
    │       ├── persistence/
    │       │   ├── TratamientoDocument.java
    │       │   ├── TratamientoItemDocument.java
    │       │   ├── TratamientoMongoRepository.java
    │       │   └── TratamientoRepositoryAdapter.java
    │       │
    │       └── webclient/
    │           ├── MaestrosWebClient.java
    │           └── dto/
    │               ├── ClientApiResponse.java
    │               ├── PatientClientResponse.java
    │               ├── DoctorClientResponse.java
    │               ├── SpecialtyClientResponse.java
    │               └── TreatmentMaestroClientResponse.java
    │
    └── config/
        ├── BeanConfiguration.java
        ├── CorsConfig.java
        ├── GlobalExceptionHandler.java
        ├── OpenApiConfiguration.java
        └── WebClientConfig.java
```

---

## 📐 Capas de la Arquitectura

### 🎯 Domain (Dominio)
- **Responsabilidad:** Lógica de negocio pura
- **Contenido:** Entidades, interfaces de casos de uso, excepciones
- **Dependencias:** Ninguna (independiente de frameworks)

### 🔄 Application (Aplicación)
- **Responsabilidad:** Orquestación de casos de uso
- **Contenido:** Implementación de casos de uso, DTOs, mappers
- **Dependencias:** Solo del dominio

### ⚙️ Infrastructure (Infraestructura)
- **Responsabilidad:** Detalles técnicos y frameworks
- **Contenido:** Controladores REST, repositorios MongoDB, WebClient, configuración
- **Dependencias:** Del dominio y aplicación

---

## 🎯 Principios de la Arquitectura

- **Independencia de Frameworks:** El dominio no depende de Spring, MongoDB ni ningún framework
- **Inversión de Dependencias:** Las dependencias apuntan hacia el dominio
- **Separación de Responsabilidades:** Cada capa tiene una responsabilidad clara
- **Testabilidad:** Los casos de uso pueden testearse sin base de datos
- **Programación Reactiva:** Uso de `Mono` y `Flux` con Spring WebFlux
- **Snapshot de datos:** Los datos de paciente, médico y especialidad se guardan como snapshot al momento del registro, preservando el historial ante cambios futuros en los maestros

---

## 🔄 Flujo de una Petición

```
Cliente HTTP
    ↓
TratamientoController (Infrastructure/Adapters/In/Rest)
    ↓
CreateTratamientoUseCase (Application/UseCases)
    ↓
MaestrosWebClient → MS Pacientes / MS Médicos / MS Especialidades (validación)
    ↓
ITratamientoRepository (Domain/Ports/Out)
    ↓
TratamientoRepositoryAdapter (Infrastructure/Adapters/Out/Persistence)
    ↓
TratamientoMongoRepository (Infrastructure/Adapters/Out/Persistence)
    ↓
MongoDB Atlas
```

---

## 🔗 Integración con Microservicios Externos

Este microservicio consume los siguientes MS del ecosistema SIGRC vía **WebClient** a través del Gateway:

| Microservicio | Endpoint consumido | Propósito |
|---|---|---|
| MS Pacientes | `GET /api/v1/patients/{id}` | Validar y obtener datos del paciente |
| MS Médicos | `GET /api/v1/doctors/{id}` | Validar y obtener datos del médico |
| MS Especialidades | `GET /api/v1/specialties/{id}` | Validar y obtener datos de especialidad |
| MS Especialidades | `GET /api/v1/specialties/treatments/{id}` | Validar tratamiento maestro |

**Base URL del Gateway:**
```
https://lab.vallegrande.edu.pe/sigrc/gateway
```

---

## 📡 Endpoints API

### Base URL
```
http://localhost:9092/api/v1/tratamientos
```

### CRUD Principal

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `POST` | `/api/v1/tratamientos` | Crear un nuevo tratamiento |
| `GET` | `/api/v1/tratamientos` | Listar todos los tratamientos |
| `GET` | `/api/v1/tratamientos/{id}` | Obtener tratamiento por ID |
| `PUT` | `/api/v1/tratamientos/{id}` | Actualizar tratamiento |
| `PATCH` | `/api/v1/tratamientos/{id}/estado` | Cambiar estado del tratamiento |
| `PATCH` | `/api/v1/tratamientos/{id}/tipo` | Cambiar tipo del tratamiento |

---

## 📝 Ejemplos de Request/Response

### Crear Tratamiento
**Request:**
```http
POST /api/v1/tratamientos
Content-Type: application/json

{
  "especialidadId": "6a02510d792b20a92016683c",
  "especialidadNombre": "TERAPIA",
  "medicoId": "64ec0aad-bdd2-4be7-8b37-ec7eb7120576",
  "medicoNombre": "cesar cardenas",
  "pacienteId": "f3ab3a0e-0b72-43e5-a50a-689956e0afc8",
  "pacienteNombre": "María Elena Torres Vega",
  "pacienteDni": "45678912",
  "registradoPor": "YASURI",
  "tipo": "venta",
  "tarjeta": "izipay",
  "items": [
    {
      "tratamientoMaestroId": "6a23d19b857c6591addacb4c",
      "nombre": "SELLANTE",
      "cantidad": 1,
      "precioUnitario": 50.00
    }
  ]
}
```

**Response:**
```json
{
  "status": 201,
  "message": "Tratamiento creado correctamente",
  "data": {
    "id": "6a2a75c75999ffcabe7883a4",
    "ticket": "TRAT-2026-06-11-61338",
    "fecha": "2026-06-11T03:45:59.841",
    "especialidadId": "6a02510d792b20a92016683c",
    "especialidadNombre": "TERAPIA",
    "medicoId": "64ec0aad-bdd2-4be7-8b37-ec7eb7120576",
    "medicoNombre": "cesar cardenas",
    "pacienteId": "f3ab3a0e-0b72-43e5-a50a-689956e0afc8",
    "pacienteNombre": "María Elena Torres Vega",
    "pacienteDni": "45678912",
    "registradoPor": "YASURI",
    "tipo": "venta",
    "tarjeta": "izipay",
    "estado": "consignado",
    "total": 50.00,
    "items": [
      {
        "tratamientoMaestroId": "6a23d19b857c6591addacb4c",
        "nombre": "SELLANTE",
        "cantidad": 1,
        "precioUnitario": 50.00,
        "subtotal": 50.00
      }
    ],
    "creadoEn": "2026-06-11T03:45:59.841",
    "actualizadoEn": "2026-06-11T03:45:59.841"
  }
}
```

### Listar Todos los Tratamientos
**Request:**
```http
GET /api/v1/tratamientos
```

**Response:**
```json
{
  "status": 200,
  "message": "Listado de tratamientos",
  "data": [
    {
      "id": "6a2a75c75999ffcabe7883a4",
      "ticket": "TRAT-2026-06-11-61338",
      "especialidadNombre": "TERAPIA",
      "medicoNombre": "cesar cardenas",
      "pacienteNombre": "María Elena Torres Vega",
      "pacienteDni": "45678912",
      "registradoPor": "YASURI",
      "tipo": "venta",
      "tarjeta": "izipay",
      "estado": "consignado",
      "total": 50.00
    }
  ]
}
```

### Obtener Tratamiento por ID
**Request:**
```http
GET /api/v1/tratamientos/6a2a75c75999ffcabe7883a4
```

**Response:**
```json
{
  "status": 200,
  "message": "Tratamiento encontrado",
  "data": {
    "id": "6a2a75c75999ffcabe7883a4",
    "ticket": "TRAT-2026-06-11-61338",
    "estado": "consignado",
    "total": 50.00
  }
}
```

### Actualizar Tratamiento
**Request:**
```http
PUT /api/v1/tratamientos/6a2a75c75999ffcabe7883a4
Content-Type: application/json

{
  "medicoId": "64ec0aad-bdd2-4be7-8b37-ec7eb7120576",
  "medicoNombre": "cesar cardenas",
  "tarjeta": "visa",
  "items": [
    {
      "tratamientoMaestroId": "6a23d19b857c6591addacb4c",
      "nombre": "SELLANTE",
      "cantidad": 2,
      "precioUnitario": 50.00
    }
  ]
}
```

**Response:**
```json
{
  "status": 200,
  "message": "Tratamiento actualizado correctamente",
  "data": { ... }
}
```

### Cambiar Estado
**Request:**
```http
PATCH /api/v1/tratamientos/6a2a75c75999ffcabe7883a4/estado
Content-Type: application/json

{
  "estado": "revocado"
}
```

**Response:**
```json
{
  "status": 200,
  "message": "Estado actualizado correctamente",
  "data": { ... }
}
```

### Cambiar Tipo
**Request:**
```http
PATCH /api/v1/tratamientos/6a2a75c75999ffcabe7883a4/tipo
Content-Type: application/json

{
  "tipo": "donado"
}
```

**Response:**
```json
{
  "status": 200,
  "message": "Tipo actualizado correctamente",
  "data": { ... }
}
```

---

## ⚠️ Manejo de Errores

### Tratamiento no encontrado (404)
```json
{
  "status": 404,
  "message": "Tratamiento no encontrado con id: 6a2a75c75999ffcabe7883a4",
  "timestamp": "2026-06-11T03:45:59.841"
}
```

### Regla de negocio violada (400)
```json
{
  "status": 400,
  "message": "No se puede modificar un tratamiento revocado",
  "timestamp": "2026-06-11T03:45:59.841"
}
```

### Error de validación (400)
```json
{
  "status": 400,
  "message": "especialidadId: La especialidad es requerida, items: Debe incluir al menos un ítem",
  "timestamp": "2026-06-11T03:45:59.841"
}
```

### Error interno (500)
```json
{
  "status": 500,
  "message": "Error interno del servidor: ...",
  "timestamp": "2026-06-11T03:45:59.841"
}
```

---

## 📋 Reglas de Negocio

### Estados del Tratamiento

```
[CREAR]
   ↓
CONSIGNADO ──[revocar]──→ REVOCADO (irreversible)
```

| Estado | Descripción |
|--------|-------------|
| `consignado` | Estado inicial, tratamiento válido y activo |
| `revocado` | Tratamiento anulado, estado final irreversible |

### Tipos del Tratamiento

```
VENTA ←──[cambiar tipo]──→ DONADO
(reversible mientras no esté revocado)
```

| Tipo | Descripción |
|------|-------------|
| `venta` | El paciente paga el tratamiento |
| `donado` | El tratamiento es una donación sin costo |

### Reglas de cambio de estado/tipo

| Acción | Condición |
|--------|-----------|
| Crear tratamiento | Estado inicial siempre `consignado`, tipo siempre `venta` |
| Editar tratamiento | Solo si estado es `consignado` |
| Cambiar a `revocado` | Disponible desde cualquier estado excepto `revocado` |
| Cambiar tipo | Solo si estado NO es `revocado` |

### Campos editables

| Campo | Editable | Razón |
|-------|----------|-------|
| `medicoId/Nombre` | ✅ | El médico puede cambiar por disponibilidad |
| `registradoPor` | ✅ | Corrección de quien registró |
| `tarjeta` | ✅ | Puede haberse equivocado al registrar |
| `items` | ✅ | Agregar/quitar tratamientos del detalle |
| `ticket` | ❌ | Identificador único generado automáticamente |
| `fecha` | ❌ | Fecha real del registro |
| `paciente` | ❌ | El paciente no cambia en un tratamiento |
| `especialidad` | ❌ | La especialidad define el contexto |
| `estado` | ❌ | Se maneja con su propio PATCH |
| `total` | ❌ | Se recalcula automáticamente desde los ítems |

---

## 🗄️ Modelo de Datos MongoDB

### Colección: `tratamientos`

```json
{
  "_id": "6a2a75c75999ffcabe7883a4",
  "ticket": "TRAT-2026-06-11-61338",
  "fecha": "2026-06-11T03:45:59.841",
  "especialidadId": "6a02510d792b20a92016683c",
  "especialidadNombre": "TERAPIA",
  "medicoId": "64ec0aad-bdd2-4be7-8b37-ec7eb7120576",
  "medicoNombre": "cesar cardenas",
  "pacienteId": "f3ab3a0e-0b72-43e5-a50a-689956e0afc8",
  "pacienteNombre": "María Elena Torres Vega",
  "pacienteDni": "45678912",
  "registradoPor": "YASURI",
  "tipo": "venta",
  "tarjeta": "izipay",
  "estado": "consignado",
  "total": 50.00,
  "items": [
    {
      "tratamientoMaestroId": "6a23d19b857c6591addacb4c",
      "nombre": "SELLANTE",
      "cantidad": 1,
      "precioUnitario": 50.00,
      "subtotal": 50.00
    }
  ],
  "creadoEn": "2026-06-11T03:45:59.841",
  "actualizadoEn": "2026-06-11T03:45:59.841"
}
```

---

## 🛠️ Tecnologías Utilizadas

| Tecnología | Versión | Uso |
|------------|---------|-----|
| Java | 17 | Lenguaje principal |
| Spring Boot | 4.0.6 | Framework base |
| Spring WebFlux | 7.x | Programación reactiva |
| Spring Data MongoDB Reactive | 4.x | Acceso reactivo a MongoDB |
| MongoDB Atlas | 7.0 | Base de datos en la nube |
| Lombok | Latest | Reducción de boilerplate |
| SpringDoc OpenAPI | 2.8.8 | Documentación Swagger |
| Maven | 3.9 | Gestión de dependencias |

---

## 🚀 Ejecución del Proyecto

### Prerrequisitos
- Java 17+
- Maven 3.x
- Docker Desktop (opcional)
- Cuenta en MongoDB Atlas

### Correr localmente

```bash
# Clonar el repositorio
git clone https://github.com/vallegrande/vg-ms-treatments.git
cd vg-ms-treatments

# Compilar
./mvnw clean compile

# Ejecutar
./mvnw spring-boot:run
```

### Variables de entorno requeridas

```yaml
spring:
  data:
    mongodb:
      uri: mongodb+srv://<user>:<password>@cluster0.xxxxx.mongodb.net/Microservice_Treatments
```

### Correr con Docker

```bash
# Construir imagen
docker build -t vg-ms-treatments .

# Ejecutar contenedor
docker run -p 9092:9092 vg-ms-treatments
```

---

## 🐳 Dockerfile

El proyecto usa un Dockerfile multi-stage optimizado para reducir el tamaño de la imagen a menos de 200MB:

- **Etapa 1 (build):** `maven:3.9-eclipse-temurin-17-alpine` — compilación
- **Etapa 2 (runtime):** `eclipse-temurin:17-jre-alpine` — ejecución mínima
- **JVM flags:** G1GC, UseStringDeduplication, MaxRAMPercentage=40%

---

## 📖 Documentación Swagger

| Recurso | URL |
|---------|-----|
| Swagger UI | http://localhost:9092/swagger-ui.html |
| OpenAPI JSON | http://localhost:9092/api-docs |

---

## 👥 Equipo

**Institución:** Vallegrande - SIGRC
**Versión:** 0.0.1-SNAPSHOT
**Equipo:** Cáritas Cañete
```