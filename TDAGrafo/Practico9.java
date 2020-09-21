package TDAGrafo;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import Excepciones.EmptyListException;
import Excepciones.EmptyQueueException;
import Excepciones.InvalidEdgeException;
import Excepciones.InvalidPositionException;
import Excepciones.InvalidVertexException;
import TDACola.ArrayQueue;
import TDACola.Queue;
import TDALista.DoubleLinkedList;
import TDALista.PositionList;

public class Practico9 {
	
	private static Graph<Character,Integer> grafo;
	private static Vertex<Character> origen;
	private static Vertex<Character> destino;

	public static void main(String[] args) {
		crearGrafo();
		Par<PositionList<Vertex<Character>>> caminoMasEco;
		caminoMasEco = caminoMasEconomico(origen, destino, grafo);
		//PositionList<Vertex<Character>> algunCamino = BFSShell(origen, destino, grafo);
		//Vertex<Character> v = ejercicio6b('b', grafo);
		//System.out.println(v.element());
		//ejercicio6c('z', grafo);
		imprimirPar(caminoMasEco);

	}
	
	private static void imprimirCamino(Iterable<Vertex<Character>> camino) {
		
		for(Vertex<Character> vertice : camino) {
			System.out.println(vertice.element());
		}
		
	}
	
	private static void imprimirPar(Par<PositionList<Vertex<Character>>> par) {

		for(Vertex<Character> vertice : par.getCamino()) {
			System.out.println(vertice.element());
		}
		System.out.println(par.getPeso());

	}

	public static Par<PositionList<Vertex<Character>>> caminoMasEconomico(Vertex<Character> o, Vertex<Character> d, Graph<Character, Integer> g){
		//Depht-first-search DPS
		
		PositionList<Vertex<Character>> caminoMin = new DoubleLinkedList<Vertex<Character>>();
		Par<PositionList<Vertex<Character>>> p = new Par<PositionList<Vertex<Character>>>(caminoMin);
		p.setPeso(Integer.MAX_VALUE);
		Map<Vertex<Character>, Boolean> visitados = new HashMap<Vertex<Character>, Boolean>();
		int pesoAux = 0;
		PositionList<Vertex<Character>> caminoAux = new DoubleLinkedList<Vertex<Character>>();
		
		for(Vertex<Character> vertice : g.vertices()) {
			visitados.put(vertice, false);
		}
		caminoMasEcoRecu(o, d, g, visitados, p, caminoAux, pesoAux);
		
		
		return p;
	}
	
	private static void caminoMasEcoRecu(Vertex<Character> o, Vertex<Character> d, Graph<Character, Integer> g,
			Map<Vertex<Character>, Boolean> visitados, Par<PositionList<Vertex<Character>>> caminoMin,
			PositionList<Vertex<Character>> caminoAux, int pesoAux) {

		caminoAux.addLast(o);
		visitados.put(o, true);
		
		try {
		for(Edge<Integer> arco : g.incidentEdges(o)) {
			
			Vertex<Character> op = g.opposite(o, arco);
			
			if(op==d) {
				caminoAux.addLast(op);
				pesoAux += arco.element();
				if(pesoAux <= caminoMin.getPeso()) {
					caminoMin.setCamino(clonar(caminoAux));
					caminoMin.setPeso(pesoAux);
				}
				caminoAux.remove(caminoAux.last());
				pesoAux -= arco.element();
			}
			else {
				if(!visitados.get(op)) {
					caminoMasEcoRecu(op, d, g, visitados, caminoMin, caminoAux, pesoAux+arco.element());
				}
			}
		}
		caminoAux.remove(caminoAux.last());
		visitados.put(o, false);
		
		
		
		
		
		} catch(InvalidVertexException | InvalidEdgeException | InvalidPositionException | EmptyListException exc) {
			System.out.println(exc.getMessage());
		}
	}
	
	public static Par<PositionList<Vertex<Character>>> caminoMasCorto(Vertex<Character> o, Vertex<Character> d, Graph<Character, Integer> g){
		//Depht-first-search DPS
		
		PositionList<Vertex<Character>> caminoMin = new DoubleLinkedList<Vertex<Character>>();
		Par<PositionList<Vertex<Character>>> p = new Par<PositionList<Vertex<Character>>>(caminoMin);
		p.setPeso(Integer.MAX_VALUE);
		Map<Vertex<Character>, Boolean> visitados = new HashMap<Vertex<Character>, Boolean>();
		int pesoAux = 0;
		PositionList<Vertex<Character>> caminoAux = new DoubleLinkedList<Vertex<Character>>();
		
		for(Vertex<Character> vertice : g.vertices()) {
			visitados.put(vertice, false);
		}
		caminoMasCortoRecu(o, d, g, visitados, p, caminoAux, pesoAux);
		
		
		return p;
	}
	
	private static void caminoMasCortoRecu(Vertex<Character> o, Vertex<Character> d, Graph<Character, Integer> g,
			Map<Vertex<Character>, Boolean> visitados, Par<PositionList<Vertex<Character>>> caminoMin,
			PositionList<Vertex<Character>> caminoAux, int pesoAux) {

		caminoAux.addLast(o);
		visitados.put(o, true);
		
		try {
		for(Edge<Integer> arco : g.incidentEdges(o)) {
			
			Vertex<Character> op = g.opposite(o, arco);
			
			if(op==d) {
				caminoAux.addLast(op);
				pesoAux ++;
				if(pesoAux <= caminoMin.getPeso()) {
					caminoMin.setCamino(clonar(caminoAux));
					caminoMin.setPeso(pesoAux);
				}
				caminoAux.remove(caminoAux.last());
				pesoAux --;
			}
			else {
				if(!visitados.get(op)) {
					caminoMasCortoRecu(op, d, g, visitados, caminoMin, caminoAux, pesoAux+1);
				}
			}
		}
		caminoAux.remove(caminoAux.last());
		visitados.put(o, false);
		
		
		
		
		
		} catch(InvalidVertexException | InvalidEdgeException | InvalidPositionException | EmptyListException exc) {
			System.out.println(exc.getMessage());
		}
	}
	
	private static PositionList<Vertex<Character>> clonar(PositionList<Vertex<Character>> caminoAux) {
		PositionList<Vertex<Character>> clon = new DoubleLinkedList<Vertex<Character>>();
		
		for(Vertex<Character> vertice : caminoAux) {
			clon.addLast(vertice);
		}
		
		return clon;
	}
	
	public static PositionList<Vertex<Character>> algunCamino(Vertex<Character> o, Vertex<Character> d, Graph<Character, Integer> g){
		//Breadth-first-search BFS
		//Map<Vertex<Character>, Vertex<Character>> previo = new HashMap<Vertex<Character>, Vertex<Character>>();
		Map<Vertex<Character>, Boolean> visitados = new HashMap<Vertex<Character>, Boolean>();
		PositionList<Vertex<Character>> camino = new DoubleLinkedList<Vertex<Character>>();
		PositionList<Vertex<Character>> caminoAux = new DoubleLinkedList<Vertex<Character>>();
		//Queue<Vertex<Character>> cola = new ArrayQueue<Vertex<Character>>();
		Booleano b = new Booleano(false);

		for(Vertex<Character> vertice : g.vertices()) {
			visitados.put(vertice, false);
		}

		algunCaminoRecu(o, d, g, visitados, camino, caminoAux ,b);


		return caminoAux;
	}

	private static boolean algunCaminoRecu(Vertex<Character> o, Vertex<Character> d, Graph<Character, Integer> g,
			Map<Vertex<Character>, Boolean> visitados, PositionList<Vertex<Character>> camino, PositionList<Vertex<Character>> caminoAux, Booleano encontre) {

		visitados.put(o, true);
		caminoAux.addLast(o);

		try {
			Iterator<Edge<Integer>> itArcos = g.incidentEdges(o).iterator();
			Edge<Integer> arco;
			Vertex<Character> op = null;

			while(itArcos.hasNext() && !encontre.getBoolean()) {
				arco = itArcos.next();
				op = g.opposite(o, arco);

				if(op==d) {
					encontre.setBoolean(true);
					caminoAux.addLast(op);
					camino = clonar(caminoAux);
					//caminoAux.remove(caminoAux.last());
				}
				else {
					if(!visitados.get(op)) {
						algunCaminoRecu(op, d, g, visitados, camino, caminoAux, encontre);
					}
				}
			}
			caminoAux.remove(caminoAux.last());
			visitados.put(o, false);



		} catch (InvalidVertexException | InvalidEdgeException | InvalidPositionException | EmptyListException  exc) {
			System.out.println(exc.getMessage());
		}
		
		return encontre.getBoolean();
	}
	
	public static PositionList<Vertex<Character>> BFSShell(Vertex<Character> o, Vertex<Character> d, Graph<Character, Integer> g) {

		
		Map<Vertex<Character>, Boolean> visitados = new HashMap<Vertex<Character>, Boolean>();
		PositionList<Vertex<Character>> camino = new DoubleLinkedList<Vertex<Character>>();
		
		for(Vertex<Character> vertice : g.vertices()) {
			visitados.put(vertice, false);
		}
		BFS(o, d, g, visitados, camino);

		return camino;
	}

	private static void BFS(Vertex<Character> o, Vertex<Character> d, Graph<Character, Integer> g, Map<Vertex<Character>, Boolean> visitados, PositionList<Vertex<Character>> camino) {
		Queue<Vertex<Character>> cola = new ArrayQueue<Vertex<Character>>();
		//PositionList<Vertex<Character>>[] caminos = new PositionList[max];
		
		Iterator<Edge<Integer>> itArcos = null;
		Vertex<Character> w = null;
		Vertex<Character> op;
		boolean corte = false;
		
		cola.enqueue(o);
		visitados.put(o, true);

		try {
			while(!cola.isEmpty() && !corte) {
				w = cola.dequeue();
				camino.addLast(w);
				itArcos = g.incidentEdges(w).iterator();
				while(itArcos.hasNext() && !corte) {
					op = g.opposite(w, itArcos.next());
					if(op==d) {
						corte = true;
						camino.addLast(op);
					}
					else if(!visitados.get(op)) {
						visitados.put(op, true);
						cola.enqueue(op);
					}
				}
			}

				


			
			
			
			
		} catch(EmptyQueueException | InvalidVertexException | InvalidEdgeException exc) {
			System.out.println(exc.getMessage());
		}
		

	}
	
	public static Vertex<Character> ejercicio6b(char r, Graph<Character, Integer> g) {
		Vertex<Character> vertice = null;
		Iterator<Vertex<Character>> itVertices = g.vertices().iterator();
		boolean encontre = false;
		
		while(itVertices.hasNext() && !encontre) {
			vertice = itVertices.next();
			encontre = vertice.element().equals(r);
		}
		if(!encontre)
			vertice = null;
		
		
		return vertice;
	}
	
	public static void ejercicio6c(char r, Graph<Character, Integer> g) {
		
		for(Vertex<Character> vertice : g.vertices()) {
			if(vertice.element().equals(r)) {
				try {
					g.removeVertex(vertice);
				} catch (InvalidVertexException exc) {
					System.out.println(exc.getMessage());
				}
			}
		}

	}
	
	public boolean ejercicio6e(Vertex<Character> v, Graph<Character, Integer> g) {
		boolean esLista= false;
		Iterator<Edge<Integer>> itArcosInc, itArcosSuc;
		
		try {
			itArcosInc = g.incidentEdges(v).iterator();
		} catch (InvalidVertexException exc) {
			System.out.println(exc.getMessage());
		}
		
		
		return esLista;
	}

	public static void crearGrafo() {

		grafo = new GrafoListaArcos<Character,Integer>();

		Vertex<Character> va = grafo.insertVertex('a');
		Vertex<Character> vb = grafo.insertVertex('b');
		Vertex<Character> vc = grafo.insertVertex('c');
		Vertex<Character> vd = grafo.insertVertex('d');
		Vertex<Character> ve = grafo.insertVertex('e');
		Vertex<Character> vf = grafo.insertVertex('f');
		Vertex<Character> vg = grafo.insertVertex('g');
		Vertex<Character> vh = grafo.insertVertex('h');
		Vertex<Character> vi = grafo.insertVertex('i');
		Vertex<Character> vj = grafo.insertVertex('j');
		Vertex<Character> vk = grafo.insertVertex('k');
		Vertex<Character> vl = grafo.insertVertex('l');
		Vertex<Character> vm = grafo.insertVertex('m');
		Vertex<Character> vn = grafo.insertVertex('n');
		Vertex<Character> vp = grafo.insertVertex('p');
		Vertex<Character> vq = grafo.insertVertex('q');
		
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
