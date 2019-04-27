package utilidades;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/* Redefinici�n de la clase ObjectOuputStream para que no escriba una cabecera
        * al inicio del Stream.
        */
public class MyObjectOutputStream extends ObjectOutputStream
{
    /*Constructor que recibe OutputStream */
    public MyObjectOutputStream(OutputStream out) throws IOException
    {
        super(out);
    }

    /* Constructor sin par�metros */
    protected MyObjectOutputStream() throws IOException, SecurityException
    {
        super();
    }

    /* Redefinici�n del m�todo de escribir la cabecera para que no haga nada. */
    protected void writeStreamHeader() throws IOException
    {
    }

}