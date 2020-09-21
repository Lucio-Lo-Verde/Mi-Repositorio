package TDALista;

import Excepciones.BoundaryViolationException;
import Excepciones.EmptyListException;
import Excepciones.InvalidPositionException;

public class TesterLista {

	public static void main(String[] args) {
		PositionList<String> lista= new ListaSimplementeEnlazada<String>();
		lista.addFirst("Hola");
	
			try {
				Position<String> pos= lista.first();
				lista.addAfter(pos, "Mundo");;
				lista.addAfter(lista.last(), "2019");
				lista.addBefore(lista.last(), "ED");
				lista.addBefore(lista.prev(lista.last()), "toRemove");
				Position<String> segundo= lista.next(lista.first());
				lista.remove(lista.next(segundo));

				Position<String> puntero= lista.first();
				while(puntero!=null)
				{System.out.println(puntero.element());
				 if(puntero==lista.last())
					 puntero=null;
				 else	
					 puntero= lista.next(puntero);}
			} catch (EmptyListException | InvalidPositionException | BoundaryViolationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}

}
