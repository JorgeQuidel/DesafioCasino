package desafioCasino.modelos;

import java.util.ArrayList;

public class Jugador {
    private String nombre;
    private boolean esDealer;
    private Mano mano;
    //private ArrayList<Mano> manos;
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

    public void sacarCarta(Baraja baraja){
        try{
            mano.addCarta(baraja.sacarCarta());
        }catch (IndexOutOfBoundsException e){
            System.out.println("No hay cartas para sacar");
        }
    }

    public int obtenerPuntajeMano(){
        return mano.obtenerPuntaje();
    }
    public ArrayList<Carta> obtenerCartasMano(){
        return mano.getCartas();
    }

    public void voltearCarta(int carta){
        try{
            mano.getCartas().get(carta).voltearCarta();
        }catch (IndexOutOfBoundsException e){
            System.out.println("La mano se encuentra vacía");
        }
    }

    public void limpiarMano(){
        mano.getCartas().clear();
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
