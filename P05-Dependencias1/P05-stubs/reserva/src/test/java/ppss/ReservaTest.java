package ppss;

import com.sun.source.tree.AssertTree;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ppss.excepciones.ReservaException;
import ppss.excepciones.SocioInvalidoException;

public class ReservaTest {

    @Test
    public void C1_realizaReserva_should_return_error_permisos_excepction_when_wrong_login(){
        // Arrange
        String login = "xxxx";
        String password = "xxxx";
        String socio = "Luis";
        String[] isbns = {"11111"};
        OperacionStub stub = new OperacionStub();
        Factoria.setOperacion(stub);
        ReservaTestable sut = new ReservaTestable();
        String esperado = "ERROR de permisos; ";

        // Act
        ReservaException excepcion = Assertions.assertThrows(ReservaException.class, () -> {sut.realizaReserva(login, password, socio, isbns);});

        // Assert
        Assertions.assertEquals(esperado, excepcion.getMessage());
    }

    @Test
    public void C2_realizaReserva_should_not_return_exception_when_right_login(){
        // Arrange
        String login = "ppss";
        String password = "ppss";
        String socio = "Luis";
        String[] isbns = {"11111", "22222"};
        OperacionStub stub = new OperacionStub();
        Factoria.setOperacion(stub);
        ReservaTestable sut = new ReservaTestable();

        // Act && Assert
        Assertions.assertDoesNotThrow(() -> {sut.realizaReserva(login, password, socio, isbns);});
    }

    @Test
    public void C3_realizaReserva_should_throw_isbn_exception_when_two_wrong_isbns(){
        // Arrange
        String login = "ppss";
        String password = "ppss";
        String socio = "Luis";
        String[] isbns = {"11111", "33333", "44444"};
        OperacionStub stub = new OperacionStub();
        Factoria.setOperacion(stub);
        ReservaTestable sut = new ReservaTestable();
        String esperado = "ISBN invalido:33333; ISBN invalido:44444; ";

        // Act
        ReservaException excepcion = Assertions.assertThrows(ReservaException.class, () -> sut.realizaReserva(login, password, socio, isbns));

        // Assert
        Assertions.assertEquals(esperado, excepcion.getMessage());
    }

    @Test
    public void C4_realizaReserva_should_throw_socio_exception_when_socio_is_Pepe(){
        // Arrange
        String login = "ppss";
        String password = "ppss";
        String socio = "Pepe";
        String[] isbns = {"11111"};
        OperacionStub stub = new OperacionStub();
        Factoria.setOperacion(stub);
        ReservaTestable sut = new ReservaTestable();
        String esperado = "SOCIO invalido; ";

        // Act
        ReservaException excepcion = Assertions.assertThrows(ReservaException.class, () -> sut.realizaReserva(login, password, socio, isbns));

        // Assert
        Assertions.assertEquals(esperado, excepcion.getMessage());
    }

    @Test
    public void C5_realizaReserva_should_throw_jbdc_exception_when_socio_Luis_y_isbn_22222(){
        // Arrange
        String login = "ppss";
        String password = "ppss";
        String socio = "Luis";
        String[] isbns = {"11111", "22222"};
        OperacionStub stub = new OperacionStub();
        stub.setLanzarDBException(true);        // falsear la JDBException
        Factoria.setOperacion(stub);
        ReservaTestable sut = new ReservaTestable();
        String esperado = "CONEXION invalida; ";

        // Act
        ReservaException excepcion = Assertions.assertThrows(ReservaException.class, () -> sut.realizaReserva(login, password, socio, isbns));

        // Assert
        Assertions.assertEquals(esperado, excepcion.getMessage());
    }
}
