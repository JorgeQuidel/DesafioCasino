import java.util.List;

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

    public void mostrarMano(){
        System.out.println(nombre + ": " + mano);
    }

    public Mano getMano() {
        return mano;
    }

    public int getApuesta() {
        return apuesta;
    }

    public void iniciarMano(Baraja baraja) {
        for (int i = 0; i < 2; i++) pedirCarta(baraja);
    }

    public void pedirCarta(Baraja baraja){
        mano.addCarta(baraja.sacarCarta());
    }

    public void setApuesta(int apuesta) {
        this.apuesta = apuesta;
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
