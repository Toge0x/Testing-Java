package ppss;

import ppss.excepciones.IsbnInvalidoException;
import ppss.excepciones.JDBCException;
import ppss.excepciones.SocioInvalidoException;


public class OperacionStub extends Operacion {
    private boolean lanzarDBException = false;

    public void setLanzarDBException(boolean lanzar) {      // falsear el caso 5
        lanzarDBException = lanzar;
    }
    @Override
    public void operacionReserva(String socio, String isbn) throws IsbnInvalidoException, JDBCException, SocioInvalidoException {
        if(socio.equals("Pepe")) {
            throw new SocioInvalidoException();
        }else if(isbn.equals("33333") || isbn.equals("44444")) {
            throw new IsbnInvalidoException();
        }else if (lanzarDBException){
            throw new JDBCException();
        }
    }
}
