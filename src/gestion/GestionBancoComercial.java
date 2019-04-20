/*
*  Voy a codificar aquÃ­ los mÃ©todos que he puesto en "ResguardoGestionBancoComercial"
*   a pesar de que creo que irÃ­an en la clase bÃ¡sica "BancoComercial" pero por tenerlos separados.
* Si luego vemos que son de BancoComercial, los cambio a allÃ­.
* */
package gestion;
import java.io.*;
//import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import clasesBasicas.ClienteImpl;
import clasesBasicas.CuentaImpl;

public class GestionBancoComercial extends GestionBanco{

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
        File f_cuentasBorradas = new File("./Files/BancosComerciales/"+obtenerNombreBancoComercialPorIBAN(iban)+"/CuentasBorradas_"+obtenerNombreBancoComercialPorIBAN(iban)+".txt");
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
        File f_cuentasBorradas = new File("./Files/BancosComerciales/"+obtenerNombreBancoComercialPorIBAN(iban_cuenta)+"/CuentasBorradas_"+obtenerNombreBancoComercialPorIBAN(iban_cuenta)+".txt");
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
     * Precondiciones: Se pasa el BIC del banco
     * Entrada: String bic
     * Salida:
     * Entrada/Salida:
     * Postcondiciones: modifica varios ficheros
     * */
    public boolean eliminarCuentasBorradasDefinitivamente(String bic) {
        File carpetaBanco = new File("./Files/BancosComerciales/" + obtenerNombrePorBIC(bic));
        File cuentas = new File(carpetaBanco, "Cuentas_" + obtenerNombrePorBIC(bic) + ".txt");
        File clientes = new File(carpetaBanco, "Clientes_" + obtenerNombrePorBIC(bic) + ".txt");
        File clientes_cuentas = new File(carpetaBanco, "Clientes_Cuentas_" + obtenerNombrePorBIC(bic) + ".txt");
        File cuentasBorradas = new File(carpetaBanco, "CuentasBorradas_" + obtenerNombrePorBIC(bic) + ".txt");
        File carpetaMovimientos = new File(carpetaBanco, "Movimientos");
        File archivoMovimientosCuenta[] = carpetaMovimientos.listFiles();
        List<String> cuentasABorrar = new ArrayList<String>();
        List<String> registrosMantenidos = new ArrayList<String>();
        FileReader fr = null;
        BufferedReader br = null;
        FileWriter fw = null;
        BufferedWriter bw = null;
        String registro = " ";
        String dniABorrar = " ";
        boolean archivoMovimientosBorrado = false;
        boolean borrada = false;
        boolean clienteBorrado = false;
        boolean cuentaBorrada = false;

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
                cuentaBorrada = true;
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

                    if (!cuentasABorrar.get(i).equals(registro.split(",")[1])) {
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
                clienteBorrado = true;
                bw.close();
                registrosMantenidos.clear();
            } catch (IOException e) {
                e.printStackTrace();
            }

            /*Borrar los ficheros de movimientos de las cuentas borradas*/
            for (int j = 0; j < archivoMovimientosCuenta.length; j++) {
                if (archivoMovimientosCuenta[j].getName().split("_")[2].equals(cuentasABorrar.get(i)+".txt")) {
                	archivoMovimientosBorrado = archivoMovimientosCuenta[j].delete();
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
        
        if(archivoMovimientosBorrado && clienteBorrado && cuentaBorrada)
        	borrada = true;
        
        return borrada;
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
    public List<String> buscarMovimientosPorFecha(String iban_cuenta, int anyo_buscado){
        File file_movimientos = new File("./Files/BancosComerciales/"+obtenerNombreBancoComercialPorIBAN(iban_cuenta)+"/Movimientos/Movimientos_Cuenta_"+iban_cuenta+".txt");
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
                if (fecha.get(Calendar.YEAR) ==  anyo_buscado ){
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
    public List<String> buscarMovimientosPorFecha(String iban_cuenta, int mes_buscado,int anyo_buscado){
        File file_movimientos = new File("./Files/BancosComerciales/"+obtenerNombreBancoComercialPorIBAN(iban_cuenta)+"/Movimientos/Movimientos_Cuenta_"+iban_cuenta+".txt");
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
                if (fecha.get(Calendar.YEAR) ==  anyo_buscado && fecha.get(Calendar.MONTH) == mes_buscado ){
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
    public List<String> buscarMovimientosPorFecha(String iban_cuenta, int dia_buscado,int mes_buscado,int anyo_buscado){
        File file_movimientos = new File("./Files/BancosComerciales/"+obtenerNombreBancoComercialPorIBAN(iban_cuenta)+"/Movimientos/Movimientos_Cuenta_"+iban_cuenta+".txt");
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
                if (fecha.get(Calendar.YEAR) ==  anyo_buscado && fecha.get(Calendar.MONTH) == mes_buscado && fecha.get(Calendar.DAY_OF_MONTH) == dia_buscado ){
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
     * Signatura: public List<String> ultimosDiezMovimientos(String iban_cuenta)
     * Comentario: devuelve los ultimos diez movimientos de la cuenta
     * Precondiciones: Se pasa un iban
     * Entrada: String iban
     * Salida: una lista de String
     * Entrada/Salida:
     * Postcondiciones: asociado al nombre devuelve una lista de String
     * */
    public List<String> ultimosDiezMovimientos(String iban_cuenta){
        String nombre_banco = obtenerNombreBancoComercialPorIBAN(iban_cuenta);
        File f_cuentas = new File("./Files/BancosComerciales/" + nombre_banco +"/Movimientos/Movimientos_Cuenta_" + iban_cuenta + ".txt");
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
    @Deprecated
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
        //File temp = new File ("./Files/BancosComerciales/"+nombre_banco+"/Movimientos/Movimientos_Cuenta_"+iban+"_temp.txt");
        FileReader fr = null;
        FileWriter fw = null;
        BufferedReader br = null;
        BufferedWriter bw = null;
        List<String> registros = new ArrayList<String>();    //arraylist - considerar cambiar a array
        String registro = " ";
        String aux=" "; //para el bubblesort de mÃ¡s abajo
        GregorianCalendar fechaUno = new GregorianCalendar();
        GregorianCalendar fechaDos = new GregorianCalendar();

        try{
            fr = new FileReader(ficheroMovimientosCuenta);
            br = new BufferedReader(fr);
            while(br.ready()){
                registro = br.readLine();
                registros.add(registro);
            }
            br.close();
            fr.close();
        }catch (IOException e){
            e.printStackTrace();
        }

        //ordenamiento burbuja

        for (int i = 0; i < registros.size()-1;i++){
            for (int j = registros.size()-1; j>i; j--){
                fechaUno.set(Calendar.YEAR, Integer.parseInt(registros.get(j-1).split(",")[3].split("/")[2]));
                fechaUno.set(Calendar.MONTH, Integer.parseInt(registros.get(j-1).split(",")[3].split("/")[1]));
                fechaUno.set(Calendar.DAY_OF_MONTH, Integer.parseInt(registros.get(j-1).split(",")[3].split("/")[0]));
                fechaDos.set(Calendar.YEAR, Integer.parseInt(registros.get(j).split(",")[3].split("/")[2]));
                fechaDos.set(Calendar.MONTH, Integer.parseInt(registros.get(j).split(",")[3].split("/")[1]));
                fechaDos.set(Calendar.DAY_OF_MONTH, Integer.parseInt(registros.get(j).split(",")[3].split("/")[0]));
                if (fechaUno.before(fechaDos) || fechaUno.equals(fechaDos) ) {
                    //se produce el intercambio de elementos
                    aux = registros.get(j);
                    registros.set(j,registros.get(j-1));
                    registros.set(j-1, aux);
                }
            }
        }


        try{
            fw = new FileWriter(ficheroMovimientosCuenta);
            bw = new BufferedWriter(fw);

            for(int i = 0; i < registros.size(); i ++){
                bw.write(registros.get(i));
                bw.newLine();
            }
            bw.close();
            fw.close();
        }catch ( IOException e ){
            e.printStackTrace();
        }

    }

    /*
     * INTERFAZ
     * Signatura: public void insertarMovimientoEnFicheroMovimientos(String ID_Cuenta,boolean isIngresoOrRetirada, String concepto, double cantidad,GregorianCalendar fecha)
     * Comentario: Este metodo se encarga de modificar en el fichero de movimientos de la cuenta, aÃ±ade un nuevo movimiento.
     * Precondiciones: Se pasa por referencia el ID de la cuenta y por valor la cantidad de dinero a mover. Se pasa
     *                  un boolean que es true si el movimiento es un ingreso o false si es una retirada de dinero. Tambien se pasa la fecha
     * Entrada: (String ID_Cuenta,boolean isIngresoOrRetirada, double cantidad,GregorianCalendar fecha)
     * Salida:
     * Entrada/Salida:
     * Postcondiciones: Se modifica el fichero de movimientos de cuentas, aÃ±adiendo un movimiento nuevo.
     * */
    public boolean insertarMovimientoEnFicheroMovimientos(String ID_Cuenta,boolean isIngresoOrRetirada, String concepto, double cantidad,GregorianCalendar fecha){
        String nombre_banco = obtenerNombreBancoComercialPorIBAN(ID_Cuenta);
        File ficheroCuentas = new File ("./Files/BancosComerciales/"+nombre_banco+"/Movimientos/Movimientos_Cuenta_"+ID_Cuenta+".txt");
        FileWriter fw = null;
        BufferedWriter bw = null;
        String signo = "RETIRADA,-";
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setCalendar(fecha);
        String fechaformateada = sdf.format(fecha.getTime());
        boolean movimientoInsertado = false;

        try {
            fw = new FileWriter(ficheroCuentas,true);
            bw = new BufferedWriter(fw);
            if(isIngresoOrRetirada){
                signo = "INGRESO,+";
            }
            bw.write(concepto+","+signo+Double.toString(cantidad)+","+fechaformateada);
            bw.newLine();
            movimientoInsertado = true;
            bw.close();
        }catch (IOException e){e.printStackTrace();}

        return movimientoInsertado;
    }


    /*
     * INTERFAZ
     * Signatura: public void modificarSaldoEnFicheroCuentas(String iban_cuenta, boolean sumaOresta,double cantidad)
     * Comentario: Este metodo se encarga de modificar en el fichero de Cuentas, el registro del saldo total (campo 2).
     * Precondiciones: Se pasa por referencia el ID de la cuenta a modificar y por valor la cantidad a aÃ±adir o substraer. Se pasa boolean que es true si aÃ±ade la cantidad o false si la resta
     * Entrada: String ID_Cuenta, boolean sumaOresta, double cantidad
     * Salida:
     * Entrada/Salida:
     * Postcondiciones: Se modifica el fichero de Cuentas y se actualiza el saldo pertinente.
     * */
    public boolean modificarSaldoEnFicheroCuentas(String iban_cuenta, boolean sumaOresta,double cantidad){
        String nombre_banco = obtenerNombreBancoComercialPorIBAN(iban_cuenta);
        File ficheroCuentas = new File ("./Files/BancosComerciales/"+nombre_banco+"/Cuentas_"+nombre_banco+".txt");
        FileReader leer = null;
        BufferedReader br = null;
        FileWriter fw = null;
        BufferedWriter bw = null;
        String campos[] = null;
        List<String> registros = new ArrayList<String>();   //toma ya usando arraylist Â¯\_(ãƒ„)_/Â¯
        String registro = " ";
        boolean saldoModificado = false;

        try {
            leer = new FileReader(ficheroCuentas);
            br = new BufferedReader(leer);

            while(br.ready()){
                registro = br.readLine();
                campos = registro.split(",");

                if(campos[0].equals(iban_cuenta)){
                    if(sumaOresta){
                        registro = registro.replace(campos[1], Double.toString(cantidad+Double.parseDouble(campos[1])));
                        saldoModificado = true;
                    }else{
                        registro = registro.replace(campos[1], Double.toString(Double.parseDouble(campos[1])-cantidad));
                        saldoModificado = true;
                    }

                }

                registros.add(registro);
            }
            br.close();



            fw = new FileWriter(ficheroCuentas);
            bw = new BufferedWriter(fw);
            for(String s : registros) {
                bw.write(s);
                bw.newLine();
            }
            bw.close();


        }catch (IOException e){e.printStackTrace();}

        return saldoModificado;
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
                
                if(registro != null)
                {
	                campos = registro.split(",");
	                if(campos[1].equals(DNI))
	                		registrado = true;
                }
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
    			
    			bw.write(new ClienteImpl(BIC, DNI,ingresosMensuales).toString());
    			
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
    					if(campos != null)
    						IBANUltimaCuenta = campos[0];
    				}
    			}
    			
    			if(br.ready() == false)
    				IBANUltimaCuenta = "ESP" + BIC + "0000000";
    			
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
    			
    			bw.write(cuenta.toString());
    			
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
	 * Comentario: Accede al fichero de cuentas y busca una cuenta por su IBAN para leer sus datos
	 * Prototipo: public String datosCuenta(String IBAN)
	 * Precondiciones: No hay
	 * Entrada: el IBAN de la cuenta.
	 * Salida: un String con los datos de la cuenta
	 * Postcondiciones: Asociado al nombre devuelve un String con los datos de la cuenta
	 */
	public String datosCuenta(String IBAN)
	{
		String cuenta = null;
		String nombreBanco = this.obtenerNombreBancoComercialPorIBAN(IBAN);
		File ficheroCuentas = new File("./Files/BancosComerciales/"+nombreBanco+"/Cuentas_"+nombreBanco+".txt");
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
}
