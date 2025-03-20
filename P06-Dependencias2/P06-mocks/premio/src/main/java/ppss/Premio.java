package ppss;

import java.util.Random;

public class Premio {
    private static final float PROBABILIDAD_PREMIO = 0.1f;
    public Random generador = new Random(System.currentTimeMillis());
    public ClienteWebService cliente = new ClienteWebService();

    // SUT
    public String compruebaPremio() {
        if (generaNumero() < PROBABILIDAD_PREMIO) {     // dependencia (SEAM)
            try {
                String premio = cliente.obtenerPremio();    // atributo publico -> facilmente asignable con un doble
                return "Premiado con " + premio;
            } catch (ClienteWebServiceException e) {
                return "No se ha podido obtener el premio";
            }
        } else {
            return "Sin premio";
        }
    }

    // Genera numero aleatorio entre 0 y 1
    public float generaNumero() {
        return generador.nextFloat();
    }
}