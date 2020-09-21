package TDAArbol;

import TDALista.DoubleLinkedList;
import TDALista.Position;
import TDALista.PositionList;

public class TNodo<E> implements Position<E> {
	
	private TNodo<E> parent;
	private E element;
	private PositionList<TNodo<E>> children;
	
	public TNodo(TNodo<E> padre, E elemento) {
		parent = padre;
		element = elemento;
		children = new DoubleLinkedList<TNodo<E>>();
	}
	public TNodo(E elemento) {
		this(null,elemento);
	}
	
	public void setPadre(TNodo<E> padre) {
		parent = padre;
	}
	public void setElemento(E elemento) {
		element = elemento;
	}
	public TNodo<E> getPadre(){
		return parent;
	}
	public PositionList<TNodo<E>> getHijos(){
		return children;
	}
	@Override
	public E element() {
		return element;
	}

}
