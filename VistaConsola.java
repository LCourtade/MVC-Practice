package Vista;
 
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;



public class VistaConsola implements InterVISTA {
	private final int PREPARANDO = 0;
	private final int COMENZADO = 1;
	private final int FINALIZADO = 2;
	private String jugadorActual;
	private int puntajeActual;
	private InterCONTROLADOR controlador;
	private int estado;
	private boolean b;
	private int apuesta;
	private String ganador;
	private String salida;
	
	
	public VistaConsola(){
		this.jugadorActual = "";
		this.puntajeActual = 0;
		this.b = false;
		this.estado = this.PREPARANDO;
	}
	
	public void iniciar() {
		while(this.estado != this.FINALIZADO){
			int op = menu();
			if(this.estado == this.PREPARANDO){
				switch(op){
					case 1:
						agregarJugador();
						break;
					case 2:
						eliminarJugador();
						break;
					case 3:
						mostrarJugadores();
						break;
					case 4:
						haypozo();
						break;
					case 5:
						controlador.iniciarJuego();
						break;
					case 6:
						establecerorden();
						break;
					case 7:
						generarpersistenciaranking();
						break;
					case 8:
						veranking();
						break;
					case 9:
						generarpersistenciapartida();
						break;
					case 10:
						 cargarpartida();
						break;
				}
			}else if (this.estado == this.COMENZADO){
				switch(op){
					case 1:
						controlador.tirar();
						remanente();
						break;
					case 2:
						mostrarcategorias();					
						break;
					case 3:
						this.finalizarTurno();
						break;
					case 4:
						mostrarPuntajes();
						break;
					case 5:
						finalizarpartida();
						ganador(ganador,apuesta);
					    break;
					case 6:
						guardarpartida();
						finalizarpartida();
						break;
						
				}
			}
		}
	}
	


		private void remanente() {
			String s2 = (this.input("¿Desea volver a tirar? (si no quedan tiros disp. diga NO) Responda SI/NO"));
			if (s2.toUpperCase().equals("SI")){
			String s = (this.input("¿Desea tirar todos los dados?  Responda SI/NO"));
			if (s.toUpperCase().equals("SI")){
				this.controlador.tirar();
				this.remanente();
			}
			if (s.toUpperCase().equals("NO")){
				s= this.input("Escriba el nro de dados a tirar (sin espacios y ascendentemente):");
				this.controlador.getRemanente(s);
				this.remanente();
			}
			}
			if (s2.toUpperCase().equals("NO")){
				this.finalizarTurno();
			}
		}
	

		private void generarpersistenciapartida() {
		 this.controlador.generarpersistenciapartida();
		 System.out.println("El archivo de partida ha sido creado"); 
	}

		private void cargarpartida() {
		 apuesta=this.controlador.cargarpartida();
		 this.controlador.iniciarJuego();
		;
	}

		private void guardarpartida() {
			this.controlador.guardarpartida();
			System.out.println("La partida actual ha sido guardada"); 
		
	}

		private void generarpersistenciaranking() {
		this.controlador.generarpersistenciaranking();
		System.out.println("El archivo de ranking ha sido creado");
		
	}

		private void veranking(){
		 System.out.println("RANKING:");
		 System.out.println(this.controlador.veranking());
		 
		
	}

	
		private void establecerorden() {
		  this.controlador.establecerorden();
		  System.out.println("El orden de juego ha sido definido correctamente");
		
	}
		
		   

	private void finalizarpartida() {
		this.estado=this.FINALIZADO;
		System.out.println("PARTIDA FINALIZADA");
	}

	private void mostrarPuntajes() {
		System.out.println("----------------------------------");
		System.out.println("Puntajes");
		int[] puntajes = this.controlador.getPuntajes();
		ArrayList<String> jugadores = this.controlador.getJugadores();
		for(int i = 0; i < jugadores.size(); i++){
			System.out.println("Puntaje de " + jugadores.get(i) + ": " + puntajes[i]);}
		
	}

	

	private void eliminarJugador() {
		String s = input("Ingrese el nombre del jugador a eliminar");
		this.controlador.eliminarJugador(s);
	}

	public void agregarJugador(){
		String s = input("Ingrese el nombre del nuevo jugador: ");
		controlador.agregarJugador(s);
		
	}
	
	public void haypozo(){ //se determina tanto la existencia de apuesta y el pozo resultante
		ArrayList<String> cantjugadores;
		cantjugadores= controlador.getJugadores();
		System.out.println("----------------------------------");
		String s = (this.input("¿Esta partida se juega por dinero?  Responda SI/NO"));
		if (s.toUpperCase().equals("SI")){
			this.controlador.determinarPozo(true);
			s= this.input("Establezca la apusta mínima por jugador:");
			apuesta= Integer.parseInt(s)*(cantjugadores.size());
			this.controlador.getApuesta(apuesta);
			System.out.println("El pozo del juego es de: " + apuesta);
			}
		if (s.toUpperCase().equals("NO")){
			this.controlador.determinarPozo(false);
		}
	}
	
	public void mostrarJugadores(){
		ArrayList<String> jugadores = controlador.getJugadores();
		System.out.println("----------------------------------");
		System.out.println("Jugadores");
		for(String j : jugadores){
			System.out.println(j);
		}
	}
	
	


	private int menu() {
		int respuesta = 0;
		while (respuesta == 0) {
			System.out.println("  ****BIENVENIDOS A YACHT!!!****    ");
			System.out.println("----------------------------------");
			if (estado == PREPARANDO) {
				System.out.println("1  --> Agregar Jugador");
				System.out.println("2  --> Eliminar Jugador");
				System.out.println("3  --> Mostrar Jugadores");
				System.out.println("4  --> Establecer apuesta");
				System.out.println("5  --> COMENZAR JUEGO ");
				System.out.println("6  --> Establecer orden de jugadores");
				System.out.println("7  --> Generar archivo ranking");
				System.out.println("8  --> Mostrar Ranking");
				System.out.println("9  --> Generar archivo partida");
				System.out.println("10 --> Cargar partida");
			} else {
				for (int i = 0; i < 5; ++i){ //simulo un clear screen 
					System.out.println();}
				System.out.println("Turno de : " + this.jugadorActual);
				System.out.println("------------------");
				System.out.println("----------------------------------");
				System.out.println("1 - Tirar");
				System.out.println("2- Mostrar categorías");
				System.out.println("3- Finalizar Turno");
				System.out.println("4- Ver puntajes");
				System.out.println("5- Finalizar partida");
				System.out.println("6- Guardar partida");
			}

			respuesta = pedirEntero("SELECCIÓN ACTUAL: ");


		}
		return respuesta;
	}
	
	public void finalizarTurno(){
			String s = "";
				while (s.equals("")){
				   s = this.input ("Seleccione la categoría de ptos a usar:");
				     this.controlador.getCategoria(s);}
			this.controlador.nuevoPuntajeActual(puntajeActual);
		    this.controlador.getPuntajes();
			controlador.finalizarTurno(true);
			
		}
	
	
	public void setJugadorActual(String jugadorActual) {
		this.jugadorActual = jugadorActual;
	}



	public void setPuntajeActual(int puntajeActual) {
		this.puntajeActual = puntajeActual;
	}



	public void setControlador(InterCONTROLADOR controlador) {
		this.controlador = controlador;
	}



	public void puedeFinalizar() {
		this.b = true;
	}


	@SuppressWarnings("resource")
	private int pedirEntero(String msg) {
		Scanner sc = new Scanner(System.in);
		int respuesta = 0;
		int flag = 0;
		while (flag == 0) {
			try {
				System.out.print(msg + " ");
				respuesta = sc.nextInt();
				flag = 1;
			} catch (Exception e) {

				System.out.println("Entrada erronea");
			}
		}
		return respuesta;
	}



    @Override
	public void cambioJugador(String n) {
		this.jugadorActual = n;
		this.puntajeActual = 0;
		this.b = false;
	}




    @Override
	public void nuevoPuntajeActual(int p) {
		this.puntajeActual = p;
	}






    @Override
	public void dadosTirados(int[] d) {
		for(int i = 0; i < d.length; i++){
			int j = i + 1;
				System.out.println("Dado nro " + j + ": " + d[i]);
			
		}
	}




	public void nuevosPuntajes(int[] puntajes) {
		System.out.println("----------------------------------");
		System.out.println("Nuevos puntajes");
		ArrayList<String> jugadores = this.controlador.getJugadores();
		for(int i = 0; i < jugadores.size(); i++){
			System.out.println("Puntaje de " + jugadores.get(i) + ": " + puntajes[i]);
		}
		
	}
	

	@SuppressWarnings("resource")
	private String input (String msg) {
		Scanner sc = new Scanner(System.in);
		String respuesta = "";
		int flag = 0;
		while (flag == 0) {
			try {
				System.out.print(msg + " ");
				respuesta = sc.nextLine();
				flag = 1;
			} catch (Exception e) {
				System.out.println("Entrada erronea");
			}
		}
		return respuesta;
	}
	
	

	@Override
	public void juegoIniciado() {
		this.estado = this.COMENZADO;
	}
	
	@Override
	public void juegoNoIniciado(){
		System.out.println("Juego no iniciado, no hay suficientes jugadores");
	}
	
	public void juegoReiniciado(){
		this.jugadorActual = "";
		this.puntajeActual = 0;
		this.b = false;
		this.estado = this.PREPARANDO;
	}

	@Override
	public void jugadorYaExiste() {
		System.out.println("No se pudo agregar jugador porque ya existe");
	}

	@Override
	public void nuevoJugador(ArrayList<String> jugadores) {
		
		
	}

	@Override
	public void jugadorEliminado(ArrayList<String> j, String n) {
		// TODO Auto-generated method stub
		
	}

	


	@Override
	public void dadosReiniciados(boolean b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mostrarcategorias() {
		System.out.println("YACHT            VALOR 50 PTOS                           Equivale a cinco dados iguales");
		System.out.println("ESCALERA MAYOR   VALOR 30 PTOS                           Equivale a cinco dados en orden");
		System.out.println("ESCALERA MENOR   VALOR 15 PTOS                           Equivale a cuatro dados en orden");
		System.out.println("4IGUALES         VALOR (4*dadoigual)+dado PTOS           Equivale a cuatro dados iguales y el valor del restante");
		System.out.println("ELECCIÓN         VALOR (sumadados) PTOS                  Equivale a la suma de los dados del ultimo tiro");
		System.out.println("FULL             VALOR (3*dadoigual)+(2*dadoigual) PTOS  Equivale a tres dados iguales y un par");
		System.out.println("AS_A_SEIS:(1..6) VALOR (nrodado*cantidad) PTOS           Equivale a la suma de las repeticiones de un cierto nro");
	}
	
	public void avisoultimotiro(){
		System.out.println("ATENCION: TIENE UN SOLO TIRO A DISPOSICION");
	}

	@Override
	public void ganador(String n, int t){
		System.out.println("HAY GANADOR!!!");
			n=this.controlador.getGanadorP(ganador, apuesta);
		System.out.println("El jugador " +" "+ n + " "+ "ha ganado la partida y se lleva un pozo de" + " "+apuesta);
		System.out.println("EL RANKING SE HA ACTUALIZADO");
	}

	

	
}

		
	
	
	
	

	
