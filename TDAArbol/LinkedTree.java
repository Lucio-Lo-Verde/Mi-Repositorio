package TDAArbol;

import java.util.Iterator;

import Excepciones.BoundaryViolationException;
import Excepciones.EmptyListException;
import Excepciones.EmptyTreeException;
import Excepciones.InvalidOperationException;
import Excepciones.InvalidPositionException;
import TDALista.DoubleLinkedList;
import TDALista.Position;
import TDALista.PositionList;

public class LinkedTree<E> implements Tree<E> {
	
	private TNodo<E> root;
	private int size;
	
	public LinkedTree() {
		root = null;
		size = 0;
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
	public Iterator<E> iterator() {
		PositionList<E> lista;
		lista = new DoubleLinkedList<E>();
		
		for(Position<E> pos : positions())
			lista.addLast(pos.element());
		
		return lista.iterator();
	}

	@Override
	public Iterable<Position<E>> positions() {
		PositionList<Position<E>> listaPos;
		listaPos = new DoubleLinkedList<Position<E>>();
		if(!isEmpty())
			pre(root,listaPos);
		return listaPos;
	}

	private void pre(TNodo<E> nodo, PositionList<Position<E>> lista) {
		
		lista.addLast(nodo);
		
		for(TNodo<E> hijo : nodo.getHijos()) {
			pre(hijo,lista);
		}

	}
	
/* NO SE UTILIZA
	private void pos(TNodo<E> nodo, PositionList<Position<E>> lista) {
		
		for(TNodo<E> hijo : nodo.getHijos()) {
			pos(hijo,lista);
		}
		
		lista.addLast(nodo);

	}
*/

	private TNodo<E> checkPosition(Position<E> pos) throws InvalidPositionException{
		if(pos==null)
			throw new InvalidPositionException("checkPosition - Posición nula");
		if(isEmpty())
			throw new InvalidPositionException("checkPosition - Posición nula por no estar en el árbol");
		TNodo<E> temp = null;
		try {
			temp = (TNodo<E>) pos;
		}
		catch(ClassCastException e) {
			throw new InvalidPositionException("checkPosition - Error de casteo");
		}
		return temp;
	}

	@Override
	public E replace(Position<E> v, E e) throws InvalidPositionException {
		TNodo<E> nodo;
		nodo = checkPosition(v);
		E toReturn = nodo.element();
		nodo.setElemento(e);
		return toReturn;
	}

	@Override
	public Position<E> root() throws EmptyTreeException {
		if(isEmpty())
			throw new EmptyTreeException("Arbol vacío");
		return root;
	}

	@Override
	public Position<E> parent(Position<E> v) throws InvalidPositionException, BoundaryViolationException {
		TNodo<E> nodo;
		nodo = checkPosition(v);
		if(nodo==root)
			throw new BoundaryViolationException("Root no tiene padre");
		return nodo.getPadre();
	}

	@Override
	public Iterable<Position<E>> children(Position<E> v) throws InvalidPositionException {
		TNodo<E> nodo;
		nodo = checkPosition(v);
		PositionList<Position<E>> listaPos;
		listaPos = new DoubleLinkedList<Position<E>>();
		
		for(TNodo<E> hijo : nodo.getHijos())
			listaPos.addLast(hijo);
		
		return  listaPos;
	}

	@Override
	public boolean isInternal(Position<E> v) throws InvalidPositionException {
		TNodo<E> nodo;
		nodo = checkPosition(v);
		return !(nodo.getHijos().isEmpty());
	}

	@Override
	public boolean isExternal(Position<E> v) throws InvalidPositionException {
		TNodo<E> nodo;
		nodo = checkPosition(v);
		return nodo.getHijos().isEmpty();
	}

	@Override
	public boolean isRoot(Position<E> v) throws InvalidPositionException {
		TNodo<E> nodo;
		nodo = checkPosition(v);
		return nodo==root;
	}

	@Override
	public void createRoot(E e) throws InvalidOperationException {
		if(!isEmpty())
			throw new InvalidOperationException("Arbol no vacío");
		root = new TNodo<E>(e);
		size = 1;
	}

	@Override
	public Position<E> addFirstChild(Position<E> p, E e) throws InvalidPositionException {
		TNodo<E> padre;
		padre = checkPosition(p);
		TNodo<E> hijo;
		hijo = new TNodo<E>(padre,e);
		padre.getHijos().addFirst(hijo);
		size ++;
		return hijo;
	}

	@Override
	public Position<E> addLastChild(Position<E> p, E e) throws InvalidPositionException {
		TNodo<E> padre;
		padre = checkPosition(p);
		TNodo<E> hijo;
		hijo = new TNodo<E>(padre,e);
		padre.getHijos().addLast(hijo);
		size ++;
		return hijo;
	}

	@Override
	public Position<E> addBefore(Position<E> p, Position<E> rb, E e) throws InvalidPositionException {
		TNodo<E> padre;
		padre = checkPosition(p);
		TNodo<E> hermanoDerecho;
		hermanoDerecho = checkPosition(rb);
		TNodo<E> nuevo;
		nuevo = new TNodo<E>(padre,e);
		PositionList<TNodo<E>> hijosPadre = padre.getHijos();
		if(hijosPadre.isEmpty())
			throw new InvalidPositionException("p no tiene hijos");
		try {
			boolean encontre = false;
			Position<TNodo<E>> pp = hijosPadre.first();
			while(pp!=null && !encontre) {
				if(hermanoDerecho == pp.element())
					encontre = true;
				else {
					if(pp == hijosPadre.last())
						pp = null;
					else
						pp = hijosPadre.next(pp);
				}
			}
			if(!encontre)
				throw new InvalidPositionException("rb no es hijo de p");
			hijosPadre.addBefore(pp,nuevo);
		}
		catch(InvalidPositionException | BoundaryViolationException | EmptyListException ex) {
			System.out.println(ex.getMessage());
		}
		size ++;
		return nuevo;
	}

	@Override
	public Position<E> addAfter(Position<E> p, Position<E> lb, E e) throws InvalidPositionException {
		TNodo<E> padre;
		padre = checkPosition(p);
		TNodo<E> hermanoIzquierdo;
		hermanoIzquierdo = checkPosition(lb);
		TNodo<E> nuevo;
		nuevo = new TNodo<E>(padre,e);
		PositionList<TNodo<E>> hijosPadre = padre.getHijos();
		if(hijosPadre.isEmpty())
			throw new InvalidPositionException("p no tiene hijos");
		try {
			boolean encontre = false;
			Position<TNodo<E>> pp = hijosPadre.first();
			while(pp!=null && !encontre) {
				if(hermanoIzquierdo == pp.element())
					encontre = true;
				else {
					if(pp == hijosPadre.last())
						pp = null;
					else
						pp = hijosPadre.next(pp);
				}
			}
			if(!encontre)
				throw new InvalidPositionException("lb no es hijo de p");
			hijosPadre.addAfter(pp,nuevo);
		}
		catch(InvalidPositionException | BoundaryViolationException | EmptyListException ex) {
			System.out.println(ex.getMessage());
		}
		size ++;
		return nuevo;
	}

	@Override
	public void removeExternalNode(Position<E> p) throws InvalidPositionException {	
		if(isInternal(p))
			throw new InvalidPositionException("p no es hoja");
		TNodo<E> nodo = checkPosition(p);
		if(nodo==root)
			root = null;
		else {
			TNodo<E> padre = nodo.getPadre();
			PositionList<TNodo<E>> hijosPadre = padre.getHijos();
			if(hijosPadre.isEmpty())
				throw new InvalidPositionException("el padre de p no tiene hijos -- árbol corrupto??");
			try {
				boolean encontre = false;
				Position<TNodo<E>> pp = hijosPadre.first();
				while(pp!=null && !encontre) {
					if(nodo == pp.element())
						encontre = true;
					else {
						if(pp == hijosPadre.last())
							pp = null;
						else
							pp = hijosPadre.next(pp);
					}
				}
				if(!encontre)
					throw new InvalidPositionException("el padre de p no es padre de p -- árbol corrupto??");
				hijosPadre.remove(pp);
			}
			catch(InvalidPositionException | BoundaryViolationException | EmptyListException ex) {
				System.out.println(ex.getMessage());
			}
		}
		size --;
	}

	@Override
	public void removeInternalNode(Position<E> p) throws InvalidPositionException {
		if(isExternal(p))
			throw new InvalidPositionException("p es hoja");
		TNodo<E> nodo = checkPosition(p);
		if(nodo==root) {
			if(nodo.getHijos().size()>1)
				throw new InvalidPositionException("error - no puede quedar un árbol con más de una raíz");
			try {
				root = root.getHijos().first().element();
			}
			catch(EmptyListException e) {
				System.out.println("removeInternalNode :: root no tiene hijos --> root es una hoja??");
			}
		}
		else {
			TNodo<E> padre = nodo.getPadre();
			PositionList<TNodo<E>> hijosPadre = padre.getHijos();
			if(hijosPadre.isEmpty())
				throw new InvalidPositionException("el padre de p no tiene hijos -- árbol corrupto??");
			try {
				boolean encontre = false;
				Position<TNodo<E>> pp = hijosPadre.first();
				while(pp!=null && !encontre) {
					if(nodo == pp.element())
						encontre = true;
					else {
						if(pp == hijosPadre.last())
							pp = null;
						else
							pp = hijosPadre.next(pp);
					}
				}
				if(!encontre)
					throw new InvalidPositionException("el padre de p no es padre de p -- árbol corrupto??");
				hijosPadre.remove(pp);
			}
			catch(InvalidPositionException | BoundaryViolationException | EmptyListException ex) {
				System.out.println(ex.getMessage());
			}
		}
		size--;
	}

	@Override
	public void removeNode(Position<E> p) throws InvalidPositionException {
	/*
		checkPosition(p);
		if(isExternal(p))
			removeExternalNode(p);
		else
			removeInternalNode(p);
	*/
		
		TNodo<E> nodo = checkPosition(p);
		try {
			if(nodo==root) {
				if(root.getHijos().size()==1) {
					Position<TNodo<E>> rNodo = root.getHijos().first();
					rNodo.element().setPadre(null);
					root.getHijos().remove(rNodo);
					root = rNodo.element();
				}
				else if(size==1)
					root = null;
				else 
					throw new InvalidPositionException("removeNode - No puede quedar más de una raíz");
			}
			else {
				TNodo<E> padre = nodo.getPadre();
				PositionList<TNodo<E>> hijosPadre = padre.getHijos();
				PositionList<TNodo<E>> hijosNodo = nodo.getHijos();
				//Buscar a n dentro de los hijos del padre
				Position<TNodo<E>> posDeN;
				Position<TNodo<E>> cursor = hijosPadre.first();
				while(cursor!=null && cursor.element()!=nodo) {
					if(cursor==hijosPadre.last())
						cursor = null;
					else
						cursor = hijosPadre.next(cursor);
				}
				if(cursor!=null)
					posDeN = cursor;
				else
					throw new InvalidPositionException("n no esta en la lista de hijos de su nodo padre -- arbol corrupto??");
				//si n tiene hijos se insertan en el lugar del padre
				while(!hijosNodo.isEmpty()) {
					Position<TNodo<E>> hijoN = hijosNodo.first();
					hijosPadre.addBefore(posDeN, hijosNodo.remove(hijoN));
					hijoN.element().setPadre(padre);
				}
				hijosPadre.remove(posDeN);
			}
		}
		catch(EmptyListException | BoundaryViolationException e) {
			throw new InvalidPositionException("n no esta en la lista de hijos de su nodo padre -- arbol corrupto??");
		}
		size--;
		
	}
	
	public Tree<E> clone() {
		Tree<E> aRetornar = new LinkedTree<E>();
		if(size>0) {
			try {
				aRetornar.createRoot(root.element());
				clonePre(root, aRetornar.root(), aRetornar);
			
			}
			catch(EmptyTreeException | InvalidOperationException e) {
				System.out.println(e.getMessage());
			}
		}
		return aRetornar;
	}
	
	private void clonePre(TNodo<E> padre, Position<E> posPadre, Tree<E> A) {
		
		try {
			for(TNodo<E> hijo : padre.getHijos()) {
				Position<E> posHijo = A.addLastChild(posPadre, hijo.element());
				clonePre(hijo, posHijo, A);
			}
		}
		catch(InvalidPositionException e) {
			System.out.println(e.getMessage());
		}
	}
	
}
