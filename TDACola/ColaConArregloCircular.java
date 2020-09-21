package TDACola;

import Excepciones.EmptyQueueException;

public class ColaConArregloCircular<E> implements Queue<E> {
	private E[] contenedor;
	private int ini;
	private int fin;
	private static final int LONGITUD=10;
	
	public ColaConArregloCircular() {
		contenedor= (E[]) new Object[LONGITUD];
		ini= 0;
		fin= 0;
	}

	@Override
	public int size() {
		return (contenedor.length-ini+fin) % contenedor.length;
	}

	@Override
	public boolean isEmpty() {
		return ini==fin;
	}

	@Override
	public E front() throws EmptyQueueException {
		if(isEmpty())
			throw new EmptyQueueException("La cola esta vacia");
		return contenedor[ini];
	}

	@Override
	public void enqueue(E element) {
		if (size()==contenedor.length-1) 
			agrandarEstructura();
		
		contenedor[fin]= element;
		fin= (fin+1) % contenedor.length;
	}
	
	/**
	 * Agranda la estructura.
	 */
	private void agrandarEstructura() {
		E[] aux;
		aux= (E[]) new Object[contenedor.length*2];
		int j= 0;
		while (ini!=fin) {
			aux[j]= contenedor[ini];
			ini= (ini+1) % contenedor.length;
			j++;
		}
		ini= 0;
		fin= contenedor.length-1;
		contenedor= aux;
	}

	@Override
	public E dequeue() throws EmptyQueueException {
		if(isEmpty())
			throw new EmptyQueueException("La cola esta vacia");
		E aux;
		aux= contenedor[ini];
		contenedor[ini]= null;
		ini= (ini+1) % contenedor.length;
		
		return aux;
	}

}
