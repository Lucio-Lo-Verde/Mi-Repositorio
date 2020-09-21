package TDAMapHashAbierto;

import java.util.Iterator;

import Excepciones.InvalidKeyException;
import Excepciones.BoundaryViolationException;
import Excepciones.EmptyListException;
import Excepciones.InvalidPositionException;
import Excepciones.InvalidValueException;
import TDALista.ListaDoblementeEnlazada;
import TDALista.Position;
import TDALista.PositionList;


public class MapeoHashAbierto<K,V> implements Map<K,V> {
	private int size;
	private int tam;
	private PositionList<Entrada<K,V>> [] contenedor; 
	private static final float FACTOR= 0.5f;
	
	/**
	 * Constructor.
	 * Crea un mapeo de entradas.
	 * @param primo Longitud del contenedor.
	 */
	public MapeoHashAbierto(int primo) {
		tam=primo;
		size=0;
		contenedor = new PositionList[tam];
		for(int i=0 ; i<tam ; i++) {
			contenedor[i] = new ListaDoblementeEnlazada<Entrada<K,V>>();
		}
	}
	/**
	 * Constructor.
	 * Crea un mapeo de entradas con la longitud del contenedor por defecto.
	 */
	public MapeoHashAbierto() {
		this(11);
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size==0;
	}
	/**
	 * Evalúa si la clave pasada por parámetro es válida.
	 * @param key Clave a evaluar.
	 * @throws InvalidKeyException si la clave pasada por parámetro no es válida.
	 */
	private void checkKey(K key) throws InvalidKeyException {
		if(key==null)
			throw new InvalidKeyException("Clave invalida");
	}

	@Override
	public V get(K key) throws InvalidKeyException {
		checkKey(key);
		V valor = null;
		Iterator<Entrada<K,V>> itlista = contenedor[funcionHash(key)].iterator();
		boolean esta = false;
		Entrada<K,V> entrada = null;
		while(itlista.hasNext() && !esta) {
			entrada = itlista.next();
			if(entrada.getKey().equals(key)) {
				esta = true;
				valor = entrada.getValue();
			}
		}
		
		return valor;
	}
	/**
	 * Calcula el número del bucket respectivo a la clave pasada por parámetro.
	 * @param key Clave pasada por parámetro.
	 * @return Número del bucket respectivo a la clave pasada por parámetro.
	 * @throws InvalidKeyException si la clave pasada por parámetro no es válida.
	 */
	private int funcionHash(K key) throws InvalidKeyException{
		checkKey(key);
		return Math.abs(key.hashCode()) % tam;
	}

	@Override
	public V put(K key, V value) throws InvalidKeyException {
		checkKey(key);
		Iterator<Entrada<K,V>> itlista = contenedor[funcionHash(key)].iterator();
		boolean esta = false;
		V valor = null;
		Entrada<K,V> entrada = null;
		while(itlista.hasNext() && !esta) {
			entrada = itlista.next();
			if(entrada.getKey().equals(key)) {
				esta= true;
				valor= entrada.getValue();
				entrada.setValue(value);
			}			
			
		}
		if(!esta) {
			contenedor[funcionHash(key)].addLast(new Entrada<K,V>(key,value));
			size++;
		}
		if((size/tam) >= FACTOR)
			reHash();
		return valor;
	}
	
	/**
	 * Agranda y reacomoda la estructura.
	 */
	private void reHash() {
	Iterable<Entry<K,V>> lista;
	lista = entries();
	int p = siguientePrimo(tam);
	contenedor = new PositionList[p];
	size= 0;
	tam= p;
	
	
	for(int i=0 ; i<p ; i++) 
		contenedor[i] = new ListaDoblementeEnlazada<Entrada<K,V>>();
	try {
		for(Entry<K,V> entrada : lista) {
			contenedor[funcionHash(entrada.getKey())].addLast(new Entrada<K,V>(entrada.getKey(),entrada.getValue()));
			size++;
		}
	}
	catch(InvalidKeyException e) {
		System.out.println(e.getMessage());
	}
	}
	
	/**
	 * Decide si el número pasado por parámetro es un número primo.
	 * @param primo Número pasado por parámetro.
	 * @return Verdadero si el número pasado por parámetro es un número primo, caso contrario retorna falso.
	 */
	  private boolean esPrimo(int primo) {
	    	boolean esPrimo = true;
	    	
	    	for(int i=2; i*i<=primo && esPrimo; i++) {
	    		esPrimo = primo % i!=0;
	    	}
	    	return esPrimo;
	    }
	  
	  /**
	   * Retorna el siguiente número primo más cercano al número pasado por parámetro.
	   * @param primo Número pasado por parámetro.
	   * @return Siguiente número primo más cercano al número pasado por parámetro.
	   */
	  private int siguientePrimo(int primo) {
		int num;
		
		if(primo%2==0 || primo==1) 
			num = primo +1;
		else
			num = primo +2;
	       
	    return siguientePrimoAux(num);
	  }
	    
	    private int siguientePrimoAux(int num) {	        
	        int aRetornar;
	        if(esPrimo(num))
	        	aRetornar = num;
	        else
	        	aRetornar = siguientePrimoAux(num+2);
	        
	    	return aRetornar;
	    }

	@Override
	public V remove(K key) throws InvalidKeyException {
		checkKey(key);
		boolean existe = false;
		V aRetornar =null;
		Position<Entrada<K,V>> pos;
		if(!contenedor[funcionHash(key)].isEmpty()) {
			try {
				pos = contenedor[funcionHash(key)].first();
				while(!existe && pos!=null) {
					if(pos.element().getKey().equals(key)) {
						aRetornar = contenedor[funcionHash(key)].remove(pos).getValue();
						existe = true;
						size--;
					}
					else {
						if(pos==contenedor[funcionHash(key)].last())
							pos = null;
						else
							pos=contenedor[funcionHash(key)].next(pos);
					}
				}
			}
			catch (EmptyListException | InvalidPositionException | BoundaryViolationException e) {
				System.out.println(e.getMessage());
			}
		}
		return aRetornar;
	}

	
/*VERSION SERGIO ANTOZZI
 @Override
	public V remove(K key) throws InvalidKeyException {
       checkKey(key);
       PositionList<Entrada<K,V>> lista= contenedor[funcionHash(key)];
       Iterable<Position<Entrada<K,V>>> itPos = lista.positions();
	   Iterator<Position<Entrada<K,V>>> itListPos = itPos.iterator();
	   boolean esta=false;
	   V aRetornar = null;
	   Position<Entrada<K,V>> pos=null;
	   while(itListPos.hasNext()&&!esta) {
		   pos= itListPos.next();
		   if(pos.element().getKey().equals(key)) {
			   aRetornar= pos.element().getValue();
			   esta=true;
			    try {
				lista.remove(pos);
				size--;
			} catch (InvalidPositionException e) {
                System.out.println(e.getMessage());
			}
			   
		   }
	   }
		return aRetornar;
	}
*/
	
	@Override
	public Iterable<K> keys() {
		PositionList<K> lista;
		lista = new ListaDoblementeEnlazada<K>();
		for(int i=0 ; i<tam ; i++) {
			for(Entrada<K,V> entrada : contenedor[i])
				lista.addLast(entrada.getKey());
		}
		return lista;
	}

	@Override
	public Iterable<V> values() {
		PositionList<V> lista;
		lista = new ListaDoblementeEnlazada<V>();
		for(int i=0 ; i<tam ; i++) {
			for(Entrada<K,V> entrada : contenedor[i])
				lista.addLast(entrada.getValue());
		}
		return lista;
	}

	@Override
	public Iterable<Entry<K,V>> entries() {
		  PositionList<Entry<K,V>> listaEntry = new ListaDoblementeEnlazada<Entry<K,V>>();
	      for(int i=0;i<tam;i++) {
	    	  for(Entrada<K,V> entrada : contenedor[i]) {
	    		  listaEntry.addLast(entrada);
	    	  }
	      }
			return listaEntry;
	}
	
	public PositionList<Entry<K,V>> Ejercicio2 (Map<K,V> M1, Map<K,V> M2) {
		Iterable<Entry<K,V>> listaIt2 = M2.entries();
		Iterator<Entry<K,V>> lista1 = M1.entries().iterator();
		Iterator<Entry<K,V>> lista2 = listaIt2.iterator();
		PositionList<Entry<K,V>> aRetornar;
		aRetornar = new ListaDoblementeEnlazada<Entry<K,V>>();
		Entry<K,V> p1, p2;
		p1 = null;
		p2 = null;
		boolean esta = false;
		
		while(lista1.hasNext() && lista2.hasNext()) {
			p1 = lista1.next();
			while(lista2.hasNext() && !esta) {
				p2 = lista2.next();
				if(p1.getKey().equals(p2.getKey())) {
					esta = true;
					if(!p1.getValue().equals(p2.getValue())) {
						aRetornar.addLast(p1);
						aRetornar.addLast(p2);
					}
				}
			}
			lista2 = listaIt2.iterator();
			esta = false;
		}
		return aRetornar;
	}
	
	public Map<V,K> crearMapeoInverso(Map<K,V> D) throws InvalidValueException {
		Iterable<Entry<K,V>> lista = D.entries();
		Map<V,K> aRetornar = new MapeoHashAbierto<V,K>();
		
		for(Entry<K,V> entrada : lista) {
			if(entrada.getValue()==null)
				throw new InvalidValueException("Valor nulo");
			try {
				aRetornar.put(entrada.getValue(),entrada.getKey());
			} catch (InvalidKeyException e) {
				System.out.println(e.getMessage());
			}			
		}
		return aRetornar;
	}
	
	   public Map<V,K> crearMapeoInvertido (Map<K,V> D)throws InvalidValueException{
		   Iterable<Entry<K,V>> itEntry =D.entries();
		   Map<V,K> mapeo = new MapeoHashAbierto<V,K>();
		   for(Entry<K,V> ent : itEntry) {
			   if(ent.getValue()==null)
				   throw new InvalidValueException("Valor nulo");
			   try {
				   mapeo.put(ent.getValue(), ent.getKey());
			   }catch(InvalidKeyException e) {
				   System.out.println(e.getMessage());
			   }
		   }
		   return mapeo;
		   
	   }
	
}

