package grupo03.command;

import java.util.Objects;

/**
 * ConcreteCommand: retira una asignatura y conserva la inscripción
 * eliminada para poder restaurarla al deshacer.
 */
public final class RetirarAsignaturaCommand implements MatriculaCommand {

    private final Matricula matricula;
    private final String codigoAsignatura;
    private Inscripcion inscripcionRetirada;

    public RetirarAsignaturaCommand(Matricula matricula, String codigoAsignatura) {
        this.matricula = Objects.requireNonNull(matricula, "La matrícula es obligatoria");
        this.codigoAsignatura = Matricula.textoObligatorio(codigoAsignatura, "El código");
    }

    @Override
    public void ejecutar() {
        inscripcionRetirada = matricula.retirar(codigoAsignatura);
    }

    @Override
    public void deshacer() {
        if (inscripcionRetirada == null) {
            throw new IllegalStateException("No se puede deshacer un retiro que no se ejecutó");
        }
        matricula.inscribir(inscripcionRetirada.getAsignatura(), inscripcionRetirada.getParalelo());
        inscripcionRetirada = null;
    }

    @Override
    public String getDescripcion() {
        return "Retirar " + codigoAsignatura;
    }
}
