package tests;

import gestion.GestionBancoCentral;

public class GestionBancoCentralTest 
{

	public static void main(String[] args) 
	{
		GestionBancoCentral gestion = new GestionBancoCentral();

		gestion.realizarMovimiento("ESPCAIXESBBXXX12XXXXXXX", "ESPBSCHESMMXXX13XXXXXXX", "Ingreso de prueba", 291248, 2, 3, 1996);
		
	}

}
