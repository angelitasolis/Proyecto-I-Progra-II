/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import java.util.ArrayList;

/**
 *
 * @Mari <3
 */
public class JugadorComputador {

    private int tamannoFilas;
    private int tamannoColumnas;
    private int nivelIA;
    

    public JugadorComputador(int tamannoFilas, int tamannoColumnas, int nivelIA) {
        this.tamannoFilas = tamannoFilas;
        this.tamannoColumnas = tamannoColumnas;
        this.nivelIA = nivelIA;
    

    }
    
    public Carta elegirCarta(int[] posicionCartaElegida, Tablero tablero) {
        ArrayList<Carta> cartasNoEncontradas = new ArrayList<>();

        for (int fila = 0; fila < tamannoFilas; fila++) {
            for (int col = 0; col < tamannoColumnas; col++) {
                Carta carta = tablero.getCarta(fila, col);
                if (!carta.esParejaEncontrada()) {
                    cartasNoEncontradas.add(carta);
                }
            }
        }

        if (cartasNoEncontradas.isEmpty()) {
            return null;
        } else {
            int indiceAleatorio = (int) (Math.random() * cartasNoEncontradas.size());
            return cartasNoEncontradas.get(indiceAleatorio);
        }

    }
    
    public Carta elegirCartaPareja(int[] posicionCartaElegida, Tablero tablero, Carta cartaAnterior) {
        ArrayList<Carta> cartasNoEncontradas = new ArrayList<>();

        for (int fila = 0; fila < tamannoFilas; fila++) {
            for (int col = 0; col < tamannoColumnas; col++) {
                Carta carta = tablero.getCarta(fila, col);
                if (!carta.esParejaEncontrada() && carta.getValor() == cartaAnterior.getValor() && carta != cartaAnterior) {
                    cartasNoEncontradas.add(carta);
                }
            }
        }

        if (cartasNoEncontradas.isEmpty()) {
            return null;
        } else {
            int indiceAleatorio = (int) (Math.random() * cartasNoEncontradas.size());
            return cartasNoEncontradas.get(indiceAleatorio);
        }

    }



 
}
