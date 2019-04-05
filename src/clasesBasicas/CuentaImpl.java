/*
 * Nombre: Cuenta
 * Propiedades:
 *   -Basicas:
 *       private String IBAN  consultable y no modificable
 *       private double cantidadDinero consultable y modificable
 *
 *   -Derivadas:
 *
 *   -Compartidas:
 *
 * Restricciones:
 * Metodos interface:
 *   public String getIBAN();
 *   public double getCantidadDinero();
 *
 *   public void setCantidadDinero(double cantidadDinero);
 * Metodos añadidos:
 * Metodos object:
 *   public String toString();
 * */
package clasesBasicas;

import interfaces.Cuenta;

public class CuentaImpl implements Cuenta {
    private String IBAN;
    private double cantidadDinero;

    public CuentaImpl(String IBAN){
        this.IBAN = IBAN;
        this.cantidadDinero = 0.0;
    }
    public CuentaImpl(String IBAN, double cantidadDinero){
        this.IBAN = IBAN;
        this.cantidadDinero = cantidadDinero;
    }


    public String getIBAN() {
        return IBAN;
    }


    public double getCantidadDinero() {
        return cantidadDinero;
    }

    public void setCantidadDinero(double cantidadDinero) {
        this.cantidadDinero = cantidadDinero;
    }

    @Override
    public String toString(){
        return getIBAN()+","+getCantidadDinero();
    }
}
