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

        while(true){
            mostrarManoOcultaDealer(manoDealer);
            mostrarManoJugador(manoJugador);

            if(esMayorQue21(manoJugador)){
                System.out.println("\nPERDISTE!");
                break;
            }

            int opcion = elegirOpcion();

            if (opcion == 1) {
                pedirCarta(baraja, manoJugador);
            } else if (opcion == 2) {
                bajarse(baraja ,manoJugador, manoDealer);
                break;
            } else if (opcion == 3) {
                leerReglas();
            } else if (opcion == 4) {
                System.out.println("Hasta pronto");
                break;
            }
        }
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
        if(baraja == null){
            return;
        }

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
    public static void mostrarMenu() {
        System.out.println("[1].Pedir Carta");
        System.out.println("[2].Bajarte");
        System.out.println("[3].Leer las reglas");
        System.out.println("[4].Salir del juego");
    }
    public static int elegirOpcion() {
        Scanner input = new Scanner(System.in);
        int opcion = 0;
        do{
            try{
                mostrarMenu();
                opcion = input.nextInt();
            }catch (Exception e){
                System.out.println("Por favor ingrese un numero que corresponda a una de las opciones");
                input.next();
            }
        }while(opcion<0 || opcion>4);

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
    public static void leerReglas() {
        System.out.println();
        System.out.println("-----OBJETIVO-----");
        System.out.println("-El objetivo es que tu puntaje llegue lo mas cerca de 21 si llegar a superar dicho numero");
        System.out.println("-Jugaras contra el Dealer por ver quien se acerca mas a 21");
        System.out.println("-----CARTAS-----");
        System.out.println("-El valor de cada carta del 2-10 es el de su indice (Ej: Diamante 5 vale 5 puntos)");
        System.out.println("-Las cartas Jota, Quina y Kaiser (J,Q,K) valen 10 puntos");
        System.out.println("-El As (A) valdra 1 o 11 puntos, segun le convenga a la mano");
        System.out.println("-----ACCIONES-----");
        System.out.println("-Empezaras la partida con dos cartas en tu mano");
        System.out.println("-El Dealer mostrara una sola de sus cartas hasta que el jugador se baje");
        System.out.println("-Puedes pedir mas cartas o bajarte con las que tengas en tu mano");
        System.out.println("-Una vez el jugador se baje, el Dealer revelara sus dos cartas y sacara mas hasta llegar a un puntaje mayor o igual a 17");
        System.out.println("-----GANAR/PERDER-----");
        System.out.println("-Si tus primeras dos cartas son un As junto a una carta con valor 10, ganas automaticamente (Blackjack)");
        System.out.println("-Si tu puntaje supera al del Dealer y no es superior a 21, ganas");
        System.out.println("-Si te llegas a pasar de 21 pierdes automaticamente, lo mismo para el Dealer");
        System.out.println("-Si empatas con el Dealer, pierdes");
        System.out.println();
    }

}