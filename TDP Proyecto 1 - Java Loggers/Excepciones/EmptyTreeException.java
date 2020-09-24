package Excepciones;

public class EmptyTreeException extends Exception {
	/**
	 * Constructor.
	 * Crea la excepción EmptyTree (arbol vacío) con un mensaje de error que recibe como parámetro.
	 * @param msg Mensaje del error que recibe como parámetro.
	 */
	public EmptyTreeException(String msg) {
		super(msg);
	}
	
}