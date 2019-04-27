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

import clasesBasicas.TransferenciaImpl;
import gestion.GestionBancoComercial;
//import resguardos.ResguardoGestionBancoComercial;
import utilidades.Utilidades;
import utilidades.ValidacionProgramaCliente;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class ProgramaCliente {
    public static void main(String[] args) {
        ValidacionProgramaCliente validar = new ValidacionProgramaCliente();
        //ResguardoGestionBancoComercial resguardo = new ResguardoGestionBancoComercial();
        Utilidades utilidad = new Utilidades();
        GestionBancoComercial gestion = new GestionBancoComercial();
        GregorianCalendar fecha = null;
        int opcion, dia, mes, anyo;
        double cantidad;
        String iban_cuenta, iban_destino,concepto;
        String mensajeUsuario;  //para almacenar mensajes de ayuda al usuario
        List<TransferenciaImpl> movimientos = new ArrayList<TransferenciaImpl>();

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
                    mensajeUsuario = (gestion.realizarMovimiento(iban_cuenta,iban_destino,concepto,cantidad,fecha))?"Transferencia realizada con exito":"Problemas en su operacion. Vuelva a intentarlo mas tarde";

                    System.out.println(mensajeUsuario);

                    break;
                case 2:
                    //Ver datos de la cuenta propia
                    utilidad.imprimirDatosCuenta(gestion.datosCuenta(iban_cuenta));
                    break;

                case 3:
                    //ver movimientos de la cuenta propia
                    System.out.println("Movimientos de la cuenta:");
                    gestion.ordenarMovimientosPorFecha(iban_cuenta);    //YA FUNCIONA! =D
                    utilidad.imprimirMovimientos(gestion.ultimosDiezMovimientos(iban_cuenta));

                    break;
                case 4:
                    //buscar movimientos
                    dia = validar.dia();
                    mes = validar.mes();
                    anyo = validar.anyo();
                    if(mes == 0 && dia == 0){
                       movimientos = gestion.buscarMovimientosPorFecha(iban_cuenta,anyo);
                    }else if (mes != 0 && dia == 0){
                        movimientos = gestion.buscarMovimientosPorFecha(iban_cuenta, mes,anyo);
                    }else{
                        movimientos = gestion.buscarMovimientosPorFecha(iban_cuenta,dia,mes,anyo);
                    }

                    if(movimientos.size() > 0 ){
                        utilidad.imprimirMovimientos(movimientos);
                    }else{
                        System.out.println("No existen movimientos con esas caracteristicas.");
                    }
                    break;
                case 5:
                    //cancelar cuenta
                    if(validar.borrarCuenta()){

                    	if(gestion.eliminarCuenta(iban_cuenta))
                    	{
	                        System.out.println("Su cuenta ha sido borrada.");
	                        System.out.println("Se le forzara el cierre de sesion.");
	                        opcion = 0;
                    	}
                    	else
                    	{
                    		System.out.println("Hubo un error inesperado, no se borrara su cuenta.");
                    	}

                    }else{
                        System.out.println("No se borrara su cuenta.");
                    }
                    break;

            }

        }while (opcion != 0);



    }
}
