package grupo03.command;

import java.util.Objects;

/**
 * ConcreteCommand: cambia el paralelo de una asignatura inscrita y recuerda
 * el paralelo anterior para poder deshacer el cambio.
 */
public final class CambiarParaleloCommand implements MatriculaCommand {

    private final Matricula matricula;
    private final String codigoAsignatura;
    private final String nuevoParalelo;
    private String paraleloAnterior;

    public CambiarParaleloCommand(Matricula matricula, String codigoAsignatura, String nuevoParalelo) {
        this.matricula = Objects.requireNonNull(matricula, "La matrícula es obligatoria");
        this.codigoAsignatura = Matricula.textoObligatorio(codigoAsignatura, "El código");
        this.nuevoParalelo = Matricula.textoObligatorio(nuevoParalelo, "El paralelo");
    }

    @Override
    public void ejecutar() {
        paraleloAnterior = matricula.cambiarParalelo(codigoAsignatura, nuevoParalelo);
    }

    @Override
    public void deshacer() {
        if (paraleloAnterior == null) {
            throw new IllegalStateException("No se puede deshacer un cambio que no se ejecutó");
        }
        matricula.cambiarParalelo(codigoAsignatura, paraleloAnterior);
        paraleloAnterior = null;
    }

    @Override
    public String getDescripcion() {
        return "Cambiar " + codigoAsignatura + " al paralelo " + nuevoParalelo;
    }
}
