public class prueba{

	private int entero;
	private char caracter;
	private String nombre;

	public prueba(){
		nombre = "Jacinto";
		entero = 0;
		caracter = 'c';
	}

	public prueba(String n, int i, char c){
		nombre = n;
		entero = i;
		caracter = c;
	}

	public int getEntero(){
		return entero;
	}

	public char getCaracter(){
		return caracter;
	}

	public String getNombre(){
		return nombre;
	}

	public void setEntero(int i){
		entero = i;
	}

	public void setCaracter(char c){
		caracter = c;
	}

	public void setNombre(String n){
		nombre = n;
	}
}