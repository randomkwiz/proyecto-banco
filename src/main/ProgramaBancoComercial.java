/* ANALISIS
 * 
 * Programa que podrá manejar un banco comercial para hacer distintas cosas, como realizar transferencia a otro banco,
 * ver la liquidez de su cuenta en el banco central, etc.
 * 
 */

/* PSEUDOCODIGO
 * Inicio
 * 	Leer y validar inicio de sesion
 * 	Mostrar menu y validar opcion elegida
 * 	Mientras (opcionElegida no sea salir)
 * 		Segun(opcionElegida)
 * 			caso 1: realizar transferencia bancaria
 * 			caso 2: ver datos de la cuenta en el banco central
 * 			caso 3: buscar movimientos de la cuenta en el banco central
 * 			caso 4: cliente nuevo
 * 			caso 5: gestionar una cuenta determinada
 * 		FinSegun
 * 		Mostrar menu y validar opcion elegida
 * 	FinMientras
 * Fin
 */

/* PSEUDOCODIGO (modulos)
 * 
 * - gestiojanr una cuenta determinada
 * Inicio
 * 	Mostrar clientes y validar opcion cliente elegido
 * 	Mientras (opcion no sea salir)
 * 		Mostrar cuentas del cliente elegido y validar opcion cuenta elegida
 * 		Mientras (opcion no sea volver atras)
 * 			Mostrar menu y elegir opcion
 * 			Mientras (opcion no sea volver atras)
 * 				Segun(opcion)
 * 					caso 1: ver datos de la cuenta
 * 					caso 2: ver movimientos de la cuenta
 * 					caso 3: modificar dinero de la cuenta
 * 					caso 4: cancelar cuenta
 * 				FinSegun
 * 			FinMientras
 * 		FinMientras
 * 	FinMientras
 * Fin
 * 
 * - realizar transferencia bancaria
 * 	Inicio
 * 		Leer y validar cuenta destino
 * 		
 * 	Fin
 */

package main;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

import gestion.GestionBancoCentral;
import gestion.GestionBancoComercial;
import utilidades.Utilidades;
import utilidades.ValidacionesProgramaBancoComercial;

public class ProgramaBancoComercial 
{
	public static void main(String[] args)
    {
    	Scanner teclado = new Scanner(System.in);
    	ValidacionesProgramaBancoComercial validaciones = new ValidacionesProgramaBancoComercial();
    	Utilidades utils = new Utilidades();
    	double cantidad, ingresosMensuales;
    	int opcionElegida;
    	GestionBancoComercial gestionComercial = new GestionBancoComercial();
    	GestionBancoCentral gestionCentral = new GestionBancoCentral();
    	String cuentaDestino, IBAN, DNI, BIC, concepto, IBANNuevoCliente;
    	GregorianCalendar fecha;
    	int dia, mes, anyo;
    	
	 	//Leer y validar inicio de sesion
    	IBAN = validaciones.iniciarSesion();
    	BIC = gestionCentral.obtenerBICporIBAN(IBAN);
	  	//Mostrar menu y validar opcion elegida
    	opcionElegida = validaciones.mostrarMenuYValidarOpcionElegida();
    	
    	while(opcionElegida != 0)
    	{
	  		switch(opcionElegida)
	  		{
		  		case 1: 
		  			//realizar transferencia bancaria
		  			cuentaDestino = validaciones.leerYValidarCuentaDestino();
		  			
		  			System.out.println("Cantidad: ");
		  			cantidad = teclado.nextDouble();
		  			
		  			teclado.nextLine();
		  			System.out.print("Concepto: ");
		  			concepto = teclado.nextLine();
		  			
		  			GregorianCalendar fechaActual = new GregorianCalendar();

		  			gestionCentral.realizarMovimiento(IBAN, cuentaDestino, concepto, cantidad, fechaActual); //TODO Aqui pondría que devolviera un boolean para saber si se realizo el movimiento bien o no.
		  			
		  			break;
		  		case 2: 
		  			//ver datos de la cuenta en el banco central
		  			utils.imprimirDatosCuenta(gestionCentral.datosCuenta(IBAN));		//TODO Estaría bien hacerle un pretty print en la clase de utilidad a este metodo
		  			break;
		  		case 3:
		  			//buscar movimientos por fecha de la cuenta en el banco central
		  			fecha = validaciones.leerYValidarFecha();
		  			
		  			System.out.println("Movimientos del " + fecha.getTime());
		  			utils.imprimirMovimientos(gestionCentral.buscarMovimientosPorFecha(IBAN, fecha.get(Calendar.DAY_OF_MONTH), fecha.get(Calendar.MONTH), fecha.get(Calendar.YEAR)));		//TODO posible pretty print
		  			break;
		  		case 4: 
		  			//cliente nuevo
		  			DNI = validaciones.leerYValidarDNI(BIC);		
		  			
		  			ingresosMensuales = validaciones.leerYValidarIngresosMensuales();
		  			
		  			IBANNuevoCliente = gestionComercial.insertarCliente(BIC, DNI,ingresosMensuales);
		  			if(IBANNuevoCliente != null)
		  				System.out.println("Nuevo cliente creado, apunta el IBAN de su cuenta: " + IBANNuevoCliente);
		  			else
		  				System.out.println("El cliente no ha podido crearse, inténtalo de nuevo");
		  			break;
		  		case 5: 
		  			//gestionar una cuenta determinada
		  			break;	//TODO Modulo de gestionar una cuenta determinada
    		}
	  		
	  		//Mostrar menu y validar opcion elegida
	    	opcionElegida = validaciones.mostrarMenuYValidarOpcionElegida();
    	}
    }
}
