import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class Baraja {
    private List<Carta> cartas;

    public void llenarBaraja(){
        String[] indices = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
        String[] pintas = {"Corazon", "Diamante", "Trebol", "Espada"};
        int[] valores = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10};

        List<Carta> cartas = new ArrayList<>();

        Arrays.stream(pintas)
                .forEach(pinta -> IntStream.range(0, indices.length)
                .forEach(indice -> cartas.add(new Carta(indices[indice], pinta, valores[indice]))));

        this.cartas = cartas;
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
