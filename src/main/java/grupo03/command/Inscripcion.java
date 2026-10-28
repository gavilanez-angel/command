package grupo03.command;

import java.util.Objects;

/**
 * Objeto de valor: una asignatura inscrita en un paralelo.
 */
public final class Inscripcion {

    private final Asignatura asignatura;
    private final String paralelo;

    public Inscripcion(Asignatura asignatura, String paralelo) {
        this.asignatura = Objects.requireNonNull(asignatura, "La asignatura es obligatoria");
        this.paralelo = Matricula.textoObligatorio(paralelo, "El paralelo");
    }

    public Asignatura getAsignatura() {
        return asignatura;
    }

    public String getParalelo() {
        return paralelo;
    }

    @Override
    public String toString() {
        return asignatura + " - paralelo " + paralelo;
    }
}
