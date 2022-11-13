package desafioCasino;

import java.util.List;

public class Jugador {
    private String nombre;
    private boolean esDealer;
    private Mano mano;
    //private List<Mano> manos;
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
        if(esDealer){
            mano.getCartas().get(1).voltearCarta();
        }
    }

    public void pedirCarta(Baraja baraja){
        mano.addCarta(baraja.sacarCarta());
    }

    /*public void partirMano(){
        BlackjackPOO.Mano manoNueva = new BlackjackPOO.Mano();
        BlackjackPOO.Mano manoAntigua = new BlackjackPOO.Mano();

        BlackjackPOO.Carta cartaManoAntigua = manoActual.getCartas().get(0);
        BlackjackPOO.Carta cartaManoNueva = manoActual.getCartas().get(1);

        manoAntigua.añadirCarta(cartaManoAntigua);
        manoNueva.añadirCarta(cartaManoNueva);

        manoActual = manoAntigua;
        manos.add(manoNueva);
    }*/

    public int obtenerPuntajeMano(){
        int puntaje = mano.obtenerPuntaje();
        List<Carta> cartas = mano.getCartas();
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

    public Mano getMano() {
        return mano;
    }

    public int getApuesta() {
        return apuesta;
    }

    public int getMonto() {
        return monto;
    }

    public void setApuesta(int apuesta) {
        this.apuesta = apuesta;
    }

    public void mostrarMano(){
        System.out.println(nombre + ": " + mano);
    }

}
