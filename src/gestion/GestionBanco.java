package gestion;

public abstract class GestionBanco {

    abstract public String obtenerBICporNombre(String nombre_banco);
    abstract public String obtenerNombreBancoComercialPorIBAN(String iban_cuenta);
    abstract public String obtenerBICporIBAN(String IBAN);
    abstract public String obtenerNumCuentaPorIBAN(String IBAN);
    abstract public String obtenerNombrePorBIC(String bic);
}
