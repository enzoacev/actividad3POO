import java.io.Serializable;

public class Asistente implements Serializable {

    // defino las variables
    private String nombreCompleto;
    private String correoElectronico;

    //datos iniciales al crear el asistente
    public Asistente(String nombreCompleto, String correoElectronico) {
        this.nombreCompleto = nombreCompleto;
        this.correoElectronico = correoElectronico;
    }

    //obtener la informacion guardada
    public String obtenerNombre() {
        return nombreCompleto;
    }

    public String obtenerCorreo() {
        return correoElectronico;
    }

    //formatea como se va a ver el texto cuando lo imprima en pantalla
    @Override
    public String toString() {
        return "- " + nombreCompleto + " (Correo: " + correoElectronico + ")";
    }
}