import java.util.List;

public class Jugador {
    private String nombre;
    private boolean esDealer;
    private Mano manoActual;
    //private List<Mano> manos;
    private int apuesta;
    private int monto;

    public Jugador(boolean esDealer) {
        this.esDealer = esDealer;
        this.manoActual = new Mano();
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setMonto(int monto) {
        this.monto = monto;
    }

    public void iniciarMano(Baraja baraja) {
        for (int i = 0; i < 2; i++) pedirCarta(baraja);
        //manos.add(manoActual);
    }

    public void pedirCarta(Baraja baraja){
        manoActual.añadirCarta(baraja.sacarCarta());
    }

    /*public void partirMano(){
        Mano manoNueva = new Mano();
        Mano manoAntigua = new Mano();

        Carta cartaManoAntigua = manoActual.getCartas().get(0);
        Carta cartaManoNueva = manoActual.getCartas().get(1);

        manoAntigua.añadirCarta(cartaManoAntigua);
        manoNueva.añadirCarta(cartaManoNueva);

        manoActual = manoAntigua;
        manos.add(manoNueva);
    }*/

    public int puntajeMano(){
        int puntaje = manoActual.obtenerPuntaje();
        List<Carta> cartas = manoActual.getCartas();
        for (Carta carta : cartas) {
            if(carta.esAs() && puntaje > 21){
                puntaje -=10;
            }
        }
        return puntaje;
    }

    public String getNombre() {
        return nombre;
    }

    public Mano getManoActual() {
        return manoActual;
    }

    public int getApuesta() {
        return apuesta;
    }

    public void setApuesta(int apuesta) {
        this.apuesta = apuesta;
    }

    public void mostrarMano(){
        System.out.println(nombre + ": " + manoActual);
    }

    public void mostrarManoOculta(){
        System.out.println(nombre + ": [" + manoActual.getCartas().get(0) + ", ?" + "]");
    }
    @Override
    public String toString() {
        return "Jugador{" +
                "nombre='" + nombre + '\'' +
                ", esDealer=" + esDealer +
                ", mano=" + manoActual +
                ", apuesta=" + apuesta +
                ", monto=" + monto +
                '}';
    }
}
