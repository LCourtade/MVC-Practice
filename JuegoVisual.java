package Yahtzee;

import Controlador.Controlador;
import Controlador.ObserverModelo;
import Modelo.Juego;

import Vista.VistaGrafica;

public class JuegoVisual {

	public static void main(String[] args) {
		try {
			Juego j = new Juego();
			VistaGrafica view = new VistaGrafica();
			Controlador c = new Controlador(j, view);
			ObserverModelo observador = new ObserverModelo(c);
			j.addObserver(observador);
			view.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}