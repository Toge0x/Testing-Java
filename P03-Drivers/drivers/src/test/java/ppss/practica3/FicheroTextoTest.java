package ppss.practica3;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;
import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;

@DisplayName("Test asociados a la clase FicheroTexto")
public class FicheroTextoTest {
    public FicheroTextoTest() {}

    @Test
    public void C1_contarCaracteres_should_return_Exception_when_file_does_not_exist() {
        // Arange
        FicheroTexto ficheroTexto = new FicheroTexto();
        String nombreFichero = "ficheroC1.txt";
        // Act
        Exception exception = (Exception) Assertions.assertThrows(FicheroException.class,
                () -> ficheroTexto.contarCaracteres(nombreFichero));
        // Assert
        Assertions.assertEquals("ficheroC1.txt (No existe el archivo o el directorio)", exception.getMessage());
    }

    @Test
    public void C2_contarCaracteres_should_return_3_when_file_has_3_chars(){
        // Arange
        FicheroTexto ficheroTexto = new FicheroTexto();
        String nombreFichero = "src/test/resources/ficheroCorrecto.txt";
        // Act
        int resultado = assertDoesNotThrow(() -> ficheroTexto.contarCaracteres(nombreFichero));
        // Assert
        Assertions.assertEquals(3, resultado);
    }

    @Test
    @Tag("excluido")
    public void C3_contarCaracteres_should_return_Exception_when_file_cannot_be_read() {
        // Arange
        FicheroTexto fichero = new FicheroTexto();
        String nombreFichero = "src/test/resources/ficheroC3.txt";
        // Act
        Exception exception = assertThrows(FicheroException.class, () -> {
            fichero.contarCaracteres(nombreFichero);
        });
        // Assert
        assertEquals("ficheroC3.txt (Error al leer el archivo)", exception.getMessage());
    }

    @Test
    @Tag("excluido")
    public void C4_contarCaracteres_should_return_Exception_when_file_cannot_be_closed() {
        // Arange
        FicheroTexto fichero = new FicheroTexto();
        String nombreFichero = "src/test/resources/ficheroC4.txt";
        // Act
        Exception exception = assertThrows(FicheroException.class, () -> {
            fichero.contarCaracteres(nombreFichero);
        });
        // Assert
        assertEquals("ficheroC4.txt (Error al cerrar el archivo)", exception.getMessage());
    }
}
