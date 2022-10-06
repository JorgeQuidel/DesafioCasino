import java.util.InputMismatchException;
import java.util.Scanner;

public class Utilidad {

    public static int pedirOpcion() {
        try {
            return pedirValorEntero();
        } catch (InputMismatchException e) {
            System.err.println("Por favor ingrese un numero que corresponda a una de las opciones");
            return pedirOpcion();
        }
    }
    public static int pedirValorEntero() throws InputMismatchException {
        return new Scanner(System.in).nextInt();
    }


}
