package gestion;
import clasesBasicas.TransferenciaImpl;
import utilidades.Utilidades;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import clasesBasicas.TransferenciaImpl;
import utilidades.MyObjectOutputStream;
import utilidades.Utilidades;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;


public class GestionBancoCentral
{
    /* INTERFAZ
     * Comentario: Escribe un registro nuevo en un fichero de movimientos determinado
     * Prototipo: public boolean escribirRegistroEnMovimientos(String registro, String rutaFichero)
     * Entrada: Un String con el registro a escribir, y otro String con la ruta del fichero donde se escribirá.
     * Precondiciones: No hay
     * Salida: Un boolean indicando si se ha escrito correctamente o no.
     * Postcondiciones: Asociado al nombre devuelve:
     * 					-> True si se ha escrito correctamente el registro en el fichero correspondiente
     * 					-> False si no se ha escrito correctamente.
     */
    public boolean escribirRegistroEnMovimientos(String registro, String rutaFichero)
    {
        boolean escrito = false;

        File fichero = new File(rutaFichero);
        FileWriter fw = null;
        BufferedWriter bw = null;

        try
        {
            fw = new FileWriter(fichero, true);
            bw = new BufferedWriter(fw);

            bw.write(registro);
            escrito = true;
            bw.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        return escrito;
    }

  /* INTERFAZ
     * Signatura: public void modificarSaldoEnFicheroCuentas(String nombre_banco,String iban_cuenta, boolean sumaOresta,double cantidad)
     * Comentario: Este método se encarga de modificar en el fichero de Cuentas, el registro del saldo total (campo 2).
     * Precondiciones: Se pasa por referencia el ID de la cuenta a modificar y por valor la cantidad a aÃ±adir o substraer. Se pasa boolean que es true si aÃ±ade la cantidad o false si la resta
     * Entrada: String nombre_banco, String ID_Cuenta, boolean sumaOresta, double cantidad
     * Salida:
     * Entrada/Salida:
     * Postcondiciones: Se modifica el fichero de Cuentas y se actualiza el saldo pertinente.
     * */
    public boolean modificarSaldoEnFicheroCuentas(String IBAN, boolean sumaOresta,double cantidad)
    {
        String nombreBanco = obtenerNombreBancoComercialPorIBAN(IBAN);

        File ficheroCuentas = new File ("./Files/BancoCentral/Cuentas_BancoCentral_Movimientos.txt");
        FileReader leer = null;
        BufferedReader br = null;
        FileWriter fw = null;
        BufferedWriter bw = null;
        String campos[] = null;
        List<String> registros = new ArrayList<String>();
        String registro = " ";
        boolean saldoModificado = false;
        boolean añadidoEnMovimientos = false;

        //Escribe el registro en el fichero de movimientos
        if(sumaOresta)
        {
            registro = IBAN + ",+" + cantidad;
            añadidoEnMovimientos = escribirRegistroEnMovimientos(registro + "\n",ficheroCuentas.getPath());
        }
        else
        {
            registro = IBAN + ",-" + cantidad;
            añadidoEnMovimientos = escribirRegistroEnMovimientos(registro + "\n",ficheroCuentas.getPath());
        }

        //Si se ha añadido en el fichero de movimientos, ahora sincronizar ambos ficheros
        if(añadidoEnMovimientos)
        {
            actualizarFichero("./Files/BancoCentral/Cuentas_BancoCentral", 0);
            saldoModificado = true;
        }

        //Limpiar el fichero de movimientos

        //Pasar el contenido del fichero maestro actualizado al maestro antiguo.

        return saldoModificado;
    }


	/* INTERFAZ
     * Signatura: public void insertarMovimientoEnFicheroMovimientos(String nombre_banco,String ID_Cuenta,boolean isIngresoOrRetirada, String concepto, double cantidad,int dia, int mes, int anyo)
     * Comentario: Este método se encarga de modificar en el fichero de movimientos de la cuenta, aÃ±ade un nuevo movimiento.
     * Precondiciones: Se pasa por referencia el ID de la cuenta y del banco a modificar y por valor la cantidad de dinero a mover. Se pasa
     *                  un boolean que es true si el movimiento es un ingreso o false si es una retirada de dinero. Tambien se pasa la fecha como tres valores enteros (se supone vÃ¡lida)
     * Entrada: (String nombre_banco,String ID_Cuenta,boolean isIngresoOrRetirada, double cantidad,int dia, int mes, int anyo)
     * Salida:
     * Entrada/Salida:
     * Postcondiciones: Se modifica el fichero de movimientos de cuentas, aÃ±adiendo un movimiento nuevo.
     * */
	public boolean insertarMovimientoEnFicheroMovimientos(String IBAN,boolean isIngresoOrRetirada, String concepto, double cantidad, GregorianCalendar fecha)
	{
		File ficheroCuentas = new File ("./Files/BancoCentral/TransferenciasCuentas/Transferencias_"+IBAN+".dat");
        TransferenciaImpl trans = new TransferenciaImpl(IBAN,isIngresoOrRetirada,concepto,cantidad,fecha);
        boolean movimientoInsertado = false;
        MyObjectOutputStream oos = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setCalendar(fecha);
        String fechaformateada = sdf.format(fecha.getTime());
        try 
        {
            oos = new MyObjectOutputStream(new FileOutputStream(ficheroCuentas,true));
            oos.writeObject(trans);
            movimientoInsertado = true;
            oos.close();
        }
        catch (IOException e)
        {
        	e.printStackTrace();
        }
        
        return movimientoInsertado;
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
		File ficheroCuentas = new File("./Files/BancoCentral/Cuentas_BancoCentral_Movimientos.txt");
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
	 * Comentario: Comprueba si existe un cliente(BIC) registrado en el banco central
	 * Prototipo: public boolean BICRegistrado(String BIC)
	 * Entrada: un String con el BIC del clente a comprobar
	 * Precondiciones: No hay
	 * Salida: un boolean indicando si el BIC esta registrado ya o no
	 * Postcondiciones: Asociado al nombre devuelve true si el BIC está ya registrado en el banco o false de lo contrario.
	 */
	public boolean BICRegistrado(String BIC)
	{
		boolean registrado = false;
		
		File ficheroClientes = new File ("./Files/BancoCentral/Clientes_BancoCentral_Maestro.txt");
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
	 * Postcondiciones: Asociado al nombre devuelve true si el IBAN está ya registrado en el banco o false de lo contrario.
	 */
	public boolean IBANRegistrado(String IBAN)
	{
		boolean registrado = false;
		//System.out.println("IBANRegistrado en resguardo");
		File ficheroCuentas = new File ("./Files/BancoCentral/Cuentas_BancoCentral_Maestro.txt");
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
     * Signatura: public ArrayList<String> buscarMovimientosPorFecha(String iban_cuenta,int dia_buscado, int mes_buscado, int anyo_buscado)
     * Comentario: busca los movimientos que se hicieron en una cuenta en la fecha dada
     * Precondiciones: Se pasa un iban y tres int
     * Entrada: String iban,int dia_buscado, int mes_buscado, int anyo_buscado
     * Salida: arraylist de cadenas con el / los movimientos requeridos
     * Entrada/Salida:
     * Postcondiciones: asociado al nombre devuelve un arraylist
     * */
    public List<TransferenciaImpl> buscarMovimientosPorFecha(String IBAN, int dia,int mes,int anyo){
    	File file_movimientos = new File("./Files/BancoCentral/TransferenciasCuentas/Transferencias_"+IBAN+".dat");
        List<TransferenciaImpl> registros_buscados = new ArrayList<TransferenciaImpl>();
        TransferenciaImpl registro = null;
        ObjectInputStream leer = null;
        boolean cont = true;
        try{
            leer = new ObjectInputStream(new FileInputStream(file_movimientos));
            while (cont){
                registro = (TransferenciaImpl) leer.readObject();
                if (registro.getFecha().get(Calendar.YEAR) ==  anyo && registro.getFecha().get(Calendar.MONTH) == mes && registro.getFecha().get(Calendar.DAY_OF_MONTH) == dia ){
                    registros_buscados.add(registro);
                }
            }
            leer.close();
        }catch (IOException e){
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }

        return registros_buscados;
    }

   /*
     * INTERFAZ
     * Signatura: public boolean isCuentaBorrada(String iban)
     * Comentario: Comprueba si el iban pertenece a una cuenta del fichero CuentasBorradas
     * Precondiciones: Se pasa un iban
     * Entrada: String iban
     * Salida: boolean
     * Entrada/Salida:
     * Postcondiciones: asociado al nombre devuelve true si el iban corresponde a una cuenta del fichero CuentasBorradas y false si no
     * */
    //TODO Este metodo pierde un poco de sentido, aunque se podria hacer que comprobara si hay un registro con el mismo IBAN que tenga un * (de hecho si se hace este metodo, se podria llamar en el metodo de sincronizacion cuando vaya a comprobar si es una cuenta a borrar)
    public boolean isCuentaBorrada(String iban){
        File f_cuentasBorradas = new File("./Files/BancoCentral/CuentasBorradas_BancoCentral_Movimientos.txt");
        FileReader fr = null;
        BufferedReader br = null;
        boolean isBorrada = false;

        try{
            fr = new FileReader(f_cuentasBorradas);
            br = new BufferedReader(fr);

            while (br.ready()){
                if(br.readLine().equals(iban)){
                    isBorrada = true;
                }
            }
            br.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        return isBorrada;
    }
   /*
     * INTERFAZ
     * Signatura: public void marcarCuentaComoBorrada(String iban_cuenta)
     * Comentario: Escribe en el fichero CuentasBorradas la cuenta indicada
     * Precondiciones: Se pasa un iban
     * Entrada: String iban
     * Salida:
     * Entrada/Salida:
     * Postcondiciones: modifica el fichero de cuentas borradas
     * */
    
    //TODO Hay que rehacerlo, que escriba un registro en el fichero movimientos con un asterisco
    public boolean marcarCuentaComoBorrada(String iban_cuenta){
        File f_cuentasBorradas = new File("./Files/BancoCentral/CuentasBorradas_BancoCentral_Movimientos.txt");
        FileWriter fw = null;
        BufferedWriter bw = null;
        boolean borrada = false;
        
        try{
            fw = new FileWriter(f_cuentasBorradas,true);
            bw = new BufferedWriter(fw);

            bw.write(iban_cuenta);
            bw.newLine();
            borrada = true;

            bw.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        
        return borrada;
    }

  /*
     * INTERFAZ
     * Signatura: public void eliminarCuentasBorradasDefinitivamente(String bic)
     * Comentario: Elimina definitivamente el rastro de las cuentas marcadas como borradas en el fichero CuentasBorradas.
     *              (borra la cuenta del fichero de cuentas, borra su historial de movimientos, borra al cliente ya que de momento cada cliente solo puede tener una cuenta, borra del fichero cuentas-clientes...)
     * Precondiciones: No hay
     * Entrada: String bic
     * Salida:
     * Entrada/Salida:
     * Postcondiciones: modifica varios ficheros
     * */
    //TODO hay que revisarlo al completo
    public void eliminarCuentasBorradasDefinitivamente() {
        File carpetaBanco = new File("./Files/BancoCentral");
        File cuentas = new File(carpetaBanco, "Cuentas_BancoCentral_Movimientos.txt");
        File clientes = new File(carpetaBanco, "Clientes_BancoCentral_Movimientos.txt");
        File clientes_cuentas = new File(carpetaBanco, "Clientes_Cuentas_BancoCentral_Movimientos.txt");
        File cuentasBorradas = new File(carpetaBanco, "CuentasBorradas_BancoCentral_Movimientos.txt");
        File carpetaMovimientos = new File(carpetaBanco, "TransferenciasCuentas");
        File archivoMovimientosCuenta[] = carpetaMovimientos.listFiles();
        List<String> cuentasABorrar = new ArrayList<String>();
        List<String> registrosMantenidos = new ArrayList<String>();
        FileReader fr = null;
        BufferedReader br = null;
        FileWriter fw = null;
        BufferedWriter bw = null;
        String registro = " ";
        String dniABorrar = " ";

        if(carpetaBanco.exists() && cuentas.exists() && clientes.exists() && clientes_cuentas.exists() && cuentasBorradas.exists() && carpetaMovimientos.exists()){
        try {
            fr = new FileReader(cuentasBorradas);
            br = new BufferedReader(fr);
            while (br.ready()) {
                registro = br.readLine();
                cuentasABorrar.add(registro);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < cuentasABorrar.size(); i++) {
            /*ComprobaciÃ³n y eliminaciÃ³n del fichero de Cuentas*/
            try {
                fr = new FileReader(cuentas);
                br = new BufferedReader(fr);
                while (br.ready()) {
                    registro = br.readLine();

                    if (!cuentasABorrar.get(i).equals(registro.split(",")[0])) {
                        registrosMantenidos.add(registro);
                    }
                }
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fw = new FileWriter(cuentas);
                bw = new BufferedWriter(fw);
                for (String element : registrosMantenidos) {
                    bw.write(element);
                    bw.newLine();
                }
                bw.close();
                registrosMantenidos.clear();
            } catch (IOException e) {
                e.printStackTrace();
            }

            /*ComprobaciÃ³n y eliminaciÃ³n del fichero de Clientes_Cuentas_ */
            try {
                fr = new FileReader(clientes_cuentas);
                br = new BufferedReader(fr);
                while (br.ready()) {
                    registro = br.readLine();

                    if (!this.obtenerNombreBancoComercialPorIBAN(cuentasABorrar.get(i)).equals(registro.split(",")[0])) {
                        registrosMantenidos.add(registro);
                    } else {
                        dniABorrar = registro.split(",")[0];
                    }
                }
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fw = new FileWriter(clientes_cuentas);
                bw = new BufferedWriter(fw);
                for (String element : registrosMantenidos) {
                    bw.write(element);
                    bw.newLine();
                }
                bw.close();
                registrosMantenidos.clear();
            } catch (IOException e) {
                e.printStackTrace();
            }

            /*ComprobaciÃ³n y eliminaciÃ³n del fichero de Clientes */
            try {
                fr = new FileReader(clientes);
                br = new BufferedReader(fr);
                while (br.ready()) {
                    registro = br.readLine();

                    if (!dniABorrar.equals(registro.split(",")[1])) {
                        registrosMantenidos.add(registro);
                    }
                }
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fw = new FileWriter(clientes);
                bw = new BufferedWriter(fw);
                for (String element : registrosMantenidos) {
                    bw.write(element);
                    bw.newLine();
                }
                bw.close();
                registrosMantenidos.clear();
            } catch (IOException e) {
                e.printStackTrace();
            }

            /*Borrar los ficheros de movimientos de las cuentas borradas*/
            for (int j = 0; j < archivoMovimientosCuenta.length; j++) {
                if (archivoMovimientosCuenta[j].getName().split("_")[1].equals(cuentasABorrar.get(i)+".txt")) {
                    archivoMovimientosCuenta[j].delete();
                }
            }

        }

        /*Eliminar el contenido del fichero de cuentasBorradas*/
        try {
            fw = new FileWriter(cuentasBorradas);
            bw = new BufferedWriter(fw);
            bw.write("");
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        }//cierra el if
    }
    /*
     * INTERFAZ
     * Signatura: public List<String> ultimosDiezMovimientos(String iban_cuenta)
     * Comentario: devuelve los ultimos diez movimientos de la cuenta
     * Precondiciones: Se pasa un iban
     * Entrada: String iban
     * Salida: una lista de String
     * Entrada/Salida:
     * Postcondiciones: asociado al nombre devuelve una lista de String
     * */
    public List<TransferenciaImpl> ultimosDiezMovimientos(String iban_cuenta){
    	
        File f_cuentas = new File("./Files/BancoCentral/TransferenciasCuentas/Transferencias_" + iban_cuenta + ".dat");
        ObjectInputStream leer = null;
        List<TransferenciaImpl> registros = new ArrayList<TransferenciaImpl>();
        TransferenciaImpl registro = null;
        int lineas=0;
        boolean cont = true;


            if (f_cuentas.exists()){
                try {
                    leer = new ObjectInputStream(new FileInputStream(f_cuentas));
                    while (cont && lineas < 10) {

                            registro = (TransferenciaImpl) leer.readObject();
                            registros.add(registro);
                            lineas++;

                    }
                    leer.close();
                }catch (EOFException e){

                } catch (IOException e) {
                    e.printStackTrace();
                }catch (ClassNotFoundException e){
                    e.printStackTrace();
                }
            }
            return registros;
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
        File clientesBancoCentral = new File("./Files/BancoCentral/Clientes_BancoCentral_Maestro.txt");
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
        File clientesBancoCentral = new File("./Files/BancoCentral/Clientes_BancoCentral_Maestro.txt");
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
     * Signatura: public void ingresarDinero(String nombre_banco, String ID_Cuenta,String concepto, double cantidad, int dia, int mes, int anyo)
     * Comentario: ingresa una cantidad dada de una cuenta
     * Precondiciones: Por valor se pasa una cantidad, por referencia la ID de una cuenta y el nombre del banco. Por valor se pasa dia, mes y aÃ±o
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
     * Comentario: Realiza un movimiento bancario, sacando una cantidad de la cuenta de origen e ingresÃ¡ndola en la cuenta destino.
     *              Llama a los mÃ©todos sacarDinero e ingresarDinero.
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
     * Precondiciones: Por valor se pasa una cantidad, por referencia la ID de una cuenta y el nombre del banco. Se pasa por valor dia mes y aÃ±o
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
        Utilidades utils = new Utilidades();

        utils.ordenarFicheroPorClave(ficheroMovimientos.getPath(), 0);

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

                    if(!registroMovimientos.contains("*"))
                    {
                        bwMaestroAct.write(registroMovimientos + "\n");
                    }

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

    public double obtenerSaldoPorIBAN(String IBAN)
    {
        String cuenta = datosCuenta(IBAN);
        double saldo = 0;

        if(cuenta != null)
            saldo = Double.parseDouble(cuenta.split(",")[1]);

        return saldo;
    }

    
}