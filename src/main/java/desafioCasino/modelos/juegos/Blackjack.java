package desafioCasino.modelos.juegos;

import desafioCasino.modelos.Baraja;
import desafioCasino.modelos.Carta;
import desafioCasino.modelos.Jugador;
import desafioCasino.utilidades.Utilidad;

import java.util.ArrayList;
import java.util.List;

public class Blackjack extends Juego {
    private Jugador dealer;

    public Blackjack() {
        this.baraja = new Baraja();
        this.jugadores = new ArrayList<>();
        this.dealer = new Jugador(true);
        this.terminoTurno = false;
    }

    @Override
    public void jugar() {
        ingresarJugadores();
        do{
            iniciarBaraja();
            System.out.println(baraja.getCartas().size());
            iniciarManoJugadores(2);
            iniciarDealer();
            iniciarPartida();
            mostrarResultadosFinales();
            limpiarJuego();
            System.out.println("\nQuiere jugar de nuevo? y/n");
        }while (Utilidad.pedirStringEspecifico("y", "n").equalsIgnoreCase("y"));
    }

    private void iniciarDealer() {
        dealer.setNombre("Dealer");
        repartirCartas(dealer, 2);
        dealer.voltearCarta(1);
    }

    public void iniciarBaraja() {
        baraja.llenarBaraja();
        baraja.barajar();
    }

    public void iniciarPartida() {
        System.out.println("\nINICIA EL JUEGO\n");
        jugadores.forEach(this::turnoJugador);
        turnoDealer();
        verficarGanador();
    }

    public void turnoJugador(Jugador jugador){
        terminoTurno = false;
        comprobarBlackjack(jugador);
        while (!terminoTurno){
            jugador.mostrarMano();
            dealer.mostrarMano();
            if (obtenerPuntajeJugador(jugador)>21) {System.out.println(jugador.getNombre() + " a PERDIDO!\n"); break;}
            mostrarMenu();
            switch (Utilidad.pedirOpcionEntera()) {
                case 1 -> jugador.sacarCarta(baraja);
                case 2 -> System.out.println("No implementado");
                case 3 -> bajarse(jugador);
                default -> System.err.println("Por favor, ingrese una de las opciones");
            }
        }
    }

    public void comprobarBlackjack(Jugador jugador){
        if(obtenerPuntajeJugador(jugador) == 21) {
            mostrarBlackjack(jugador);
            terminoTurno = true;
        }
    }

    @Override
    public void bajarse(Jugador jugador) {
        System.out.println(jugador.getNombre() + " se baja\n");
        terminoTurno = true;
    }

    public int obtenerPuntajeJugador(Jugador jugador){
        int puntaje = jugador.obtenerPuntajeMano();
        List<Carta> cartas = jugador.obtenerCartasMano();
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
        while(obtenerPuntajeJugador(dealer) < 17 && !baraja.noQuedanCartas()) {
            dealer.sacarCarta(baraja);
            dealer.mostrarMano();
        }
        System.out.println("------------------------------------------------------------------------");
    }

    private void obtenerJugadoresQueSiguenEnJuego(ArrayList<Jugador> jugadoresEnJuego) {
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
        obtenerJugadoresQueSiguenEnJuego(jugadoresEnJuego);
        if (obtenerPuntajeJugador(dealer) > 21) {
            jugadoresEnJuego.forEach(this::recibirDinero);
        }else{
            jugadoresEnJuego.forEach(this::compararPuntaje);
        }
    }

    private void compararPuntaje(Jugador jugador) {
        int puntajeJugador = obtenerPuntajeJugador(jugador);
        int puntajeDealer = obtenerPuntajeJugador(dealer);
        if(puntajeDealer > puntajeJugador) {
            pagarApuesta(jugador);
        } else if(puntajeDealer < puntajeJugador || puntajeJugador == 21) {
            recibirDinero(jugador);
        } else {
            System.out.println(jugador.getNombre() + " recupera su apuesta");
        }
    }

    private void mostrarPuntajes(){
        jugadores.stream()
                .map(jugador -> "Puntaje de " + jugador.getNombre() + ": " + obtenerPuntajeJugador(jugador))
                .forEach(System.out::println);
    }

    public void mostrarResultadosFinales(){
        System.out.println("------------------------------------------------------------------------");
        System.out.println("Puntaje del Dealer " + ": " + obtenerPuntajeJugador(dealer));
        mostrarPuntajes();
        System.out.println("------------------------------------------------------------------------");
        mostrarMontos();
    }

    public void limpiarJuego() {
        jugadores.forEach(Jugador::limpiarMano);
        baraja.limpiarBaraja();
        dealer.limpiarMano();
    }

    public void mostrarMenu(){
        System.out.print("[1].Pedir Carta\n[2].Partir Mano\n[3].Bajarse\n> ");
    }
}
