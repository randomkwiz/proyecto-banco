/*
* Programa de creación de ficheros que creará todos los ficheros necesarios
* y los dejará con algunos pocos registros.
* Estructura de ficheros:
* 	BancoCentral -> tiene una carpeta de "ficheros de banco central"
                    UN fichero con los bancos comerciales(clientes) que gestiona
                    UN fichero con las cuentas (de esos bancos) que gestiona
                            fichero con los movimientos de cada cuenta (un fichero x cada cuenta)

	BancoComercial -> tiene una carpeta de "ficheros de banco comercial" (una carpeta x cada banco)
                    UN fichero con las cuentas (de clientes) que gestiona (sí, UN fichero con todas las cuentas de ese banco)
                            fichero con los movimientos de cada cuenta (un fichero x cada cuenta)
* */
package creacionFicheros;
import clasesBasicas.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class crearFicheros {
    public static void main(String[] args) {

        FileWriter escribir = null;
        FileReader leer = null;
        BufferedWriter bw = null;
        BufferedReader br = null;

        /*Creación de carpetas*/
        File carpetaMadre = new File("./Files");
        carpetaMadre.mkdir();
        File carpetaBancoCentral = new File(carpetaMadre, "BancoCentral");
        carpetaBancoCentral.mkdir();
        File carpetaBancosComerciales = new File(carpetaMadre, "BancosComerciales");
        carpetaBancosComerciales.mkdir();

        /*Primero creamos los ficheros de banco central*/

        //Esto es para el fichero del banco central que almacena los clientes (bancos comerciales) que lleva.
        File listadoBancosComerciales_BancoCentral = new File(carpetaBancoCentral, "Clientes_BancoCentral.txt");
        BancoComercial bancos[] = new BancoComercial[3];
        bancos[0] = new BancoComercial("ESP","CAIXESBBXXX", "LaCaixa");
        bancos[1] = new BancoComercial("ESP","BSCHESMMXXX", "Santander");
        bancos[2] = new BancoComercial("ESP","INGDESMMXXX", "INGDirect");


            try{
                escribir = new FileWriter(listadoBancosComerciales_BancoCentral);
                bw = new BufferedWriter(escribir);
                for(int i = 0; i < bancos.length; i ++){
                    bw.write(bancos[i].toString());
                    bw.newLine();
                }
               bw.close();
            }catch (IOException e){
                e.getStackTrace();
            }


            try{
                leer = new FileReader(listadoBancosComerciales_BancoCentral);
                br = new BufferedReader(leer);
                while(br.ready()) {
                    System.out.println(br.readLine());
                }
            }catch (IOException e){
                e.getStackTrace();
            }

        //FIN

        //Esto es para el fichero que contiene los datos del propio banco central
        BancoCentral banco_central = new BancoCentral("ESP",1.5, 5);
        File datosBancoCentral = new File(carpetaBancoCentral, "Datos_BancoCentral.txt");
        try{
            escribir = new FileWriter(datosBancoCentral);
            bw = new BufferedWriter(escribir);
            bw.write(banco_central.toString());
            bw.close();

        }catch (IOException e){
            e.getStackTrace();
        }


        try{
            leer = new FileReader(datosBancoCentral);
            br = new BufferedReader(leer);
            while(br.ready()){
                System.out.println(br.readLine());
            }

        }catch (IOException e){
            e.getStackTrace();
        }
        //FIN fichero con datos del banco central

        //Fichero del banco central que contiene las cuentas que gestiona (una cuenta por cada banco comercial)

        File listadoCuentas_BancoCentral = new File(carpetaBancoCentral, "Cuentas_BancoCentral.txt");
        CuentaImpl cuentas[] = new CuentaImpl[3];
        cuentas[0] = new CuentaImpl("ESPCAIXESBBXXX0000000", 5000000.0);
        cuentas[1] = new CuentaImpl("ESPBSCHESMMXXX0000000", 5000000.0);
        cuentas[2] = new CuentaImpl("ESPINGDESMMXXX0000000", 5000000.0);
        try{
            escribir = new FileWriter(listadoCuentas_BancoCentral);
            bw = new BufferedWriter(escribir);
            for(int i = 0; i < cuentas.length; i ++){
                bw.write(cuentas[i].toString());
                bw.newLine();
            }
            bw.close();
        }catch (IOException e){
            e.getStackTrace();
        }


        try{
            leer = new FileReader(listadoCuentas_BancoCentral);
            br = new BufferedReader(leer);
            while(br.ready()) {
                System.out.println(br.readLine());
            }
        }catch (IOException e){
            e.getStackTrace();
        }
        //FIN


        /*Fichero que relaciona las cuentas con los clientes del BANCO CENTRAL*/

        File clientes_cuentas_BancoCentral = new File(carpetaBancoCentral, "Clientes_Cuentas_BancoCentral.txt");
        String linea[] = new String[2];
        String campo[][] = new String[2][3];
        try{
            escribir = new FileWriter(clientes_cuentas_BancoCentral);
            bw = new BufferedWriter(escribir);
            for(int i =0; i< cuentas.length; i++){
                linea[0] = cuentas[i].toString();
                campo[0] = linea[0].split(",");

                linea[1] = bancos[i].toString();
                campo[1] = linea[1].split(",");

                bw.write(campo[1][1]+","+campo[0][1]);
                bw.newLine();
            }

            bw.close();

        }catch (IOException e){
            e.printStackTrace();
        }

        /*FIN*/



        //Fichero con los movimientos de cada cuenta que maneja
        File carpetaMovimientos_BancoCentral = new File(carpetaBancoCentral, "MovimientosCuentas");
        carpetaMovimientos_BancoCentral.mkdir();

        String registro=" ";
        String[] campos = null;
        String nombreArchivo= " ";

            try {
                leer = new FileReader(listadoCuentas_BancoCentral);
                br = new BufferedReader(leer);


                while (br.ready()) {
                    registro = br.readLine();
                    campos = registro.split(",");
                    nombreArchivo = "Movimientos_" + campos[0]+".txt";
                    File f_movimientos_BancoCentral = new File(carpetaMovimientos_BancoCentral, nombreArchivo);
                    if(f_movimientos_BancoCentral.createNewFile()){
                        System.out.println(nombreArchivo+" creado con éxito.");
                    }else{
                        System.out.println("Error en la creación de "+nombreArchivo+"\nO bien el archivo ya existe.");
                    }
                }
                br.close();
            } catch (IOException e) {
                e.getStackTrace();
            }

        //FIN


        /*---------------------------------------------------------------------------------------------------------*/


        /*EMPEZAMOS CON LOS FICHEROS DE BANCO COMERCIAL*/

        //Esto crea una carpeta por cada cliente del banco central. O sea, una carpeta por cada banco comercial.
        try {
            leer = new FileReader(listadoBancosComerciales_BancoCentral);
            br = new BufferedReader(leer);
            while (br.ready()) {
                registro = br.readLine();
                campos = registro.split(",");
                nombreArchivo = campos[1];
                File carpetaBancoComercial = new File(carpetaBancosComerciales, nombreArchivo);
                carpetaBancoComercial.mkdir();
            }
            br.close();
        } catch (IOException e) {
            e.getStackTrace();
        }

        //FIN

        //Vamos a crear un archivo de cuentas en cada carpeta de cada banco comercial.
        String nombre_carpeta_banco_comercial[] = carpetaBancosComerciales.list();
        String bancoComercial=" ";
        File archivo_Cuentas_BancosComerciales = null;
        File carpetaSuperior = null;


        for(int i = 0; i < nombre_carpeta_banco_comercial.length; i ++) {
            try {
                carpetaSuperior = new File(carpetaBancosComerciales, nombre_carpeta_banco_comercial[i]);
                archivo_Cuentas_BancosComerciales = new File(carpetaSuperior, "Cuentas_" + nombre_carpeta_banco_comercial[i] + ".txt");
                if (archivo_Cuentas_BancosComerciales.createNewFile()) {
                    System.out.println("Exito al crear " + archivo_Cuentas_BancosComerciales.getPath());
                } else {
                    System.out.println("Error al crear " + archivo_Cuentas_BancosComerciales.getPath() + " o bien el archivo ya existe.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (archivo_Cuentas_BancosComerciales.exists()){
                try {
                    leer = new FileReader(listadoBancosComerciales_BancoCentral);
                    br = new BufferedReader(leer);
                    while (br.ready()) {
                        registro = br.readLine();
                        campos = registro.split(",");

                        if (campos[1].equals(nombre_carpeta_banco_comercial[i])) {
                            bancoComercial = campos[0];
                        }
                    }
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            try {
                escribir = new FileWriter(archivo_Cuentas_BancosComerciales);
                bw = new BufferedWriter(escribir);
                bw.write(new CuentaImpl(bancoComercial+"0000001", Math.random() * 8502).toString());
                bw.newLine();
                bw.write(new CuentaImpl(bancoComercial+"0000002", Math.random() * 8502).toString());
                bw.newLine();
                bw.write(new CuentaImpl(bancoComercial+"0000003", Math.random() * 8502).toString());
                bw.newLine();
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        }

        //FIN




    /*Fichero de clientes con los datos de los clientes de cada banco comercial*/

        File archivo_Clientes_BancosComerciales = null;
        for(int i = 0; i < nombre_carpeta_banco_comercial.length; i ++) {
            try {
                carpetaSuperior = new File(carpetaBancosComerciales, nombre_carpeta_banco_comercial[i]);
                archivo_Clientes_BancosComerciales = new File(carpetaSuperior, "Clientes_" + nombre_carpeta_banco_comercial[i] + ".txt");
                if (archivo_Clientes_BancosComerciales.createNewFile()) {
                    System.out.println("Exito al crear " + archivo_Clientes_BancosComerciales.getPath());
                } else {
                    System.out.println("Error al crear " + archivo_Clientes_BancosComerciales.getPath() + " o bien el archivo ya existe.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            /*obtener bic por nombre*/
            String bic = " ";
            try{
                leer = new FileReader(new File("./Files/BancoCentral/Clientes_BancoCentral.txt"));
                br = new BufferedReader(leer);
                while(br.ready()){
                    registro = br.readLine();
                    campos = registro.split(",");

                    if ( campos[1].equals(carpetaSuperior.getName())){
                        bic = campos[0].substring(3,13);
                    }
                }
            }catch (IOException e){
                e.printStackTrace();
            }




            try{
                escribir = new FileWriter(archivo_Clientes_BancosComerciales);
                bw = new BufferedWriter(escribir);

                    bw.write(new ClienteImpl(bic, "28835488-C", Math.random()*1500).toString());
                    bw.newLine();
                    bw.write(new ClienteImpl(bic, "68835488-A", Math.random()*1500).toString());
                    bw.newLine();
                    bw.write(new ClienteImpl(bic, "98835488-C", Math.random()*1500).toString());
                    bw.newLine();

                bw.close();

            }catch (IOException e){
                e.printStackTrace();
            }



        }//cierre del for




        /*FIN*/



        /*Fichero clientes - cuentas banco comercial*/

        File archivo_Clientes_Cuentas_BancosComerciales = null;
        List<String> iban_cuentas = new ArrayList<String>();
        List<String> dni_clientes = new ArrayList<String>();
        String iban_cuenta = " ";
        String dni_cliente = " ";


        for(int i = 0; i < nombre_carpeta_banco_comercial.length; i ++) {
            try {
                carpetaSuperior = new File(carpetaBancosComerciales, nombre_carpeta_banco_comercial[i]);
                archivo_Clientes_Cuentas_BancosComerciales = new File(carpetaSuperior, "Clientes_Cuentas_" + nombre_carpeta_banco_comercial[i] + ".txt");
                if (archivo_Clientes_Cuentas_BancosComerciales.createNewFile()) {
                    System.out.println("Exito al crear " + archivo_Clientes_Cuentas_BancosComerciales.getPath());
                } else {
                    System.out.println("Error al crear " + archivo_Clientes_Cuentas_BancosComerciales.getPath() + " o bien el archivo ya existe.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try{
                archivo_Cuentas_BancosComerciales = new File(carpetaSuperior, "Cuentas_" + nombre_carpeta_banco_comercial[i] + ".txt");
                leer = new FileReader(archivo_Cuentas_BancosComerciales);
                br = new BufferedReader(leer);

                while(br.ready()){
                    iban_cuenta = br.readLine().split(",")[0];
                    iban_cuentas.add(iban_cuenta);
                }
                br.close();

            }catch (IOException e){
                e.printStackTrace();
            }


            try{
                archivo_Clientes_BancosComerciales = new File(carpetaSuperior, "Clientes_" + nombre_carpeta_banco_comercial[i] + ".txt");
                leer = new FileReader(archivo_Clientes_BancosComerciales);
                br = new BufferedReader(leer);

                while(br.ready()){
                    dni_cliente = br.readLine().split(",")[1];
                    dni_clientes.add(dni_cliente);

                }
                br.close();

            }catch (IOException e){
                e.printStackTrace();
            }

            try{
                escribir = new FileWriter(archivo_Clientes_Cuentas_BancosComerciales);
                bw = new BufferedWriter(escribir);
                for(int j =0; j < iban_cuentas.size(); j ++){
                    bw.write(dni_clientes.get(j)+","+iban_cuentas.get(j));
                    bw.newLine();
                }
                bw.close();

            }catch (IOException e){
                e.printStackTrace();
            }

            dni_clientes.clear();
            iban_cuentas.clear();

            }//cierre del for




        /*FIN*/





        /*-----*/

        /*crear archivo de MOVIMIENTO de cada cuenta en cada carpeta de cada banco comercial*/

        File archivoCuentas_BancoComercial = null;
        File[] listadoFilesEnCarpeta = carpetaBancosComerciales.listFiles();
        File carpetaBanco[] = null;
        iban_cuenta=" ";
        File carpetaMovimientos = null;
        File file_movimientos_iban = null;
        for(int i =0; i < listadoFilesEnCarpeta.length; i ++) {
            System.out.println("Listado files en carpeta: " + listadoFilesEnCarpeta[i].getName());
            archivoCuentas_BancoComercial = new File(listadoFilesEnCarpeta[i], "Cuentas_"+listadoFilesEnCarpeta[i].getName()+".txt");
            try {
                leer = new FileReader(archivoCuentas_BancoComercial);
                System.out.println(archivoCuentas_BancoComercial.getName());
                br = new BufferedReader(leer);
                carpetaMovimientos = new File(listadoFilesEnCarpeta[i], "Movimientos");
                carpetaMovimientos.mkdir();
                while (br.ready()) {
                    registro = br.readLine();
                    campos = registro.split(",");
                    iban_cuenta = campos[0];
                    file_movimientos_iban = new File(carpetaMovimientos, "Movimientos_Cuenta_" + iban_cuenta+".txt");
                    if (file_movimientos_iban.createNewFile()) {
                        System.out.println("Exito al crear el archivo " + file_movimientos_iban.getName());
                    } else {
                        System.out.println("Erros al crear el archivo " + file_movimientos_iban.getName() + " o bien el archivo ya existe.");
                    }
                }
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        //FIN



        }//cierre del main
    }

