package blackjackPOO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.stream.Collectors;

public class Juego {
    private Baraja baraja;
    private ArrayList<Jugador> jugadores;
    private Jugador dealer;

    public Juego() {
        this.baraja = new Baraja();
        this.jugadores = new ArrayList<>();
        this.dealer = new Jugador(true);
    }

    public void jugar() {
        iniciarBaraja();
        crearDealer();
        ingresarJugadores();
        iniciarPartida();
        mostrarResultadosFinales();
    }

    private void iniciarBaraja() {
        baraja.llenarBaraja();
        baraja.barajar();
    }

    private void crearDealer() {
        dealer.setNombre("Dealer");
        dealer.iniciarMano(baraja);
    }

    private void ingresarJugadores(){
        do {
            crearJugador();
            System.out.println("Quiere agregar otro jugador? y/n");
        } while (Utilidad.pedirStringEspecifico("y", "n").equalsIgnoreCase("y"));
    }

    private void crearJugador(){
        Jugador jugador = new Jugador(false);
        ingresarNombre(jugador);
        ingresarMonto(jugador);
        ingresarApuesta(jugador);
        jugador.iniciarMano(baraja);
        jugadores.add(jugador);
    }

    private void ingresarNombre(Jugador jugador){
        System.out.print("Ingrese su nombre (limite de 20 caracteres): ");
        jugador.setNombre(Utilidad.pedirStringNoVacio(20));
    }

    private void ingresarMonto(Jugador jugador){
        System.out.print("Ingrese su monto: ");
        jugador.setMonto(Utilidad.pedirOpcionEnteraPositiva());
    }

    private void ingresarApuesta(Jugador jugador){
        System.out.print("Ingrese su apuesta: ");
        jugador.setApuesta(Utilidad.pedirOpcionPositivaMenorA(jugador.getMonto()));
    }

    private void iniciarPartida() {
        System.out.println("\nINICIA EL JUEGO\n");
        jugadores.forEach(this::turnoJugador);
        turnoDealer();
        verficarGanador();
    }

    private void turnoJugador(Jugador jugador){
        System.out.println("Turno de " + jugador.getNombre() + "\n");
        if(jugador.obtenerPuntajeMano() == 21) {System.out.println(jugador.getNombre() + " obtuvo BLACKJACK!"); return;}
        bucle:
        while (true){
            jugador.mostrarMano();
            dealer.mostrarMano();
            if (jugador.obtenerPuntajeMano()>21) {System.out.println(jugador.getNombre() + " a PERDIDO!\n"); break;}
            mostrarMenu();
            switch (Utilidad.pedirOpcionEntera()) {
                case 1:
                    jugador.pedirCarta(baraja);
                    break;
                case 2:
                    break bucle;
                case 3:
                    System.out.println("No implementado");
                    break;
                default:
                    System.err.println("Por favor, ingrese una de las opciones");
                    break;
            }
        }
    }

    private HashMap<String, Integer> obtenerPuntajes() {
        return jugadores.stream()
                .collect(Collectors
                        .toMap(Jugador::getNombre, Jugador::obtenerPuntajeMano, (a, b) -> b, HashMap::new));
    }

    private void turnoDealer(){
        System.out.println("\nTurno del Dealer\n");
        dealer.getMano().getCartas().get(1).voltearCarta();
        dealer.mostrarMano();
        while(dealer.obtenerPuntajeMano() < 17) {
            dealer.pedirCarta(baraja);
            dealer.mostrarMano();
        }
    }

    private void ordenarJugadoresPorPuntaje(ArrayList<Jugador> jugadores){
        jugadores.sort(Comparator.comparing(Jugador::obtenerPuntajeMano));
        Collections.reverse(jugadores);
    }

    private void verficarGanador(){
        System.out.println("------------------------------------------------------------------------");
        ArrayList<Jugador> jugadoresEnJuego = new ArrayList<>();
        obtenerJugadoresEnJuego(jugadoresEnJuego);
        System.out.println("------------------------------------------------------------------------");
        if (dealer.obtenerPuntajeMano() <= 21) jugadoresEnJuego.add(dealer);
        ordenarJugadoresPorPuntaje(jugadoresEnJuego);
        distribuirDinero(jugadoresEnJuego);
    }

    private void distribuirDinero(ArrayList<Jugador> jugadoresEnJuego) {
        for (Jugador jugador: jugadoresEnJuego) {
            if(jugadoresEnJuego.get(0).obtenerPuntajeMano() > jugador.obtenerPuntajeMano()) {
                pagarApuesta(jugador);
            } else {
                recibirDinero(jugador);
            }
        }
    }

    private void obtenerJugadoresEnJuego(ArrayList<Jugador> jugadoresEnJuego) {
        for (Jugador jugador : jugadores) {
            if (jugador.obtenerPuntajeMano() > 21) {
                pagarApuesta(jugador);
            } else {
                jugadoresEnJuego.add(jugador);
            }
        }
    }

    private void recibirDinero(Jugador jugador) {
        if(jugador.equals(dealer)) return;
        System.out.println("A " + jugador.getNombre() + " se le paga " + (jugador.getApuesta()*2) + " pesos");
        int monto = jugador.getMonto();
        int apuesta = jugador.getApuesta();
        jugador.setMonto(monto + (apuesta*2));
    }

    private void pagarApuesta(Jugador jugador) {
        System.out.println(jugador.getNombre() + " tiene que pagar " + jugador.getApuesta() + " pesos");
        int monto = jugador.getMonto();
        int apuesta = jugador.getApuesta();
        jugador.setMonto(monto - apuesta);
    }

    private void mostrarPuntajes(){
        HashMap<String, Integer> puntajes = obtenerPuntajes();
        System.out.println("Puntaje del Dealer: " + dealer.obtenerPuntajeMano());
        puntajes.forEach((nombre, puntaje) -> System.out.println("Puntaje de " + nombre + ": " + puntaje));
    }

    private void mostrarResultadosFinales(){
        System.out.println("------------------------------------------------------------------------");
        mostrarPuntajes();
        System.out.println("------------------------------------------------------------------------");
        mostrarMontosFinales();
    }

    private void mostrarMontosFinales(){
        jugadores.stream().map(jugador -> "Monto final de " + jugador.getNombre() + ": " + jugador.getMonto())
                .forEach(System.out::println);
    }

    private void mostrarMenu(){
        System.out.print("[1].Pedir Carta\n[2].Bajarte\n[3].Partir Mano\n> ");
    }
}
