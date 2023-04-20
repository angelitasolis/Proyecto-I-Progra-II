/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import java.util.ArrayList;
import java.util.Collections;

public class Tablero {

    private Carta[][] cartas;
    private int tamannoFilas;
    private int tamannoColumnas;
    private int numCartasPorGrupo;

    public Tablero(int tamannoFilas, int tamannoColumnas, String[] cartasImagenes, int numCartasPorGrupo) {
        this.tamannoFilas = tamannoFilas;
        this.tamannoColumnas = tamannoColumnas;
        this.numCartasPorGrupo = numCartasPorGrupo;
        this.cartas = new Carta[tamannoFilas][tamannoColumnas];
        ordenamientoCartas(cartasImagenes);
    }

    private void ordenamientoCartas(String[] cartasImagenes) {
        ArrayList<Integer> valoresCarta = new ArrayList<>();
        int total = (tamannoFilas * tamannoColumnas) / numCartasPorGrupo;
        for (int i = 0; i < total; i++) {
            for (int j = 0; j < numCartasPorGrupo; j++) {
                valoresCarta.add(i);
            }
        }

        Collections.shuffle(valoresCarta);
        for (int fila = 0; fila < tamannoFilas; fila++) {
            for (int col = 0; col < tamannoColumnas; col++) {
                int valor = valoresCarta.remove(0);
                this.cartas[fila][col] = new Carta(valor, cartasImagenes[valor]);
            }
        }
    }

    public Carta getCarta(int fila, int col) {
        return cartas[fila][col];
    }

    public boolean esGrupo(Carta... cartasGrupo) {
        int primerValor = cartasGrupo[0].getValor();
        for (Carta carta : cartasGrupo) {
            if (carta.getValor() != primerValor) {
                return false;
            }
        }
        for (Carta carta : cartasGrupo) {
            carta.setParejaEncontrada(true);
        }
        return true;
    }
}