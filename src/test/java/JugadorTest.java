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
        assertEquals(jugador.getManoActual().getCartas().size(), 1);
    }

    @Test
    void iniciarManoTest() {
        baraja.llenarBaraja();
        jugador.iniciarMano(baraja);
        assertEquals(jugador.getManoActual().getCartas().size(), 2);
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

    @Test
    void puntajeManoTestVariosAs(){
        jugador.getManoActual().añadirCarta(new Carta("A", "Trebol", 11));
        jugador.getManoActual().añadirCarta(new Carta("A", "Diamante", 11));
        jugador.getManoActual().añadirCarta(new Carta("A", "Corazon", 11));
        jugador.getManoActual().añadirCarta(new Carta("A", "Espada", 11));
        assertEquals(jugador.puntajeMano(), 14);
    }
}