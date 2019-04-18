package utilidades;

import gestion.GestionBancoComercial;

import java.util.Scanner;

public class ValidacionProgramaCliente {


    public int menu(){
        Scanner sc = new Scanner(System.in);
        int op;
        do {
            System.out.println("MENU");
            System.out.println("0. Salir");
            System.out.println("1. Realizar transferencia bancaria\n" +
                    "2. Ver datos de la cuenta propia\n" +
                    "3. Buscar movimientos\n" +
                    "4. Cancelar cuenta ");
            op = sc.nextInt();
        }while(op < 0 && op >4);
        return op;
    }

    /*
    * INTERFAZ
    * Signatura: public String inicioSesion()
    * Comentario: pide, lee y valida un inicio de sesión
    * Precondiciones:
    * Entradas:
    * Salidas: String
    * Postcondiciones: asociado al nombre se devuelve un String que es el IBAN de la cuenta que se va a manejar
    * */
    public String inicioSesion(){
        Scanner sc = new Scanner(System.in);
        GestionBancoComercial gbc = new GestionBancoComercial();
        String dni, iban_cuenta;
    do {
        System.out.println("Inicio de sesión: ");
        do {
            System.out.println("Introduce el IBAN de la cuenta: ");
            iban_cuenta = sc.next().toUpperCase();
        } while (!gbc.isIBANvalido(iban_cuenta));

        do {
            System.out.println("Introduce el DNI asociado a la cuenta: ");
            dni = sc.next().toUpperCase();
        } while (!gbc.isDNIvalido(gbc.obtenerNombreBancoComercialPorIBAN(iban_cuenta), dni));

    }while (!gbc.isPropietario(dni,iban_cuenta));
        System.out.println("Sesión iniciada correctamente");
        System.out.println("Datos: ");
        System.out.println("DNI: "+dni);
        System.out.println("IBAN: "+iban_cuenta);
    return iban_cuenta;
    }

    /*
     * INTERFAZ
     * Signatura: public String iban()
     * Comentario: pide, lee y valida un IBAN
     * Precondiciones:
     * Entradas:
     * Salidas: String
     * Postcondiciones: asociado al nombre se devuelve un String que es el IBAN de la cuenta
     * */
    public String iban(){
        Scanner sc = new Scanner(System.in);
        GestionBancoComercial gbc = new GestionBancoComercial();
        String iban_cuenta;
            do {
                System.out.println("IBAN de la cuenta destino: ");
                iban_cuenta = sc.next().toUpperCase();
            } while (!gbc.isIBANvalido(iban_cuenta));

        return iban_cuenta;
    }

    /*
     * INTERFAZ
     * Signatura: public String concepto()
     * Comentario: pide un concepto para una transferencia
     * Precondiciones:
     * Entradas:
     * Salidas: String
     * Postcondiciones: asociado al nombre se devuelve un String que es el concepto
     * */
    public String concepto(){
        Scanner sc = new Scanner(System.in);
        String concepto;
        do {
            System.out.println("Concepto: ");
            concepto = sc.nextLine().toUpperCase();
        }while(concepto.length()<= 2);

        return concepto;
    }

    /*
     * INTERFAZ
     * Signatura: public double cantidadATransferir()
     * Comentario: pide una cantidad para una transferencia
     * Precondiciones:
     * Entradas:
     * Salidas: String
     * Postcondiciones: asociado al nombre se devuelve un double que es la cantidad
     * */
    public double cantidadATransferir(){
        Scanner sc = new Scanner(System.in);
        double cantidad;
        do {
            System.out.println("Cantidad a transferir: ");
            cantidad = sc.nextDouble();
        }while(cantidad <= 0.0);

        return cantidad;
    }

}
