package tests;
import gestion.GestionBancoCentral;
import utilidades.Utilidades;

import java.util.GregorianCalendar;

public class UtilidadesTest {
    public static void main(String[] args) {
        Utilidades util = new Utilidades();
        GestionBancoCentral g = new GestionBancoCentral();
        g.insertarMovimientoEnFicheroMovimientos("ESPCAIXESBBXXX0000000",true,"Hola",45,new GregorianCalendar());

        util.imprimirMovimientos(g.ultimosDiezMovimientos("ESPCAIXESBBXXX0000000"));
    }
}
