/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @Mari <3
 */
public class JugadorComputador {
    private Tablero tablero;
    private int tamannoFilas;
    private int tamannoColumnas;
    private int nivelIA;
    private HashMap<Integer, ArrayList<int[]>> cartasVistas;

    public JugadorComputador(Tablero tablero, int tamannoFilas, int tamannoColumnas, int nivelIA) {
        this.tablero = tablero;
        this.tamannoFilas = tamannoFilas;
        this.tamannoColumnas = tamannoColumnas;
        this.nivelIA = nivelIA;
         if (nivelIA == 3) {
            this.cartasVistas = new HashMap<>();
        }
        
    }

     public Carta elegirCarta(int[] posicionCartaElegida) {
        if (nivelIA == 3) {
            return elegirCartaCasoTres(posicionCartaElegida);
        } else {
            return elegirCartaCasoUnoDos(posicionCartaElegida);
        }
    }
     
    private Carta elegirCartaCasoUnoDos(int[] posicionCartaElegida) {
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
    private Carta elegirCartaCasoTres(int[] posicionCartaElegida) {
    if (cartasVistas == null) {
        cartasVistas = new HashMap<>();
    }

    // Busca una carta que tenga una pareja en el HashMap cartasVistas
    for (Map.Entry<Integer, ArrayList<int[]>> entry : cartasVistas.entrySet()) {
        if (entry.getValue().size() >= 2) {
            int[] posicion = entry.getValue().get(0);
            posicionCartaElegida[0] = posicion[0];
            posicionCartaElegida[1] = posicion[1];
            return tablero.getCarta(posicion[0], posicion[1]);
        }
    }

    // Si no se encontró una carta con pareja en el HashMap, selecciona una carta aleatoria que no haya sido encontrada
    ArrayList<Carta> cartasNoEncontradas = new ArrayList<>();

    for (int fila = 0; fila < tamannoFilas; fila++) {
        for (int col = 0; col < tamannoColumnas; col++) {
            Carta carta = tablero.getCarta(fila, col);
            if (!carta.esParejaEncontrada()) {
                cartasNoEncontradas.add(carta);
            }
        }
    }

    int indiceAleatorio = (int) (Math.random() * cartasNoEncontradas.size());
    Carta cartaElegida = cartasNoEncontradas.get(indiceAleatorio);

    // Actualiza la posición de la carta elegida
    for (int fila = 0; fila < tamannoFilas; fila++) {
        for (int col = 0; col < tamannoColumnas; col++) {
            if (tablero.getCarta(fila, col) == cartaElegida) {
                posicionCartaElegida[0] = fila;
                posicionCartaElegida[1] = col;
                break;
            }
        }
    }

    return cartaElegida;
}
    
    
    private Carta elegirSegundaCartaCasoTres(Carta primeraCarta, int[] posicionPrimeraCarta) {
    if (cartasVistas != null && cartasVistas.get(primeraCarta.getValor()).size() > 1) {
        for (int[] posicion : cartasVistas.get(primeraCarta.getValor())) {
            if (posicion[0] != posicionPrimeraCarta[0] || posicion[1] != posicionPrimeraCarta[1]) {
                cartasVistas.get(primeraCarta.getValor()).remove(posicion);
                return tablero.getCarta(posicion[0], posicion[1]);
            }
        }
    }

    // Si no se encontró una pareja adecuada en el HashMap, selecciona una carta aleatoria que no sea la primera carta
    ArrayList<Carta> cartasNoEncontradas = new ArrayList<>();

    for (int fila = 0; fila < tamannoFilas; fila++) {
        for (int col = 0; col < tamannoColumnas; col++) {
            Carta carta = tablero.getCarta(fila, col);
            if (!carta.esParejaEncontrada() && (fila != posicionPrimeraCarta[0] || col != posicionPrimeraCarta[1])) {
                cartasNoEncontradas.add(carta);
            }
        }
    }

    
    int indiceAleatorio = (int) (Math.random() * cartasNoEncontradas.size());
    return cartasNoEncontradas.get(indiceAleatorio);

}
    public Carta elegirSegundaCarta(Carta primeraCarta, int[] posicionPrimeraCarta, int[] posicionSegundaCarta) {
    Carta segundaCarta;
    do {
        posicionSegundaCarta[0] = (int) (Math.random() * tablero.getTamannoFilas());
        posicionSegundaCarta[1] = (int) (Math.random() * tablero.getTamannoColumnas());
        segundaCarta = tablero.getCarta(posicionSegundaCarta[0], posicionSegundaCarta[1]);
    } while (primeraCarta == segundaCarta || segundaCarta.esParejaEncontrada() || (posicionSegundaCarta[0] == posicionPrimeraCarta[0] && posicionSegundaCarta[1] == posicionPrimeraCarta[1]));

    return segundaCarta;
}
    
}