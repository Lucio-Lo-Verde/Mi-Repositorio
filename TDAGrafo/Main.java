package TDAGrafo;

import Excepciones.InvalidVertexException;

public class Main {

	public static void main(String[] args) {
		
		Graph<Character,Integer> grafo = new GrafoListaArcos<Character,Integer>();
		
		Vertex<Character> va = grafo.insertVertex('a');
		Vertex<Character> vb = grafo.insertVertex('b');
		Vertex<Character> vc = grafo.insertVertex('c');
		Vertex<Character> vd = grafo.insertVertex('d');
		Vertex<Character> ve = grafo.insertVertex('e');
		Vertex<Character> vf = grafo.insertVertex('f');
		Vertex<Character> vg = grafo.insertVertex('g');
		Vertex<Character> vh = grafo.insertVertex('h');
		
		try {
			
			grafo.insertEdge(va,ve,84);
			grafo.insertEdge(va,vb,32);
			grafo.insertEdge(vb,vc,41);
			grafo.insertEdge(vb,vd,58);
			grafo.insertEdge(vd,vf,74);
			grafo.insertEdge(ve,vf,36);
			grafo.insertEdge(ve,vg,37);
			grafo.insertEdge(vf,vg,46);
			grafo.insertEdge(vf,vh,66);
			grafo.insertEdge(vg,vh,55);
			
		} 
		catch (InvalidVertexException ive) {
			System.out.println(ive.getMessage());
		}

	}

}
