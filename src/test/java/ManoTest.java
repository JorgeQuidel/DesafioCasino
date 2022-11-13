import desafioCasino.modelos.enums.Indice;
import desafioCasino.modelos.enums.Pinta;
import desafioCasino.modelos.Carta;
import desafioCasino.modelos.Mano;
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
        mano.addCarta(new Carta(Indice.OCHO, Pinta.DIAMANTE));
        mano.addCarta(new Carta(Indice.AS, Pinta.TREBOL));
        mano.addCarta(new Carta(Indice.DIEZ, Pinta.CORAZON));
        var listaCartas = mano.getCartas();

        assertEquals(listaCartas.size(), 3);
    }

    @Test
    void puntajeTotalTest(){
        mano.addCarta(new Carta(Indice.OCHO, Pinta.DIAMANTE));
        mano.addCarta(new Carta(Indice.AS, Pinta.TREBOL));
        assertEquals(mano.obtenerPuntaje(), 19);
    }
}
