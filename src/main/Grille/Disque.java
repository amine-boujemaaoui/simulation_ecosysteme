package main.Grille;

import java.awt.Color;
import java.awt.Image;

public class Disque {

	private int rayon;
	private Color c;
	private Image icon;

	Disque() {
	}

	public Disque(int rayon, Color c, Image icon) {
		this.rayon = rayon;
		this.c = c;
		this.icon = icon;
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
	
	public void setIcon(Image icon) {
		this.icon = icon;
	}
	
	public Image getIcon() {
		return icon;
	}

}
