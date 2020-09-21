package TDAPila;

import Excepciones.EmptyStackException;

public class PilaArreglo<E> implements Stack<E> {

	private E [] p;
	private int tope;
	private static final int longitud=10;
	
	public PilaArreglo() {
		p = (E[]) new Object[longitud];
		tope = 0;
	}
	
	@Override
	public int size() {
		return tope;
	}

	@Override
	public boolean isEmpty() {
		return tope==0;
	}

	@Override
	public E top() throws EmptyStackException {
		if(isEmpty())
			throw new EmptyStackException("La pila esta vacia");		
		return p[tope-1];
	}

	@Override
	public void push(E element) {
		if(tope==p.length) 
			agrandar();
		p[tope]=element;
		tope++;
	}
	
	private void agrandar() {
		E[] aux;
		aux = (E[]) new Object[p.length*2];
		for(int i=0 ; i<tope ; i++)
			aux[i]=p[i];
		p= aux;
	}

	@Override
	public E pop() throws EmptyStackException {
		if(isEmpty())
			throw new EmptyStackException("La pila esta vacia");
		E aux=p[tope-1];
		p[tope-1]=null;
		tope--;
		return aux;
	}
	
	public void invertir(PilaArreglo<E> P) throws EmptyStackException {
		if(P.isEmpty())
			throw new EmptyStackException("La pila esta vacia");
		Stack<E> otrapila1 , otrapila2;
		otrapila1= new PilaArreglo<E>();
		otrapila2= new PilaArreglo<E>();

		while(!P.isEmpty())
			otrapila1.push(P.pop());
		while(!otrapila1.isEmpty())
			otrapila2.push(otrapila1.pop());
		while(!otrapila2.isEmpty())
			P.push(otrapila2.pop());
		
	}
	
	private boolean checkIndex(int pos) {
		boolean aRetornar= false;
		if(pos>=0 && pos<tope)
			aRetornar= true;
		return aRetornar;
	}
	
	public E getElemento(int pos) {
		E aRetornar= null;
		if(checkIndex(pos))
			aRetornar= p[pos];
		return aRetornar;
	}

}
