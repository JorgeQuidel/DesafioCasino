import blackjackPOO.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class BarajaTest {

    private Baraja baraja;

    @BeforeEach
    void init(){
        baraja = new Baraja();
    }

    @Test
    void llenarBarajaTest() {
        assertNull(baraja.getCartas());
        baraja.llenarBaraja();
        assertNotNull(baraja.getCartas());
    }

    @Test
    void sacarCartaTest(){
        baraja.llenarBaraja();
        baraja.sacarCarta();
        assertEquals(baraja.getCartas().size(), 51);
    }

    @Test
    void sacarCartaTestBarajaNull(){
        assertThrows(NullPointerException.class, () ->
                baraja.sacarCarta());
    }
}
