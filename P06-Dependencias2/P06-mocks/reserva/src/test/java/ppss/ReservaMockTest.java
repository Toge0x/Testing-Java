package ppss;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ppss.excepciones.IsbnInvalidoException;
import ppss.excepciones.JDBCException;
import ppss.excepciones.ReservaException;
import ppss.excepciones.SocioInvalidoException;


public class ReservaMockTest {
    IMocksControl ctrl;
    Reserva sut;
    IOperacionBO mockOperacion;
    FactoriaBOs mockFactoria;

    @BeforeEach
    public void setUp() {
        ctrl = EasyMock.createStrictControl();
        sut = EasyMock.partialMockBuilder(Reserva.class).addMockedMethod("compruebaPermisos").mock(ctrl);
        mockOperacion = ctrl.mock(IOperacionBO.class);
        mockFactoria = ctrl.mock(FactoriaBOs.class);
        sut.setFactoria(mockFactoria);      // NO HACEMOS DOBLES DE LOS SETTERS NI GETTERS!!!!!!!!!!!!!!!
    }

    @Test
    public void C1_realizarReserva_should_return_error_permisos_when_socio_is_Pepe(){
        // Arrange
        String login = "xxxx";
        String password = "xxxx";
        String socio = "Pepe";
        String[] isbns = {"33333"};
        String esperado = "ERROR de permisos; ";

        // Act
        EasyMock.expect(sut.compruebaPermisos(login, password, Usuario.BIBLIOTECARIO)).andReturn(false);
        ctrl.replay();
        ReservaException reservaException = Assertions.assertThrows(ReservaException.class,
                () -> sut.realizaReserva(login, password, socio, isbns));

        // Assert
        Assertions.assertEquals(esperado, reservaException.getMessage());
        ctrl.verify();
    }

    @Test
    public void C2_realizarReserva_should_not_return_exception_when_correct_login(){
        // Arrange
        String login = "ppss";
        String password = "ppss";
        String socio = "Pepe";
        String[] isbns = {"22222", "33333"};

        // Act
        EasyMock.expect(sut.compruebaPermisos(login, password, Usuario.BIBLIOTECARIO)).andReturn(true);
        mockFactoria.getOperacionBO();
        EasyMock.expectLastCall().andReturn(mockOperacion);
        Assertions.assertDoesNotThrow(() -> {
            mockOperacion.operacionReserva(socio, isbns[0]);
            mockOperacion.operacionReserva(socio, isbns[1]);
        });
        ctrl.replay();

        // Assert
        Assertions.assertDoesNotThrow(() -> sut.realizaReserva(login, password, socio, isbns));
        ctrl.verify();
    }

    @Test
    public void C3_realizarReserva_should_return_isbn_exception_when_no_isbn_exists(){
        // Arrange
        String login = "ppss";
        String password = "ppss";
        String socio = "Pepe";
        String[] isbns = {"11111", "22222", "55555"};
        String esperado = "ISBN invalido:11111; ISBN invalido:55555; ";

        // Act
        EasyMock.expect(sut.compruebaPermisos(login, password, Usuario.BIBLIOTECARIO)).andReturn(true);
        mockFactoria.getOperacionBO();
        EasyMock.expectLastCall().andReturn(mockOperacion);
        Assertions.assertDoesNotThrow(() -> {
            mockOperacion.operacionReserva(socio, isbns[0]);
            EasyMock.expectLastCall().andThrow(new IsbnInvalidoException());
            mockOperacion.operacionReserva(socio, isbns[1]);
            mockOperacion.operacionReserva(socio, isbns[2]);
            EasyMock.expectLastCall().andThrow(new IsbnInvalidoException());
        });
        ctrl.replay();
        ReservaException excepcion = Assertions.assertThrows(ReservaException.class, () ->
                sut.realizaReserva(login, password, socio, isbns));

        // Asert
        Assertions.assertEquals(esperado, excepcion.getMessage());
        ctrl.verify();
    }

    @Test
    public void C4_realizarReserva_should_return_socio_invalido_when_socio_is_not_socio(){
        // Arrange
        String login = "ppss";
        String password = "ppss";
        String socio = "Luis";
        String[] isbns = {"22222"};
        String esperado = "SOCIO invalido; ";

        // Act
        EasyMock.expect(sut.compruebaPermisos(login, password, Usuario.BIBLIOTECARIO)).andReturn(true);
        EasyMock.expect(mockFactoria.getOperacionBO()).andReturn(mockOperacion);
        Assertions.assertDoesNotThrow(() -> mockOperacion.operacionReserva(socio, isbns[0]));
        EasyMock.expectLastCall().andThrow(new SocioInvalidoException());
        ctrl.replay();
        ReservaException excepcion = Assertions.assertThrows(ReservaException.class, () -> sut.realizaReserva(login, password, socio, isbns));

        // Assert
        Assertions.assertEquals(esperado, excepcion.getMessage());
        ctrl.verify();
    }

    @Test
    public void C5_realizarReserva_should_return_isbn_conexion_exception_when_isbn_not_valid_and_invalid_conexion(){
        String login = "ppss";
        String password = "ppss";
        String socio = "Pepe";
        String[] isbns = {"11111", "22222", "33333"};
        String esperado = "ISBN invalido:11111; CONEXION invalida; ";

        // Act
        EasyMock.expect(sut.compruebaPermisos(login, password, Usuario.BIBLIOTECARIO)).andReturn(true);
        EasyMock.expect(mockFactoria.getOperacionBO()).andReturn(mockOperacion);
        Assertions.assertDoesNotThrow(() -> {
            mockOperacion.operacionReserva(socio, isbns[0]);
            EasyMock.expectLastCall().andThrow(new IsbnInvalidoException());
            mockOperacion.operacionReserva(socio, isbns[1]);
            mockOperacion.operacionReserva(socio, isbns[2]);
            EasyMock.expectLastCall().andThrow(new JDBCException());
        });
        ctrl.replay();
        ReservaException excepcion = Assertions.assertThrows(ReservaException.class, () -> sut.realizaReserva(login, password, socio, isbns));

        // Assert
        Assertions.assertEquals(esperado, excepcion.getMessage());
        ctrl.verify();
    }
}
