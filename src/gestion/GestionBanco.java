package gestion;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.GregorianCalendar;
import java.util.List;

public abstract class GestionBanco {

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
    
    public abstract boolean insertarMovimientoEnFicheroMovimientos(String IBAN,boolean isIngresoOrRetirada, String concepto, double cantidad, GregorianCalendar fecha);
	public abstract boolean modificarSaldoEnFicheroCuentas(String IBAN, boolean sumaOresta,double cantidad);
    public abstract String datosCuenta(String IBAN);
    public abstract List<String> buscarMovimientosPorFecha(String IBAN, int anyo);
    public abstract List<String> buscarMovimientosPorFecha(String IBAN, int mes, int anyo);
    public abstract List<String> buscarMovimientosPorFecha(String IBAN, int dia, int mes, int anyo);
    public abstract boolean marcarCuentaComoBorrada(String iban_cuenta);
    public abstract boolean isCuentaBorrada(String iban);
	
    /* INTERFAZ
     * Signatura: public void ingresarDinero(String nombre_banco, String ID_Cuenta,String concepto, double cantidad, int dia, int mes, int anyo)
     * Comentario: ingresa una cantidad dada de una cuenta
     * Precondiciones: Por valor se pasa una cantidad, por referencia la ID de una cuenta y el nombre del banco. Por valor se pasa dia, mes y año
     * Entrada: String nombre_banco, String ID_Cuenta, double cantidad, int dia, int mes, int anyo
     * Salida: Un boolean indiciando si se ha ingresado el dinero con exito o no
     * Entrada/Salida:
     * Postcondiciones: Asociado al nombre devuelve:
     * 				-> true si se ha ingresado el dinero con exito, insertando el movimiento en el fichero de movimientos y modificando el saldo en el fichero de cuentas
     * 				-> false si no se ha podido realizar con exito la operacion
     * */
	public boolean ingresarDinero(String IBAN,String concepto, double cantidad, GregorianCalendar fecha){
		
		boolean ingresado = false;
        
		boolean insertado = insertarMovimientoEnFicheroMovimientos(IBAN, true, concepto, cantidad, fecha);
        boolean modificado = modificarSaldoEnFicheroCuentas(IBAN, true, cantidad);
        
        if(insertado && modificado)
        	ingresado = true;
        
        return ingresado;

	}
	
	/* INTERFAZ
     * Signatura: public void realizarMovimiento(String nombre_banco_origen,String cuenta_origen,String nombre_banco_destino, String cuenta_destino, String concepto,double cantidad, int dia, int mes, int anyo)
     * Comentario: Realiza un movimiento bancario, sacando una cantidad de la cuenta de origen e ingresándola en la cuenta destino.
     *              Llama a los métodos sacarDinero e ingresarDinero.
     * Precondiciones: Por referencia se pasan las ID de las cuentas y los nombres de los bancos de origen y destino, por valor se pasa la cantidad y dia mes y anyo. Tambien se pasa por referencia el concepto
     * Entrada: (String nombre_banco_origen,String cuenta_origen,String nombre_banco_destino, String cuenta_destino, String concepto,double cantidad, int dia, int mes, int anyo)
     * Salida: Un boolean indicando si se ha realizado bien el movimiento o no
     * Entrada/Salida:
     * Postcondiciones: Asociado al nombre devuelve:
     * 					-> true si se ha realizado bien el movimiento
     * 					-> false si no se ha realizado bien el movimiento
     * */
	public boolean realizarMovimiento(String IBANOrigen,String IBANDestino, String concepto,double cantidad, GregorianCalendar fecha){
        
		boolean movimientoRealizado = false;
		
		boolean sacado = sacarDinero(IBANOrigen, concepto, cantidad, fecha);
        boolean ingresado = ingresarDinero(IBANDestino, concepto, cantidad, fecha);
        
        if(sacado && ingresado)
        	movimientoRealizado = true;
        
        return movimientoRealizado;
    }
	
	/*INTERFAZ
     * Signatura: public void sacarDinero(String nombre_banco, String ID_Cuenta, String concepto,double cantidad, int dia, int mes, int anyo)
     * Comentario: saca una cantidad dada de una cuenta
     * Precondiciones: Por valor se pasa una cantidad, por referencia la ID de una cuenta y el nombre del banco. Se pasa por valor dia mes y año
     * Entrada: String nombre_banco, String ID_Cuenta, double cantidad, int dia, int mes, int anyo
     * Salida: Un boolean indicando si se ha sacado bien el dinero
     * Entrada/Salida:
     * Postcondiciones: Asociado al nombre devuelve:
     * 					-> true si se ha sacado bien el dinero de la cuenta, insertando el movimiento en el fichero de movimientos y modificando el saldo en el fichero de cuentas
     * 					-> false si no se ha podido realizar bien la operacion.
     * */
	public boolean sacarDinero(String IBAN,String concepto, double cantidad, GregorianCalendar fecha){
		
		boolean dineroSacado = false;
		
        boolean movimientoInsertado = insertarMovimientoEnFicheroMovimientos(IBAN, false, concepto, cantidad, fecha);
        boolean modificado = modificarSaldoEnFicheroCuentas(IBAN, false, cantidad);

        if (movimientoInsertado && modificado)
        {
        	dineroSacado = true;
        }
        
        return dineroSacado;
    }
	
	/* INTERFAZ
	 * Comentario: Actualiza un fichero maestro determinado, mirando los registros de su fichero de movimiento correspondiente.
	 * Prototipo: public boolean actualizarFichero(String fichero)
	 * Entrada: Un string con la ruta del fichero
	 * Precondiciones: 
	 * Salida: 
	 * Postcondiciones: 
	 */
	public boolean actualizarFichero(String fichero, int posicionCampoClave)
	{
		boolean actualizado = false;
		File ficheroMaestro = new File(fichero + "_Maestro.txt");
		File ficheroMovimientos = new File(fichero + "_Movimientos.txt");
		File ficheroMaestroAct = new File(fichero + "_Maestro_act.txt");
		FileReader frMaestro = null;
		FileReader frMovimientos = null;
		FileWriter fwMaestroAct = null;
		BufferedReader brMaestro = null;
		BufferedReader brMovimientos = null;
		BufferedWriter bwMaestroAct = null;
		
		try
		{
			frMaestro = new FileReader(ficheroMaestro);
			frMovimientos = new FileReader(ficheroMovimientos);
			fwMaestroAct = new FileWriter(ficheroMaestroAct);
			
			brMaestro = new BufferedReader(frMaestro);
			brMovimientos = new BufferedReader(frMovimientos);
			bwMaestroAct = new BufferedWriter(fwMaestroAct);
			
			//Leer primeros registros de ficheroMovimientos y ficheroMaestro
			String registroMovimientos = brMovimientos.readLine();
			String registroMaestro = brMaestro.readLine();
			
			String campoClaveMovimientos = registroMovimientos.split(",")[posicionCampoClave];
			String campoClaveMaestro = registroMaestro.split(",")[posicionCampoClave];
			
			//Mientras no sea fin de fichero en ninguno de los dos
			while(registroMovimientos != null && registroMaestro != null)
			{	
				if(campoClaveMovimientos.compareTo(campoClaveMaestro) == 0)
				{
					//Modificacion
					bwMaestroAct.write(registroMovimientos + "\n");
					
					//Se mueven los dos punteros
					registroMovimientos = brMovimientos.readLine();
					if(registroMovimientos != null)
						campoClaveMovimientos = registroMovimientos.split(",")[posicionCampoClave];
					
					registroMaestro = brMaestro.readLine();
					if(registroMaestro != null)
						campoClaveMaestro = registroMaestro.split(",")[posicionCampoClave];
				}
				else if(campoClaveMovimientos.compareTo(campoClaveMaestro) > 0)
				{
					
					while(campoClaveMovimientos.compareTo(campoClaveMaestro) > 0 && registroMaestro != null)
					{
						//Escribir registro de maestro en maestroAct
						bwMaestroAct.write(registroMaestro + "\n");
						
						//leer registro de maestro
						
						registroMaestro = brMaestro.readLine();
						
						if(registroMaestro != null)
						{
							campoClaveMaestro = registroMaestro.split(",")[posicionCampoClave];
						}
					}
				}
				else if(campoClaveMovimientos.compareTo(campoClaveMaestro) < 0)
				{
					//Es un alta o un error
					bwMaestroAct.write(registroMovimientos + "\n");
					
					//Leer registro de movimiento
					registroMovimientos = brMovimientos.readLine();
					
					if(registroMovimientos != null)
						campoClaveMovimientos = registroMovimientos.split(",")[posicionCampoClave];
				}
			}
			//Se ha acabado el fichero de movimientos y aun quedan registros en el maestro
			while(registroMaestro != null)
			{
				//Escribir registro de maestro en maestroAct
				bwMaestroAct.write(registroMaestro + "\n");
				
				//leer registro de maestro
				registroMaestro = brMaestro.readLine();
				
				if(registroMaestro != null)
					campoClaveMaestro = registroMaestro.split(",")[posicionCampoClave];
			}
			//Se ha acabado el fichero maestro y aun quedan registros en el de movimientos
			while(registroMovimientos != null) 
			{
				//Escribir registro de movimientos en maestroAct
				bwMaestroAct.write(registroMovimientos + "\n");
				
				//leer registro de movimientos
				registroMovimientos = brMovimientos.readLine();
				
				if(registroMovimientos != null)
					campoClaveMovimientos = registroMovimientos.split(",")[posicionCampoClave];
			}
			
			//Cerrar archivos
			brMaestro.close();
			brMovimientos.close();
			bwMaestroAct.close();
			
			ficheroMaestro.delete();
			ficheroMaestroAct.renameTo(ficheroMaestro);
			
			ficheroMovimientos.delete();
			ficheroMovimientos.createNewFile();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		return actualizado;
	}

	
}
