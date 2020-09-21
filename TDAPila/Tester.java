package TDAPila;

import Excepciones.EmptyStackException;

public class Tester {

	public static void main(String[] args) {
		PilaArreglo<Integer> p;
		p= new PilaArreglo<Integer>();
		
		p.push(1);
		p.push(2);
		p.push(3);
		p.push(4);
		p.push(5);
		
		System.out.println("Pila:");
		for(int i=0 ; i<5 ; i++)
			System.out.println(p.getElemento(i));
		
		try {
			p.invertir(p);
		}
		catch(EmptyStackException e){
			System.out.println("La pila esta vacia");
		}
		System.out.println();
		System.out.println("Pila invertida:");
		for(int i=0 ; i<5 ; i++)
			System.out.println(p.getElemento(i));
	}

}
