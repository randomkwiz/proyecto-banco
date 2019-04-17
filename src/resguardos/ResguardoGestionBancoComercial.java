/*Aquí están los resguardos de los métodos más complicados del Banco Comercial como por ejemplo:
* -Cobrar gastos administración cada X tiempo a X cuentas
* -Realizar movimientos en cuentas de cliente
* -La cosa esa de la reserva fraccionaria
* -Aplicar comisión por descubierto
*
* */
package resguardos;
public class ResguardoGestionBancoComercial {

    /*
    * INTERFAZ
    * Signatura:
    * Comentario:
    * Precondiciones:
    * Entrada:
    * Salida:
    * Entrada/Salida:
    * Postcondiciones:
    * */


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
        System.out.println("imprimir ultimos diez movimientos en resguardo");
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
        return false;
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
        return false;
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
        return false;
    }

    /*
     * INTERFAZ
     * Signatura: public String obtenerClientePorIBAN(String nombre_banco, String iban_cuenta)
     * Comentario: dado el nombre del banco y el iban de la cuenta, te devuelve el cliente al que pertenece la cuenta
     * Precondiciones: se pasan el nombre del banco y el iban de la cuenta
     * Entrada: String nombre_banco, String iban_cuenta
     * Salida: String que es el dni del cliente
     * Entrada/Salida:
     * Postcondiciones: Asociado al nombre se devuelve el DNI del cliente al que pertenece la cuenta
     * */
    public String obtenerClientePorIBAN(String nombre_banco, String iban_cuenta){
        System.out.println("Metodo obtener cliente por IBAN en resguardo");
        return " ";
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
        System.out.println("Metodo obtener IBAN por cliente en resguardo");
        return " ";
    }



    /*
     * INTERFAZ
     * Signatura: public void aplicarComisionDescubierto(String ID_Cuenta, double comision)
     * Comentario: Aplica una comisión por descubierto a una cuenta indicada
     * Precondiciones: Este método no controla que la cuenta a la que se le va a aplicar la comisión esté realmente descubierta
     * Entrada: String ID_Cuenta, double comision
     * Salida: -
     * Entrada/Salida:
     * Postcondiciones: El fichero de movimientos de esa cuenta se modificará, añadiendo el movimiento nuevo. También se
     * modificará el fichero de Cuentas, con el saldo actualizado tras el movimiento.
     * */
    public void aplicarComisionDescubierto(String ID_Cuenta, double comision){
        System.out.println("Aplicar comision por descubierto en resguardo");
    }

    /*
     * INTERFAZ
     * Signatura: public void sacarDinero(String nombre_banco, String ID_Cuenta, String concepto,double cantidad, int dia, int mes, int anyo)
     * Comentario: saca una cantidad dada de una cuenta
     * Precondiciones: Por valor se pasa una cantidad, por referencia la ID de una cuenta y el nombre del banco. Se pasa por valor dia mes y año
     * Entrada: String nombre_banco, String ID_Cuenta, double cantidad, int dia, int mes, int anyo
     * Salida:
     * Entrada/Salida:
     * Postcondiciones: Se modificarán los ficheros de Cuentas modificando el saldo y de movimientos, añadiendo el movimiento correspondiente.
     * */
    public void sacarDinero(String nombre_banco, String ID_Cuenta, double cantidad, int dia, int mes, int anyo){
        System.out.println("Método sacar dinero en resguardo");
    }

    /*
     * INTERFAZ
     * Signatura: public void ingresarDinero(String nombre_banco, String ID_Cuenta,String concepto, double cantidad, int dia, int mes, int anyo)
     * Comentario: ingresa una cantidad dada de una cuenta
     * Precondiciones: Por valor se pasa una cantidad, por referencia la ID de una cuenta y el nombre del banco. Por valor se pasa dia, mes y año
     * Entrada: String nombre_banco, String ID_Cuenta, double cantidad, int dia, int mes, int anyo
     * Salida:
     * Entrada/Salida:
     * Postcondiciones: Se modificarán los ficheros de Cuentas modificando el saldo y de movimientos, añadiendo el movimiento correspondiente.
     * */
    public void ingresarDinero(String nombre_banco,String ID_Cuenta,String concepto, double cantidad, int dia, int mes, int anyo){
        System.out.println("Método ingresar dinero en resguardo");
    }

    /*
     * INTERFAZ
     * Signatura: public void realizarMovimiento(String nombre_banco_origen,String cuenta_origen,String nombre_banco_destino, String cuenta_destino, String concepto,double cantidad, int dia, int mes, int anyo)
     * Comentario: Realiza un movimiento bancario, sacando una cantidad de la cuenta de origen e ingresándola en la cuenta destino.
     *              Llama a los métodos sacarDinero e ingresarDinero.
     * Precondiciones: Por referencia se pasan las ID de las cuentas y los nombres de los bancos de origen y destino, por valor se pasa la cantidad y dia mes y anyo. Tambien se pasa por referencia el concepto
     * Entrada: (String nombre_banco_origen,String cuenta_origen,String nombre_banco_destino, String cuenta_destino, String concepto,double cantidad, int dia, int mes, int anyo)
     * Salida:
     * Entrada/Salida:
     * Postcondiciones: Se modificarán los ficheros de cuentas y de movimientos correspondientes.
     * */
    public void realizarMovimiento(String nombre_banco_origen,String cuenta_origen,String nombre_banco_destino, String cuenta_destino, String concepto,double cantidad, int dia, int mes, int anyo){
        System.out.println("Método realizar movimiento en resguardo");
    }

    /*
     * INTERFAZ
     * Signatura: public void ordenarMovimientosPorFecha(String nombre_banco, String iban)
     * Comentario: Ordena un fichero de movimientos de una cuenta en base a las fechas, deja primero los más recientes.
     * Precondiciones: Se le pasa por referencia el nombre del banco y el IBAN de la cuenta
     * Entrada: String nombre_banco, String IBAN
     * Salida:
     * Entrada/Salida:
     * Postcondiciones: Se modifica el fichero y se deja ordenado de forma descendente
     * */
    public void ordenarMovimientosPorFecha(String bic, String iban){
        System.out.println("Metodo ordenar fichero movimientos por fecha en resguardo");
    }


    /*
     * INTERFAZ
     * Signatura: public void insertarMovimientoEnFicheroMovimientos(String nombre_banco,String ID_Cuenta,boolean isIngresoOrRetirada, String concepto, double cantidad,int dia, int mes, int anyo)
     * Comentario: Este método se encarga de modificar en el fichero de movimientos de la cuenta, añade un nuevo movimiento.
     * Precondiciones: Se pasa por referencia el ID de la cuenta y del banco a modificar y por valor la cantidad de dinero a mover. Se pasa
     *                  un boolean que es true si el movimiento es un ingreso o false si es una retirada de dinero. Tambien se pasa la fecha como tres valores enteros (se supone válida)
     * Entrada: (String nombre_banco,String ID_Cuenta,boolean isIngresoOrRetirada, double cantidad,int dia, int mes, int anyo)
     * Salida:
     * Entrada/Salida:
     * Postcondiciones: Se modifica el fichero de movimientos de cuentas, añadiendo un movimiento nuevo.
     * */
    public void insertarMovimientoEnFicheroMovimientos(String nombre_banco,String ID_Cuenta,boolean isIngresoOrRetirada, String concepto, double cantidad,int dia, int mes, int anyo){
        System.out.println("Metodo de insertar movimiento en fichero movimientos en resguardo");
    }


    /*
     * INTERFAZ
     * Signatura: public void modificarSaldoEnFicheroCuentas(String nombre_banco,String iban_cuenta, boolean sumaOresta,double cantidad)
     * Comentario: Este método se encarga de modificar en el fichero de Cuentas, el registro del saldo total (campo 2).
     * Precondiciones: Se pasa por referencia el ID de la cuenta a modificar y por valor la cantidad a añadir o substraer. Se pasa boolean que es true si añade la cantidad o false si la resta
     * Entrada: String nombre_banco, String ID_Cuenta, boolean sumaOresta, double cantidad
     * Salida:
     * Entrada/Salida:
     * Postcondiciones: Se modifica el fichero de Cuentas y se actualiza el saldo pertinente.
     * */
    public void modificarSaldoEnFicheroCuentas(String nombre_banco,String iban_cuenta, boolean sumaOresta,double cantidad){
        System.out.println("Metodo modificar saldo en fichero cuentas en resguardo");
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
        System.out.println("Metodo obtener BIC por nombre en resguardo");
        return " ";
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
    public String obtenerNombrePorBIC(String nombre_banco){
        System.out.println("Metodo obtener nombre por BIC en resguardo");
        return " ";
    }

}
