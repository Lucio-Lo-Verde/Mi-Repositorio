package TDALista;

import java.util.Iterator;

import Excepciones.BoundaryViolationException;
import Excepciones.EmptyListException;
import Excepciones.InvalidPositionException;

public class DoubleLinkedList<E> implements PositionList<E> {
	
	private DNode<E> header;
	private DNode<E> trailer;
	private int size;
	
	/**
	 * Constructor.
	 * Crea una lista vacía con nodos centinelas.
	 */
	public DoubleLinkedList() {
		header= new DNode<E>(null);
		trailer= new DNode<E>(header,null,null);
		header.setSiguiente(trailer);
		size=0;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size==0;
	}

	@Override
	public Position<E> first() throws EmptyListException {
		if(isEmpty())
			throw new EmptyListException("La lista esta vacia");
		return header.getSiguiente();
	}

	@Override
	public Position<E> last() throws EmptyListException {
		if(isEmpty())
			throw new EmptyListException("La lista esta vacia");
		return trailer.getAnterior();
	}

	@Override
	public Position<E> next(Position<E> p) throws InvalidPositionException, BoundaryViolationException {
		DNode<E> nodo = checkPosition(p);
		if(nodo==trailer.getAnterior())
			throw new  BoundaryViolationException("Pide siguiente al ultimo");
		return nodo.getSiguiente();
	}

	/**
	 * Evalúa si la posición pasada por parámetro es válida
	 * @param p Posición a evaluar.
	 * @return Nodo de la posición pasada por parámetro.
	 * @throws InvalidPositionException si la posición pasada por parámetro no es válida.
	 */
	 private DNode<E> checkPosition(Position<E> p)throws InvalidPositionException{
	    	if(p==null)
	    	  throw new InvalidPositionException("Posicion invalida-Nodo nulo");
	    	if(p==header)
	      	  throw new InvalidPositionException("Posicion invalida-Header no es utilizable");
	    	if(p==trailer)
	      	  throw new InvalidPositionException("Posicion invalida-Trailer no es utilizable");
	    	try {
	    		DNode<E> temp = (DNode<E>)p;
	    		if((temp.getAnterior()==null)||(temp.getSiguiente()==null))
	    			throw new InvalidPositionException("La posicion no pertenece a la lista");
	    		return temp;
	    	}catch(ClassCastException e) {
	    		throw new InvalidPositionException("Error de casteo");
	    	}
	    }
	@Override
	public Position<E> prev(Position<E> p) throws InvalidPositionException, BoundaryViolationException {
		DNode<E> nodo = checkPosition(p);
		if(nodo==header.getSiguiente())
			throw new BoundaryViolationException("Pide anterior al primero");
		return nodo.getAnterior();
	}

	@Override
	public void addFirst(E element) {
		DNode<E> nodo;
		nodo = new DNode<E>(header,element,header.getSiguiente());
		header.getSiguiente().setAnterior(nodo);
		header.setSiguiente(nodo);
		size++;
	}

	@Override
	public void addLast(E e) {
		DNode<E> nodo;
		nodo = new DNode<E>(trailer.getAnterior(),e,trailer);
		trailer.getAnterior().setSiguiente(nodo);
		trailer.setAnterior(nodo);
		size++;
	}

	@Override
	public void addAfter(Position<E> p, E element) throws InvalidPositionException {
		DNode<E> nodo; 
		nodo = checkPosition(p);
		DNode<E> nuevo;
		nuevo= new DNode<E>(element);
		nuevo.setAnterior(nodo);
		nuevo.setSiguiente(nodo.getSiguiente());
		nodo.getSiguiente().setAnterior(nuevo);
		nodo.setSiguiente(nuevo);
		size++;
	}

	@Override
	public void addBefore(Position<E> p, E element) throws InvalidPositionException {
		DNode<E> nodo;
		nodo= checkPosition(p);
		DNode<E> nuevo;
		nuevo= new DNode<E>(element);
		nuevo.setSiguiente(nodo);
		nuevo.setAnterior(nodo.getAnterior());
		nodo.getAnterior().setSiguiente(nuevo);
		nodo.setAnterior(nuevo);
		size++;
	}

	@Override
	public E remove(Position<E> p) throws InvalidPositionException {
		DNode<E> nodo;
		nodo= checkPosition(p);
		E aux;
		aux= nodo.element();
		nodo.getAnterior().setSiguiente(nodo.getSiguiente());
		nodo.getSiguiente().setAnterior(nodo.getAnterior());
		nodo.setElemento(null);
		nodo.setAnterior(null);
		nodo.setSiguiente(null);
		size--;
		return aux;
	}

	@Override
	public E set(Position<E> p, E element) throws InvalidPositionException {
		DNode<E> nodo;
		nodo= checkPosition(p);
		E aux;
		aux= nodo.element();
		nodo.setElemento(element);
		return aux;
	}

	@Override
	public Iterator<E> iterator() {
		return new ElementIterator<E>(this);
	}

	@Override
	public Iterable<Position<E>> positions() {
		PositionList<Position<E>> nuevo= new DoubleLinkedList<Position<E>>();
		DNode<E> aux = header.getSiguiente();
	 
		if(!isEmpty()) {
			while(aux!=trailer) {
				nuevo.addLast(aux);
				aux = aux.getSiguiente();
			}
	    }
		return nuevo;
	}
}
