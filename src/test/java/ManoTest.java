import blackjackPOO.Carta;
import blackjackPOO.Mano;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class ManoTest {

    private Mano mano;

    @BeforeEach
    void init(){
        mano = new Mano();
    }

    @Test
    void agregarCartaTest(){
        mano.añadirCarta(new Carta("8", "Diamante", 8));
        mano.añadirCarta(new Carta("A", "Trebol", 11));
        mano.añadirCarta(new Carta("10", "Corazon", 10));
        var listaCartas = mano.getCartas();

        assertEquals(listaCartas.size(), 3);
    }

    @Test
    void puntajeTotalTest(){
        mano.añadirCarta(new Carta("8", "Diamante", 8));
        mano.añadirCarta(new Carta("A", "Trebol", 11));
        assertEquals(mano.obtenerPuntaje(), 19);
    }

    @Test
    void contieneAsTestCasoTrue(){
        mano.añadirCarta(new Carta("A", "Trebol", 11));
        assertTrue(mano.contieneAs());
    }

    @Test
    void contieneAsTestCasoFalse(){
        mano.añadirCarta(new Carta("8", "Diamante", 8));
        assertFalse(mano.contieneAs());
    }

    @Test
    void esBlackjackTestCasoTrue(){
        mano.añadirCarta(new Carta("A", "Trebol", 11));
        mano.añadirCarta(new Carta("10", "Trebol", 10));
        assertTrue(mano.esBlackjack());
    }

    @Test
    void esBlackjackTestCasoFalse(){
        mano.añadirCarta(new Carta("A", "Trebol", 11));
        mano.añadirCarta(new Carta("8", "Diamante", 8));
        assertFalse(mano.esBlackjack());
    }

    @Test
    void esMayorQue21TestCasoTrue(){
        mano.añadirCarta(new Carta("J", "Diamante", 10));
        mano.añadirCarta(new Carta("J", "Trebol", 10));
        mano.añadirCarta(new Carta("J", "Corazon", 10));
        assertTrue(mano.esMayorQue21());
    }

    @Test
    void esMayorQue21TestCasoFalse(){
        mano.añadirCarta(new Carta("J", "Diamante", 10));
        mano.añadirCarta(new Carta("J", "Trebol", 10));
        assertFalse(mano.esMayorQue21());
    }
}
