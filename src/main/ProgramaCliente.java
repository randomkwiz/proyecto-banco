/*
* Comentario: Programa para el cliente desde donde puede realizar transferencias bancarias a otras cuentas,
*               ver el saldo de su propia cuenta, buscar un movimiento de su cuenta y cancelar su cuenta, entre otras cosas.
*
* PSEUDOCODIGO MAIN - VISTA DEL CLIENTE
* inicio
*   repetir
*       pedirValidarInicioSesion    //pide el DNI del cliente y el IBAN de la cuenta propia
*   mientras(login incorrecto)
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
public class ProgramaCliente {
    public static void main(String[] args) {

    }
}
