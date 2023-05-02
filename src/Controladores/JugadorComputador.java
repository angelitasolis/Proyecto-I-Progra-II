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

    private int tamannoFilas;
    private int tamannoColumnas;
    private int nivelIA;
    public HashMap<Integer, ArrayList<int[]>> cartasVistas;

    public JugadorComputador(int tamannoFilas, int tamannoColumnas, int nivelIA) {
        this.tamannoFilas = tamannoFilas;
        this.tamannoColumnas = tamannoColumnas;
        this.nivelIA = nivelIA;
        if (nivelIA == 3) {
            this.cartasVistas = new HashMap<>();
        }

    }

//    public Carta elegirCasoUno(){
//     do {
//                    int[] posicionCarta1 = new int[2];
//                    carta1 = jugadorComputador.elegirCarta(posicionCarta1, tablero);
//
//                                    
//                    for (int fila = 0; fila < tamannoFilas; fila++) {
//                        for (int col = 0; col < tamannoColumnas; col++) {
//                            Carta cartaI = tablero.getCarta(fila, col);
//                            if(carta1.getValor() == cartaI.getValor()) {
//                                jugadorComputador.actualizarCartasVistasJugadorHumano(carta1, fila, col);
//                            }
//                        }
//                    }
//                    int[] posicionCarta2 = new int[2];
//                    carta2 = jugadorComputador.elegirCarta(posicionCarta2, tablero);
//
//                    for (int fila = 0; fila < tamannoFilas; fila++) {
//                        for (int col = 0; col < tamannoColumnas; col++) {
//                            Carta cartaII = tablero.getCarta(fila, col);
//                            if(carta2.getValor() == cartaII.getValor()) {
//                                jugadorComputador.actualizarCartasVistasJugadorHumano(carta2, fila, col);
//                            }
//                        }
//                    }
//
//                } while (carta1.esParejaEncontrada() || cartasSeleccionadas.contains(carta1) || carta1 == carta2);
////        int valor1= carta1.getValor();
////        int valor2= carta2.getValor();
//
//                ejecutarClickEnCarta(carta1);
//                ejecutarClickEnCarta(carta2);
//    }

    
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


    public Carta elegirCartaCasoDos(int[] posicionCartaElegida, Tablero tablero) {
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

    public Carta elegirCartaCasoTres(int[] posicionCartaElegida, Tablero tablero) {
        if (cartasVistas == null) {
            cartasVistas = new HashMap<>();
        }

        // Busca una carta que tenga una pareja en el HashMap cartasVistas
        for (Map.Entry<Integer, ArrayList<int[]>> entry : cartasVistas.entrySet()) {
            if (entry.getValue().size() >= 2) {
                int[] posicion = entry.getValue().get(0);
                if(posicionCartaElegida[0] == posicion[0] && posicionCartaElegida[1] == posicion[1]){
                    return tablero.getCarta(posicion[0], posicion[1]);   
                }
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

    public Carta elegirSegundaCartaCasoTres(Carta primeraCarta, int[] posicionPrimeraCarta, Tablero tablero) {

        if ( !cartasVistas.isEmpty()){
            if(cartasVistas.get(primeraCarta.getValor()).size() > 1) {
                for (int[] posicion : cartasVistas.get(primeraCarta.getValor())) {
                    if (posicion[0] != posicionPrimeraCarta[0] || posicion[1] != posicionPrimeraCarta[1]) {
                        cartasVistas.get(primeraCarta.getValor()).remove(posicion);
                        System.out.println(primeraCarta.getValor());
                        return tablero.getCarta(posicion[0], posicion[1]);

                    }
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
        Carta segundaCarta = cartasNoEncontradas.get(indiceAleatorio);

        // Encuentra la posición de la segunda carta y actualiza cartasVistas
        for (int fila = 0; fila < tamannoFilas; fila++) {
            for (int col = 0; col < tamannoColumnas; col++) {
                if (tablero.getCarta(fila, col) == segundaCarta) {
                    actualizarCartasVistas(segundaCarta.getValor(), new int[]{fila, col});
                    break;
                }
            }
        }

        return segundaCarta;

    }

    public Carta elegirSegundaCarta(Carta primeraCarta, int[] posicionPrimeraCarta, int[] posicionSegundaCarta, Tablero tablero) {
        Carta segundaCarta;
        do {
            posicionSegundaCarta[0] = (int) (Math.random() * tablero.getTamannoFilas());
            posicionSegundaCarta[1] = (int) (Math.random() * tablero.getTamannoColumnas());
            segundaCarta = tablero.getCarta(posicionSegundaCarta[0], posicionSegundaCarta[1]);
        } while (primeraCarta == segundaCarta || segundaCarta.esParejaEncontrada() || (posicionSegundaCarta[0] == posicionPrimeraCarta[0] && posicionSegundaCarta[1] == posicionPrimeraCarta[1]));

        return segundaCarta;
    }

    public final void actualizarCartasVistas(int valorCarta, int[] posicionCarta) {
        boolean flagRepetida = false;
        if (cartasVistas == null) {
        cartasVistas = new HashMap<>();
    }
        System.out.println("EJDK");
        if (!cartasVistas.isEmpty()) {
        // Busca una carta que tenga una pareja en el HashMap cartasVistas
        for (Map.Entry<Integer, ArrayList<int[]>> entry : cartasVistas.entrySet()) {
            if (entry.getValue().size() >= 2) {
                System.out.println("EJDKSSSSSS");
                int[] posicion = entry.getValue().get(0);
                if(posicionCarta[0] == posicion[0] && posicionCarta[1] == posicion[1]){
                    flagRepetida = true;
                                 }
            }
        }            
        }


        if(!flagRepetida) {        
            if(cartasVistas != null && !cartasVistas.isEmpty()){
                ArrayList<int[]> posiciones = cartasVistas.getOrDefault(valorCarta, new ArrayList<>());
                posiciones.add(posicionCarta);
                cartasVistas.put(valorCarta, posiciones);
            }
        }

    }

    public void actualizarCartasVistasJugadorHumano(Carta carta, int fila, int columna) {
        actualizarCartasVistas(carta.getValor(), new int[]{fila, columna});
    }
}
