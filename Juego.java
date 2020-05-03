package Modelo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import Modelo.InterMODELO.estados;
import Observer.Observable;
import Observer.Observer;
import Persistencia.MiObjectOutputStream;


	
	public class Juego implements Observable, InterMODELO, InterJUEGO,Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = -8244873061831161466L;
		estados estadoActual;
		private int id;
		private ArrayList<Observer> observadores;
		private ArrayList<Jugador> jugadores;
		private Cubilete dados;
		private int jugadorActual;
		private int puntosActuales;
		private String ganador;
		private boolean puedeTerminar;
		private String jugadorEliminado;
		private ArrayList<InterDADO> dadosUtilizados;
		private ArrayList<InterDADO> dadosTirados;
		private int nrodetiro;
		private int[] arraydados;
		private ObjectOutputStream oos;
		private ObjectInputStream ois;
		private ObjectOutputStream partida;
		private Random tirada;
		private String pozo;
		private ObjectOutputStream archivopozo;
		private int apuesta;
		

		public Juego(){
			this.observadores = new ArrayList<Observer>();
			this.jugadores = new ArrayList<Jugador>();
			this.dados = new Cubilete();
			ganador = null;
			apuesta=0;
			jugadorActual = -1;
			puntosActuales = 0;
			nrodetiro=0;
			puedeTerminar = false;
			this.estadoActual = estados.Preparando;
		}

		public void iniciarJuego(){
			if (jugadores.size() > 1){
				jugadorActual = 0;
				puntosActuales = 0;
				this.estadoActual = estados.Comenzado;
				this.notifyObservers(5);
				
			}else{
				this.notifyObservers(8);
			}
			
		}
	
		public void mostrarcategorias(){
			this.notifyObservers(13);
		}
		
		public void determinarpozo(boolean b){
			if (b==true){
				this.notifyObservers(14);
			}
		   if (b==false){
			   this.notifyObservers(15);
		   }
		}
			
		
   	
		public void tirar(){
			if (nrodetiro>2){ //limite de tiros por turno de jugador
			  this.finalizarTurno(true);
              this.notifyObservers(6);
			}
			if (nrodetiro==1){ //se emite un aviso sobre ultimo tiro al jugador
			  this.notifyObservers(16);
			}
			dadosTirados = new ArrayList<InterDADO>();
			dadosTirados =	dados.tirar();
			nrodetiro = nrodetiro + 1;
			dadosUtilizados = new ArrayList<InterDADO>();
			dados.dadosUtilizados(dadosUtilizados);
			this.notifyObservers(4);
			}

					
		

		public void AgregarJugador(String nombre){
			Jugador nj = new Jugador(jugadores.size(), nombre);
			boolean b = true;
			for(Jugador j : jugadores){//chequeo si el jugador ya existe
				if (j.compareTo(nj) == 0){
					b = false;
				}
			}
			if (b){
				jugadores.add(nj);
				this.notifyObservers(1);//se agrega exitosamente
			}else{
				this.notifyObservers(3);//no se agrega
			}
			
		}
		
		public ArrayList<String> getJugadores(){// armo un arraylist para visualizar los jug.
			ArrayList<String> ju = new ArrayList<String>();
			for (Jugador j : this.jugadores){
				ju.add(j.getNombre());
			}
			return ju;
		}

		
		
		public int[] getDados(){// retorno un array que contiene los valores de los dados tirados
			int[] valores = new int[5];
			InterDADO[] dados = this.dados.getDados();
			for(int i = 0; i < dados.length; i++){
				valores[i] = dados[i].getValor();
			}
			return valores;
		}
		
		public boolean[] dadosDisp(){
			return dados.getDadosDisp();
		}
		
		
		
		@Override
		public void addObserver(Object o) { //OBSERVER
			if (o instanceof Observer){
				Observer ob = (Observer) o;
				observadores.add(ob);
			}
			
			
		}

		@Override
		public void notifyObservers(int op) {
			for (Observer o : observadores){
				try {
					o.Update(this, op);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}


		public int getPuntosActuales() {
			return this.puntosActuales;
		}
		
		

		public String getJugadorEliminado() {
			String j = jugadorEliminado;
			this.jugadorEliminado = "";
			return j;
		}
		
		public int[] getPuntajes(){ // obtengo los puntajes para cada jugador
			int s = jugadores.size();
			int[] p = new int[s];
			for(int i = 0; i < jugadores.size(); i++){
				p[i] = jugadores.get(i).getPuntos();
			}
			return p;
		}
		
		
		
		public void eliminarJugador(String nombre){
			int i = -1;
			for(Jugador j : jugadores){
				if(nombre.equals(j.getNombre())){
					i = jugadores.indexOf(j);
				}
			}
			if (i != -1){
				this.jugadores.remove(i);
				this.notifyObservers(17);
			}
		}
		
		
		
		@Override
		public int getId() {
			return id;
		}

		
		@Override
		public int getJA() {
			
			return this.jugadorActual;
		}
        
		
		@Override
		public int getPA() {
			return this.puntosActuales;
		}


		@Override
		public String getPuedeTerminar() {
			if (this.puedeTerminar){
				return "T";
			}else{
				return "F";
			}
		}


		@Override
		public int getEstado() {
			if (this.estadoActual == estados.Preparando){
				return 1;
			}else if (this.estadoActual == estados.Comenzado){
				return 2;
			}else{
				return 3;
			}
		}
		

		public boolean getTerminar(){
			return this.puedeTerminar;
		}


		public String getJugadorActual(){
			return jugadores.get(jugadorActual).getNombre();
		}
		
		@Override
		public estados getEstadoActual() {
			return this.estadoActual;
		}

		@Override
		public void calcularptosyacht() {
			puntosActuales=getPA();
			InterDADO[] dados = this.dados.getDados();
			int valor = dados[0].getValor();
			boolean b= false;
			for(int i = 1; i < dados.length; i++){
				 int valorsig = dados[i].getValor();
				if (valorsig != valor){
				   b=false;}
			}
			if (b==false){
				puntosActuales= puntosActuales+0;
			}else{
				puntosActuales= puntosActuales+50;
			}
			this.jugadores.get(jugadorActual).sumarPuntos(puntosActuales);
			this.notifyObservers(2);
			this.notifyObservers(7);
		}

		@Override
		public void calcularptosescmayor() {
			InterDADO[] dados = this.dados.getDados();
			int val1= dados[0].getValor();
			int val2= dados[1].getValor();
			int val3= dados[2].getValor();
			int val4= dados[3].getValor();
			int val5= dados[4].getValor();
			arraydados = new int[]{val1,val2,val3,val4,val5};
			Arrays.sort(arraydados);
			boolean b=false;
			int valor = arraydados[0];
			for(int i = 1; i < arraydados.length; i++){
				 int valorsig = arraydados[i];
				if (valorsig == valor+i){
				   b=true;
				}else{
					b=false;
				}
			}
				if (b==false){
					puntosActuales= puntosActuales+0;
				}else{
					puntosActuales= puntosActuales+30;
				}
				this.jugadores.get(jugadorActual).sumarPuntos(puntosActuales);
				this.notifyObservers(2);
				this.notifyObservers(7);
			
		}

		@Override
		public void calcularptosescmenor() {
			InterDADO[] dados = this.dados.getDados();
			int val1= dados[0].getValor();
			int val2= dados[1].getValor();
			int val3= dados[2].getValor();
			int val4= dados[3].getValor();
			int val5= dados[4].getValor();
			arraydados = new int[]{val1,val2,val3,val4,val5};
			Arrays.sort(arraydados);
			int c=0;
			int valor = arraydados[0];
			for(int i = 1; i < arraydados.length; i++){
				 int valorsig = arraydados[i];
				if(valorsig == valor+i){
				   c+=1;}
				if (valorsig== valor){
					c+=0;
				}
			}
				if (c<=3){
					puntosActuales= puntosActuales+0;
				}else{
					puntosActuales= puntosActuales+15;
				}
				this.jugadores.get(jugadorActual).sumarPuntos(puntosActuales);
				this.notifyObservers(2);
				this.notifyObservers(7);
			
		}
	


		@Override
		public void calcularptosiguales() {
			InterDADO[] dados = this.dados.getDados();
			int val1= dados[0].getValor();
			int val2= dados[1].getValor();
			int val3= dados[2].getValor();
			int val4= dados[3].getValor();
			int val5= dados[4].getValor();
			int nrosuma=0;
			arraydados = new int[]{val1,val2,val3,val4,val5};
			Arrays.sort(arraydados);
			//Pruebo desde el principio
			int valor = arraydados[0];
			int contador=1;
			for(int i = 1; i < arraydados.length; i++){
				 int valorsig = arraydados[i];
				if (valorsig == valor){
				   contador += 1;
				   }else{
					   nrosuma=valorsig;
				   }
			}
			//Pruebo desde el ultimo elemento al primero
			if (contador<4){
				  contador=0;
				  valor = arraydados[4];
				 contador=1;
				for(int i = 3 ; i >= 0; i--){
					 int valorsig = arraydados[i];
					if (valorsig == valor){
					   contador += 1;
					}else{
						nrosuma=valorsig;
					}		
				}
			}
			if (contador<4){
				puntosActuales+=0;
			}else{
				puntosActuales+= (valor*4)+nrosuma;
			}
			this.jugadores.get(jugadorActual).sumarPuntos(puntosActuales);
			this.notifyObservers(2);
			this.notifyObservers(7);
				}
	
		

		@Override
		public void calcularptosfull() {
			InterDADO[] dados = this.dados.getDados();
			int val1= dados[0].getValor();
			int val2= dados[1].getValor();
			int val3= dados[2].getValor();
			int val4= dados[3].getValor();
			int val5= dados[4].getValor();
			boolean b1=false;
			boolean b2=false;
			arraydados = new int[]{val1,val2,val3,val4,val5};
			Arrays.sort(arraydados);
			//Primer posibilidad
			int valor = arraydados[0];
			for(int i = 1; i < arraydados.length; i++){
				 int valorsig = arraydados[i];
				if ((valorsig == valor) && i<=2){
				    b1=true;}
				 if (valorsig!=valor && i>=3){
						 b2=true;
						 valorsig=valor;}
				 if (valorsig!=valor && i==4){
					 b2=false;
				 }
			}
		  if (b1==false && b2==false){		 
            //Segunda posibilidad
			valor = arraydados[0];
			for(int i = 1; i < arraydados.length; i++){
				 int valorsig = arraydados[i];
				if ((valorsig == valor) && i<=1){
				    b1=true;}
				 if (valorsig!=valor && i>=2){
						 b2=true;
						 valorsig=valor;}
				 if (valorsig!=valor && i>=3){
					 b2=false;
				 }
				   }
		  }
			if (b1==true && b2==true){
				puntosActuales+=val1+val2+val3+val4+val5;
			}
			this.jugadores.get(jugadorActual).sumarPuntos(puntosActuales);
			this.notifyObservers(2);
			this.notifyObservers(7);
			}
		
		

		@Override
		public void calcularptoseleccion() {
			InterDADO[] dados = this.dados.getDados();
			int val1= dados[0].getValor();
			int val2= dados[1].getValor();
			int val3= dados[2].getValor();
			int val4= dados[3].getValor();
			int val5= dados[4].getValor();
			puntosActuales+= val1+val2+val3+val4+val5;
		this.jugadores.get(jugadorActual).sumarPuntos(puntosActuales);
		this.notifyObservers(2);
		this.notifyObservers(7);
			
		}
		

		@Override
		public void finalizarTurno(boolean b) {
			if (jugadorActual + 1 < jugadores.size()){
				jugadorActual++;
			}else{
			   jugadorActual=0;}
			    nrodetiro=0;
			    puntosActuales=0;
				this.notifyObservers(5);
				dados.reiniciarDados();
				this.notifyObservers(10);
				this.notifyObservers(6);
			}

		@Override
		public void establecerorden() {  
			Collections.sort(jugadores);
			}

		@Override
		public void calcularptosas() {
			InterDADO[] dados = this.dados.getDados();
			int val1= dados[0].getValor();
			    if (val1==1){
			    	puntosActuales+=1;}
			int val2= dados[1].getValor();
			 if (val2==1){
			    	puntosActuales+=1;}
			int val3= dados[2].getValor();
			 if (val3==1){
			    	puntosActuales+=1;}
			int val4= dados[3].getValor();
			 if (val4==1){
			    	puntosActuales+=1;}
			int val5= dados[4].getValor();
			 if (val5==1){
			    	puntosActuales+=1;}
			 this.jugadores.get(jugadorActual).sumarPuntos(puntosActuales);
				this.notifyObservers(2);
				this.notifyObservers(7);
			}
			

		@Override
		public void calcularptosdos() {
			
			InterDADO[] dados = this.dados.getDados();
			int val1= dados[0].getValor();
			    if (val1==2){
			    	puntosActuales+=2;}
			int val2= dados[1].getValor();
			 if (val2==2){
			    	puntosActuales+=2;}
			int val3= dados[2].getValor();
			 if (val3==2){
			    	puntosActuales+=2;}
			int val4= dados[3].getValor();
			 if (val4==2){
			    	puntosActuales+=2;}
			int val5= dados[4].getValor();
			 if (val5==2){
			    	puntosActuales+=2;}
			 this.jugadores.get(jugadorActual).sumarPuntos(puntosActuales);
				this.notifyObservers(2);
				this.notifyObservers(7);
			}
		

		@Override
		public void calcularptostres() {
			InterDADO[] dados = this.dados.getDados();
			int val1= dados[0].getValor();
			    if (val1==3){
			    	puntosActuales+=3;}
			int val2= dados[1].getValor();
			 if (val2==3){
			    	puntosActuales+=3;}
			int val3= dados[2].getValor();
			 if (val3==3){
			    	puntosActuales+=3;}
			int val4= dados[3].getValor();
			 if (val4==3){
			    	puntosActuales+=3;}
			int val5= dados[4].getValor();
			 if (val5==3){
			    	puntosActuales+=3;}
			 this.jugadores.get(jugadorActual).sumarPuntos(puntosActuales);
				this.notifyObservers(2);
				this.notifyObservers(7);
			}
		

		@Override
		public void calcularptoscuatro() {
			InterDADO[] dados = this.dados.getDados();
			int val1= dados[0].getValor();
			    if (val1==4){
			    	puntosActuales+=4;}
			int val2= dados[1].getValor();
			 if (val2==4){
			    	puntosActuales+=4;}
			int val3= dados[2].getValor();
			 if (val3==4){
			    	puntosActuales+=4;}
			int val4= dados[3].getValor();
			 if (val4==4){
			    	puntosActuales+=4;}
			int val5= dados[4].getValor();
			 if (val5==4){
			    	puntosActuales+=4;}
			 this.jugadores.get(jugadorActual).sumarPuntos(puntosActuales);
				this.notifyObservers(2);
				this.notifyObservers(7);
			}
			
		

		@Override
		public void calcularptoscinco() {
			InterDADO[] dados = this.dados.getDados();
			int val1= dados[0].getValor();
			    if (val1==5){
			    	puntosActuales+=5;}
			int val2= dados[1].getValor();
			 if (val2==5){
			    	puntosActuales+=5;}
			int val3= dados[2].getValor();
			 if (val3==5){
			    	puntosActuales+=5;}
			int val4= dados[3].getValor();
			 if (val4==5){
			    	puntosActuales+=5;}
			int val5= dados[4].getValor();
			 if (val5==5){
			    	puntosActuales+=5;}
			 this.jugadores.get(jugadorActual).sumarPuntos(puntosActuales);
				this.notifyObservers(2);
				this.notifyObservers(7);
			}
			
		
		@Override
		public void calcularptosseis() {
			InterDADO[] dados = this.dados.getDados();
			int val1= dados[0].getValor();
			    if (val1==6){
			    	puntosActuales+=6;}
			int val2= dados[1].getValor();
			 if (val2==6){
			    	puntosActuales+=6;}
			int val3= dados[2].getValor();
			 if (val3==6){
			    	puntosActuales+=6;}
			int val4= dados[3].getValor();
			 if (val4==6){
			    	puntosActuales+=6;}
			int val5= dados[4].getValor();
			 if (val5==6){
			    	puntosActuales+=6;}
			}

		
		
	

	

		@Override
		public String getGanadorP(String n) {
			int [] puntajes= this.getPuntajes();
			ArrayList<String> jugadores = this.getJugadores();
			int mayor= puntajes[0];
			String jug= jugadores.get(0);
			for(int i = 0; i < puntajes.length; i++){
				 if (puntajes[i]>mayor){
					 mayor=puntajes[i];
					 jug=jugadores.get(i);
		}
			}
			ganador=jug;
			try {
				oos = new MiObjectOutputStream(new FileOutputStream("ranking.oo", true));
				oos.writeObject(ganador + " " + "Puntos:" + " " + String.valueOf(mayor) + " ");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				oos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return ganador;
		}

	
		
		@Override
		public ArrayList<String> veranking() { //retorno en un arraylist el contenido del archivo ranking
			ArrayList<String> mostrar = new ArrayList<String>();
			try{	
			try {
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream("ranking.oo"));
				    Object salida =ois.readObject();
				  while (salida != null){
						 mostrar.add((String)salida);
						 salida= ois.readObject();
					  }
				  ois.close();
					}
			catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}catch (IOException e) {
			}
		return mostrar;
		}
		
		
		public void generarpersistenciaranking() { 
			try {
				oos = new ObjectOutputStream(new FileOutputStream("ranking.oo"));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
			}
		}

		@Override
		public void guardarpartida() {
			generarpersistenciapozo();
			obtenerpozopartida();//obtengo (si lo hubiera) el pozo de la partida
			int s = jugadores.size();
			String[] ju= new String[s];
			for (int i = 0; i < jugadores.size(); i++){
				ju[i]=jugadores.get(i).getNombre();
			}	// obtengo los nombres de cada jugador
				int[] p = new int[s];
				for(int j = 0; j < jugadores.size(); j++){
					p[j] = jugadores.get(j).getPuntos();
				}// obtengo los puntajes de cada jugador	
			try{	 
				for(int h = 0; h < jugadores.size(); h++){
					partida = new MiObjectOutputStream(new FileOutputStream("partida.oo", true));
					partida.writeObject((String)ju[h]);
					partida.writeObject(String.valueOf(p[h]));
				}
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
					}
               try {
				partida.close();
			} catch (IOException e) {
			}
		
			}
		
				
						
		

		@Override
		public int cargarpartida() {
			recuperarpozopartida();
			String jug;
			String puntos;
			try{	
			try {
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream("partida.oo"));
				    Object salida =ois.readObject();
				  while (salida != null){
					  jug=((String)salida);//recupero jugador
					  Jugador nj = new Jugador(jugadores.size(), jug);
							jugadores.add(nj);
							this.notifyObservers(1);//se agrega exitosamente
							salida= ois.readObject();//recupero su puntaje
						  puntos=((String)salida);
						    nj.sumarPuntos(Integer.parseInt(puntos));
						    salida=ois.readObject();//recupero siguiente jugador
					}
				  ois.close();
			}
			catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}catch (IOException e) {
			}
			return apuesta;
		}

		@Override
		public void generarpersistenciapartida() {
			try{  
				partida = new ObjectOutputStream(new FileOutputStream("partida.oo"));
				partida.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
				}
		}

		@Override
		public void tiro5() {
			if (nrodetiro==1){ //se emite un aviso sobre ultimo tiro al jugador
	              this.notifyObservers(16);
				}
			tirada= new Random();
			InterDADO[] dados = this.dados.getDados();
			int v= tirada.nextInt(6)+1;
			dados[4].setValor(v);
			nrodetiro= nrodetiro + 1;
			this.notifyObservers(4);
			
		}

		@Override
		public void tiro4() {
			if (nrodetiro==1){ //se emite un aviso sobre ultimo tiro al jugador
				  this.notifyObservers(16);
				}
			tirada= new Random();
			InterDADO[] dados = this.dados.getDados();
			int v= tirada.nextInt(6)+1;
			dados[3].setValor(v);
			nrodetiro= nrodetiro + 1;
			this.notifyObservers(4);
			
		}

		@Override
		public void tiro3() {
			if (nrodetiro==1){ //se emite un aviso sobre ultimo tiro al jugador
				  this.notifyObservers(16);
				}
			tirada= new Random();
			InterDADO[] dados = this.dados.getDados();
			int v= tirada.nextInt(6)+1;
			dados[2].setValor(v);
			nrodetiro= nrodetiro + 1;
			this.notifyObservers(4);
			
		}

		@Override
		public void tiro2() {
			if (nrodetiro==1){ //se emite un aviso sobre ultimo tiro al jugador
				  this.notifyObservers(16);
				}
			tirada= new Random();
			InterDADO[] dados = this.dados.getDados();
			int v= tirada.nextInt(6)+1;
			dados[1].setValor(v);
			nrodetiro= nrodetiro + 1;
			this.notifyObservers(4);
			
		}

		@Override
		public void tiro1() {
			if (nrodetiro==1){ //se emite un aviso sobre ultimo tiro al jugador
				  this.notifyObservers(16);
				}
			tirada= new Random();
			InterDADO[] dados = this.dados.getDados();
			int v= tirada.nextInt(6)+1;
			dados[0].setValor(v);
			nrodetiro= nrodetiro + 1;
			this.notifyObservers(4);
			
		}

		@Override
		public void tiro34() {
			if (nrodetiro==1){ //se emite un aviso sobre ultimo tiro al jugador
				  this.notifyObservers(16);
				}
			tirada= new Random();
			InterDADO[] dados = this.dados.getDados();
			int v= tirada.nextInt(6)+1;
			int v1= tirada.nextInt(6)+1;
			dados[3].setValor(v);
			dados[2].setValor(v1);
			nrodetiro= nrodetiro + 1;
			this.notifyObservers(4);
			
		}

		@Override
		public void tiro45() {
			if (nrodetiro==1){ //se emite un aviso sobre ultimo tiro al jugador
				  this.notifyObservers(16);
				}
			tirada= new Random();
			InterDADO[] dados = this.dados.getDados();
			int v= tirada.nextInt(6)+1;
			int v1= tirada.nextInt(6)+1;
			dados[4].setValor(v);
			dados[3].setValor(v1);
			nrodetiro= nrodetiro + 1;
			this.notifyObservers(4);
			
		}

		@Override
		public void tiro35() {
			if (nrodetiro==1){ //se emite un aviso sobre ultimo tiro al jugador
				  this.notifyObservers(16);
				}
			tirada= new Random();
			InterDADO[] dados = this.dados.getDados();
			int v= tirada.nextInt(6)+1;
			int v1= tirada.nextInt(6)+1;
			dados[2].setValor(v);
			dados[4].setValor(v1);
			nrodetiro= nrodetiro + 1;
			this.notifyObservers(4);
			
		}
		
		@Override
		public void tiro24() {
			if (nrodetiro==1){ //se emite un aviso sobre ultimo tiro al jugador
				  this.notifyObservers(16);
				}
			tirada= new Random();
			InterDADO[] dados = this.dados.getDados();
			int v= tirada.nextInt(6)+1;
			int v1= tirada.nextInt(6)+1;
			dados[1].setValor(v);
			dados[3].setValor(v1);
			nrodetiro= nrodetiro + 1;
			this.notifyObservers(4);
			
		}
		public void tiro13() {
			if (nrodetiro==1){ //se emite un aviso sobre ultimo tiro al jugador
				  this.notifyObservers(16);
				}
			tirada= new Random();
			InterDADO[] dados = this.dados.getDados();
			int v= tirada.nextInt(6)+1;
			int v1= tirada.nextInt(6)+1;
			dados[0].setValor(v);
			dados[2].setValor(v1);
			nrodetiro= nrodetiro + 1;
			this.notifyObservers(4);
			
		}
		public void tiro14() {
			if (nrodetiro==1){ //se emite un aviso sobre ultimo tiro al jugador
				  this.notifyObservers(16);
				}
			tirada= new Random();
			InterDADO[] dados = this.dados.getDados();
			int v= tirada.nextInt(6)+1;
			int v1= tirada.nextInt(6)+1;
			dados[0].setValor(v);
			dados[3].setValor(v1);
			nrodetiro= nrodetiro + 1;
			this.notifyObservers(4);
			
		}

		@Override
		public void tiro25() {
			if (nrodetiro==1){ //se emite un aviso sobre ultimo tiro al jugador
				  this.notifyObservers(16);
				}
			tirada= new Random();
			InterDADO[] dados = this.dados.getDados();
			int v= tirada.nextInt(6)+1;
			int v1= tirada.nextInt(6)+1;
			dados[1].setValor(v);
			dados[4].setValor(v1);
			nrodetiro= nrodetiro + 1;
			this.notifyObservers(4);
			
		}

		@Override
		public void tiro15() {
			if (nrodetiro==1){ //se emite un aviso sobre ultimo tiro al jugador
				  this.notifyObservers(16);
				}
			tirada= new Random();
			InterDADO[] dados = this.dados.getDados();
			int v= tirada.nextInt(6)+1;
			int v1= tirada.nextInt(6)+1;
			dados[0].setValor(v);
			dados[4].setValor(v1);
			nrodetiro= nrodetiro + 1;
			this.notifyObservers(4);
			
		}

		@Override
		public void tiro23() {
			if (nrodetiro==1){ //se emite un aviso sobre ultimo tiro al jugador
				  this.notifyObservers(16);
				}
			tirada= new Random();
			InterDADO[] dados = this.dados.getDados();
			int v= tirada.nextInt(6)+1;
			int v1= tirada.nextInt(6)+1;
			dados[1].setValor(v);
			dados[2].setValor(v1);
			nrodetiro= nrodetiro + 1;
			this.notifyObservers(4);
			
		}

		@Override
		public void tiro12() {
			if (nrodetiro==1){ //se emite un aviso sobre ultimo tiro al jugador
				  this.notifyObservers(16);
				}
			tirada= new Random();
			InterDADO[] dados = this.dados.getDados();
			int v= tirada.nextInt(6)+1;
			int v1= tirada.nextInt(6)+1;
			dados[0].setValor(v);
			dados[1].setValor(v1);
			nrodetiro= nrodetiro + 1;
			this.notifyObservers(4);
			
		}

		@Override
		public void tiro123() {
			if (nrodetiro==1){ //se emite un aviso sobre ultimo tiro al jugador
				  this.notifyObservers(16);
				}
			tirada= new Random();
			InterDADO[] dados = this.dados.getDados();
			int v= tirada.nextInt(6)+1;
			int v1= tirada.nextInt(6)+1;
			int v2= tirada.nextInt(6)+1;
			dados[0].setValor(v);
			dados[1].setValor(v1);
			dados[2].setValor(v2);
			nrodetiro= nrodetiro + 1;
			this.notifyObservers(4);
			
		}

		@Override
		public void tiro345() {
			if (nrodetiro==1){ //se emite un aviso sobre ultimo tiro al jugador
				  this.notifyObservers(16);
				}
			tirada= new Random();
			InterDADO[] dados = this.dados.getDados();
			int v= tirada.nextInt(6)+1;
			int v1= tirada.nextInt(6)+1;
			int v2= tirada.nextInt(6)+1;
			dados[2].setValor(v);
			dados[3].setValor(v1);
			dados[4].setValor(v2);
			nrodetiro= nrodetiro + 1;
			this.notifyObservers(4);
			
		}
		
		@Override
		public void tiro125() {
			if (nrodetiro==1){ //se emite un aviso sobre ultimo tiro al jugador
				  this.notifyObservers(16);
				}
			tirada= new Random();
			InterDADO[] dados = this.dados.getDados();
			int v= tirada.nextInt(6)+1;
			int v1= tirada.nextInt(6)+1;
			int v2= tirada.nextInt(6)+1;
			dados[0].setValor(v);
			dados[1].setValor(v1);
			dados[4].setValor(v2);
			nrodetiro= nrodetiro + 1;
			this.notifyObservers(4);
			
		}
		public void tiro134() {
			if (nrodetiro==1){ //se emite un aviso sobre ultimo tiro al jugador
				  this.notifyObservers(16);
				}
			tirada= new Random();
			InterDADO[] dados = this.dados.getDados();
			int v= tirada.nextInt(6)+1;
			int v1= tirada.nextInt(6)+1;
			int v2= tirada.nextInt(6)+1;
			dados[0].setValor(v);
			dados[2].setValor(v1);
			dados[3].setValor(v2);
			nrodetiro= nrodetiro + 1;
			this.notifyObservers(4);
			
		}

		@Override
		public void tiro234() {
			if (nrodetiro==1){ //se emite un aviso sobre ultimo tiro al jugador
				  this.notifyObservers(16);
				}
			tirada= new Random();
			InterDADO[] dados = this.dados.getDados();
			int v= tirada.nextInt(6)+1;
			int v1= tirada.nextInt(6)+1;
			int v2= tirada.nextInt(6)+1;
			dados[1].setValor(v);
			dados[2].setValor(v1);
			dados[3].setValor(v2);
			nrodetiro= nrodetiro + 1;
			this.notifyObservers(4);
			
		}	
		
		@Override
		public void tiro135() {
			if (nrodetiro==1){ //se emite un aviso sobre ultimo tiro al jugador
				  this.notifyObservers(16);
				}
			tirada= new Random();
			InterDADO[] dados = this.dados.getDados();
			int v= tirada.nextInt(6)+1;
			int v1= tirada.nextInt(6)+1;
			int v2= tirada.nextInt(6)+1;
			dados[0].setValor(v);
			dados[2].setValor(v1);
			dados[4].setValor(v2);
			nrodetiro= nrodetiro + 1;
			this.notifyObservers(4);
			
		}
		public void tiro124() {
			if (nrodetiro==1){ //se emite un aviso sobre ultimo tiro al jugador
				  this.notifyObservers(16);
				}
			tirada= new Random();
			InterDADO[] dados = this.dados.getDados();
			int v= tirada.nextInt(6)+1;
			int v1= tirada.nextInt(6)+1;
			int v2= tirada.nextInt(6)+1;
			dados[0].setValor(v);
			dados[1].setValor(v1);
			dados[3].setValor(v2);
			nrodetiro= nrodetiro + 1;
			this.notifyObservers(4);
			
		}

		@Override
		public void tiro145() {
			if (nrodetiro==1){ //se emite un aviso sobre ultimo tiro al jugador
				  this.notifyObservers(16);
				}
			tirada= new Random();
			InterDADO[] dados = this.dados.getDados();
			int v= tirada.nextInt(6)+1;
			int v1= tirada.nextInt(6)+1;
			int v2= tirada.nextInt(6)+1;
			dados[0].setValor(v);
			dados[3].setValor(v1);
			dados[4].setValor(v2);
			nrodetiro= nrodetiro + 1;
			this.notifyObservers(4);
			
		}

		@Override
		public void tiro1234() {
			if (nrodetiro==1){ //se emite un aviso sobre ultimo tiro al jugador
				  this.notifyObservers(16);
				}
			tirada= new Random();
			InterDADO[] dados = this.dados.getDados();
			int v= tirada.nextInt(6)+1;
			int v1= tirada.nextInt(6)+1;
			int v2= tirada.nextInt(6)+1;
			int v3= tirada.nextInt(6)+1;
			dados[0].setValor(v);
			dados[1].setValor(v1);
			dados[2].setValor(v2);
			dados[3].setValor(v3);
			nrodetiro= nrodetiro + 1;
			this.notifyObservers(4);
			
		}

		@Override
		public void tiro2345() {
			if (nrodetiro==1){ //se emite un aviso sobre ultimo tiro al jugador
				  this.notifyObservers(16);
				}
			tirada= new Random();
			InterDADO[] dados = this.dados.getDados();
			int v= tirada.nextInt(6)+1;
			int v1= tirada.nextInt(6)+1;
			int v2= tirada.nextInt(6)+1;
			int v3= tirada.nextInt(6)+1;
			dados[1].setValor(v);
			dados[2].setValor(v1);
			dados[3].setValor(v2);
			dados[4].setValor(v3);
			nrodetiro= nrodetiro + 1;
			this.notifyObservers(4);
			
		}

		@Override
		public String apuesta(int valor) {
			apuesta=valor;
			return pozo=String.valueOf(apuesta);
			
		}
		
		public void generarpersistenciapozo(){
		try {
			archivopozo = new ObjectOutputStream(new FileOutputStream("filepozo.oo"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
		}
		}
		
		public void obtenerpozopartida(){// GUARDO VALOR DEL POZO
			try {
				archivopozo = new MiObjectOutputStream(new FileOutputStream("filepozo.oo", true));
				apuesta(apuesta);
				archivopozo.writeObject(pozo);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
			}
			
		}
		
		public void recuperarpozopartida(){// RETOMO EL VALOR DEL POZO
				try {
					ObjectInputStream n = new ObjectInputStream(new FileInputStream("filepozo.oo"));
					try {
						Object salida = n.readObject();
						pozo=(String)salida;
						apuesta=Integer.parseInt(pozo);
						n.close();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					} catch (IOException e) {
					}
			
		}
		
	}
	
	
			
			
	
		
		
	
		


