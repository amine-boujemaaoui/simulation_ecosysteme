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

		this.zones = new Zone[nbZonesL][nbZonesH];
		for (int i = 0; i < nbZonesL; i++) {
			for (int j = 0; j < nbZonesH; j++) {
				zones[i][j] = new Zone(i, j, Math.round(r.nextDouble(2.4, 19) * 100.0) / 100.0,
						Math.round(r.nextDouble(6, 24) * 100.0) / 100.0, this);
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

	public GrilleNature getGrille() {
		return grille;
	}

	public void deplacerAnimal(Animal animal, int x, int y) {
		animal.getZone_actuel().removeAnimal(animal);
		this.zones[x][y].addAnimal(animal);
	}

	private void initRandomNbAnimaux() {
		int minPigeons = 2, maxPigeons = 20;
		int minInsectes = 2, maxInsectes = 20;
		for (int i = 0; i < nbZonesL; i++) {
			for (int j = 0; j < nbZonesH; j++) {
				Zone z = zones[i][j];
				for (int nbPigeons = 0; nbPigeons < r.nextInt(minPigeons, maxPigeons); nbPigeons++)
					z.addAnimal((Animal) new Pigeon(z));
				for (int nbInsectes = 0; nbInsectes < r.nextInt(minInsectes, maxInsectes); nbInsectes++)
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
				//System.out.print(zones[i][j].toString());
			}
			//System.out.print("\n\n");
		}
		grille.redessine();
	}

	public void simulation() {
		while (true) {
			this.redessine();
			int i, j;
			for (i = 0; i < nbZonesL; i++) {
				for (j = 0; j < nbZonesH; j++) {
					Zone z = zones[i][j];
					z.getAnimaux().forEach((animal) -> {
						try {
							animal.boir();
						} catch (BoirException e) {
						}
						try {
							Animal a = z.getAnimal(r.nextInt(z.getAnimaux().size()));
							if (!animal.isDejaReproduiCecycle())
								animal.seReproduire(a);
						} catch (ReproduireException e) {
						}
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
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
