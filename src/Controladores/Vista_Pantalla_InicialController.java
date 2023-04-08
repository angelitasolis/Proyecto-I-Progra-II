/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Administrador
 */
public class Vista_Pantalla_InicialController implements Initializable {

    private JuegoMemoria juegoMemoria;
    int dificultad = 0;

    @FXML
    private Text Jugador1;
    @FXML
    private Text Jugador2;

    private String Jugador1vsComputadora;
    private String Jugador1HvsH;
    private String Jugador2HvsH;

    @FXML
    private BorderPane VistaPrincipal;
    @FXML
    private BorderPane VistaModoJuego;
    @FXML
    private CheckBox BotCartaComdin;
    @FXML
    private CheckBox BotPuntoExtra;
    @FXML
    private CheckBox BotPuntoMenos;
    @FXML
    private BorderPane VistaJugadores;
    @FXML
    private CheckBox HumanoVSHumano;
    @FXML
    private CheckBox HumanoVSComputadora;
    @FXML
    private TextField JugadorHumano1vsH;
    @FXML
    private TextField JugadorHumano2vsH;
    @FXML
    private TextField JugadorHumano1vsC;
    @FXML
    private Button EnviarHumanoVSHumano;
    @FXML
    private Button EnviarHumanoVSC;
    @FXML
    private ImageView BotBackJugadores;
    @FXML
    private ImageView BackModoJuego;
    @FXML
    private ImageView BackAcercaDe;
    @FXML
    private ImageView AcercaDeImagen;
    @FXML
    private BorderPane VistaAcercaDe;
    @FXML
    private BorderPane VistaJuegoDificultad1;
    @FXML
    private BorderPane VistaJuegoDificultad2;
    @FXML
    private BorderPane VistaJuegoDificultad3;
    @FXML
    private BorderPane InformacionJuego;
    @FXML
    private Text TimerDificultad3;
    @FXML
    private Text ScoreJugador1;
    @FXML
    private Text Jugador2Score;
    @FXML
    private Button ExitDificultad3;
    @FXML
    private ImageView Carta2;
    @FXML
    private ImageView Carta3;
    @FXML
    private ImageView Carta4;
    @FXML
    private ImageView Carta5;
    @FXML
    private ImageView Carta7;
    @FXML
    private ImageView Carta6;
    @FXML
    private ImageView Carta9;
    @FXML
    private ImageView Carta1;
    @FXML
    private ImageView Carta11;
    @FXML
    private ImageView Carta10;
    @FXML
    private ImageView Carta13;
    @FXML
    private ImageView Carta12;
    @FXML
    private ImageView Carta14;
    @FXML
    private ImageView Carta16;
    @FXML
    private ImageView Carta17;
    @FXML
    private ImageView Carta8;
    @FXML
    private ImageView Carta15;
    @FXML
    private ImageView Carta18;
    @FXML
    private ImageView Carta19;
    @FXML
    private ImageView Carta20;
    @FXML
    private ImageView Carta21;
    @FXML
    private ImageView Carta23;
    @FXML
    private ImageView Carta22;
    @FXML
    private ImageView Carta24;
    @FXML
    private ImageView Carta25;
    @FXML
    private ImageView Carta26;
    @FXML
    private ImageView Carta27;
    @FXML
    private ImageView Carta28;
    @FXML
    private ImageView Carta30;
    @FXML
    private ImageView Carta31;
    @FXML
    private ImageView Carta32;
    @FXML
    private Text TimerDificultad3111;
    @FXML
    private Text Jugador1111;
    @FXML
    private Text Jugador2111;
    @FXML
    private Text ScoreJugador1111;
    @FXML
    private Text Jugador2Score111;
    @FXML
    private Button ExitDificultad3111;
    @FXML
    private ImageView Carta3111;
    @FXML
    private ImageView Carta5111;
    @FXML
    private ImageView Carta7111;
    @FXML
    private ImageView Carta1111;
    @FXML
    private ImageView Carta10111;
    @FXML
    private ImageView Carta12111;
    @FXML
    private ImageView Carta14111;
    @FXML
    private ImageView Carta16111;
    @FXML
    private ImageView Carta17111;
    @FXML
    private ImageView Carta19111;
    @FXML
    private ImageView Carta21111;
    @FXML
    private ImageView Carta23111;
    @FXML
    private ImageView Carta2611;
    @FXML
    private ImageView Carta27111;
    @FXML
    private ImageView Carta30111;
    @FXML
    private ImageView Carta32111;
    @FXML
    private Text TimerDificultad31111;
    @FXML
    private Text Jugador11111;
    @FXML
    private Text Jugador21111;
    @FXML
    private Text ScoreJugador11111;
    @FXML
    private Text Jugador2Score1111;
    @FXML
    private Button ExitDificultad31111;
    @FXML
    private ImageView Carta101111;
    @FXML
    private ImageView Carta121111;
    @FXML
    private ImageView Carta141111;
    @FXML
    private ImageView Carta161111;
    @FXML
    private ImageView Carta171111;
    @FXML
    private ImageView Carta191111;
    @FXML
    private ImageView Carta211111;
    @FXML
    private ImageView Carta231111;
    @FXML
    private BorderPane VistaDificultades;
    @FXML
    private ImageView BackDificultades;
    private BorderPane vistaModosAlternativos;

    @FXML
    private Button BotDificultad;

    private Stage getPrimaryStage() {
        return (Stage) VistaPrincipal.getScene().getWindow();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        VistaPrincipal.toFront();
        juegoMemoria = new JuegoMemoria();
    }

    @FXML
    private void DesplegarInfo(MouseEvent event) {
        System.out.println("hola");

    }

    @FXML
    private void AcercaDe(MouseEvent event) {
        VistaAcercaDe.toFront();
    }

    @FXML
    private void mostrarPantallaModoJuego(ActionEvent event) {
        VistaModoJuego.toFront();
    }

    @FXML
    private void VistaJugadores(ActionEvent event) {
        VistaJugadores.toFront();
    }

    @FXML
    private void HumanoVSHumano(ActionEvent event) {
        if (HumanoVSHumano.isSelected() == true) {
            HumanoVSComputadora.setDisable(true);
            EnviarHumanoVSC.setDisable(true);
            JugadorHumano1vsC.setDisable(true);

        } else {
            HumanoVSComputadora.setDisable(false);
            EnviarHumanoVSC.setDisable(false);
            JugadorHumano1vsC.setDisable(false);
        }
    }

    @FXML
    private void HumanoVSComputadora(ActionEvent event) {
        if (HumanoVSComputadora.isSelected() == true) {
            HumanoVSHumano.setDisable(true);
            EnviarHumanoVSHumano.setDisable(true);
            JugadorHumano1vsH.setDisable(true);
            JugadorHumano2vsH.setDisable(true);

        } else {
            HumanoVSHumano.setDisable(false);
            HumanoVSHumano.setDisable(false);
            EnviarHumanoVSHumano.setDisable(false);
            JugadorHumano1vsH.setDisable(false);
            JugadorHumano2vsH.setDisable(false);
        }
    }

    @FXML
    private void getInfoHumanovsHumano(ActionEvent event) {
        Jugador1HvsH = JugadorHumano1vsH.getText();
        Jugador2HvsH= JugadorHumano2vsH.getText();
        VistaPrincipal.toFront();
    }

    @FXML
    private void getInfoHumanovsComputadora(ActionEvent event) {
        Jugador1vsComputadora = JugadorHumano1vsC.getText();
        VistaPrincipal.toFront();
    }

    @FXML
    private void BackJugadores(MouseEvent event) {
        VistaPrincipal.toFront();
    }

    @FXML
    private void BackMododeJuego(MouseEvent event) {
        VistaPrincipal.toFront();
    }

    @FXML
    private void BackAcercaDe(MouseEvent event) {
        VistaPrincipal.toFront();
    }

    @FXML
    private void BackInformacionJuego(MouseEvent event) {
        VistaPrincipal.toFront();
    }

    @FXML
    private void ExplicacionJuego(MouseEvent event) {
        InformacionJuego.toFront();
    }

    @FXML
    private void BackDificultades(MouseEvent event) {
        VistaPrincipal.toFront();
    }

    private void botModosAlternativos(MouseEvent event) {
        vistaModosAlternativos.toFront();
    }

    private void backMetodosAlternativos(MouseEvent event) {
        VistaPrincipal.toFront();
    }

    @FXML
    private void BotDificultad(MouseEvent event) {
        VistaDificultades.toFront();
    }

    @FXML
    void onDificultad1(MouseEvent event) {
        dificultad = 1;

    }

    @FXML
    void onDificultad2(MouseEvent event) {
        dificultad = 2;
    }

    @FXML
    void onDificultad3(MouseEvent event) {
        dificultad = 3;

    }

    @FXML
    private void botIniciarJuego(MouseEvent event) {
        Stage primaryStage = getPrimaryStage();

        if (dificultad == 1) {
            juegoMemoria.mostrarJuego(primaryStage, 2, 4, 60, true, Jugador1HvsH, Jugador2HvsH);
        } else if (dificultad == 2) {
            juegoMemoria.mostrarJuego(primaryStage, 4, 4, 60, true, Jugador1HvsH, Jugador2HvsH);
        } else {
            juegoMemoria.mostrarJuego(primaryStage, 4, 8, 40, true, Jugador1HvsH, Jugador2HvsH);
        }

    }
}
