package ppss.P05.asignaturas;

import java.util.ArrayList;

public class JustificanteMatricula {
    private String dni;
    private ArrayList<String> asignaturas;
    private ArrayList<String> errores;

    public String getDni() {
        return dni;
    }
    public void setDni(String dni) {
        this.dni = dni;
    }
    public ArrayList<String> getAsignaturas() {
        return asignaturas;
    }
    public void setAsignaturas(ArrayList<String> asignaturas) {
        this.asignaturas = asignaturas;
    }
    public ArrayList<String> getErrores() {
        return errores;
    }
    public void setErrores(ArrayList<String> errores) {
        this.errores = errores;
    }
}