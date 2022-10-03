public class Jugador {
    private String nombre;
    private boolean esDealer;
    private Mano mano;
    private int apuesta;
    private int monto;

    public Jugador(String nombre, boolean esDealer, int monto) {
        this.nombre = nombre;
        this.esDealer = esDealer;
        this.monto = monto;
        this.mano = new Mano();
    }

    public void partirMano(){}

    public void bajarse(){}

    public void iniciarMano(Baraja baraja) {
        for (int i = 0; i < 2; i++) pedirCarta(baraja);
    }

    public void pedirCarta(Baraja baraja){
        mano.añadirCarta(baraja.sacarCarta());
    }

    public String getNombre() {
        return nombre;
    }

    public Mano getMano() {
        return mano;
    }

    public int getApuesta() {
        return apuesta;
    }

    public void setApuesta(int apuesta) {
        this.apuesta = apuesta;
    }

    public void mostrarMano(){
        System.out.println(nombre + ": " + mano);
    }
    @Override
    public String toString() {
        return "Jugador{" +
                "nombre='" + nombre + '\'' +
                ", esDealer=" + esDealer +
                ", mano=" + mano +
                ", apuesta=" + apuesta +
                ", monto=" + monto +
                '}';
    }
}
