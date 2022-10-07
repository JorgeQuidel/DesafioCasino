import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

public class JugadorTest {

    private Jugador jugador;
    private Baraja baraja;

    @BeforeEach
    void init(){
        jugador = new Jugador(false);
        baraja = new Baraja();
    }

    @Test
    void pedirCartaTest() {
        baraja.llenarBaraja();
        jugador.pedirCarta(baraja);
        assertEquals(jugador.getMano().getCartas().size(), 1);
    }

    @Test
    void iniciarManoTest() {
        baraja.llenarBaraja();
        jugador.iniciarMano(baraja);
        assertEquals(jugador.getMano().getCartas().size(), 2);
    }

    @Test
    void pedirCartaTestBarajaNull(){
        assertThrows(NullPointerException.class, () ->
                jugador.pedirCarta(baraja));
    }

    @Test
    void setApuestaTest(){
        jugador.setApuesta(5000);
        assertEquals(jugador.getApuesta(), 5000);
    }

    @Test
    void puntajeManoTest(){
        baraja.llenarBaraja();
        IntStream.range(0, 5).forEach(index -> jugador.pedirCarta(baraja));
        assertEquals(jugador.puntajeMano(), 15);
    }
}