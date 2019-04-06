package creacionFicheros;

import clasesBasicas.BancoComercial;
import clasesBasicas.ClienteImpl;

import java.io.*;
import java.util.Scanner;

public class crearFicheroClientes {
    public static void main(String[] args) {
        File carpetaFicheros = new File("./ficheros/");
        carpetaFicheros.mkdir();    //crea la carpeta "ficherosCuentas"
        File archivoClientes = new File(carpetaFicheros, "listadoClientes.txt");
        FileWriter escribir = null;
        BufferedWriter bw = null;
        FileReader leer = null;
        BufferedReader br = null;
        Scanner sc = new Scanner(System.in);
        ClienteImpl c = null;
        String dni= " ";
        double ingresos=0.0;
        String registro;
        String[] campos = new String[2];
        try {
            escribir = new FileWriter(archivoClientes, false);
            bw = new BufferedWriter(escribir);
            for (int i = 0; i < 3; i++) {
                System.out.println("Introduce DNI: ");
                dni = sc.next();
                System.out.println("Ingresos mensuales netos:");
                ingresos = sc.nextDouble();

                c = new ClienteImpl(dni, ingresos);

                bw.write(c.toString());
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            leer = new FileReader(archivoClientes);
            br = new BufferedReader(leer);
            while (br.ready()) {
                System.out.println(br.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
