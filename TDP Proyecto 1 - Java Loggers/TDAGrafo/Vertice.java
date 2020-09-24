package TDAGrafo;

import TDALista.Position;

public class Vertice<V> implements Vertex<V> {
	
	protected V rotulo;
	protected Position<Vertice<V>> posEnListaVertices; 
	
	public Vertice(V r, Position<Vertice<V>> p) {
		rotulo = r;
		posEnListaVertices = p;
	}
	public Vertice(V r) {
		this(r,null);
	}
	public Vertice(Position<Vertice<V>> p) {
		this(null,p);
	}
	public Vertice() {
		this(null,null);
	}

	@Override
	public V element() {
		return rotulo;
	}
	
	public Position<Vertice<V>> getPosicionEnListaDeVertices(){
		return posEnListaVertices;
	}
	
	public void setElemento(V x) {
		rotulo = x;
	}
	
	public void setPosicionEnListaDeVertices(Position<Vertice<V>> position) {
		posEnListaVertices = position;
	}

}
