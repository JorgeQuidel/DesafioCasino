import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BlackjackTest {
    private String[] manoJugador;
    private String[] manoDealer;
    private String[][] baraja;

    @Test
    @DisplayName("Prueba unitaria metodo verificarGanador cuando el jugador gana")
    public void verificarGanadorTestJugadorGana(){
        manoJugador = new String[] {"Diamante 2", "Corazon 3", "Corazon A"};
        manoDealer = new String[] {"Trebol K", "Espada 10", "Corazon 8"};
        String[] manoGanadora = Blackjack.verificarGanador(manoJugador, manoDealer);
        assertEquals(manoJugador, manoGanadora);
    }

    @Test
    @DisplayName("Prueba unitaria metodo verificarGanador cuando el dealer gana")
    public void verificarGanadorTestDealerGana(){
        manoJugador = new String[] {"Diamante 10", "Corazon 3", "Corazon Q"};
        manoDealer = new String[] {"Trebol 2", "Espada 10", "Corazon 4", "Trebol 4"};
        String[] manoGanadora = Blackjack.verificarGanador(manoJugador, manoDealer);
        assertEquals(manoDealer, manoGanadora);
    }

    @Test
    @DisplayName("Prueba unitaria metodo verificarGanador cuando hay un empate")
    public void verificarGanadorTestEmpate(){
        manoJugador = new String[] {"Diamante 10", "Corazon Q"};
        manoDealer = new String[] {"Trebol 2", "Espada 10", "Corazon 4", "Trebol 4"};
        String[] manoGanadora = Blackjack.verificarGanador(manoJugador, manoDealer);
        assertEquals(manoDealer, manoGanadora);
    }
    @Test
    @DisplayName("Prueba unitaria metodo pedirCarta para caso normal")
    public void pedirCartaTestCasoNormal(){
        manoJugador = new String[] {"Diamante 2", "Corazon Q", null};
        baraja = Blackjack.crearBaraja();
        String[] manoEsperada = {"Diamante 2", "Corazon Q", "Corazon A"};
        Blackjack.pedirCarta(baraja, manoJugador);
        assertArrayEquals(manoJugador, manoEsperada);
    }
    @Test
    @DisplayName("Prueba unitaria metodo pedirCarta para caso baraja vacia")
    public void pedirCartaTestBarajaVacia(){
        manoJugador = new String[] {"Diamante 2", "Corazon Q"};
        String[] manoEsperada = {"Diamante 2", "Corazon Q"};
        Blackjack.pedirCarta(baraja, manoJugador);
        assertArrayEquals(manoJugador, manoEsperada);
    }

}
