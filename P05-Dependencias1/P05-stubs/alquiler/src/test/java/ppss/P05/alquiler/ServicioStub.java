package ppss.P05.alquiler;

public class ServicioStub extends Servicio {
    @Override
    public float consultaPrecio(TipoCoche tipo) {
        float precio = 10.0f;
        return precio;
    }
}
