package blackjackPOO;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Utilidad {

    public static int pedirOpcionMenorA(int base){
        int valor = pedirOpcionEntera();
        if(valor > base){
            System.err.println("Por favor ingrese un numero menor o igual a " + base);
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

    public static String pedirStringNoVacio() {
        String texto = pedirString();
        if(texto.isBlank()){
            System.err.println("La entrada ingresada se encuentra vacia, intente nuevamente");
            return pedirStringNoVacio();
        }else {
            return texto;
        }
    }

    public static String pedirString() {
        return new Scanner(System.in).nextLine();
    }
}
