package ppss.P05.llamadas;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GestorLlamadasTest {

    @Test
    public void C1_should_return_207_when_horario_diurno(){
        // Arrange
        int horas = 12;
        int minutos = 10;
        int resultadoEsperado = 207;
        GestorLlamadasTestable g = new GestorLlamadasTestable();
        CalendarioStub stub = new CalendarioStub();
        stub.setHoraActual(horas);
        g.setCalendario(stub);

        // Act
        double resultado = g.calculaConsumo(minutos);

        // Assert
        assertEquals(resultadoEsperado, resultado);
    }

    @Test
    public void C2_should_return_122_when_horario_nocturno(){
        // Arrange
        int horas = 21;
        int minutos = 10;
        int resultadoEsperado = 122;
        GestorLlamadasTestable g = new GestorLlamadasTestable();
        CalendarioStub stub = new CalendarioStub();
        stub.setHoraActual(horas);
        g.setCalendario(stub);

        // Act
        double resultado = g.calculaConsumo(minutos);

        // Assert
        assertEquals(resultadoEsperado, resultado);
    }
}
