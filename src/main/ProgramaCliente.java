/*
* Comentario: Programa para el cliente desde donde puede realizar transferencias bancarias a otras cuentas,
*               ver el saldo de su propia cuenta, buscar un movimiento de su cuenta y cancelar su cuenta, entre otras cosas.
*
* PSEUDOCODIGO MAIN - VISTA DEL CLIENTE
* inicio
*   pedirValidarInicioSesion    //pide el DNI del cliente y el IBAN de la cuenta propia
*   repetir
*       mostrarMenuPedirValidarOpcion
*       si (opcion no es salir)
*           segun(opcion)
*               caso 1: realizar transferencia bancaria
*               caso 2: ver datos de la cuenta propia
*               caso 3: buscar movimientos
*               caso 4: cancelar cuenta
*           finSegun
*       finSi
*   mientras(opcion no sea salir)
* fin
* */
package main;

import gestion.GestionBancoComercial;
import resguardos.ResguardoGestionBancoComercial;
import utilidades.ValidacionProgramaCliente;

import java.util.GregorianCalendar;

public class ProgramaCliente {
    public static void main(String[] args) {
        ValidacionProgramaCliente validar = new ValidacionProgramaCliente();
        ResguardoGestionBancoComercial resguardo = new ResguardoGestionBancoComercial();
        GestionBancoComercial gestion = new GestionBancoComercial();
        GregorianCalendar fecha = null;
        int opcion;
        double cantidad;
        String iban_cuenta, iban_destino,concepto;
        //pedirValidarInicioSesion
        iban_cuenta = validar.inicioSesion();
        do{
            opcion = validar.menu();
            switch (opcion){
                case 1:
                    //realizar transferencia bancaria
                    System.out.println("Va a realizar una transferencia bancaria.");
                    iban_destino = validar.iban();
                    concepto = validar.concepto();
                    cantidad = validar.cantidadATransferir();
                    fecha = new GregorianCalendar();
                    gestion.realizarMovimiento(iban_cuenta,iban_destino,concepto,cantidad,fecha);
                    System.out.println("Transferencia realizada con éxito");

                    break;
                case 2:
                    //ver datos de la cuenta propia
                    System.out.println("Últimos diez movimientos de la cuenta:");
                    gestion.imprimirUltimosDiezMovimientos(iban_cuenta);

                    break;
                case 3:
                    //buscar movimientos
                    break;
                case 4:
                    //cancelar cuenta
                    break;

            }

        }while (opcion != 0);



    }
}
