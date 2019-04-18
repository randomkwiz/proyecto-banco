/*
*  Voy a codificar aquÃ­ los mÃ©todos que he puesto en "ResguardoGestionBancoComercial"
*   a pesar de que creo que irÃ­an en la clase bÃ¡sica "BancoComercial" pero por tenerlos separados.
* Si luego vemos que son de BancoComercial, los cambio a allÃ­.
* */
package gestion;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import clasesBasicas.ClienteImpl;
import clasesBasicas.CuentaImpl;

public class GestionBancoComercial {

    /*
     * INTERFAZ
     * Signatura: public void imprimirUltimosDiezMovimientos(String iban_cuenta)
     * Comentario:imprime en pantalla los ultimos diez movimientos de la cuenta
     * Precondiciones: Se pasa un iban
     * Entrada: String iban
     * Salida: nada, imprime en pantalla
     * Entrada/Salida:
     * Postcondiciones: imprime en pantalla
     * */
    public void imprimirUltimosDiezMovimientos(String iban_cuenta){
        String nombre_banco = obtenerNombreBancoComercialPorIBAN(iban_cuenta);
        File f_cuentas = new File("./Files/BancosComerciales/" + nombre_banco +"/Movimientos/Movimientos_Cuenta_" + iban_cuenta + ".txt");
        FileReader fr = null;
        BufferedReader br = null;
        int lineas=0;


            if (f_cuentas.exists()){
                try {
                    fr = new FileReader(f_cuentas);
                    br = new BufferedReader(fr);
                    while (br.ready() && lineas < 10) {
                        System.out.println(br.readLine());
                        lineas++;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

    }

    /*
     * INTERFAZ
     * Signatura: public boolean isDNIvalido(String nombre_banco,String dni_cliente)
     * Comentario: Dado un DNI, devuelve true si este pertenece a un cliente existente del banco y false si no
     * Precondiciones: Se pasa el nombre del banco y el dni del cliente
     * Entrada: String nombre_banco y String dni_cliente
     * Salida: boolean
     * Entrada/Salida:
     * Postcondiciones: asociado al nombre se devuelve un boolean que devuelve true si este pertenece a un cliente existente del banco y false si no
     * */
    public boolean isDNIvalido(String nombre_banco,String dni_cliente){
        File f_clientes = new File("./Files/BancosComerciales/"+nombre_banco+"/Clientes_"+nombre_banco+".txt");
        FileReader fr = null;
        BufferedReader br = null;
        boolean isValido = false;
        if(f_clientes.exists()) {
            try {
                fr = new FileReader(f_clientes);
                br = new BufferedReader(fr);
                while (br.ready()) {
                    if (br.readLine().split(",")[1].equals(dni_cliente)) {
                        isValido = true;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return isValido;
    }

    /*
     * INTERFAZ
     * Signatura: public boolean isIBANvalido(String iban_cuenta)
     * Comentario: Dado un iban devuelve true si este existe o false si no
     * Precondiciones: Se pasa el iban de la cuenta
     * Entrada: String iban_cuenta
     * Salida: boolean
     * Entrada/Salida:
     * Postcondiciones: asociado al nombre se devuelve un boolean que devuelve true si existe y false si no
     * */
    public boolean isIBANvalido(String iban_cuenta){
        String nombre_banco = " ";
        File f_cuentas = null;
        FileReader fr = null;
        BufferedReader br = null;
        boolean isValido = false;

        if(iban_cuenta.length() >= 13) {
            nombre_banco = obtenerNombreBancoComercialPorIBAN(iban_cuenta);
            f_cuentas = new File("./Files/BancosComerciales/" + nombre_banco + "/Cuentas_" + nombre_banco + ".txt");
            if (f_cuentas.exists()){
                try {
                    fr = new FileReader(f_cuentas);
                    br = new BufferedReader(fr);
                    while (br.ready()) {
                        if (br.readLine().split(",")[0].equals(iban_cuenta)) {
                            isValido = true;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        }
        return isValido;
    }

    /*
     * INTERFAZ
     * Signatura: public boolean isPropietario(String dni_cliente, String iban_cuenta)
     * Comentario: Dado un iban y un dni de cliente, devuelve true si este iban pertenece a este cliente o false si no
     * Precondiciones: Se pasa el iban de la cuenta
     * Entrada: String dni_cliente, String iban_cuenta
     * Salida: boolean
     * Entrada/Salida:
     * Postcondiciones: asociado al nombre se devuelve true si este iban pertenece a este cliente o false si no
     * */
    public boolean isPropietario(String dni_cliente, String iban_cuenta){
        String nombre_banco = obtenerNombreBancoComercialPorIBAN(iban_cuenta);
        File f_clientes_cuentas = new File("./Files/BancosComerciales/"+nombre_banco+"/Clientes_Cuentas_"+nombre_banco+".txt");
        FileReader fr = null;
        BufferedReader br = null;
        boolean isProp = false;
        String registro = " ";

        if(f_clientes_cuentas.exists()) {
            try {
                fr = new FileReader(f_clientes_cuentas);
                br = new BufferedReader(fr);
                while (br.ready()) {
                    registro = br.readLine();
                    if (registro.split(",")[0].equals(dni_cliente) && registro.split(",")[1].equals(iban_cuenta)) {
                        isProp = true;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return isProp;
    }




    /*
     * INTERFAZ
     * Signatura: public String obtenerClientePorIBAN(String iban_cuenta)
     * Comentario: dado el iban de la cuenta, te devuelve el cliente al que pertenece la cuenta
     * Precondiciones: se pasa el iban de la cuenta
     * Entrada: String iban_cuenta
     * Salida: String que es el dni del cliente
     * Entrada/Salida:
     * Postcondiciones: Asociado al nombre se devuelve el DNI del cliente al que pertenece la cuenta
     * */
    public String obtenerClientePorIBAN(String iban_cuenta){
        String nombre_banco = obtenerNombreBancoComercialPorIBAN(iban_cuenta);
        File fichero_clientes_cuentas = new File("./Files/BancosComerciales/"+nombre_banco+"/Clientes_Cuentas_"+nombre_banco+".txt");
        FileReader fr = null;
        BufferedReader br = null;
        String registro = " ";
        String dni_cliente = " ";

        try{
            fr = new FileReader(fichero_clientes_cuentas);
            br = new BufferedReader(fr);
            while (br.ready()){
                registro = br.readLine();

                if (registro.split(",")[1].equals(iban_cuenta)){
                    dni_cliente = registro.split(",")[0];
                }
            }
            br.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        return dni_cliente;
    }
    /*
     * INTERFAZ
     * Signatura: public String obtenerIBANPorCliente(String nombre_banco, String dni_cliente)
     * Comentario: dado el nombre del banco y el dni del propietario de la cuenta, te devuelve el IBAN de la cuenta
     * Precondiciones: se pasan el nombre del banco y el dni del propietario
     * Entrada: String nombre_banco, String dni_cliente
     * Salida: String que es el IBAN de la cuenta
     * Entrada/Salida:
     * Postcondiciones: Asociado al nombre se devuelve el IBAN de la cuenta que pertenece a dicho cliente
     * */
    public String obtenerIBANPorCliente(String nombre_banco, String dni_cliente){
        File fichero_clientes_cuentas = new File("./Files/BancosComerciales/"+nombre_banco+"/Clientes_Cuentas_"+nombre_banco+".txt");
        FileReader fr = null;
        BufferedReader br = null;
        String registro = " ";
        String iban_cuenta = " ";

        try{
            fr = new FileReader(fichero_clientes_cuentas);
            br = new BufferedReader(fr);
            while (br.ready()){
                registro = br.readLine();

                if (registro.split(",")[0].equals(dni_cliente)){
                    iban_cuenta = registro.split(",")[1];
                }
            }
            br.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        return iban_cuenta;
    }



    /*
     * INTERFAZ
     * Signatura: public void sacarDinero(String ID_Cuenta, String concepto,double cantidad, GregorianCalendar fecha)
     * Comentario: saca una cantidad dada de una cuenta
     * Precondiciones: Por valor se pasa una cantidad, por referencia la ID de una cuenta(IBAN) y un objeto fecha
     * Entrada: String ID_Cuenta, double cantidad, GregorianCalendar fecha
     * Salida:
     * Entrada/Salida:
     * Postcondiciones: Se modificarÃ¡n los ficheros de Cuentas modificando el saldo y de movimientos, aÃ±adiendo el movimiento correspondiente.
     * */
    public void sacarDinero(String ID_Cuenta, String concepto, double cantidad, GregorianCalendar fecha){
        insertarMovimientoEnFicheroMovimientos(ID_Cuenta, false, concepto, cantidad, fecha);
        modificarSaldoEnFicheroCuentas(ID_Cuenta, false, cantidad);

    }

    /*
     * INTERFAZ
     * Signatura: public void ingresarDinero(String ID_Cuenta,String concepto, double cantidad, GregorianCalendar fecha)
     * Comentario: ingresa una cantidad dada de una cuenta
     * Precondiciones: Por valor se pasa una cantidad, por referencia la ID de una cuenta y objeto fecha
     * Entrada: String ID_Cuenta, double cantidad, GregorianCalendar fecha
     * Salida:
     * Entrada/Salida:
     * Postcondiciones: Se modificarÃ¡n los ficheros de Cuentas modificando el saldo y de movimientos, aÃ±adiendo el movimiento correspondiente.
     * */
    public void ingresarDinero(String ID_Cuenta,String concepto, double cantidad, GregorianCalendar fecha){
        insertarMovimientoEnFicheroMovimientos(ID_Cuenta, true, concepto, cantidad, fecha);
        modificarSaldoEnFicheroCuentas(ID_Cuenta, true, cantidad);

    }

    /*
     * INTERFAZ
     * Signatura: public void realizarMovimiento(String cuenta_origen,String cuenta_destino, String concepto,double cantidad, GregorianCalendar fecha)
     * Comentario: Realiza un movimiento bancario, sacando una cantidad de la cuenta de origen e ingresÃ¡ndola en la cuenta destino.
     *              Llama a los mÃ©todos sacarDinero e ingresarDinero.
     * Precondiciones: Por referencia se pasan las ID de las cuentas por valor se pasa la cantidad. Tambien se pasa por referencia el concepto y objeto fecha
     * Entrada: (String cuenta_origen,String cuenta_destino, String concepto,double cantidad, GregorianCalendar fecha)
     * Salida:
     * Entrada/Salida:
     * Postcondiciones: Se modificarÃ¡n los ficheros de cuentas y de movimientos correspondientes.
     * */
    public void realizarMovimiento(String cuenta_origen,String cuenta_destino, String concepto,double cantidad, GregorianCalendar fecha){
        sacarDinero(cuenta_origen, concepto, cantidad, fecha);
        ingresarDinero(cuenta_destino, concepto, cantidad, fecha);
    }


    /*
     * INTERFAZ
     * Signatura: public void ordenarMovimientosPorFecha(String iban)
     * Comentario: Ordena un fichero de movimientos de una cuenta en base a las fechas, deja primero los mÃ¡s recientes.
     * Precondiciones: Se le pasa por referencia el IBAN de la cuenta
     * Entrada: String IBAN
     * Salida:
     * Entrada/Salida:
     * Postcondiciones: Se modifica el fichero y se deja ordenado de forma descendente
     * */
    public void ordenarMovimientosPorFecha(String iban){
        String nombre_banco = obtenerNombreBancoComercialPorIBAN(iban);
        File ficheroMovimientosCuenta = new File ("./Files/BancosComerciales/"+nombre_banco+"/Movimientos/Movimientos_Cuenta_"+iban+".txt");
        File temp = new File ("./Files/BancosComerciales/"+nombre_banco+"/Movimientos/Movimientos_Cuenta_"+iban+"_temp.txt");
        FileReader fr = null;
        FileWriter fw = null;
        BufferedReader br = null;
        BufferedWriter bw = null;
        List<String> registros = new ArrayList<String>();    //arraylist - considerar cambiar a array
        String registro = " ";
        String aux=" "; //para el bubblesort de mÃ¡s abajo


        try{
            fr = new FileReader(ficheroMovimientosCuenta);
            br = new BufferedReader(fr);
            while(br.ready()){
                registro = br.readLine();
                registros.add(registro);

            }
            br.close();
        }catch (IOException e){
            e.printStackTrace();
        }

        //ordenamiento burbuja

        for (int i = 0; i < registros.size()-1;i++){
            for (int j = registros.size()-1; j>i; j--){

            /*SÃ‰ QUE ESTO NO SE DEBE PONER ASÃ� PERO NO SÃ‰ CÃ“MO PONERLO DE OTRA MANERA*/
                /*HAY QUE CAMBIARLO*/

                boolean anyoEsMasPequenyo = (Integer.parseInt(registros.get(j).split(",")[3].split("/")[2]) < Integer.parseInt(registros.get(j-1).split(",")[3].split("/")[2] )  ) ;
                boolean anyoEsIgual = (Integer.parseInt(registros.get(j).split(",")[3].split("/")[2]) == Integer.parseInt(registros.get(j-1).split(",")[3].split("/")[2] )  ) ;
                boolean mesEsMasPequenyo = (Integer.parseInt(registros.get(j).split(",")[3].split("/")[1]) < Integer.parseInt(registros.get(j-1).split(",")[3].split("/")[1] )  ) ;
                boolean mesEsIgual = (Integer.parseInt(registros.get(j).split(",")[3].split("/")[1]) == Integer.parseInt(registros.get(j-1).split(",")[3].split("/")[1] )  ) ;
                boolean diaEsMasPequenyo = (Integer.parseInt(registros.get(j).split(",")[3].split("/")[0]) < Integer.parseInt(registros.get(j-1).split(",")[3].split("/")[0] )  ) ;

                /*ADEMÃ�S AUNQUE NO LE GUSTE ASÃ� A ASUN, QUEDA MÃ�S LEGIBLE LA CONDICIÃ“N DEL IF Â¯\_(ãƒ„)_/Â¯*/

                if ( anyoEsMasPequenyo || anyoEsIgual && mesEsMasPequenyo || anyoEsIgual && mesEsIgual && diaEsMasPequenyo ) {
                    //se produce el intercambio de elementos

                    aux = registros.get(j);
                    registros.set(j,registros.get(j-1));
                    registros.set(j-1, aux);
                }
            }
        }


        try{
            fw = new FileWriter(temp,true);
            bw = new BufferedWriter(fw);

            for(int i = 0; i < registros.size(); i ++){
                bw.write(registros.get(i));
                bw.newLine();
            }
            bw.close();
            ficheroMovimientosCuenta.delete();
            temp.renameTo(ficheroMovimientosCuenta);


        }catch ( IOException e ){
            e.printStackTrace();
        }

    }

    /*
     * INTERFAZ
     * Signatura: public void insertarMovimientoEnFicheroMovimientos(String ID_Cuenta,boolean isIngresoOrRetirada, String concepto, double cantidad,GregorianCalendar fecha)
     * Comentario: Este mÃ©todo se encarga de modificar en el fichero de movimientos de la cuenta, aÃ±ade un nuevo movimiento.
     * Precondiciones: Se pasa por referencia el ID de la cuenta y por valor la cantidad de dinero a mover. Se pasa
     *                  un boolean que es true si el movimiento es un ingreso o false si es una retirada de dinero. Tambien se pasa la fecha
     * Entrada: (String ID_Cuenta,boolean isIngresoOrRetirada, double cantidad,GregorianCalendar fecha)
     * Salida:
     * Entrada/Salida:
     * Postcondiciones: Se modifica el fichero de movimientos de cuentas, aÃ±adiendo un movimiento nuevo.
     * */
    public void insertarMovimientoEnFicheroMovimientos(String ID_Cuenta,boolean isIngresoOrRetirada, String concepto, double cantidad,GregorianCalendar fecha){
        String nombre_banco = obtenerNombreBancoComercialPorIBAN(ID_Cuenta);
        File ficheroCuentas = new File ("./Files/BancosComerciales/"+nombre_banco+"/Movimientos/Movimientos_Cuenta_"+ID_Cuenta+".txt");
        FileWriter fw = null;
        BufferedWriter bw = null;
        String signo = "RETIRADA,-";
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setCalendar(fecha);
        String fechaformateada = sdf.format(fecha.getTime());

        try {
            fw = new FileWriter(ficheroCuentas,true);
            bw = new BufferedWriter(fw);
            if(isIngresoOrRetirada){
                signo = "INGRESO,+";
            }
            bw.write(concepto+","+signo+Double.toString(cantidad)+","+fechaformateada);
            bw.newLine();
            bw.close();
        }catch (IOException e){e.printStackTrace();}


    }


    /*
     * INTERFAZ
     * Signatura: public void modificarSaldoEnFicheroCuentas(String iban_cuenta, boolean sumaOresta,double cantidad)
     * Comentario: Este mÃ©todo se encarga de modificar en el fichero de Cuentas, el registro del saldo total (campo 2).
     * Precondiciones: Se pasa por referencia el ID de la cuenta a modificar y por valor la cantidad a aÃ±adir o substraer. Se pasa boolean que es true si aÃ±ade la cantidad o false si la resta
     * Entrada: String ID_Cuenta, boolean sumaOresta, double cantidad
     * Salida:
     * Entrada/Salida:
     * Postcondiciones: Se modifica el fichero de Cuentas y se actualiza el saldo pertinente.
     * */
    public void modificarSaldoEnFicheroCuentas(String iban_cuenta, boolean sumaOresta,double cantidad){
        String nombre_banco = obtenerNombreBancoComercialPorIBAN(iban_cuenta);
        File ficheroCuentas = new File ("./Files/BancosComerciales/"+nombre_banco+"/Cuentas_"+nombre_banco+".txt");
        FileReader leer = null;
        BufferedReader br = null;
        FileWriter fw = null;
        BufferedWriter bw = null;
        String campos[] = null;
        List<String> registros = new ArrayList<String>();   //toma ya usando arraylist Â¯\_(ãƒ„)_/Â¯
        String registro = " ";


        try {
            leer = new FileReader(ficheroCuentas);
            br = new BufferedReader(leer);

            while(br.ready()){
                registro = br.readLine();
                campos = registro.split(",");

                if(campos[0].equals(iban_cuenta)){
                    if(sumaOresta){
                        registro = registro.replace(campos[1], Double.toString(cantidad+Double.parseDouble(campos[1])));
                    }else{
                        registro = registro.replace(campos[1], Double.toString(Double.parseDouble(campos[1])-cantidad));
                    }

                }

                registros.add(registro);
            }
            br.close();


            /*SÃ­, aquÃ­ lo escribe de nuevo Â¯\_(ãƒ„)_/Â¯ */
            fw = new FileWriter(ficheroCuentas);
            bw = new BufferedWriter(fw);
            for(String s : registros) {
                bw.write(s);
                bw.newLine();
            }
            bw.close();


        }catch (IOException e){e.printStackTrace();}


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
     * Comentario: A partir de un IBAN, obtiene el BIC del banco comercial que gestiona la cuenta
     * Prototipo: public String obtenerBICporIBAN(String IBAN)
     * Entrada: Un String con el IBAN
     * Precondiciones: No hay
     * Salida: Un string con el BIC del banco que gestiona la cuenta
     * Postcondiciones: Asociado al nombre devuelve un string con el BIC del banco que gestiona la cuenta
     */
    public String obtenerBICporIBAN(String IBAN)
    {
    	return IBAN.substring(3, 9);
    }
    
    /* INTERFAZ
     * Comentario: Comprueba si un cliente (DNI) está registrado en un banco(BIC)
     * Prototipo: public boolean DNIRegistrado(String DNI, String BIC)
     * Entrada: Un string con el DNI a comprobar, y un String con el BIC del banco donde se quiere comprobar
     * Precondiciones: No hay
     * Salida: Un boolean indicando si el DNI está registrado en el banco o no
     * Postcondiciones: Asociado al nombre deuvelve true si el DNI está registrado en el banco (BIC), o false si no lo está.
     */
    public boolean DNIRegistrado(String DNI, String BIC)
    {
    	boolean registrado = false;
    	String nombreBanco = this.obtenerNombrePorBIC(BIC);
    	File ficheroClientes = new File ("./Files/BancosComerciales/"+nombreBanco+"/Clientes_"+nombreBanco+".txt");
		FileReader leer = null;
	    BufferedReader br = null;
	    String campos[] = null;
	    String registro = null;
	    
	    try 
        {
            leer = new FileReader(ficheroClientes);
            br = new BufferedReader(leer);

            while(br.ready())
            {
                registro = br.readLine();
                campos = registro.split(",");
                
                if(campos[1].equals(DNI))
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
     * Comentario: crea un nuevo cliente y una cuenta asociada a él en un banco determinado.
     * Prototipo: public boolean insertarCliente(String BIC, String DNI, double ingresosMensuales)
     * Entrada:
     * 		-> Un string con el BIC del banco donde se insertará el nuevo cliente
     * 		-> un String con el DNI del cliente
     * 		-> un double con los ingresos mensuales del cliente
     * Precondiciones: El BIC debe ser de un banco existente.
     * Salida: Un String indicando el IBAN de la cuenta asociada al cliente nuevo creado.
     * Postcondiciones: Asociado al nombre devuelve un String, que será el IBAN de la cuenta asociada al cliente nuevo, o null
     * 					Si no se creó correctamente.
     */
    public String insertarCliente(String BIC, String DNI, double ingresosMensuales)
    {
    	String IBAN = null;
    	
    	if(this.DNIRegistrado(DNI, BIC) == false && DNI.length() == 10)
    	{
    		String nombreBanco = this.obtenerNombrePorBIC(BIC);
    		File ficheroClientes = new File("./Files/BancosComerciales/"+nombreBanco+"/Clientes_"+nombreBanco+".txt");
    		File ficheroCuentas = new File("./Files/BancosComerciales/"+nombreBanco+"/Cuentas_"+nombreBanco+".txt");
    		FileWriter fw = null;
    		BufferedWriter bw = null;
    		
    		FileReader fr = null;
    		BufferedReader br = null;
    		String registro = null;
    		String[] campos = null;
    		String IBANUltimaCuenta = null;
    		String numeroCuentaUltima = null;
    		
    		//Insertar registro en el fichero de clientes del banco
    		try
    		{
    			fw = new FileWriter(ficheroClientes, true);
    			bw = new BufferedWriter(fw);
    			
    			bw.write("\n" + new ClienteImpl(BIC, DNI,ingresosMensuales).toString());
    			
    			bw.close();
    		}
    		catch(IOException e)
    		{
    			e.printStackTrace();
    		}
    		
    		//Crear una cuenta
    		
    		//Leer cual es la ultima cuenta registrada, para 
    		try
    		{
    			fr = new FileReader(ficheroCuentas);
    			br = new BufferedReader(fr);
    			
    			while(br.ready())
    			{
    				registro = br.readLine();
    				
    				if(br.ready() == false)
    				{
    					campos = registro.split(",");
    					IBANUltimaCuenta = campos[0];
    				}
    			}
    			
    			br.close();
    			fr.close();
    		}
    		catch(IOException e)
    		{
    			e.printStackTrace();
    		}
    		
    		numeroCuentaUltima = this.obtenerNumCuentaPorIBAN(IBANUltimaCuenta);

    		String numeroCuenta = String.valueOf((Integer.parseInt(numeroCuentaUltima) + 1));
    		while(numeroCuenta.length() < 7)
    		{
    			numeroCuenta = "0" + numeroCuenta;
    		}
    		
    		IBAN = "ESP"+BIC+numeroCuenta;
    		
    		CuentaImpl cuenta = new CuentaImpl(IBAN);
    		
    		//Añadir cuenta al fichero de cuentas
    		try
    		{
    			fw = new FileWriter(ficheroCuentas, true);
    			bw = new BufferedWriter(fw);
    			
    			bw.write("\n" + cuenta.toString());
    			
    			bw.close();
    		}
    		catch(IOException e)
    		{
    			e.printStackTrace();
    		}
    	}
    	
    	return IBAN;
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
