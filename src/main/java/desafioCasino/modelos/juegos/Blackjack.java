package desafioCasino.modelos.juegos;

import desafioCasino.modelos.Baraja;
import desafioCasino.modelos.Carta;
import desafioCasino.modelos.Jugador;
import desafioCasino.utilidades.Utilidad;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Blackjack extends Juego {
    private Baraja baraja;
    private ArrayList<Jugador> jugadores;
    private Jugador dealer;

    public Blackjack() {
        this.baraja = new Baraja();
        this.jugadores = new ArrayList<>();
        this.dealer = new Jugador(true);
    }

    @Override
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
        // TODO que no se repita el nombre de otro jugador
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
        if(obtenerPuntajeJugador(jugador) == 21) {mostrarBlackjack(jugador); return;}
        bucle:
        while (true){
            jugador.mostrarMano();
            dealer.mostrarMano();
            if (obtenerPuntajeJugador(jugador)>21) {System.out.println(jugador.getNombre() + " a PERDIDO!\n"); break;}
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

    private int obtenerPuntajeJugador(Jugador jugador){
        int puntaje = jugador.obtenerPuntajeMano();
        List<Carta> cartas = jugador.getMano().getCartas();
        for (Carta carta : cartas) {
            if(carta.esAs() && puntaje > 21){
                puntaje -=10;
            }
        }
        return puntaje;
    }

    private void mostrarBlackjack(Jugador jugador) {
        jugador.mostrarMano();
        System.out.println(jugador.getNombre() + " obtuvo BLACKJACK!\n");
    }

    private HashMap<String, Integer> obtenerPuntajes() {
        HashMap<String, Integer> map = new HashMap<>();
        for (Jugador jugador : jugadores) {
            map.put(jugador.getNombre(), obtenerPuntajeJugador(jugador));
        }
        return map;
    }

    private void turnoDealer(){
        System.out.println("\nTurno del Dealer\n");
        dealer.getMano().getCartas().get(1).voltearCarta();
        dealer.mostrarMano();
        while(obtenerPuntajeJugador(dealer) < 17) {
            dealer.pedirCarta(baraja);
            dealer.mostrarMano();
        }
        System.out.println("------------------------------------------------------------------------");
    }

    private void obtenerJugadoresEnJuego(ArrayList<Jugador> jugadoresEnJuego) {
        for (Jugador jugador : jugadores) {
            if (obtenerPuntajeJugador(jugador) > 21) {
                pagarApuesta(jugador);
            } else {
                jugadoresEnJuego.add(jugador);
            }
        }
    }

    public void verficarGanador(){
        ArrayList<Jugador> jugadoresEnJuego = new ArrayList<>();
        obtenerJugadoresEnJuego(jugadoresEnJuego);
        if (obtenerPuntajeJugador(dealer) > 21) {
            jugadoresEnJuego.forEach(this::recibirDinero);
        }else{
            jugadoresEnJuego.forEach(this::compararJugadores);
        }
    }

    private void compararJugadores(Jugador jugador) {
        if(obtenerPuntajeJugador(dealer) > obtenerPuntajeJugador(jugador)) {
            pagarApuesta(jugador);
        } else if(obtenerPuntajeJugador(dealer) < obtenerPuntajeJugador(jugador)) {
            recibirDinero(jugador);
        } else {
            System.out.println("Empate, a " + jugador.getNombre() + " se le devuelve su apuesta");
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
        System.out.println("Puntaje del Dealer: " + obtenerPuntajeJugador(dealer));
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
