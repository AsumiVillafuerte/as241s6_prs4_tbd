# Flujo Arquitectónico del Sistema

## Concepto

El flujo arquitectónico sigue un patrón de **microservicios** donde el usuario interactúa a través de un navegador web que consume el **Frontend (Angular 20)**. Las peticiones HTTP/HTTPS son recibidas por el **API Gateway**, el cual se encarga del enrutamiento, autenticación y distribución de las solicitudes hacia los microservicios correspondientes.

Cada microservicio es independiente y se comunica directamente con su propia base de datos, ya sea **PostgreSQL** para datos relacionales o **MongoDB** para datos NoSQL. El API Gateway actúa como punto único de entrada, orquestando la comunicación entre el cliente y los 16 microservicios que conforman el sistema.

## Flujo de la Arquitectura

```
                                ╔═══════════════╗
                                ║    USUARIO    ║
                                ╚═══════╤═══════╝
                                        │
                                        ▼
                                ┌───────────────┐
                                │  Navegador    │
                                │     Web       │
                                └───────┬───────┘
                                        │
                                        ▼
                         ╔═══════════════════════════╗
                         ║  Frontend (Angular 20)    ║
                         ╚═══════════════╤═══════════╝
                                         │
                              HTTP/HTTPS - REST API
                                         │
                                         ▼
                         ╔═══════════════════════════╗
                         ║       API Gateway         ║
                         ╚═══════════════╤═══════════╝
                                         │
          ══════════════════════════════════════════════════
          ║          MICROSERVICIOS DEL SISTEMA           ║
          ══════════════════════════════════════════════════
                                         │
       ┌─────────────┬─────────────┬─────┴─────┬─────────────┬─────────────┐
       │             │             │           │             │             │
       ▼             ▼             ▼           ▼             ▼             ▼
  ┌─────────┐  ┌──────────┐  ┌─────────┐  ┌─────────┐  ┌──────────┐  ┌──────────┐
  │ vg-ms-  │  │  vg-ms-  │  │  vg-ms- │  │  vg-ms- │  │  vg-ms-  │  │  vg-ms-  │
  │  users  │  │ patients │  │ doctors │  │ products│  │ suppliers│  │ therapies│
  └────┬────┘  └────┬─────┘  └────┬────┘  └────┬────┘  └────┬─────┘  └────┬─────┘
       │            │             │            │             │             │
       ▼            ▼             ▼            ▼             ▼             ▼
  ┌─────────┐  ┌──────────┐  ┌─────────┐  ┌─────────┐  ┌──────────┐  ┌──────────┐
  │ vg-ms-  │  │  vg-ms-  │  │  vg-ms- │  │  vg-ms- │  │  vg-ms-  │  │  vg-ms-  │
  │medicat- │  │special-  │  │appoint- │  │product- │  │ shopping │  │treatments│
  │  ions   │  │  ties    │  │ ments   │  │  sale   │  │          │  │          │
  └────┬────┘  └────┬─────┘  └────┬────┘  └────┬────┘  └──────────┘  └──────────┘
       │            │             │            │
       ▼            ▼             ▼            ▼
  ┌──────────┐ ┌──────────┐  ┌─────────┐  ┌──────────┐
  │  vg-ms-  │ │  vg-ms-  │  │  vg-ms- │  │  vg-ms-  │
  │ medicine │ │ medical  │  │consult- │  │ generate-│
  │  -sale   │ │ -history │  │ ation   │  │ analysis │
  └────┬─────┘ └────┬─────┘  └────┬────┘  └────┬─────┘
       │            │             │             │
       └────────────┴─────────────┴─────────────┘
                                │
                  ┌─────────────┴─────────────┐
                  │                           │
                  ▼                           ▼
         ╔═══════════════╗          ╔═══════════════╗
         ║  PostgreSQL   ║          ║    MongoDB    ║
         ║ (Relacional)  ║          ║   (NoSQL)     ║
          ╚═══════════════╝          ╚═══════════════╝
```

---

## Microservicios Maestros

```
+------------------+----------------------------------------------------+-----------+------+-----------------------+
| Microservicio    | Descripcion                                        | DB        | Port | Responsable           |
+------------------+----------------------------------------------------+-----------+------+-----------------------+
| ms-users         | Administracion de usuarios, autenticacion,         | MongoDB   | 8081 | Jesus Sanchez         |
|                  | perfiles, roles y permisos.                        |           |      |                       |
+------------------+----------------------------------------------------+-----------+------+-----------------------+
| ms-suppliers     | Registro y mantenimiento de proveedores de         | PostgreSQL| 8082 | Marilyn Vilcapuma     |
|                  | medicamentos y productos.                          |           |      |                       |
+------------------+----------------------------------------------------+-----------+------+-----------------------+
| ms-patients      | Registro de pacientes con datos personales e       | MongoDB   | 8083 | Marco Infante         |
|                  | informacion medica.                                |           |      |                       |
+------------------+----------------------------------------------------+-----------+------+-----------------------+
| ms-doctors       | Informacion de medicos, datos profesionales y      | PostgreSQL| 8084 | Jesus Huaripaucar     |
|                  | especialidad.                                      |           |      |                       |
+------------------+----------------------------------------------------+-----------+------+-----------------------+
| ms-medications   | Catalogo de medicamentos: nombre, presentacion,    | MongoDB   | 8088 | Luis Rivas /          |
|                  | concentracion y stock.                             |           |      | Lionel Huamani        |
+------------------+----------------------------------------------------+-----------+------+-----------------------+
| ms-products      | Catalogo de productos comercializados por la       | PostgreSQL| 8086 | Asumi Penafiel        |
|                  | institucion.                                       |           |      |                       |
+------------------+----------------------------------------------------+-----------+------+-----------------------+
| ms-specialties   | Especialidades del sistema: salud, educacion y     | MongoDB   | 8087 | Alonso /              |
|                  | terapias.                                          |           |      | Snayder Vicente       |
+------------------+----------------------------------------------------+-----------+------+-----------------------+
```

---

## Microservicios Transaccionales

```
+------------------+----------------------------------------------------+-----------+------+-----------------------+
| Microservicio    | Descripcion                                        | DB        | Port | Responsable           |
+------------------+----------------------------------------------------+-----------+------+-----------------------+
| ms-appointments  | Gestion del proceso de programacion de citas       | MongoDB   | 9090 | Marco Infante         |
|                  | medicas: registrar, modificar, cancelar y          |           |      |                       |
|                  | consultar citas por paciente, medico y             |           |      |                       |
|                  | especialidad.                                      |           |      |                       |
+------------------+----------------------------------------------------+-----------+------+-----------------------+
| ms-medical-hist  | Administracion del historial clinico de cada       | PostgreSQL| 9091 | Jesus Huaripaucar     |
|                  | paciente: diagnosticos, antecedentes,              |           |      |                       |
|                  | tratamientos, observaciones y evolucion clinica.   |           |      |                       |
+------------------+----------------------------------------------------+-----------+------+-----------------------+
| ms-treatments    | Registra los tratamientos asignados a pacientes    | MongoDB   | 9092 | Jesus Sanchez         |
|                  | segun diagnostico y especialidad medica.           |           |      |                       |
|                  | Administra informacion para facturacion.           |           |      |                       |
+------------------+----------------------------------------------------+-----------+------+-----------------------+
| ms-consultation  | Gestion de consultas medicas realizadas a          | PostgreSQL| 9093 | Alonso Cardenas       |
|                  | pacientes, registrando atencion y calculando       |           |      |                       |
|                  | automaticamente el costo segun tipo de consulta.   |           |      |                       |
+------------------+----------------------------------------------------+-----------+------+-----------------------+
| ms-therapies     | Administracion de sesiones de terapia: fecha,      | MongoDB   | 9094 | Snayder Vicente       |
|                  | profesional responsable, tipo de terapia,          |           |      |                       |
|                  | duracion e importe para facturacion.               |           |      |                       |
+------------------+----------------------------------------------------+-----------+------+-----------------------+
| ms-generate-     | Registro y gestion de solicitudes de analisis      | PostgreSQL| 9095 | Luis Rivas            |
| analysis         | clinicos emitidos por medicos, almacenando         |           |      |                       |
|                  | examenes solicitados y seguimiento de resultados.  |           |      |                       |
+------------------+----------------------------------------------------+-----------+------+-----------------------+
| ms-shopping      | Gestion de compra de medicamentos y productos a    | PostgreSQL| 9096 | Marilyn Vilcapuma     |
|                  | proveedores. Registra cabecera y detalle de        |           |      |                       |
|                  | compra, actualizando inventario automaticamente.   |           |      |                       |
+------------------+----------------------------------------------------+-----------+------+-----------------------+
| ms-medicine-sale | Administracion de ventas de medicamentos:          | MongoDB   | 9097 | Lionel Huamani        |
|                  | informacion de venta, detalle, cantidades,         |           |      |                       |
|                  | precios y monto total de cada transaccion.         |           |      |                       |
+------------------+----------------------------------------------------+-----------+------+-----------------------+
| ms-product-sale  | Gestion de venta de productos: cabecera y          | PostgreSQL| 9098 | Asumi Penafiel        |
|                  | detalle de venta, importe total, stock y           |           |      |                       |
|                  | tipo de venta realizado por la institucion.        |           |      |                       |
+------------------+----------------------------------------------------+-----------+------+-----------------------+
```

---

## Tecnologias Utilizadas

```
+=============================================================================+
|                         TECNOLOGIAS DEL SISTEMA                             |
+=============================================================================+

+---------------------+  +----------------------------------------------------+
|                     |  |                                                    |
|  FRONTEND (UI)      |  |  BACKEND (API)                                     |
|                     |  |                                                    |
+---------------------+  +----------------------------------------------------+

+=============================================================================+
|                      TECNOLOGIAS FRONT END                                  |
+=============================================================================+
|                                                                             |
|  +--------------------+    Framework principal y componentes:               |
|  | Angular 20.3.x     |    Core, Common, Forms, Router,                     |
|  |                    |    Animations, Platform Browser                     |
|  +--------------------+                                                     |
|                                                                             |
|  +--------------------+    +--------------------+    +--------------------+ |
|  | TypeScript 5.9.2   |    | RxJS 7.8.x        |    | Zone.js 0.15.0     |  |
|  | Lenguaje principal |    | Programacion      |    | Manejo de          |  |
|  |                    |    | reactiva          |    | ciclos Angular     |  |
|  +--------------------+    +--------------------+    +--------------------+ |
|                                                                             |
|  +--------------------+    +--------------------+    +--------------------+ |
|  | SweetAlert2        |    | ngx-sonner 3.1.0  |    | jsPDF 4.2.1        |  |
|  | 11.26.25           |    | Notificaciones    |    | Generacion de      |  |
|  | Alertas y modales  |    | emergentes        |    | documentos PDF     |  |
|  +--------------------+    +--------------------+    +--------------------+ |
|                                                                             |
|  +--------------------+    +--------------------+                           |
|  | Lucide Angular     |    | RemixIcon 4.9.1   |                            |
|  | 1.0.0              |    |                    |                           |
|  | Iconografia SVG    |    | Iconografia        |                           |
|  +--------------------+    +--------------------+                           |
+=============================================================================+

+=============================================================================+
|                      TECNOLOGIAS BACK END                                   |
+=============================================================================+
|                                                                             |
|  Lenguaje y Framework:                                                      |
|  +--------------------+    +--------------------+    +--------------------+ |
|  | Java 17            |    | Spring Boot 3.2.0 |    | Maven               | |
|  | Lenguaje principal |    | Framework core     |    | Gestion de         | |
|  |                    |    |                    |    | dependencias       | |
|  +--------------------+    +--------------------+    +--------------------+ |
|                                                                             |
|  Programacion Reactiva:                                                     |
|  +--------------------+    +--------------------+    +--------------------+ |
|  | Spring WebFlux     |    | Project Reactor    |    | Spring Data        | |
|  | Endpoints reactivos|    | Mono y Flux        |    | R2DBC              | |
|  |                    |    |                    |    |                    | |
|  +--------------------+    +--------------------+    +--------------------+ |
|                                                                             |
|  Persistencia y Validacion:                                                 |
|  +--------------------+    +--------------------+    +--------------------+ |
|  | PostgreSQL         |    | R2DBC PostgreSQL   |    | Jakarta Bean       | |
|  | Base de datos      |    | Driver reactivo    |    | Validation         | |
|  | relacional         |    |                    |    | Validacion datos   | |
|  +--------------------+    +--------------------+    +--------------------+ |
|                                                                             |
|  Utilidades y Pruebas:                                                      |
|  +--------------------+    +--------------------+                           |
|  | Lombok             |    | JUnit 5 +         |                            |
|  | Reduccion de       |    | Reactor Test       |                           |
|  | codigo repetitivo  |    | Pruebas unitarias  |                           |
|  +--------------------+    +--------------------+                           |
+=============================================================================+
```
