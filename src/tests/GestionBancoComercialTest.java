package tests;

import gestion.GestionBancoComercial;

public class GestionBancoComercialTest {
    public static void main(String[] args) {
        GestionBancoComercial gestion = new GestionBancoComercial();
/*
        System.out.println(gestion.obtenerBICporNombre("LaCaixa"));
        System.out.println(gestion.obtenerNombrePorBIC("CAIXESBBXXX"));

        gestion.modificarSaldoEnFicheroCuentas("ESPCAIXESBBXXX12XXXXXXX", true,50);

        gestion.insertarMovimientoEnFicheroMovimientos("ESPCAIXESBBXXX0000001",true,"PRUEBA",60,1,1,1999  );
        gestion.insertarMovimientoEnFicheroMovimientos("ESPCAIXESBBXXX0000001",true,"PRUEBA",60,1,1,1989  );
        gestion.insertarMovimientoEnFicheroMovimientos("ESPCAIXESBBXXX0000002",true,"PRUEBA",60,1,1,2015  );
        gestion.insertarMovimientoEnFicheroMovimientos("ESPINGDESMMXXX0000002",true,"PRUEBA",60,1,1,1999  );
        gestion.insertarMovimientoEnFicheroMovimientos("ESPBSCHESMMXXX0000003",true,"PRUEBA",60,1,1,1999  );

        //gestion.ordenarMovimientosPorFecha("ESPCAIXESBBXXX12XXXXXXX");

        gestion.realizarMovimiento("ESPBSCHESMMXXX0000003", "ESPCAIXESBBXXX0000001","prueba de transaccion", 512,5,12,2019 );

       System.out.println(gestion.obtenerClientePorIBAN("ESPBSCHESMMXXX0000003"));
        System.out.println(gestion.obtenerIBANPorCliente("LaCaixa", "28835488-C"));

        System.out.println(gestion.obtenerNombreBancoComercialPorIBAN("ESPCAIXESBBXXX0000001"));
*/
/*
        System.out.println("Este dni en este banco: "+gestion.isDNIvalido("LaCaixa","28835488-C"));
        System.out.println("Este IBAN existe: "+gestion.isIBANvalido("ESPINGDESMMXXX0000001"));
        System.out.println("Este dni es propietario de esta cuenta: "+gestion.isPropietario("28835488-C", "ESPINGDESMMXXX0000001"));
        System.out.println(gestion.isDNIvalido("Santander","55555"));
        System.out.println(gestion.isIBANvalido("555555555"));  //nota: el IBAN debe tener al menos 13 caracteres o peta
        System.out.println(gestion.isPropietario("28835488-C","ESPINGDESMMXXX0000003"));
        */


        //gestion.ordenarMovimientosPorFecha("ESPCAIXESBBXXX0000001");    //funciona bien tras el cambio a GregorianCalendar

        gestion.imprimirUltimosDiezMovimientos("ESPCAIXESBBXXX0000001");
    }
}
