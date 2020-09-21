package TDAGrafoD;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import Excepciones.EmptyListException;
import Excepciones.InvalidEdgeException;
import Excepciones.InvalidPositionException;
import Excepciones.InvalidVertexException;
import TDAGrafoD.Arco;
import TDAGrafoD.Edge;
import TDALista.DoubleLinkedList;
import TDALista.PositionList;

public class GrafoDListaAdy<V, E> implements GraphD<V, E> {
	
	protected PositionList<Vertice<V,E>> vertices;
	
	public GrafoDListaAdy() {
		vertices = new DoubleLinkedList<Vertice<V, E>>();
	}

	@Override
	public Iterable<Vertex<V>> vertices() {
		PositionList<Vertex<V>> aRetornar = new DoubleLinkedList<Vertex<V>>();
		
		for(Vertice<V, E> ver : vertices) {
			aRetornar.addLast(ver);
		}
		
		return aRetornar;
	}

	@Override
	public Iterable<Edge<E>> edges() {
		Map<Edge<E>, Vertex<V>> mapeo = new HashMap<Edge<E>, Vertex<V>>();
		
		for(Vertice<V, E> ver : vertices) {
			for(Arco<V, E> arco : ver.getAdyacentes()) {
				mapeo.put(arco, ver);
			}
		}

		
		return mapeo.keySet();
	}
	
	private Vertice<V, E> checkVertex(Vertex<V> v) throws InvalidVertexException {
		if(v==null)
			throw new InvalidVertexException("CheckVertex: Vertice nulo");
		Vertice<V, E> temp = null;
		try {
			temp = (Vertice<V, E>) v;
		}
		catch(ClassCastException cce) {
			throw new InvalidVertexException("CheckVertex: Error de casteo");
		}
		if(temp.element()==null)
			throw new InvalidVertexException("CheckVertex: No tiene rotulo");
		if(temp.getPosicionEnListaDeVertices()==null) //No controla si esta en la lista de vertices, en ese caso quedaria en orden n
			throw new InvalidVertexException("CheckVertex: No esta en la lista de vertices");
		if(temp.getAdyacentes()==null) //No controla si esta en la lista de vertices, en ese caso quedaria en orden n
			throw new InvalidVertexException("CheckVertex: V no tiene lista de adyacentes");
		return temp;
	}
	
	private Arco<V,E> checkEdge(Edge<E> e) throws InvalidEdgeException {
		if(e==null)
			throw new InvalidEdgeException("CheckEdge: Arco nulo");
		Arco<V,E> temp = null;
		try {
			temp = (Arco<V,E>) e;
		}
		catch(ClassCastException cce) {
			throw new InvalidEdgeException("CheckEdge: Error de casteo");
		}
		if(temp.element()==null)
			throw new InvalidEdgeException("CheckEdge: El arco no tiene rotulo");
		if(temp.getPred()==null || temp.getSuc()==null)
			throw new InvalidEdgeException("CheckEdge: Arco invalido");
		if(temp.getPosEnListaAdyDeSuc()==null)
			throw new InvalidEdgeException("CheckEdge: No esta en la lista de adyacencia del vertice sucesor");
		if(temp.getPosEnListaAdyDePred()==null)
			throw new InvalidEdgeException("CheckEdge: No esta en la lista de adyacencia del vertice predecesor");
		return temp;
	}

	@Override
	public Iterable<Edge<E>> incidentEdges(Vertex<V> v) throws InvalidVertexException {
		Vertice<V, E> vv = checkVertex(v);
		PositionList<Edge<E>> arcosIncidentes = new DoubleLinkedList<Edge<E>>();
		
		for(Arco<V, E> arco : vv.getAdyacentes()) {
			arcosIncidentes.addLast(arco);
		}
		
		return arcosIncidentes;
	}

	@Override
	public Iterable<Edge<E>> succesorEdges(Vertex<V> v) throws InvalidVertexException {
		Vertice<V, E> vv = checkVertex(v);
		PositionList<Edge<E>> arcosSubyacentes = new DoubleLinkedList<Edge<E>>();
		
		for(Arco<V, E> arco : vv.getAdyacentes()) {
			if(arco.getPred()==vv)
				arcosSubyacentes.addLast(arco);
		}
		
		return arcosSubyacentes;
	}

	@Override
	public Vertex<V> opposite(Vertex<V> v, Edge<E> e) throws InvalidVertexException, InvalidEdgeException {
		Vertice<V, E> vv = checkVertex(v);
		Arco<V,E> ee = checkEdge(e);
		Vertice<V, E> op = null;
		
		if(ee.getPred()==vv)
			op = ee.getSuc();
		else if(ee.getSuc()==vv)
			op = ee.getPred();
		else
			throw new InvalidEdgeException("El vertice v no es adyacente al arco e");

		return op;
	}

	@Override
	public Vertex<V>[] endvertices(Edge<E> e) throws InvalidEdgeException {
		Arco<V,E> ee = checkEdge(e);
		Vertex<V>[] puntas = new Vertex[2];
		
		puntas[0] = ee.getPred();
		puntas[1] = ee.getSuc();
		
		return puntas;
	}

	@Override
	public boolean areAdjacent(Vertex<V> v, Vertex<V> w) throws InvalidVertexException {
		Vertice<V, E> vv = checkVertex(v);
		Vertice<V, E> ww = checkVertex(w);
		boolean sonAdyacentes = false;
		Iterator<Arco<V, E>> adyacentesDeV = vv.getAdyacentes().iterator();
		Arco<V, E> arco;
		
		while(adyacentesDeV.hasNext() && !sonAdyacentes) {
			arco = adyacentesDeV.next();
			sonAdyacentes = ((arco.getPred()==vv && arco.getSuc()==ww) || (arco.getPred()==ww && arco.getSuc()==vv));
		}
		
		return sonAdyacentes;
	}

	@Override
	public V replace(Vertex<V> v, V x) throws InvalidVertexException {
		Vertice<V, E> vv = checkVertex(v);
		V aRetornar = vv.element();
		vv.setElemento(x);
		
		return aRetornar;
	}

	@Override
	public Vertex<V> insertVertex(V x) {
		Vertice<V, E> vertice = new Vertice<V, E>(x);

		vertices.addLast(vertice);
		try {
			vertice.setPosicionEnListaDeVertices(vertices.last());
		}
		catch(EmptyListException ele) {
			System.out.println(ele.getMessage());
		}

		return vertice;

	}

	@Override
	public Edge<E> insertEdge(Vertex<V> v, Vertex<V> w, E e) throws InvalidVertexException {
		Vertice<V, E> vv = checkVertex(v);
		Vertice<V, E> ww = checkVertex(w);
		Arco<V, E> arco = new Arco<V, E>(e);
		
		arco.setPred(vv);
		arco.setSuc(ww);
		vv.getAdyacentes().addLast(arco);
		ww.getAdyacentes().addLast(arco);
		try {
			arco.setPosEnListaAdyDePred(vv.getAdyacentes().last());
			arco.setPosEnListaAdyDeSuc(ww.getAdyacentes().last());
		}
		catch(EmptyListException ele) {
			System.out.println(ele.getMessage());
		}
		
		return arco;
	}

	@Override
	public V removeVertex(Vertex<V> v) throws InvalidVertexException {
		Vertice<V, E> vv = checkVertex(v);
		V aRetornar = vv.element();
		
		for(Arco<V, E> arco : vv.getAdyacentes()) {
			
			try {
				arco.getPred().getAdyacentes().remove(arco.getPosEnListaAdyDePred());
				arco.getSuc().getAdyacentes().remove(arco.getPosEnListaAdyDeSuc());
			} 
			catch(InvalidPositionException ipe) {
				System.out.println(ipe.getMessage());
			}			
			arco.setPred(null);
			arco.setSuc(null);
			arco.setPosEnListaAdyDePred(null);
			arco.setPosEnListaAdyDeSuc(null);
			
		}
		try {
			vertices.remove(vv.getPosicionEnListaDeVertices());
		} catch (InvalidPositionException ipe) {
			System.out.println(ipe.getMessage());
		}
		
		vv.setPosicionEnListaDeVertices(null);
		
		return aRetornar;
	}

	@Override
	public E removeEdge(Edge<E> e) throws InvalidEdgeException {
		Arco<V, E> ee = checkEdge(e);
		E aRetornar = ee.element();
		
		try {
			ee.getPred().getAdyacentes().remove(ee.getPosEnListaAdyDePred());
			ee.getSuc().getAdyacentes().remove(ee.getPosEnListaAdyDeSuc());
		} 
		catch(InvalidPositionException ipe) {
			System.out.println(ipe.getMessage());
		}
		
		ee.setPred(null);
		ee.setSuc(null);
		ee.setPosEnListaAdyDePred(null);
		ee.setPosEnListaAdyDeSuc(null);
		
		return aRetornar;
	}
}
