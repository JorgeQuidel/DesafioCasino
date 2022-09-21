import java.util.Scanner;
public class Blackjack {
    public static void main(String[] args) {
        jugar();
    }
    public static void jugar() {
        String[][] baraja = crearBaraja();
        String[] manoJugador = crearMano();
        String[] manoDealer = crearMano();

        barajar(baraja);
        repartir(baraja, manoJugador);
        repartir(baraja, manoDealer);

        if(esBlackjack(manoJugador)) {
            System.out.println("\nGANASTE!");
            return;
        }
        turnoJugador(baraja, manoJugador, manoDealer);
    }

    private static void turnoJugador(String[][] baraja, String[] manoJugador, String[] manoDealer) {
        bucle:
        while (true) {
            mostrarManoOcultaDealer(manoDealer);
            mostrarManoJugador(manoJugador);

            if (esMayorQue21(manoJugador)) {
                System.out.println("\nPERDISTE!");
                break;
            }

            int opcion = elegirOpcion();

            switch (opcion) {
                case 1 -> pedirCarta(baraja, manoJugador);
                case 2 -> {
                    bajarse(baraja, manoJugador, manoDealer);
                    break bucle;
                }
                case 3 -> {
                    if(!cartasMismoValor(manoJugador)){
                        System.out.println("No se puede partir la mano, ambas cartas deben tener el mismo valor");
                        break ;
                    }
                    jugarPartidaDobleMano(baraja, manoJugador, manoDealer);
                    break bucle;
                }
                case 4 -> {
                    System.out.println("Hasta pronto");
                    break bucle;
                }
                default -> System.out.println("Por favor ingrese una de las opciones");
            }
        }
    }

    private static void jugarPartidaDobleMano(String[][] baraja, String[] manoJugador, String[] manoDealer) {
        String[][] dobleManoJugador = partirMano(manoJugador);
        String[] manoJugador1 = dobleManoJugador[0];
        String[] manoJugador2 = dobleManoJugador[1];
        bucle:
        while (true) {
            mostrarManoOcultaDealer(manoDealer);
            mostrarManoJugador(manoJugador1);
            mostrarManoJugador(manoJugador2);

            if (esMayorQue21(manoJugador1) && esMayorQue21(manoJugador2)) {
                System.out.println("\nPERDISTE!");
                break;
            }

            int opcion = elegirOpcionDobleMano();

            switch (opcion) {
                case 1 -> {
                    if(esMayorQue21(manoJugador1)){
                        System.out.println("La mano ya quedó fuera de juego");
                        break;
                    }
                    pedirCarta(baraja, manoJugador1);
                }
                case 2 -> {
                    if(esMayorQue21(manoJugador2)){
                        System.out.println("La mano ya quedó fuera de juego");
                        break;
                    }
                    pedirCarta(baraja, manoJugador2);
                }
                case 3 -> {
                    bajarseDobleMano(baraja, manoJugador1, manoJugador2,manoDealer);
                    break bucle;
                }
                case 4 -> {
                    System.out.println("Hasta pronto");
                    break bucle;
                }
                default -> System.out.println("Por favor ingrese una de las opciones");
            }
        }
    }

    private static void bajarseDobleMano(String[][] baraja, String[] manoJugador1, String[] manoJugador2,String[] manoDealer) {
        turnoDealer(baraja, manoDealer);
        String[] manoGanadora = verificarGanadorDobleMano(manoJugador1, manoJugador2, manoDealer);
        mostrarResultadosDobleMano(manoJugador1, manoJugador2, manoDealer, manoGanadora);
    }

    private static void mostrarResultadosDobleMano(String[] manoJugador1, String[] manoJugador2,String[] manoDealer, String[] manoGanadora) {
        int puntosJugadorMano1 = sumarPuntosMano(manoJugador1);
        int puntosJugadorMano2 = sumarPuntosMano(manoJugador2);
        int puntosDealer = sumarPuntosMano(manoDealer);

        if(manoGanadora == manoDealer){
            System.out.println("\nPERDISTE!");
        }else {
            System.out.println("\nGANASTE!");
        }

        mostrarManoDealer(manoDealer);
        System.out.println("Puntos Dealer = " + puntosDealer);
        mostrarManoJugador(manoJugador1);
        System.out.println("Puntos Jugador Mano 1 = " + puntosJugadorMano1);
        mostrarManoJugador(manoJugador2);
        System.out.println("Puntos Jugador Mano 2 = " + puntosJugadorMano2);

    }

    private static String[] verificarGanadorDobleMano(String[] manoJugador1, String[] manoJugador2, String[] manoDealer) {
        int puntosJugadorMano1 = sumarPuntosMano(manoJugador1);
        int puntosJugadorMano2 = sumarPuntosMano(manoJugador2);
        int puntosDealer = sumarPuntosMano(manoDealer);

        if (puntosDealer <= 21) {
            if (puntosDealer > puntosJugadorMano1 || puntosDealer > puntosJugadorMano2) {
                return manoDealer;
            }else if(puntosJugadorMano1>puntosJugadorMano2){
                return manoJugador1;
            }else{
                return manoJugador2;
            }
        }

        if (puntosJugadorMano1>21) {
            return manoJugador2;
        }
        if (puntosJugadorMano2>21) {
            return manoJugador1;
        }
        if(puntosJugadorMano1>puntosJugadorMano2){
            return manoJugador1;
        } else if (puntosJugadorMano2>puntosJugadorMano1) {
            return manoJugador2;

        }
        return manoDealer;
    }

    public static String[][] crearBaraja() {
        String[] indices = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
        String[] pintas = {"Corazon", "Diamante", "Trebol", "Espada"};
        String[][] baraja = new String[pintas.length][indices.length];

        for (int i = 0; i < pintas.length; i++) {
            for (int j = 0; j < indices.length; j++) {
                baraja[i][j] = pintas[i] + " " + indices[j];
            }
        }
        return baraja;
    }
    public static String[] crearMano(){
        return new String[12];
    }
    public static void barajar(String[][] baraja) {
        for (int i = 0; i < baraja.length; i++) {
            for (int j = 0; j < baraja[i].length; j++) {
                int iRandom = (int)(Math.random()* baraja.length);
                int jRandom = (int)(Math.random()* baraja[i].length);

                String aux = baraja[i][j];
                baraja[i][j] = baraja[iRandom][jRandom];
                baraja[iRandom][jRandom] = aux;
            }
        }
    }
    public static void repartir(String[][] baraja, String[] mano) {
        for (int i = 0; i < 2; i++) {
            pedirCarta(baraja,mano);
        }
    }
    public static void pedirCarta(String[][] baraja, String[] mano) {
        int espacioDisponible = contarCartas(mano);

        for (int i = 0; i < baraja.length; i++) {
            for (int j = 0; j < baraja[i].length; j++) {
                if(!baraja[i][j].equals("")){
                    mano[espacioDisponible] = baraja[i][j];
                    baraja[i][j] = "";
                    return;
                }
            }
        }
    }
    public static void bajarse(String[][] baraja,String[] manoJugador, String[] manoDealer) {
        turnoDealer(baraja, manoDealer);
        String[] manoGanadora = verificarGanador(manoJugador, manoDealer);
        mostrarResultados(manoJugador, manoDealer, manoGanadora);
    }
    public static String[][] partirMano(String[] mano){
        String[][] manoDoble = new String[][]{crearMano(), crearMano()};
        manoDoble[0][0] = mano[0];
        manoDoble[1][0] = mano[1];
        return manoDoble;
    }
    public static void turnoDealer(String[][] baraja, String[] manoDealer) {
        mostrarManoDealer(manoDealer);
        if(esBlackjack(manoDealer)){
            return;
        }
        agregarCartasDealer(baraja, manoDealer);
    }
    public static void agregarCartasDealer(String[][] baraja, String[] manoDealer) {
        while(sumarPuntosMano(manoDealer)<17){
            pedirCarta(baraja, manoDealer);
        }
    }
    public static int sumarPuntosMano(String[] mano) {
        int puntajeTotal = 0;
        int cantidadCartas = contarCartas(mano);

        for (int i = 0; i < cantidadCartas; i++) {
            puntajeTotal += asignarValorCarta(mano[i]);
        }

        if(puntajeTotal>21 && contieneAs(mano)){
            puntajeTotal = puntajeTotal - 10;
        }

        return puntajeTotal;
    }
    public static int asignarValorCarta(String carta) {

        String indice = carta.split(" ")[1];

        return switch (indice) {
            case "A" -> 11;
            case "2" -> 2;
            case "3" -> 3;
            case "4" -> 4;
            case "5" -> 5;
            case "6" -> 6;
            case "7" -> 7;
            case "8" -> 8;
            case "9" -> 9;
            case "10", "J", "Q", "K" -> 10;
            default -> 0;
        };
    }
    public static boolean cartasMismoValor(String[] mano){
        int valorCarta1 = asignarValorCarta(mano[0]);
        int valorCarta2 = asignarValorCarta(mano[1]);
        return valorCarta1 == valorCarta2;
    }
    public static boolean esBlackjack(String[] mano) {
        int cantidadCartas = contarCartas(mano);

        if (cantidadCartas > 2) {
            return false;
        }

        if (contieneAs(mano) && contiene10(mano)) {
            System.out.println("\n!!BLACKJACK!!");
            return true;
        }
        return false;
    }
    public static boolean esMayorQue21(String[] mano){
        int puntos = sumarPuntosMano(mano);
        return puntos > 21;
    }
    public static boolean contieneAs(String[] mano){
        int cantidadCartas = contarCartas(mano);

        for (int carta = 0; carta < cantidadCartas; carta++) {
            if (mano[carta].split(" ")[1].equals("A")) {
                return true;
            }
        }
        return false;
    }
    public static boolean contiene10(String[] mano){
        int cantidadCartas = contarCartas(mano);

        for (int carta = 0; carta < cantidadCartas; carta++) {
            if (asignarValorCarta(mano[carta]) == 10) {
                return true;
            }
        }
        return false;
    }
    public static String[] verificarGanador(String[] manoJugador, String[] manoDealer) {
        int puntosJugador = sumarPuntosMano(manoJugador);
        int puntosDealer = sumarPuntosMano(manoDealer);

        if(puntosDealer>21){
            return manoJugador;
        }else if(puntosJugador>21) {
            return manoDealer;
        }else if(puntosDealer>puntosJugador) {
            return manoDealer;
        }else if (puntosDealer<puntosJugador) {
            return manoJugador;
        }else {
            return manoDealer;
        }
    }
    public static int contarCartas(String[] mano){
        int cantidadCartas = 0;
        for (String carta : mano) {
            if (carta == null) {
                return cantidadCartas;
            }
            cantidadCartas++;
        }
        return cantidadCartas;
    }
    public static void mostrarMenuDobleMano() {
        System.out.println("[1].Pedir Carta Mano 1");
        System.out.println("[2].Pedir Carta Mano 2");
        System.out.println("[3].Bajarte");
        System.out.println("[4].Salir del juego");
    }
    public static void mostrarMenu() {
        System.out.println("[1].Pedir Carta");
        System.out.println("[2].Bajarte");
        System.out.println("[3].Partir Mano");
        System.out.println("[4].Salir del juego");
    }

    public static int elegirOpcionDobleMano() {
        Scanner input = new Scanner(System.in);
        int opcion = 0;
        try{
            mostrarMenuDobleMano();
            opcion = input.nextInt();
        }catch (Exception e){
            System.out.println("Por favor ingrese un numero que corresponda a una de las opciones");
            input.next();
        }
        return opcion;
    }
    public static int elegirOpcion() {
        Scanner input = new Scanner(System.in);
        int opcion = 0;
        try{
            mostrarMenu();
            opcion = input.nextInt();
        }catch (Exception e){
            System.out.println("Por favor ingrese un numero que corresponda a una de las opciones");
            input.next();
        }
        return opcion;
    }
    public static void mostrarManoJugador(String[] mano){
        int cantidadCartas = contarCartas(mano);
        System.out.print("Mano Jugador = ");
        for (int i = 0; i < cantidadCartas; i++) {
            System.out.print("["+mano[i]+"]");
        }
        System.out.println();
    }
    public static void mostrarManoDealer(String[] mano){
        int cantidadCartas = contarCartas(mano);
        System.out.print("Mano Dealer = ");
        for (int i = 0; i < cantidadCartas; i++) {
            System.out.print("["+mano[i]+"]");
        }
        System.out.println();
    }
    public static void mostrarManoOcultaDealer(String[] manoDealer){
        System.out.println("Mano Dealer = ["+ manoDealer[0] + ", ?]");
    }
    public static void mostrarResultados(String[] manoJugador, String[] manoDealer, String[] manoGanadora) {
        int puntosJugador = sumarPuntosMano(manoJugador);
        int puntosDealer = sumarPuntosMano(manoDealer);

        if(manoGanadora == manoDealer){
            System.out.println("\nPERDISTE!");
        }else {
            System.out.println("\nGANASTE!");
        }

        mostrarManoDealer(manoDealer);
        System.out.println("Puntos Dealer = " + puntosDealer);
        mostrarManoJugador(manoJugador);
        System.out.println("Puntos Jugador = " + puntosJugador);
    }

}