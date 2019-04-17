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

import java.util.GregorianCalendar;
import java.util.Scanner;

import gestion.GestionBancoCentral;
import gestion.GestionBancoComercial;

import utilidades.ValidacionesProgramaBancoComercial;

public class ProgramaBancoComercial 
{
    private static int DAY_OF_MONTH;
	private static int MONTH;
	private static int YEAR;

	public static void main(String[] args)
    {
    	Scanner teclado = new Scanner(System.in);
    	ValidacionesProgramaBancoComercial validaciones = new ValidacionesProgramaBancoComercial();
    	double cantidad, ingresosMensuales;
    	int opcionElegida;
    	GestionBancoComercial gestionComercial = new GestionBancoComercial();
    	GestionBancoCentral gestionCentral = new GestionBancoCentral();
    	String cuentaDestino, IBAN, DNI, BIC, concepto;
    	GregorianCalendar fecha;
    	int dia, mes, anyo;
    	
	 	//Leer y validar inicio de sesion
    	IBAN = validaciones.iniciarSesion();
    	
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
		  			
		  			System.out.print("Concepto: ");
		  			concepto = teclado.next();
		  			
		  			GregorianCalendar fechaActual = new GregorianCalendar();
		  			dia = fechaActual.get(DAY_OF_MONTH);	// <-     No se si esto esta bien
		  			mes = fechaActual.get(MONTH);			// <-     No se si esto esta bien
		  			anyo = fechaActual.get(YEAR);			//TODO <- No se si esto esta bien
		  			
		  			//TODO En este metodo faltaria que se hiciera tambien el movimiento en el banco central cuando es una transferencia entre dos clientes de distintos bancos.
		  			gestionComercial.realizarMovimiento(IBAN, cuentaDestino, concepto, cantidad, dia, mes, anyo);
		  			
		  			break;
		  		case 2: 
		  			//ver datos de la cuenta en el banco central
		  			System.out.println(gestionCentral.datosCuenta(IBAN));
		  			break;
		  		case 3:
		  			//buscar movimientos por fecha de la cuenta en el banco central
		  			fecha = validaciones.leerYValidarFecha();
		  			
		  			gestionCentral.mostrarMovimientosPorFecha(fecha, IBAN);
		  			break;
		  		case 4: 
		  			//cliente nuevo
		  			//DNI = validaciones.leerYValidarDNI(BIC);		//TODO Hay que sacar el BIC a partir del IBAN ingresado para iniciar sesion
		  			
		  			ingresosMensuales = validaciones.leerYValidarIngresosMensuales();
		  			
		  			//gestionComercial.insertarCliente(BIC, DNI,ingresosMensuales);
		  			break;
		  		case 5: 
		  			//gestionar una cuenta determinada
		  			break;
    		}
	  		
	  		//Mostrar menu y validar opcion elegida
	    	opcionElegida = validaciones.mostrarMenuYValidarOpcionElegida();
    	}
    }
}
