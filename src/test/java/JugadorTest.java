import blackjackPOO.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


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
        assertThrows(IndexOutOfBoundsException.class, () ->
                jugador.pedirCarta(baraja));
    }

    @Test
    void setApuestaTest(){
        jugador.setApuesta(5000);
        assertEquals(jugador.getApuesta(), 5000);
    }

    @Test
    void puntajeManoTest(){
        jugador.getManoActual().añadirCarta(new Carta(Indice.AS, Pinta.TREBOL));
        jugador.getManoActual().añadirCarta(new Carta(Indice.KAISER, Pinta.CORAZON));
        jugador.getManoActual().añadirCarta(new Carta(Indice.CUATRO, Pinta.DIAMANTE));
        assertEquals(jugador.puntajeMano(), 15);
    }

    @Test
    void puntajeManoTestVariosAs(){
        jugador.getManoActual().añadirCarta(new Carta(Indice.AS, Pinta.TREBOL));
        jugador.getManoActual().añadirCarta(new Carta(Indice.AS, Pinta.CORAZON));
        jugador.getManoActual().añadirCarta(new Carta(Indice.AS, Pinta.DIAMANTE));
        jugador.getManoActual().añadirCarta(new Carta(Indice.AS, Pinta.ESPADA));
        assertEquals(jugador.puntajeMano(), 14);
    }
}