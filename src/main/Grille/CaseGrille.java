package main.Grille;

import java.awt.Color;
import java.awt.Image;
import java.util.LinkedList;

public class CaseGrille {

	private Color c;
	private Image icon;
	public LinkedList<Disque> lDisques;

	public CaseGrille() {
		lDisques = new LinkedList<Disque>();
	}
	

	public void setCouleur(Color c) {
		this.c = c;
	}

	public Color getCouleur() {
		return c;
	}

	public void addDisque(int rayon, Color c, Image icon) {
		lDisques.add(new Disque(rayon, c, icon));
	}
	
	public void resetlDisques() {
		lDisques = new LinkedList<Disque>();
	}



	public Image getIcon() {
		return icon;
	}



	public void setIcon(Image icon) {
		this.icon = icon;
	}

}
