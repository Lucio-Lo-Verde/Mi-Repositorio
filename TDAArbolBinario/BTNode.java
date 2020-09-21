package TDAArbolBinario;

public class BTNode<E> implements BTPosition<E> {
	
	private E rotulo;
	private BTPosition<E> parent;
	private BTPosition<E> left;
	private BTPosition<E> right;
	
	public BTNode(E e, BTPosition<E> p, BTPosition<E> l, BTPosition<E> r) {
		rotulo = e;
		parent = p;
		left = l;
		right = r;
	}
	public BTNode(E e, BTPosition<E> p) {
		this(e, p, null, null);
	}
	public BTNode(E e) {
		this(e, null, null, null);
	}
	public BTNode() {
		this(null, null, null, null);
	}

	@Override
	public E element() {
		return rotulo;
	}

	@Override
	public BTPosition<E> parent() {
		return parent;
	}

	@Override
	public BTPosition<E> left() {
		return left;
	}

	@Override
	public BTPosition<E> right() {
		return right;
	}
	
	@Override
	public void setElement(E e) {
		rotulo = e;
	}
	
	@Override
	public void setParent(BTPosition<E> p) {
		parent = p;
	}
	
	@Override
	public void setLeft(BTPosition<E> l) {
		left = l;
	}
	
	@Override
	public void setRight(BTPosition<E> r) {
		right = r;
	}
	
}
