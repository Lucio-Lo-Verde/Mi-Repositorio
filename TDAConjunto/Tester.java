package TDAConjunto;

public class Tester {
		
		public static void main (String args[]) {
			
			//conjuntos a testear
			Conjunto<Integer> c1= new ConjuntoArreglo<Integer>(5);
			Conjunto<Integer> c2= new ConjuntoArreglo<Integer>(3);
			
			//definición de las componentes del conjunto
			c1.put(10);
			c1.put(11);
			c1.put(12);
			c1.put(13);
			c1.put(14);
			
			c2.put(20);
			c2.put(11);
			c2.put(12);
			
			//prueba get
			System.out.println("Contenido c1:");
			try {
				for(int i=0;i<c1.capacity();i++)
					System.out.print(c1.get(i)+",");
			} catch (ConjuntoVacioException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println();
			System.out.println("Contenido c2:");
			try {
				for(int i=0;i<c2.capacity();i++)
					System.out.print(c2.get(i)+",");
			} catch (ConjuntoVacioException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println();
			
		
			
			//prueba intersección
			Conjunto<Integer> c3= c2.intersection(c1);
			System.out.println("Contenido c3 (c2 intersectado con c1):");
			try {
				for(int i=0;i<c3.capacity();i++)
					System.out.print(c3.get(i)+",");
			} catch (ConjuntoVacioException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println();
			//prueba intersección
			Conjunto<Integer> c4= c1.intersection(c2);
			System.out.println("Contenido c3 (c1 intersectado con c2):");
			try {
				for(int i=0;i<c4.capacity();i++)
					System.out.print(c4.get(i)+",");
				} catch (ConjuntoVacioException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
					}
		}

	}
	

