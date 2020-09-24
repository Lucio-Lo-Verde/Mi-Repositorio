package TDALista;

import java.util.Iterator;
import Excepciones.BoundaryViolationException;
import Excepciones.EmptyListException;
import Excepciones.InvalidPositionException;

public class ElementIterator<E> implements Iterator<E> {
	
	private Position<E> cursor;
	private PositionList<E> list;

	public ElementIterator(PositionList<E> list) {
		this.list = list;
		if(list.isEmpty())
			cursor=null;
		else {
			try {
				cursor=list.first();
			} catch (EmptyListException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	@Override
	public boolean hasNext() {
		return cursor!=null;
	}

	@Override
	public E next() {
		E aRetornar=cursor.element();
		try {
			if(cursor==list.last())
				cursor=null;
			else
				cursor=list.next(cursor);
		}
		catch (EmptyListException | BoundaryViolationException | InvalidPositionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return aRetornar;
	}
}