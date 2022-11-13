package desafioCasino;

public enum Indice {
    AS(11, "A"),
    DOS(2, "2"),
    TRES(3, "3"),
    CUATRO(4, "4"),
    CINCO(5, "5"),
    SEIS(6, "6"),
    SIETE(7, "7"),
    OCHO(8, "8"),
    NUEVE(9, "9"),
    DIEZ(10, "10"),
    JOTA(10, "J"),
    QUINA(10, "Q"),
    KAISER(10, "K");

    private final int valorIndice;
    private final String stringIndice;

    Indice(int valorIndice, String stringIndice) {
        this.valorIndice = valorIndice;
        this.stringIndice = stringIndice;
    }

    public int getValorIndice() {
        return valorIndice;
    }

    public String getStringIndice(){
        return stringIndice;
    }
}
