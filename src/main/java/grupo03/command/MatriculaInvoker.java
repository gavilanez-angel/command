package grupo03.command;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Objects;

/**
 * Invoker: ejecuta los comandos sin conocer qué operación realizan,
 * mantiene el historial para deshacer y rehacer, y registra una bitácora
 * de auditoría.
 */
public final class MatriculaInvoker {

    private final Deque<MatriculaCommand> ejecutados = new ArrayDeque<>();
    private final Deque<MatriculaCommand> deshechos = new ArrayDeque<>();
    private final List<String> bitacora = new ArrayList<>();

    public void ejecutar(MatriculaCommand comando) {
        Objects.requireNonNull(comando, "El comando es obligatorio");
        try {
            comando.ejecutar();
        } catch (RuntimeException error) {
            registrar("RECHAZADO", comando.getDescripcion() + " -> " + error.getMessage());
            throw error;
        }
        ejecutados.push(comando);
        deshechos.clear();
        registrar("EJECUTADO", comando.getDescripcion());
    }

    public boolean deshacer() {
        if (ejecutados.isEmpty()) {
            return false;
        }
        MatriculaCommand comando = ejecutados.pop();
        comando.deshacer();
        deshechos.push(comando);
        registrar("DESHECHO", comando.getDescripcion());
        return true;
    }

    public boolean rehacer() {
        if (deshechos.isEmpty()) {
            return false;
        }
        MatriculaCommand comando = deshechos.pop();
        comando.ejecutar();
        ejecutados.push(comando);
        registrar("REHECHO", comando.getDescripcion());
        return true;
    }

    public boolean puedeDeshacer() {
        return !ejecutados.isEmpty();
    }

    public boolean puedeRehacer() {
        return !deshechos.isEmpty();
    }

    public List<String> getBitacora() {
        return List.copyOf(bitacora);
    }

    private void registrar(String accion, String detalle) {
        bitacora.add(String.format("%02d %-9s %s", bitacora.size() + 1, accion, detalle));
    }
}
