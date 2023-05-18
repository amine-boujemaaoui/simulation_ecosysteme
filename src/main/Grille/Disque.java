package main.Grille;

import java.awt.Color;
import javax.swing.ImageIcon;

public class Disque {

	private int rayon;
	private Color c;
	private ImageIcon icon;

	Disque() {
	}

	public Disque(int rayon, Color c, ImageIcon icon) {
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
	
	public void setIcon(ImageIcon icon) {
		this.icon = icon;
	}
	
	public ImageIcon getIcon() {
		return icon;
	}

}
