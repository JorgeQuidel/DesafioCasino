import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BlackjackTest {
    private String[] manoJugador;
    private String[] manoDealer;

    @Test
    @DisplayName("Prueba unitaria para verificarGanador cuando el jugador gana")
    public void verificarGanadorTestJugadorGana(){
        manoJugador = new String[] {"Diamante 1", "Corazon 3", "Corazon A"};
        manoDealer = new String[] {"Trebol K", "Espada 10", "Corazon 8"};
        String[] manoGanadora = Blackjack.verificarGanador(manoJugador, manoDealer);
        assertEquals(manoJugador, manoGanadora);
    }

    @Test
    @DisplayName("Prueba unitaria para verificarGanador cuando el dealer gana")
    public void verificarGanadorTestDealerGana(){
        manoJugador = new String[] {"Diamante 10", "Corazon 3", "Corazon Q"};
        manoDealer = new String[] {"Trebol 2", "Espada 10", "Corazon 4", "Trebol 4"};
        String[] manoGanadora = Blackjack.verificarGanador(manoJugador, manoDealer);
        assertEquals(manoDealer, manoGanadora);
    }

    @Test
    @DisplayName("Prueba unitaria para verificarGanador cuando hay un empate")
    public void verificarGanadorTestEmpate(){
        manoJugador = new String[] {"Diamante 10", "Corazon Q"};
        manoDealer = new String[] {"Trebol 2", "Espada 10", "Corazon 4", "Trebol 4"};
        String[] manoGanadora = Blackjack.verificarGanador(manoJugador, manoDealer);
        assertEquals(manoDealer, manoGanadora);
    }
}
