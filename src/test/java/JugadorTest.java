import desafioCasino.enums.Indice;
import desafioCasino.enums.Pinta;
import desafioCasino.modelos.Baraja;
import desafioCasino.modelos.Carta;
import desafioCasino.modelos.Jugador;
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
        jugador.getMano().addCarta(new Carta(Indice.AS, Pinta.TREBOL));
        jugador.getMano().addCarta(new Carta(Indice.KAISER, Pinta.CORAZON));
        jugador.getMano().addCarta(new Carta(Indice.CUATRO, Pinta.DIAMANTE));
        assertEquals(jugador.obtenerPuntajeMano(), 15);
    }

    @Test
    void puntajeManoTestVariosAs(){
        jugador.getMano().addCarta(new Carta(Indice.AS, Pinta.TREBOL));
        jugador.getMano().addCarta(new Carta(Indice.AS, Pinta.CORAZON));
        jugador.getMano().addCarta(new Carta(Indice.AS, Pinta.DIAMANTE));
        jugador.getMano().addCarta(new Carta(Indice.AS, Pinta.ESPADA));
        assertEquals(jugador.obtenerPuntajeMano(), 14);
    }
}