import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Juego {
    private Baraja baraja;
    private Jugador jugador;
    private Jugador dealer;

    public Juego() {
        this.baraja = new Baraja();
        this.jugador = new Jugador(false);
        this.dealer = new Jugador(true);
    }

    public void jugar() {
        baraja.llenarBaraja();
        baraja.barajar();

        inicializarDealer();
        inicializarJugador();
        //añadirJugadores(baraja);

        System.out.println("\nINICIA EL JUEGO\n");

        if(jugador.getManoActual().esBlackjack()) {
            System.out.println("\nGANASTE!");
            return;
        }
        turnoJugador();
    }

    private void inicializarJugador() {
        ingresarDatosJugador();
        jugador.iniciarMano(baraja);
    }

    private void ingresarDatosJugador() {
        System.out.print("Ingrese su nombre: ");
        jugador.setNombre(Utilidad.pedirString());

        System.out.print("Ingrese su monto: ");
        jugador.setMonto(Utilidad.pedirOpcionEntera());

        ingresarApuesta();

    }

    private void ingresarApuesta(){
        System.out.print("Ingrese su apuesta: ");
        int apuesta = Utilidad.pedirOpcionEntera();

        if(apuesta <= jugador.getMonto()){
            jugador.setApuesta(apuesta);
        }else {
            System.err.println("La apuesta supera su monto, por favor ingrese una cantidad menor");
            ingresarApuesta();
        }
    }

    private void inicializarDealer() {
        dealer.setNombre("Dealer");
        dealer.setMonto(5000000);
        dealer.setApuesta(250000);
        dealer.iniciarMano(baraja);
    }

    public void turnoJugador(){
        bucle:
        while (true){

            jugador.mostrarMano();
            dealer.mostrarManoOculta();

            if (jugador.getManoActual().esMayorQue21()) {
                System.out.println("\nPERDISTE!");
                break;
            }

            mostrarMenu();
            switch (Utilidad.pedirOpcionEntera()) {
                case 1 -> jugador.pedirCarta(baraja);
                case 2 ->
                {
                    bajarse();
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

    /*private void añadirJugadores(Baraja baraja){
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
    }*/

    private void bajarse(){
        dealer.mostrarMano();
        turnoDealer();
        var ganador = verificarGanador();
        mostrarResultados(ganador);
    }

    /*public HashMap<String, Integer> obtenerPuntajes() {
        return jugadores.stream()
                .collect(Collectors.toMap(Jugador::getNombre, Jugador::puntajeMano, (a, b) -> b, HashMap::new));
    }*/

    public void turnoDealer(){
        while(dealer.getManoActual().obtenerPuntaje() < 17) dealer.pedirCarta(baraja);
    }

    public Jugador verificarGanador() {
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

    public void mostrarResultados(Jugador ganador) {
        if(ganador == dealer){
            System.out.println("\nPERDISTE!");
        }else {
            System.out.println("\nGANASTE!");
        }
        mostrarPuntajes();
        //System.out.println(obtenerPuntajes());
    }

    private void mostrarPuntajes(){
        System.out.println("Puntaje Dealer = " + dealer.puntajeMano());
        System.out.println("Puntaje " + jugador.getNombre() + " = "+ jugador.puntajeMano());
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
