package tests;

import gestion.GestionBanco;
import gestion.GestionBancoComercial;

public class GestionBancoTest {

	public static void main(String[] args) 
	{
		GestionBanco gestion = new GestionBancoComercial();
		
		gestion.actualizarFichero("./Files/BancoCentral/Cuentas_BancoCentral", 0);

	}

}
