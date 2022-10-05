public class Carta {
    private String indice;
    private String pinta;
    private int valor;


    public Carta(String indice, String pinta, int valor) {
        this.indice = indice;
        this.pinta = pinta;
        this.valor = valor;
    }

    public int getValor() {
        return valor;
    }

    public String getIndice() {
        return indice;
    }

    @Override
    public String toString() {
        return indice + " " + pinta;
    }
}