package grupo03.command;

/**
 * Client: crea el receptor y los comandos concretos, y los entrega al
 * invocador. Datos ficticios.
 */
public final class Principal {

    private Principal() {
    }

    public static void main(String[] args) {
        Matricula matricula = new Matricula("Ana Torres", 20);
        MatriculaInvoker invoker = new MatriculaInvoker();

        Asignatura patrones = new Asignatura("PDS-101", "Patrones de Diseño de Software", 6);
        Asignatura arquitectura = new Asignatura("ARQ-201", "Arquitectura de Software", 6);
        Asignatura calidad = new Asignatura("CAL-301", "Calidad de Software", 6);
        Asignatura gestion = new Asignatura("GPS-401", "Gestión de Proyectos de Software", 4);

        System.out.println("=== Operaciones ejecutadas ===");
        invoker.ejecutar(new InscribirAsignaturaCommand(matricula, patrones, "P1"));
        invoker.ejecutar(new InscribirAsignaturaCommand(matricula, arquitectura, "P2"));
        invoker.ejecutar(new InscribirAsignaturaCommand(matricula, calidad, "P1"));
        invoker.ejecutar(new CambiarParaleloCommand(matricula, "ARQ-201", "P3"));
        System.out.println(matricula);

        System.out.println();
        System.out.println("=== Operación rechazada por el receptor ===");
        try {
            invoker.ejecutar(new InscribirAsignaturaCommand(matricula, gestion, "P1"));
        } catch (IllegalStateException error) {
            System.out.println("No se inscribió: " + error.getMessage());
        }

        System.out.println();
        System.out.println("=== Deshacer las dos últimas operaciones ===");
        invoker.deshacer();
        invoker.deshacer();
        System.out.println(matricula);

        System.out.println();
        System.out.println("=== Rehacer una operación y ejecutar una nueva ===");
        invoker.rehacer();
        invoker.ejecutar(new RetirarAsignaturaCommand(matricula, "PDS-101"));
        invoker.ejecutar(new InscribirAsignaturaCommand(matricula, gestion, "P1"));
        System.out.println(matricula);
        System.out.println("¿Se puede rehacer? " + invoker.puedeRehacer());

        System.out.println();
        System.out.println("=== Bitácora de auditoría ===");
        invoker.getBitacora().forEach(System.out::println);
    }
}
