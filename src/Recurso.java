import java.io.Serializable;

public class Recurso implements Serializable {

    private String tipoDeRecurso;
    private String descripcionDelRecurso;

    //inicializo los valores
    public Recurso(String tipoDeRecurso, String descripcionDelRecurso) {
        this.tipoDeRecurso = tipoDeRecurso;
        this.descripcionDelRecurso = descripcionDelRecurso;
    }

    // devuelvo texto para mostrar en la interfaz
    @Override
    public String toString() {
        return tipoDeRecurso + ": " + descripcionDelRecurso;
    }
}