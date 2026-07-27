# Buenas Practicas de Desarrollo

Con el proposito de mantener un desarrollo organizado y garantizar la calidad del codigo en el PRS de Caritas, el equipo seguira las siguientes buenas practicas durante el desarrollo de los microservicios.

---

## Principios de trabajo

- No trabajar directamente sobre la rama main
- Mantener ramas pequenas y de corta duracion
- Realizar commits descriptivos
- Revisar el codigo mediante Pull Request
- No subir credenciales ni informacion sensible
- Mantener sincronizada la rama main
- Respetar la estructura definida del proyecto

---

## Buenas practicas aplicadas

```
+---------------------+---------------------------------------------------------------+
| Practica            | Descripcion                                                   |
+---------------------+---------------------------------------------------------------+
| Trabajar en ramas   | Cada funcionalidad se desarrolla en una rama independiente    |
| feature/*           | creada desde main.                                            |
+---------------------+---------------------------------------------------------------+
| Mantener ramas      | Cada rama debe contener una unica funcionalidad o             |
| pequanas            | correccion para facilitar la integracion.                     |
+---------------------+---------------------------------------------------------------+
| Commits             | Los cambios deben seguir la convencion Conventional           |
| descriptivos        | Commits para mejorar la trazabilidad.                        |
+---------------------+---------------------------------------------------------------+
| Revisar el codigo   | Todo cambio debe aprobarse mediante un Pull Request           |
|                     | antes de integrarse a main.                                  |
+---------------------+---------------------------------------------------------------+
| Proteger            | No almacenar credenciales, contrasenas o archivos de         |
| informacion         | configuracion en el repositorio.                             |
| sensible            |                                                               |
+---------------------+---------------------------------------------------------------+
| Actualizar main     | Antes de iniciar una nueva tarea, sincronizar la rama        |
|                     | principal con el repositorio remoto.                         |
+---------------------+---------------------------------------------------------------+
| Respetar la         | Todos los microservicios deben mantener la estructura y      |
| arquitectura        | estandares definidos por el proyecto.                        |
+---------------------+---------------------------------------------------------------+
```

---

# Beneficios Esperados

La implementacion de **Trunk-Based Development (TBD)** permitira mejorar la organizacion y el desarrollo del PRS de Caritas, favoreciendo el trabajo colaborativo y la integracion continua de los microservicios.

## Beneficios esperados

```
                       Trunk-Based Development
                                  |
       +--------------+--------------+--------------+
       |              |              |              |
       v              v              v              v
  Menos         Integracion      Mejor        Mayor
  conflictos      continua     organizacion trazabilidad
       |              |              |              |
       +--------------+--------------+--------------+
                                  v
                     Pull Requests pequenos
                                  |
                                  v
                   Desarrollo mas escalable
```

## Beneficios para el proyecto

```
+-------------------------+---------------------------------------------------------------+
| Beneficio               | Impacto en el PRS de Caritas                                  |
+-------------------------+---------------------------------------------------------------+
| Menos conflictos        | Reduce problemas al integrar cambios de varios                |
|                         | microservicios.                                               |
+-------------------------+---------------------------------------------------------------+
| Integracion continua    | Permite validar y unir cambios con mayor frecuencia.          |
+-------------------------+---------------------------------------------------------------+
| Organizacion del codigo | Mantiene una estructura uniforme entre todos los              |
|                         | microservicios.                                               |
+-------------------------+---------------------------------------------------------------+
| Trazabilidad            | Facilita identificar quien realizo cada cambio y cuando       |
|                         | se hizo.                                                      |
+-------------------------+---------------------------------------------------------------+
| Pull Requests pequenos  | Simplifica la revision del codigo y reduce errores.           |
+-------------------------+---------------------------------------------------------------+
| Escalabilidad           | Facilita incorporar nuevos modulos y desarrolladores          |
|                         | al proyecto.                                                  |
+-------------------------+---------------------------------------------------------------+
```

---

# Conclusiones

La propuesta basada en **Trunk-Based Development (TBD)** proporciona una metodologia adecuada para el desarrollo del PRS de Caritas, permitiendo que el equipo trabaje de manera colaborativa sobre una unica rama principal mediante ramas temporales para cada funcionalidad.

## Resultado de la propuesta

```
               Trunk-Based Development
                          |
                          v
               Desarrollo organizado del equipo
                          |
                          v
               Integracion continua de cambios
                          |
                          v
                 Menor cantidad de conflictos
                          |
                          v
                Mayor calidad y estabilidad
                          |
                          v
           Sistema PRS de Caritas mas mantenible
```

## Conclusesion

La aplicacion de esta estrategia, junto con el uso de convenciones de ramas, Conventional Commits, Pull Requests, GitHub Actions y buenas practicas de desarrollo, permitira mantener un proceso de trabajo ordenado, mejorar la calidad del software y facilitar la evolucion de los microservicios del proyecto. Esto contribuira a construir una solucion confiable y escalable para la gestion de los procesos de Caritas.
