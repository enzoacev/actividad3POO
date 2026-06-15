import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

public class SistemaDeEventos {

    public static void main(String[] args) {

        // instancio el gestor para recuperar los datos si ya habia guardados
        GestorDeDatos gestorDeArchivos = new GestorDeDatos();
        ArrayList<Evento> listaDeEventos = gestorDeArchivos.cargarEventos();

        //ventana principal de la aplicacion
        JFrame ventanaPrincipal = new JFrame("Panel de Planificación y Gestión de Eventos");
        ventanaPrincipal.setSize(800, 600);
        ventanaPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // uso borderlayout para poner cosas arriba, a la izquierda y al centro
        ventanaPrincipal.setLayout(new BorderLayout());

        // armo el panel
        JPanel panelFormulario = new JPanel(new GridLayout(4, 2, 5, 5));
        JTextField campoNombre = new JTextField();
        JTextField campoFecha = new JTextField();
        JTextField campoUbicacion = new JTextField();
        JTextField campoDescripcion = new JTextField();

        // agrego las etiquetas
        panelFormulario.add(new JLabel(" Nombre del Evento:"));
        panelFormulario.add(campoNombre);
        panelFormulario.add(new JLabel(" Fecha (Usar AAAA-MM-DD para el calendario):"));
        panelFormulario.add(campoFecha);
        panelFormulario.add(new JLabel(" Ubicación:"));
        panelFormulario.add(campoUbicacion);
        panelFormulario.add(new JLabel(" Descripción:"));
        panelFormulario.add(campoDescripcion);

        //panel de la izquierda
        JPanel panelBotones = new JPanel(new GridLayout(8, 1, 5, 5));
        JButton botonCrearEvento = new JButton("1. Guardar Evento Nuevo");
        JButton botonModificarEvento = new JButton("2. Modificar Evento");
        JButton botonRegistrarAsistente = new JButton("3. Registrar Asistente");
        JButton botonGestionarRecursos = new JButton("4. Asignar Recursos");
        JButton botonVerCalendario = new JButton("5. Ver Calendario (Pasados/Futuros)");
        JButton botonNotificaciones = new JButton("6. Enviar Notificaciones");
        JButton botonAnalisis = new JButton("7. Análisis de Participación");
        JButton botonVerDetalles = new JButton("8. Ver Lista Completa");

        panelBotones.add(botonCrearEvento);
        panelBotones.add(botonModificarEvento);
        panelBotones.add(botonRegistrarAsistente);
        panelBotones.add(botonGestionarRecursos);
        panelBotones.add(botonVerCalendario);
        panelBotones.add(botonNotificaciones);
        panelBotones.add(botonAnalisis);
        panelBotones.add(botonVerDetalles);

        //mostrar los resultados
        JTextArea areaDeVisualizacion = new JTextArea();
        areaDeVisualizacion.setEditable(false);
        JScrollPane panelDeDesplazamiento = new JScrollPane(areaDeVisualizacion);

        ventanaPrincipal.add(panelFormulario, BorderLayout.NORTH);
        ventanaPrincipal.add(panelBotones, BorderLayout.WEST);
        ventanaPrincipal.add(panelDeDesplazamiento, BorderLayout.CENTER);

        // boton 1: tomo el texto de las cajitas, creo el evento y lo guardo
        botonCrearEvento.addActionListener(e -> {
            if (!campoNombre.getText().isEmpty()) {
                Evento nuevoEvento = new Evento(campoNombre.getText(), campoFecha.getText(), campoUbicacion.getText(), campoDescripcion.getText());
                listaDeEventos.add(nuevoEvento);
                gestorDeArchivos.guardarEventos(listaDeEventos);

                areaDeVisualizacion.setText("Evento creado exitosamente.\n" + nuevoEvento.toString());

                // borro el texto de las cajitas para que quede prolijo
                campoNombre.setText("");
                campoFecha.setText("");
                campoUbicacion.setText("");
                campoDescripcion.setText("");
            }
        });

        // boton 2: armo una lista desplegable para elegir que evento modificar
        botonModificarEvento.addActionListener(e -> {
            if (listaDeEventos.isEmpty()) {
                JOptionPane.showMessageDialog(ventanaPrincipal, "No hay eventos guardados todavía.");
                return; // salgo del metodo si no hay eventos
            }

            // extraigo solo los nombres para pasarselos al cuadrito de seleccion
            String[] opcionesDeEventos = new String[listaDeEventos.size()];
            for (int i = 0; i < listaDeEventos.size(); i++) {
                opcionesDeEventos[i] = listaDeEventos.get(i).obtenerNombre();
            }

            // muestro la lista desplegable
            String eventoSeleccionado = (String) JOptionPane.showInputDialog(ventanaPrincipal, "Seleccione el Evento a modificar:", "Modificar", JOptionPane.QUESTION_MESSAGE, null, opcionesDeEventos, opcionesDeEventos[0]);

            // si el usuario eligio algo y no cancelo
            if (eventoSeleccionado != null) {
                for (Evento eventoActual : listaDeEventos) {
                    if (eventoActual.obtenerNombre().equals(eventoSeleccionado)) {
                        // pido los nuevos datos
                        String nuevaFecha = JOptionPane.showInputDialog("Nueva Fecha (AAAA-MM-DD):", eventoActual.obtenerFecha());
                        String nuevaUbicacion = JOptionPane.showInputDialog("Nueva Ubicación:");
                        String nuevaDescripcion = JOptionPane.showInputDialog("Nueva Descripción:");

                        // aplico los cambios y guardo en el txt
                        eventoActual.modificarDetalles(nuevaFecha, nuevaUbicacion, nuevaDescripcion);
                        gestorDeArchivos.guardarEventos(listaDeEventos);
                        areaDeVisualizacion.setText("El evento fue modificado con éxito.");
                    }
                }
            }
        });

        // boton 3: elegir evento de la lista y agregarle una persona
        botonRegistrarAsistente.addActionListener(e -> {
            if (listaDeEventos.isEmpty()) {
                JOptionPane.showMessageDialog(ventanaPrincipal, "No hay eventos para agregar asistentes.");
                return;
            }

            String[] opcionesDeEventos = new String[listaDeEventos.size()];
            for (int i = 0; i < listaDeEventos.size(); i++) {
                opcionesDeEventos[i] = listaDeEventos.get(i).obtenerNombre();
            }

            String eventoSeleccionado = (String) JOptionPane.showInputDialog(ventanaPrincipal, "Seleccione el Evento:", "Registrar Asistente", JOptionPane.QUESTION_MESSAGE, null, opcionesDeEventos, opcionesDeEventos[0]);

            if (eventoSeleccionado != null) {
                for (Evento eventoActual : listaDeEventos) {
                    if (eventoActual.obtenerNombre().equals(eventoSeleccionado)) {
                        // creo un asistente con los datos que pido en ventanas
                        Asistente nuevoAsistente = new Asistente(
                                JOptionPane.showInputDialog("Nombre del Asistente:"),
                                JOptionPane.showInputDialog("Correo Electrónico:")
                        );
                        // guardo en la memoria
                        eventoActual.agregarAsistente(nuevoAsistente);
                        gestorDeArchivos.guardarEventos(listaDeEventos);
                        areaDeVisualizacion.setText("Asistente guardado en " + eventoActual.obtenerNombre());
                    }
                }
            }
        });

        // boton 4: asignar catering o salon a un evento
        botonGestionarRecursos.addActionListener(e -> {
            if (listaDeEventos.isEmpty()) return;

            String[] opcionesDeEventos = new String[listaDeEventos.size()];
            for (int i = 0; i < listaDeEventos.size(); i++) {
                opcionesDeEventos[i] = listaDeEventos.get(i).obtenerNombre();
            }

            String eventoSeleccionado = (String) JOptionPane.showInputDialog(ventanaPrincipal, "Seleccione el Evento:", "Asignar Recurso", JOptionPane.QUESTION_MESSAGE, null, opcionesDeEventos, opcionesDeEventos[0]);

            if (eventoSeleccionado != null) {
                for (Evento eventoActual : listaDeEventos) {
                    if (eventoActual.obtenerNombre().equals(eventoSeleccionado)) {
                        Recurso nuevoRecurso = new Recurso(
                                JOptionPane.showInputDialog("Tipo de Recurso (ej. Catering, Salón, Proyector):"),
                                JOptionPane.showInputDialog("Descripción / Cantidad:")
                        );
                        eventoActual.agregarRecurso(nuevoRecurso);
                        gestorDeArchivos.guardarEventos(listaDeEventos);
                        areaDeVisualizacion.setText("Recurso asignado correctamente.");
                    }
                }
            }
        });

        // boton 5: comparar fechas de manera simple como si fueran palabras
        botonVerCalendario.addActionListener(e -> {
            String fechaDeHoy = "2026-06-15";
            areaDeVisualizacion.setText("--- CALENDARIO DE EVENTOS ---\n\n");

            areaDeVisualizacion.append(">> EVENTOS FUTUROS Y DE HOY:\n");
            for (Evento eventoActual : listaDeEventos) {
                // si la fecha ingresada es mayor o igual a hoy
                if (eventoActual.obtenerFecha().compareTo(fechaDeHoy) >= 0) {
                    areaDeVisualizacion.append(eventoActual.toString() + "\n");
                }
            }

            areaDeVisualizacion.append("\n>> EVENTOS PASADOS:\n");
            for (Evento eventoActual : listaDeEventos) {
                // si la fecha es menor que hoy
                if (eventoActual.obtenerFecha().compareTo(fechaDeHoy) < 0) {
                    areaDeVisualizacion.append(eventoActual.toString() + "\n");
                }
            }
        });

        // boton 6: mandar correos a todos los de un evento
        botonNotificaciones.addActionListener(e -> {
            if (listaDeEventos.isEmpty()) return;

            String[] opcionesDeEventos = new String[listaDeEventos.size()];
            for (int i = 0; i < listaDeEventos.size(); i++) {
                opcionesDeEventos[i] = listaDeEventos.get(i).obtenerNombre();
            }

            String eventoSeleccionado = (String) JOptionPane.showInputDialog(ventanaPrincipal, "Enviar recordatorio para el evento:", "Notificaciones", JOptionPane.QUESTION_MESSAGE, null, opcionesDeEventos, opcionesDeEventos[0]);

            if (eventoSeleccionado != null) {
                areaDeVisualizacion.setText("--- ENVIANDO NOTIFICACIONES ---\n\n");
                for (Evento eventoActual : listaDeEventos) {
                    if (eventoActual.obtenerNombre().equals(eventoSeleccionado)) {
                        // recorro la lista de personas y simulo que mando un mail
                        for (Asistente asistenteActual : eventoActual.obtenerAsistentes()) {
                            areaDeVisualizacion.append("Enviando correo a: " + asistenteActual.obtenerCorreo() + "...\n");
                        }
                        areaDeVisualizacion.append("\n¡Todos los correos electrónicos fueron enviados!");
                    }
                }
            }
        });

        // boton 7: cuenta cuantas cosas tiene cada evento
        botonAnalisis.addActionListener(e -> {
            areaDeVisualizacion.setText("--- INFORME DE ANÁLISIS DE EVENTOS ---\n\n");
            for (Evento eventoActual : listaDeEventos) {
                areaDeVisualizacion.append("Evento: " + eventoActual.obtenerNombre() + "\n");
                // cuento el tamaño de las listas para dar estadisticas
                areaDeVisualizacion.append("Total de Asistentes Confirmados: " + eventoActual.obtenerAsistentes().size() + "\n");
                areaDeVisualizacion.append("Total de Recursos Invertidos: " + eventoActual.obtenerRecursos().size() + "\n");
                areaDeVisualizacion.append("Feedback general: Excelente convocatoria.\n");
                areaDeVisualizacion.append("-------------------------\n");
            }
        });

        // boton 8: limpia la pantalla y muestra todo lo que hay cargado
        botonVerDetalles.addActionListener(e -> {
            areaDeVisualizacion.setText("--- LISTA DETALLADA ---\n\n");
            for (Evento eventoActual : listaDeEventos) {
                areaDeVisualizacion.append(eventoActual.toString() + "\n");
                areaDeVisualizacion.append("  Recursos: " + eventoActual.obtenerRecursos().toString() + "\n");
                areaDeVisualizacion.append("  Asistentes: " + eventoActual.obtenerAsistentes().toString() + "\n\n");
            }
        });

        // toda la ventana sea visible al usuario
        ventanaPrincipal.setVisible(true);
    }
}