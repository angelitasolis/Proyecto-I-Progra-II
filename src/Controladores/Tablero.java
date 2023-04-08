/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Mariangel Solis Ceciliano
 */
public class Tablero {

    private Carta[][] cartas;
    private int tamannoFilas;
    private int tamannoColumnas;

    public Tablero(int tamannoFilas, int tamannoColumnas, String[] cartasImagenes) {
        this.tamannoFilas = tamannoFilas;
        this.tamannoColumnas = tamannoColumnas;
        this.cartas = new Carta[tamannoFilas][tamannoColumnas];//instancia el arreglo que guarta las cartas
        ordenamientoCartas(cartasImagenes);
    }

    private void ordenamientoCartas(String[] cartasImagenes) {
        ArrayList<Integer> valoresCarta = new ArrayList<>();//llenando los valores del 0 a largo por ancho /dos las mete en la lista
        int total = (tamannoFilas * tamannoColumnas) / 2;
        for (int i = 0; i < (total); i++) {
            valoresCarta.add(i);
            valoresCarta.add(i);
        }

        Collections.shuffle(valoresCarta);//collection listas dinamicas, este la desordena
        for (int fila = 0; fila < tamannoFilas; fila++) {
            for (int col = 0; col < tamannoColumnas; col++) {
                int valor = valoresCarta.remove(0);//toma la primer carta de la baraja, la saca y la asiga al valor del tablero
                this.cartas[fila][col] = new Carta(valor, cartasImagenes[valor]);
            }
        }
    }

    public Carta getCarta(int fila, int col) {
        return cartas[fila][col];
    }

    public boolean esPareja(Carta carta1, Carta carta2) {//recibe dos cartas y verifica si hizo match
        if (carta1.getValor() == carta2.getValor()) {
            carta1.setParejaEncontrada(true);
            carta2.setParejaEncontrada(true);
            return true;
        }
        return false;
    }

}
