package main.Grille;

import java.awt.Color;
import java.util.LinkedList;

import javax.swing.ImageIcon;

public class CaseGrille {

	private Color c;
	private ImageIcon icon;
	public LinkedList<Disque> lAnimaux;
	public LinkedList<Disque> lVegetaux;

	public CaseGrille() {
		lAnimaux = new LinkedList<Disque>();
		lVegetaux = new LinkedList<Disque>();
	}

	public void setCouleur(Color c) {
		this.c = c;
	}

	public Color getCouleur() {
		return c;
	}

	public void addDisqueAnimaux(int rayon, Color c, ImageIcon icon) {
		lAnimaux.add(new Disque(rayon, c, icon));
	}

	public void addDisqueVegetaux(int rayon, Color c, ImageIcon icon) {
		lVegetaux.add(new Disque(rayon, c, icon));
	}

	public void resetlDisquesAnimaux() {
		lAnimaux = new LinkedList<Disque>();
	}

	public void resetlDisquesVegetaux() {
		lVegetaux = new LinkedList<Disque>();
	}

	public void resetlDisques() {
		lAnimaux = new LinkedList<Disque>();
		lVegetaux = new LinkedList<Disque>();
	}

	public ImageIcon getIcon() {
		return icon;
	}

	public void setIcon(ImageIcon icon) {
		this.icon = icon;
	}

}
