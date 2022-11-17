package desafioCasino.modelos;

import desafioCasino.modelos.enums.Indice;
import desafioCasino.modelos.enums.Pinta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.IntStream;

public class Baraja {
    private ArrayList<Carta> cartas;

    public Baraja() {
        this.cartas = new ArrayList<>();
    }

    public void llenarBaraja(int cantidadMazos){
        IntStream.range(0, cantidadMazos).forEach(i -> llenarBaraja());
    }
    public void llenarBaraja(){
        Arrays.stream(Pinta.values())
                .filter(pinta -> !pinta.equals(Pinta.JOKER))
                .forEach(pinta -> Arrays.stream(Indice.values())
                        .filter(indice -> !indice.equals(Indice.JOKER))
                        .map(indice -> new Carta(indice, pinta)).forEach(carta -> cartas.add(carta)));
    }

    public void añadirJoker(int cantidad){
        for (int i = 0; i < cantidad; i++) {
            añadirCarta(new Carta(Indice.JOKER, Pinta.JOKER));
        }
    }

    public void añadirCarta(Carta carta){
        cartas.add(carta);
    }

    public ArrayList<Carta> getCartas() {
        return cartas;
    }

    public void barajar() {
        Collections.shuffle(this.cartas);
    }

    public Carta sacarCarta(){
        var carta = cartas.get(0);
        cartas.remove(0);
        return carta;
    }

    public Carta sacarCarta(int indice){
        var carta = cartas.get(indice);
        cartas.remove(indice);
        return carta;
    }

    public int cantidadCartas(){
        return cartas.size();
    }

    public boolean noQuedanCartas(){
        return cantidadCartas() == 0;
    }

    public void limpiarBaraja(){
        cartas.clear();
    }
}
