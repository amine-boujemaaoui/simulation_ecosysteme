package main.Grille;

import java.awt.Color;

public class Disque {

	private int rayon;
	private Color c;

	Disque() {
	}

	public Disque(int rayon, Color c) {
		this.rayon = rayon;
		this.c = c;
	}

	public int getRayon() {
		return rayon;
	}

	public void setRayon(int rayon) {
		this.rayon = rayon;
	}

	public Color getCouleur() {
		return c;
	}

	public void setCouleur(Color c) {
		this.c = c;
	}

}
