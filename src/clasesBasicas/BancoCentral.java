/* ESTUDIO DE INTERFAZ
 * 
 * Propiedades basicas:
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

import java.io.Serializable;

public class BancoCentral implements Cloneable, Serializable
{
	private double tasaInteres;
	private double coeficienteCaja;
	
	public BancoCentral()
	{
		this.tasaInteres = 0;
		this.coeficienteCaja = 0;
	}
	
	public BancoCentral(double tasaInteres, double coeficienteCaja)
	{
		this.tasaInteres = tasaInteres;
		this.coeficienteCaja = coeficienteCaja;
	}
	
	 public double getTasaInteres() { return this.tasaInteres; }
	 public double getCoeficienteCaja() { return this.coeficienteCaja; }
	  
	 public void setTasaInteres(double tasaInteres) { this.tasaInteres = tasaInteres; }
	 public void setCoeficienteCaja(double coeficienteCaja) { this.coeficienteCaja = coeficienteCaja; }
	 
	 //Representacion como cadena: sus atributos separados por coma
	 @Override
	 public String toString()
	 {
		 return "" + this.tasaInteres + "," + this.coeficienteCaja;
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
