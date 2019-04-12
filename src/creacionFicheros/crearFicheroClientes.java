package creacionFicheros;

import clasesBasicas.BancoComercial;
import clasesBasicas.ClienteImpl;

import java.io.*;
import java.util.Scanner;

public class crearFicheroClientes {
    public static void main(String[] args) {
        File carpetaFicheros = new File("./ficheros/");
        carpetaFicheros.mkdir();    //crea la carpeta "ficherosCuentas"
        File archivoBancos = new File(carpetaFicheros, "listadoBancos.txt");
        FileWriter escribir = null;
        BufferedWriter bw = null;
        FileReader leer = null;
        BufferedReader br = null;
        Scanner sc = new Scanner(System.in);
        ClienteImpl c = null;
        String dni= " ";
        String BIC=" ";
        double ingresos=0.0;
        String registro=" ";
        String[] campos = new String[3];
        String nombreArchivo= " ";

        for (int i = 0; i < 1; i++) {
            System.out.println("Introduce BIC: ");
            BIC = sc.next();
           // System.out.println("Este es el BIC que está pillando: "+BIC);

            System.out.println("Introduce DNI: ");
            dni = sc.next();

            System.out.println("Ingresos mensuales netos:");
            ingresos = sc.nextDouble();

            c = new ClienteImpl(BIC,dni, ingresos);


            try{
                leer = new FileReader(archivoBancos);
                br = new BufferedReader(leer);


                while(br.ready()){
                    registro = br.readLine();
                    campos = registro.split(",");
                    //System.out.println("Este es el campo que compara "+ campos[1]);
                    if (campos[1].equals(BIC)){
                        nombreArchivo = "clientes_"+campos[2]+".txt";
                       // System.out.println("Nombre archivo: "+nombreArchivo);
                    }
                }
                br.close();

            }catch (IOException e){
                e.getStackTrace();
            }


            try{
                escribir = new FileWriter(new File (carpetaFicheros,nombreArchivo),false);
                bw = new BufferedWriter(escribir);

                bw.write(c.toString());
               // System.out.println("Esto se esta escribiendo en el fichero de clientes: "+ c.toString());
                bw.newLine();
                bw.close();

            }catch (IOException e){
                e.getStackTrace();
            }

            try{
                leer = new FileReader(new File (carpetaFicheros,nombreArchivo));
                br = new BufferedReader(leer);

                while(br.ready()){
                    System.out.println(br.readLine());
                }
                br.close();
            }catch (IOException e){
                e.getStackTrace();
            }

        }

    }
}
