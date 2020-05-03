package Controlador;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import Modelo.InterMODELO;
import Vista.InterCONTROLADOR;
import Vista.InterVISTA;



	public class Controlador implements InterCONTROLADOR{
		private InterVISTA vista;
		private InterMODELO modelo;
		
		public Controlador(InterMODELO modelo, InterVISTA view){
			this.modelo = modelo;
			this.vista = view;
			this.vista.setControlador(this);
		}



	public void agregarJugador(String nombre){
		switch(this.modelo.getEstadoActual()){
		case Preparando:
			modelo.AgregarJugador(nombre);
			break;
		case Comenzado:
			break;
		case Finalizado:
			break;
	}
	}
	
	public void nuevoJugador(ArrayList<String> jugadores){
		this.vista.nuevoJugador(jugadores);
	}
	
	public void nuevoJugadorActual(String n){
		switch(this.modelo.getEstadoActual()){
		case Preparando:
			break;
		case Comenzado:
			this.vista.juegoIniciado();
			this.vista.cambioJugador(n);
			break;
		case Finalizado:
			break;
		}
		
	}
	
	public void tirar(){
		switch(this.modelo.getEstadoActual()){
		case Preparando:
			break;
		case Comenzado:
			modelo.tirar();
			break;
		case Finalizado:
			break;
	}
	}
	
	public void nuevoPuntajeActual(int p){
		this.vista.nuevoPuntajeActual(p);
	}
	
	public void finalizarTurno(boolean b){
		switch(this.modelo.getEstadoActual()){
		case Preparando:
			break;
		case Comenzado:
			modelo.finalizarTurno(b);
			break;
		case Finalizado:
			break;
		}
		
		
	}
	
	
	
	public void dadosTirados(int[] d){
		this.vista.dadosTirados(d);
	}
	
	public void habilitarFinTurno(){
		this.vista.puedeFinalizar();
	}
	
	
	
	
	public void iniciarJuego(){
		switch(this.modelo.getEstadoActual()){
		case Preparando:
			modelo.iniciarJuego();
			break;
		case Comenzado:
			break;
		case Finalizado:
			break;
	}
	}
	

	@Override
	public ArrayList<String> getJugadores() {
		return this.modelo.getJugadores();
	}

	@Override
	public int[] getPuntajes() {
		return this.modelo.getPuntajes();
	}

	
	public void noPudoIniciar(){
		this.vista.juegoNoIniciado();
	}
	
	public void eliminarJugador(String nombre){
		switch(this.modelo.getEstadoActual()){
		case Preparando:
			this.modelo.eliminarJugador(nombre);
			break;
		case Comenzado:
			break;
		case Finalizado:
			break;
		}
		
	}
	


	public void dadosReiniciados(boolean b) {
		this.vista.dadosReiniciados(b);
		
	}

	public void noSeAgregoJugador() {
		this.vista.jugadorYaExiste();		
	}

	public void mostrarcategorias(){
		this.vista.mostrarcategorias();
	}





	@Override
	public void determinarPozo(boolean b) {
		
	}



	public void avisoultimotiro() {
	   this.vista.avisoultimotiro();
		
	}



	@Override
	public void getCategoria(String s) {
		if (s.equals("YACHT")){
			this.modelo.calcularptosyacht();
		}
		if (s.equals("ESCALERA MAYOR")){
			this.modelo.calcularptosescmayor();
		}
		if (s.equals("ESCALERA MENOR")){
			this.modelo.calcularptosescmenor();
		}
		if (s.equals("4IGUALES")){
			this.modelo.calcularptosiguales();
		}
		if (s.equals("FULL")){
			this.modelo.calcularptosfull();
		}
		if (s.equals("ELECCION")){
			this.modelo.calcularptoseleccion();
		}
		if (s.equals("AS_A_SEIS:1")){ 
			this.modelo.calcularptosas();
		}
		if (s.equals("AS_A_SEIS:2")){ 
			this.modelo.calcularptosdos();
		}
		if (s.equals("AS_A_SEIS:3")){ 
			this.modelo.calcularptostres();
		}
		if (s.equals("AS_A_SEIS:4")){ 
			this.modelo.calcularptoscuatro();
		}
		if (s.equals("AS_A_SEIS:5")){ 
	         this.modelo.calcularptoscinco();
	    }
		if (s.equals("AS_A_SEIS:6")){ 
			 this.modelo.calcularptosseis();
		}
		
	}



	@Override
	public void nuevosPuntajes(int [] puntajes) {
			this.vista.nuevosPuntajes(puntajes);
		}



	@Override
	public void establecerorden() {
		this.modelo.establecerorden();
		
	}



	@Override
	public String getGanadorP(String n,int t) {
		return this.modelo.getGanadorP(n);
	}



	


    @Override
	public  ArrayList<String> veranking() {
		return this.modelo.veranking();
		
	}



	@Override
	public void generarpersistenciaranking() {
		this.modelo.generarpersistenciaranking();
		
	}



	@Override
	public void guardarpartida() {
		this.modelo.guardarpartida();
		
	}






	@Override
	public int cargarpartida() {
		return this.modelo.cargarpartida();
		
	}



	@Override
	public void generarpersistenciapartida() {
		this.modelo.generarpersistenciapartida();
		
	}



	@Override
	public void getRemanente(String s) { 
		if (s.equals("345")){ 
			this.modelo.tiro345();
			}
		if (s.equals("124")){
			this.modelo.tiro124();
		}
		if (s.equals("145")){
			this.modelo.tiro145();
		}
		if (s.equals("134")){
			this.modelo.tiro134();
		}
		if (s.equals("135")){
			this.modelo.tiro135();
		}
		if (s.equals("234")){
			this.modelo.tiro234();
		}
		if (s.equals("125")){
			this.modelo.tiro125();
		}
		if (s.equals("123")){
			this.modelo.tiro123();
		}
		if (s.equals("12")){
			this.modelo.tiro12();
		}
		if (s.equals("23")){
			this.modelo.tiro23();
		}
		if (s.equals("15")){
			this.modelo.tiro15();
		}
		if (s.equals("25")){
			this.modelo.tiro25();
		}
		if (s.equals("35")){ 
			this.modelo.tiro35();
		}
		if (s.equals("45")){ 
			this.modelo.tiro45();
		}
		if (s.equals("34")){ 
			this.modelo.tiro34();
		}
		if (s.equals("13")){ 
			this.modelo.tiro13();
		}
		if (s.equals("14")){ 
			this.modelo.tiro14();
		}
		if (s.equals("24")){ 
			this.modelo.tiro24();
		}
		if (s.equals("1")){ 
			this.modelo.tiro1();
		}
		if (s.equals("2")){ 
	         this.modelo.tiro2();
	    }
		if (s.equals("3")){ 
			this.modelo.tiro3();
		}
		if (s.equals("4")){ 
			this.modelo.tiro4();
			}
		if (s.equals("5")){ 
			this.modelo.tiro5();
			}
		if (s.equals("1234")){ 
			this.modelo.tiro1234();
			}
		if (s.equals("2345")){ 
			this.modelo.tiro2345();
			}
	}



	@Override
	public void getApuesta(int apuesta) {
		this.modelo.apuesta(apuesta);
		
	}
	}
	



	






	
	

	
	