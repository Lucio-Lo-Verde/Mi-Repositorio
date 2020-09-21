package TDAArbolBinario;

import java.util.Iterator;

import Excepciones.BoundaryViolationException;
import Excepciones.EmptyTreeException;
import Excepciones.InvalidOperationException;
import Excepciones.InvalidPositionException;
import TDALista.DoubleLinkedList;
import TDALista.ElementIterator;
import TDALista.PositionList;

public class LinkedBinaryTree<E> implements BinaryTree<E> {
	
	private BTPosition<E> root;
	private int size;
	
	public LinkedBinaryTree() {
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
		PositionList<E> lista = new DoubleLinkedList<E>();

		for(Position<E> pos : positions())
			lista.addLast(pos.element());
		
		return lista.iterator();
	}

	@Override
	public Iterable<Position<E>> positions() {
		PositionList<Position<E>> listaPos = new DoubleLinkedList<Position<E>>();
		if(size!=0)
			preOrden(root, listaPos);
		
		return listaPos;
	}
	
	private void preOrden(BTPosition<E> pos, PositionList<Position<E>> lista) {
		
		lista.addLast(pos);

		if(pos.left()!=null)
			preOrden(pos.left(), lista);
		if(pos.right()!=null)
			preOrden(pos.right(), lista);
			
	}
	
	private BTPosition<E> checkPosition(Position<E> p) throws InvalidPositionException{
		if(p==null)
			throw new InvalidPositionException("CheckPosition: Posicion nula");
		BTNode<E> toReturn = null;
		try {
			toReturn = (BTNode<E>) p;
		}
		catch(ClassCastException cce) {
			throw new InvalidPositionException("CheckPosition: Error de conversion de variable");
		}
		if(size==0)
			throw new InvalidPositionException("CheckPosition: El nodo no pertenece al arbol");
		if(toReturn.element()==null)
			throw new InvalidPositionException("CheckPosition: El nodo no tiene rotulo");
		if(toReturn.parent()==null && toReturn != root)
			throw new InvalidPositionException("CheckPosition: El nodo no tiene padre y tampoco es raiz");
		
		return toReturn;
	}

	@Override
	public E replace(Position<E> v, E e) throws InvalidPositionException {
		BTPosition<E> btn = checkPosition(v);
		E toReturn = btn.element();
		btn.setElement(e);
		
		return toReturn;
	}

	@Override
	public Position<E> root() throws EmptyTreeException {
		if(size==0)
			throw new EmptyTreeException("root(): El arbol esta vacio");
		return root;
	}

	@Override
	public Position<E> parent(Position<E> v) throws InvalidPositionException, BoundaryViolationException {
		BTPosition<E> btn = checkPosition(v);
		if(btn==root)
			throw new BoundaryViolationException("parent(v): Root no tiene padre");
		
		return btn.parent();
	}

	@Override
	public Iterable<Position<E>> children(Position<E> v) throws InvalidPositionException {
		BTPosition<E> btn = checkPosition(v);
		PositionList<Position<E>> children = new DoubleLinkedList<Position<E>>();
		
		if(btn.left()!=null)
		children.addLast(btn.left());
		if(btn.right()!=null)
		children.addLast(btn.right());
		
		return children;
	}

	@Override
	public boolean isInternal(Position<E> v) throws InvalidPositionException {
		BTPosition<E> btn = checkPosition(v);
		boolean esInterno = (btn.left()!=null || btn.right()!=null);
		
		return esInterno;
	}

	@Override
	public boolean isExternal(Position<E> v) throws InvalidPositionException {
		BTPosition<E> btn = checkPosition(v);
		boolean esExterno = (btn.left()==null && btn.right()==null);
		
		return esExterno;
	}

	@Override
	public boolean isRoot(Position<E> v) throws InvalidPositionException {
		BTPosition<E> btn = checkPosition(v);
		
		return btn==root;
	}

	@Override
	public Position<E> left(Position<E> v) throws InvalidPositionException, BoundaryViolationException {
		BTPosition<E> btn = checkPosition(v);
		if(btn.left()==null)
			throw new BoundaryViolationException("left(v): v no tiene hijo izquierdo");
		
		return btn.left();
	}

	@Override
	public Position<E> right(Position<E> v) throws InvalidPositionException, BoundaryViolationException {
		BTPosition<E> btn = checkPosition(v);
		if(btn.right()==null)
			throw new BoundaryViolationException("right(v): v no tiene hijo derecho");
		
		return btn.right();
	}

	@Override
	public boolean hasLeft(Position<E> v) throws InvalidPositionException {
		BTPosition<E> btn = checkPosition(v);
		boolean tieneHIzq = btn.left()!=null;
		
		return tieneHIzq;
	}

	@Override
	public boolean hasRight(Position<E> v) throws InvalidPositionException {
		BTPosition<E> btn = checkPosition(v);
		boolean tieneHDer = btn.right()!=null;
		
		return tieneHDer;
	}

	@Override
	public Position<E> createRoot(E r) throws InvalidOperationException {
		if(size!=0)
			throw new InvalidOperationException("createRoot(e): Root ya ha sido creado");
		root = new BTNode<E>(r);
		size = 1;
		
		return root;
	}

	@Override
	public Position<E> addLeft(Position<E> v, E r) throws InvalidOperationException, InvalidPositionException {
		BTPosition<E> btn = checkPosition(v);
		if(btn.left()!=null)
			throw new InvalidOperationException("addLeft(v, e): v ya tiene hijo izquierdo");
		BTNode<E> hijoIzq = new BTNode<E>(r, btn);
		btn.setLeft(hijoIzq);
		size ++;
		
		return hijoIzq;
	}

	@Override
	public Position<E> addRight(Position<E> v, E r) throws InvalidOperationException, InvalidPositionException {
		BTPosition<E> btn = checkPosition(v);
		if(btn.right()!=null)
			throw new InvalidOperationException("addRight(v, e): v ya tiene hijo derecho");
		BTNode<E> hijoDer = new BTNode<E>(r, btn);
		btn.setRight(hijoDer);
		size ++;
		
		return hijoDer;
	}

	@Override
	public E remove(Position<E> v) throws InvalidOperationException, InvalidPositionException {
		BTPosition<E> btn = checkPosition(v);
		E toReturn = btn.element();
		
		if(btn.left()!=null && btn.right()!=null)
			throw new InvalidOperationException("remove(v): v tiene 2 hijos");
		
		BTPosition<E> childBtn = null;
		
		if(btn.left()!=null) {
			childBtn = btn.left();
			btn.setLeft(null);
		}
		else if(btn.right()!=null) {
			childBtn = btn.right();
			btn.setRight(null);
		}
		
		if(childBtn!=null)
			childBtn.setParent(btn.parent());
		
		if(btn==root) 
			root = childBtn;
		else {
			if(btn.parent().left()==btn)
				btn.parent().setLeft(childBtn);
			else if(btn.parent().right()==btn)
				btn.parent().setRight(childBtn);
			else
				throw new InvalidPositionException("remove(v): Error...v no es hijo de su padre");
			
			btn.setParent(null);
		}
		size --;
		
		return toReturn;
	}

	@Override
	public void attach(Position<E> r, BinaryTree<E> T1, BinaryTree<E> T2) throws InvalidPositionException {
		BTPosition<E> btn = checkPosition(r);
		if(btn.left()!=null || btn.right()!=null)
			throw new InvalidPositionException("attach(p, t, t): p no es hoja");

		if(!T1.isEmpty()) {
			BTNode<E> raizT1 = null;
			try {
				raizT1 = (BTNode<E>) T1.root();
			}
			catch(ClassCastException | EmptyTreeException exc) {
				throw new InvalidPositionException("attach(p, t, t): Error");
			}
			btn.setLeft(raizT1);
			raizT1.setParent(btn);
			size += T1.size();
		}
		if(!T2.isEmpty()) {
			BTNode<E> raizT2 = null;
			try {
				raizT2 = (BTNode<E>) T2.root();
			}
			catch(ClassCastException | EmptyTreeException exc) {
				throw new InvalidPositionException("attach(p, t, t): Error");
			}
			btn.setRight(raizT2);
			raizT2.setParent(btn);
			size += T2.size();
		}
		
	}

}
