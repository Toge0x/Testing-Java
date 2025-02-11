package ppss.P01;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class EjemploTest {
     Ejemplo ejemplo;

    @BeforeEach
    public void setup() {
        ejemplo= new Ejemplo();
    }

    @Test
    void C1_fechaValida_should_be_true_when_date_is_29_2_2020() {
        //Preparamos los datos (Arrange)
        int dia = 29;
        int mes = 2;
        int anyo = 2020;
        boolean resultadoEsperado = true;
        //Ejecutamos (Act)
        boolean resultadoReal = ejemplo.fechaValida(dia,mes,anyo);
        assertEquals(resultadoEsperado, resultadoReal);
    }

    @Test
    void C1_fechaValida_should_be_false_when_date_is_31_4_2020() {  // no tiene 31 dias abril
        //Preparamos los datos (Arrange)
        int dia = 31;
        int mes = 4;
        int anyo = 2020;
        boolean resultadoEsperado = false;
        //Ejecutamos (Act)
        boolean resultadoReal = ejemplo.fechaValida(dia,mes,anyo);
        assertEquals(resultadoEsperado, resultadoReal);
    }

    @Test
    void C1_fechaValida_should_be_false_when_date_is_29_2_2021() {      // 2021 no es bisiesto
        //Preparamos los datos (Arrange)
        int dia = 29;
        int mes = 2;
        int anyo = 2021;
        boolean resultadoEsperado = false;
        //Ejecutamos (Act)
        boolean resultadoReal = ejemplo.fechaValida(dia,mes,anyo);
        assertEquals(resultadoEsperado, resultadoReal);
    }

    @Test
    void C1_fechaValida_should_be_false_when_date_is_29_2_2022() {      // 2022 no es bisiesto
        //Preparamos los datos (Arrange)
        int dia = 29;
        int mes = 2;
        int anyo = 2022;
        boolean resultadoEsperado = false;
        //Ejecutamos (Act)
        boolean resultadoReal = ejemplo.fechaValida(dia,mes,anyo);
        assertEquals(resultadoEsperado, resultadoReal);
    }

    @Test
    void C5_fechaValida_should_be_true_when_date_is_30_6_2023() {  // junio tiene 30 dias
        // Preparamos los datos (Arrange)
        int dia = 30;
        int mes = 6;
        int anyo = 2023;
        boolean resultadoEsperado = true;
        // Ejecutamos (Act)
        boolean resultadoReal = ejemplo.fechaValida(dia, mes, anyo);
        assertEquals(resultadoEsperado, resultadoReal);
    }

    @Test
    void C6_fechaValida_should_be_false_when_date_is_32_1_2023() {  // enero solo tiene 31 dias
        // Preparamos los datos (Arrange)
        int dia = 32;
        int mes = 1;
        int anyo = 2023;
        boolean resultadoEsperado = false;
        // Ejecutamos (Act)
        boolean resultadoReal = ejemplo.fechaValida(dia, mes, anyo);
        assertEquals(resultadoEsperado, resultadoReal);
    }

    @Test
    void C7_fechaValida_should_be_false_when_date_is_15_13_2023() {  // no existe el mes 13
        // Preparamos los datos (Arrange)
        int dia = 15;
        int mes = 13;
        int anyo = 2023;
        boolean resultadoEsperado = false;
        // Ejecutamos (Act)
        boolean resultadoReal = ejemplo.fechaValida(dia, mes, anyo);
        assertEquals(resultadoEsperado, resultadoReal);
    }

    @Test
    void C8_fechaValida_should_be_false_when_date_is_0_5_2023() {  // No existen días menores a 1
        // Preparamos los datos (Arrange)
        int dia = 0;
        int mes = 5;
        int anyo = 2023;
        boolean resultadoEsperado = false;
        // Ejecutamos (Act)
        boolean resultadoReal = ejemplo.fechaValida(dia, mes, anyo);
        assertEquals(resultadoEsperado, resultadoReal);
    }
}
