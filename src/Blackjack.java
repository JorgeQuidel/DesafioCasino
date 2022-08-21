import java.util.ArrayList;
import java.util.Scanner;

public class Blackjack {
    public static void main(String[] args) {
        jugar();
    }

    public static void jugar() {
        //Crea la baraja y las manos del Jugador y el Dealer
        String[][] baraja = crearBaraja();
        ArrayList<String> manoJugador = new ArrayList<>();
        ArrayList<String> manoDealer = new ArrayList<>();

        //Se barajan y reparten las cartas
        barajar(baraja);
        repartir(baraja, manoJugador);
        repartir(baraja, manoDealer);

        //Loop del juego hasta que alguien gana/pierde
        while(true){
            System.out.println("Mano Jugador = " + manoJugador);
            System.out.println("Mano Dealer = " + "["+ manoDealer.get(0) + ", ?]");


            //Verifica si sale Blackjack o se pasa de 21 automáticamente
            if(verificarGanadorAutomatico(manoJugador,manoDealer)){
                mostrarEstadoJuego(manoJugador, manoDealer);
                break;
            }

            int opcion = elegirOpcion();

            if(opcion == 1){
                pedirCarta(baraja, manoJugador);
            } else if (opcion == 2) {
                System.out.println();
                System.out.println("Mano Jugador = " + manoJugador);
                System.out.println("Mano Dealer = " + manoDealer);

                //Suma cartas al Dealer hasta que sea igual o mayor a 17
                do{
                    pedirCarta(baraja, manoDealer);
                }while(sumarPuntos(manoDealer)<17);

                //Verifica si el Dealer se paso de 21 al sumar cartas
                if(verificarGanadorAutomatico(manoJugador,manoDealer)){
                    mostrarEstadoJuego(manoJugador, manoDealer);
                    break;
                }

                verificarGanadorAlBajarse(manoJugador,manoDealer);
                mostrarEstadoJuego(manoJugador, manoDealer);
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

    public static void pedirCarta(String[][] baraja, ArrayList<String> mano) {
        int pintaRandom;
        int indiceRandom;
        do {
            pintaRandom = (int)(Math.random()*4);
            indiceRandom = (int)(Math.random()*13);
        }while(baraja[pintaRandom][indiceRandom].equals(""));

        mano.add(baraja[pintaRandom][indiceRandom]);
        baraja[pintaRandom][indiceRandom] = "";
    }

    public static void repartir(String[][] baraja, ArrayList<String> mano) {
        for (int i = 0; i < 2; i++) {
            pedirCarta(baraja,mano);
        }
    }

    public static ArrayList<Integer> asignarPuntos(ArrayList<String> mano) {
        ArrayList<Integer> puntos = new ArrayList<>();

        for (String carta : mano) {
            String indice = carta.substring(carta.length() - 1);
            switch (indice) {
                case "A" -> puntos.add(11);
                case "2" -> puntos.add(2);
                case "3" -> puntos.add(3);
                case "4" -> puntos.add(4);
                case "5" -> puntos.add(5);
                case "6" -> puntos.add(6);
                case "7" -> puntos.add(7);
                case "8" -> puntos.add(8);
                case "9" -> puntos.add(9);
                case "0", "J", "Q", "K" -> puntos.add(10);
            }
        }
        return puntos;
    }

    public static int sumarPuntos(ArrayList<String> mano) {
        ArrayList<Integer> puntosPorCarta = asignarPuntos(mano);
        int puntajeTotal = 0;
        for (Integer punto : puntosPorCarta) {
            puntajeTotal += punto;
        }
        if(puntosPorCarta.contains(11) && puntajeTotal>21){
            puntajeTotal = puntajeTotal - 10;
        }
        return puntajeTotal;
    }

    public static boolean esBlackJack(ArrayList<String> mano) {
        ArrayList<String> indices = new ArrayList<>();

        for (String carta : mano) {
            String indice = carta.substring(carta.length() - 1);
            indices.add(indice);
        }

        return (indices.contains("A") && (indices.contains("0") || indices.contains("J") || indices.contains("Q") || indices.contains("K")));
    }
    public static boolean verificarGanadorAutomatico(ArrayList<String> manoJugador, ArrayList<String> manoDealer) {
        int puntosJugador = sumarPuntos(manoJugador);
        int puntosDealer = sumarPuntos(manoDealer);


        if(esBlackJack(manoJugador)){
            System.out.println();
            System.out.println("GANASTE!");
            return true;
        }else if(puntosJugador>21){
            System.out.println();
            System.out.println("PERDISTE!");
            return true;
        }else if(puntosDealer>21){
            System.out.println();
            System.out.println("GANASTE!");
            return true;
        }else {
            return false;
        }
    }

    public static void verificarGanadorAlBajarse(ArrayList<String> manoJugador, ArrayList<String> manoDealer) {
        int puntosJugador = sumarPuntos(manoJugador);
        int puntosDealer = sumarPuntos(manoDealer);

        if(esBlackJack(manoDealer)) {
            System.out.println();
            System.out.println("PERDISTE!");
        }else if(puntosDealer>puntosJugador){
            System.out.println();
            System.out.println("PERDISTE!");
        }else if(puntosDealer<puntosJugador){
            System.out.println();
            System.out.println("GANASTE!");
        }else{
            System.out.println();
            System.out.println("PERDISTE!");
        }
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

    public static void mostrarEstadoJuego(ArrayList<String> manoJugador, ArrayList<String> manoDealer) {
        System.out.println();
        System.out.println("Mano Jugador = " + manoJugador);
        System.out.println("Puntos Jugador = " + sumarPuntos(manoJugador));
        System.out.println("Mano Dealer = " + manoDealer);
        System.out.println("Puntos Dealer = " + sumarPuntos(manoDealer));
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

}