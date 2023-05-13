package main;

import main.Animaux.Animal;
import main.Animaux.Insectes.Abeille;
import main.Animaux.Insectes.Fourmi;
import main.Animaux.Oiseaux.Pigeon;
import main.Execeptions.BoirException;
import main.Execeptions.ChangerTemperatureException;
import main.Execeptions.ReproduireException;
import main.Grille.Grille;
import main.TypeZones.*;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

public class Ecosysteme {
	private int nbZonesH;
	private int nbZonesL;
	private Grille grille;
	private Zone[][] zones;
	private ArrayList<TypeZone> typeZones = new ArrayList<TypeZone>();
	private Random r = new Random();
	private int cycle;
	private int[][] defaultZones = { 
			{ 0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2 },
			{ 0, 0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2 },
			{ 1, 0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2 },
			{ 1, 0, 0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2 },
			{ 1, 1, 0, 0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2 },
			{ 1, 1, 1, 0, 0, 0, 2, 2, 2, 2, 2, 2, 2, 2, 2 },
			{ 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 2, 2, 2, 2, 2 },
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 2, 2, 2, 2 },
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 2, 2, 2 },
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 2, 2, 2 },
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 2 },
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 2 },
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0 },
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0 },
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0 },
			};

	public Ecosysteme(int nbZonesL, int nbZonesH, ArrayList<TypeZone> typeZones) {
		super();
		this.nbZonesH = nbZonesH;
		this.nbZonesL = nbZonesL;
		this.grille = new Grille(nbZonesL, nbZonesH, 60);
		this.zones = new Zone[nbZonesL][nbZonesH];
		this.cycle = 0;

		// DEFAULTS typeZones
		this.typeZones.add(new Riviere());
		this.typeZones.add(new Foret());
		this.typeZones.add(new Plaine());
		this.typeZones.add(new Desert());

		// PARAMETER TYPE ZONES
		/*
		 * if (typeZones.size() > 0) { typeZones.forEach((tz) -> {
		 * this.typeZones.add(tz); }); }
		 */

		initdefaultZones();
		initRandomNbAnimaux();
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

	public Zone getZone(int i, int j) {
		return zones[i][j];
	}

	public Grille getGrille() {
		return grille;
	}
	
	public int getCycle() {
		return cycle;
	}
	
	public void nextCycle() {
		this.cycle++;
	}

	public void deplacerAnimal(Animal animal, int x, int y) {
		animal.getZone_actuel().removeAnimal(animal);
		this.zones[x][y].addAnimal(animal);
	}

	private void redessine() {
		for (int i = 0; i < nbZonesL; i++) {
			for (int j = 0; j < nbZonesH; j++) {
				grille.setCycle(cycle);
				grille.resetlDisques(i, j);
				grille.colorieFond(i, j, zones[i][j].getTypeZone().getC());
				grille.iconFond(i, j, zones[i][j].getTypeZone().getTypeZoneIcon());
				grille.addDisque(i, j, zones[i][j].getNbInsecte() / 2, Color.BLUE);
				grille.addDisque(i, j, zones[i][j].getNbOiseau() / 2, Color.RED);
			}
		}
		grille.redessine();
	}

	public void initdefaultZones() {
		for (int i = 0; i < nbZonesL; i++) {
			for (int j = 0; j < nbZonesH; j++) {
				double minEau, maxEau, minTemperature, maxTemperature;
				switch (this.defaultZones[i][j]) {
				case 0:
					minEau = 50;
					maxEau = 55;
					minTemperature = 10.5;
					maxTemperature = 25;
					break;

				case 1:
					minEau = 2.5;
					maxEau = 9.8;
					minTemperature = 5;
					maxTemperature = 10;
					break;

				default:
					minEau = 10;
					maxEau = 20;
					minTemperature = 5;
					maxTemperature = 10;
					break;
				}
				double eau = Math.round((r.nextDouble() * (maxEau - minEau) + minEau) * 100.0) / 100.0;
				double temperature = Math
						.round((r.nextDouble() * (maxTemperature - minTemperature) + minTemperature) * 100.0) / 100.0;
				zones[i][j] = new Zone(i, j, eau, temperature, this);
				zones[i][j].verifierTypeZone();
			}
		}
	}

	private void initRandomNbAnimaux() {
		for (int i = 0; i < nbZonesL; i++) {
			for (int j = 0; j < nbZonesH; j++) {
				Zone z = zones[i][j];
				if (z.getTypeZone() instanceof Riviere)
					continue;
				for (int nbPigeon = 0; nbPigeon < r.nextInt(20); nbPigeon++)
					z.addAnimal((Animal) new Pigeon(z));
				for (int nbFourmi = 0; nbFourmi < r.nextInt(30); nbFourmi++)
					z.addAnimal((Animal) new Fourmi(z));
				for (int nbAbeille = 0; nbAbeille < r.nextInt(15); nbAbeille++)
					z.addAnimal((Animal) new Abeille(z));
				// TODO ajout d'autres animaux
			}
		}
	}

	public void simulation() {
		while (true) {
			this.redessine();
			int i, j;
			//System.out.println(cycle);
			for (i = 0; i < nbZonesL; i++) {
				for (j = 0; j < nbZonesH; j++) {
					Zone z = zones[i][j];
					z.getAnimaux().forEach((animal) -> {
						if (animal.getAge() == animal.getAgeMax() || animal.getNbCyclesSansEau() == 3) {
							z.removeAnimal(animal);
						}
						try {
							animal.boir();
						} catch (BoirException e) {
						}
						if (z.getAnimaux().size() > 0) {
							try {
								if (!animal.isDejaReproduiCecycle() && r.nextInt(100) <= animal.getTauxDeReproduction())
									animal.seReproduire();
							} catch (ReproduireException e) {
							}
						}
						animal.vieillir();
					});

					z.getAnimaux().forEach((animal) -> animal.setDejaReproduiCecycle(false));
					try {
						z.changerTemperature(1);
					} catch (ChangerTemperatureException e) {
					}
					z.verifierTypeZone();
				}
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			nextCycle();
		}
	}
}
