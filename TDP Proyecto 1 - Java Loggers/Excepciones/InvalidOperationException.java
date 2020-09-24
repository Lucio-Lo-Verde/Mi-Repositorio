package Excepciones;

public class InvalidOperationException extends Exception {
	/**
	 * Constructor.
	 * Crea la excepci�n InvalidOperation (operaci�n inv�lida) con un mensaje de error que recibe como par�metro.
	 * @param msg Mensaje de error que recibe como par�metro.
	 */
	public InvalidOperationException(String msg) {
		super(msg);
	}
}
