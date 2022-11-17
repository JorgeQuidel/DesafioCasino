package desafioCasino.modelos;

import desafioCasino.modelos.enums.Indice;
import desafioCasino.modelos.enums.Pinta;

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

    public Indice getIndice() {
        return indice;
    }

    public void voltearCarta(){
        estaBocaArriba = !estaBocaArriba;
    }

    public boolean esAs(){
        return getIndice().equals(Indice.AS);
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