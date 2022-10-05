import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Juego {
    private Baraja baraja;
    private List<Jugador> jugadores;

    public Juego(Baraja baraja, List<Jugador> jugadores) {
        this.baraja = baraja;
        this.jugadores = jugadores;
    }

    public void turnoJugador(Baraja baraja, Jugador jugador){
        switch (elegirOpcion()) {
            case 1 -> jugador.pedirCarta(baraja);
            case 2 -> jugador.bajarse();
            case 3 -> jugador.partirMano();
            case 4 -> System.out.println("Hasta pronto");
            default -> System.out.println("Por favor, ingrese una de las opciones");
        }
    }

    public HashMap<String, Integer> obtenerPuntajes() {
        return jugadores.stream()
                .collect(Collectors.toMap(Jugador::getNombre, Jugador::puntajeMano, (a, b) -> b, HashMap::new));
    }

    public void turnoDealer(Baraja baraja, Jugador dealer){
        while(dealer.getMano().obtenerPuntaje() < 17) dealer.pedirCarta(baraja);
    }

    private int elegirOpcion() {
        Scanner input = new Scanner(System.in);
        int opcion = 0;
        try{
            mostrarMenu();
            opcion = input.nextInt();
        }catch (Exception e){
            System.out.println("Por favor ingrese un numero que corresponda a una de las opciones");
            input.next();
        }
        return opcion;
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
