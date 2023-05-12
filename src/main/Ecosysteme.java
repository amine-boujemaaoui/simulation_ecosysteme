package main;

import main.Animaux.Animal;
import main.Animaux.Insectes.Fourmi;
import main.Animaux.Oiseaux.Pigeon;
import main.Execeptions.BoirException;
import main.Execeptions.ChangerTemperatureException;
import main.Execeptions.ReproduireException;
import main.Grille.GrilleNature;
import main.TypeZones.*;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

public class Ecosysteme {
	private int nbZonesH;
	private int nbZonesL;
	private GrilleNature grille;
	private Zone[][] zones;
	private ArrayList<TypeZone> typeZones = new ArrayList<TypeZone>();
	private Random r = new Random();

	public Ecosysteme(int nbZonesL, int nbZonesH) {
		super();
		this.nbZonesH = nbZonesH;
		this.nbZonesL = nbZonesL;
		this.grille = new GrilleNature(nbZonesL, nbZonesH, 80);

		typeZones.add(new Foret());
		typeZones.add(new Plaine());
		typeZones.add(new Desert());
		typeZones.add(new Riviere());

		this.zones = new Zone[nbZonesL][nbZonesH];
		for (int i = 0; i < nbZonesL; i++) {
			for (int j = 0; j < nbZonesH; j++) {
				double minEau, maxEau, minTemperature, maxTemperature;
				if (j > i) {
					minEau = 2.5;
					maxEau = 9.8;
					minTemperature = 5;
					maxTemperature = 10;
				} else if (i == j) {
					minEau = 50;
					maxEau = 55;
					minTemperature = 10.5;
					maxTemperature = 25;
				} else {
					minEau = 10;
					maxEau = 20;
					minTemperature = 5;
					maxTemperature = 10;
				}
				double eau = Math.round((r.nextDouble() * (maxEau - minEau) + minEau) * 100.0) / 100.0;
				double temperature = Math
						.round((r.nextDouble() * (maxTemperature - minTemperature) + minTemperature) * 100.0) / 100.0;
				zones[i][j] = new Zone(i, j, eau, temperature, this);
				zones[i][j].verifierTypeZone();
			}
		}
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

	public GrilleNature getGrille() {
		return grille;
	}

	public void deplacerAnimal(Animal animal, int x, int y) {
		animal.getZone_actuel().removeAnimal(animal);
		this.zones[x][y].addAnimal(animal);
	}

	private void initRandomNbAnimaux() {
		for (int i = 0; i < nbZonesL; i++) {
			for (int j = 0; j < nbZonesH; j++) {
				Zone z = zones[i][j];
				if (z.getTypeZone() instanceof Riviere)
					continue;
				for (int nbPigeons = 0; nbPigeons < r.nextInt(10); nbPigeons++)
					z.addAnimal((Animal) new Pigeon(z));
				for (int nbInsectes = 0; nbInsectes < r.nextInt(100); nbInsectes++)
					z.addAnimal((Animal) new Fourmi(z));
				// TODO ajout d'autres animaux
			}
		}
	}

	private void redessine() {
		for (int i = 0; i < nbZonesL; i++) {
			for (int j = 0; j < nbZonesH; j++) {
				grille.resetlDisques(i, j);
				grille.colorieFond(i, j, zones[i][j].getTypeZone().getC());
				grille.addDisque(i, j, zones[i][j].getAnimaux().size() * 2, Color.BLUE);
				grille.addDisque(i, j, zones[i][j].getVegeteaux().size() * 2, Color.RED);
				// System.out.print(zones[i][j].toString());
			}
			// System.out.print("\n\n");
		}
		grille.redessine();
	}

	public void simulation() {
		int cycle = 0;
		while (true) {
			this.redessine();
			int i, j;
			System.out.println(cycle);
			for (i = 0; i < nbZonesL; i++) {
				for (j = 0; j < nbZonesH; j++) {
					Zone z = zones[i][j];
					// Pour chaque animal de la zone
					z.getAnimaux().forEach((animal) -> {
						// MOURIR - Age maximal atteint ou plus d'eau depuis 3 cycle
						if (animal.getAge() == animal.getAgeMax() || animal.getNbCyclesSansEau() == 3) {
							z.removeAnimal(animal);	
						}
						// BOIR
						try {
							animal.boir();
						} catch (BoirException e) {
						}
						// REPRODUIRE
						if(z.getAnimaux().size()>0) {
							try {
								Animal a = z.getAnimal(r.nextInt(z.getAnimaux().size()));
								if (!animal.isDejaReproduiCecycle() && r.nextInt(100) <= animal.getTauxDeReproduction())
									animal.seReproduire(a);
							} catch (ReproduireException e) {
							}
						}
						// VIELLIR
						animal.vieillir();
					});

					z.getAnimaux().forEach((animal) -> animal.setDejaReproduiCecycle(false));
					try {
						z.changerTemperature(1);
					} catch (ChangerTemperatureException e) {
					}
					z.verifierTypeZone();
					System.out.print("[" + z.getNbOiseau() + "," + z.getNbInsecte() + "]");
				}
				System.out.println("");
			}
			System.out.println("");
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			cycle+=1;
		}
	}
}
