package TDAConjunto;

public class ConjuntoArreglo<E> implements Conjunto<E> {
	
	private E[] s;	
	
	public ConjuntoArreglo(int max){
		
		s = (E[]) new Object[max];
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		int cant = 0;
		for(int i=0;i<s.length;i++) {
			if(s[i]!=null)
				cant++;
		}
		return cant;
	}

	@Override
	public int capacity() {
		// TODO Auto-generated method stub
		
		return s.length;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		
		return size()==0;
	}

	@Override
	public E get(int i) throws ConjuntoVacioException {
		// TODO Auto-generated method stub
		if(isEmpty())
			throw new ConjuntoVacioException("El conjunto esta vacio");
		
		return s[i];
	}

	@Override
	public void put(E elem) {
		// TODO Auto-generated method stub
		boolean vacio = false;
		if(size()==s.length)
			agrandar();
		
			
		for(int i=0; i<s.length && !vacio ; i++)
			if(s[i]==null) {
				s[i]=elem;
				vacio = true;
			}
					
			
		
	}
	
	private void agrandar() {
		
		 E[] nuevo = (E[]) new Object[s.length*2];
		 for(int i=0 ; i<s.length ; i++) {
			 nuevo[i]=s[i];
		 }
			 
	s = nuevo;
	}

	@Override
	public boolean pertenece(E elem) {
		// TODO Auto-generated method stub
		boolean esta = false;
		for(int i=0 ; i<s.length && !esta ; i++) {
			if(s[i].equals(elem)) {
				esta = true;
			}
		}
		return esta;
	}

	@Override
	public Conjunto<E> intersection(Conjunto<E> c) {
		// TODO Auto-generated method stub
		int menor = menor(s.length,c.size());
		Conjunto<E> interseccion = new ConjuntoArreglo(menor);
		Conjunto<E> menorC;
		Conjunto<E> mayorC;
		boolean esta = false;
		
		if(menor==s.length) {
			menorC = this;
		    mayorC = c;
		}
		else {
			menorC = c;
			mayorC = this;
		}
		for(int i=0 ; i<menor ; i++) {
			try {				
				E elem = menorC.get(i);
				esta = mayorC.pertenece(elem);
				if(esta)
					interseccion.put(elem);
			}
			catch(ConjuntoVacioException e) {
				e.getMessage();
			}
		
	}
		return interseccion;
	}

	
	private int menor(int a,int b) {
		int resultado = 0;
		if(a<=b)
			resultado = a;
		else
			resultado = b;
		
		return resultado;
	}
}
