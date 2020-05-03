package Modelo;
 

public class Jugador implements Comparable<Object>, InterJUGADOR{
	private int id;
	private String nombre;
	private int puntos;
	private int tiros;

	
	public Jugador(int id, String n){ //se inicializa el jugador
		this.id = id;
		nombre = n;
		puntos = 0;
		tiros = 0;
	}
	
	public Jugador(int id, String n, int p, int t){
		this.id = id;
		this.nombre = n;
		this.puntos = p;
		this.tiros = t;
	}
	
	public void sumarTiro(){
		tiros++;
	}
	
	public int getTiros(){
		return tiros;
	}
	
	public int getPuntos(){
		return puntos;
	}

	public int getId() {
		return id;
	}

	public String getNombreG() {
		return sacarEspacios(this.nombre);
	}
	
	public String getNombre(){
		return this.nombre;
	}
	
	public void sumarPuntos(int puntos){
		this.puntos += puntos;
	}

	
	private String sacarEspacios(String s){
		return s.replace(" ", "");
	}
	
	
	public String toString(){
		return (this.id + " " + this.nombre + " " + this.puntos + " " + this.tiros);
	}

	public int compareTo(Object arg0) {
		if (arg0 instanceof Jugador){
			Jugador j = (Jugador) arg0;
			return this.nombre.compareTo(j.getNombre());
		}
		return 0;
	}
}


