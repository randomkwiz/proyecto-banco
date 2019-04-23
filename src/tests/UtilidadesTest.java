package tests;
import utilidades.Utilidades;
public class UtilidadesTest {
    public static void main(String[] args) {
        Utilidades util = new Utilidades();
        util.ordenarFicheroPorClave("./prueba.txt",1);
        //System.out.println("Hola");
    }
}
