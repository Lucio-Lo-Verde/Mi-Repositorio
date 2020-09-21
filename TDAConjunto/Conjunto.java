package TDAConjunto;

public interface Conjunto<E> {


	/**
	 * Consulta la cantidad de elementos del conjunto.
	 * @return Cantidad de elementos del conjunto.
	 */
	public int size();
	
	/**
	 * Consulta la capacidad del conjunto.
	 * @return Capacidad máxima del conjunto.
	 */
	public int capacity();

	/**
	 * Consulta si el conjunto está vacío.
	 * @return Verdadero si el conjunto está vacío, falso en caso contrario.
	 */
	public boolean isEmpty();

	/**
	 * Retorna el i-esimo elemento del conjunto. Requiere que i sea una posición válida
	 * @return Retorna el i-esimo elemento del conjunto..
	 * @throws ConjuntoVacioException si el conjunto está vacio. 
	 */
	
	public E get(int i) throws ConjuntoVacioException;
	
	/**
	 * Agrega un elemento al conjunto. Requiere que el conjunto no esté lleno
	 *
	 */
	public void put (E elem);
	
		/**
	 * Verifica si el elemento elem pertenece a la colección.
	 * @return Retorna verdadero si elem pertenece a la coleccion.
	 *
	 */
	public boolean pertenece(E elem);
	
	/**
	 * Calcula la intersección entre el conjunto que recibe el mensaje y el pasado por parámetro.
	 * @return Retorna el conjunto resultante de la intersección.
	 *
	 */
	public Conjunto<E> intersection(Conjunto<E> c);
	
}
