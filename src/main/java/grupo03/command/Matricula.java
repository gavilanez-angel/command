package grupo03.command;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * Receiver: conoce cómo realizar las operaciones reales sobre la matrícula
 * y protege sus reglas (sin duplicados y con un límite de créditos).
 */
public final class Matricula {

    public static final int MAXIMO_CREDITOS_PREDETERMINADO = 20;

    private final String estudiante;
    private final int maximoCreditos;
    private final Map<String, Inscripcion> inscripciones = new LinkedHashMap<>();

    public Matricula(String estudiante) {
        this(estudiante, MAXIMO_CREDITOS_PREDETERMINADO);
    }

    public Matricula(String estudiante, int maximoCreditos) {
        this.estudiante = textoObligatorio(estudiante, "El estudiante");
        if (maximoCreditos <= 0) {
            throw new IllegalArgumentException("El máximo de créditos debe ser mayor que cero");
        }
        this.maximoCreditos = maximoCreditos;
    }

    public void inscribir(Asignatura asignatura, String paralelo) {
        Objects.requireNonNull(asignatura, "La asignatura es obligatoria");
        if (estaInscrito(asignatura.getCodigo())) {
            throw new IllegalStateException("La asignatura " + asignatura.getCodigo()
                    + " ya está inscrita");
        }
        int creditosResultantes = getTotalCreditos() + asignatura.getCreditos();
        if (creditosResultantes > maximoCreditos) {
            throw new IllegalStateException("Se superaría el máximo de " + maximoCreditos
                    + " créditos (" + creditosResultantes + ")");
        }
        inscripciones.put(asignatura.getCodigo(), new Inscripcion(asignatura, paralelo));
    }

    public Inscripcion retirar(String codigo) {
        Inscripcion retirada = inscripciones.remove(normalizarCodigo(codigo));
        if (retirada == null) {
            throw new IllegalStateException("La asignatura " + codigo + " no está inscrita");
        }
        return retirada;
    }

    /**
     * Cambia el paralelo y devuelve el paralelo anterior.
     */
    public String cambiarParalelo(String codigo, String nuevoParalelo) {
        String clave = normalizarCodigo(codigo);
        Inscripcion actual = inscripciones.get(clave);
        if (actual == null) {
            throw new IllegalStateException("La asignatura " + codigo + " no está inscrita");
        }
        inscripciones.put(clave, new Inscripcion(actual.getAsignatura(), nuevoParalelo));
        return actual.getParalelo();
    }

    public boolean estaInscrito(String codigo) {
        return inscripciones.containsKey(normalizarCodigo(codigo));
    }

    public String getParalelo(String codigo) {
        Inscripcion inscripcion = inscripciones.get(normalizarCodigo(codigo));
        return inscripcion == null ? null : inscripcion.getParalelo();
    }

    public int getTotalCreditos() {
        return inscripciones.values().stream()
                .mapToInt(inscripcion -> inscripcion.getAsignatura().getCreditos())
                .sum();
    }

    public List<Inscripcion> getInscripciones() {
        return List.copyOf(inscripciones.values());
    }

    public String getEstudiante() {
        return estudiante;
    }

    public int getMaximoCreditos() {
        return maximoCreditos;
    }

    @Override
    public String toString() {
        StringBuilder texto = new StringBuilder("Matrícula de ").append(estudiante)
                .append(" | créditos: ").append(getTotalCreditos())
                .append("/").append(maximoCreditos);
        inscripciones.values().forEach(inscripcion ->
                texto.append(System.lineSeparator()).append("  - ").append(inscripcion));
        return texto.toString();
    }

    static String textoObligatorio(String texto, String campo) {
        if (texto == null || texto.isBlank()) {
            throw new IllegalArgumentException(campo + " es obligatorio");
        }
        return texto.strip();
    }

    private static String normalizarCodigo(String codigo) {
        return textoObligatorio(codigo, "El código").toUpperCase(Locale.ROOT);
    }
}
