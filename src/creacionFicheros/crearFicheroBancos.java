/*
 * Creación de los ficheros de Bancos.
 * Un fichero con todos los bancos comerciales.
 * */
package creacionFicheros;

import clasesBasicas.BancoComercial;

import java.io.*;

public class crearFicheroBancos {
    public static void main(String[] args) {
        File carpetaFicheros = new File("./ficheros/");
        carpetaFicheros.mkdir();    //crea la carpeta "ficherosCuentas"
        File archivoBancos = new File(carpetaFicheros, "listadoBancos.txt");
        FileWriter escribir = null;
        BufferedWriter bw = null;
        FileReader leer = null;
        BufferedReader br = null;
        BancoComercial bancos[] = new BancoComercial[3];

        bancos[0] = new BancoComercial("CAIXESBBXXX", "La Caixa");
        bancos[1] = new BancoComercial("BSCHESMMXXX", "Santander");
        bancos[2] = new BancoComercial("INGDESMMXXX", "ING Direct");

        try{
            escribir = new FileWriter(archivoBancos, false);
            bw = new BufferedWriter(escribir);
            for(int i = 0; i < bancos.length; i ++){
                bw.write(bancos[i].toString());
                bw.newLine();
            }
            bw.close();
        }catch (IOException e){
            e.printStackTrace();
        }

        try{
            leer = new FileReader(archivoBancos);
            br = new BufferedReader(leer);
            while(br.ready()){
                System.out.println(br.readLine());
            }
        }catch (IOException e){
            e.printStackTrace();
        }





    }
}
