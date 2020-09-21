package TDADiccionario;

import java.util.Iterator;

import Excepciones.BoundaryViolationException;
import Excepciones.EmptyListException;
import Excepciones.InvalidEntryException;
import Excepciones.InvalidKeyException;
import Excepciones.InvalidPositionException;
import TDALista.ListaDoblementeEnlazada;
import TDALista.Position;
import TDALista.PositionList;

public class DiccionarioHashAbierto<K,V> implements Dictionary<K,V> {
	private int size;
	private int tam;
	private PositionList<Entrada<K,V>> [] contenedor;
	private static final float FACTOR= 0.5f;
	
	public DiccionarioHashAbierto() {
		tam= 119999;
		size= 0;
		contenedor = new PositionList[tam];
		for(int i=0 ; i<tam ; i++) 
			contenedor[i] = new ListaDoblementeEnlazada<Entrada<K,V>>();
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size==0;
	}
	
	private int hashThisKey(K key) throws InvalidKeyException {
		checkKey(key);
		return Math.abs(key.hashCode()) % tam;
	}
	
	private void checkKey(K key) throws InvalidKeyException {
		if(key==null)
			throw new InvalidKeyException("Clave invalida");
	}
	
	private boolean esPrimo(int primo) {
    	boolean esprimo = true;
    	for(int i=2; i<primo&&esprimo;i++) {
    		esprimo= !(primo % i==0);
    	}
    	return esprimo;
    }
    private int siguientePrimo(int primo) {
        int cont = primo +1;
    	while(!esPrimo(cont)) {
    		cont ++;
    	}
    	return cont;
    }
	@Override
	public Entry<K, V> find(K key) throws InvalidKeyException {
		checkKey(key);
		PositionList<Entrada<K,V>> lista;
		lista = contenedor[hashThisKey(key)];
		boolean esta= false;
		Position<Entrada<K,V>> pos;
		Entry<K,V> aRetornar= null;
		if(!lista.isEmpty()) {
			try {
				pos= lista.first();
				while(!esta && pos!=lista.last()) {
					if(pos.element().getKey().equals(key)) {
						esta= true;
						aRetornar= pos.element();
					}
					pos= lista.next(pos);
				}
				if(pos.element().getKey().equals(key)) 
					aRetornar= pos.element();
			}
			catch(EmptyListException | InvalidPositionException | BoundaryViolationException e) {
				throw new InvalidKeyException("La clave no pertenece al bucket");
			}
		}
		return aRetornar;
	}

	@Override
	public Iterable<Entry<K, V>> findAll(K key) throws InvalidKeyException {
		checkKey(key);
		PositionList<Entrada<K,V>> lista;
		lista= contenedor[hashThisKey(key)];
		Position<Entrada<K,V>> pos;
		PositionList<Entry<K,V>> aRetornar;
		aRetornar = new ListaDoblementeEnlazada<Entry<K,V>>();
		if(!lista.isEmpty()) {
			try {
				pos= lista.first();
				while(pos != lista.last()) {
					if(pos.element().getKey().equals(key))
						aRetornar.addLast(pos.element());
					pos= lista.next(pos);
				}
				if(pos.element().getKey().equals(key))
					aRetornar.addLast(pos.element());				
			}
			catch(EmptyListException | InvalidPositionException | BoundaryViolationException e) {
				throw new InvalidKeyException("La clave no pertenece al bucket");
			}
		}
		return aRetornar;
	}

	@Override
	public Entry<K, V> insert(K key, V value) throws InvalidKeyException {
		checkKey(key);
		Entrada<K,V> nueva;
		nueva= new Entrada<K,V>(key,value);
		
		if(((size+1)/tam) >= FACTOR)
			reHash();
		
		contenedor[hashThisKey(key)].addLast(nueva);
		size ++;
		
		return nueva;
	}

	private void reHash() {
		int primo= siguientePrimo(tam);
		Iterable<Entry<K, V>> lista;
		lista= entries();
		contenedor= new PositionList[primo];
		for(int i=0 ; i<primo ; i++)
			contenedor[i]= new ListaDoblementeEnlazada<Entrada<K,V>>();
		size= 0;
		tam= primo;
		try {
			for(Entry<K,V> entrada : lista) {
				contenedor[hashThisKey(entrada.getKey())].addLast(new Entrada<K,V>(entrada.getKey(),entrada.getValue()));
				size++;
			}
		}
		catch(InvalidKeyException e) {
			e.printStackTrace();
		}
	}
	
	private void checkEntry(Entry<K,V> entry) throws InvalidEntryException {
		if(entry==null)
			throw new InvalidEntryException("Entrada nula");
	}

	@Override
	public Entry<K, V> remove(Entry<K, V> entry) throws InvalidEntryException {
		Entry<K,V> aRetornar= null;
		PositionList<Entrada<K, V>> lista;
		Position<Entrada<K,V>> pos;
		boolean esta= false;
		try {
			checkEntry(entry);
			lista= contenedor[hashThisKey(entry.getKey())];
			if(!lista.isEmpty()) {
				pos= lista.first();
				while(!esta && pos != lista.last()) {
					if(pos.element().equals(entry)) {
						esta= true;
						aRetornar= pos.element();
						lista.remove(pos);
						size--;
					}
						pos= lista.next(pos);
				}
				if(!esta && (pos.element().equals(entry))) {
					esta= true;
					aRetornar= pos.element();
					lista.remove(pos);
				}
			}
		}
		catch(InvalidKeyException | InvalidEntryException e) {
			throw new InvalidEntryException("Entrada nula");
		}
		catch(EmptyListException | InvalidPositionException | BoundaryViolationException e) {
			throw new InvalidEntryException("El bucket esta vacio");
		}
		return aRetornar;
		}

	@Override
	public Iterable<Entry<K, V>> entries() {
		PositionList<Entry<K,V>> aRetornar;
		aRetornar = new ListaDoblementeEnlazada<Entry<K,V>>();
		for(int i=0 ; i<tam ; i++) 
			for(Entrada<K,V> entrada : contenedor[i]) 
				aRetornar.addLast(entrada);

		return aRetornar;
	}
	
}
