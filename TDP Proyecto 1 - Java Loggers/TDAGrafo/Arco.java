package TDAGrafo;

import TDALista.Position;

public class Arco<V, E> implements Edge<E> {
	
	protected E rotulo;
	protected Position<Arco<V,E>> posEnListaArcos;
	protected Vertice<V> cola;
	protected Vertice<V> punta;
	
	public Arco(E rot, Position<Arco<V,E>> pos, Vertice<V> col, Vertice<V> pun) {
		rotulo = rot;
		posEnListaArcos = pos;
		cola = col;
		punta = pun;
	}
	public Arco(E r) {
		this(r,null,null,null);
	}
	
	public Arco() {
		this(null,null,null,null);
	}

	@Override
	public E element() {
		return rotulo;
	}
	
	public Position<Arco<V,E>> getPosicionEnListaDeArcos(){
		return posEnListaArcos;
	}
	
	public void setElemento(E x) {
		rotulo = x;
	}
	
	public void setPosicionEnListaDeArcos(Position<Arco<V, E>> position) {
		posEnListaArcos = position;
	}
	
	public Vertice<V> getPred() {
		return cola;
	}
	
	public Vertice<V> getSuc() {
		return punta;
	}
	
	public void setPred(Vertice<V> v) {
		cola = v;
	}
	
	public void setSuc(Vertice<V> v) {
		punta = v;
	}

}