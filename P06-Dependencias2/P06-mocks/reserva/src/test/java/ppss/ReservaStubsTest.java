package ppss;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ppss.excepciones.IsbnInvalidoException;
import ppss.excepciones.JDBCException;
import ppss.excepciones.ReservaException;
import ppss.excepciones.SocioInvalidoException;

public class ReservaStubsTest {
    Reserva sut;
    FactoriaBOs stubFactoria;
    IOperacionBO stubOperacion;

    @BeforeEach
    public void setUp() {
        sut = EasyMock.partialMockBuilder(Reserva.class).addMockedMethod("compruebaPermisos").niceMock();
        stubFactoria = EasyMock.niceMock(FactoriaBOs.class);
        stubOperacion = EasyMock.niceMock(IOperacionBO.class);
        sut.setFactoria(stubFactoria);
    }

    @Test
    public void C1_realizaReserva_should_return_error_permisos_when_wrong_login(){
        // Arrange
        String login = "xxxx";
        String password = "xxxx";
        String socio = "Pepe";
        String[] isbns = {"33333"};
        String esperado = "ERROR de permisos; ";

        // Act
        EasyMock.expect(sut.compruebaPermisos(login, password, Usuario.BIBLIOTECARIO)).andStubReturn(false);
        EasyMock.replay(sut, stubFactoria, stubOperacion);
        ReservaException excepcion = Assertions.assertThrows(ReservaException.class,
                () -> sut.realizaReserva(login, password, socio, isbns));

        // Assert
        Assertions.assertEquals(esperado, excepcion.getMessage());
        EasyMock.verify(sut, stubFactoria, stubOperacion);
    }

    @Test
    public void C2_realizarReserva_should_not_return_exception_when_correct_login() {
        // Arrange
        String login = "ppss";
        String password = "ppss";
        String socio = "Pepe";
        String[] isbns = {"22222", "33333"};

        // Act
        EasyMock.expect(sut.compruebaPermisos(login, password, Usuario.BIBLIOTECARIO)).andStubReturn(true);
        EasyMock.expect(stubFactoria.getOperacionBO()).andStubReturn(stubOperacion);
        Assertions.assertAll(() -> {        // PARA STUBS CON EASYMOCK COMPROBAR LOS BUCLES CON ASSERTALL TODAS LAS LLAMADAS
            Assertions.assertDoesNotThrow(() -> stubOperacion.operacionReserva(socio, isbns[0]));
            Assertions.assertDoesNotThrow(() -> stubOperacion.operacionReserva(socio, isbns[1]));
        });
        EasyMock.replay(sut, stubFactoria, stubOperacion);

        // Assert
        Assertions.assertDoesNotThrow(() -> sut.realizaReserva(login, password, socio, isbns));
        EasyMock.verify(sut, stubFactoria, stubOperacion);
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
        EasyMock.expect(sut.compruebaPermisos(login, password, Usuario.BIBLIOTECARIO)).andStubReturn(true);
        EasyMock.expect(stubFactoria.getOperacionBO()).andStubReturn(stubOperacion);
        Assertions.assertAll(() -> {
            Assertions.assertDoesNotThrow(() -> stubOperacion.operacionReserva(socio, isbns[0]));
            EasyMock.expectLastCall().andThrow(new IsbnInvalidoException());
            Assertions.assertDoesNotThrow(() -> stubOperacion.operacionReserva(socio, isbns[1]));
            Assertions.assertDoesNotThrow(() -> stubOperacion.operacionReserva(socio, isbns[2]));
            EasyMock.expectLastCall().andThrow(new IsbnInvalidoException());
        });
        EasyMock.replay(sut, stubFactoria, stubOperacion);
        ReservaException excepcion = Assertions.assertThrows(ReservaException.class, () -> sut.realizaReserva(login, password, socio, isbns));

        // Assert
        Assertions.assertEquals(esperado, excepcion.getMessage());
        EasyMock.verify(sut, stubFactoria, stubOperacion);
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
        EasyMock.expect(sut.compruebaPermisos(login, password, Usuario.BIBLIOTECARIO)).andStubReturn(true);
        EasyMock.expect(stubFactoria.getOperacionBO()).andStubReturn(stubOperacion);
        Assertions.assertDoesNotThrow(() -> stubOperacion.operacionReserva(socio, isbns[0]));
        EasyMock.expectLastCall().andStubThrow(new SocioInvalidoException());
        EasyMock.replay(sut, stubFactoria, stubOperacion);
        ReservaException exception = Assertions.assertThrows(ReservaException.class, () -> sut.realizaReserva(login, password, socio, isbns));

        // Assert
        Assertions.assertEquals(esperado, exception.getMessage());
        EasyMock.verify(sut, stubFactoria, stubOperacion);
    }

    @Test
    public void C5_realizarReserva_should_return_isbn_conexion_exception_when_isbn_not_valid_and_invalid_conexion(){
        // Arrange
        String login = "ppss";
        String password = "ppss";
        String socio = "Pepe";
        String[] isbns = {"11111", "22222", "33333"};
        String esperado = "ISBN invalido:11111; CONEXION invalida; ";

        // Act
        EasyMock.expect(sut.compruebaPermisos(login, password, Usuario.BIBLIOTECARIO)).andStubReturn(true);
        EasyMock.expect(stubFactoria.getOperacionBO()).andStubReturn(stubOperacion);
        Assertions.assertAll(() -> {
            Assertions.assertDoesNotThrow(() -> stubOperacion.operacionReserva(socio, isbns[0]));
            EasyMock.expectLastCall().andThrow(new IsbnInvalidoException());
            Assertions.assertDoesNotThrow(() -> stubOperacion.operacionReserva(socio, isbns[1]));
            Assertions.assertDoesNotThrow(() -> stubOperacion.operacionReserva(socio, isbns[2]));
            EasyMock.expectLastCall().andThrow(new JDBCException());
        });
        EasyMock.replay(sut, stubFactoria, stubOperacion);
        ReservaException exception = Assertions.assertThrows(ReservaException.class, () -> sut.realizaReserva(login, password, socio, isbns));

        // Assert
        Assertions.assertEquals(esperado, exception.getMessage());
        EasyMock.verify(sut, stubFactoria, stubOperacion);
    }
}
