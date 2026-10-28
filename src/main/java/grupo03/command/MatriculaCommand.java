package grupo03.command;

/**
 * Command: interfaz común de las operaciones de matrícula.
 */
public interface MatriculaCommand {

    void ejecutar();

    void deshacer();

    String getDescripcion();
}
