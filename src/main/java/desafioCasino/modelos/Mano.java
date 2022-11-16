package desafioCasino.modelos;

import desafioCasino.modelos.Carta;

import java.util.ArrayList;
import java.util.List;

public class Mano {
    private ArrayList<Carta> cartas;

    public Mano(){
        this.cartas = new ArrayList<>();
    }

    public void addCarta(Carta nuevaCarta){
        cartas.add(nuevaCarta);
    }

    public int obtenerPuntaje(){
        return cartas.stream().mapToInt(Carta::getValor).sum();
    }

    public ArrayList<Carta> getCartas() {
        return cartas;
    }

    @Override
    public String toString() {
        return cartas.toString();
    }
}
