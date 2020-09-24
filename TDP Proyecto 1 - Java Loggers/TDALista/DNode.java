package TDALista;

public class DNode<E> implements Position<E>{
	
	private DNode<E> siguiente;
	private DNode<E> anterior;
	private E elemento;
	
	public DNode(DNode<E> anterior, E elemento, DNode<E> siguiente) {
		this.siguiente = siguiente;
		this.anterior = anterior;
		this.elemento = elemento;
	}
	public DNode(E element) {
		this(null,element,null);
	}
	public DNode() {
		this(null,null,null);
	}
	public DNode<E> getSiguiente() {
		return siguiente;
	}
	public void setSiguiente(DNode<E> siguiente) {
		this.siguiente = siguiente;
	}
	public DNode<E> getAnterior() {
		return anterior;
	}
	public void setAnterior(DNode<E> anterior) {
		this.anterior = anterior;
	}
	public E getElemento() {
		return elemento;
	}
	public void setElemento(E elemento) {
		this.elemento = elemento;
	}
	@Override
	public E element() {
		return elemento;
	}

	
}
