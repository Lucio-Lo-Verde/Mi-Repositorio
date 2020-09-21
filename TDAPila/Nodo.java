package TDAPila;

public class Nodo<E> {

	private E elemento;
	private Nodo<E> siguiente;
	
	/**
	 * Constructor.
	 * Crea un nodo.
	 * @param item Elemento pasado por parámetro.
	 * @param sig Nodo pasado por parámetro.
	 */
	public Nodo(E item, Nodo<E> sig) {
		elemento=item;
		siguiente=sig;		
	}
	public Nodo(E item) {
		this(item,null);
	}
	
	/**
	 * Modifica el elemento del nodo.
	 * @param item Elemento pasado por parámetro.
	 */
	public void setElemento(E elem) {
		elemento=elem;
	}
	/**
	 * Modifica la referencia del nodo siguiente a éste nodo.
	 * @param sig Nodo pasado por parámetro.
	 */
	public void setSiguiente(Nodo<E> sig) {
		siguiente=sig;
	}
	
	/**
	 * Consulta el elemento del nodo.
	 * @return Elemento del nodo.
	 */
	public E getElemento() {
		return elemento;
	}
	/**
	 * Consulta una referencia al nodo siguiente de éste nodo.
	 * @return Referencia al nodo siguiente de éste nodo.
	 */
	public Nodo<E> getSiguiente() {
		return siguiente;
	}
}
