package utilidades;

import java.util.List;

public class Utilidades {
    /*
    *Signatura: public void imprimirMovimientos(List<String> movimientos)
    * Comentario: imprime una lista de Strings en pantalla
    * */
    public void imprimirMovimientos(List<String> movimientos){
    	String[] campos;
    	
        for(String element:movimientos)
        {
            campos = element.split(",");
            
            System.out.println("-------------------------------");
            System.out.println("Fecha: " + campos[3]);
            System.out.println("Tipo: " + campos[1]);
            System.out.println("Cantidad: " + campos[2]);
            System.out.println("Concepto: " + campos[0]);
        }
    }
    
    public void imprimirDatosCuenta(String datosCuenta)
    {
    	String[] campos = datosCuenta.split(",");
    	
    	System.out.println("Numero de cuenta: " + campos[0]);
    	System.out.println("Saldo: " + campos[1]);
    }
}
