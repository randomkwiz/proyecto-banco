package tests;

import gestion.GestionBancoComercial;

public class GestionBancoComercialTest {
    public static void main(String[] args) {
        GestionBancoComercial gestion = new GestionBancoComercial();

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

    }
}
