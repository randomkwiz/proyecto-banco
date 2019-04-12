package creacionFicheros;

import clasesBasicas.BancoCentral;

import java.io.*;
public class crearFicheroBancoCentral {
    public static void main(String[] args) {
        File carpetaFicheros = new File("./ficheros/");
        carpetaFicheros.mkdir();    //crea la carpeta "ficherosCuentas"
        File archivoBancoCentral = new File(carpetaFicheros, "datosBancoCentral.txt");
        FileWriter escribir = null;
        FileReader leer = null;
        BufferedWriter bw = null;
        BufferedReader br = null;
        BancoCentral banco = new BancoCentral("ESP",1.5, 5);


        try{
            escribir = new FileWriter(archivoBancoCentral,false);
            bw = new BufferedWriter(escribir);
            bw.write(banco.toString());
            bw.close();

        }catch (IOException e){
            e.getStackTrace();
        }


        try{
            leer = new FileReader(archivoBancoCentral);
            br = new BufferedReader(leer);
            while(br.ready()){
                System.out.println(br.readLine());
            }

        }catch (IOException e){
            e.getStackTrace();
        }
     }

}
