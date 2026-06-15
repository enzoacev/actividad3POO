import java.io.Serializable;
import java.util.ArrayList;

public class Evento implements Serializable {

    private String nombreDelEvento;
    private String fechaDelEvento;
    private String ubicacionDelEvento;
    private String descripcionDelEvento;

    // listas para guardar los objetos
    private ArrayList<Asistente> listaDeAsistentes;
    private ArrayList<Recurso> listaDeRecursos;


    public Evento(String nombreDelEvento, String fechaDelEvento, String ubicacionDelEvento, String descripcionDelEvento) {
        this.nombreDelEvento = nombreDelEvento;
        this.fechaDelEvento = fechaDelEvento;
        this.ubicacionDelEvento = ubicacionDelEvento;
        this.descripcionDelEvento = descripcionDelEvento;

        //inicializar las listas vacias
        this.listaDeAsistentes = new ArrayList<>();
        this.listaDeRecursos = new ArrayList<>();
    }

    //leer los datos del evento
    public String obtenerNombre() {
        return nombreDelEvento;
    }

    public String obtenerFecha() {
        return fechaDelEvento;
    }

    //cambiar los datos de un evento ya creado
    public void modificarDetalles(String nuevaFecha, String nuevaUbicacion, String nuevaDescripcion) {
        this.fechaDelEvento = nuevaFecha;
        this.ubicacionDelEvento = nuevaUbicacion;
        this.descripcionDelEvento = nuevaDescripcion;
    }

    // llena las listas con objetos nuevos
    public void agregarAsistente(Asistente nuevoAsistente) {
        listaDeAsistentes.add(nuevoAsistente);
    }

    public void agregarRecurso(Recurso nuevoRecurso) {
        listaDeRecursos.add(nuevoRecurso);
    }

    //recuperar las listas completas y poder mostrarlas
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