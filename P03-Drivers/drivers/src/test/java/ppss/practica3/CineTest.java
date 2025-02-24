//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package ppss.practica3;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;

@DisplayName("Tests asociados a la clase Cine")
public class CineTest {
    public CineTest() {
    }

    @Test
    void C1_reservaButacas_should_return_Exception_when_fila_empty_and_want_3() {
        Cine cine = new Cine();
        boolean[] asientos = new boolean[0];
        int solicitados = 3;
        Exception exception = (Exception)Assertions.assertThrows(ButacasException.class, () -> cine.reservaButacas(asientos, solicitados));
        Assertions.assertEquals("No se puede procesar la solicitud", exception.getMessage());
    }

    @Test
    void C2_reservaButacas_should_return_false_when_fila_empty_and_want_zero() {
        Cine cine = new Cine();
        boolean[] asientos = new boolean[]{false};
        int solicitados = 0;
        boolean resultado = cine.reservaButacas(asientos, solicitados);
        Assertions.assertFalse(resultado);
        Assertions.assertArrayEquals(new boolean[]{false}, asientos);
    }

    @Test
    void C3_reservaButacas_should_return_true_when_fila_has_3_seats_free_and_want_2() {
        Cine cine = new Cine();
        boolean[] asientos = new boolean[]{false, false, false, true, true};
        int solicitados = 2;
        boolean resultado = cine.reservaButacas(asientos, solicitados);
        Assertions.assertTrue(resultado);
        Assertions.assertArrayEquals(new boolean[]{true, true, false, true, true}, asientos);
    }

    @Test
    void C4_reservaButacas_should_return_false_when_no_free_seats_and_want_1() {
        Cine cine = new Cine();
        boolean[] asientos = new boolean[]{true, true, true};
        int solicitados = 1;
        boolean resultado = cine.reservaButacas(asientos, solicitados);
        Assertions.assertFalse(resultado);
        Assertions.assertArrayEquals(new boolean[]{true, true, true}, asientos);
    }

    private static Stream<Arguments> casosDePrueba() {
        return Stream.of(
                Arguments.of(false, 0, "fila has no seats"),
                Arguments.of(true, 2, "there are 2 free seats"),
                Arguments.of(false, 1, "there are 2 free seats")
        );
    }

    @DisplayName("reservaButacas_")
    @ParameterizedTest(name = "reservaButacas_{index} should be {1} when we want {2} and {3}")
    @MethodSource("casosDePrueba")
    @Tag("parametrizado")
    public void C5_reservaButacas(boolean expected, int solicitados, String message){
        // Arrange
        Cine cine = new Cine();
        boolean[] asientos = {false, false, false, true, true};
        // Act
        boolean resultado = cine.reservaButacas(asientos, solicitados);
        // Assert
        Assertions.assertEquals(expected, resultado, "reservaButacas_ should be " + expected + " when we want " + solicitados + " and " + message);
    }

}
