package blackjackPOO;

public class Juego {
    private Baraja baraja;
    private Jugador jugador;
    private Jugador dealer;

    public Juego() {
        this.baraja = new Baraja();
        this.jugador = new Jugador(false);
        this.dealer = new Jugador(true);
    }

    public void jugar() {
        baraja.llenarBaraja();
        baraja.barajar();

        inicializarDealer();
        inicializarJugador();
        //añadirJugadores(baraja);

        System.out.println("\nINICIA EL JUEGO\n");

        if(jugador.getMano().esBlackjack()) {
            System.out.println("\nGANASTE!");
            return;
        }
        turnoJugador();
    }

    private void inicializarJugador() {
        ingresarDatosJugador();
        jugador.iniciarMano(baraja);
    }

    private void ingresarDatosJugador() {
        ingresarNombre();
        ingresarMonto();
        ingresarApuesta();
    }
    private void ingresarNombre(){
        System.out.print("Ingrese su nombre: ");
        jugador.setNombre(Utilidad.pedirString());
    }

    private void ingresarMonto(){
        System.out.print("Ingrese su monto: ");
        jugador.setMonto(Utilidad.pedirOpcionEnteraPositiva());
    }

    private void ingresarApuesta(){
        System.out.print("Ingrese su apuesta: ");
        jugador.setApuesta(Utilidad.pedirOpcionMenorA(jugador.getMonto()));
    }

    private void inicializarDealer() {
        dealer.setNombre("Dealer");
        dealer.setMonto(5000000);
        dealer.setApuesta(250000);
        dealer.iniciarMano(baraja);
    }

    private void turnoJugador(){
        bucle:
        while (true){

            jugador.mostrarMano();
            dealer.mostrarMano();

            if (jugador.getMano().esMayorQue21()) {
                System.out.println("\nPERDISTE!");
                break;
            }

            mostrarMenu();
            switch (Utilidad.pedirOpcionEntera()) {
                case 1 -> jugador.pedirCarta(baraja);
                case 2 ->
                {
                    bajarse();
                    break bucle;
                }
                case 3 -> System.out.println("No implementado todavia");
                case 4 -> {
                    System.out.println("Hasta pronto");
                    break bucle;
                }
                default -> System.out.println("Por favor, ingrese una de las opciones");
            }
        }
    }

    /*private void añadirJugadores(BlackjackPOO.Baraja baraja){
        do {
            var jugador = new BlackjackPOO.Jugador(false);

            System.out.print("Ingrese su nombre: ");
            jugador.setNombre(BlackjackPOO.Utilidad.pedirString());

            System.out.print("Ingrese su monto: ");
            jugador.setMonto(BlackjackPOO.Utilidad.pedirOpcionEntera());

            System.out.print("Ingrese su apuesta: ");
            jugador.setApuesta(BlackjackPOO.Utilidad.pedirOpcionEntera());

            jugador.iniciarMano(baraja);
            jugadores.add(jugador);
            System.out.println("Quiere agregar otro jugador? y/n");
        } while (BlackjackPOO.Utilidad.pedirStringEspecifico("y", "n").equalsIgnoreCase("y"));
    }*/

    private void bajarse(){
        dealer.getMano().getCartas().get(1).voltearCarta();
        dealer.mostrarMano();
        turnoDealer();
        var ganador = verificarGanador();
        mostrarResultados(ganador);
    }

    /*public HashMap<String, Integer> obtenerPuntajes() {
        return jugadores.stream()
                .collect(Collectors.toMap(BlackjackPOO.Jugador::getNombre, BlackjackPOO.Jugador::puntajeMano, (a, b) -> b, HashMap::new));
    }*/

    public void turnoDealer(){
        while(dealer.getMano().obtenerPuntaje() < 17) dealer.pedirCarta(baraja);
    }

    private Jugador verificarGanador() {
        int puntosJugador = jugador.obtenerPuntajeMano();
        int puntosDealer = dealer.obtenerPuntajeMano();

        if(puntosDealer>21){
            return jugador;
        }else if(puntosJugador>21) {
            return dealer;
        }else if(puntosDealer>puntosJugador) {
            return dealer;
        }else if (puntosDealer<puntosJugador) {
            return jugador;
        }else {
            return dealer;
        }
    }

    private void mostrarResultados(Jugador ganador) {
        if(ganador == dealer){
            System.out.println("\nPERDISTE!");
        }else {
            System.out.println("\nGANASTE!");
        }
        mostrarPuntajes();
        //System.out.println(obtenerPuntajes());
    }

    private void mostrarPuntajes(){
        System.out.println("Puntaje Dealer = " + dealer.obtenerPuntajeMano());
        System.out.println("Puntaje " + jugador.getNombre() + " = "+ jugador.obtenerPuntajeMano());
    }

    private void mostrarMenu(){
        System.out.print("""
                [1].Pedir Carta
                [2].Bajarte
                [3].Partir Mano
                [4].Salir del juego
                """.concat("> "));
    }

}
