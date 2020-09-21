package TDACola;

import Excepciones.EmptyQueueException;

public class ColaConPila<E> implements Queue<E> {
	private E[] p;
	private int tail;
	private int tamanio;
	private static final int longitud=10;
	
	public ColaConPila() {
		p= (E[]) new Object[longitud];
		tail= 0;
		tamanio= 0;
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
	public E front() throws EmptyQueueException {
		if(isEmpty())
			throw new EmptyQueueException("La pila esta vacia");
		
		return p[tail-1];
	}

	@Override
	public void enqueue(E element) {
		if(tamanio==p.length-1)
			agrandarEstructura();
		p[tail]= element;
		tail++;

	}
	
	private void agrandarEstructura() {
		E[] aux;
		aux= (E[]) new Object[p.length*2];
		int j= 0;
		for(int i=0 ; i<tail ; i++) {
			aux[j]= p[i];
			j++;
		}
		
		p= aux;
	}

	@Override
	public E dequeue() throws EmptyQueueException {
		if(isEmpty())
			throw new EmptyQueueException("La pila esta vacia");
		
		E aux;
		tail--;
		aux= p[tail];
		p[tail]= null;
		return aux;
	}

}
