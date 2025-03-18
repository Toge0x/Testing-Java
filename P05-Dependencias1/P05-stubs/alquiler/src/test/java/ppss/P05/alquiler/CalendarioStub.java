package ppss.P05.alquiler;

import java.time.LocalDate;

public class CalendarioStub extends Calendario{

    @Override
    public boolean es_festivo(LocalDate fecha) throws CalendarioException {
        if (fecha.isEqual(LocalDate.of(2024, 6, 20)) ||
                fecha.isEqual(LocalDate.of(2024, 6, 24))) {  // festivos
            return true;
        } else if (fecha.isEqual(LocalDate.of(2024, 4, 18)) ||
                fecha.isEqual(LocalDate.of(2024, 4, 21)) ||
                fecha.isEqual(LocalDate.of(2024, 4, 22))) {  // fecha con excepcion
            throw new CalendarioException();
        } else {
            return false;  // no son festivos
        }
    }

}
