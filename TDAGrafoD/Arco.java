package TDAGrafoD;

import TDALista.Position;

public class Arco<V, E> implements Edge<E> {
	
	private E rotulo;
	private Vertice<V, E> sucesor;
	private Vertice<V, E> predecesor;
	private Position<Arco<V,E>> posListaAdySuc;
	private Position<Arco<V,E>> posListaAdyPred;
	
	public Arco(E rot, Position<Arco<V,E>> posPred, Position<Arco<V,E>> posSuc, Vertice<V, E> suc, Vertice<V, E> pre) {
		rotulo = rot;
		posListaAdySuc = posSuc;
		posListaAdyPred = posPred;
		sucesor = suc;
		predecesor = pre;
	}
	public Arco(E r) {
		this(r,null,null,null,null);
	}
	
	public Arco() {
		this(null,null,null,null,null);
	}

	@Override
	public E element() {
		return rotulo;
	}
	
	public Position<Arco<V,E>> getPosEnListaAdyDeSuc(){
		return posListaAdySuc;
	}
	public Position<Arco<V,E>> getPosEnListaAdyDePred(){
		return posListaAdyPred;
	}
	
	public void setElemento(E x) {
		rotulo = x;
	}
	
	public Vertice<V, E> getPred() {
		return predecesor;
	}
	
	public Vertice<V, E> getSuc() {
		return sucesor;
	}
	
	public void setPred(Vertice<V, E> v) {
		predecesor = v;
	}
	
	public void setSuc(Vertice<V, E> v) {
		sucesor = v;
	}
	public void setPosEnListaAdyDeSuc(Position<Arco<V,E>> p){
		posListaAdySuc = p;
	}
	public void setPosEnListaAdyDePred(Position<Arco<V,E>> p){
		posListaAdyPred = p;
	}

}