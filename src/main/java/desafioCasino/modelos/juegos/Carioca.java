package desafioCasino.modelos.juegos;

import desafioCasino.modelos.Baraja;
import desafioCasino.modelos.Carta;
import desafioCasino.modelos.Jugador;
import desafioCasino.modelos.enums.Indice;
import desafioCasino.utilidades.Utilidad;

import java.util.ArrayList;
import java.util.Collections;

public class Carioca extends Juego {
    private Baraja pila;
    private boolean terminoPartida;
    public Carioca() {
        this.baraja = new Baraja();
        this.pila = new Baraja();
        this.jugadores = new ArrayList<>();
        this.terminoPartida = false;
    }

    @Override
    public void jugar() {
        ingresarJugadores();
        do {
            iniciarBaraja();
            iniciarPartida();
            limpiarJuego();
            System.out.println("\nQuiere jugar de nuevo? y/n");
        }while (Utilidad.pedirStringEspecifico("y", "n").equalsIgnoreCase("y"));
    }

    @Override
    public void iniciarBaraja() {
        baraja.llenarBaraja(2);
        baraja.añadirJoker(4);
        baraja.barajar();
        pila.añadirCarta(baraja.sacarCarta());
    }

    @Override
    public void iniciarPartida() {
        mostrarCombinacionPartida();
        iniciarManoJugadores(6);
        while(!terminoPartida){
            jugadores.forEach(this::turnoJugador);
        }
    }

    @Override
    public void turnoJugador(Jugador jugador) {
        bucle:
        while (!terminoPartida){
            mostrarCartaSuperiorPila();
            jugador.mostrarMano();
            mostrarMenu();
            switch (Utilidad.pedirOpcionEntera()) {
                case 1:
                    sacarCartaMazo(jugador);
                    break bucle;
                case 2:
                    sacarCartaPila(jugador);
                    break bucle;
                case 3:
                    comprobarTrios(jugador);
                    break;
                default:
                    System.err.println("Por favor, ingrese una de las opciones");
            }
        }

    }

    private void mostrarCartaSuperiorPila(){
        ArrayList<Carta> cartasPila = pila.getCartas();
        if(cartasPila.size() != 0){
            System.out.println("Pila: [" + cartasPila.get(cartasPila.size()-1) + "]");
        }else{
            System.out.println("Pila vacia");
        }
    }

    private void sacarCartaPila(Jugador jugador) {
        jugador.sacarUltimaCarta(pila);
        menuBotarCarta(jugador);
    }

    private void menuBotarCarta(Jugador jugador) {
        ArrayList<Carta> cartasJugador = jugador.obtenerCartasMano();
        do{
            jugador.mostrarMano();
            System.out.println("Eliga que carta botar:");
            mostrarCartasConIndice(cartasJugador);
            System.out.println((cartasJugador.size()+1) + ".Bajarse");
            elegirOpcionMenuBotarCarta(jugador, cartasJugador);
        }while(jugador.obtenerCartasMano().size() != 6 && !terminoPartida);
    }

    private void elegirOpcionMenuBotarCarta(Jugador jugador, ArrayList<Carta> cartasJugador){
        int indiceCarta = Utilidad.pedirOpcionEntera() - 1;
        if(indiceCarta >= 0 && indiceCarta < cartasJugador.size()){
            botarCarta(jugador, cartasJugador.get(indiceCarta));
        } else if (indiceCarta == cartasJugador.size()) {
            comprobarTrios(jugador);
        } else{
            System.out.println("Numero fuera de rango");
        }
    }

    private void comprobarTrios(Jugador jugador){
        if(hayTrios(jugador, 2)){
            bajarse(jugador);
        }else{
            System.out.println("No puedes bajarte con tu mano actual");
        }
    }

    public void botarCarta(Jugador jugador, Carta carta){
        jugador.obtenerCartasMano().remove(carta);
        pila.getCartas().add(carta);
    }

    private void sacarCartaMazo(Jugador jugador) {
        jugador.sacarCarta(baraja);
        menuBotarCarta(jugador);
    }

    private void mostrarCartasConIndice(ArrayList<Carta> cartas){
        for (int indice = 0; indice < cartas.size(); indice++) {
            Carta carta = cartas.get(indice);
            System.out.println((indice + 1) + ".[" + carta + "]");
        }
    }

    private boolean hayTrios(Jugador jugador, int cantidadDeTrios){
        int trios = 0;
        var indicesDeLaMano = obtenerIndicesPorCartas(jugador);
        for (Indice indice: Indice.values()){
            if (esTrio(indicesDeLaMano, indice)) {
                trios++;
            }else if(esTrioConJoker(indicesDeLaMano, indice)){
                trios++;
                indicesDeLaMano.remove(Indice.JOKER);
            }
        }
        return trios >= cantidadDeTrios;
    }

    private boolean esTrioConJoker(ArrayList<Indice> indices, Indice indice) {
        return Collections.frequency(indices, indice) == 2 && indices.contains(Indice.JOKER);
    }

    private boolean esTrio(ArrayList<Indice> indices, Indice indice) {
        return Collections.frequency(indices, indice) == 3;
    }

    private void bajarse(Jugador jugador){
        System.out.println(jugador.getNombre() + " se baja\n");
        recibirDinero(jugador);
        jugadores.stream().filter(jugadorRestante -> !jugadorRestante.equals(jugador)).forEach(this::pagarApuesta);
        terminoPartida = true;
    }

    @Override
    public void limpiarJuego() {
        baraja.limpiarBaraja();
        pila.limpiarBaraja();
        jugadores.forEach(Jugador::limpiarMano);
        terminoPartida = false;
    }

    public void mostrarCombinacionPartida(){
        System.out.println("CONSIGUE 2 TRIOS DE CARTAS DEL MISMO INDICE\n");
    }

    @Override
    public void mostrarMenu() {
        System.out.print("[1].Sacar Carta Baraja\n[2].Sacar Carta Pila\n[3].Bajarse\n> ");
    }
}
