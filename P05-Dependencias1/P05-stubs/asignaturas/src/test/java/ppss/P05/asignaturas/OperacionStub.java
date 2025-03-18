package ppss.P05.asignaturas;

import java.util.Arrays;
import java.util.List;

public class OperacionStub extends Operacion {
    @Override
    public void compruebaMatricula(String dni, String asignatura) throws AsignaturaCursadaException, AsignaturaIncorrectaException {
        List<String> asignaturasInexistentes = Arrays.asList("YYY", "ZZ");
        List<String> asignaturasCursadas = Arrays.asList("P1", "FC", "FFI");

        if(asignaturasCursadas.contains(asignatura)){
            throw new AsignaturaCursadaException();
        }else if(asignaturasInexistentes.contains(asignatura)){
            throw new AsignaturaIncorrectaException();
        }
    }
}
