package gestion;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class GestionBancoCentral extends GestionBanco
{
	/* INTERFAZ
     * Signatura: public void realizarMovimiento(String nombre_banco_origen,String cuenta_origen,String nombre_banco_destino, String cuenta_destino, String concepto,double cantidad, int dia, int mes, int anyo)
     * Comentario: Realiza un movimiento bancario, sacando una cantidad de la cuenta de origen e ingresándola en la cuenta destino.
     *              Llama a los métodos sacarDinero e ingresarDinero.
     * Precondiciones: Por referencia se pasan las ID de las cuentas y los nombres de los bancos de origen y destino, por valor se pasa la cantidad y dia mes y anyo. Tambien se pasa por referencia el concepto
     * Entrada: (String nombre_banco_origen,String cuenta_origen,String nombre_banco_destino, String cuenta_destino, String concepto,double cantidad, int dia, int mes, int anyo)
     * Salida:
     * Entrada/Salida:
     * Postcondiciones: Se modificarán los ficheros de cuentas y de movimientos correspondientes.
     * */
	public void realizarMovimiento(String IBANOrigen,String IBANDestino, String concepto,double cantidad, int dia, int mes, int anyo){
        sacarDinero(IBANOrigen, concepto, cantidad, dia, mes, anyo);
        ingresarDinero(IBANDestino, concepto, cantidad, dia, mes, anyo);
    }
	
	/*INTERFAZ
     * Signatura: public void sacarDinero(String nombre_banco, String ID_Cuenta, String concepto,double cantidad, int dia, int mes, int anyo)
     * Comentario: saca una cantidad dada de una cuenta
     * Precondiciones: Por valor se pasa una cantidad, por referencia la ID de una cuenta y el nombre del banco. Se pasa por valor dia mes y año
     * Entrada: String nombre_banco, String ID_Cuenta, double cantidad, int dia, int mes, int anyo
     * Salida:
     * Entrada/Salida:
     * Postcondiciones: Se modificarán los ficheros de Cuentas modificando el saldo y de movimientos, añadiendo el movimiento correspondiente.
     * */
	public void sacarDinero(String IBAN,String concepto, double cantidad, int dia, int mes, int anyo){
        insertarMovimientoEnFicheroMovimientos(IBAN, false, concepto, cantidad, dia, mes, anyo);
        modificarSaldoEnFicheroCuentas(IBAN, false, cantidad);

    }
	
	/* INTERFAZ
     * Signatura: public void modificarSaldoEnFicheroCuentas(String nombre_banco,String iban_cuenta, boolean sumaOresta,double cantidad)
     * Comentario: Este método se encarga de modificar en el fichero de Cuentas, el registro del saldo total (campo 2).
     * Precondiciones: Se pasa por referencia el ID de la cuenta a modificar y por valor la cantidad a añadir o substraer. Se pasa boolean que es true si añade la cantidad o false si la resta
     * Entrada: String nombre_banco, String ID_Cuenta, boolean sumaOresta, double cantidad
     * Salida:
     * Entrada/Salida:
     * Postcondiciones: Se modifica el fichero de Cuentas y se actualiza el saldo pertinente.
     * */
	public void modificarSaldoEnFicheroCuentas(String IBAN, boolean sumaOresta,double cantidad)
	{
		//String nombreBanco = obtenerNombreBancoComercialPorIBAN(IBAN);
		
		File ficheroCuentas = new File ("./Files/BancoCentral/Cuentas_BancoCentral.txt");
        FileReader leer = null;
        BufferedReader br = null;
        FileWriter fw = null;
        BufferedWriter bw = null;
        String campos[] = null;
        List<String> registros = new ArrayList<String>();
        String registro = " ";

        try 
        {
            leer = new FileReader(ficheroCuentas);
            br = new BufferedReader(leer);

            while(br.ready())
            {
                registro = br.readLine();
                campos = registro.split(",");

                if(campos[0].equals(IBAN))
                {
                    if(sumaOresta)
                        registro = registro.replace(campos[1], Double.toString(cantidad+Double.parseDouble(campos[1])));
                    else
                        registro = registro.replace(campos[1], Double.toString(Double.parseDouble(campos[1])-cantidad));
                }

                registros.add(registro);
            }
            br.close();

            /*Sí, aquí lo escribe de nuevo ¯\_(ツ)_/¯ */
            fw = new FileWriter(ficheroCuentas);
            bw = new BufferedWriter(fw);
            for(String s : registros) {
                bw.write(s);
                bw.newLine();
            }
            bw.close();
            
        }
        catch (IOException e)
        {
        	e.printStackTrace();
        }
	}
	
	/* INTERFAZ
     * Signatura: public void insertarMovimientoEnFicheroMovimientos(String nombre_banco,String ID_Cuenta,boolean isIngresoOrRetirada, String concepto, double cantidad,int dia, int mes, int anyo)
     * Comentario: Este método se encarga de modificar en el fichero de movimientos de la cuenta, añade un nuevo movimiento.
     * Precondiciones: Se pasa por referencia el ID de la cuenta y del banco a modificar y por valor la cantidad de dinero a mover. Se pasa
     *                  un boolean que es true si el movimiento es un ingreso o false si es una retirada de dinero. Tambien se pasa la fecha como tres valores enteros (se supone válida)
     * Entrada: (String nombre_banco,String ID_Cuenta,boolean isIngresoOrRetirada, double cantidad,int dia, int mes, int anyo)
     * Salida:
     * Entrada/Salida:
     * Postcondiciones: Se modifica el fichero de movimientos de cuentas, añadiendo un movimiento nuevo.
     * */
	public void insertarMovimientoEnFicheroMovimientos(String IBAN,boolean isIngresoOrRetirada, String concepto, double cantidad,int dia, int mes, int anyo)
	{
		File ficheroCuentas = new File ("./Files/BancoCentral/MovimientosCuentas/Movimientos_"+IBAN+".txt");
        FileWriter fw = null;
        BufferedWriter bw = null;
        String signo = "RETIRADA,-";
        try 
        {
            fw = new FileWriter(ficheroCuentas,true);
            bw = new BufferedWriter(fw);
            if(isIngresoOrRetirada)
            	
                signo = "INGRESO,+";
            
            bw.write(concepto+","+signo+Double.toString(cantidad)+","+Integer.toString(dia)+"/"+Integer.toString(mes)+"/"+Integer.toString(anyo));
            bw.newLine();
            bw.close();
            
        }
        catch (IOException e)
        {
        	e.printStackTrace();
        }

	}
	
	/* INTERFAZ
     * Signatura: public void ingresarDinero(String nombre_banco, String ID_Cuenta,String concepto, double cantidad, int dia, int mes, int anyo)
     * Comentario: ingresa una cantidad dada de una cuenta
     * Precondiciones: Por valor se pasa una cantidad, por referencia la ID de una cuenta y el nombre del banco. Por valor se pasa dia, mes y año
     * Entrada: String nombre_banco, String ID_Cuenta, double cantidad, int dia, int mes, int anyo
     * Salida:
     * Entrada/Salida:
     * Postcondiciones: Se modificarán los ficheros de Cuentas modificando el saldo y de movimientos, añadiendo el movimiento correspondiente.
     * */
	public void ingresarDinero(String IBAN,String concepto, double cantidad, int dia, int mes, int anyo){
        insertarMovimientoEnFicheroMovimientos(IBAN, true, concepto, cantidad, dia, mes, anyo);
        modificarSaldoEnFicheroCuentas(IBAN, true, cantidad);

    }
	
	/* INTERFAZ
	 * Comentario: Accede al fichero de cuentas y busca una cuenta por su IBAN para leer sus datos
	 * Prototipo: public String datosCuenta(String IBAN)
	 * Precondiciones: No hay
	 * Entrada: el IBAN de la cuenta
	 * Salida: un String con los datos de la cuenta
	 * Postcondiciones: Asociado al nombre devuelve un String con los datos de la cuenta
	 */
	public String datosCuenta(String IBAN)
	{
		String cuenta = null;
		File ficheroCuentas = new File("./Files/BancoCentral/Cuentas_BancoCentral.txt");
		FileReader fr = null;
		BufferedReader br = null;
		String registro;
		String[] campos;
		
		try
		{
			fr = new FileReader(ficheroCuentas);
			br = new BufferedReader(fr);
			
			while(br.ready())
			{
				//buscar la cuenta y guardarla en el String que se devuelve
				registro = br.readLine();
				campos = registro.split(",");
				
				if(campos[0].equals(IBAN))
					cuenta = registro;
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		//System.out.println("datosCuenta en resguardo");
		
		return cuenta;
	}
	
	/* INTERFAZ
	 * Comentario: muestra los movimientos de una cuenta en una determinada fecha
	 * Prototipo: public void mostrarMovmientosPorFecha(GregorianCalendar fecha, String IBAN)
	 * Precondiciones: No hay
	 * Entrada: un GregorianCalendar con la fecha y un String con el IBAN de la cuenta
	 * Salida: No hay.
	 * Postcondiciones: No hay. solo imprime en pantalla.
	 */
	public void mostrarMovimientosPorFecha(GregorianCalendar fecha, String IBAN)
	{
		System.out.println("mostrarMovimientosPorFecha en resguardo");
	}
	
	/* INTERFAZ
	 * Comentario: Comprueba si existe un cliente(BIC) registrado en el banco central
	 * Prototipo: public boolean BICRegistrado(String BIC)
	 * Entrada: un String con el BIC del clente a comprobar
	 * Precondiciones: No hay
	 * Salida: un boolean indicando si el BIC esta registrado ya o no
	 * Postcondiciones: Asociado al nombre devuelve true si el BIC est� ya registrado en el banco o false de lo contrario.
	 */
	public boolean BICRegistrado(String BIC)
	{
		boolean registrado = false;
		
		File ficheroClientes = new File ("./Files/BancoCentral/Clientes_BancoCentral.txt");
        FileReader leer = null;
        BufferedReader br = null;
        
        String campos[] = null;
        String registro = " ";

        try 
        {
            leer = new FileReader(ficheroClientes);
            br = new BufferedReader(leer);

            while(br.ready())
            {
                registro = br.readLine();
                campos = registro.split(",");
                
                //campos[0] = campos[0].substring(3, campos[0].length());
                
                if(campos[0].equals("ESP"+BIC))
                	registrado = true;
            }
            
            br.close();
        }
        catch (IOException e)
        {
        	e.printStackTrace();
        }
		
		return registrado;
	}
	
	/* INTERFAZ
	 * Comentario: Comprueba si existe una cuenta(IBAN) registrada en el banco central
	 * Prototipo: public boolean IBANRegistrado(String IBAN)
	 * Entrada: un String con el IBAN de la cuenta a comprobar
	 * Precondiciones: No hay
	 * Salida: un boolean indicando si el IBAN esta registrado ya o no
	 * Postcondiciones: Asociado al nombre devuelve true si el IBAN est� ya registrado en el banco o false de lo contrario.
	 */
	public boolean IBANRegistrado(String IBAN)
	{
		boolean registrado = false;
		//System.out.println("IBANRegistrado en resguardo");
		File ficheroCuentas = new File ("./Files/BancoCentral/Cuentas_BancoCentral.txt");
        FileReader leer = null;
        BufferedReader br = null;
        //FileWriter fw = null;
        //BufferedWriter bw = null;
        String campos[] = null;
        //List<String> registros = new ArrayList<String>();
        String registro = " ";

        try 
        {
            leer = new FileReader(ficheroCuentas);
            br = new BufferedReader(leer);

            while(br.ready())
            {
                registro = br.readLine();
                campos = registro.split(",");
                
                if(campos[0].equals(IBAN))
                	registrado = true;
            }
            
            br.close();
        }
        catch (IOException e)
        {
        	e.printStackTrace();
        }
		
		return registrado;
	}
	
	/*
     * INTERFAZ
     * Signatura: public String obtenerNombreBancoComercialPorIBAN(String iban_cuenta)
     * Comentario: devuelve el nombre de un banco dado el IBAN de una cuenta
     * Precondiciones: por referencia se pasa un string
     * Entrada: String iban_cuenta
     * Salida: String nombre
     * Entrada/Salida:
     * Postcondiciones: Asociado al nombre se devuelve un String
     * */
    public String obtenerNombreBancoComercialPorIBAN(String iban_cuenta){

        return obtenerNombrePorBIC(iban_cuenta.substring(3,14));
    }
    
    /*
     * INTERFAZ
     * Signatura: public String obtenerNombrePorBIC(String BIC)
     * Comentario: devuelve el nombre de un banco dando su BIC
     * Precondiciones: por referencia se pasa un string
     * Entrada: String BIC
     * Salida: String nombre
     * Entrada/Salida:
     * Postcondiciones: Asociado al nombre se devuelve un String
     * */
    public String obtenerNombrePorBIC(String bic){
        File clientesBancoCentral = new File("./Files/BancoCentral/Clientes_BancoCentral.txt");
        FileReader leer = null;
        BufferedReader br = null;
        String registro = " ";
        String campos[] = null;
        String nombre = " ";
        try{
            leer = new FileReader(clientesBancoCentral);
            br = new BufferedReader(leer);
            while(br.ready()){
                registro = br.readLine();
                campos = registro.split(",");

                if ( campos[0].substring(3,14).equals(bic)){
                    nombre = campos[1];
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }


        return nombre;
    }
    
    /* INTERFAZ
     * Comentario: A partir de un IBAN, obtiene el BIC del banco central al que pertenece el banco que gestiona la cuenta
     * Prototipo: public String obtenerBICporIBAN(String IBAN)
     * Entrada: Un String con el IBAN
     * Precondiciones: No hay
     * Salida: Un string con el BIC del banco central al que pertenece el banco que gestiona la cuenta
     * Postcondiciones: Asociado al nombre devuelve un strnig con el BIC del banco central al que pertenece el banco que gestiona la cuenta
     */
    public String obtenerBICporIBAN(String IBAN)
    {
    	return IBAN.substring(3, 14);
    }


    /*
     * INTERFAZ
     * Signatura: public String obtenerBICporNombre(String nombre_banco)
     * Comentario: devuelve el BIC de un banco dando su nombre
     * Precondiciones: por referencia se pasa un string
     * Entrada: String nombre
     * Salida: String BIC
     * Entrada/Salida:
     * Postcondiciones: Asociado al nombre se devuelve un String
     * */
    public String obtenerBICporNombre(String nombre_banco){
        File clientesBancoCentral = new File("./Files/BancoCentral/Clientes_BancoCentral.txt");
        FileReader leer = null;
        BufferedReader br = null;
        String registro = " ";
        String campos[] = null;
        String bic = " ";
        try{
            leer = new FileReader(clientesBancoCentral);
            br = new BufferedReader(leer);
            while(br.ready()){
                registro = br.readLine();
                campos = registro.split(",");

                if ( campos[1].equals(nombre_banco)){
                    bic = campos[0].substring(3,14);
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }


        return bic;
    }

    /* INTERFAZ
     * Comentario: Obtiene el numero de cuenta de un IBAN
     * Prototipo: public String obtenerNumCuentaPorIBAN(String IBAN)
     * Entrada: Un String con el IBAN del que se quiere obtener su numero de cuenta
     * Precondiciones: No hay
     * Salida: Un String con el numero de cuenta del IBAN especificado
     * Postcondiciones: Asociado al nombre devuelve un String con el numero de cuenta del IBAN especificado
     */
    public String obtenerNumCuentaPorIBAN(String IBAN)
    {
        return IBAN.substring(14, 21);
    }
}
