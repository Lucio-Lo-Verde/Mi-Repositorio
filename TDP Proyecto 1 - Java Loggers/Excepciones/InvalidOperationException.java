package Excepciones;

public class InvalidOperationException extends Exception {
	/**
	 * Constructor.
	 * Crea la excepción InvalidOperation (operación inválida) con un mensaje de error que recibe como parámetro.
	 * @param msg Mensaje de error que recibe como parámetro.
	 */
	public InvalidOperationException(String msg) {
		super(msg);
	}
}
