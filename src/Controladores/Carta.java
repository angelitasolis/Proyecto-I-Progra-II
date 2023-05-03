/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

/**
 *
 * @author Administrador
 */
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Carta {

    private ImageView vistaImagen;
    private int valor;
    private boolean parejaEncontrada;
    private final String rutaImagen;
    private boolean esCartaComodin = false;
         
    public Carta(int valor, String rutaImagen) {

        this.valor = valor;
        this.parejaEncontrada = false;
        this.rutaImagen = rutaImagen;
        this.vistaImagen = new ImageView();
        this.vistaImagen.setImage(new Image(rutaImagen, 100, 100, true, true));
        this.vistaImagen.setPreserveRatio(true);
        this.vistaImagen.setFitWidth(100);
        
        
    }
    

    public ImageView getVistaImagen() {
        return vistaImagen;
    }

    public int getValor() {
        return valor;
    }

    public boolean esParejaEncontrada() {
        return parejaEncontrada;
    }

    public void setParejaEncontrada(boolean parejaEncontrada) {
        this.parejaEncontrada = parejaEncontrada;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    

}
