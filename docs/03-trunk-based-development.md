# Trunk-Based Development (TBD)

## ¿Por que eligieron Trunk-Based Development (TBD)?

Se eligio **Trunk-Based Development (TBD)** porque permite que el equipo del proyecto PRS de Caritas trabaje de forma organizada sobre los diferentes microservicios, integrando los cambios de manera frecuente en la rama principal (main). Esto facilita el desarrollo colaborativo y mantiene el sistema siempre actualizado.

## ¿Que problemas quieren evitar?

Con TBD se busca evitar:

- **Conflictos de integracion** entre los microservicios.
- **Ramas desactualizadas** que dificultan la fusion de codigo.
- **Retrasos al unir cambios** que retrasan el avance del proyecto.
- **Errores provocados por desarrollos aislados** que no se prueban en conjunto.

Ademas, permite mantener un codigo estable durante todo el desarrollo del sistema.

## ¿Como ayuda cuando existen muchos microservicios?

En el PRS de Caritas, cada integrante desarrolla uno o mas microservicios de forma independiente. TBD facilita que todos integren sus cambios continuamente mediante **Pull Requests**, reduciendo conflictos, mejorando la colaboracion y asegurando que todos los servicios funcionen correctamente dentro del sistema.

---

## Flujo de trabajo con Trunk-Based Development (TBD)

```
                      Nueva tarea asignada
                               |
                               v
                   Actualizar rama main local
                  (git checkout main / git pull)
                               |
                               v
      Crear una rama feature desde main
 (feature/ms-products, feature/ms-users, etc.)
                               |
                               v
            Desarrollar el microservicio asignado
                               |
                               v
                 Realizar commits frecuentes
      (feat:, fix:, docs:, refactor:, chore:)
                               |
                               v
                    Subir cambios al repositorio
                           (git push)
                               |
                               v
                  Crear un Pull Request (PR)
                               |
                               v
                   Revision del codigo por el equipo
                               |
                     Cumple los estandares?
                      +---------+---------+
                      |                   |
                    No                   Si
                      |                   |
      Realizar correcciones          Aprobar PR
                      |                   |
                      +---------+---------+
                                |
                                v
                     Merge hacia la rama main
                                |
                                v
                  Eliminar la rama feature/*
                                |
                                v
               Todos actualizan nuevamente main
                                |
                                v
                   Comienza una nueva funcionalidad
```

---

## Ejemplo Practico: Flujo TBD aplicado al PRS de Caritas

Supongamos que **Asumi** debe implementar una nueva funcionalidad en el microservicio **ms-products**.

```
       main (actualizada)
           |
           v
   git checkout -b feature/ms-products
           |
           v
   Asumi desarrolla la funcionalidad
   + commits frecuentes (feat:, fix:, docs:)
           |
           v
   git push origin feature/ms-products
           |
           v
   Crear Pull Request --> Revision del equipo
           |
     Cumple estandares?
      +--------+--------+
      |                  |
    No                  Si
      |                  |
  Corregir          Aprobar PR
      |                  |
      +--------+--------+
               |
               v
   Merge a main --> Eliminar feature/ms-products
               |
               v
       main (con nueva funcionalidad)
```

---

## ¿Como se aplica en los microservicios?

Cada integrante trabaja unicamente sobre el microservicio que tiene asignado:

```
+---------------------+------------------+---------------------+
| Integrante          | Microservicio    | Rama de trabajo     |
+---------------------+------------------+---------------------+
| Asumi Penafiel      | ms-products      | feature/ms-products |
+---------------------+------------------+---------------------+
| Jesus Sanchez       | ms-users         | feature/ms-users    |
+---------------------+------------------+---------------------+
| Marilyn Vilcapuma   | ms-suppliers     | feature/ms-suppliers|
+---------------------+------------------+---------------------+
| Marco Infante       | ms-patients      | feature/ms-patients |
+---------------------+------------------+---------------------+
| Jesus Huaripaucar   | ms-doctors       | feature/ms-doctors  |
+---------------------+------------------+---------------------+
| Luis Rivas          | ms-medications   | feature/ms-medicat- |
|                     |                  | ions                |
+---------------------+------------------+---------------------+
| Lionel Huamani      | ms-medicine-sale | feature/ms-medicine-|
|                     |                  | sale                |
+---------------------+------------------+---------------------+
| Alonso Cardenas     | ms-consultation  | feature/ms-consulta-|
|                     |                  | tion                |
+---------------------+------------------+---------------------+
| Snayder Vicente     | ms-specialties   | feature/ms-special- |
|                     |                  | ties                |
+---------------------+------------------+---------------------+
```

Cuando una funcionalidad esta terminada:

1. Se crea un **Pull Request** hacia main.
2. El equipo revisa el codigo.
3. Si es aprobado, se realiza el **merge**.
4. Se elimina la rama `feature/*`.
5. Todos actualizan su rama main antes de comenzar una nueva tarea.

