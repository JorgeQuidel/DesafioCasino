import java.util.stream.IntStream;

public class Utilidad {

    public static boolean esMayorQue21(int numero){
        return numero>21;
    }

    public static boolean esBlackjack(Mano mano){
        if(mano.getCartas().size()>2){
            return false;
        }
        return contieneAs(mano) && contieneValor10(mano);
    }

    public static boolean contieneAs(Mano mano){
        return IntStream.range(0, mano.getCartas().size())
                .anyMatch(carta -> mano.getCartas().get(carta).getIndice().equals("A"));
    }

    public static boolean contieneValor10(Mano mano){
        return IntStream.range(0, mano.getCartas().size())
                .anyMatch(carta -> mano.getCartas().get(carta).getValor() == 10);
    }

    public static boolean asVale11(Mano mano){
        int puntaje = mano.obtenerPuntaje();
        return puntaje <= 21 && contieneAs(mano);
    }
}
