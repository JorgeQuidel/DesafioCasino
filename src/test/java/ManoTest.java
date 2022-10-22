import blackjackPOO.Carta;
import blackjackPOO.Indice;
import blackjackPOO.Mano;
import blackjackPOO.Pinta;
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
        mano.añadirCarta(new Carta(Indice.OCHO, Pinta.DIAMANTE));
        mano.añadirCarta(new Carta(Indice.AS, Pinta.TREBOL));
        mano.añadirCarta(new Carta(Indice.DIEZ, Pinta.CORAZON));
        var listaCartas = mano.getCartas();

        assertEquals(listaCartas.size(), 3);
    }

    @Test
    void puntajeTotalTest(){
        mano.añadirCarta(new Carta(Indice.OCHO, Pinta.DIAMANTE));
        mano.añadirCarta(new Carta(Indice.AS, Pinta.TREBOL));
        assertEquals(mano.obtenerPuntaje(), 19);
    }

    @Test
    void contieneAsTestCasoTrue(){
        mano.añadirCarta(new Carta(Indice.AS, Pinta.TREBOL));
        assertTrue(mano.contieneAs());
    }

    @Test
    void contieneAsTestCasoFalse(){
        mano.añadirCarta(new Carta(Indice.OCHO, Pinta.DIAMANTE));
        assertFalse(mano.contieneAs());
    }

    @Test
    void esBlackjackTestCasoTrue(){
        mano.añadirCarta(new Carta(Indice.AS, Pinta.TREBOL));
        mano.añadirCarta(new Carta(Indice.DIEZ, Pinta.TREBOL));
        assertTrue(mano.esBlackjack());
    }

    @Test
    void esBlackjackTestCasoFalse(){
        mano.añadirCarta(new Carta(Indice.AS, Pinta.TREBOL));
        mano.añadirCarta(new Carta(Indice.OCHO, Pinta.DIAMANTE));
        assertFalse(mano.esBlackjack());
    }

    @Test
    void esMayorQue21TestCasoTrue(){
        mano.añadirCarta(new Carta(Indice.JOTA, Pinta.DIAMANTE));
        mano.añadirCarta(new Carta(Indice.JOTA, Pinta.TREBOL));
        mano.añadirCarta(new Carta(Indice.JOTA, Pinta.CORAZON));
        assertTrue(mano.esMayorQue21());
    }

    @Test
    void esMayorQue21TestCasoFalse(){
        mano.añadirCarta(new Carta(Indice.JOTA, Pinta.DIAMANTE));
        mano.añadirCarta(new Carta(Indice.JOTA, Pinta.TREBOL));
        assertFalse(mano.esMayorQue21());
    }
}
