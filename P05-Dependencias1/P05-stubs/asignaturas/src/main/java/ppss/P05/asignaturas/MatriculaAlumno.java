package ppss.P05.asignaturas;
import java.util.ArrayList;

public class MatriculaAlumno {
    protected Operacion getOperacion() {
        return new Operacion();
    }
    // SUT Testable, tiene un punto de inyección
    public JustificanteMatricula validaAsignaturas(String dni, String[] asignaturas) {
        JustificanteMatricula justificante = new JustificanteMatricula();
        ArrayList<String> validas = new ArrayList<>();
        ArrayList<String> listaErrores = new ArrayList<>();
        Operacion op = getOperacion();                      // dependencia externa (SEAM)
        for (String asignatura : asignaturas) {
            try {
                op.compruebaMatricula(dni, asignatura);     // dependencia externa
                validas.add(asignatura);
            } catch (AsignaturaIncorrectaException ex) {
                listaErrores.add("Asignatura " + asignatura + " no existe");
            } catch (AsignaturaCursadaException ex) {
                listaErrores.add("Asignatura " + asignatura + " ya cursada");
            }
        }
        justificante.setDni(dni);
        justificante.setAsignaturas(validas);
        justificante.setErrores(listaErrores);
        return justificante;
    }
}
