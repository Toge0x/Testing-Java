package ppss.P05.asignaturas;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MatriculaAlumnoTest{
    @Test
    public void C1_validaAsignaturas_should_return_zz_no_existe_p1_ya_cursada_when_entradas_son_md_zz_fbd_p1(){
        // Arrange
        String dni = "00000000T";
        String[] asignaturas = {"MD", "ZZ", "FBD", "P1"};

        MatriculaAlumnoTestable sut = new MatriculaAlumnoTestable();
        OperacionStub stub = new OperacionStub();
        sut.setOperacion(stub);     // Inyección del stub

        List<String> asignaturasEsperadas = Arrays.asList("MD", "FBD");
        List<String> erroresEsperados = Arrays.asList("Asignatura ZZ no existe", "Asignatura P1 ya cursada");

        // Act
        JustificanteMatricula resultado = sut.validaAsignaturas(dni, asignaturas);

        // Assert
        assertEquals(dni, resultado.getDni());
        assertEquals(asignaturasEsperadas, resultado.getAsignaturas());
        assertEquals(erroresEsperados, resultado.getErrores());

    }

    @Test
    public void C2_validaAsignaturas_should_return_ppss_ada_p3_when_todas_entradas_validas(){
        // Arrange
        String dni = "00000000T";
        String[] asignaturas = {"PPSS", "ADA", "P3"};
        MatriculaAlumnoTestable sut = new MatriculaAlumnoTestable();
        OperacionStub stub = new OperacionStub();
        sut.setOperacion(stub);     // Inyección del stub
        List<String> asignaturasEsperadas = Arrays.asList("PPSS", "ADA", "P3");
        List<String> erroresEsperados = Arrays.asList();

        // Act
        JustificanteMatricula resultado = sut.validaAsignaturas(dni, asignaturas);

        // Assert
        assertEquals(dni, resultado.getDni());
        assertEquals(asignaturasEsperadas, resultado.getAsignaturas());
        assertEquals(erroresEsperados, resultado.getErrores());

    }
}
