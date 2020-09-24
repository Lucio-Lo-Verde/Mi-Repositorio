package TDAGrafo;

import java.util.Iterator;

import Excepciones.*;

public class TestTDP {
	
	private static Graph<Integer,Integer> grafo;
	private static Vertex<Integer> origen;
	private static Vertex<Integer> destino;

	public static void main(String[] args) {
		crearGrafo();
		//imprimirVertices(grafo.vertices());
		//imprimirArcos(grafo.edges());
		
		//metodo a testear
		removeEdge(5,6);
		
		//imprimirVertices(grafo.vertices());
		//imprimirArcos(grafo.edges());
	}	

	public static void addNode(int node) {
		
		//Agrega el nodo “node” al grafo, si aún no pertenecía a la estructura
		Iterator<Vertex<Integer>> itInt = grafo.vertices().iterator();
		Vertex<Integer> nuevo;
		boolean encontre = false;
		int elem;
		
		while(itInt.hasNext() && !encontre) {
			elem = itInt.next().element();
			if(elem == node)
				encontre = true;
		}
		if(!encontre)
			nuevo = grafo.insertVertex(node);
	}
	
	public static void addEdge(int node1, int node2) { 
		//asumo que node1 y node2 son distintos
		//agrega un arco entre el nodo “node1” y el nodo “node2”, si aún no existía el arco y ambos parámetros son nodos pertenecientes a la estructura.
		Iterator<Vertex<Integer>> itInt = grafo.vertices().iterator();
		Vertex<Integer> vert;
		Vertex<Integer> vertice1 = new Vertice();
		Vertex<Integer> vertice2 = new Vertice();
		boolean encontreN1 = false;
		boolean encontreN2 = false;
		int elem;
		
		while(itInt.hasNext() && (!encontreN1 || !encontreN2)) {
			vert = itInt.next();
			elem = vert.element();
			if(elem == node1 && !encontreN1) {
				encontreN1 = true;
				vertice1 = vert;
			}
			else if(elem == node2 && !encontreN2) {
				encontreN2 = true;
				vertice2 = vert;
			}
		}
		if(encontreN1 && encontreN2) {
			try {
				if(!grafo.areAdjacent(vertice1, vertice2))
					grafo.insertEdge(vertice1, vertice2, 10);
			} catch (InvalidVertexException e) {
				e.printStackTrace();
				System.out.println("metodo addEdge: vertice invalido");
			}
		}
		
	}
	
	public static void removeNode(int node) {
		
		//remueve el nodo “node” del grafo, si el parámetro es un nodo de la estructura.
		Iterator<Vertex<Integer>> itInt = grafo.vertices().iterator();
		Vertex<Integer> vert = new Vertice();
		int elem;
		boolean encontre = false;
		
		while(itInt.hasNext() && !encontre) {
			vert = itInt.next();
			elem = vert.element();
			if(elem == node) 
				encontre = true;
		}
		if(encontre) {
			try {
				grafo.removeVertex(vert);
			} catch (InvalidVertexException e) {
				e.printStackTrace();
				System.out.println("metodo removeNode: vertice invalido");
			}
		}
		
	}
	
	public static void removeEdge(int node1, int node2) {

		//remueve el arco entre el nodo “node1” y el nodo “node2”, si el arco formado por los parámetros pertenecen a la estructura.
		Iterator<Edge<Integer>> itArcos = grafo.edges().iterator();
		Vertex<Integer>[] laterales;
		Vertex<Integer> cola;
		Vertex<Integer> punta;
		Edge<Integer> arco = new Arco();
		boolean encontre = false;

		try {
			while(itArcos.hasNext() && !encontre) {
				arco = itArcos.next();
				laterales = grafo.endvertices(arco);
				cola = laterales[0];
				punta = laterales[1];
				if((cola.element() == node1 && punta.element() == node2) || (cola.element() == node2 && punta.element() == node1)) 
					encontre = true;
			}
			if(encontre) 
				grafo.removeEdge(arco);
			
		} catch(InvalidEdgeException e){
			e.printStackTrace();
			System.out.println("metodo removeEdge: arco invalido");
		}

	}
	
	private static void imprimirVertices(Iterable<Vertex<Integer>> camino) {
		
		for(Vertex<Integer> vertice : camino) {
			System.out.print("(" + vertice.element() + ") - ");
		}
		System.out.println();
		
	}
	
	private static void imprimirArcos(Iterable<Edge<Integer>> camino) {
		
		for(Edge<Integer> arco : camino) {
			System.out.print("( ) -" + arco.element() + "- ");
		}
		System.out.println("( )");
		
	}

	
	public static void crearGrafo() {

		grafo = new GrafoListaArcos<Integer,Integer>();

		Vertex<Integer> va = grafo.insertVertex(1);
		Vertex<Integer> vb = grafo.insertVertex(2);
		Vertex<Integer> vc = grafo.insertVertex(3);
		Vertex<Integer> vd = grafo.insertVertex(4);
		Vertex<Integer> ve = grafo.insertVertex(5);
		Vertex<Integer> vf = grafo.insertVertex(6);
		Vertex<Integer> vg = grafo.insertVertex(7);
		Vertex<Integer> vh = grafo.insertVertex(8);
		Vertex<Integer> vi = grafo.insertVertex(9);
		Vertex<Integer> vj = grafo.insertVertex(10);
		Vertex<Integer> vk = grafo.insertVertex(11);
		Vertex<Integer> vl = grafo.insertVertex(12);
		Vertex<Integer> vm = grafo.insertVertex(13);
		Vertex<Integer> vn = grafo.insertVertex(14);
		Vertex<Integer> vp = grafo.insertVertex(15);
		Vertex<Integer> vq = grafo.insertVertex(16);
		
		origen = va;
		destino = vb;

		try {

			grafo.insertEdge(va,ve,84);
			grafo.insertEdge(va,vb,32);
			grafo.insertEdge(vb,vc,41);
			grafo.insertEdge(vb,vd,58);
			grafo.insertEdge(vd,vf,4);
			grafo.insertEdge(ve,vf,36);
			grafo.insertEdge(ve,vg,37);
			grafo.insertEdge(vf,vg,46);
			grafo.insertEdge(vf,vh,200);
			grafo.insertEdge(vg,vh,16);
			grafo.insertEdge(vb,vi,4);
			grafo.insertEdge(vi,ve,3);
			grafo.insertEdge(va,vj,2);
			grafo.insertEdge(vj,ve,13);
			grafo.insertEdge(vc,vk,53);
			grafo.insertEdge(vj,vp,56);
			grafo.insertEdge(vl,vk,36);
			grafo.insertEdge(vn,vl,31);
			grafo.insertEdge(vl,vm,15);
			grafo.insertEdge(vk,vq,31);
			

		} 
		catch (InvalidVertexException ive) {
			System.out.println(ive.getMessage());
		}

	}

}
