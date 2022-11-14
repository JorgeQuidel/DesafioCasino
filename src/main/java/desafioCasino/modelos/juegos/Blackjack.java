package desafioCasino.modelos.juegos;

import desafioCasino.modelos.Baraja;
import desafioCasino.modelos.Carta;
import desafioCasino.modelos.Jugador;
import desafioCasino.utilidades.Utilidad;

import java.util.ArrayList;
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
        ingresarJugadores();
        crearDealer();
        do{
            iniciarBaraja();
            iniciarManoJugadores();
            iniciarPartida();
            mostrarResultadosFinales();
            limpiarJuego();
            System.out.println("\nQuiere jugar de nuevo? y/n");
        }while (Utilidad.pedirStringEspecifico("y", "n").equalsIgnoreCase("y"));
    }

    private void ingresarJugadores(){
        do {
            crearJugador();
            System.out.println("Quiere agregar otro jugador? y/n");
        } while (Utilidad.pedirStringEspecifico("y", "n").equalsIgnoreCase("y"));
    }

    private void crearDealer() {
        dealer.setNombre("Dealer");
    }

    private void iniciarBaraja() {
        baraja.llenarBaraja();
        baraja.barajar();
    }

    private void iniciarManoJugadores() {
        jugadores.forEach(this::repartirCartas);
        repartirCartas(dealer);
        dealer.voltearCarta(1);
    }

    private void repartirCartas(Jugador jugador){
        jugador.pedirCarta(baraja);
        jugador.pedirCarta(baraja);
    }

    private void crearJugador(){
        Jugador jugador = new Jugador(false);
        ingresarNombre(jugador);
        ingresarMonto(jugador);
        ingresarApuesta(jugador);
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
                case 1 -> jugador.pedirCarta(baraja);
                case 2 -> System.out.println("No implementado");
                case 3 -> {System.out.println(jugador.getNombre() + " se baja\n"); break bucle;}
                default -> System.err.println("Por favor, ingrese una de las opciones");
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

    private void turnoDealer(){
        System.out.println("\nTurno del Dealer\n");
        dealer.voltearCarta(1);
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
            jugadoresEnJuego.forEach(this::compararPuntaje);
        }
    }

    private void compararPuntaje(Jugador jugador) {
        if(obtenerPuntajeJugador(dealer) > obtenerPuntajeJugador(jugador)) {
            pagarApuesta(jugador);
        } else if(obtenerPuntajeJugador(dealer) < obtenerPuntajeJugador(jugador)) {
            recibirDinero(jugador);
        } else {
            System.out.println(jugador.getNombre() + " recupera su apuesta");
        }
    }

    private void recibirDinero(Jugador jugador) {
        System.out.println(jugador.getNombre() + " gana " + (jugador.getApuesta()*2) + " pesos");
        int monto = jugador.getMonto();
        int apuesta = jugador.getApuesta();
        jugador.setMonto(monto + (apuesta*2));
    }

    private void pagarApuesta(Jugador jugador) {
        System.out.println(jugador.getNombre() + " pierde " + jugador.getApuesta() + " pesos");
        int monto = jugador.getMonto();
        int apuesta = jugador.getApuesta();
        jugador.setMonto(monto - apuesta);
    }

    private void mostrarResultadosFinales(){
        System.out.println("------------------------------------------------------------------------");
        mostrarPuntajes();
        System.out.println("------------------------------------------------------------------------");
        mostrarMontosFinales();
    }

    private void mostrarPuntajes(){
        System.out.println("Puntaje del Dealer " + ": " + obtenerPuntajeJugador(dealer));
        jugadores.stream()
                .map(jugador -> "Puntaje de " + jugador.getNombre() + ": " + obtenerPuntajeJugador(jugador))
                .forEach(System.out::println);
    }

    private void mostrarMontosFinales(){
        jugadores.stream()
                .map(jugador -> "Monto final de " + jugador.getNombre() + ": " + jugador.getMonto())
                .forEach(System.out::println);
    }

    private void limpiarJuego() {
        jugadores.forEach(jugador -> jugador.getMano().getCartas().clear());
        baraja.getCartas().clear();
        dealer.getMano().getCartas().clear();
    }

    private void mostrarMenu(){
        System.out.print("[1].Pedir Carta\n[2].Partir Mano\n[3].Bajarse\n> ");
    }
}
