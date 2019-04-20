package gestion;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class GestionBancoCentral extends GestionBanco
{
	/* INTERFAZ
     * Signatura: public void modificarSaldoEnFicheroCuentas(String nombre_banco,String iban_cuenta, boolean sumaOresta,double cantidad)
     * Comentario: Este método se encarga de modificar en el fichero de Cuentas, el registro del saldo total (campo 2).
     * Precondiciones: Se pasa por referencia el ID de la cuenta a modificar y por valor la cantidad a añadir o substraer. Se pasa boolean que es true si añade la cantidad o false si la resta
     * Entrada: String nombre_banco, String ID_Cuenta, boolean sumaOresta, double cantidad
     * Salida:
     * Entrada/Salida:
     * Postcondiciones: Se modifica el fichero de Cuentas y se actualiza el saldo pertinente.
     * */
	public boolean modificarSaldoEnFicheroCuentas(String IBAN, boolean sumaOresta,double cantidad)
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
        boolean saldoModificado = false;

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
                    {
                        registro = registro.replace(campos[1], Double.toString(cantidad+Double.parseDouble(campos[1])));
                   		saldoModificado = true;
                    }
                    else
                    {
                        registro = registro.replace(campos[1], Double.toString(Double.parseDouble(campos[1])-cantidad));
                        saldoModificado = true;
                    }
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
        
        return saldoModificado;
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
	public boolean insertarMovimientoEnFicheroMovimientos(String IBAN,boolean isIngresoOrRetirada, String concepto, double cantidad, GregorianCalendar fecha)
	{
		File ficheroCuentas = new File ("./Files/BancoCentral/MovimientosCuentas/Movimientos_"+IBAN+".txt");
        FileWriter fw = null;
        BufferedWriter bw = null;
        String signo = "RETIRADA,-";
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setCalendar(fecha);
        String fechaformateada = sdf.format(fecha.getTime());
        boolean movimientoInsertado = false;
        
        try 
        {
            fw = new FileWriter(ficheroCuentas,true);
            bw = new BufferedWriter(fw);
            if(isIngresoOrRetirada)
            	
                signo = "INGRESO,+";
            
            bw.write(concepto+","+signo+Double.toString(cantidad)+","+fechaformateada);
            bw.newLine();
            movimientoInsertado = true;
            bw.close();
            
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
     * Signatura: public ArrayList<String> buscarMovimientosPorFecha(String iban_cuenta, int anyo_buscado)
     * Comentario: busca los movimientos que se hicieron en una cuenta en la fecha dada
     * Precondiciones: Se pasa un iban y un int
     * Entrada: String iban, int anyo_buscado
     * Salida: arraylist de cadenas con el / los movimientos requeridos
     * Entrada/Salida:
     * Postcondiciones: asociado al nombre devuelve un arraylist
     * */
    public List<String> buscarMovimientosPorFecha(String IBAN, int anyo){
        File file_movimientos = new File("./Files/BancoCentral/MovimientosCuentas/Movimientos_"+IBAN+".txt");
        FileReader fr = null;
        BufferedReader br = null;
        GregorianCalendar fecha = new GregorianCalendar();
        List<String> registros_buscados = new ArrayList<String>();
        String registro= " ";


        try{
            fr = new FileReader(file_movimientos);
            br = new BufferedReader(fr);

            while (br.ready()){
                registro = br.readLine();
                fecha.set(Calendar.YEAR, Integer.parseInt(registro.split(",")[3].split("/")[2]));
                fecha.set(Calendar.MONTH, Integer.parseInt(registro.split(",")[3].split("/")[1]));
                fecha.set(Calendar.DAY_OF_MONTH, Integer.parseInt(registro.split(",")[3].split("/")[0]));
                if (fecha.get(Calendar.YEAR) ==  anyo ){
                    registros_buscados.add(registro);
                }
            }
            br.close();
        }catch (IOException e){
            e.printStackTrace();
        }

        return registros_buscados;
    }
    
    /*
     * INTERFAZ
     * Signatura: public ArrayList<String> buscarMovimientosPorFecha(String iban_cuenta, int mes_buscado, int anyo_buscado)
     * Comentario: busca los movimientos que se hicieron en una cuenta en la fecha dada
     * Precondiciones: Se pasa un iban y dos int
     * Entrada: String iban, int mes_buscado, int anyo_buscado
     * Salida: arraylist de cadenas con el / los movimientos requeridos
     * Entrada/Salida:
     * Postcondiciones: asociado al nombre devuelve un arraylist
     * */
    public List<String> buscarMovimientosPorFecha(String IBAN, int mes, int anyo){
        File file_movimientos = new File("./Files/BancoCentral/MovimientosCuentas/Movimientos_"+IBAN+".txt");
        FileReader fr = null;
        BufferedReader br = null;
        GregorianCalendar fecha = new GregorianCalendar();
        List<String> registros_buscados = new ArrayList<String>();
        String registro= " ";


        try{
            fr = new FileReader(file_movimientos);
            br = new BufferedReader(fr);

            while (br.ready()){
                registro = br.readLine();
                fecha.set(Calendar.YEAR, Integer.parseInt(registro.split(",")[3].split("/")[2]));
                fecha.set(Calendar.MONTH, Integer.parseInt(registro.split(",")[3].split("/")[1]));
                fecha.set(Calendar.DAY_OF_MONTH, Integer.parseInt(registro.split(",")[3].split("/")[0]));
                if (fecha.get(Calendar.YEAR) ==  anyo && fecha.get(Calendar.MONTH) == mes ){
                    registros_buscados.add(registro);
                }
            }
            br.close();
        }catch (IOException e){
            e.printStackTrace();
        }

        return registros_buscados;
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
    public List<String> buscarMovimientosPorFecha(String IBAN, int dia,int mes,int anyo){
    	File file_movimientos = new File("./Files/BancoCentral/MovimientosCuentas/Movimientos_"+IBAN+".txt");
        FileReader fr = null;
        BufferedReader br = null;
        GregorianCalendar fecha = new GregorianCalendar();
        List<String> registros_buscados = new ArrayList<String>();
        String registro= " ";


        try{
            fr = new FileReader(file_movimientos);
            br = new BufferedReader(fr);

            while (br.ready()){
                registro = br.readLine();
                fecha.set(Calendar.YEAR, Integer.parseInt(registro.split(",")[3].split("/")[2]));
                fecha.set(Calendar.MONTH, Integer.parseInt(registro.split(",")[3].split("/")[1]));
                fecha.set(Calendar.DAY_OF_MONTH, Integer.parseInt(registro.split(",")[3].split("/")[0]));
                if (fecha.get(Calendar.YEAR) ==  anyo && fecha.get(Calendar.MONTH) == mes && fecha.get(Calendar.DAY_OF_MONTH) == dia ){
                    registros_buscados.add(registro);
                }
            }
            br.close();
        }catch (IOException e){
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
    public boolean isCuentaBorrada(String iban){
        File f_cuentasBorradas = new File("./Files/BancoCentral/CuentasBorradas_BancoCentral.txt");
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
    public boolean marcarCuentaComoBorrada(String iban_cuenta){
        File f_cuentasBorradas = new File("./Files/BancoCentral/CuentasBorradas_BancoCentral.txt");
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
    public void eliminarCuentasBorradasDefinitivamente() {
        File carpetaBanco = new File("./Files/BancoCentral");
        File cuentas = new File(carpetaBanco, "Cuentas_BancoCentral.txt");
        File clientes = new File(carpetaBanco, "Clientes_BancoCentral.txt");
        File clientes_cuentas = new File(carpetaBanco, "Clientes_Cuentas_BancoCentral.txt");
        File cuentasBorradas = new File(carpetaBanco, "CuentasBorradas_BancoCentral.txt");
        File carpetaMovimientos = new File(carpetaBanco, "MovimientosCuentas");
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
            /*Comprobación y eliminación del fichero de Cuentas*/
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

            /*Comprobación y eliminación del fichero de Clientes_Cuentas_ */
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

            /*Comprobación y eliminación del fichero de Clientes */
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
    public List<String> ultimosDiezMovimientos(String iban_cuenta){
    	
        File f_cuentas = new File("./Files/BancoCentral/MovimientosCuentas/Movimientos_" + iban_cuenta + ".txt");
        FileReader fr = null;
        BufferedReader br = null;
        List<String> registros = new ArrayList<String>();
        String registro = " ";
        int lineas=0;


            if (f_cuentas.exists()){
                try {
                    fr = new FileReader(f_cuentas);
                    br = new BufferedReader(fr);
                    while (br.ready() && lineas < 10) {
                        registro = br.readLine();
                        registros.add(registro);
                        lineas++;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return registros;
    }
    
}
