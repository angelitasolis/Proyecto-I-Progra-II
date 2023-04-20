package Controladores;

import java.net.URL;
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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;

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

    //Cronometro
    private void iniciarCronometro(int pduracionSegundos) {
        jugador1Label.setText(jugador1Nombre + " :" + jugador1Puntaje + "-> Turno actual");
        Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.seconds(1),
                        event -> tiempoTranscurrido.set(tiempoTranscurrido.get() + 1)
                )
        );
        timeline.setCycleCount(pduracionSegundos);
        timeline.setOnFinished(event -> System.out.println("Tiempo terminado"));
        timeline.play();
    }

    public JuegoMemoria() {
    }

    public void mostrarJuego(Stage primaryStage, int ptamannoFilas, int ptamannoColumnas, int pcantidadSegundos, boolean pModoHumanoVsHumano, String pJugador1Nombre, String pJugador2Nombre, boolean pPuntosExtra, boolean pmodoTrio) {
        primaryStage.setTitle("Juego Memoria");
        tamannoFilas = ptamannoFilas;
        tamannoColumnas = ptamannoColumnas;

        this.modoHumanoVsHumano = pModoHumanoVsHumano;
        this.jugador1Nombre = pJugador1Nombre;
        this.jugador2Nombre = pJugador2Nombre;
        this.jugador1Puntaje = 0;
        this.jugador2Puntaje = 0;
        this.turnoJugador1 = true;
        this.puntosExtra = pPuntosExtra;
        this.modoTrio = pmodoTrio;

        String[] cartasImagenes = new String[tamannoFilas * tamannoColumnas];//Crea la baraja de cartas

        for (int i = 1; i < (tamannoFilas * tamannoColumnas) / 2 + 1; i++) {

            String ruta = getClass().getResource("/Imagenes/" + i + ".png").toExternalForm();
            cartasImagenes[i - 1] = ruta;
            System.out.println(i);
        }

        tablero = new Tablero(tamannoFilas, tamannoColumnas, cartasImagenes);

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

        // Add the label to the scene
        VBox vbox = new VBox();
        jugador1Label = new Label(jugador1Nombre + ": " + jugador1Puntaje);
        jugador2Label = new Label(jugador2Nombre + ": " + jugador2Puntaje);
        vbox.getChildren().addAll(jugador1Label, jugador2Label, cuadricula, tiempoTranscurridoLabel);

        primaryStage.setScene(new Scene(vbox));
        primaryStage.show();

        iniciarCronometro(cantidadSegundos);//llega hasta donde yo quiera

    }

    private Carta primerCarta = null;

    private void handleCardClick(MouseEvent event) {

        ImageView imageView = (ImageView) event.getSource();
        Carta carta = (Carta) imageView.getUserData();

        if (carta.esParejaEncontrada() || carta == primerCarta) {
            return;
        }
        imageView.setImage(new Image(carta.getRutaImagen(), 100, 100, true, true));

        if (primerCarta == null) {
            primerCarta = carta;
        } else {
            if (tablero.esPareja(primerCarta, carta)) {
                System.out.println("Son pareja");
                primerCarta.setParejaEncontrada(true);
                carta.setParejaEncontrada(true);
                primerCarta = null;

                if (partidaGanada()) {
                    System.out.println("Felicidades, ha ganado la partida");
                }

                //jugadores
                if (turnoJugador1) {
                    jugador1Puntaje++;
                    if (puntosExtra && mismoJugador) {
                        jugador1Puntaje++;
                    }

                    jugador1Label.setText(jugador1Nombre + ": Puntaje:" + jugador1Puntaje + "-> Turno actual");
                    mismoJugador = true;

                } else {
                    jugador2Puntaje++;
                    if (puntosExtra && mismoJugador) {
                        jugador2Puntaje++;

                    }

                    jugador2Label.setText(jugador2Nombre + ": Puntaje:" + jugador2Puntaje + "-> Turno actual");

                    mismoJugador = true;
                }

            } else {
                System.out.println("No son pareja");
                PauseTransition pausa = new PauseTransition(Duration.seconds(1));
                pausa.setOnFinished((e) -> {
                    imageView.setImage(cartaImagenVuelta);
                    if (primerCarta != null) {
                        primerCarta.getVistaImagen().setImage(cartaImagenVuelta);
                        primerCarta = null;

                        //cambia el turno
                        turnoJugador1 = !turnoJugador1;
                        mismoJugador = false;

                        if (turnoJugador1) {
                            jugador1Label.setText(jugador1Nombre + ": Puntaje:" + jugador1Puntaje + "-> Turno actual");
                            jugador2Label.setText(jugador2Nombre + ": Puntaje:" + jugador2Puntaje);
                        } else {
                            jugador2Label.setText(jugador2Nombre + ": Puntaje:" + jugador2Puntaje + "-> Turno actual");
                            jugador1Label.setText(jugador1Nombre + ": Puntaje:" + jugador1Puntaje);
                        }
                    }
                });
                pausa.play();
            }
        }
    }

    private boolean partidaGanada() {
        for (int fila = 0; fila < tamannoFilas; fila++) {
            for (int col = 0; col < tamannoColumnas; col++) {
                Carta carta = tablero.getCarta(fila, col);
                if (!carta.esParejaEncontrada()) {
                    return false;
                }
            }
        }
        return true;
    }

};
