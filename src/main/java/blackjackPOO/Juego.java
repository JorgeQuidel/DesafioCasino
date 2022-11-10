package blackjackPOO;

import java.util.ArrayList;
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
        añadirJugadores();
        iniciarPartida();
    }

    private void iniciarBaraja() {
        baraja.llenarBaraja();
        baraja.barajar();
    }

    private void crearDealer() {
        dealer.setNombre("Dealer");
        dealer.setMonto(5000000);
        dealer.setApuesta(250000);
        dealer.iniciarMano(baraja);
    }

    private void añadirJugadores(){
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
        System.out.print("Ingrese su nombre: ");
        jugador.setNombre(Utilidad.pedirStringNoVacio());
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

        for (Jugador jugador: jugadores) {
            turnoJugador(jugador);
        }
        turnoDealer();
        System.out.println(obtenerPuntajes());
    }

    private void turnoJugador(Jugador jugador){
        if(jugador.obtenerPuntajeMano() == 21) {System.out.println("\nGANASTE!"); return;}
        System.out.println("Turno de " + jugador.getNombre() + "\n");
        bucle:
        while (true){
            jugador.mostrarMano();
            dealer.mostrarMano();
            if (jugador.obtenerPuntajeMano()>21) {System.out.println("\nPERDISTE!\n"); break;}
            mostrarMenu();
            switch (Utilidad.pedirOpcionEntera()) {
                case 1 -> jugador.pedirCarta(baraja);
                case 2 -> {bajarse(jugador); break bucle;}
                case 3 -> System.out.println("No implementado todavia");
                case 4 -> {System.out.println("Hasta pronto"); break bucle;}
                default -> System.err.println("Por favor, ingrese una de las opciones");
            }
        }
    }

    private void bajarse(Jugador jugador){

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

    private void verficarGanador(){

    }

    private void mostrarMenu(){
        System.out.print("[1].Pedir Carta\n[2].Bajarte\n[3].Partir Mano\n[4].Salir del juego\n> ");
    }

}
