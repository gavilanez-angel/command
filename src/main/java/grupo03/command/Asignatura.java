package grupo03.command;

import java.util.Locale;
import java.util.Objects;

/**
 * Objeto de valor del dominio: asignatura que puede inscribirse.
 */
public final class Asignatura {

    private final String codigo;
    private final String nombre;
    private final int creditos;

    public Asignatura(String codigo, String nombre, int creditos) {
        this.codigo = Matricula.textoObligatorio(codigo, "El código").toUpperCase(Locale.ROOT);
        this.nombre = Matricula.textoObligatorio(nombre, "El nombre");
        if (creditos <= 0) {
            throw new IllegalArgumentException("Los créditos deben ser mayores que cero");
        }
        this.creditos = creditos;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCreditos() {
        return creditos;
    }

    @Override
    public boolean equals(Object otro) {
        return otro instanceof Asignatura && codigo.equals(((Asignatura) otro).codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return codigo + " " + nombre + " (" + creditos + " cr.)";
    }
}
