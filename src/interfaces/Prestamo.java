/* ESTUDIO DE INTERFAZ
 * 
 * Propiedades b�sicas:
 * 		-> ID: int, consultable
 * 		-> cantidad: double, consultable, modificable
 * 		-> tiempoMeses: int, consultable, modificable
 * 
 * Propiedades derivadas:
 * 		-> mensualidad: double, consultable
 * 	
 * Propiedades compartidas: 
 */

/* INTERFAZ
 *  public int getID();
 *	public double getCantidad();
 *  public int getTiempoMeses();
 *	
 *	public double getMensualidad();
 */
package interfaces;

public interface Prestamo
{
	public int getID();
	public double getCantidad();
	public int getTiempoMeses();
	
	public double getMensualidad();
}
