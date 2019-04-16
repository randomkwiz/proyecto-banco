package tests;

import gestion.GestionBancoComercial;

public class GestionBancoComercialTest {
    public static void main(String[] args) {
        GestionBancoComercial gestion = new GestionBancoComercial();

        //System.out.println(gestion.obtenerBICporNombre("LaCaixa"));
        //System.out.println(gestion.obtenerNombrePorBIC("CAIXESBBXXX"));

        //gestion.modificarSaldoEnFicheroCuentas("LaCaixa","AAAAAAAAAAA", 50);
        /*
        gestion.insertarMovimientoEnFicheroMovimientos("LaCaixa","AAAAAAAAAAA",true,"PRUEBA",60,1,1,1999  );
        gestion.insertarMovimientoEnFicheroMovimientos("LaCaixa","AAAAAAAAAAA",false,"PRUEBA2",415,5,8,2010  );
        gestion.insertarMovimientoEnFicheroMovimientos("LaCaixa","AAAAAAAAAAA",false,"PRUEBA542",415,6,9,2005  );
*/
        //gestion.insertarMovimientoEnFicheroMovimientos("Santander","AAAAAAAAAAA",false,"PRUEBA542",415,6,9,2005  );

        //gestion.ordenarMovimientosPorFecha("LaCaixa","AAAAAAAAAAA");

        gestion.realizarMovimiento("LaCaixa","AAAAAAAAAAA", "LaCaixa","EEEEEEEEEEE", "Transferencia de prueba22",12300000,16,4,2019 );


    }
}
