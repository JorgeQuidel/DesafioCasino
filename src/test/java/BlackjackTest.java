import org.apache.log4j.Logger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BlackjackTest {
    private String[] manoJugador;
    private String[] manoDealer;
    private String[][] baraja;
    private String[] manoJugador2;

    @Test
    @DisplayName("Prueba unitaria metodo verificarGanador")
    public void verificarGanadorTestJugadorGana(){
        manoJugador = new String[] {"Diamante 2", "Corazon 3", "Corazon A"};
        manoDealer = new String[] {"Trebol K", "Espada 10", "Corazon 8"};
        String[] manoGanadora = Blackjack.verificarGanador(manoJugador, manoDealer);
        assertEquals(manoJugador, manoGanadora);
    }

    @Test
    @DisplayName("Prueba unitaria metodo verificarGanador para caso mano vacia")
    public void verificarGanadorTestCasoManoNula(){
        var logger = Logger.getLogger("Blackjack.clas");
        var manoDealer = new String[] {"Trebol 2", "Espada 10", "Corazon 4", "Trebol 4"};
        assertThrows(NullPointerException.class, () ->
                Blackjack.verificarGanador(null, manoDealer), "Se ha ingresado una entrada nula");
        logger.info("Se ha lanzado la excepcion NullPointerException, dado que la mano del jugador estaba nula");
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
    public void pedirCartaTestManoNula(){
        baraja = Blackjack.crearBaraja();
        var logger = Logger.getLogger("Blackjack.clas");
        assertThrows(NullPointerException.class, () ->
                Blackjack.pedirCarta(baraja, null), "Se ha ingresado una entrada nula");
        logger.info("Se ha lanzado la excepcion NullPointerException, dado que la mano entregada estaba nula");
    }

    @Test
    @DisplayName("Prueba unitaria metodo bajarse para caso normal")
    public void bajarseCasoNormal(){
        manoDealer = new String[] {"Trebol 10", "Espada 6", null};
        manoJugador = new String[] {"Diamante 10", "Corazon Q"};
        baraja = Blackjack.crearBaraja();
        String[] manoEsperada = {"Trebol 10", "Espada 6", "Corazon A"};
        Blackjack.bajarse(baraja, manoJugador, manoDealer);
        assertArrayEquals(manoDealer, manoEsperada);
    }

    @Test
    @DisplayName("Prueba unitaria metodo cartasSonIguales")
    public void cartasSonIgualesTest(){
        manoJugador = new String[] {"Corazon 10", "Diamante K"};
        assertTrue(Blackjack.cartasMismoValor(manoJugador));
    }

    @Test
    @DisplayName("Prueba unitaria metodo partirMano")
    public void partirManoTest(){
        manoJugador = new String[] {"Corazon 10", "Diamante K"};
        String[][] manoEsperada = new String[2][12];
        manoEsperada[0][0] = manoJugador[0];
        manoEsperada[1][0] = manoJugador[1];
        assertArrayEquals(Blackjack.partirMano(manoJugador), manoEsperada);
    }

    @Test
    @DisplayName("Prueba unitaria metodo partirMano")
    public void partirManoTestCasoNulo(){
        assertThrows(NullPointerException.class, () ->
                Blackjack.partirMano(null), "Se ha ingresado una entrada nula");
    }

    @Test
    @DisplayName("Prueba unitaria metodo verificarGanadorDobleMano")
    public void verificarGanadorDobleManoTest(){
        manoJugador = new String[] {"Diamante 4", "Corazon 3", "Corazon A"};
        manoJugador2 = new String[] {"Diamante 2", "Corazon 3", "Corazon A"};
        manoDealer = new String[] {"Trebol 3", "Espada 10", "Corazon 3"};
        String[] manoGanadora = Blackjack.verificarGanadorDobleMano(manoJugador, manoJugador2, manoDealer);
        assertEquals(manoJugador, manoGanadora);
    }
}
