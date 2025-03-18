package ppss.P05.alquiler;

public class AlquilaCochesTestable extends AlquilaCoches {
    IService servicio;
    CalendarioStub calendarioStub;

    @Override
    public IService getServicio(){
        return servicio;
    }

    public void setServicio(IService servicio) {
        this.servicio = servicio;
    }

    public void setCalendario(Calendario calendario) {
        this.calendario = calendario;
    }

    @Override
    public Calendario getCalendario(){
        return calendarioStub;
    }
}
