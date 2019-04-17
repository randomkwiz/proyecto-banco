/*
 * Nombre: BancoComercial
 * Propiedades:
 *   -Basicas:
 *       private String ID_BancoCentral consultable y modificable
 *       private String BIC  consultable y no modificable
 *       private String nombre consultable y modificable
 *
 *   -Derivadas:
 *
 *   -Compartidas:
 *
 * Restricciones:
 * Metodos interface:
 *   public String getBIC();
 *   public String getNombre();
 *
 *   public void setNombre(String nombre);
 * Metodos añadidos:
 *  public boolean realizarIngreso (ClienteImpl cl_origen, CuentaImpl cu_origen, ClienteImpl cl_destino, CuentaImpl cu_destino, double cantidad)
 *  public boolean aplicarComisionDescubierto(Cliente cliente, Cuenta cuenta, double cantidad)
 * Metodos object:
 *   public String toString();
 * */
package clasesBasicas;

import interfaces.Cuenta;

public class BancoComercial {

    private String ID_BancoCentral;
    private String BIC;
    private String nombre;

    public BancoComercial(String ID_BancoCentral,String BIC, String nombre) {
        this.ID_BancoCentral = ID_BancoCentral;
        this.BIC = BIC;
        this.nombre = nombre;
    }

    //setters y getters
    public String getBIC() {
        return BIC;
    }

    public String getNombre() {
        return nombre;
    }

    public String getID_BancoCentral() {
        return ID_BancoCentral;
    }

    public void setID_BancoCentral(String ID_BancoCentral) {
        this.ID_BancoCentral = ID_BancoCentral;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    //metodos añadidos

    /*
     * INTERFAZ
     * Signatura: public boolean realizarIngreso (ClienteImpl cl_origen, CuentaImpl cu_origen, ClienteImpl cl_destino, CuentaImpl cu_destino, double cantidad)
     * Comentario: recibe dos objetos (origen y destino) tipo Cliente, dos tipo Cuenta y una cantidad de dinero y saca esa cantidad de la cuenta origen y la ingresa en la cuenta destino.
     * Precondiciones: las cuentas deben existir. Por referencia se pasan dos objetos tipo Cuenta y dos Clientes y por valor se pasa un real.
     * Entradas: ClienteImpl cliente_origen, ClienteImpl cliente_destino, CuentaImpl origen, CuentaImpl destino, double cantidad
     * Salidas: boolean
     * Postcondiciones: se modifica el fichero y asociado al nombre se devuelve true si la operación se ha realizado con éxito y false si no.
     * */
    public boolean realizarIngreso (ClienteImpl cl_origen, CuentaImpl cu_origen, ClienteImpl cl_destino, CuentaImpl cu_destino, double cantidad){
        System.out.println("En construcción");
    return false;
    }

    /*
     * INTERFAZ
     * Signatura: public boolean aplicarComisionDescubierto(ClienteImpl cliente, CuentaImpl cuenta, double cantidad)
     * Comentario: recibe objetos tipo Cliente, Cuenta y una cantidad de dinero y aplica una comisión por descubierto a esa cuenta del cliente si está en saldo negativo.
     * Precondiciones: la cuenta debe existir. Por referencia se pasan ClienteImpl cliente y CuentaImpl cuenta y por valor se pasa un real.
     * Entradas: ClienteImpl cliente, CuentaImpl cuenta, double cantidad
     * Salidas: boolean
     * Postcondiciones: se modifica el fichero y asociado al nombre se devuelve true si la operación se ha realizado con éxito y false si no.
     * */
    public boolean aplicarComisionDescubierto(ClienteImpl cliente, CuentaImpl cuenta, double cantidad){
        System.out.println("En construcción");
        return false;
    }

    //Metodos object
    @Override
    public String toString(){
        return   getID_BancoCentral()+getBIC()+","+getNombre();
    }


}
