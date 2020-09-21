package TDAMapHashAbierto;

public class Entrada<K, V> implements Entry<K, V> {
    private K key;
    private V value;
    
    /**
	 * Constructor.
	 * Crea una entrada de tipo <clave,valor>.
	 * @param key 
	 */
    public Entrada(K key, V value) {
    	this.key = key;
    	this.value=value;
    }
	@Override
	public K getKey() {
		return key;
	}

	@Override
	public V getValue() {
		return value;
	}
	/**
	 * Modifica la clave de la entrada.
	 * @param key Clave pasada por parámetro.
	 */
	public void setKey(K key) {
		this.key = key;
	}
	/**
	 * Modifica el valor de la entrada.
	 * @param value Valor pasado por parámetro.
	 */
	public void setValue(V value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return "<"+key+","+value+">";
	}

}
