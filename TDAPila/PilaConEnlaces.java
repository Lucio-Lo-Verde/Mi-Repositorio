package TDAPila;

import Excepciones.EmptyStackException;

public class PilaConEnlaces<E> implements Stack<E> {

	private Nodo<E> tope;
	private int tamanio;
	
	public PilaConEnlaces() {
		tope=null;
		tamanio=0;
	}
	
	@Override
	public int size() {
		return tamanio;
	}

	@Override
	public boolean isEmpty() {
		return tamanio==0;
	}

	@Override
	public E top() throws EmptyStackException {
		if(isEmpty())
			throw new EmptyStackException("No hay Nodos");
		return tope.getElemento();
	}

	@Override
	public void push(E element) {
		Nodo<E> aux;
		aux=new Nodo<E>(element);
		aux.setSiguiente(tope);
		tope=aux;
		tamanio++;
	}

	@Override
	public E pop() throws EmptyStackException {
		if(isEmpty())
			throw new EmptyStackException("No hay Nodos");
		E aux;
		Nodo<E> nodoAux;
		
		aux=tope.getElemento();
		nodoAux=tope.getSiguiente();
		tope.setElemento(null);
		tope.setSiguiente(null);
		tope= nodoAux;
		tamanio--;
		return aux;
	}

}
