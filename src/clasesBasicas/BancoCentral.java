/* ESTUDIO DE INTERFAZ
 *
 * Propiedades basicas:
 *    ->  ID: String, consultable, modificable //se necesita el set
 * 		-> tasaInteres: double, consultable, modificable
 * 		-> coeficienteCaja: double, consultable, modificable
 *
 * Propiedades derivadas: No hay
 * Propiedades comaprtidas: No hay
 */

/* INTERFAZ
 * public double getTasaInteres();
 * public double getCoeficienteCaja();
 *
 * public void setTasaInteres(double tasaInteres);
 * public void setCoeficienteCaja(double coeficienteCaja);
 */
package clasesBasicas;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Serializable;

public class BancoCentral implements Cloneable, Serializable
{
    private String ID;
    private double tasaInteres;
    private double coeficienteCaja;

    public BancoCentral()
    {
        this.tasaInteres = 0;
        this.coeficienteCaja = 0;
    }

    public BancoCentral(String ID,double tasaInteres, double coeficienteCaja)
    {
        this.ID = ID;
        this.tasaInteres = tasaInteres;
        this.coeficienteCaja = coeficienteCaja;
    }
    
    public BancoCentral(BancoCentral otro)
    {
    	this.ID = otro.ID;
    	this.tasaInteres = otro.tasaInteres;
    	this.coeficienteCaja = otro.coeficienteCaja;
    }

    public double getTasaInteres() { return this.tasaInteres; }
    public double getCoeficienteCaja() { return this.coeficienteCaja; }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setTasaInteres(double tasaInteres) { this.tasaInteres = tasaInteres; }
    public void setCoeficienteCaja(double coeficienteCaja) { this.coeficienteCaja = coeficienteCaja; }
    
    /*
     * INTERFAZ
     * Comentario: recibe dos objetos (origen y destino) tipo Cliente, dos tipo Cuenta y una cantidad de dinero y saca esa cantidad de la cuenta origen y la ingresa en la cuenta destino.
     * Prtotipo: public boolean realizarIngreso (ClienteImpl cl_origen, CuentaImpl cu_origen, ClienteImpl cl_destino, CuentaImpl cu_destino, double cantidad)
     * Precondiciones: las cuentas deben existir. Por referencia se pasan dos objetos tipo Cuenta y dos Clientes y por valor se pasa un real.
     * Entrada: ClienteImpl cliente_origen, ClienteImpl cliente_destino, CuentaImpl origen, CuentaImpl destino, double cantidad
     * Salida: boolean
     * Postcondiciones: se modifica el fichero y asociado al nombre se devuelve true si la operación se ha realizado con éxito y false si no.
     * */
    /* PSEUDOCODIGO
     * realizarIngreso(Ivan, cuentaIvan, Pepe, cuentaPepe, 145.23)
     * Inicio
     * 	Abrir fichero de la cuenta de origen
     * 	Escribir registro del movimiento de salida de dinero
     * 	Cerrar fichero de la cuenta de origen
     * 	Abrir fichero la cuenta de destino
     * 	Escribir registro del movimiento de entrada de dinero
     * 	Cerrar fichero de lac uenta de destino
     * 	Abrir fichero de las cuentas del banco
     * 	Modificar el dinero de las cuentas de origen y destino
     * Fin
     */
    public boolean realizarIngreso (CuentaImpl cu_origen, CuentaImpl cu_destino, double cantidad)
    {
    	File ficheroCuentaOrigen = null;
    	File ficheroCuentaDestino = null;
    	File ficheroCuentas = null;
    	FileWriter salidaFichero = null;
    	BufferedWriter writer = null;
    	RandomAccessFile randAccessFile = null;
    	
        //Abrir fichero de la cuenta de origen
        try
        {
        	//Escribir registro del movimiento de salida de dinero
        	ficheroCuentaOrigen = new File("./Files/BancoCentral/MovimientosCuentas/Movimientos_" + cu_origen.getIBAN() + ".txt");
        	salidaFichero = new FileWriter(ficheroCuentaOrigen,true);
        	writer = new BufferedWriter(salidaFichero);
        	
        	writer.write(cu_origen.getIBAN() + "," + cu_destino.getIBAN() + "," + cantidad + ",R");
        	writer.newLine();
        	
        }
        catch(IOException e)
        {
        	e.printStackTrace();
        }
        finally
        {
        	try
            {
        		//Cerrar fichero de la cuenta de origen
        		writer.close();
        		salidaFichero.close();
            }
            catch(IOException e)
            {
            	e.printStackTrace();
            }
        }
        
      //Abrir fichero la cuenta de destino
        try
        {
        	//Escribir registro del movimiento de entrada de dinero
        	ficheroCuentaDestino = new File("./Files/BancoCentral/MovimientosCuentas/Movimientos_" + cu_destino.getIBAN() + ".txt");
        	salidaFichero = new FileWriter(ficheroCuentaDestino,true);
        	writer = new BufferedWriter(salidaFichero);
        	
        	writer.write(cu_origen.getIBAN() + "," + cu_destino.getIBAN() + "," + cantidad + ",D");
        	writer.newLine();
        	
        }
        catch(IOException e)
        {
        	e.printStackTrace();
        }
        finally
        {
        	try
            {
        		//Cerrar fichero de la cuenta de destino
        		writer.close();
        		salidaFichero.close();
            }
            catch(IOException e)
            {
            	e.printStackTrace();
            }
        }
        
        //TODO Falta que se modifique el dinero en el fichero de las cuentas del banco
        
        //Abrir fichero de las cuentas del banco
        
        //Modificar el dinero de las cuentas de origen y destino

        return false;
    }

    //Representacion como cadena: sus atributos separados por coma
    @Override
    public String toString()
    {
        return this.ID+"," + this.tasaInteres + "," + this.coeficienteCaja;
    }

    public BancoCentral clone()
    {
        BancoCentral copia = null;

        try
        {
            copia = (BancoCentral)super.clone();
        }
        catch(CloneNotSupportedException e)
        {
            e.printStackTrace();
        }

        return copia;
    }
}

