package gestion;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GestionBancoCentral 
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
	public void realizarMovimiento(String nombre_banco_origen,String cuenta_origen,String nombre_banco_destino, String cuenta_destino, String concepto,double cantidad, int dia, int mes, int anyo)
	{
		sacarDinero(nombre_banco_origen, cuenta_origen, concepto, cantidad, dia, mes, anyo);
        ingresarDinero(nombre_banco_destino, cuenta_destino, concepto, cantidad, dia, mes, anyo);
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
	public void sacarDinero(String nombre_banco, String ID_Cuenta,String concepto, double cantidad, int dia, int mes, int anyo)
	{
		insertarMovimientoEnFicheroMovimientos(nombre_banco, ID_Cuenta, false, concepto, cantidad, dia, mes, anyo);
        modificarSaldoEnFicheroCuentas(nombre_banco, ID_Cuenta, false, cantidad);
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
	public void modificarSaldoEnFicheroCuentas(String nombre_banco,String iban_cuenta, boolean sumaOresta,double cantidad)
	{
		File ficheroCuentas = new File ("./Files/BancoCentral/Cuentas_"+nombre_banco+".txt");
        FileReader leer = null;
        BufferedReader br = null;
        FileWriter fw = null;
        BufferedWriter bw = null;
        String campos[] = null;
        List<String> registros = new ArrayList<String>();   //toma ya usando arraylist ¯\_(ツ)_/¯
        String registro = " ";

        try 
        {
            leer = new FileReader(ficheroCuentas);
            br = new BufferedReader(leer);

            while(br.ready())
            {
                registro = br.readLine();
                campos = registro.split(",");

                if(campos[1].equals(iban_cuenta))
                {
                    if(sumaOresta)
                        registro = registro.replace(campos[2], Double.toString(cantidad+Double.parseDouble(campos[2])));
                    else
                        registro = registro.replace(campos[2], Double.toString(Double.parseDouble(campos[2])-cantidad));
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
	public void insertarMovimientoEnFicheroMovimientos(String nombre_banco,String ID_Cuenta,boolean isIngresoOrRetirada, String concepto, double cantidad,int dia, int mes, int anyo)
	{
		File ficheroCuentas = new File ("./Files/BancoCentral/MovimientosCuentas/Movimientos_"+ID_Cuenta+".txt");
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
	public void ingresarDinero(String nombre_banco,String ID_Cuenta,String concepto, double cantidad, int dia, int mes, int anyo)
	{
		insertarMovimientoEnFicheroMovimientos(nombre_banco, ID_Cuenta, true, concepto, cantidad, dia, mes, anyo);
        modificarSaldoEnFicheroCuentas(nombre_banco, ID_Cuenta, true, cantidad);
	}
}
