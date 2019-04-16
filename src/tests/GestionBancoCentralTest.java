package tests;

import gestion.GestionBancoCentral;

public class GestionBancoCentralTest 
{

	public static void main(String[] args) 
	{
		GestionBancoCentral gestion = new GestionBancoCentral();

		gestion.realizarMovimiento("BancoCentral", "13XXXXXXX", "BancoCentral", "12XXXXXXX", "Hola", 291248, 2, 3, 1996);
		
	}

}
