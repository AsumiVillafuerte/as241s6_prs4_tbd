# Estandares de Desarrollo

Con el objetivo de mantener un desarrollo organizado y colaborativo en el proyecto PRS de Caritas, el equipo seguira un conjunto de estandares para la gestion del codigo fuente, la creacion de ramas, los commits y la revision del codigo. Estos lineamientos permiten mantener la calidad del software y facilitar el trabajo simultaneo sobre los diferentes microservicios.

---

## 1. Convencion de ramas

Cada nueva funcionalidad o correccion se desarrollara en una rama independiente creada a partir de la rama principal (main). Las ramas seran de corta duracion y se eliminaran una vez integradas mediante un Pull Request.

```
+------------------+------------------------------------------+-------------------------------+
| Tipo de rama     | Uso                                     | Ejemplo                        |
+------------------+------------------------------------------+-------------------------------+
| feature/         | Nuevas funcionalidades                   | feature/ms-products           |
+------------------+------------------------------------------+-------------------------------+
| fix/             | Correccion de errores                    | fix/login                     |
+------------------+------------------------------------------+-------------------------------+
| docs/            | Documentacion                           | docs/readme                    |
+------------------+------------------------------------------+-------------------------------+
| refactor/        | Mejoras al codigo sin cambiar funcional- | refactor/ms-users             |
|                  | idades                                   |                               |
+------------------+------------------------------------------+-------------------------------+
| test/            | Pruebas                                 | test/ms-patients               |
+------------------+------------------------------------------+-------------------------------+
| chore/           | Tareas de mantenimiento                 | chore/dependencies             |
+------------------+------------------------------------------+-------------------------------+
```

---

## 2. Conventional Commits

Todos los commits seguiran la especificacion **Conventional Commits** para facilitar la trazabilidad de los cambios en el proyecto.

```
+------------------+------------------------------------------+-----------------------------------------------+
| Prefijo          | Descripcion                              | Ejemplo                                       |
+------------------+------------------------------------------+-----------------------------------------------+
| feat:            | Nueva funcionalidad                      | feat: agregar registro de productos           |
+------------------+------------------------------------------+-----------------------------------------------+
| fix:             | Correccion de errores                    | fix: corregir validacion de pacientes         |
+------------------+------------------------------------------+-----------------------------------------------+
| docs:            | Cambios en documentacion                 | docs: actualizar arquitectura del proyecto    |
+------------------+------------------------------------------+-----------------------------------------------+
| refactor:        | Refactorizacion de codigo                | refactor: optimizar servicio de usuarios      |
+------------------+------------------------------------------+-----------------------------------------------+
| test:            | Incorporacion o mejora de pruebas        | test: agregar pruebas de medicamentos         |
+------------------+------------------------------------------+-----------------------------------------------+
| chore:           | Mantenimiento del proyecto               | chore: actualizar dependencias Maven          |
+------------------+------------------------------------------+-----------------------------------------------+
```

---

## 3. Pull Requests

Todo cambio debera integrarse mediante un **Pull Request (PR)** hacia la rama main.

Cada Pull Request debera incluir:

- Descripcion de la funcionalidad o correccion realizada.
- Microservicio afectado.
- Evidencias de funcionamiento (capturas o pruebas, cuando corresponda).
- Referencia a la tarea o requerimiento desarrollado.

Un Pull Request solo podra aprobarse cuando:

- El codigo compile correctamente.
- Cumpla con los estandares definidos por el equipo.
- No presente conflictos con la rama main.
- Haya sido revisado por otro integrante del equipo.

---

## 4. Buenas practicas

Durante el desarrollo del PRS de Caritas se seguiran las siguientes buenas practicas:

- No desarrollar directamente sobre la rama main.
- Crear una rama por cada funcionalidad o correccion.
- Mantener ramas de corta duracion.
- Realizar commits pequenos y descriptivos.
- Integrar los cambios con frecuencia mediante Pull Requests.
- No subir credenciales, contrasenas o informacion sensible al repositorio.
- Mantener una estructura uniforme en todos los microservicios.
- Documentar los cambios importantes realizados.

---

## 5. Revision de codigo

Antes de integrar cualquier cambio al proyecto, el codigo sera revisado por otro integrante del equipo para verificar su calidad y funcionamiento.

Durante la revision se comprobara que:

- Se respeten las convenciones de nombres y la estructura del proyecto.
- La funcionalidad cumpla con el requerimiento solicitado.
- No existan errores de compilacion o codigo innecesario.
- Se mantenga la arquitectura definida para los microservicios.
- Los cambios no afecten el funcionamiento de otros modulos del sistema.

Una vez aprobada la revision, el Pull Request sera fusionado con la rama main y la rama de trabajo sera eliminada para mantener el repositorio limpio y organizado.
