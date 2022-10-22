package blackjackPOO;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Utilidad {

    public static int pedirOpcionMenorA(int base){
        int valor = pedirOpcionEntera();
        if(valor > base){
            System.err.println("Por favor ingrese un numero menor a " + base);
            return pedirOpcionMenorA(base);
        }else {
            return valor;
        }
    }
    public static int pedirOpcionEnteraPositiva(){
        int valor = pedirOpcionEntera();
        if (valor <= 0) {
            System.err.println("Por favor ingrese un numero mayor a 0");
            return pedirOpcionEnteraPositiva();
        }else{
            return valor;
        }
    }
    public static int pedirOpcionEntera() {
        try {
            return pedirValorEntero();
        } catch (InputMismatchException e) {
            System.err.println("Por favor ingrese un numero entero");
            return pedirOpcionEntera();
        }
    }
    public static int pedirValorEntero() throws InputMismatchException {
        return new Scanner(System.in).nextInt();
    }

    public static String pedirString() {
        return new Scanner(System.in).next();
    }
    public static String pedirStringEspecifico(String stringEsperado, String stringEsperado2) {
        String resultante = "";
        do{
            resultante = pedirString();
        }while(!resultante.equalsIgnoreCase(stringEsperado) && !resultante.equalsIgnoreCase(stringEsperado2));
        return resultante;
    }

}
