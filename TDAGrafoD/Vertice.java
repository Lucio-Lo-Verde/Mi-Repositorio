package TDAGrafoD;

import TDALista.DoubleLinkedList;
import TDALista.Position;
import TDALista.PositionList;

public class Vertice<V, E> implements Vertex<V> {
	
	private V rotulo;
	private Position<Vertice<V, E>> posEnListaVertices;
	private PositionList<Arco<V, E>> adyacentes; 
	
	public Vertice(V r, Position<Vertice<V, E>> p) {
		rotulo = r;
		posEnListaVertices = p;
		adyacentes = new DoubleLinkedList<Arco<V, E>>();
	}
	public Vertice(V r) {
		this(r,null);
	}
	public Vertice(Position<Vertice<V, E>> p) {
		this(null,p);
	}
	public Vertice() {
		this(null,null);
	}

	@Override
	public V element() {
		return rotulo;
	}
	
	public Position<Vertice<V, E>> getPosicionEnListaDeVertices(){
		return posEnListaVertices;
	}
	public PositionList<Arco<V, E>> getAdyacentes() {
		return adyacentes;
	}
	
	public void setElemento(V x) {
		rotulo = x;
	}
	
	public void setPosicionEnListaDeVertices(Position<Vertice<V, E>> position) {
		posEnListaVertices = position;
	}

}
