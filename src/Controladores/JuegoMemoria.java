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
    private boolean mismoJugador = false;
    private boolean modoTrio = false;
    private int grupoCartas;
    private ArrayList<Carta> cartasSeleccionadas;
    private Stage ventanaRevision;
    private boolean jugadorAutomatico = false;
    private Random random = new Random();
    private JugadorComputador jugadorComputador;

    private enum Resultado {
        GANADOR_JUGADOR1, GANADOR_JUGADOR2, EMPATE, JUEGO_EN_PROGRESO
    }

    //Cronometro
    private void iniciarCronometro(int pduracionSegundos) {
        jugador1Label.setText(jugador1Nombre + " :" + jugador1Puntaje + "-> Turno actual");
        Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.seconds(1),
                        event -> tiempoTranscurrido.set(tiempoTranscurrido.get() + 1)
                )
        );
        timeline.setCycleCount(Timeline.INDEFINITE); // Cronómetro continuo
        timeline.setOnFinished(event -> System.out.println("Tiempo terminado"));
        timeline.play();
    }

    public JuegoMemoria() {
    }

    public Tablero getTablero() {
        return tablero;
    }

    public void mostrarJuego(Stage primaryStage, int ptamannoFilas, int ptamannoColumnas, int pcantidadSegundos, boolean pModoHumanoVsHumano, String pJugador1Nombre, String pJugador2Nombre, boolean pPuntosExtra, boolean pmodoTrio) {
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

        String[] cartasImagenes = new String[tamannoFilas * tamannoColumnas];//Crea la baraja de cartas

        for (int i = 1; i < (tamannoFilas * tamannoColumnas) / 2 + 1; i++) {

            String ruta = getClass().getResource("/Imagenes/" + i + ".png").toExternalForm();
            cartasImagenes[i - 1] = ruta;
            System.out.println(i);
        }

        tablero = new Tablero(tamannoFilas, tamannoColumnas, cartasImagenes, grupoCartas);
        jugadorComputador = new JugadorComputador(tablero, tamannoFilas, tamannoColumnas);
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
        jugador1Label = new Label(jugador1Nombre + ": " + jugador1Puntaje);
        jugador2Label = new Label(jugador2Nombre + ": " + jugador2Puntaje);
        Label tipoJugadores = new Label(modoHumanoVsHumano ? " Modo Humano vs Humano" : "Modo Humano vs Computador");

        vbox.getChildren().addAll(tipoJugadores, modoJuegoLabel, jugador1Label, jugador2Label, verTodasCartas, cuadricula, tiempoTranscurridoLabel);

        primaryStage.setScene(new Scene(vbox));
        primaryStage.show();

        iniciarCronometro(cantidadSegundos); // Llamar al cronómetro con la variable actualizada

    }

    private void jugarComputador() {

        Carta carta1, carta2;
        do {
            carta1 = jugadorComputador.elegirCarta();
            carta2 = jugadorComputador.elegirCarta();
        } while (carta1.esParejaEncontrada() || cartasSeleccionadas.contains(carta1) || carta1 == carta2);

        int valor1 = carta1.getValor();
        int valor2 = carta2.getValor();

        ejecutarClickEnCarta(carta1);
        ejecutarClickEnCarta(carta2);

//        Platform.runLater(() -> {
//            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//            alert.setTitle("Juego terminado");
//            alert.setHeaderText(null);
//            alert.setContentText("Juega computador: " + valor1 + ": " + valor2  );
//            alert.showAndWait();
//        });
    }

    private void handleCardClick(MouseEvent event) {
        ImageView imageView = (ImageView) event.getSource();
        Carta carta = (Carta) imageView.getUserData();

        if (carta.esParejaEncontrada() || cartasSeleccionadas.contains(carta)) {
            return;
        }
        imageView.setImage(new Image(carta.getRutaImagen(), 100, 100, true, true));
        cartasSeleccionadas.add(carta);

        if (cartasSeleccionadas.size() == grupoCartas) {
            boolean grupoEncontrado = tablero.esGrupo(cartasSeleccionadas.toArray(new Carta[0]));

            if (grupoEncontrado) {
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
                cartasSeleccionadas.clear(); // Limpia la lista de cartas seleccionadas

                actualizarEtiquetasJugadores(); // Actualiza las etiquetas de los jugadores

                if (!modoHumanoVsHumano && turnoJugador1 == false) {
                    jugarComputador();
                }

                if (partidaGanada() != Resultado.JUEGO_EN_PROGRESO) {
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
                        alert.setHeaderText(null);
                        alert.setContentText(mensaje);
                        alert.showAndWait();
                    });
                }

            } else {
                PauseTransition pausa = new PauseTransition(Duration.seconds(2));
                pausa.setOnFinished((e) -> {
                    for (Carta cartaSeleccionada : cartasSeleccionadas) {
                        cartaSeleccionada.getVistaImagen().setImage(cartaImagenVuelta);
                    }
                    cartasSeleccionadas.clear();
                    mismoJugador = false;
                    turnoJugador1 = !turnoJugador1;
                    if (!modoHumanoVsHumano && turnoJugador1 == false) {
                        jugarComputador();
                    }
                    actualizarEtiquetasJugadores(); // Actualiza las etiquetas de los jugadores
                });
                pausa.play();
            }
        }
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
