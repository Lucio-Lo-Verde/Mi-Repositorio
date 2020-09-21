package TDALista;

import java.util.Iterator;

import Excepciones.BoundaryViolationException;
import Excepciones.EmptyListException;
import Excepciones.InvalidPositionException;

public class ListaSimplementeEnlazada<E> implements PositionList<E> {

	private Nodo<E> head;
	private int size;
	
	public ListaSimplementeEnlazada() {
		head= null;
		size= 0;
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

		return head;
	}

	@Override
	public Position<E> last() throws EmptyListException {
		if(isEmpty())
			throw new EmptyListException("La lista esta vacia");
		Nodo<E> ultimo;
		ultimo= head;
		while(ultimo.getNext() != null)
			ultimo= ultimo.getNext();
		return ultimo;
	}

	@Override
	public Position<E> next(Position<E> p) throws InvalidPositionException, BoundaryViolationException {
		Nodo<E> nodo;
		nodo= checkPosition(p);
		if(nodo.getNext() == null)
			throw new BoundaryViolationException("next:: e: pide el siguiente al ultimo");
		
		return nodo.getNext();
	}

	@Override
	public Position<E> prev(Position<E> p) throws InvalidPositionException, BoundaryViolationException {
		Nodo<E> nodo;
		nodo= checkPosition(p);
		if(nodo == head)
			throw new BoundaryViolationException("prev:: e: pide el anterior al primero");
		Nodo<E> anterior;
		anterior= head;
		while(anterior.getNext() != nodo)
			anterior= anterior.getNext();
		return anterior;
	}

	@Override
	public void addFirst(E element) {
		Nodo<E> nuevo;
		nuevo= new Nodo<E>(element);
		nuevo.setNext(head);
		head= nuevo;
		size++;
	}

	@Override
	public void addLast(E element) {
		Nodo<E> nuevo;
		nuevo= new Nodo<E>(element);
		Nodo<E> ultimo;
		ultimo= head;
		if(!isEmpty()) {
			while(ultimo.getNext() != null)
				ultimo= ultimo.getNext();
			ultimo.setNext(nuevo);
		}
		else
			head= nuevo;
		size++;
	}

	@Override
	public void addAfter(Position<E> p, E element) throws InvalidPositionException {
		Nodo<E> nodo;
		nodo = checkPosition(p);
		Nodo<E> nuevo;
		nuevo= new Nodo<E>(element);
		nuevo.setNext(nodo.getNext());
		nodo.setNext(nuevo);
		size++;
	}

	@Override
	public void addBefore(Position<E> p, E element) throws InvalidPositionException {
		Nodo<E> nodo;
		nodo = checkPosition(p);
		Nodo<E> nuevo;
		nuevo= new Nodo<E>(element);
		Nodo<E> anterior;
		nuevo.setNext(nodo);
		if(nodo == head)
			head= nuevo;
		else {
			anterior= head;
			while(anterior.getNext() != nodo)
				anterior= anterior.getNext();
			anterior.setNext(nuevo);
		}
		size++;
	}

	@Override
	public E remove(Position<E> p) throws InvalidPositionException {
		Nodo<E> nodo;
		nodo= checkPosition(p);
		Nodo<E> anterior;
		E aux;
		aux= p.element();
		if(nodo == head)
			nodo= null;
		else {
			anterior= head;
			while(anterior.getNext() != nodo)
				anterior= anterior.getNext();
			anterior.setNext(nodo.getNext());
		}
		nodo.setElement(null);
		nodo.setNext(null);
		size--;
		return aux;
	}

	@Override
	public E set(Position<E> p, E element) throws InvalidPositionException {
		Nodo<E> nodo;
		nodo= checkPosition(p);
		E aux;
		aux= p.element();
		nodo.setElement(element);		
		return aux;
	}
	
	private Nodo<E> checkPosition(Position<E> p) throws InvalidPositionException{
		Nodo<E> aux;
		try {
			aux= (Nodo<E>) p;
		}
		catch(ClassCastException e) {
			throw new InvalidPositionException("e: Posicion invalida");
		}
		return aux;
	}

	@Override
	public Iterator<E> iterator() {
		return new ElementIterator<E>(this);
	}

	@Override
	public Iterable<Position<E>> positions() {
		PositionList<Position<E>> p;
		p= new ListaSimplementeEnlazada<Position<E>>();
		Position<E> aux;
		Position<E> ultimo;
		try {
			if(!isEmpty()) {
				aux= first();
				ultimo= last();
				while(aux != ultimo) {
					p.addLast(aux);
					aux= next(aux);
				}
				p.addLast(ultimo);
			}
		}
		catch(EmptyListException | BoundaryViolationException | InvalidPositionException e) {
			e.printStackTrace();
		}
		return p;
	}	
	
	@Override
	public PositionList<E> clone() {
		PositionList<E> aRetornar;
		aRetornar= new ListaSimplementeEnlazada<E>();
		Nodo<E> nodo;
		nodo= head;
		while(nodo != null) {
			aRetornar.addLast(nodo.element());
			nodo= nodo.getNext();
		}
		return aRetornar;
	}
	
	public PositionList<E> cloneOrdn() {
		PositionList<E> aRetornar;
		aRetornar= new ListaSimplementeEnlazada<E>();
		Nodo<E> nodo;
		Nodo<E> aux;
		if(!isEmpty()) {
			try {
				aRetornar.addFirst(head.element());
				nodo= checkPosition(aRetornar.first());
				aux= head;
				while(aux.getNext() != null) {
					aRetornar.addAfter(nodo,aux.getNext().element());
					nodo= nodo.getNext();
					aux= aux.getNext();
				}
			}
			catch(EmptyListException | InvalidPositionException e) {
				e.printStackTrace();
			}
		}
		return aRetornar;
	}
}