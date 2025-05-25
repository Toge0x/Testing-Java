package ppss.ejercicio1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Test_Sample_countValues {
    Sample sut;

    @BeforeEach
    void setUp() {
        sut = new Sample();
    }
    @Test
    public void C1_countValues_should_return_40_when_there_are_four_10_data_values(){
        int [] datos= {10,10,10,10};
        int resultado_esperado = 40;

        int resultado_real = sut.countValues(datos);
        Assertions.assertEquals(resultado_esperado,resultado_real);
    }

    @Test
    void C2_countValues_should_return_zero_when_value_is_under_of_range() {
        int[] data = {10, 20, 5, 40}; // ← el 5 esta fuera de rango (< 10)
        int esperado = 0;

        int result = sut.countValues(data);
        Assertions.assertEquals(esperado, result);
    }

    @Test
    void C3_countValues_should_return_zero_when_value_is_out_of_range() {
        int[] data = {10, 20, 90, 40}; // ← el 90 esta fuera de rango (> 80)
        int esperado = 0;

        int result = sut.countValues(data);
        Assertions.assertEquals(esperado, result);
    }

    @Test
    void C4_countValues_should_return_zero_when_array_is_empty() {
        int[] data = {};
        int esperado = 0;

        int result = sut.countValues(data);
        Assertions.assertEquals(esperado, result);
    }
}