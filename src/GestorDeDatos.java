import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class GestorDeDatos {

    // defino el nombre del archivo donde voy a guardar todo
    private String rutaDelArchivo = "eventos_guardados.txt";

    // recibe la lista entera y la sobreescribe en el archivo
    public void guardarEventos(ArrayList<Evento> listaParaGuardar) {
        try (ObjectOutputStream escritorDeArchivo = new ObjectOutputStream(new FileOutputStream(rutaDelArchivo))) {
            // aca es donde ocurre la magia y se guarda toda la lista de una sola vez
            escritorDeArchivo.writeObject(listaParaGuardar);
        } catch (IOException errorDeEscritura) {
            System.out.println("ocurrio un error al intentar guardar los datos en el archivo.");
        }
    }

    //va a buscar el archivo txt y rearma la lista en memoria
    public ArrayList<Evento> cargarEventos() {
        // creo una lista vacia por defecto
        ArrayList<Evento> listaRecuperada = new ArrayList<>();

        try (ObjectInputStream lectorDeArchivo = new ObjectInputStream(new FileInputStream(rutaDelArchivo))) {
            // leo el archivo y lo convierto a una lista de eventos
            listaRecuperada = (ArrayList<Evento>) lectorDeArchivo.readObject();
        } catch (IOException | ClassNotFoundException errorDeLectura) {
            // si el archivo no existe atrapo el error y devuelvo la lista vacia
        }

        return listaRecuperada;
    }
}