package blackjackPOO;

import java.util.ArrayList;
import java.util.List;

public class Mano {
    private List<Carta> cartas;

    public Mano(){
        this.cartas = new ArrayList<>();
    }

    public void addCarta(Carta nuevaCarta){
        cartas.add(nuevaCarta);
    }

    public int obtenerPuntaje(){
        return cartas.stream().mapToInt(Carta::getValor).sum();
    }

    public boolean esBlackjack(){
        if(cartas.size()>2){
            return false;
        }
        return contieneAs() && contieneValor10();
    }

    public boolean esMayorQue21(){
        return obtenerPuntaje() > 21;
    }

    public boolean contieneAs(){
        return cartas.stream().anyMatch(Carta::esAs);
    }

    public boolean contieneValor10() {
        return cartas.stream().anyMatch(value -> value.getValor() == 10);
    }

    public List<Carta> getCartas() {
        return cartas;
    }

    @Override
    public String toString() {
        return cartas.toString();
    }
}
