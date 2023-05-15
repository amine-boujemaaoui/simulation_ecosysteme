package main;

import main.Animaux.Animal;
import main.Animaux.Insectes.*;
import main.Animaux.Mammiferes.Mammifere;
import main.Animaux.Mammiferes.Carnivores.Loup;
import main.Animaux.Mammiferes.Herbivore.Vache;
import main.Animaux.Oiseaux.*;
import main.Execeptions.BoirException;
import main.Execeptions.ChangerTemperatureException;
import main.Execeptions.MangerException;
import main.Execeptions.PropagerException;
import main.Execeptions.ReproduireException;
import main.Execeptions.SeDeplacerException;
import main.Execeptions.VolerException;
import main.Grille.Grille;
import main.TypeZones.*;
import main.Vegetaux.Vegetal;
import main.Vegetaux.Arbres.Arbre;
import main.Vegetaux.Arbres.Chene;
import main.Vegetaux.Arbres.Sapin;
import main.Vegetaux.Vivaces.Absinthe;
import main.Vegetaux.Vivaces.Artichaut;
import main.Vegetaux.Vivaces.Vivace;

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
	public static boolean simulate = true;

	private int[][] defaultZones = { { 0, 3, 3, 3, 2, 2, 2, 2, 2, 2, 2, 2 }, { 0, 0, 3, 3, 2, 2, 2, 2, 2, 2, 2, 2 },
			{ 2, 0, 3, 2, 2, 2, 2, 2, 2, 2, 2, 2 }, { 2, 0, 0, 2, 2, 2, 2, 2, 2, 2, 1, 1 },
			{ 2, 2, 0, 0, 2, 2, 2, 2, 1, 1, 1, 2 }, { 2, 2, 2, 0, 0, 0, 1, 1, 1, 1, 2, 2 },
			{ 2, 2, 2, 2, 2, 0, 0, 0, 0, 0, 2, 2 }, { 2, 2, 2, 2, 1, 1, 0, 1, 1, 0, 0, 2 },
			{ 2, 2, 1, 1, 1, 0, 0, 1, 1, 1, 2, 2 }, { 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 2, 2 },
			{ 1, 1, 1, 1, 0, 1, 1, 1, 1, 2, 2, 2 }, { 1, 1, 1, 1, 0, 1, 1, 1, 1, 2, 2, 2 } };

	/*
	 * private int[][] defaultZones = { { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }, { 2,
	 * 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }, { 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }, {
	 * 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1 }, { 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1 },
	 * { 2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1 }, { 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1
	 * }, { 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1 }, { 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1,
	 * 1 }, { 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1 }, { 2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
	 * 1, 1 }, { 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1 } };
	 */

	public Ecosysteme(ArrayList<TypeZone> typeZones) {
		super();
		this.nbZonesH = 12;
		this.nbZonesL = 12;
		this.grille = new Grille(nbZonesL, nbZonesH, 64, true);
		this.zones = new Zone[nbZonesL][nbZonesH];
		this.cycle = 0;

		this.typeZones.add(new Riviere());
		this.typeZones.add(new Foret());
		this.typeZones.add(new Plaine());
		this.typeZones.add(new Desert());

		initdefaultZones();
		initRandomNbAnimaux();
		initRandomNbVegetaux();
		/*
		 * for (int i = 0; i < 30; i++) { zones[9][2].addAnimal(new
		 * Corbeau(zones[9][2])); zones[9][2].addAnimal(new Vache(zones[9][2])); //
		 * zones[9][2].addAnimal(new Fourmi(zones[9][2])); }
		 */
		/*
		 * for (int i = 0; i < 30; i++) { zones[9][2].addVegetal(new
		 * Sapin(zones[9][2])); zones[9][2].addVegetal(new Chene(zones[9][2]));
		 * zones[5][1].addVegetal(new Artichaut(zones[5][1]));
		 * zones[5][1].addVegetal(new Absinthe(zones[5][1])); }
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

	public void propagerVegetal(Vegetal vegetal, int x, int y) {
		if (!(this.zones[x][y].getTypeZone() instanceof Riviere)) {
			this.zones[x][y].addVegetal(vegetal);
			vegetal.setZone_actuel(zones[x][y]);
		}
	}

	private void redessine() {
		int taillePaquets = 32;
		for (int i = 0; i < nbZonesL; i++) {
			for (int j = 0; j < nbZonesH; j++) {
				grille.setCycle(cycle);
				grille.resetlDisques(i, j);
				grille.colorieFond(i, j, zones[i][j].getTypeZone().getC());
				grille.iconFond(i, j, zones[i][j].getTypeZone().getTypeZoneIcon());
				// grille.addDisque(i, j, zones[i][j].getNbInsecte(), Color.BLUE);
				for (int k = 0; k < zones[i][j].getNbInsecte() / taillePaquets; k++)
					grille.addDisqueAnimaux(Insecte.icon, i, j, taillePaquets, Color.BLUE);
				grille.addDisqueAnimaux(Insecte.icon, i, j, zones[i][j].getNbInsecte() % taillePaquets, Color.BLUE);
				for (int k = 0; k < zones[i][j].getNbOiseau() / taillePaquets; k++)
					grille.addDisqueAnimaux(Oiseau.icon, i, j, taillePaquets, Color.RED);
				grille.addDisqueAnimaux(Oiseau.icon, i, j, zones[i][j].getNbOiseau() % taillePaquets, Color.RED);
				for (int k = 0; k < zones[i][j].getNbMammifere() / taillePaquets; k++)
					grille.addDisqueAnimaux(Mammifere.icon, i, j, taillePaquets, Color.YELLOW);
				grille.addDisqueAnimaux(Mammifere.icon, i, j, zones[i][j].getNbMammifere() % taillePaquets,
						Color.YELLOW);
				for (int k = 0; k < zones[i][j].getNbArbre() / taillePaquets; k++)
					grille.addDisqueVegetaux(Arbre.icon, i, j, taillePaquets, Color.BLACK);
				grille.addDisqueVegetaux(Arbre.icon, i, j, zones[i][j].getNbArbre() % taillePaquets, Color.BLACK);
				for (int k = 0; k < zones[i][j].getNbVivace() / taillePaquets; k++)
					grille.addDisqueVegetaux(Vivace.icon, i, j, taillePaquets, Color.GRAY);
				grille.addDisqueVegetaux(Vivace.icon, i, j, zones[i][j].getNbVivace() % taillePaquets, Color.GRAY);
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
						nbFourmiMax = 40;
						nbAbeilleMax = 160;
						nbLoupMax = 60;
						nbVacheMax = 4;
						nbPigeonMax =8;
						nbCorbeauMax = 60;
						break;
					case 2:
						nbFourmiMax = 160;
						nbAbeilleMax = 20;
						nbLoupMax = 4;
						nbVacheMax = 160;
						nbPigeonMax = 80;
						nbCorbeauMax = 6;
						break;
					default:
						continue;
					}
					Zone z = zones[i][j];
					if (z.getTypeZone() instanceof Riviere)
						continue;
					/*
					 * for (int nbFourmi = 0; nbFourmi < r.nextInt(nbFourmiMax); nbFourmi++)
					 * z.addAnimal(new Fourmi(z)); for (int nbAbeille = 0; nbAbeille <
					 * r.nextInt(nbAbeilleMax); nbAbeille++) z.addAnimal(new Abeille(z));
					 */
					for (int nbLoup = 0; nbLoup < r.nextInt(nbLoupMax); nbLoup++)
						z.addAnimal(new Loup(z));
					for (int nbVache = 0; nbVache < r.nextInt(nbPigeonMax); nbVache++)
						z.addAnimal((Animal) new Vache(z));
					for (int nbPigeon = 0; nbPigeon < r.nextInt(nbVacheMax); nbPigeon++)
						z.addAnimal(new Pigeon(z));
					for (int nbCorbeau = 0; nbCorbeau < r.nextInt(nbCorbeauMax); nbCorbeau++)
						z.addAnimal(new Corbeau(z));
				}
			}
		}
	}

	public void initRandomNbVegetaux() {
		for (int i = 0; i < nbZonesL; i++) {
			for (int j = 0; j < nbZonesH; j++) {
				if (r.nextInt(100) <= 30) {
					int nbSapinMax, nbCheneMax, nbArtichautMax, nbAbsintheMax;
					switch (defaultZones[i][j]) {
					case 1:
						nbSapinMax = 150;
						nbCheneMax = 200;
						nbArtichautMax = 10;
						nbAbsintheMax = 25;
						break;
					case 2:
						nbSapinMax = 3;
						nbCheneMax = 5;
						nbArtichautMax = 200;
						nbAbsintheMax = 250;
						break;
					default:
						continue;
					}
					Zone z = zones[i][j];
					if (z.getTypeZone() instanceof Riviere)
						continue;
					for (int nbSapin = 0; nbSapin < r.nextInt(nbSapinMax); nbSapin++)
						z.addVegetal(new Sapin(z));
					for (int nbChene = 0; nbChene < r.nextInt(nbCheneMax); nbChene++)
						z.addVegetal(new Chene(z));
					for (int nbArtichaut = 0; nbArtichaut < r.nextInt(nbArtichautMax); nbArtichaut++)
						z.addVegetal(new Artichaut(z));
					for (int nbAbsinthe = 0; nbAbsinthe < r.nextInt(nbAbsintheMax); nbAbsinthe++)
						z.addVegetal(new Absinthe(z));
				}
			}
		}
	}

	public void deplacementAleatoire(Zone z) {
		z.getAnimaux().forEach((animal) -> {
			if (!(z.getTypeZone().getClass() == animal.getZoneFavorable().getClass()) || r.nextInt(100) < 20) {
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
			} else if (ax - 1 >= 0 && ay - 1 >= 0
					&& zones[ax - 1][ay - 1].getTypeZone().getClass() == animal.getZoneFavorable().getClass()) {
				x = ax - 1;
				y = ay - 1;
			} else if (ax - 1 >= 0
					&& zones[ax - 1][ay + 0].getTypeZone().getClass() == animal.getZoneFavorable().getClass()) {
				x = ax - 1;
				y = ay + 0;
			} else if (ax - 1 >= 0 && ay + 1 < nbZonesL
					&& zones[ax - 1][ay + 1].getTypeZone().getClass() == animal.getZoneFavorable().getClass()) {
				x = ax - 1;
				y = ay + 1;
			} else if (ay - 1 >= 0
					&& zones[ax + 0][ay - 1].getTypeZone().getClass() == animal.getZoneFavorable().getClass()) {
				x = ax + 0;
				y = ay - 1;
			} else if (ay + 1 < nbZonesL
					&& zones[ax + 0][ay + 1].getTypeZone().getClass() == animal.getZoneFavorable().getClass()) {
				x = ax + 0;
				y = ay + 1;
			} else if (ax + 1 < nbZonesH && ay - 1 >= 0
					&& zones[ax + 1][ay - 1].getTypeZone().getClass() == animal.getZoneFavorable().getClass()) {
				x = ax + 1;
				y = ay - 1;
			} else if (ax + 1 < nbZonesH
					&& zones[ax + 1][ay + 0].getTypeZone().getClass() == animal.getZoneFavorable().getClass()) {
				x = ax + 1;
				y = ay + 0;
			} else if (ax + 1 < nbZonesH && ay + 1 < nbZonesL
					&& zones[ax + 1][ay + 1].getTypeZone().getClass() == animal.getZoneFavorable().getClass()) {
				x = ax + 1;
				y = ay + 1;
			} else {
				int rx = r.nextInt(-1, 2);
				int ry = r.nextInt(-1, 2);
				x = ax + rx;
				y = ay + ry;
			}
			try {
				if (x >= 0 && x < nbZonesH && y >= 0 && y < nbZonesL
						&& !(zones[x][y].getTypeZone() instanceof Riviere)) {
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
							&& oiseau.getNbCyclesSansEau() == 0 && oiseau.getNbCyclesSansManger() == 0
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
							&& mammifere.getNbCyclesSansEau() == 0 && mammifere.getNbCyclesSansManger() == 0
							&& r.nextInt(100) <= mammifere.getTauxDeReproduction())
						mammifere.seReproduire();
				} catch (ReproduireException e) {
				}
			}
		});
	}

	public void propagation(Zone z) {
		z.getVegetaux().forEach((vegetal) -> {
			if (z.getNbVegetaux() > 0) {
				try {
					if (vegetal.getAge() >= vegetal.getAgeMinPropagation()
							&& vegetal.getZoneFavorable().getClass() == z.getTypeZone().getClass()
							&& r.nextInt(100) < vegetal.getTauxDePropagation()) {
						int x = 0, y = 0, rx, ry, vx = z.getX(), vy = z.getY();
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
							if (vx + rx >= 0 && vx + rx < nbZonesH && vy + ry >= 0 && vy + ry < nbZonesL
									&& zones[vx + rx][vy + ry].getEau() > vegetal.getEauRequise()) {
								x = vx + rx;
								y = vy + ry;
								END = false;
							}
						}
						vegetal.sePropager(x, y);
					}
				} catch (PropagerException e) {
				}
				if (vegetal.getZone_actuel().getTypeZone().getClass() == vegetal.getZoneFavorable().getClass())
					vegetal.setNbCycleTypeZoneNonFavorable(0);
			}
		});
	}

	public void vieillissement(Zone z) {
		z.getAnimaux().forEach((animal) -> {
			if (animal.getAge() == animal.getAgeMax() || animal.getNbCyclesSansEau() >= 3
					|| animal.getNbCyclesSansManger() >= 5) {
				z.removeAnimal(animal);
			} else {
				animal.setDejaReproduiCecycle(false);
				animal.vieillir();
			}
		});
		z.getVegetaux().forEach((vegetal) -> {
			if (vegetal.getAge() == vegetal.getAgeMax() || vegetal.getZone_actuel().getTypeZone() instanceof Desert) {
				z.removeVegetal(vegetal);
			} else
				vegetal.vieillir();
		});
	}

	public void abreuvage(Zone z) {
		z.getAnimaux().forEach((animal) -> {
			try {
				animal.boir();
			} catch (BoirException e) {
			}
		});
		z.getVegetaux().forEach((vegetal) -> {
			try {
				vegetal.boir();
			} catch (BoirException e) {
			}
		});
	}

	public void nourrissage(Zone z) {
		z.getAnimaux().forEach((animal) -> {
			try {
				animal.manger();
			} catch (MangerException e) {
			}
		});
	}

	public void evolutionEnvironement(Zone z) {
		z.verifierTypeZone();
		try {
			z.changerTemperature(r.nextInt(-10, 11));
		} catch (ChangerTemperatureException e) {
		}
	}

	public void simulation() {
		boolean b = true;
		while (true) {
			if (Ecosysteme.simulate) {
				b = true;
				this.redessine();
				int i, j;
				for (i = 0; i < nbZonesL; i++) {
					for (j = 0; j < nbZonesH; j++) {
						Zone z = zones[i][j];
						evolutionEnvironement(z);
						vieillissement(z);
						abreuvage(z);
						propagation(z);
						reproduction(z);
						deplacementAleatoire(z);
						nourrissage(z);
					}
				}
				nextCycle();
			} else if (b) {
				b = false;
				grille.redessine();
			}
			try {
				Thread.sleep(90);
			} catch (InterruptedException e) {
			}
		}
	}
}
