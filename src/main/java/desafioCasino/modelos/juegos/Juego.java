package desafioCasino.modelos.juegos;

import desafioCasino.modelos.Baraja;
import desafioCasino.modelos.Jugador;
import desafioCasino.utilidades.Utilidad;

import java.util.ArrayList;
import java.util.stream.IntStream;

public abstract class Juego {
    protected ArrayList<Jugador> jugadores;
    protected Baraja baraja;
    public abstract void jugar();
    public abstract void iniciarBaraja();
    public abstract void iniciarPartida();
    public abstract void turnoJugador(Jugador jugador);
    public abstract int obtenerPuntajeJugador(Jugador jugador);
    public abstract void limpiarJuego();
    public abstract void mostrarResultadosFinales();
    public abstract void mostrarMenu();

    protected void ingresarJugadores(){
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

    protected void iniciarManoJugadores(int cantidadCartas) {
        jugadores.forEach(jugador -> repartirCartas(jugador, cantidadCartas));
    }

    protected void repartirCartas(Jugador jugador, int cantidadCartas){
        IntStream.range(0, cantidadCartas).mapToObj(i -> baraja).forEach(jugador::sacarCarta);
    }

    protected void mostrarPuntajes(){
        jugadores.stream()
                .map(jugador -> "Puntaje de " + jugador.getNombre() + ": " + obtenerPuntajeJugador(jugador))
                .forEach(System.out::println);
    }

    protected void mostrarMontos(){
        jugadores.stream()
                .map(jugador -> "Monto de " + jugador.getNombre() + ": " + jugador.getMonto())
                .forEach(System.out::println);
    }

    protected void recibirDinero(Jugador jugador) {
        System.out.println(jugador.getNombre() + " gana " + (jugador.getApuesta()*2) + " pesos");
        int monto = jugador.getMonto();
        int apuesta = jugador.getApuesta();
        jugador.setMonto(monto + (apuesta*2));
    }

    protected void pagarApuesta(Jugador jugador) {
        System.out.println(jugador.getNombre() + " pierde " + jugador.getApuesta() + " pesos");
        int monto = jugador.getMonto();
        int apuesta = jugador.getApuesta();
        jugador.setMonto(monto - apuesta);
    }

}
