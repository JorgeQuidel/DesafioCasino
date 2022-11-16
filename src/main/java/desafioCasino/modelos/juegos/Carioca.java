package desafioCasino.modelos.juegos;

import desafioCasino.modelos.Baraja;
import desafioCasino.modelos.Carta;
import desafioCasino.modelos.Jugador;
import desafioCasino.modelos.enums.Indice;
import desafioCasino.utilidades.Utilidad;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

public class Carioca extends Juego {
    private Baraja pila;
    private boolean terminoPartida;
    private int partidasJugadas;
    public Carioca() {
        this.baraja = new Baraja();
        this.pila = new Baraja();
        this.jugadores = new ArrayList<>();
        this.terminoPartida = false;
        this.partidasJugadas = 0;
    }

    @Override
    public void jugar() {
        ingresarJugadores();
        do {
            iniciarBaraja();
            iniciarPartida();
            System.out.println("\nQuiere jugar de nuevo? y/n");
        }while (Utilidad.pedirStringEspecifico("y", "n").equalsIgnoreCase("y"));
    }

    @Override
    public void iniciarBaraja() {
        baraja.llenarBaraja();
        baraja.barajar();
        pila.añadirCarta(baraja.sacarCarta());
    }

    @Override
    public void iniciarPartida() {
        iniciarManoJugadores(6);
        while(!terminoPartida){
            jugadores.forEach(this::turnoJugador);
        }
    }

    @Override
    public void turnoJugador(Jugador jugador) {
        bucle:
        while (true){
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
                    if(comprobarTrio(jugador, 2)){
                        bajarse(jugador);
                        terminoPartida = true;
                        partidasJugadas++;
                        break bucle;
                    }else{
                        System.out.println("No puedes bajarte con tu mano actual");
                        break;
                    }
                default:
                    System.err.println("Por favor, ingrese una de las opciones");
            }
        }

    }

    private void mostrarCartaSuperiorPila(){
        ArrayList<Carta> cartasPila = pila.getCartas();
        if(cartasPila.size() != 0){
            System.out.println("Pila: [" + obtenerUltimaCarta(cartasPila) + "]");
        }else{
            System.out.println("Pila vacia");
        }
    }

    private Carta obtenerUltimaCarta(ArrayList<Carta> cartas){
        return cartas.get(cartas.size()-1);
    }

    private void sacarCartaPila(Jugador jugador) {
        // TODO se saca la primera pero se muestra la ultima carta
        jugador.sacarCarta(pila);
        jugador.mostrarMano();
        Carta carta = elegirCartaParaBotar(jugador);
        jugador.obtenerCartasMano().remove(carta);
        pila.getCartas().add(carta);
    }

    private void sacarCartaMazo(Jugador jugador) {
        // TODO que pasa si se acaban las cartas del mazo
        jugador.sacarCarta(baraja);
        jugador.mostrarMano();
        Carta carta = elegirCartaParaBotar(jugador);
        jugador.obtenerCartasMano().remove(carta);
        pila.getCartas().add(carta);
    }

    private Carta elegirCartaParaBotar(Jugador jugador) {
        ArrayList<Carta> cartasJugador = jugador.obtenerCartasMano();
        System.out.println("Eliga que carta botar:");
        mostrarCartasConIndice(cartasJugador);
        int indiceCarta = Utilidad.pedirOpcionEntera();
        if(indiceCarta >= 0 && indiceCarta < cartasJugador.size()){
            return cartasJugador.get(indiceCarta);
        }else{
            System.out.println("Numero fuera de rango");
            return elegirCartaParaBotar(jugador);
        }
    }

    private void mostrarCartasConIndice(ArrayList<Carta> cartas){
        for (int indice = 0; indice < cartas.size(); indice++) {
            Carta carta = cartas.get(indice);
            System.out.println(indice+ ".[" + carta + "]");
        }
    }

    private ArrayList<Integer> obtenerPuntosPorCartas(Jugador jugador){
        return jugador
                .obtenerCartasMano()
                .stream()
                .map(Carta::getValor)
                .sorted().collect(Collectors.toCollection(ArrayList::new));
    }

    private ArrayList<String> obtenerIndicesPorCartas(Jugador jugador){
        return jugador
                .obtenerCartasMano()
                .stream()
                .map(Carta::getIndice)
                .sorted().collect(Collectors.toCollection(ArrayList::new));
    }

    private boolean comprobarTrio(Jugador jugador, int cantidadDeTrios){
        int trios = 0;
        var indicesDeLaMano = obtenerIndicesPorCartas(jugador);
        for (Indice indice: Indice.values()){
            if (Collections.frequency(indicesDeLaMano, indice.getStringIndice()) == 3) {
                trios++;
            }
        }
        return trios >= cantidadDeTrios;
    }

    private void bajarse(Jugador jugador){
        System.out.println(jugador.getNombre() + " se baja\n");
    }

    @Override
    public int obtenerPuntajeJugador(Jugador jugador) {
        return 0;
    }

    @Override
    public void limpiarJuego() {

    }

    @Override
    public void mostrarResultadosFinales() {

    }

    @Override
    public void mostrarMenu() {
        System.out.print("[1].Sacar Carta Baraja\n[2].Sacar Carta Pila\n[3].Bajarse\n> ");
    }
}
