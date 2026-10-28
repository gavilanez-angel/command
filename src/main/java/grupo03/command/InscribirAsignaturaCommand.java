package grupo03.command;

import java.util.Objects;

/**
 * ConcreteCommand: inscribe una asignatura en un paralelo.
 */
public final class InscribirAsignaturaCommand implements MatriculaCommand {

    private final Matricula matricula;
    private final Asignatura asignatura;
    private final String paralelo;

    public InscribirAsignaturaCommand(Matricula matricula, Asignatura asignatura, String paralelo) {
        this.matricula = Objects.requireNonNull(matricula, "La matrícula es obligatoria");
        this.asignatura = Objects.requireNonNull(asignatura, "La asignatura es obligatoria");
        this.paralelo = Matricula.textoObligatorio(paralelo, "El paralelo");
    }

    @Override
    public void ejecutar() {
        matricula.inscribir(asignatura, paralelo);
    }

    @Override
    public void deshacer() {
        matricula.retirar(asignatura.getCodigo());
    }

    @Override
    public String getDescripcion() {
        return "Inscribir " + asignatura.getCodigo() + " en paralelo " + paralelo;
    }
}
