package ppss;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.easymock.EasyMock;
import java.io.FileReader;
import java.io.IOException;

public class FicheroTextoTest {
    FicheroTexto ficheroPM;
    FileReader fileReaderMock;

    @BeforeEach
    public void setup() {
        ficheroPM = EasyMock.partialMockBuilder(FicheroTexto.class).addMockedMethod("getFileReader").mock();
        fileReaderMock = EasyMock.mock(FileReader.class);
    }

    @Test
    public void C1_contarCaracteres_should_return_exception_error_leer_fichero_when_fichero_is_ficheroC1() {
        // Arrange
        String nombreFichero = "src/test/resources/ficheroC1.txt";
        String esperado = "src/test/resources/ficheroC1.txt (Error al leer el archivo)";

        // Act
        Assertions.assertDoesNotThrow(() -> EasyMock.expect(fileReaderMock.read())
                .andReturn((int) 'a')
                .andReturn((int) 'b')
                .andThrow(new IOException()));
        Assertions.assertDoesNotThrow(() -> EasyMock.expect(ficheroPM.getFileReader(nombreFichero)).andReturn(fileReaderMock));
        EasyMock.replay(fileReaderMock, ficheroPM);

        FicheroException exception = Assertions.assertThrows(FicheroException.class, () -> ficheroPM.contarCaracteres(nombreFichero));

        // Assert
        Assertions.assertEquals(esperado, exception.getMessage());
        EasyMock.verify(fileReaderMock, ficheroPM);
    }

    @Test
    public void C2_contarCaracteres_should_return_exception_error_cerrar_fichero_when_fichero_is_ficheroC2() {
        // Arrange
        String nombreFichero = "src/test/resources/ficheroC2.txt";
        String esperado = "src/test/resources/ficheroC2.txt (Error al cerrar el archivo)";

        // Act
        Assertions.assertDoesNotThrow(() -> EasyMock.expect(ficheroPM.getFileReader(nombreFichero)).andReturn(fileReaderMock));
        Assertions.assertDoesNotThrow(() -> EasyMock.expect(fileReaderMock.read())
                .andReturn((int) 'a')
                .andReturn((int) 'b')
                .andReturn((int) 'c')
                .andReturn(-1));
        //Assertions.assertDoesNotThrow(() -> EasyMock.expect(fileReaderMock.close()).andThrow(new IOException()));   // Error de tipo
        Assertions.assertDoesNotThrow(() -> fileReaderMock.close());
        EasyMock.expectLastCall().andThrow(new IOException());                              // quitamos el problema del tipo
        EasyMock.replay(fileReaderMock, ficheroPM);
        FicheroException excepcion = Assertions.assertThrows(FicheroException.class, () -> ficheroPM.contarCaracteres(nombreFichero));  // OJO! capturar la excepción después del replay

        // Assert
        Assertions.assertEquals(esperado, excepcion.getMessage());
        EasyMock.verify(fileReaderMock, ficheroPM);
    }
}
