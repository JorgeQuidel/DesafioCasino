import java.util.ArrayList;
import java.util.List;

public class Jugador {
    private String nombre;
    private boolean esDealer;
    private Mano mano;
    private List<Mano> manos;
    private int apuesta;
    private int monto;

    public Jugador(boolean esDealer) {
        this.esDealer = esDealer;
        this.mano = new Mano();
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setMonto(int monto) {
        this.monto = monto;
    }

    public void iniciarMano(Baraja baraja) {
        for (int i = 0; i < 2; i++) pedirCarta(baraja);
    }

    public void pedirCarta(Baraja baraja){
        mano.añadirCarta(baraja.sacarCarta());
    }

    public int puntajeMano(){
        int puntaje = mano.obtenerPuntaje();
        if(mano.asNoVale11()){
            puntaje -= 10;
        }
        return puntaje;
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

    public void mostrarManoOculta(){
        System.out.println(nombre + ": [" + mano.getCartas().get(0) + ", ?" + "]");
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
