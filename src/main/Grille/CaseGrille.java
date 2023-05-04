package main.Grille;

import java.awt.Color;
import java.util.LinkedList;

public class CaseGrille {

	private Color c;
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

	public void addDisque(int rayon, Color c) {
		lDisques.add(new Disque(rayon, c));
	}

}
