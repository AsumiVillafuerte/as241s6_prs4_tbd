# Arquitectura Hexagonal - Template

Template para microservicios con arquitectura hexagonal.

## Estructura Completa

```
nombremicro/
│
├── domain/                                    #  LÓGICA DE NEGOCIO
│   ├── models/
│   │   └── Entity.java                       # Tu modelo de dominio
│   │
│   ├── ports/
│   │   ├── in/                               # Casos de uso (lo que puede hacer)
│   │   │   ├── ICreateEntityUseCase.java     # Crear
│   │   │   ├── IGetEntityUseCase.java        # Consultar
│   │   │   ├── IUpdateEntityUseCase.java     # Actualizar
│   │   │   └── IDeleteEntityUseCase.java     # Eliminar
│   │   │
│   │   └── out/                              # Repositorios (cómo guardar)
│   │       └── IEntityRepository.java        # Interface para BD
│   │
│   └── exceptions/                           # Errores de negocio
│       ├── DomainException.java              # Error base
│       └── NotFoundException.java            # No encontrado
│
├── application/                               # ORQUESTACIÓN
│   ├── usecases/                             # Implementa la lógica
│   │   ├── CreateEntityUseCaseImpl.java      # Cómo crear
│   │   ├── GetEntityUseCaseImpl.java         # Cómo consultar
│   │   ├── UpdateEntityUseCaseImpl.java      # Cómo actualizar
│   │   └── DeleteEntityUseCaseImpl.java      # Cómo eliminar
│   │
│   ├── dto/                                  # Contratos de entrada/salida
│   │   ├── request/
│   │   │   ├── CreateEntityRequest.java      # Datos para crear
│   │   │   └── UpdateEntityRequest.java      # Datos para actualizar
│   │   │
│   │   ├── response/
│   │   │   └── EntityResponse.java           # Datos de respuesta
│   │   │
│   │   └── common/
│   │       ├── ApiResponse.java              # Respuesta estándar
│   │       └── ErrorResponse.java            # Respuesta de error
│   │
│   └── mappers/
│       └── EntityMapper.java                 # Convierte DTO ↔ Entity
│
└── infrastructure/                            # ⚙️ TECNOLOGÍA
    ├── adapters/
    │   ├── in/
    │   │   └── rest/
    │   │       └── EntityController.java     # Endpoints REST
    │   │
    │   └── out/
    │       └── persistence/
    │           ├── EntityDocument.java       # Modelo PostgreSQL
    │           ├── EntityR2dbcRepository.java # Interface R2DBC
    │           └── EntityRepositoryAdapter.java # Implementa IEntityRepository
    │
    └── config/
        └── GlobalExceptionHandler.java       # Maneja errores HTTP
```

## Capas de la Arquitectura

### Domain (Dominio)
**Sin dependencias de frameworks**
- `Entity.java` → Tu modelo de negocio
- `ports/in/` → Define QUÉ puede hacer el sistema
- `ports/out/` → Define QUÉ necesita el sistema
- `exceptions/` → Errores del negocio

### Application (Aplicación)
**Orquesta el flujo**
- `usecases/` → Implementa la lógica de negocio
- `dto/` → Objetos para recibir/enviar datos
- `mappers/` → Transforma Request → Entity → Response

### Infrastructure (Infraestructura)
**Implementa la tecnología**
- `adapters/in/rest/` → Recibe peticiones HTTP
- `adapters/out/persistence/` → Guarda en PostgreSQL
- `config/` → Configuración de Spring

## Flujo de una Petición

```
Cliente
  ↓
EntityController (REST)
  ↓
EntityMapper (Request → Entity)
  ↓
CreateEntityUseCaseImpl (Lógica de negocio)
  ↓
EntityRepositoryAdapter (Implementa IEntityRepository)
  ↓
EntityR2dbcRepository (R2DBC)
  ↓
PostgreSQL
```

## Principios

**Dependencias hacia adentro**
- Infrastructure → Application → Domain
- Domain NO conoce Application ni Infrastructure
- Application NO conoce Infrastructure

**Inversión de dependencias**
- Domain define interfaces (ports)
- Infrastructure implementa esas interfaces (adapters)

**Separación de responsabilidades**
- Domain: Lógica de negocio
- Application: Orquestación
- Infrastructure: Tecnología
