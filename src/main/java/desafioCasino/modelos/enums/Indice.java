package desafioCasino.modelos.enums;

public enum Indice {
    AS(11),
    DOS(2),
    TRES(3),
    CUATRO(4),
    CINCO(5),
    SEIS(6),
    SIETE(7),
    OCHO(8),
    NUEVE(9),
    DIEZ(10),
    JOTA(10),
    QUINA(10),
    KAISER(10),
    JOKER(0);

    private final int valorIndice;

    Indice(int valorIndice) {
        this.valorIndice = valorIndice;
    }

    public int getValorIndice() {
        return valorIndice;
    }
}
