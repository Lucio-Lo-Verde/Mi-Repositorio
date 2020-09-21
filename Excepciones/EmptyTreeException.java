package Excepciones;

public class EmptyTreeException extends Exception {
	/**
	 * Constructor.
	 * Crea la excepci�n EmptyTree (arbol vac�o) con un mensaje de error que recibe como par�metro.
	 * @param msg Mensaje del error que recibe como par�metro.
	 */
	public EmptyTreeException(String msg) {
		super(msg);
	}
	
}