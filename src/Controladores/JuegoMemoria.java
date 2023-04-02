/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import java.io.IOException;
import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author Administrador
 */
public class JuegoMemoria extends Application {

    private Tablero tablero;
    private int tamanno = 4;

    public JuegoMemoria() {

    }

    public static void main(String[] args) {//argumentos
        try {
            launch(args);
        } catch (Exception e) {
            if (e.getCause() != null) {
                e.getCause().printStackTrace();
            } else {
                e.printStackTrace();

            }
        }
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Juego Memoria");
        try {
            
            String ruta = getClass().getResource("/Imagenes/1.png").toExternalForm();
            String ruta2 = getClass().getResource("/Imagenes/2.png").toExternalForm();
            
            String ruta3 = getClass().getResource("/Imagenes/3.png").toExternalForm();
            
            String ruta4 = getClass().getResource("/Imagenes/4.png").toExternalForm();
            
            String ruta5 = getClass().getResource("/Imagenes/5.png").toExternalForm();
            
            String ruta6 = getClass().getResource("/Imagenes/6.png").toExternalForm();

            String[] cartasImagenes = {
                ruta, ruta2, ruta3, ruta4, ruta5, ruta6, ruta, ruta, ruta, ruta, ruta, ruta};
            
            
            
            tablero = new Tablero(tamanno, cartasImagenes);

            // Create the game board layout
            GridPane cuadricula = new GridPane();

            for (int fila = 0; fila < tamanno; fila++) {
                for (int col = 0; col < tamanno; col++) {
                    Carta carta = tablero.getCarta(fila, col);
                    ImageView imageView = carta.getVistaImagen();
                    imageView.setUserData(carta);
                    imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, this::handleCardClick);
                    cuadricula.add(imageView, col, fila);
                }
            };

            primaryStage.setScene(new Scene(cuadricula));
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Carta primerCarta = null;

    private void handleCardClick(MouseEvent event) {
        ImageView imageView = (ImageView) event.getSource();
        Carta carta = (Carta) imageView.getUserData();

        if (carta.esParejaEncontrada()) {
            return;
        }

        // Show the card image
        imageView.setImage(carta.getVistaImagen().getImage());

        if (primerCarta == null) {
            primerCarta = carta;
        } else {
            if (tablero.esPareja(primerCarta, carta)) {
                System.out.println("Son pareja");
            } else {
                System.out.println("No son pareja");
                // Hide the card images after a short delay
                imageView.setImage(null);
                primerCarta.getVistaImagen().setImage(null);
            }
            primerCarta = null;
        }
    }
}
