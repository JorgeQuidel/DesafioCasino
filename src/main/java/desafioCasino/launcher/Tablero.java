package desafioCasino.launcher;

import desafioCasino.modelos.juegos.Blackjack;
import desafioCasino.modelos.juegos.Carioca;
import desafioCasino.modelos.juegos.Escoba;
import desafioCasino.modelos.juegos.Poker;
import desafioCasino.utilidades.Utilidad;

public class Tablero {
    public static void main(String[] args) {
        elegirJuego();
    }
    public static void elegirJuego(){
        bucle:
        while(true){
            System.out.print("\nQue quiere jugar?\n[1].Blackjack\n[2].Poker\n[3].Carioca\n[4].Escoba\n[0].Salir\n>");
            switch (Utilidad.pedirOpcionEntera()) {
                case 0 -> {System.out.println("Hasta pronto");break bucle;}
                case 1 -> new Blackjack().jugar();
                case 2 -> new Poker().jugar();
                case 3 -> new Carioca().jugar();
                case 4 -> new Escoba().jugar();
                default -> System.out.println("Ingrese una de las opciones");
            }
        }
    }
}