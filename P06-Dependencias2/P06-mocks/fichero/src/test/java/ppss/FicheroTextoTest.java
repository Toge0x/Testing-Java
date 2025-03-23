package ppss;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.IOException;

public class FicheroTextoTest {
    IMocksControl ctrl;
    FicheroTexto sut;
    FileReader mock;

    @BeforeEach
    public void setUp() {
        ctrl = EasyMock.createStrictControl();
        sut = EasyMock.partialMockBuilder(FicheroTexto.class).addMockedMethod("getFileReader").mock(ctrl);
        mock = ctrl.mock(FileReader.class);
    }

    @Test
    public void C1_contarCaracteres_should_return_exception_leer_archivo_when_cannot_read_fichero(){
        // Arrange
        String nombreFichero = "src/test/resources/ficheroC1.txt";
        String esperado = "src/test/resources/ficheroC1.txt (Error al leer el archivo)";

        // Act
        Assertions.assertDoesNotThrow(() -> EasyMock.expect(sut.getFileReader(nombreFichero)).andReturn(mock));
        Assertions.assertDoesNotThrow(() -> EasyMock.expect(mock.read()).andReturn((int) 'a').andReturn((int) 'b').andThrow(new IOException()));
        ctrl.replay();
        FicheroException excepcion = Assertions.assertThrows(FicheroException.class, () -> sut.contarCaracteres(nombreFichero));

        // Assert
        Assertions.assertEquals(esperado, excepcion.getMessage());
        ctrl.verify();
    }

    @Test
    public void C2_contarCaracteres_should_return_exception_cerrar_archivo_when_cannot_close_fichero(){
        // Arrange
        String nombreFichero = "src/test/resources/ficheroC2.txt";
        String esperado = "src/test/resources/ficheroC2.txt (Error al cerrar el archivo)";

        // Act
        Assertions.assertDoesNotThrow(() -> EasyMock.expect(sut.getFileReader(nombreFichero)).andReturn(mock));
        Assertions.assertDoesNotThrow(() -> EasyMock.expect(mock.read()).andReturn((int) 'a').andReturn((int) 'b').andReturn((int) 'c').andReturn(-1));
        Assertions.assertDoesNotThrow(() -> mock.close());
        EasyMock.expectLastCall().andThrow(new IOException());
        ctrl.replay();
        FicheroException excepcion = Assertions.assertThrows(FicheroException.class, () -> sut.contarCaracteres(nombreFichero));

        // Assert
        Assertions.assertEquals(esperado, excepcion.getMessage());
        ctrl.verify();
    }
}
