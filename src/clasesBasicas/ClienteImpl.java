/*
 * Nombre: Cliente
 * Propiedades:
 *   -Basicas:
 *      private String BIC_Banco consultable y modificable
 *       private String DNI  consultable y no modificable
 *       private double ingresoMensual consultable y modificable
 *
 *   -Derivadas:
 *
 *   -Compartidas:
 *
 * Restricciones: El DNI debe ser válido y el ingreso mensual debe ser igual o mayor que 0
 * Metodos interface:
 *   public String getDNI();
 *   public double getIngresoMensual();
 *
 *   public void setIngresoMensual(double ingresoMensual);
 * Metodos añadidos:
 *   public void sacarDinero(Cuenta cuenta)
 *   public void ingresarDinero(Cuenta cuenta)
 *   public void realizarTransferencia(Cuenta origen, Cuenta destino)
 *   public void verHistorialTransferencias(Cuenta cuenta)
 * Metodos object:
 *   public String toString();
 * */
package clasesBasicas;
import interfaces.Cliente;
import interfaces.Cuenta;

public class ClienteImpl implements Cliente{

    private String BIC_Banco;
    private String DNI;
    private double ingresoMensual;

    public ClienteImpl()
    {
    	this.BIC_Banco = "000000MMXXX";
    	this.DNI = "00000000A";
    	this.ingresoMensual = 0;
    }

    public ClienteImpl(String BIC_Banco, String DNI, double ingresoMensual) {
        this.BIC_Banco = BIC_Banco;
        this.DNI = DNI;
        this.ingresoMensual = ingresoMensual;
    }

    public ClienteImpl(ClienteImpl o){
        this.BIC_Banco = o.BIC_Banco;
        this.DNI = o.DNI;
        this.ingresoMensual = o.ingresoMensual;
    }

    //metodos interface

    public String getDNI() {
        return DNI;
    }
    public String getBIC_Banco(){return BIC_Banco;}
    public void setBIC_Banco(String BIC_Banco){this.BIC_Banco = BIC_Banco;}
    public double getIngresoMensual() {
        return ingresoMensual;
    }

    public void setIngresoMensual(double ingresoMensual) {
        this.ingresoMensual = ingresoMensual;
    }

    //metodos añadidos

    /*
    * INTERFAZ
    * Signatura: public void sacarDinero(Cuenta cuenta, double cantidad)
    * Comentario: recibe un objeto tipo Cuenta y una cantidad de dinero y saca ese dinero de esa cuenta
    * Precondiciones: la cuenta debe existir. Por referencia se pasa un objeto tipo Cuenta y por valor se pasa un real.
    * Entradas: Cuenta cuenta y double cantidad
    * Salidas:
    * Postcondiciones: se modifica el fichero.
    * */
    @Deprecated
    public void sacarDinero(Cuenta cuenta, double cantidad){
        System.out.println("En construcción");
    }

    /*
     * INTERFAZ
     * Signatura: public void ingresarDinero(Cuenta cuenta, double cantidad)
     * Comentario: recibe un objeto tipo Cuenta y una cantidad de dinero e introduce ese dinero en esa cuenta
     * Precondiciones: la cuenta debe existir. Por referencia se pasa un objeto tipo Cuenta y por valor se pasa un real.
     * Entradas: Cuenta cuenta y double cantidad
     * Salidas:
     * Postcondiciones: se modifica el fichero.
     * */
    @Deprecated
    public void ingresarDinero(Cuenta cuenta, double cantidad){
        System.out.println("En construcción");
    }

    /*
     * INTERFAZ
     * Signatura: public void realizarTransferencia(Cuenta origen, Cuenta destino, double cantidad)
     * Comentario: recibe dos objeto tipo Cuenta y una cantidad de dinero. Saca esa cantidad de la cuenta de origen y la ingresa en la cuenta destino.
     * Precondiciones: las cuentas deben existir. Por referencia se pasa un objeto tipo Cuenta y por valor se pasa un real.
     *    -->          La cuenta origen debe tener esa cantidad de dinero que se desea sacar. --> esto no sé si quitarlo
     * Entradas: Cuenta origen, Cuenta destino y double cantidad
     * Salidas:
     * Postcondiciones: se modifica el fichero.
     * */
    @Deprecated
    public void realizarTransferencia(Cuenta origen, Cuenta destino, double cantidad){
        System.out.println("En construcción");
    }

    /*
     * INTERFAZ
     * Signatura: public void verHistorialTransferencias(Cuenta cuenta)
     * Comentario: imprime en pantalla el historial de movimientos de una Cuenta
     * Precondiciones: La cuenta debe existir
     * Entradas: Cuenta cuenta
     * Salidas:
     * Postcondiciones: imprime en pantalla
     * */
    @Deprecated
    public void verHistorialTransferencias(Cuenta cuenta){
        System.out.println("En construcción");
    }


    //Metodos Object

    @Override
    public String toString(){
        return  getBIC_Banco()+","+  getDNI()+","+getIngresoMensual();
    }
}
