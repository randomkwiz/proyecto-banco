/*
 * Nombre: Cuenta
 * Propiedades:
 *   -Basicas:
 *      private String ID_Banco consultable y no modificable
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
    private String ID_Banco;
    private String IBAN;
    private double cantidadDinero;

    public CuentaImpl(String ID_Banco,String IBAN){
        this.ID_Banco = ID_Banco;
        this.IBAN = IBAN;
        this.cantidadDinero = 0.0;
    }
    public CuentaImpl(String ID_Banco,String IBAN, double cantidadDinero){
        this.ID_Banco = ID_Banco;
        this.IBAN = IBAN;
        this.cantidadDinero = cantidadDinero;
    }


    public String getID_Banco() {
        return ID_Banco;
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
        return getID_Banco()+","+getIBAN()+","+getCantidadDinero();
    }
}
