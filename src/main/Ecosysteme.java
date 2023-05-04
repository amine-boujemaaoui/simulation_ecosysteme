package main;

import main.Animaux.Animal;
import main.Grille.GrilleNature;
import main.TypeZones.*;

import java.util.ArrayList;
import java.util.Random;

public class Ecosysteme {
	private int nbZonesH;
	private int nbZonesL;
	private GrilleNature grille;
	private Zone[][] zones;
	private ArrayList<TypeZone> typeZones = new ArrayList<TypeZone>();;
	

	public Ecosysteme(int nbZonesL, int nbZonesH) {
		super();
		this.nbZonesH = nbZonesH;
		this.nbZonesL = nbZonesL;
		this.grille = new GrilleNature(nbZonesL, nbZonesH, 80);
		
		typeZones.add(new Foret());
		typeZones.add(new Plaine());
		
		Random r = new Random();
		this.zones = new Zone[nbZonesL][nbZonesH];
		for (int i = 0; i < nbZonesL; i++)
			for (int j = 0; j < nbZonesH; j++) {
				zones[i][j] = new Zone(r.nextDouble(2.4, 19), r.nextDouble(6, 24), this);
				zones[i][j].verifierTypeZone();
			}
	}

	public ArrayList<TypeZone> getTypeZones() {
		return typeZones;
	}

	public int getNbZonesH() {
		return nbZonesH;
	}

	public int getNbZonesL() {
		return nbZonesL;
	}

	public GrilleNature getGrille() {
		return grille;
	}
	
	public void deplacerAnimal(Animal animal, int x, int y) {
		animal.getZone_actuel().removeAnimal(animal);
		this.zones[x][y].addAnimal(animal);
	}
}
