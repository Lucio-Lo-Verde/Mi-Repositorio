package TDALista;

import java.util.Iterator;

import Excepciones.BoundaryViolationException;
import Excepciones.EmptyListException;
import Excepciones.InvalidPositionException;

public class ListaDoblementeEnlazada<E> implements PositionList<E> {
	
	private DNode<E> header;
	private DNode<E> trailer;
	private int size;
	
	public ListaDoblementeEnlazada() {
		header= new DNode<E>(null,null,null);
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
/*
	private DNode<E> checkPosition(Position<E> p) throws InvalidPositionException {
		if(isEmpty())
			throw new InvalidPositionException("La lista esta vacia");
		if(p==null)
			throw new InvalidPositionException("La lista esta vacia");
		if(p==header)
			throw new InvalidPositionException("La lista esta vacia");
		if(p==trailer)
			throw new InvalidPositionException("La lista esta vacia");
		DNode<E> aux = null;
		try {
			aux = (DNode<E>) p;
			
			if(aux.getAnterior()==header || aux.getSiguiente()==trailer)
				throw new InvalidPositionException("Posicion invalida");
			
		}
		catch(ClassCastException e){
			throw new InvalidPositionException("Error de casteo");
			
		}
		return aux;
	}
*/
	 private DNode<E> checkPosition(Position<E> p)throws InvalidPositionException{
	    	if(p==null)
	    	  throw new InvalidPositionException("Posición invalida-Nodo nulo");
	    	if(p==header)
	      	  throw new InvalidPositionException("Posición invalida-Header no es utilizable");
	    	if(p==trailer)
	      	  throw new InvalidPositionException("Posición invalida-Trailer no es utilizable");
	    	try {
	    		DNode<E> temp = (DNode<E>)p;
	    		if((temp.getAnterior()==null)||(temp.getSiguiente()==null))
	    			throw new InvalidPositionException("Error");
	    		return temp;
	    	}catch(ClassCastException e) {
	    		throw new InvalidPositionException("error");
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
		aux= nodo.getElemento();
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
		aux= nodo.getElemento();
		nodo.setElemento(element);
		return aux;
	}

	@Override
	public Iterator<E> iterator() {
		return new ElementIterator<E>(this);
	}
/*
	@Override
	public Iterable<Position<E>> positions() {
		PositionList<Position<E>> aRetornar;
		aRetornar= new ListaDoblementeEnlazada<Position<E>>();
		Position<E> p;
		if(!isEmpty()) {
			try {
				p= first();
				while(p != last()) {
					aRetornar.addLast(p);
					p= next(p);
				}
				//si la lista esta vacia?
				aRetornar.addLast(p);
			}
			catch(EmptyListException | BoundaryViolationException | InvalidPositionException e) {
				e.printStackTrace();
			}
		}
		return aRetornar;
	}
	*/
	@Override
	public Iterable<Position<E>> positions() {
		PositionList<Position<E>> nuevo= new ListaDoblementeEnlazada<Position<E>>();
		DNode<E> aux = header.getSiguiente();
	 
	   if(!isEmpty()) {
	    while(aux!=trailer) {
	    	nuevo.addLast(aux);
	    	aux = aux.getSiguiente();
	    }
	    }
		return nuevo;
	}
	
	@Override
	public PositionList<E> clone(){
		PositionList<E> aRetornar;
		aRetornar= new ListaDoblementeEnlazada<E>();
		Position<E> p;
		
		if(!isEmpty()) {
			try {
				p= first();
				while(p != last()) {
					aRetornar.addLast(p.element());
					p= next(p);
				}
				aRetornar.addLast(p.element());
			}
			catch(EmptyListException | BoundaryViolationException | InvalidPositionException e) {
				e.printStackTrace();
			}
		}
		return aRetornar;
	}

}
