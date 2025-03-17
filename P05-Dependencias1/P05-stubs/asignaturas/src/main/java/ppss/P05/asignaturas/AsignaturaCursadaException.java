package ppss.P05.asignaturas;

public class AsignaturaCursadaException extends Exception {
    public AsignaturaCursadaException() throws AsignaturaIncorrectaException {
        throw new AsignaturaIncorrectaException();
    }
}
