package ppss.matriculacion.dao;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("Integracion-Fase1")
public class AlumnoDAOTest {        // solo se ejecutan los unitarios si haces mvn test porque
    @Test                           // los tests de integración se lanzan con mvn verify, está por debajo
    public void test1() {           // en el ciclo de vida de maven
        assertTrue(true);
    }
}
