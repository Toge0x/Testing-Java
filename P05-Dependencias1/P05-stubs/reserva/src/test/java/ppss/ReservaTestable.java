package ppss;

public class ReservaTestable extends Reserva {
    @Override
    public boolean compruebaPermisos(String login, String password, Usuario tipoUsu){
        return login.equals("ppss") && password.equals("ppss");
    }
}
