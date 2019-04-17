/*
*  Voy a codificar aquí los métodos que he puesto en "ResguardoGestionBancoComercial"
*   a pesar de que creo que irían en la clase básica "BancoComercial" pero por tenerlos separados.
* Si luego vemos que son de BancoComercial, los cambio a allí.
* */
package gestion;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GestionBancoComercial {
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
     * Signatura: public void sacarDinero(String ID_Cuenta, String concepto,double cantidad, int dia, int mes, int anyo)
     * Comentario: saca una cantidad dada de una cuenta
     * Precondiciones: Por valor se pasa una cantidad, por referencia la ID de una cuenta(IBAN). Se pasa por valor dia mes y año
     * Entrada: String ID_Cuenta, double cantidad, int dia, int mes, int anyo
     * Salida:
     * Entrada/Salida:
     * Postcondiciones: Se modificarán los ficheros de Cuentas modificando el saldo y de movimientos, añadiendo el movimiento correspondiente.
     * */
    public void sacarDinero(String ID_Cuenta,String concepto, double cantidad, int dia, int mes, int anyo){
        insertarMovimientoEnFicheroMovimientos(ID_Cuenta, false, concepto, cantidad, dia, mes, anyo);
        modificarSaldoEnFicheroCuentas(ID_Cuenta, false, cantidad);

    }

    /*
     * INTERFAZ
     * Signatura: public void ingresarDinero(String ID_Cuenta,String concepto, double cantidad, int dia, int mes, int anyo)
     * Comentario: ingresa una cantidad dada de una cuenta
     * Precondiciones: Por valor se pasa una cantidad, por referencia la ID de una cuenta. Por valor se pasa dia, mes y año
     * Entrada: String ID_Cuenta, double cantidad, int dia, int mes, int anyo
     * Salida:
     * Entrada/Salida:
     * Postcondiciones: Se modificarán los ficheros de Cuentas modificando el saldo y de movimientos, añadiendo el movimiento correspondiente.
     * */
    public void ingresarDinero(String ID_Cuenta,String concepto, double cantidad, int dia, int mes, int anyo){
        insertarMovimientoEnFicheroMovimientos(ID_Cuenta, true, concepto, cantidad, dia, mes, anyo);
        modificarSaldoEnFicheroCuentas(ID_Cuenta, true, cantidad);

    }

    /*
     * INTERFAZ
     * Signatura: public void realizarMovimiento(String cuenta_origen,String cuenta_destino, String concepto,double cantidad, int dia, int mes, int anyo)
     * Comentario: Realiza un movimiento bancario, sacando una cantidad de la cuenta de origen e ingresándola en la cuenta destino.
     *              Llama a los métodos sacarDinero e ingresarDinero.
     * Precondiciones: Por referencia se pasan las ID de las cuentas por valor se pasa la cantidad y dia mes y anyo. Tambien se pasa por referencia el concepto
     * Entrada: (String cuenta_origen,String cuenta_destino, String concepto,double cantidad, int dia, int mes, int anyo)
     * Salida:
     * Entrada/Salida:
     * Postcondiciones: Se modificarán los ficheros de cuentas y de movimientos correspondientes.
     * */
    public void realizarMovimiento(String cuenta_origen,String cuenta_destino, String concepto,double cantidad, int dia, int mes, int anyo){
        sacarDinero(cuenta_origen, concepto, cantidad, dia, mes, anyo);
        ingresarDinero(cuenta_destino, concepto, cantidad, dia, mes, anyo);
    }


    /*
     * INTERFAZ
     * Signatura: public void ordenarMovimientosPorFecha(String iban)
     * Comentario: Ordena un fichero de movimientos de una cuenta en base a las fechas, deja primero los más recientes.
     * Precondiciones: Se le pasa por referencia el IBAN de la cuenta
     * Entrada: String IBAN
     * Salida:
     * Entrada/Salida:
     * Postcondiciones: Se modifica el fichero y se deja ordenado de forma descendente
     * */
    public void ordenarMovimientosPorFecha(String iban){
        String nombre_banco = obtenerNombreBancoComercialPorIBAN(iban);
        File ficheroMovimientosCuenta = new File ("./Files/BancosComerciales/"+nombre_banco+"/Movimientos/Movimientos_Cuenta_"+iban+".txt");
        File temp = new File ("./Files/BancosComerciales/"+nombre_banco+"/Movimientos/Movimientos_Cuenta_"+iban+"_temp.txt");
        FileReader fr = null;
        FileWriter fw = null;
        BufferedReader br = null;
        BufferedWriter bw = null;
        List<String> registros = new ArrayList<String>();    //arraylist - considerar cambiar a array
        String registro = " ";
        String aux=" "; //para el bubblesort de más abajo


        try{
            fr = new FileReader(ficheroMovimientosCuenta);
            br = new BufferedReader(fr);
            while(br.ready()){
                registro = br.readLine();
                registros.add(registro);

            }
            br.close();
        }catch (IOException e){
            e.printStackTrace();
        }

        //ordenamiento burbuja

        for (int i = 0; i < registros.size()-1;i++){
            for (int j = registros.size()-1; j>i; j--){

            /*SÉ QUE ESTO NO SE DEBE PONER ASÍ PERO NO SÉ CÓMO PONERLO DE OTRA MANERA*/
                /*HAY QUE CAMBIARLO*/

                boolean anyoEsMasPequenyo = (Integer.parseInt(registros.get(j).split(",")[3].split("/")[2]) < Integer.parseInt(registros.get(j-1).split(",")[3].split("/")[2] )  ) ;
                boolean anyoEsIgual = (Integer.parseInt(registros.get(j).split(",")[3].split("/")[2]) == Integer.parseInt(registros.get(j-1).split(",")[3].split("/")[2] )  ) ;
                boolean mesEsMasPequenyo = (Integer.parseInt(registros.get(j).split(",")[3].split("/")[1]) < Integer.parseInt(registros.get(j-1).split(",")[3].split("/")[1] )  ) ;
                boolean mesEsIgual = (Integer.parseInt(registros.get(j).split(",")[3].split("/")[1]) == Integer.parseInt(registros.get(j-1).split(",")[3].split("/")[1] )  ) ;
                boolean diaEsMasPequenyo = (Integer.parseInt(registros.get(j).split(",")[3].split("/")[0]) < Integer.parseInt(registros.get(j-1).split(",")[3].split("/")[0] )  ) ;

                /*ADEMÁS AUNQUE NO LE GUSTE ASÍ A ASUN, QUEDA MÁS LEGIBLE LA CONDICIÓN DEL IF ¯\_(ツ)_/¯*/

                if ( anyoEsMasPequenyo || anyoEsIgual && mesEsMasPequenyo || anyoEsIgual && mesEsIgual && diaEsMasPequenyo ) {
                    //se produce el intercambio de elementos

                    aux = registros.get(j);
                    registros.set(j,registros.get(j-1));
                    registros.set(j-1, aux);
                }
            }
        }


        try{
            fw = new FileWriter(temp,true);
            bw = new BufferedWriter(fw);

            for(int i = 0; i < registros.size(); i ++){
                bw.write(registros.get(i));
                bw.newLine();
            }
            bw.close();
            ficheroMovimientosCuenta.delete();
            temp.renameTo(ficheroMovimientosCuenta);


        }catch ( IOException e ){
            e.printStackTrace();
        }

    }

    /*
     * INTERFAZ
     * Signatura: public void insertarMovimientoEnFicheroMovimientos(String ID_Cuenta,boolean isIngresoOrRetirada, String concepto, double cantidad,int dia, int mes, int anyo)
     * Comentario: Este método se encarga de modificar en el fichero de movimientos de la cuenta, añade un nuevo movimiento.
     * Precondiciones: Se pasa por referencia el ID de la cuenta y por valor la cantidad de dinero a mover. Se pasa
     *                  un boolean que es true si el movimiento es un ingreso o false si es una retirada de dinero. Tambien se pasa la fecha como tres valores enteros (se supone válida)
     * Entrada: (String ID_Cuenta,boolean isIngresoOrRetirada, double cantidad,int dia, int mes, int anyo)
     * Salida:
     * Entrada/Salida:
     * Postcondiciones: Se modifica el fichero de movimientos de cuentas, añadiendo un movimiento nuevo.
     * */
    public void insertarMovimientoEnFicheroMovimientos(String ID_Cuenta,boolean isIngresoOrRetirada, String concepto, double cantidad,int dia, int mes, int anyo){
        String nombre_banco = obtenerNombreBancoComercialPorIBAN(ID_Cuenta);
        File ficheroCuentas = new File ("./Files/BancosComerciales/"+nombre_banco+"/Movimientos/Movimientos_Cuenta_"+ID_Cuenta+".txt");
        FileWriter fw = null;
        BufferedWriter bw = null;
        String signo = "RETIRADA,-";
        try {
            fw = new FileWriter(ficheroCuentas,true);
            bw = new BufferedWriter(fw);
            if(isIngresoOrRetirada){
                signo = "INGRESO,+";
            }
            bw.write(concepto+","+signo+Double.toString(cantidad)+","+Integer.toString(dia)+"/"+Integer.toString(mes)+"/"+Integer.toString(anyo));
            bw.newLine();
            bw.close();
        }catch (IOException e){e.printStackTrace();}


    }


    /*
     * INTERFAZ
     * Signatura: public void modificarSaldoEnFicheroCuentas(String iban_cuenta, boolean sumaOresta,double cantidad)
     * Comentario: Este método se encarga de modificar en el fichero de Cuentas, el registro del saldo total (campo 2).
     * Precondiciones: Se pasa por referencia el ID de la cuenta a modificar y por valor la cantidad a añadir o substraer. Se pasa boolean que es true si añade la cantidad o false si la resta
     * Entrada: String ID_Cuenta, boolean sumaOresta, double cantidad
     * Salida:
     * Entrada/Salida:
     * Postcondiciones: Se modifica el fichero de Cuentas y se actualiza el saldo pertinente.
     * */
    public void modificarSaldoEnFicheroCuentas(String iban_cuenta, boolean sumaOresta,double cantidad){
        String nombre_banco = obtenerNombreBancoComercialPorIBAN(iban_cuenta);
        File ficheroCuentas = new File ("./Files/BancosComerciales/"+nombre_banco+"/Cuentas_"+nombre_banco+".txt");
        FileReader leer = null;
        BufferedReader br = null;
        FileWriter fw = null;
        BufferedWriter bw = null;
        String campos[] = null;
        List<String> registros = new ArrayList<String>();   //toma ya usando arraylist ¯\_(ツ)_/¯
        String registro = " ";


        try {
            leer = new FileReader(ficheroCuentas);
            br = new BufferedReader(leer);

            while(br.ready()){
                registro = br.readLine();
                campos = registro.split(",");

                if(campos[1].equals(iban_cuenta)){
                    if(sumaOresta){
                        registro = registro.replace(campos[2], Double.toString(cantidad+Double.parseDouble(campos[2])));
                    }else{
                        registro = registro.replace(campos[2], Double.toString(Double.parseDouble(campos[2])-cantidad));
                    }

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


        }catch (IOException e){e.printStackTrace();}


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
        File clientesBancoCentral = new File("./Files/BancoCentral/Clientes_BancoCentral.txt");
        FileReader leer = null;
        BufferedReader br = null;
        String registro = " ";
        String campos[] = null;
        String bic = " ";
        try{
            leer = new FileReader(clientesBancoCentral);
            br = new BufferedReader(leer);
            while(br.ready()){
                registro = br.readLine();
                campos = registro.split(",");

                if ( campos[1].equals(nombre_banco)){
                    bic = campos[0].substring(3,13);
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }


        return bic;
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
    @Deprecated //teniendo obtenerNombrePorIBAN este metodo ya es innecesario
    public String obtenerNombrePorBIC(String bic){
        File clientesBancoCentral = new File("./Files/BancoCentral/Clientes_BancoCentral.txt");
        FileReader leer = null;
        BufferedReader br = null;
        String registro = " ";
        String campos[] = null;
        String nombre = " ";
        try{
            leer = new FileReader(clientesBancoCentral);
            br = new BufferedReader(leer);
            while(br.ready()){
                registro = br.readLine();
                campos = registro.split(",");

                if ( campos[0].substring(3,13).equals(bic)){
                    nombre = campos[1];
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }


        return nombre;
    }

    /*
     * INTERFAZ
     * Signatura: public String obtenerNombreBancoComercialPorIBAN(String iban_cuenta)
     * Comentario: devuelve el nombre de un banco dado el IBAN de una cuenta
     * Precondiciones: por referencia se pasa un string
     * Entrada: String iban_cuenta
     * Salida: String nombre
     * Entrada/Salida:
     * Postcondiciones: Asociado al nombre se devuelve un String
     * */
    public String obtenerNombreBancoComercialPorIBAN(String iban_cuenta){

        return obtenerNombrePorBIC(iban_cuenta.substring(3,13));
    }

}
