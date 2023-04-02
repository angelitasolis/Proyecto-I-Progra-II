package Controladores;

import java.net.URL;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class JuegoMemoria extends Application {

    private Tablero tablero;
    private int tamanno = 4;
    private String ruta = getClass().getResource("/Imagenes/SignoPreguntabien.png").toExternalForm();
    private Image cartaImagenVuelta = new Image(ruta);

    public JuegoMemoria() {
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Juego Memoria");

        String[] cartasImagenes = new String[16];
        for (int i = 1; i < 17; i++) {
            String ruta = getClass().getResource("/Imagenes/" + i + ".png").toExternalForm();
            cartasImagenes[i - 1] = ruta;
            System.out.println(i);
        }

        tablero = new Tablero(tamanno, cartasImagenes);

        // Create the game board layout
        GridPane cuadricula = new GridPane();

        for (int fila = 0; fila < tamanno; fila++) {
            for (int col = 0; col < tamanno; col++) {
                Carta carta = tablero.getCarta(fila, col);
                ImageView imageView = carta.getVistaImagen();
                imageView.setImage(cartaImagenVuelta);
                imageView.setUserData(carta);
                imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, this::handleCardClick);
                cuadricula.add(imageView, col, fila);
            }
        }

        primaryStage.setScene(new Scene(cuadricula));
        primaryStage.show();
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
            } else {
                System.out.println("No son pareja");
                PauseTransition pausa = new PauseTransition(Duration.seconds(1));
                pausa.setOnFinished((e) -> {
                    imageView.setImage(cartaImagenVuelta);
                    if (primerCarta != null) {
                        primerCarta.getVistaImagen().setImage(cartaImagenVuelta);
                        primerCarta = null;
                    }
                });
                pausa.play();
            }
        }
    }
}