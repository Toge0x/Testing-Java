package ppss.P05.alquiler;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class AlquilaCochesTest {

    @Test
    public void C1_calculaPrecio_should_return_75_when_festivo_falso_todos_los_dias(){
        // Arrange
        TipoCoche coche = TipoCoche.TURISMO;
        LocalDate fecha = LocalDate.of(2024, 5, 18);
        int ndias = 10;
        float esperado = 75;
        CalendarioStub calendarioStub = new CalendarioStub();
        ServicioStub servicioStub = new ServicioStub();
        AlquilaCochesTestable sut = new AlquilaCochesTestable();
        sut.setCalendario(calendarioStub);
        sut.setServicio(servicioStub);

        // Act
        Ticket resultado = Assertions.assertDoesNotThrow(() -> sut.calculaPrecio(coche, fecha, ndias));

        // Assert
        Assertions.assertEquals(esperado, resultado.getPrecioFinal());
    }

    @Test
    public void C2_calculaPrecio_should_return_62_5_when_festivo_dia_20_y_24(){
        // Arrange
        TipoCoche coche = TipoCoche.CARAVANA;
        LocalDate fecha = LocalDate.of(2024, 6, 19);
        int ndias = 7;
        float esperado = 62.5f;
        CalendarioStub calendarioStub = new CalendarioStub();
        ServicioStub servicioStub = new ServicioStub();
        AlquilaCochesTestable sut = new AlquilaCochesTestable();
        sut.setCalendario(calendarioStub);
        sut.setServicio(servicioStub);

        // Act
        Ticket resultado = Assertions.assertDoesNotThrow(() -> sut.calculaPrecio(coche, fecha, ndias));

        // Assert
        Assertions.assertEquals(esperado, resultado.getPrecioFinal());
    }

    @Test
    public void C3_calculaPrecio_should_return_exceptions_when_dia_18_21_y_22(){
        // Arrange
        TipoCoche coche = TipoCoche.TURISMO;
        LocalDate fecha = LocalDate.of(2024, 4, 17);
        int ndias = 8;

        CalendarioStub calendarioStub = new CalendarioStub();
        ServicioStub servicioStub = new ServicioStub();
        AlquilaCochesTestable sut = new AlquilaCochesTestable();
        sut.setCalendario(calendarioStub);
        sut.setServicio(servicioStub);

        // Act
        MensajeException excepcion = Assertions.assertThrows(MensajeException.class, () -> sut.calculaPrecio(coche, fecha, ndias));
        // Assert
        Assertions.assertEquals("Error en dia: 2024-04-18; Error en dia: 2024-04-21; Error en dia: 2024-04-22; ", excepcion.getMessage());
    }
}
