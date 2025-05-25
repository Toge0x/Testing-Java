package ppss.ejercicio1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Test_Sample_isValid {
    Sample sut;

    @BeforeEach
    void setUp() {
        sut = new Sample();
    }

    @Test
    public void C1_isValid_should_return_true_when_all_data_values_are_valid(){
        int [] datos= {10,10,10,10};
        boolean resultado_esperado = true;

        boolean resultado_real = sut.isValid(datos);
        Assertions.assertEquals(resultado_esperado,resultado_real);
    }

    @Test
    public void C2_isValid_should_return_false_when_array_is_empty(){
        int[] data = new int[0]; // array vacío
        boolean result = sut.isValid(data);
        Assertions.assertFalse(result); // esperamos false
    }

    @Test
    public void C3_isValid_should_return_false_when_value_out_of_range(){
        int[] data = {20, 30, 100}; // array con valor fuera de rango por encima
        boolean result = sut.isValid(data);
        Assertions.assertFalse(result); // esperamos false
    }

    @Test
    public void C4_isValid_should_return_false_when_value_under_of_range(){
        int[] data = {3}; // array con valor por debajo
        boolean result = sut.isValid(data);
        Assertions.assertFalse(result); // esperamos false
    }

}