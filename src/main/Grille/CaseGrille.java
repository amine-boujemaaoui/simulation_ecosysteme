package main.Grille;

import java.awt.Color;
import java.awt.Image;
import java.util.LinkedList;

public class CaseGrille {

	private Color c;
	private Image icon;
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

	public void addDisqueAnimaux(int rayon, Color c, Image icon) {
		lAnimaux.add(new Disque(rayon, c, icon));
	}

	public void addDisqueVegetaux(int rayon, Color c, Image icon) {
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

	public Image getIcon() {
		return icon;
	}

	public void setIcon(Image icon) {
		this.icon = icon;
	}

}
