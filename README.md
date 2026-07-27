# PRS - Sistema Integrado de Gestion para Caritas

Sistema de gestion integral para la administracion de ingresos, egresos, inventarios, donaciones, usuarios, especialidades medicas, campanas y demas procesos de la institucion Caritas.

---

## Versionamiento del codigo fuente

El proyecto utiliza **Trunk-Based Development (TBD)** como estrategia de ramificacion para el desarrollo colaborativo de los 16 microservicios que conforman el sistema.

### Estructura de ramas

```
main (rama principal - siempre estable)
 │
 ├── feature/ms-users
 ├── feature/ms-patients
 ├── feature/ms-doctors
 ├── feature/ms-products
 ├── feature/ms-suppliers
 ├── feature/ms-medications
 ├── feature/ms-specialties
 ├── feature/ms-appointments
 ├── feature/ms-consultation
 ├── feature/ms-medical-history
 ├── feature/ms-treatments
 ├── feature/ms-therapies
 ├── feature/ms-medicine-sale
 ├── feature/ms-product-sale
 ├── feature/ms-shopping
 └── feature/ms-generate-analysis
```

### Flujo de trabajo

1. El integrante actualiza su rama `main` local.
2. Crea una rama `feature/ms-nombre` desde `main`.
3. Desarrolla la funcionalidad con commits frecuentes.
4. Sube los cambios y crea un Pull Request.
5. El equipo revisa y aprueba el codigo.
6. Se realiza el merge a `main` y se elimina la rama feature.

### Convencion de commits

| Prefijo | Uso | Ejemplo |
|---|---|---|
| `feat:` | Nueva funcionalidad | `feat: agregar registro de productos` |
| `fix:` | Correccion de errores | `fix: corregir validacion de pacientes` |
| `docs:` | Documentacion | `docs: actualizar README` |
| `refactor:` | Mejoras al codigo | `refactor: optimizar servicio de usuarios` |
| `test:` | Pruebas | `test: agregar pruebas de medicamentos` |
| `chore:` | Mantenimiento | `chore: actualizar dependencias` |

### Integracion continua

Cada Pull Request ejecuta automaticamente un pipeline de **GitHub Actions** que valida:

- Compilacion del microservicio con Maven
- Ejecucion de pruebas unitarias con JUnit
- Construccion de imagen Docker

---

## Documentacion del proyecto

| Documento | Contenido |
|---|---|
| `docs/01-project-presentation.md` | Presentacion del proyecto y alcance |
| `docs/02-architectural-flow.md` | Flujo arquitectonico y microservicios |
| `docs/03-trunk-based-development.md` | Estrategia TBD y flujo de trabajo |
| `docs/04-development-standards.md` | Estandares de desarrollo |
| `docs/05-best-practices.md` | Buenas practicas y beneficios |
| `docs/06-Continuous-Integration.md` | Integracion continua con GitHub Actions |
