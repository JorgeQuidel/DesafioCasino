package desafioCasino;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Baraja {
    private List<Carta> cartas;

    public Baraja() {
        this.cartas = new ArrayList<>();
    }

    public void llenarBaraja(){
        Arrays.stream(Pinta.values())
                .forEach(pinta -> Arrays.stream(Indice.values())
                        .map(indice -> new Carta(indice, pinta))
                        .forEach(carta -> cartas.add(carta)));

    }

    public List<Carta> getCartas() {
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
}
