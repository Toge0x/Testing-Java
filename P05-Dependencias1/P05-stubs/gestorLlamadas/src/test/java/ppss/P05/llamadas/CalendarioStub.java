package ppss.P05.llamadas;

public class CalendarioStub extends Calendario {
    private int hora;

    public void setHoraActual(int hora) {
        this.hora = hora;
    }

    @Override
    public int getHoraActual() {
        return hora;
    }
}
