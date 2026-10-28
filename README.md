# PDS – Actividad 03 – Grupo 03 – Patrón Command

Maestría en Software · Patrones de Diseño de Software

**Integrantes:** Anton Yagual Juan · Arboleda Naranjo Isaac · Encalada Cruz Edison ·
Gavilanez Guanoluisa Angel · Guamushig Jaque Klever

**Escenario:** durante la matrícula en línea el estudiante inscribe asignaturas, las retira o
cambia de paralelo. Cada operación es un objeto comando que se puede ejecutar, deshacer y
rehacer, y queda registrada en una bitácora de auditoría (incluidas las operaciones rechazadas).

## Ejecutar

- NetBeans: abrir la carpeta como proyecto Maven y ejecutar `grupo03.command.Principal`.
- Pruebas (opcional): `mvn test`

## Participantes GoF

| Participante | Clase |
|---|---|
| Command | `MatriculaCommand` |
| ConcreteCommand | `InscribirAsignaturaCommand`, `RetirarAsignaturaCommand`, `CambiarParaleloCommand` |
| Receiver | `Matricula` |
| Invoker | `MatriculaInvoker` |
| Client | `Principal` |

Diagrama UML: `docs/uml/command_uml.png`
