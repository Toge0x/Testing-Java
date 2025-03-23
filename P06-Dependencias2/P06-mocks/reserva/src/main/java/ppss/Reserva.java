package ppss;

import ppss.excepciones.IsbnInvalidoException;
import ppss.excepciones.JDBCException;
import ppss.excepciones.ReservaException;
import ppss.excepciones.SocioInvalidoException;

import java.util.ArrayList;

public class Reserva {

    private FactoriaBOs factoria;

    public boolean compruebaPermisos(String login, String password, Usuario tipoUsu) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void setFactoria(FactoriaBOs factoria) {     // solo queda la opcion de setter con atributo privado
        this.factoria = factoria;
    }

    // SUT NO TESTABLE - Refactorizar
    public void realizaReserva(String login, String password, String socio, String [] isbns) throws ReservaException {
        ArrayList<String> errores = new ArrayList<String>();
        if(!compruebaPermisos(login, password, Usuario.BIBLIOTECARIO)) {    // dependencia interna
            errores.add("ERROR de permisos");
        } else {
            //FactoriaBOs fd = new FactoriaBOs();         // NO TESTABLE
            FactoriaBOs fd = factoria;                      // SEAM
            IOperacionBO io = fd.getOperacionBO();          // dependencia externa
            try {
                for(String isbn: isbns) {
                    try {
                        io.operacionReserva(socio, isbn);       // dependencia externa
                    } catch (IsbnInvalidoException iie) {
                        errores.add("ISBN invalido" + ":" + isbn);
                    }
                }
            } catch (SocioInvalidoException sie) {
                errores.add("SOCIO invalido");
            } catch (JDBCException je) {
                errores.add("CONEXION invalida");
            }
        }
        if (errores.size() > 0) {
            String mensajeError = "";
            for(String error: errores) {
                mensajeError += error + "; ";
            }
            throw new ReservaException(mensajeError);
        }
    }
}