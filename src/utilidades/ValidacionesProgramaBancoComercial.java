package utilidades;

import java.util.GregorianCalendar;
import java.util.Scanner;

import gestion.GestionBancoCentral;
import gestion.GestionBancoComercial;

public class ValidacionesProgramaBancoComercial 
{
	/* INTERFAZ
	 * Comentario: Lee y valida un inicio de sesion.
	 * Prototipo: public void iniciarSesion()
	 * Entrada: No hay
	 * Precondiciones: No hay
	 * Salida: Un String con el IBAN de la cuenta que ha iniciado sesion
	 * Postcondiciones: Asociado al nombre devuelve un String con el IBAN de la cuenta que ha iniciado sesion
	 */
	public String iniciarSesion()
	{
		GestionBancoCentral gestion = new GestionBancoCentral();
		Scanner teclado = new Scanner(System.in);
		String BIC, IBAN;
		
		System.out.println("Iniciar sesion...");
		
		do
		{
			System.out.print("IBAN: ");
			IBAN = teclado.next();
		}while(gestion.IBANRegistrado(IBAN) == false);		//OK
		
		System.out.println("Inicio de sesion correcto en la cuenta " + IBAN);
		
		return IBAN;
	}
	
	/* INTERFAZ
	 * Comentario: Muestra el menu principal y valida una opcion elegida
	 * Prototipo: public int mostrarMenuYValidarOpcionElegida
	 * Entrada: No hay
	 * Precondiciones: No hay
	 * Salida: un entero con la opcion elegida
	 * Postcondiciones: Asociado al nombre devuelve un entero con la opcion del menu elegida
	 */
	public int mostrarMenuYValidarOpcionElegida()
	{
		int opcion;
		Scanner teclado = new Scanner(System.in);
		
		System.out.println("MENU PRINCIPAL");
		System.out.println("1) Realizar transferencia bancaria");
		System.out.println("2) Ver datos de la cuenta en el banco central");
		System.out.println("3) Buscar movimientos de la cuenta en el banco central");
		System.out.println("4) Cliente nuevo");
		System.out.println("5) Gestionar una cuenta determinada");
		System.out.println("0) Salir del programa");
		
		do
		{
			System.out.println("Elige una opcion:");
			opcion = teclado.nextInt();
		}while(opcion < 0 || opcion > 6);
		
		return opcion;
	}
	
	/* INTERFAZ
	 * Comentario: Lee y valida si existe una cuenta
	 * Prototipo: public String leerYValidarCuentaDestino()
	 * Entrada: No hay
	 * Precondiciones: No hay
	 * Salida: Un String con el IBAN de la cuenta
	 * Postcondiciones: Asociado al nombre devuelve un String con el IBAN de la cuenta
	 */
	public String leerYValidarCuentaDestino()
	{
		String cuentaDestino;
		GestionBancoCentral gestion = new GestionBancoCentral();
		Scanner teclado = new Scanner(System.in);
		
		do
		{
			System.out.print("Introduce IBAN de destino: ");
			cuentaDestino = teclado.next();
		}while(gestion.IBANRegistrado(cuentaDestino) == false); 	//OK
		
		return cuentaDestino;
	}
	
	/* INTERFAZ
	 * Comentario: Lee y valida una fecha
	 * Prototipo: public GregorianCalendar leerYValidarFecha()
	 * Entrada: No hay
	 * Precondiciones: No hay
	 * Salida: Un objeto tipo GregorianCalendar
	 * Postcondiciones: Asociado al nombre devuelve un GregorianCalendar que representa la fecha validada.
	 */
	public GregorianCalendar leerYValidarFecha()
	{
		int dia = 0;
		int mes = 0;
		int anho = 0;
		Scanner teclado = new Scanner(System.in);
		
		System.out.print("Dia: ");
		dia = teclado.nextInt();
		
		System.out.print("Mes: ");
		dia = teclado.nextInt();
		
		System.out.print("Anho: ");
		dia = teclado.nextInt();
		
		return new GregorianCalendar(anho, mes, dia);
	}
	
	/* INTERFAZ
	 * Comentario: Lee y valida un DNI
	 * Prototipo: public Strnig leerYValidarDNI()
	 * Entrada: un String con el BIC del banco donde se comprobará si el DNI existe o no
	 * Precondiciones: el BIC debe ser de un banco existente
	 * Salida: Un String con un DNI
	 * Postcondiciones: Asociado al nombre devuelve un String con un DNI validado
	 */
	public String leerYValidarDNI(String BIC)
	{
		String DNI;
		Scanner teclado = new Scanner(System.in);
		GestionBancoComercial gestion = new GestionBancoComercial();
		
		do
		{
			System.out.print("Introduce DNI: ");
			DNI = teclado.next();
		}while(gestion.DNIRegistrado(DNI, BIC));
		
		return DNI;
	}
	
	/* INTERFAZ
	 * Comentario: Lee y valida una cantidad de ingresos mensuales
	 * Prototipo: public double leerYValdiarIngresosMensuales()
	 * Entrada: No hay
	 * Precondiciones: No hay
	 * Salida: un double con una cantidad de ingresos mensuales
	 * Postcondiciones: Asociado al nombre devuelve un double con un cantidad de ingresos mensuales positiva
	 */
	public double leerYValidarIngresosMensuales()
	{
		Scanner teclado = new Scanner(System.in);
		double ingresosMensuales;
		
		do
		{
			System.out.print("Introduce ingresos mensuales: ");
			ingresosMensuales = teclado.nextDouble();
		}while(ingresosMensuales < 0);
		
		return ingresosMensuales;
	}
}
