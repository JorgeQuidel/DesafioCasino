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

}
