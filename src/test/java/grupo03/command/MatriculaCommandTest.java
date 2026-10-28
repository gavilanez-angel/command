package grupo03.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MatriculaCommandTest {

    private final Asignatura patrones = new Asignatura("PDS-101", "Patrones de Diseño", 6);
    private final Asignatura arquitectura = new Asignatura("ARQ-201", "Arquitectura", 6);
    private final Asignatura calidad = new Asignatura("CAL-301", "Calidad", 6);
    private final Asignatura gestion = new Asignatura("GPS-401", "Gestión", 4);

    private Matricula matricula;
    private MatriculaInvoker invoker;

    @BeforeEach
    void preparar() {
        matricula = new Matricula("Ana Torres", 20);
        invoker = new MatriculaInvoker();
    }

    @Test
    void cadaComandoEjecutaSuOperacionSobreElReceptor() {
        invoker.ejecutar(new InscribirAsignaturaCommand(matricula, patrones, "P1"));
        assertTrue(matricula.estaInscrito("PDS-101"));
        invoker.ejecutar(new CambiarParaleloCommand(matricula, "PDS-101", "P2"));
        assertEquals("P2", matricula.getParalelo("PDS-101"));
        invoker.ejecutar(new RetirarAsignaturaCommand(matricula, "PDS-101"));
        assertFalse(matricula.estaInscrito("PDS-101"));
    }

    @Test
    void deshacerRestauraElEstadoAnteriorEnOrdenInverso() {
        invoker.ejecutar(new InscribirAsignaturaCommand(matricula, patrones, "P1"));
        invoker.ejecutar(new CambiarParaleloCommand(matricula, "PDS-101", "P3"));
        invoker.ejecutar(new RetirarAsignaturaCommand(matricula, "PDS-101"));

        assertTrue(invoker.deshacer());
        assertEquals("P3", matricula.getParalelo("PDS-101"));
        assertTrue(invoker.deshacer());
        assertEquals("P1", matricula.getParalelo("PDS-101"));
        assertTrue(invoker.deshacer());
        assertFalse(matricula.estaInscrito("PDS-101"));
        assertFalse(invoker.deshacer());
    }

    @Test
    void rehacerVuelveAEjecutarLoDeshecho() {
        invoker.ejecutar(new InscribirAsignaturaCommand(matricula, patrones, "P1"));
        invoker.ejecutar(new InscribirAsignaturaCommand(matricula, arquitectura, "P2"));
        invoker.deshacer();
        assertEquals(6, matricula.getTotalCreditos());
        assertTrue(invoker.rehacer());
        assertEquals(12, matricula.getTotalCreditos());
        assertFalse(invoker.rehacer());
    }

    @Test
    void unNuevoComandoDescartaLasOperacionesPorRehacer() {
        invoker.ejecutar(new InscribirAsignaturaCommand(matricula, patrones, "P1"));
        invoker.deshacer();
        assertTrue(invoker.puedeRehacer());
        invoker.ejecutar(new InscribirAsignaturaCommand(matricula, arquitectura, "P1"));
        assertFalse(invoker.puedeRehacer());
    }

    @Test
    void unComandoRechazadoNoEntraAlHistorialPeroQuedaEnLaBitacora() {
        invoker.ejecutar(new InscribirAsignaturaCommand(matricula, patrones, "P1"));
        invoker.ejecutar(new InscribirAsignaturaCommand(matricula, arquitectura, "P1"));
        invoker.ejecutar(new InscribirAsignaturaCommand(matricula, calidad, "P1"));

        assertThrows(IllegalStateException.class,
                () -> invoker.ejecutar(new InscribirAsignaturaCommand(matricula, gestion, "P1")));
        assertEquals(18, matricula.getTotalCreditos());
        assertTrue(invoker.getBitacora().get(3).contains("RECHAZADO"));

        invoker.deshacer();
        assertFalse(matricula.estaInscrito("CAL-301"));
    }

    @Test
    void elReceptorProtegeSusReglas() {
        matricula.inscribir(patrones, "P1");
        assertThrows(IllegalStateException.class, () -> matricula.inscribir(patrones, "P2"));
        assertThrows(IllegalStateException.class, () -> matricula.retirar("ARQ-201"));
        assertThrows(IllegalStateException.class, () -> matricula.cambiarParalelo("ARQ-201", "P1"));
        assertThrows(UnsupportedOperationException.class,
                () -> matricula.getInscripciones().clear());
    }

    @Test
    void noSePuedeDeshacerUnComandoQueNoSeEjecuto() {
        assertThrows(IllegalStateException.class,
                () -> new RetirarAsignaturaCommand(matricula, "PDS-101").deshacer());
        assertThrows(IllegalStateException.class,
                () -> new CambiarParaleloCommand(matricula, "PDS-101", "P2").deshacer());
    }

    @Test
    void elInvocadorTrabajaSoloConLaAbstraccionCommand() {
        List<String> llamadas = new ArrayList<>();
        MatriculaCommand comandoNuevo = new MatriculaCommand() {
            @Override
            public void ejecutar() {
                llamadas.add("ejecutar");
            }

            @Override
            public void deshacer() {
                llamadas.add("deshacer");
            }

            @Override
            public String getDescripcion() {
                return "Solicitar homologación";
            }
        };
        invoker.ejecutar(comandoNuevo);
        invoker.deshacer();
        invoker.rehacer();
        assertEquals(List.of("ejecutar", "deshacer", "ejecutar"), llamadas);
        assertEquals(3, invoker.getBitacora().size());
        assertTrue(invoker.getBitacora().get(0).contains("Solicitar homologación"));
    }

    @Test
    void validaDatosObligatorios() {
        assertThrows(NullPointerException.class, () -> invoker.ejecutar(null));
        assertThrows(NullPointerException.class,
                () -> new InscribirAsignaturaCommand(null, patrones, "P1"));
        assertThrows(IllegalArgumentException.class,
                () -> new InscribirAsignaturaCommand(matricula, patrones, " "));
        assertThrows(IllegalArgumentException.class, () -> new Asignatura("X", "Y", 0));
        assertThrows(IllegalArgumentException.class, () -> new Matricula("Ana", 0));
    }
}
