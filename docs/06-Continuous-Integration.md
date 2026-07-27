# Continuous Integration

## ¿Por que implementar Integracion Continua?

El proyecto PRS de Caritas esta compuesto por varios microservicios desarrollados por diferentes integrantes del equipo. Para garantizar que cada cambio mantenga la estabilidad del sistema, se implementara un proceso de **Integracion Continua (CI)** mediante **GitHub Actions**, automatizando la validacion del codigo antes de integrarlo a la rama main.

---

## Flujo de Integracion Continua

```
                    Desarrollador
                          |
             Realiza Push o Pull Request
                          |
                          v
                  GitHub Actions inicia
                          |
           +--------------+--------------+
           |              |              |
           v              v              v
      Descargar       Compilar       Ejecutar
      dependencias   microservicio    pruebas
           |              |              |
           +--------------+--------------+
                          v
                Construir imagen Docker
                          |
                 ¿Todo fue exitoso?
                   +--------+--------+
                   |                 |
                 No                Si
                   |                 |
         Notificar errores    Aprobar Pipeline
                   |                 |
                   +--------+--------+
                            v
                     Merge hacia main
```

---

## Pipeline del proyecto

Cada vez que un integrante realiza un Push o crea un Pull Request, GitHub Actions ejecutara automaticamente el siguiente proceso:

```
+------------+-------------------------------------------------------+
| Etapa      | Descripcion                                           |
+------------+-------------------------------------------------------+
| Checkout   | Descarga el codigo del repositorio.                   |
+------------+-------------------------------------------------------+
| Dependenc- | Instala las dependencias del proyecto.                |
| ias        |                                                       |
+------------+-------------------------------------------------------+
| Build      | Compila el microservicio con Maven.                   |
+------------+-------------------------------------------------------+
| Test       | Ejecuta las pruebas automaticas.                      |
+------------+-------------------------------------------------------+
| Docker     | Construye la imagen Docker del microservicio.         |
+------------+-------------------------------------------------------+
| Validacion | Si todas las etapas son exitosas, el cambio puede     |
|            | integrarse a main.                                    |
+------------+-------------------------------------------------------+
```

---

## ¿Que valida GitHub Actions?

Antes de aceptar un Pull Request, el pipeline verificara:

- El microservicio compila correctamente.
- No existen errores de construccion.
- Las pruebas se ejecutan sin fallos.
- La imagen Docker puede generarse correctamente.
- El codigo esta listo para integrarse a la rama main.

Si alguna validacion falla, el Pull Request debera corregirse antes de ser aprobado.

---

## Tecnologias utilizadas

```
+------------------+-------------------------------------------------------+
| Herramienta      | Funcion                                               |
+------------------+-------------------------------------------------------+
| GitHub Actions   | Automatiza el proceso de Integracion Continua.        |
+------------------+-------------------------------------------------------+
| Maven            | Compila los microservicios y gestiona dependencias.   |
+------------------+-------------------------------------------------------+
| JUnit            | Ejecuta las pruebas automaticas del proyecto.         |
+------------------+-------------------------------------------------------+
| Docker           | Genera las imagenes de los microservicios para su     |
|                  | despliegue.                                           |
+------------------+-------------------------------------------------------+
| GitHub           | Gestiona el codigo fuente y los Pull Requests.        |
+------------------+-------------------------------------------------------+
```

---

## Beneficios para el PRS de Caritas

- **Detecta errores** antes de integrar cambios.
- **Automatiza** la compilacion y validacion de cada microservicio.
- **Facilita** el trabajo colaborativo entre los integrantes del equipo.
- **Garantiza** que cada microservicio pueda desplegarse mediante Docker.
- **Mantiene** la rama main estable y lista para continuar el desarrollo.

**Resultado esperado:** Cada funcionalidad desarrollada para los microservicios del PRS de Caritas sera validada automaticamente antes de integrarse al proyecto, mejorando la calidad del software y reduciendo errores durante el desarrollo.
