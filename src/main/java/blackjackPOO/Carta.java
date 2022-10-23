package blackjackPOO;

public class Carta {
    private Indice indice;
    private Pinta pinta;
    private boolean estaBocaArriba;


    public Carta(Indice indice, Pinta pinta) {
        this.indice = indice;
        this.pinta = pinta;
        this.estaBocaArriba = true;
    }

    public int getValor() {
        return indice.getValorIndice();
    }

    public String getIndice() {
        return indice.getStringIndice();
    }

    public void voltearCarta(){
        estaBocaArriba = !estaBocaArriba;
    }

    public boolean esAs(){
        return getIndice().equals("A");
    }

    @Override
    public String toString() {
        if(estaBocaArriba){
            return indice.getStringIndice() + " " + pinta.getPintaElegida();
        }else {
            return "?";
        }
    }
}