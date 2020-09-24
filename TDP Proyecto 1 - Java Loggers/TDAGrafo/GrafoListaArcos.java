package TDAGrafo;

import java.util.Iterator;

import Excepciones.EmptyListException;
import Excepciones.InvalidEdgeException;
import Excepciones.InvalidPositionException;
import Excepciones.InvalidVertexException;
import TDALista.DoubleLinkedList;
import TDALista.PositionList;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.Filter;

public class GrafoListaArcos<V, E> implements Graph<V,E> {
	
	protected PositionList<Vertice<V>> vertices;
	protected PositionList<Arco<V,E>> arcos;
	private static Logger logger;
	
	public GrafoListaArcos() {
		vertices = new DoubleLinkedList<Vertice<V>>();
		arcos = new DoubleLinkedList<Arco<V,E>>();
		
		if(logger == null) {
			
			logger = Logger.getLogger(GrafoListaArcos.class.getName());
			
			Handler hnd = new ConsoleHandler();
			hnd.setLevel(Level.FINE);
			logger.addHandler(hnd);
			
			logger.setLevel(Level.INFO);
			
			/*Logger rootLogger = logger.getParent();
			for(Handler h : rootLogger.getHandlers())
				h.setLevel(Level.OFF);
			*/
		}
		
	}

	@Override
	public Iterable<Vertex<V>> vertices() {
		PositionList<Vertex<V>> listaVertices = new DoubleLinkedList<Vertex<V>>();
		for(Vertice<V> v : vertices)
			listaVertices.addLast(v);
		
		logger.info("CONSULTA LISTA DE VERTICES");
		
		return listaVertices;
	}

	@Override
	public Iterable<Edge<E>> edges() {
		PositionList<Edge<E>> listaArcos = new DoubleLinkedList<Edge<E>>();
		for(Arco<V,E> a : arcos)
			listaArcos.addLast(a);
		
		return listaArcos;
	}
	
	private Vertice<V> checkVertex(Vertex<V> v) throws InvalidVertexException {
		if(v==null)
			throw new InvalidVertexException("CheckVertex: Vertice nulo");
		Vertice<V> temp = null;
		try {
			temp = (Vertice<V>) v;
		}
		catch(ClassCastException cce) {
			throw new InvalidVertexException("CheckVertex: Error de casteo");
		}
		if(temp.element()==null)
			throw new InvalidVertexException("CheckVertex: No tiene rotulo");
		if(temp.getPosicionEnListaDeVertices()==null) //No controla si esta en la lista de vertices, en ese caso quedaria en orden n
			throw new InvalidVertexException("CheckVertex: No esta en la lista de vertices");
		
		//logger.fine("vertice " + v.element() + " es valido!");
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
		if(temp.getPosicionEnListaDeArcos()==null) //No controla si esta en la lista de arcos, en ese caso quedaria en orden m
			throw new InvalidEdgeException("CheckEdge: No esta en la lista de vertices");
		return temp;
	}
	
	@Override
	public Iterable<Edge<E>> incidentEdges(Vertex<V> v) throws InvalidVertexException {
		Vertice<V> vv = checkVertex(v);
		PositionList<Edge<E>> aRetornar = new DoubleLinkedList<Edge<E>>();
		for(Arco<V,E> arco : arcos) {
			if(arco.getPred()==vv || arco.getSuc()==vv)
				aRetornar.addLast(arco);
		}
		return aRetornar;
	}

	@Override
	public Vertex<V> opposite(Vertex<V> v, Edge<E> e) throws InvalidVertexException, InvalidEdgeException {
		Vertice<V> vv = checkVertex(v);
		Arco<V,E> ee = checkEdge(e);
		Vertice<V> op = null;
		
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
		Arco<V,E> arco = checkEdge(e);
		Vertex<V>[] laterales = new Vertex[2];
		laterales[0] = arco.getPred();
		laterales[1] = arco.getSuc();
		
		return laterales;
	}

	@Override
	public boolean areAdjacent(Vertex<V> v, Vertex<V> w) throws InvalidVertexException {
		Vertice<V> vv = checkVertex(v);
		Vertice<V> vw = checkVertex(w);
		boolean sonAdyacentes = false;
		Iterator<Arco<V,E>> itListaArcos = arcos.iterator();
		Arco<V,E> cursor;
		
		while(itListaArcos.hasNext() && !sonAdyacentes) {
			cursor = itListaArcos.next();
			if((cursor.getPred()==vv && cursor.getSuc()==vw) || (cursor.getPred()==vw && cursor.getSuc()==vv))
				sonAdyacentes = true;
		}
		
		if(sonAdyacentes)
			logger.info("LOS VERTICES " + v.element() + " Y " + w.element() + " SON ADYACENTES");
		else
			logger.info("LOS VERTICES " + v.element() + " Y " + w.element() + " NO SON ADYACENTES");
		
		return sonAdyacentes;
	}

	@Override
	public V replace(Vertex<V> v, V x) throws InvalidVertexException {
		Vertice<V> vv = checkVertex(v);
		V aRetornar = vv.element();
		vv.setElemento(x);
		
		return aRetornar;
	}

	@Override
	public Vertex<V> insertVertex(V x) {
		Vertice<V> vertice = new Vertice<V>(x);
		
		vertices.addLast(vertice);
		try {
			vertice.setPosicionEnListaDeVertices(vertices.last());
		}
		catch(EmptyListException ele) {
			//System.out.println(ele.getMessage());
			logger.severe("LISTA DE VERTICES ESTA VACIA!!");
		}
		
		logger.fine("VERTICE " + x + " INSERTADO CON EXITO!");

		return vertice;
	}

	@Override
	public Edge<E> insertEdge(Vertex<V> v, Vertex<V> w, E e) throws InvalidVertexException {
		Vertice<V> vv = checkVertex(v);
		Vertice<V> vw = checkVertex(w);
		Arco<V,E> arco = new Arco<V,E>(e);
		
		arco.setPred(vv);
		arco.setSuc(vw);
		arcos.addLast(arco);
		try {
			arco.setPosicionEnListaDeArcos(arcos.last());
		}
		catch(EmptyListException ele) {
			//System.out.println(ele.getMessage());
			logger.severe("LISTA DE VERTICES ESTA VACIA!!");
		}
		
		logger.fine("ARCO " + e + " INSERTADO CON EXITO ENTRE LOS VERTICES " + v.element() + " Y " + w.element());
		
		return arco;
	}

	@Override
	public V removeVertex(Vertex<V> v) throws InvalidVertexException {
		//tiempo = m*m+n+c
		//Orden(m*m)
		Vertice<V> vv = checkVertex(v);
		V aRetornar = vv.element();
		
		for(Arco<V,E> arco : arcos) {
			if(arco.getPred()==vv || arco.getSuc()==vv) {
				try {
					arcos.remove(arco.getPosicionEnListaDeArcos());
				}
				catch (InvalidPositionException ipe) {
					//System.out.println(ipe.getMessage());
					logger.severe("LISTA DE VERTICES ESTA VACIA!!");
				}
				arco.setPred(null);
				arco.setSuc(null);
				arco.setPosicionEnListaDeArcos(null); //No es necesario
			}
		}
		
		try {
			vertices.remove(vv.getPosicionEnListaDeVertices());
		}
		catch (InvalidPositionException ipe) {
			//System.out.println(ipe.getMessage());
			logger.severe("LISTA DE VERTICES ESTA VACIA!!");
		}
		vv.setPosicionEnListaDeVertices(null); //No es necesario
		
		logger.fine("VERTICE " + v.element() + " REMOVIDO CON EXITO!");
		
		return aRetornar;
	}

	@Override
	public E removeEdge(Edge<E> e) throws InvalidEdgeException {
		Arco<V,E> arco = checkEdge(e);
		E aRetornar = arco.element();
		
		try {
			arcos.remove(arco.getPosicionEnListaDeArcos());
			arcos.remove(arco.getPosicionEnListaDeArcos());
		}
		catch (InvalidPositionException ipe) {
			//System.out.println(ipe.getMessage());
			logger.severe("LISTA DE VERTICES ESTA VACIA!!");
		}
		arco.setPred(null);
		arco.setSuc(null);
		arco.setPosicionEnListaDeArcos(null); //No es necesario
		
		logger.fine("ARCO " + e.element() + " REMOVIDO CON EXITO!");
		
		return aRetornar;
	}

}
