package main;

import main.Animaux.Animal;
import main.Animaux.Insectes.*;
import main.Animaux.Mammiferes.*;
import main.Animaux.Oiseaux.*;
import interfaces.Herbivore;
import interfaces.Carnivore;
import main.Execeptions.BoirException;
import main.Execeptions.ChangerTemperatureException;
import main.Execeptions.ReproduireException;
import main.Execeptions.SeDeplacerException;
import main.Execeptions.VolerException;
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
			{ 0, 3, 3, 3, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1 },
			{ 0, 0, 3, 3, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1 }, 
			{ 2, 0, 3, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1 },
			{ 2, 0, 0, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1 }, 
			{ 2, 2, 0, 0, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1 },
			{ 2, 2, 2, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1 }, 
			{ 2, 2, 2, 2, 2, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1 },
			{ 2, 2, 2, 2, 1, 1, 0, 1, 1, 0, 0, 1, 1, 1, 1 }, 
			{ 2, 2, 1, 1, 1, 0, 0, 1, 1, 1, 0, 0, 1, 1, 1 },
			{ 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1 }, 
			{ 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1 },
			{ 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1 },
			{ 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0 },
			{ 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0 },
			{ 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0 }, };

	public Ecosysteme(int nbZonesL, int nbZonesH, ArrayList<TypeZone> typeZones) {
		super();
		this.nbZonesH = nbZonesH;
		this.nbZonesL = nbZonesL;
		this.grille = new Grille(nbZonesL, nbZonesH, 60);
		this.zones = new Zone[nbZonesL][nbZonesH];
		this.cycle = 0;

		this.typeZones.add(new Riviere());
		this.typeZones.add(new Foret());
		this.typeZones.add(new Plaine());
		this.typeZones.add(new Desert());

		initdefaultZones();
		initRandomNbAnimaux();
		/*
		for (int nbCorbeau = 0; nbCorbeau < 40; nbCorbeau++) {
			Carnivore corbeau = new Corbeau(zones[9][2]);
			zones[9][2].addAnimal((Animal) corbeau);
			zones[9][2].addCarnivore(corbeau);
		}
		
		for (int nbCorbeau = 0; nbCorbeau < 40; nbCorbeau++) {
			Carnivore corbeau = new Corbeau(zones[2][11]);
			zones[2][11].addAnimal((Animal) corbeau);
			zones[2][11].addCarnivore(corbeau);
		}
		*/
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
		if (!(this.zones[x][y].getTypeZone() instanceof Riviere)) {
			animal.getZone_actuel().removeAnimal(animal);
			this.zones[x][y].addAnimal(animal);
			animal.setZone_actuel(zones[x][y]);
		}
	}

	private void redessine() {
		for (int i = 0; i < nbZonesL; i++) {
			for (int j = 0; j < nbZonesH; j++) {
				grille.setCycle(cycle);
				grille.resetlDisques(i, j);
				grille.colorieFond(i, j, zones[i][j].getTypeZone().getC());
				grille.iconFond(i, j, zones[i][j].getTypeZone().getTypeZoneIcon());
				grille.addDisque(i, j, zones[i][j].getNbInsecte(), Color.BLUE);
				grille.addDisque(i, j, zones[i][j].getNbOiseau(), Color.RED);
				grille.addDisque(i, j, zones[i][j].getNbMammifere(), new Color(107, 42, 0));
			}
		}
		grille.redessine();
	}

	public void initdefaultZones() {
		for (int i = 0; i < nbZonesL; i++) {
			for (int j = 0; j < nbZonesH; j++) {
				double minEau, maxEau, minTemperature, maxTemperature;
				minEau = this.typeZones.get(this.defaultZones[i][j]).getEauMin();
				maxEau = this.typeZones.get(this.defaultZones[i][j]).getEauMax();
				minTemperature = this.typeZones.get(this.defaultZones[i][j]).getTemperatureMin();
				maxTemperature = this.typeZones.get(this.defaultZones[i][j]).getTemperatureMin();
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
				if (r.nextInt(100) <= 30) {
					int nbFourmiMax, nbAbeilleMax, nbLoupMax, nbVacheMax, nbPigeonMax, nbCorbeauMax;
					switch (defaultZones[i][j]) {
					case 1:
						nbFourmiMax = 20;
						nbAbeilleMax = 40;
						nbLoupMax = 30;
						nbPigeonMax = 20;
						nbVacheMax = 2;
						nbCorbeauMax = 4;
						break;
					case 2:
						nbFourmiMax = 50;
						nbAbeilleMax = 10;
						nbLoupMax = 2;
						nbPigeonMax = 15;
						nbVacheMax = 20;
						nbCorbeauMax = 15;
						break;
					default:
						continue;
					}
					Zone z = zones[i][j];
					if (z.getTypeZone() instanceof Riviere)
						continue;
					for (int nbFourmi = 0; nbFourmi < r.nextInt(nbFourmiMax); nbFourmi++) {
						Carnivore fourmi = new Fourmi(z);
						z.addAnimal((Animal) fourmi);
						z.addCarnivore(fourmi);
					}
					for (int nbAbeille = 0; nbAbeille < r.nextInt(nbAbeilleMax); nbAbeille++) {
						Herbivore abeille = new Abeille(z);
						z.addAnimal((Animal) abeille);
						z.addHerbivore(abeille);
					}
					for (int nbLoup = 0; nbLoup < r.nextInt(nbLoupMax); nbLoup++) {
						Carnivore loup = new Loup(z);
						z.addAnimal((Animal) loup);
						z.addCarnivore(loup);
					}
					for (int nbVache = 0; nbVache < r.nextInt(nbPigeonMax); nbVache++) {
						Herbivore vache = new Vache(z);
						z.addAnimal((Animal) vache);
						z.addHerbivore(vache);
					}
					for (int nbPigeon = 0; nbPigeon < r.nextInt(nbVacheMax); nbPigeon++) {
						Herbivore pigeon = new Pigeon(z);
						z.addAnimal((Animal) pigeon);
						z.addHerbivore(pigeon);
					}
					for (int nbCorbeau = 0; nbCorbeau < r.nextInt(nbCorbeauMax); nbCorbeau++) {
						Carnivore corbeau = new Corbeau(z);
						z.addAnimal((Animal) corbeau);
						z.addCarnivore(corbeau);
					}
				}
			}
		}
	}

	public void deplacementAleatoire(Zone z) {
		z.getAnimaux().forEach((animal) -> {
			if (!(z.getTypeZone().getClass() == animal.getZoneFavorable().getClass())) {
				int x = 0, y = 0, rx, ry, ax = z.getX(), ay = z.getY();
				boolean END = true, HG = true, HM = true, HD = true, MG = true, MM = true, MD = true, BG = true,
						BM = true, BD = true;
				while (END && (HG || HM || HD || MG || MM || MD || BG || BM || BD)) {
					rx = r.nextInt(-1, 2);
					ry = r.nextInt(-1, 2);
					if (rx == -1 && ry == -1)
						HG = false;
					if (rx == -1 && ry == 0)
						HM = false;
					if (rx == -1 && ry == 1)
						HD = false;
					if (rx == 0 && ry == -1)
						MG = false;
					if (rx == 0 && ry == 0)
						MM = false;
					if (rx == 0 && ry == 1)
						MD = false;
					if (rx == 1 && ry == -1)
						BG = false;
					if (rx == 1 && ry == 0)
						BM = false;
					if (rx == 1 && ry == 1)
						BD = false;
					if (ax + rx >= 0 && ax + rx < nbZonesH && ay + ry >= 0 && ay + ry < nbZonesL
							&& zones[ax + rx][ay + ry].getTypeZone().getClass() == animal.getZoneFavorable()
									.getClass()) {
						x = ax + rx;
						y = ay + ry;
						END = false;
					}
				}
				try {
					if (END)
						animal.seDeplacer(x, y);
					else
						animal.seDeplacer(ax + r.nextInt(-1, 2), ay + r.nextInt(-1, 2));
				} catch (VolerException | SeDeplacerException e) {
				}
			}
		});
	}

	public void deplacement(Zone z) {
		z.getAnimaux().forEach((animal) -> {
			int x = 0, y = 0, ax = z.getX(), ay = z.getY();
			if (zones[ax + 0][ay + 0].getTypeZone().getClass() == animal.getZoneFavorable().getClass()) {
				x = ax + 0;
				y = ay + 0;
				System.out.println("-");
			} else if (ax - 1 >= 0 && ay - 1 >= 0
					&& zones[ax - 1][ay - 1].getTypeZone().getClass() == animal.getZoneFavorable().getClass()) {
				x = ax - 1;
				y = ay - 1;
				System.out.println("\\");
			} else if (ax - 1 >= 0
					&& zones[ax - 1][ay + 0].getTypeZone().getClass() == animal.getZoneFavorable().getClass()) {
				x = ax - 1;
				y = ay + 0;
				System.out.println("\\");
			} else if (ax - 1 >= 0 && ay + 1 < nbZonesL
					&& zones[ax - 1][ay + 1].getTypeZone().getClass() == animal.getZoneFavorable().getClass()) {
				x = ax - 1;
				y = ay + 1;
				System.out.println("\\");
			} else if (ay - 1 >= 0
					&& zones[ax + 0][ay - 1].getTypeZone().getClass() == animal.getZoneFavorable().getClass()) {
				x = ax + 0;
				y = ay - 1;
				System.out.println("\\");
			} else if (ay + 1 < nbZonesL
					&& zones[ax + 0][ay + 1].getTypeZone().getClass() == animal.getZoneFavorable().getClass()) {
				x = ax + 0;
				y = ay + 1;
				System.out.println("\\");
			} else if (ax + 1 < nbZonesH && ay - 1 >= 0
					&& zones[ax + 1][ay - 1].getTypeZone().getClass() == animal.getZoneFavorable().getClass()) {
				x = ax + 1;
				y = ay - 1;
				System.out.println("\\");
			} else if (ax + 1 < nbZonesH
					&& zones[ax + 1][ay + 0].getTypeZone().getClass() == animal.getZoneFavorable().getClass()) {
				x = ax + 1;
				y = ay + 0;
				System.out.println("\\");
			} else if (ax + 1 < nbZonesH && ay + 1 < nbZonesL
					&& zones[ax + 1][ay + 1].getTypeZone().getClass() == animal.getZoneFavorable().getClass()) {
				x = ax + 1;
				y = ay + 1;
				System.out.println("\\");
			} else {
				int rx = r.nextInt(-1, 2);
				int ry = r.nextInt(-1, 2);
				x = ax + rx;
				y = ay + ry;
				System.out.println("||");
			}
			try {
				if (x >= 0 && x < nbZonesH && y >= 0 && y < nbZonesL && !(zones[x][y].getTypeZone() instanceof Riviere)) {
					animal.seDeplacer(x, y);
					System.out.println("YES");
				}
			} catch (VolerException | SeDeplacerException e) {
			}
		});

	}

	public void reproduction(Zone z) {
		z.getInsectes().forEach((insecte) -> {
			if (z.getNbInsecte() > 0) {
				try {
					if (insecte.getAge() >= insecte.getAgeMinReproduction() && !insecte.isDejaReproduiCecycle()
							&& r.nextInt(100) <= insecte.getTauxDeReproduction()
							&& insecte.getZoneFavorable().getClass() == z.getTypeZone().getClass())
						insecte.seReproduire();
				} catch (ReproduireException e) {
				}
			}
		});
		z.getOiseaux().forEach((oiseau) -> {
			if (z.getNbOiseau() > 0) {
				try {
					if (oiseau.getAge() >= oiseau.getAgeMinReproduction() && !oiseau.isDejaReproduiCecycle()
							&& r.nextInt(100) <= oiseau.getTauxDeReproduction())
						oiseau.seReproduire();
				} catch (ReproduireException e) {
				}
			}
		});
		z.getMammiferes().forEach((mammifere) -> {
			if (z.getNbMammifere() > 0) {
				try {
					if (mammifere.getAge() >= mammifere.getAgeMinReproduction() && !mammifere.isDejaReproduiCecycle()
							&& r.nextInt(100) <= mammifere.getTauxDeReproduction())
						mammifere.seReproduire();
				} catch (ReproduireException e) {
				}
			}
		});
	}

	public void vieillissement(Zone z) {
		z.getAnimaux().forEach((animal) -> {
			if (animal.getAge() == animal.getAgeMax() || animal.getNbCyclesSansEau() == 3) {
				z.removeAnimal(animal);
			} else {
				animal.setDejaReproduiCecycle(false);
				animal.vieillir();
			}
		});
	}

	public void abreuvage(Zone z) {
		z.getAnimaux().forEach((animal) -> {
			try {
				animal.boir();
			} catch (BoirException e) {
			}
		});
	}

	public void simulation() {
		while (true) {
			this.redessine();
			int i, j;
			for (i = 0; i < nbZonesL; i++) {
				for (j = 0; j < nbZonesH; j++) {
					Zone z = zones[i][j];
					z.verifierTypeZone();
					try {
						z.changerTemperature(1);
					} catch (ChangerTemperatureException e) {
					}

					vieillissement(z);
					abreuvage(z);
					reproduction(z);
					deplacementAleatoire(z);
					//deplacement(z);
				}
			}
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
			}
			nextCycle();
		}
	}
}
