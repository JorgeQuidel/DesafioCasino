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

    public List<Carta> getCartas() {
        return cartas;
    }

    @Override
    public String toString() {
        return cartas.toString();
    }
}