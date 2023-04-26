package Controladores;

import java.net.URL;
import java.util.ArrayList;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.layout.VBox;
import java.util.Random;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseButton;
import javafx.scene.control.CheckBox;

public class JuegoMemoria {
    // private int n=0;

    private Tablero tablero;
    private int tamannoFilas;
    private int tamannoColumnas;
    private String ruta = getClass().getResource("/Imagenes/SignoPreguntabien.png").toExternalForm();
    private Image cartaImagenVuelta = new Image(ruta);
// crono variables
    private IntegerProperty tiempoTranscurrido = new SimpleIntegerProperty(0);// para almacenar el tiempo transcurrido 
    private Timeline cronometro;
    private int cantidadSegundos;

    private int jugador1Puntaje;
    private int jugador2Puntaje;
    private String jugador1Nombre = "JUGADOR 1";
    private String jugador2Nombre = "JUGADOR 2";
    private String Jugador1vsC = "JUGADOR HUMANO";
    private boolean turnoJugador1;
    private boolean modoHumanoVsHumano = true;
    private Label jugador1Label;
    private Label jugador2Label;
    private boolean puntosExtra = true;
    private boolean puntosMenos = false;
    private boolean mismoJugador = false;
    private boolean modoTrio = false;
    private int grupoCartas;
    private ArrayList<Carta> cartasSeleccionadas;
    private Stage ventanaRevision;
    private boolean jugadorAutomatico = false;
    private Random random = new Random();
    private JugadorComputador jugadorComputador;
    private int nivelIA;

    private enum Resultado {
        GANADOR_JUGADOR1, GANADOR_JUGADOR2, EMPATE, JUEGO_EN_PROGRESO
    }

    //Cronometro
    private void iniciarCronometro(int pduracionSegundos) {
        jugador1Label.setText(jugador1Nombre + " :" + jugador1Puntaje + "-> Turno actual");

        Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.seconds(1),
                        event -> {
                            tiempoTranscurrido.set(tiempoTranscurrido.get() + 1);
                            if (tiempoTranscurrido.get() >= pduracionSegundos) {
                                //detener el tiempo, mostrar panatallla

                            }
                        }
                )
        );
        timeline.setCycleCount(Timeline.INDEFINITE); // Cronómetro continuo
        timeline.play();
    }

    public JuegoMemoria() {
    }

    public Tablero getTablero() {
        return tablero;
    }

    public void mostrarJuego(Stage primaryStage, int ptamannoFilas, int ptamannoColumnas, int pcantidadSegundos, boolean pModoHumanoVsHumano, String pJugador1Nombre, String pJugador2Nombre, boolean pPuntosExtra, boolean pmodoTrio, int pnivelIA) {
        primaryStage.setTitle("Juego Memoria");
        tamannoFilas = ptamannoFilas;
        tamannoColumnas = ptamannoColumnas;

        this.modoTrio = pmodoTrio;
        this.cantidadSegundos = pcantidadSegundos; // Actualizar la variable cantidadSegundos
        this.grupoCartas = pmodoTrio ? 3 : 2;
        this.cartasSeleccionadas = new ArrayList<>();
        this.modoHumanoVsHumano = pModoHumanoVsHumano;
        this.jugador1Nombre = pJugador1Nombre;
        this.jugador2Nombre = pJugador2Nombre;
        this.jugador1Puntaje = 0;
        this.jugador2Puntaje = 0;
        this.turnoJugador1 = true;
        this.puntosExtra = pPuntosExtra;
        this.modoTrio = pmodoTrio;
        this.jugadorAutomatico = !pModoHumanoVsHumano;
        this.nivelIA = pnivelIA;

        String[] cartasImagenes = new String[tamannoFilas * tamannoColumnas];//Crea la baraja de cartas

        for (int i = 1; i < (tamannoFilas * tamannoColumnas) / 2 + 1; i++) {

            String ruta = getClass().getResource("/Imagenes/" + i + ".png").toExternalForm();
            cartasImagenes[i - 1] = ruta;
            System.out.println(i);
        }

        tablero = new Tablero(tamannoFilas, tamannoColumnas, cartasImagenes, grupoCartas);
        jugadorComputador = new JugadorComputador(tablero, tamannoFilas, tamannoColumnas, nivelIA);
        // Create the game board layout
        GridPane cuadricula = new GridPane();

        for (int fila = 0; fila < tamannoFilas; fila++) {
            for (int col = 0; col < tamannoColumnas; col++) {
                Carta carta = tablero.getCarta(fila, col);
                ImageView imageView = carta.getVistaImagen();
                imageView.setImage(cartaImagenVuelta);
                imageView.setUserData(carta);
                imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, this::handleCardClick);
                cuadricula.add(imageView, col, fila);
            }
        }

        Label tiempoTranscurridoLabel = new Label();
        tiempoTranscurridoLabel.textProperty().bind(tiempoTranscurrido.asString());

        Label modoJuegoLabel = new Label();
        if (pmodoTrio) {
            modoJuegoLabel.setText("Modo de juego: Trio");
        } else {
            modoJuegoLabel.setText("Modo de juego: Parejas");
        }

        CheckBox verTodasCartas = new CheckBox("Modo Revision");
        verTodasCartas.setSelected(false);
        verTodasCartas.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                // Mostrar ventana de revisión
                mostrarRevision();
            } else {
                // Ocultar ventana de revisión
                if (ventanaRevision != null) {
                    ventanaRevision.hide();
                }
            }
        });

        // Add the label to the scene
        VBox vbox = new VBox();
        jugador1Label = new Label(modoHumanoVsHumano ? (jugador1Nombre + ": " + jugador1Puntaje) : (Jugador1vsC + ": " + jugador1Puntaje));
        jugador2Label = new Label(modoHumanoVsHumano ? (jugador2Nombre + ": " + jugador2Puntaje) : ("COMPUTADOR" + ": " + jugador2Puntaje));
        Label tipoJugadores = new Label(modoHumanoVsHumano ? " Modo Humano vs Humano" : "Modo Humano vs Computador");

        vbox.getChildren().addAll(tipoJugadores, modoJuegoLabel, jugador1Label, jugador2Label, verTodasCartas, cuadricula, tiempoTranscurridoLabel);

        primaryStage.setScene(new Scene(vbox));
        primaryStage.show();

        iniciarCronometro(cantidadSegundos); // Llamar al cronómetro con la variable actualizada

    }

    private void jugarComputador(int nivelIA) {

        Carta carta1, carta2;

        switch (nivelIA) {
            case 1: // Nivel fácil: no se realiza ninguna mejora en la lógica de selección
                do {
                    int[] posicionCarta1 = new int[2];
                    carta1 = jugadorComputador.elegirCarta(posicionCarta1);

                    int[] posicionCarta2 = new int[2];
                    carta2 = jugadorComputador.elegirCarta(posicionCarta2);

                } while (carta1.esParejaEncontrada() || cartasSeleccionadas.contains(carta1) || carta1 == carta2);
//        int valor1= carta1.getValor();
//        int valor2= carta2.getValor();

                ejecutarClickEnCarta(carta1);
                ejecutarClickEnCarta(carta2);

                break;

            case 2:
                // Lista de las cartas vistas por el computador
                ArrayList<Carta> cartasVistas = new ArrayList<>();

                do {
                    // Si hay alguna carta vista que no haya encontrado su pareja, elegirla
                    for (Carta carta : cartasVistas) {
                        if (!carta.esParejaEncontrada()) {
                            carta1 = carta;
                            carta2 = buscarParejaParaCarta(carta1);
                            if (carta2 != null) {
                                ejecutarClickEnCarta(carta1);
                                ejecutarClickEnCarta(carta2);
                                return;
                            }
                        }
                    }

                    // Si no hay ninguna carta vista por encontrar pareja, elegir dos cartas al azar
                    int[] posicionCarta1 = new int[2];
                    carta1 = jugadorComputador.elegirCarta(posicionCarta1);

                    int[] posicionCarta2 = new int[2];
                    carta2 = jugadorComputador.elegirCarta(posicionCarta2);
                    // Guardar las cartas elegidas en la lista de cartas vistas
                    if (!cartasVistas.contains(carta1)) {
                        cartasVistas.add(carta1);
                    }
                    if (!cartasVistas.contains(carta2)) {
                        cartasVistas.add(carta2);
                    }
                } while (carta1.esParejaEncontrada() || cartasSeleccionadas.contains(carta1) || carta1 == carta2);

                ejecutarClickEnCarta(carta1);
                ejecutarClickEnCarta(carta2);
                break;

            case 3: // Nivel 3
                System.out.println("Caso tres");
                int[] posicionCarta1 = new int[2];
                int[] posicionCarta2 = new int[2];

                jugadorComputador = new JugadorComputador(tablero, tamannoFilas, tamannoColumnas, nivelIA);

                do {
                    carta1 = jugadorComputador.elegirCarta(posicionCarta1);

                    jugadorComputador.actualizarCartasVistas(carta1.getValor(), posicionCarta1);

                    carta2 = jugadorComputador.elegirSegundaCartaCasoTres(carta1, posicionCarta1);

                } while (carta1.esParejaEncontrada() || carta1 == carta2);

                ejecutarClickEnCarta(carta1);
                ejecutarClickEnCarta(carta2);

                break;

        }

    }

    private Carta buscarParejaParaCarta(Carta carta) {
        for (int fila = 0; fila < tablero.getTamannoFilas(); fila++) {
            for (int columna = 0; columna < tablero.getTamannoColumnas(); columna++) {
                Carta posiblePareja = tablero.getCarta(fila, columna);
                if (posiblePareja != carta && posiblePareja.getValor() == carta.getValor() && !posiblePareja.esParejaEncontrada()) {
                    return posiblePareja;
                }
            }
        }
        return null;
    }

  private void handleCardClick(MouseEvent event) {
    ImageView imageView = (ImageView) event.getSource();
    Carta carta = (Carta) imageView.getUserData();

    // Si la carta ya ha sido encontrada como parte de un grupo o si ya está en la lista de cartas seleccionadas,
    // se evita que sea seleccionada nuevamente.
    if (carta.esParejaEncontrada() || cartasSeleccionadas.contains(carta)) {
        return;
    }

    // Se actualiza la imagen de la carta y se añade a la lista de cartas seleccionadas.
    imageView.setImage(new Image(carta.getRutaImagen(), 100, 100, true, true));
    cartasSeleccionadas.add(carta);

    // Si se alcanza el tamaño del grupo, se verifica si las cartas seleccionadas forman un grupo válido.
    if (cartasSeleccionadas.size() == grupoCartas) {
        boolean grupoEncontrado = tablero.esGrupo(cartasSeleccionadas.toArray(new Carta[0]));

        if (grupoEncontrado) {
            procesarGrupoEncontrado();
        } else {
            procesarGrupoNoEncontrado();
        }
    }
}

private void procesarGrupoEncontrado() {
    // Se marca cada carta como encontrada y se actualizan los puntos.
    for (Carta cartaSeleccionada : cartasSeleccionadas) {
        cartaSeleccionada.setParejaEncontrada(true);
    }

    int puntosGanados = 1; // Un punto por encontrar un grupo
    if (puntosExtra && mismoJugador) {
        puntosGanados++; // Un punto extra por encontrar grupos consecutivos
    }
    mismoJugador = true;

    if (turnoJugador1) {
        jugador1Puntaje += puntosGanados;
    } else {
        jugador2Puntaje += puntosGanados;
    }

    // Se limpia la lista de cartas seleccionadas y se actualizan las etiquetas de los jugadores.
    cartasSeleccionadas.clear();
    actualizarEtiquetasJugadores();

    if (!modoHumanoVsHumano && !turnoJugador1) {
        jugarComputador(nivelIA);
    }

    // Se verifica si el juego ha terminado y se muestra el resultado.
    if (partidaGanada() != Resultado.JUEGO_EN_PROGRESO) {
        mostrarResultados();
    }
}

private void procesarGrupoNoEncontrado() {
    // Se crea una pausa antes de voltear las cartas y cambiar el turno al otro jugador.
    PauseTransition pausa = new PauseTransition(Duration.seconds(0.7));
    pausa.setOnFinished((e) -> {
        for (Carta cartaSeleccionada : cartasSeleccionadas) {
            cartaSeleccionada.getVistaImagen().setImage(cartaImagenVuelta);
        }
        cartasSeleccionadas.clear();
        mismoJugador = false;
        turnoJugador1 = !turnoJugador1;

        actualizarEtiquetasJugadores();

        if (puntosMenos) {
            ajustarPuntaje();
        }

        actualizarEtiquetasJugadores();

        if (!modoHumanoVsHumano && !turnoJugador1) {
            jugarComputador(nivelIA);
        }
    });
    pausa.play();
}

    private void ajustarPuntaje() {
        if (turnoJugador1 && jugador1Puntaje > 0) {
            jugador1Puntaje -= 0.5;
        } else if (!turnoJugador1 && jugador2Puntaje > 0) {
            jugador2Puntaje -= 0.5;
        }
    }

    private void mostrarResultados() {
        String mensaje;
        switch (partidaGanada()) {
            case GANADOR_JUGADOR1:
                mensaje = "¡Partida ganada! Ganador: " + jugador1Nombre + " con " + jugador1Puntaje + " puntos.";
                break;
            case GANADOR_JUGADOR2:
                mensaje = "¡Partida ganada! Ganador: " + jugador2Nombre + " con " + jugador2Puntaje + " puntos.";
                break;
            case EMPATE:
                mensaje = "¡Empate! Ambos jugadores tienen " + jugador1Puntaje + " puntos.";
                break;
            default:
                throw new IllegalStateException("Resultado de la partida desconocido.");
        }
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Juego terminado");
            alert.setHeaderText("RESULTADOS");
            alert.setContentText(mensaje);
            alert.showAndWait();
        });
    }



    private void actualizarEtiquetasJugadores() {
        if (turnoJugador1) {
            jugador1Label.setText(jugador1Nombre + ": Puntaje:" + jugador1Puntaje + "-> Turno actual");
            jugador2Label.setText(jugador2Nombre + ": Puntaje:" + jugador2Puntaje);
        } else {
            jugador2Label.setText(jugador2Nombre + ": Puntaje:" + jugador2Puntaje + "-> Turno actual");
            jugador1Label.setText(jugador1Nombre + ": Puntaje:" + jugador1Puntaje);
        }
    }

    private Resultado partidaGanada() {
        int cartasRestantes = 0;
        for (int fila = 0; fila < tamannoFilas; fila++) {
            for (int col = 0; col < tamannoColumnas; col++) {
                Carta carta = tablero.getCarta(fila, col);
                if (!carta.esParejaEncontrada()) {
                    cartasRestantes++;
                }
            }
        }

        if (cartasRestantes > 0) {
            return Resultado.JUEGO_EN_PROGRESO;
        } else {
            if (jugador1Puntaje > jugador2Puntaje) {
                return Resultado.GANADOR_JUGADOR1;
            } else if (jugador1Puntaje < jugador2Puntaje) {
                return Resultado.GANADOR_JUGADOR2;
            } else {
                return Resultado.EMPATE;
            }
        }
    }

    private void ejecutarClickEnCarta(Carta cartaElegida) {
        ImageView imageView = cartaElegida.getVistaImagen();
        MouseEvent eventoClick = new MouseEvent(MouseEvent.MOUSE_CLICKED, 0, 0, 0, 0, MouseButton.PRIMARY, 1, true, true, true, true, true, true, true, true, true, true, null);
        imageView.fireEvent(eventoClick);

    }

    private void mostrarRevision() {
        if (ventanaRevision == null) {
            ventanaRevision = new Stage();
            ventanaRevision.setTitle("Modo Revisión");

            GridPane cuadricula = new GridPane();

            for (int fila = 0; fila < tamannoFilas; fila++) {
                for (int col = 0; col < tamannoColumnas; col++) {
                    Carta carta = tablero.getCarta(fila, col);
                    ImageView imageView = new ImageView(new Image(carta.getRutaImagen(), 100, 100, true, true));
                    cuadricula.add(imageView, col, fila);
                }
            }

            VBox vbox = new VBox();
            vbox.getChildren().addAll(cuadricula);

            ventanaRevision.setScene(new Scene(vbox));
        }
        ventanaRevision.show();
    }

};
