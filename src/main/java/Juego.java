import java.util.List;

public class Juego {
    private Baraja baraja;
    private List<Jugador> jugadores;

    public Juego(Baraja baraja, List<Jugador> jugadores) {
        this.baraja = baraja;
        this.jugadores = jugadores;
    }

    public void turnoJugador(Baraja baraja, Jugador jugador){}

    public void turnoDealer(Baraja baraja, Jugador dealer){}


}
