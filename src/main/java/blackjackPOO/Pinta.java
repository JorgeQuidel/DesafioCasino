package blackjackPOO;

public enum Pinta {
    CORAZON("Corazon"),
    ESPADA("Espada"),
    DIAMANTE("Diamante"),
    TREBOL("Trebol");

    private final String pintaElegida;

    Pinta(String pintaElegida) {
        this.pintaElegida = pintaElegida;
    }

    public String getPintaElegida(){
        return pintaElegida;
    }
}
