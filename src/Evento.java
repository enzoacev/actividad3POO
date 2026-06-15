import java.io.Serializable;
import java.util.ArrayList;

// esta es la clase principal del modelo, junta toda la informacion
public class Evento implements Serializable {

    // atributos de texto simples para la informacion basica
    private String nombreDelEvento;
    private String fechaDelEvento;
    private String ubicacionDelEvento;
    private String descripcionDelEvento;

    // listas para guardar los objetos que creamos en las otras clases
    private ArrayList<Asistente> listaDeAsistentes;
    private ArrayList<Recurso> listaDeRecursos;

    // constructor que recibe la informacion basica
    public Evento(String nombreDelEvento, String fechaDelEvento, String ubicacionDelEvento, String descripcionDelEvento) {
        this.nombreDelEvento = nombreDelEvento;
        this.fechaDelEvento = fechaDelEvento;
        this.ubicacionDelEvento = ubicacionDelEvento;
        this.descripcionDelEvento = descripcionDelEvento;

        // es muy importante inicializar las listas vacias para que no tire error al agregar
        this.listaDeAsistentes = new ArrayList<>();
        this.listaDeRecursos = new ArrayList<>();
    }

    // metodos para leer los datos del evento
    public String obtenerNombre() {
        return nombreDelEvento;
    }

    public String obtenerFecha() {
        return fechaDelEvento;
    }

    // este metodo permite cambiar los datos de un evento ya creado
    public void modificarDetalles(String nuevaFecha, String nuevaUbicacion, String nuevaDescripcion) {
        this.fechaDelEvento = nuevaFecha;
        this.ubicacionDelEvento = nuevaUbicacion;
        this.descripcionDelEvento = nuevaDescripcion;
    }

    // metodos para ir llenando las listas con objetos nuevos
    public void agregarAsistente(Asistente nuevoAsistente) {
        listaDeAsistentes.add(nuevoAsistente);
    }

    public void agregarRecurso(Recurso nuevoRecurso) {
        listaDeRecursos.add(nuevoRecurso);
    }

    // metodos para recuperar las listas completas y poder mostrarlas
    public ArrayList<Asistente> obtenerAsistentes() {
        return listaDeAsistentes;
    }

    public ArrayList<Recurso> obtenerRecursos() {
        return listaDeRecursos;
    }

    // armo una cadena de texto para resumir de que trata el evento
    @Override
    public String toString() {
        return "Evento: " + nombreDelEvento + " | Fecha: " + fechaDelEvento + " | Ubicación: " + ubicacionDelEvento + " | Info: " + descripcionDelEvento;
    }
}