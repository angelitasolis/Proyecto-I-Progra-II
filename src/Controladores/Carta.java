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

    public Carta(int valor, String rutaImagen) {
        
        this.valor = valor;
        this.parejaEncontrada = false;
        this.vistaImagen = new ImageView( new Image(rutaImagen));
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
    
    
    
            
    
}
