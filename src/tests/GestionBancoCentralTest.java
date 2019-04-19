package tests;

import java.util.GregorianCalendar;

import gestion.GestionBancoCentral;

public class GestionBancoCentralTest 
{

	public static void main(String[] args) 
	{
		GestionBancoCentral gestion = new GestionBancoCentral();

		System.out.println("gestion.BICRegistrado(\"CAIXESBBXXX\"): " + gestion.BICRegistrado("CAIXESBBXXX"));
		
		System.out.println("gestion.datosCuenta(\"ESPCAIXESBBXXX0000001\"): " + gestion.datosCuenta("ESPCAIXESBBXXX0000001"));
		
		System.out.println("gestion.IBANRegistrado(\"ESPBSCHESMMXXX13XXXXXXX\"): " + gestion.IBANRegistrado("ESPBSCHESMMXXX13XXXXXXX"));
		
		System.out.println("-------------------------------------------");
		
		System.out.println("gestion.mostrarMovimientosPorFecha(fechaActual, \"ESPINGDESMMXXX14XXXXXXX\"):");
		gestion.mostrarMovimientosPorFecha(new GregorianCalendar(), "ESPINGDESMMXXX14XXXXXXX");
		
		System.out.println("-------------------------------------------");
		
		System.out.println("gestion.obtenerBICporIBAN(\"ESPCAIXESBBXXX12XXXXXXX\"): " + gestion.obtenerBICporIBAN("ESPCAIXESBBXXX12XXXXXXX"));
		
		System.out.println("gestion.obtenerNombreBancoComercialPorIBAN(\"ESPCAIXESBBXXX12XXXXXXX\"): " + gestion.obtenerNombreBancoComercialPorIBAN("ESPCAIXESBBXXX12XXXXXXX"));
		
		System.out.println("gestion.obtenerNombrePorBIC(\"BSCHESMMXXX\"): " + gestion.obtenerNombrePorBIC("BSCHESMMXXX"));
		
		System.out.println("-------------------------------------------");
		
		System.out.println("gestion.ingresarDinero(\"ESPCAIXESBBXXX12XXXXXXX\", \"Concepto\", 25000, 10, 4, 2019)");
		System.out.println("ANTES -> " + gestion.datosCuenta("ESPCAIXESBBXXX12XXXXXXX"));
		gestion.ingresarDinero("ESPCAIXESBBXXX12XXXXXXX", "Concepto", 25000, new GregorianCalendar());
		System.out.println("DESPUES -> " + gestion.datosCuenta("ESPCAIXESBBXXX12XXXXXXX"));
		
		System.out.println("-------------------------------------------");
		
		System.out.println("gestion.modificarSaldoEnFicheroCuentas(\"ESPCAIXESBBXXX12XXXXXXX\", true, 500)");
		System.out.println("ANTES -> " + gestion.datosCuenta("ESPCAIXESBBXXX12XXXXXXX"));
		gestion.modificarSaldoEnFicheroCuentas("ESPCAIXESBBXXX12XXXXXXX", true, 500);
		System.out.println("DESPUES -> " + gestion.datosCuenta("ESPCAIXESBBXXX12XXXXXXX"));
		
		System.out.println("-------------------------------------------");
		
		System.out.println("gestion.realizarMovimiento(\"ESPINGDESMMXXX14XXXXXXX\", \"ESPBSCHESMMXXX13XXXXXXX\", \"test_realizarMovimiento\", 200, 17, 11, 2016)");
		System.out.println("ANTES:");
		System.out.println("CUENTA 1 -> " + gestion.datosCuenta("ESPINGDESMMXXX14XXXXXXX"));
		System.out.println("CUENTA 2 -> " + gestion.datosCuenta("ESPBSCHESMMXXX13XXXXXXX"));
		gestion.realizarMovimiento("ESPINGDESMMXXX14XXXXXXX", "ESPBSCHESMMXXX13XXXXXXX", "test_realizarMovimiento", 200, new GregorianCalendar());
		System.out.println("DESPUES:");
		System.out.println("CUENTA 1 -> " + gestion.datosCuenta("ESPINGDESMMXXX14XXXXXXX"));
		System.out.println("CUENTA 2 -> " + gestion.datosCuenta("ESPBSCHESMMXXX13XXXXXXX"));
		
		System.out.println("-------------------------------------------");
		
		System.out.println("gestion.sacarDinero(\"ESPCAIXESBBXXX12XXXXXXX\", \"Concepto\", 25000, 10, 4, 2019)");
		System.out.println("ANTES -> " + gestion.datosCuenta("ESPCAIXESBBXXX12XXXXXXX"));
		gestion.sacarDinero("ESPCAIXESBBXXX12XXXXXXX", "Concepto", 25000, new GregorianCalendar());
		System.out.println("DESPUES -> " + gestion.datosCuenta("ESPCAIXESBBXXX12XXXXXXX"));
		
		System.out.println("-------------------------------------------");
		
		System.out.println("gestion.buscarMovimientosPorFecha(\"ESPCAIXESBBXXX12XXXXXXX\", 2019)");
		System.out.println(gestion.buscarMovimientosPorFecha("ESPCAIXESBBXXX12XXXXXXX", 2019));
		
		System.out.println();
		
		System.out.println("gestion.buscarMovimientosPorFecha(\"ESPCAIXESBBXXX12XXXXXXX\", 4, 2019)");
		System.out.println(gestion.buscarMovimientosPorFecha("ESPCAIXESBBXXX12XXXXXXX", 4, 2019));
		
		System.out.println();
		
		System.out.println("gestion.buscarMovimientosPorFecha(\"ESPCAIXESBBXXX12XXXXXXX\", 19, 4, 2019)");
		System.out.println(gestion.buscarMovimientosPorFecha("ESPCAIXESBBXXX12XXXXXXX", 19, 4, 2019));
		
		System.out.println("-------------------------------------------");
		
		System.out.println("gestion.isCuentaBorrada(\"ESPCAIXESBBXXX12XXXXXX\"): " + gestion.isCuentaBorrada("ESPCAIXESBBXXX12XXXXXX"));
		
		System.out.println("gestion.marcarCuentaComoBorrada(\"ESPBSCHESMMXXX0000000\")");
		System.out.println("ANTES -> " + gestion.isCuentaBorrada("ESPBSCHESMMXXX0000000"));
		gestion.marcarCuentaComoBorrada("ESPBSCHESMMXXX0000000");
		System.out.println("DESPUES -> " + gestion.isCuentaBorrada("ESPBSCHESMMXXX0000000"));
		
		System.out.println("-------------------------------------------");
		
		System.out.println("gestion.eliminarCuentasBorradasDefinitivamente()");
		System.out.println("ANTES -> " + gestion.IBANRegistrado("ESPBSCHESMMXXX0000000"));
		gestion.eliminarCuentasBorradasDefinitivamente();
		System.out.println("DESPUES -> " + gestion.IBANRegistrado("ESPBSCHESMMXXX0000000"));
	}

}
