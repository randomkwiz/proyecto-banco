package utilidades;

import java.util.List;

public class Utilidades {
    /*
    *Signatura: public void imprimirMovimientos(List<String> movimientos)
    * Comentario: imprime una lista de Strings en pantalla
    * */
    public void imprimirMovimientos(List<String> movimientos){
        for(String element:movimientos){
            System.out.println(element);
        }
    }
}
