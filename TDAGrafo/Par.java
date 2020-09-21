package TDAGrafo;

public class Par<E> {
	
	private E camino;
	private int peso;
	
	public Par(E x) {
		camino = x;
		peso = 0;
	}
	
	public E getCamino() {
		return camino;
	}
	
	public int getPeso() {
		return peso;
	}
	
	public void setCamino(E cam) {
		camino = cam;
	}
	
	public void setPeso(int p) {
		peso = p;
	}

}
