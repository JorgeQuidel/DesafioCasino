import java.util.List;

public class Tablero {
    public static void main(String[] args) {
        Baraja b = new Baraja();
        b.llenarBaraja();
        b.barajar();

        Jugador jugador1 = new Jugador("Jorge", false, 500000);
        jugador1.iniciarMano(b);
        jugador1.setApuesta(200000);

        Jugador dealer = new Jugador("Dealer", true, 1000000);
        dealer.iniciarMano(b);
        dealer.mostrarMano();

        jugador1.mostrarMano();

        jugador1.pedirCarta(b);

        jugador1.mostrarMano();

        System.out.println(jugador1.getMano().obtenerPuntaje());

        Juego j = new Juego(b, List.of(jugador1));
        j.mostrarMenu();
    }
}
