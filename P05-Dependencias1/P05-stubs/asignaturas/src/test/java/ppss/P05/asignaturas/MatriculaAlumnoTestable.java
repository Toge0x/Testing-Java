package ppss.P05.asignaturas;

public class MatriculaAlumnoTestable extends MatriculaAlumno {
    private OperacionStub stub;

    @Override
    public Operacion getOperacion() {
        if(stub != null){
            return stub;
        }else{
            return new Operacion();
        }
    }

    public void setOperacion(OperacionStub stub) {
        this.stub = stub;
    }
}
