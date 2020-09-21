package TDAArbolBinario;

public interface BTPosition<E> extends Position<E> {

	@Override
	public E element();
	
	public BTPosition<E> parent();
	
	public BTPosition<E> left();
	
	public BTPosition<E> right();
	
	public void setElement(E e);
	
	public void setParent(BTPosition<E> p);
	
	public void setLeft(BTPosition<E> l);
	
	public void setRight(BTPosition<E> r);
	
}
