package Controladores;

import java.net.URL;
import java.util.Random;
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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;

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
    private String jugador1Nombre;
    private String jugador2Nombre;
    private boolean turnoJugador1;
    private boolean modoHumanoVsHumano = true;//True
    private Label jugador1Label;
    private Label jugador2Label;

    //Cronometro
    private void iniciarCronometro(int duracionSegundos) {
        Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.seconds(1),
                        event -> tiempoTranscurrido.set(tiempoTranscurrido.get() + 1)
                )
        );
        timeline.setCycleCount(duracionSegundos);
        timeline.setOnFinished(event -> System.out.println("Tiempo terminado"));
        timeline.play();
    }

    public JuegoMemoria() {
    }

    public void mostrarJuego(Stage primaryStage, int ptamannoFilas, int ptamannoColumnas, int pcantidadSegundos, boolean pModoHumanoVsHumano, String pJugador1Nombre, String pJugador2Nombre) {
        primaryStage.setTitle("Juego Memoria");
        tamannoFilas = ptamannoFilas;
        tamannoColumnas = ptamannoColumnas;

        this.modoHumanoVsHumano = pModoHumanoVsHumano;
        this.jugador1Nombre = pJugador1Nombre;
        this.jugador2Nombre = pJugador2Nombre;
        this.jugador1Puntaje = 0;
        this.jugador2Puntaje = 0;
        this.turnoJugador1 = true;

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
                    jugador1Label.setText(jugador1Nombre + ": " + jugador1Puntaje);
                } else {
                    jugador2Puntaje++;
                    jugador2Label.setText(jugador2Nombre + ": " + jugador2Puntaje);
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

                    }

                });

                pausa.play();
            }
            if (!modoHumanoVsHumano && !turnoJugador1) {
                jugarComputador();
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

    private void jugarComputador() {
        Random random = new Random();

        Carta carta1 = null;
        Carta carta2 = null;

        while (carta1 == null || carta1.esParejaEncontrada()) {
            int fila = random.nextInt(tamannoFilas);
            int col = random.nextInt(tamannoColumnas);
            carta1 = tablero.getCarta(fila, col);
        }

        while (carta2 == null || carta2.esParejaEncontrada() || carta2 == carta1) {
            int fila = random.nextInt(tamannoFilas);
            int col = random.nextInt(tamannoColumnas);
            carta2 = tablero.getCarta(fila, col);
        }
        // Revela las cartas elegidas
        carta1.getVistaImagen().setImage(new Image(carta1.getRutaImagen(), 100, 100, true, true));
        carta2.getVistaImagen().setImage(new Image(carta2.getRutaImagen(), 100, 100, true, true));

        if (tablero.esPareja(carta1, carta2)) {
            System.out.println("Computador encontró una pareja");
            carta1.setParejaEncontrada(true);
            carta2.setParejaEncontrada(true);

            jugador2Puntaje++;
            jugador2Label.setText(jugador2Nombre + ": " + jugador2Puntaje);

            if (partidaGanada()) {
                System.out.println("Felicidades, ha ganado la partida");
            }
        } else {
            final Carta finalCarta1 = carta1;
            final Carta finalCarta2 = carta2;

            System.out.println("Computador no encontró una pareja");
            PauseTransition pausa = new PauseTransition(Duration.seconds(1));
            pausa.setOnFinished((e) -> {
                finalCarta1.getVistaImagen().setImage(cartaImagenVuelta);
                finalCarta2.getVistaImagen().setImage(cartaImagenVuelta);
            });
            pausa.play();
        }

        // Cambia el turno
        turnoJugador1 = !turnoJugador1;
    }

};
