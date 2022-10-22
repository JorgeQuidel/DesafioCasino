package blackjackPOO;

public class Carta {
    private String indice;
    private String pinta;
    private int valor;
    private boolean estaBocaArriba;


    public Carta(String indice, String pinta, int valor) {
        this.indice = indice;
        this.pinta = pinta;
        this.valor = valor;
        this.estaBocaArriba = true;
    }

    public int getValor() {
        return valor;
    }

    public String getIndice() {
        return indice;
    }

    public void voltearCarta(){
        estaBocaArriba = !estaBocaArriba;
    }

    public boolean esAs(){
        return indice.equals("A");
    }

    @Override
    public String toString() {
        if(estaBocaArriba){
            return indice + " " + pinta;
        }else {
            return "?";
        }
    }
}