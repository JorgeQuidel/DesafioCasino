import java.util.List;
import java.util.Scanner;

public class Juego {
    private Baraja baraja;
    private List<Jugador> jugadores;

    public Juego(Baraja baraja, List<Jugador> jugadores) {
        this.baraja = baraja;
        this.jugadores = jugadores;
    }

    public void turnoJugador(Baraja baraja, Jugador jugador){}

    public void turnoDealer(Baraja baraja, Jugador dealer){}

    public void verificarGanador(List<Jugador> jugadores){}

    public int elegirOpcion() {
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
