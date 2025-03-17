package ppss.P05.llamadas;

public class GestorLlamadasTestable extends GestorLlamadas {
    private Calendario calendario;

    public void setCalendario(Calendario calendario) {
        this.calendario = calendario;
    }
    @Override
    public Calendario getCalendario(){
        return calendario;
    }

}
