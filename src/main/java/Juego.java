import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Juego {
    private Baraja baraja;
    private List<Jugador> jugadores;
    private Jugador dealer;

    public Juego() {
        this.baraja = new Baraja();
        this.jugadores = new ArrayList<>();
    }

    public void jugar() {
        baraja.llenarBaraja();
        baraja.barajar();

        inicializarDealer(baraja);
        añadirJugadores(baraja);

        if(jugadores.get(0).getMano().esBlackjack()) {
            System.out.println("\nGANASTE!");
            return;
        }
        turnoJugador(baraja, dealer, jugadores.get(0));
    }

    private void inicializarDealer(Baraja baraja) {
        dealer = new Jugador(true);
        dealer.setNombre("Dealer");
        dealer.setMonto(5000000);
        dealer.setApuesta(250000);
        dealer.iniciarMano(baraja);
    }

    public void turnoJugador(Baraja baraja, Jugador dealer, Jugador jugador){
        bucle:
        while (true){

            jugador.mostrarMano();
            dealer.mostrarManoOculta();

            if (jugador.getMano().esMayorQue21()) {
                System.out.println("\nPERDISTE!");
                break;
            }

            mostrarMenu();
            switch (Utilidad.pedirOpcionEntera()) {
                case 1 -> jugador.pedirCarta(baraja);
                case 2 ->
                {
                    bajarse(jugador, dealer);
                    break bucle;
                }
                case 3 -> System.out.println("No implementado todavia");
                case 4 -> {
                    System.out.println("Hasta pronto");
                    break bucle;
                }
                default -> System.out.println("Por favor, ingrese una de las opciones");
            }
        }
    }

    private void añadirJugadores(Baraja baraja){
        do {
            var jugador = new Jugador(false);

            System.out.print("Ingrese su nombre: ");
            jugador.setNombre(Utilidad.pedirString());

            System.out.print("Ingrese su monto: ");
            jugador.setMonto(Utilidad.pedirOpcionEntera());

            System.out.print("Ingrese su apuesta: ");
            jugador.setApuesta(Utilidad.pedirOpcionEntera());

            jugador.iniciarMano(baraja);
            jugadores.add(jugador);
            System.out.println("Quiere agregar otro jugador? y/n");
        } while (Utilidad.pedirStringEspecifico("y", "n").equalsIgnoreCase("y"));
    }

    private void bajarse(Jugador jugador, Jugador dealer){
        dealer.mostrarMano();
        turnoDealer(baraja, dealer);
        var ganador = verificarGanador(jugador, dealer);
        mostrarResultados(jugador, dealer, ganador);
    }

    public HashMap<String, Integer> obtenerPuntajes() {
        return jugadores.stream()
                .collect(Collectors.toMap(Jugador::getNombre, Jugador::puntajeMano, (a, b) -> b, HashMap::new));
    }

    public void turnoDealer(Baraja baraja, Jugador dealer){
        while(dealer.getMano().obtenerPuntaje() < 17) dealer.pedirCarta(baraja);
    }

    public static Jugador verificarGanador(Jugador jugador, Jugador dealer) {
        int puntosJugador = jugador.puntajeMano();
        int puntosDealer = dealer.puntajeMano();

        if(puntosDealer>21){
            return jugador;
        }else if(puntosJugador>21) {
            return dealer;
        }else if(puntosDealer>puntosJugador) {
            return dealer;
        }else if (puntosDealer<puntosJugador) {
            return jugador;
        }else {
            return dealer;
        }
    }

    public void mostrarResultados(Jugador jugador, Jugador dealer, Jugador ganador) {
        if(ganador == dealer){
            System.out.println("\nPERDISTE!");
        }else {
            System.out.println("\nGANASTE!");
        }
        System.out.println("{Dealer=" + dealer.puntajeMano() + "}");
        System.out.println(obtenerPuntajes());
    }

    public void mostrarMenu(){
        System.out.print("""
                [1].Pedir Carta
                [2].Bajarte
                [3].Partir Mano
                [4].Salir del juego
                """.concat("> "));
    }

}
