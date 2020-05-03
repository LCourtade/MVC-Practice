package Yahtzee;

import java.io.FileNotFoundException;
import java.io.IOException;

import Controlador.Controlador;
import Controlador.ObserverModelo;
import Modelo.Juego;
import Vista.VistaConsola;

public class JuegoConsola {
	public static void main(String[] args){
		Juego j = new Juego();
		VistaConsola view = new VistaConsola();
		Controlador c= new Controlador(j,view);
	    ObserverModelo observador = new ObserverModelo(c);
		j.addObserver(observador);
        view.iniciar();
	}
	}

