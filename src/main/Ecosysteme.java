package main;

import main.Animaux.Animal;
import main.Animaux.Insectes.*;
import main.Animaux.Mammiferes.Mammifere;
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
import main.Vegetaux.Vivaces.Vivace;
import java.awt.Color;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import javax.swing.ImageIcon;

public class Ecosysteme {
	private int nbZonesH;
	private int nbZonesL;
	private Grille grille;
	private Zone[][] zones;
	private ArrayList<TypeZone> typeZones = new ArrayList<TypeZone>();
	private ArrayList<Animal> typeAnimaux = new ArrayList<Animal>();
	private ArrayList<Vegetal> typeVegetaux = new ArrayList<Vegetal>();
	private ArrayList<Event> events = new ArrayList<Event>();
	private ArrayList<Event> pastEvents = new ArrayList<Event>();
	private Random r = new Random();
	private int cycle;
	public static boolean simulate = false;
	private int vitesseSimulation;
	private int nbMaxEntiteParZone;
	private final int maxNbCasesL = 27, maxNbCasesH = 14;
	private int[][] defaultZones;

	// Grille carre

	/*
	 * private int[][] defaultZones = { { 0, 3, 3, 3, 2, 2, 2, 2, 2, 2, 2, 2 }, { 0,
	 * 0, 3, 3, 2, 2, 2, 2, 2, 2, 2, 2 }, { 2, 0, 3, 2, 2, 2, 2, 2, 2, 2, 1, 1 }, {
	 * 2, 0, 0, 2, 2, 2, 2, 2, 2, 1, 1, 1 }, { 2, 2, 0, 0, 2, 2, 2, 1, 1, 1, 1, 2 },
	 * { 2, 2, 2, 0, 0, 0, 1, 1, 1, 1, 2, 2 }, { 2, 2, 2, 2, 2, 0, 0, 0, 0, 0, 2, 2
	 * }, { 2, 2, 2, 2, 1, 1, 0, 1, 1, 2, 2, 2 }, { 2, 2, 1, 1, 1, 0, 0, 1, 1, 1, 2,
	 * 2 }, { 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 2, 2 }, { 1, 1, 1, 1, 0, 1, 1, 1, 1, 2,
	 * 2, 2 }, { 1, 1, 1, 1, 0, 1, 1, 1, 1, 2, 2, 2 } };
	 */

	// Grille rectangulaire

	/*
	 * private int[][] defaultZones = { { 0, 3, 3, 3, 2, 2, 2, 2, 2, 2, 2, 0, 1, 1,
	 * 2, 2, 2, 1, 1, 2, 2, 1, 1, 1, 1, 1, 1 }, { 0, 0, 3, 3, 2, 2, 2, 2, 2, 2, 2,
	 * 0, 1, 1, 2, 2, 2, 1, 1, 2, 2, 1, 1, 1, 1, 1, 1 }, { 2, 0, 3, 2, 2, 2, 2, 2,
	 * 2, 2, 1, 0, 0, 1, 2, 2, 1, 1, 1, 2, 2, 1, 1, 1, 1, 1, 1 }, { 2, 0, 0, 2, 2,
	 * 2, 2, 2, 2, 1, 1, 1, 0, 2, 2, 2, 1, 1, 1, 2, 2, 1, 1, 1, 1, 1, 1 }, { 2, 2,
	 * 0, 0, 2, 2, 2, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 2, 2, 2, 1, 1, 1, 1, 1 },
	 * { 2, 2, 2, 0, 0, 0, 1, 1, 1, 1, 0, 0, 2, 2, 2, 2, 0, 0, 0, 2, 2, 2, 2, 1, 1,
	 * 1, 1 }, { 2, 2, 2, 2, 2, 0, 0, 0, 0, 0, 0, 2, 2, 2, 2, 2, 2, 2, 0, 0, 2, 2,
	 * 2, 2, 1, 1, 1 }, { 2, 2, 2, 2, 1, 1, 0, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
	 * 0, 2, 2, 2, 2, 2, 2, 2 }, { 2, 2, 1, 1, 1, 0, 0, 1, 1, 1, 2, 2, 2, 2, 2, 2,
	 * 1, 1, 1, 0, 2, 2, 2, 2, 2, 2, 2 }, { 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 2, 2, 2,
	 * 2, 2, 1, 1, 1, 1, 0, 2, 2, 2, 2, 2, 2, 2 }, { 1, 1, 1, 1, 0, 1, 1, 1, 1, 2,
	 * 2, 2, 2, 2, 1, 1, 1, 1, 1, 0, 2, 2, 2, 2, 2, 3, 3 }, { 1, 1, 1, 1, 0, 1, 1,
	 * 1, 1, 2, 2, 2, 2, 2, 1, 1, 1, 1, 2, 0, 0, 2, 3, 3, 3, 3, 3 }, { 1, 1, 1, 1,
	 * 0, 1, 1, 1, 1, 2, 2, 2, 2, 1, 1, 1, 1, 2, 2, 2, 0, 3, 3, 3, 3, 3, 3 }, { 1,
	 * 1, 1, 1, 0, 1, 1, 1, 1, 2, 2, 2, 1, 1, 1, 1, 2, 2, 2, 2, 0, 3, 3, 3, 3, 3, 3
	 * } };
	 */

	// Zones par defaut du sujet
	/*
	 * private int[][] defaultZones = { { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }, { 2,
	 * 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }, { 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }, {
	 * 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1 }, { 2, 2, 2, 2, 1, 1, 3, 1, 1, 1, 1, 1 },
	 * { 2, 2, 2, 2, 2, 3, 3, 3, 1, 1, 1, 1 }, { 2, 2, 2, 2, 2, 3, 3, 3, 3, 1, 1, 1
	 * }, { 2, 2, 2, 2, 2, 2, 3, 3, 1, 1, 1, 1 }, { 2, 2, 2, 2, 2, 2, 3, 2, 1, 1, 1,
	 * 1 }, { 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1 }, { 2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
	 * 1, 1 }, { 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1 } };
	 */

	public Ecosysteme(String map, ArrayList<TypeZone> defaultTypeZones, ArrayList<Animal> defaultTypeAnimaux,
			ArrayList<Vegetal> defaultTypeVegetaux, ArrayList<Event> defaultEvents, int nbMaxEntiteParZone) {
		super();
		chargerdefaultZones(map);
		this.grille = new Grille(nbZonesL, nbZonesH, 64, true, this);
		this.zones = new Zone[nbZonesH][nbZonesL];
		this.cycle = 0;
		this.vitesseSimulation = 100;
		this.setNbMaxEntiteParZone(nbMaxEntiteParZone);

		if (defaultTypeZones.size() > 0)
			this.typeZones.addAll(defaultTypeZones);
		if (defaultTypeAnimaux.size() > 0)
			this.typeAnimaux.addAll(defaultTypeAnimaux);
		if (defaultTypeVegetaux.size() > 0)
			this.typeVegetaux.addAll(defaultTypeVegetaux);
		if (defaultEvents.size() > 0)
			defaultEvents.forEach((event) -> {
				event.setEcosysteme(this);
				this.pastEvents.add(event);
			});

	}

	public void chargerdefaultZones(String map) {
		try {
			InputStream is = getClass().getResourceAsStream("/maps/" + map);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			int[][] tempDefaultZones = new int[maxNbCasesH][maxNbCasesL];
			String line = br.readLine();
			this.nbZonesL = line.length() / 2;
			this.nbZonesL += (line.length()%2 == 0) ? 0 : 1;
			int col = 0, row = 0;
			while (line != null) {
				while (col < nbZonesL) {
					String idDefaultZones[] = line.split(" ");
					int id = Integer.parseInt(idDefaultZones[col]);
					tempDefaultZones[row][col] = id;
					col++;
				}
				if (col == nbZonesL) {
					col = 0;
					row++;
				}
				line = br.readLine();
			}
			this.nbZonesH = row;
			br.close();
			this.defaultZones = new int[nbZonesH][nbZonesL];
			for (int i = 0; i < nbZonesH; i++)
				for (int j = 0; j < nbZonesL; j++)
					this.defaultZones[i][j] = tempDefaultZones[i][j];
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
		}
	}

	public int nextIntBounds(int min, int max) {
		return r.nextInt(max - min) + min;
	}

	public void initSimulation() {
		Ecosysteme.simulate = false;
		this.zones = new Zone[nbZonesH][nbZonesL];
		this.cycle = 0;
		initdefaultZones();
		initEvents();
		initRandomNbAnimaux();
		initRandomNbVegetaux();
	}

	public ArrayList<TypeZone> getTypeZones() {
		ArrayList<TypeZone> typeZones_copie = new ArrayList<TypeZone>();
		typeZones_copie.addAll(typeZones);
		return typeZones_copie;
	}

	public void removeEvent(Event e) {
		this.events.remove(e);
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

	public Zone getZoneForGrille(int j, int i) {
		return zones[i][j];
	}

	public Grille getGrille() {
		return grille;
	}

	public int getCycle() {
		return cycle;
	}

	public int getNbMaxEntiteParZone() {
		return nbMaxEntiteParZone;
	}

	public void setNbMaxEntiteParZone(int nbMaxEntiteParZone) {
		this.nbMaxEntiteParZone = nbMaxEntiteParZone;
	}

	public void augmenterVistesseSimulation() {
		if (this.vitesseSimulation + 50 <= 300)
			this.vitesseSimulation += 50;
	}

	public void diminuerVistesseSimulation() {
		if (this.vitesseSimulation - 50 >= 50)
			this.vitesseSimulation -= 50;
	}

	public int getVitesseSimulation() {
		return this.vitesseSimulation;
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

	public void redessine() {
		int taillePaquets = 32, tailleRestant = 0;
		for (int i = 0; i < nbZonesH; i++) {
			for (int j = 0; j < nbZonesL; j++) {
				Zone z = zones[i][j];
				grille.setCycle(cycle);
				grille.resetlDisques(i, j);
				grille.iconFond(i, j, z.getTypeZone().getTypeZoneIcon());

				int[] nbAnimaux = { z.getNbInsecte(), z.getNbOiseau(), z.getNbMammifere() };
				ImageIcon[] animauxIcons = { Insecte.icon, Oiseau.icon, Mammifere.icon };
				int[] nbVegetaux = { z.getNbVivace(), z.getNbArbre() };
				ImageIcon[] vegetauxIcons = { Vivace.icon, Arbre.icon };

				for (int l = 0; l < 3; l++) {
					for (int k = 0; k < nbAnimaux[l] / taillePaquets; k++) {
						tailleRestant = (nbAnimaux[l] > 0) ? 0 : taillePaquets / 2;
						grille.addDisqueAnimaux(animauxIcons[l], i, j, taillePaquets, Color.BLUE);
					}
					grille.addDisqueAnimaux(animauxIcons[l], i, j, nbAnimaux[l] % taillePaquets + tailleRestant,
							Color.BLUE);
				}

				for (int l = 0; l < 2; l++) {
					for (int k = 0; k < nbVegetaux[l] / taillePaquets; k++) {
						tailleRestant = (nbVegetaux[l] > 0) ? 0 : taillePaquets / 2;
						grille.addDisqueVegetaux(vegetauxIcons[l], i, j, taillePaquets, Color.RED);
					}
					grille.addDisqueVegetaux(vegetauxIcons[l], i, j, nbVegetaux[l] % taillePaquets + tailleRestant,
							Color.BLUE);
				}
			}
		}
		grille.redessine();
	}

	public void initdefaultZones() {
		for (int i = 0; i < nbZonesH; i++) {
			for (int j = 0; j < nbZonesL; j++) {
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

	public void initEvents() {
		events.addAll(pastEvents);
		pastEvents.clear();
	}

	private void initRandomNbAnimaux() {
		for (int i = 0; i < nbZonesH; i++) {
			for (int j = 0; j < nbZonesL; j++) {
				Zone z = zones[i][j];
				if (r.nextInt(100) <= 35) {
					if (z.getTypeZone() instanceof Riviere || z.getTypeZone() instanceof Desert)
						continue;
					this.typeAnimaux.forEach((typeAnimal) -> {
						int nbMax;
						if (z.getTypeZone().getClass() == typeAnimal.getZoneFavorable().getClass())
							nbMax = typeAnimal.getNbMinDansZoneFavorableForInit();
						else
							nbMax = typeAnimal.getNbMinDansZoneFavorableForInit() / 4;
						for (int k = 0; k < nextIntBounds(nbMax / 2, nbMax); k++) {
							Animal animal = typeAnimal.getNewAnimal();
							animal.setZone_actuel(z);
							z.addAnimal(animal);
						}

					});
				}
			}
		}
	}

	private void initRandomNbVegetaux() {
		for (int i = 0; i < nbZonesH; i++) {
			for (int j = 0; j < nbZonesL; j++) {
				Zone z = zones[i][j];
				if (r.nextInt(100) <= 45) {
					if (z.getTypeZone() instanceof Riviere || z.getTypeZone() instanceof Desert)
						continue;
					this.typeVegetaux.forEach((typeVegetal) -> {
						int nbMax;
						if (z.getTypeZone().getClass() == typeVegetal.getZoneFavorable().getClass())
							nbMax = typeVegetal.getNbMinDansZoneFavorableForInit();
						else
							nbMax = typeVegetal.getNbMinDansZoneFavorableForInit() / 6;
						for (int k = 0; k < nextIntBounds(nbMax / 2, nbMax); k++) {
							Vegetal vegetal = typeVegetal.getNewVegetal();
							vegetal.setZone_actuel(z);
							z.addVegetal(vegetal);
						}

					});
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
					rx = nextIntBounds(-1, 2);
					ry = nextIntBounds(-1, 2);
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
						if (x >= 0 && x < nbZonesH && y >= 0 && y < nbZonesL
								&& !(!(animal.getZone_actuel().getTypeZone() instanceof Desert)
										&& zones[x][y].getTypeZone() instanceof Desert)
								&& !(zones[x][y].getTypeZone() instanceof Riviere))
							animal.seDeplacer(x, y);
						else {
							x = ax + nextIntBounds(-1, 2);
							y = ay + nextIntBounds(-1, 2);
							if (x >= 0 && x < nbZonesH && y >= 0 && y < nbZonesL
									&& !(!(animal.getZone_actuel().getTypeZone() instanceof Desert)
											&& zones[x][y].getTypeZone() instanceof Desert)
									&& !(zones[x][y].getTypeZone() instanceof Riviere))
								animal.seDeplacer(x, y);
						}
				} catch (VolerException | SeDeplacerException e) {
				}
			} else if (r.nextInt(100) < 10) {
				int x = z.getX() + nextIntBounds(-1, 2);
				int y = z.getY() + nextIntBounds(-1, 2);
				if (x >= 0 && x < nbZonesH && y >= 0 && y < nbZonesL
						&& !(!(animal.getZone_actuel().getTypeZone() instanceof Desert)
								&& zones[x][y].getTypeZone() instanceof Desert))
					try {
						animal.seDeplacer(x, y);
					} catch (SeDeplacerException | VolerException e) {
					}
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
							rx = nextIntBounds(-1, 2);
							ry = nextIntBounds(-1, 2);
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
			if (animal.getAge() == animal.getAgeMax() || animal.getNbCyclesSansEau() >= 4
					|| animal.getNbCyclesSansManger() >= 20) {
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
			z.changerTemperature(nextIntBounds(-4, 5));
		} catch (ChangerTemperatureException e) {
		}
	}

	public void nextCycle() {
		this.redessine();
		int i, j;
		for (i = 0; i < nbZonesH; i++) {
			for (j = 0; j < nbZonesL; j++) {
				Zone z = zones[i][j];
				this.evolutionEnvironement(z);
				this.vieillissement(z);
				this.abreuvage(z);
				this.propagation(z);
				this.reproduction(z);
				this.deplacementAleatoire(z);
				this.nourrissage(z);
			}
		}
		this.checkEvents();
		this.cycle++;
	}

	public void addEvent(int cycle, int x, int y, Animal animal, int nbAnimal, Vegetal vegetal, int nbVegetal,
			double eau, double temperature) {
		events.add(new Event(this, cycle, x, y, animal, nbAnimal, vegetal, nbVegetal, temperature, temperature));

	}

	public void checkEvents() {
		Iterator<Event> iter = events.iterator();
		while (iter.hasNext()) {
			Event e = iter.next();
			if (e.checkEvent(cycle)) {
				pastEvents.add(e);
				iter.remove();
			}
		}
	}

	public void simulation() {
		initSimulation();
		this.redessine();
		boolean b = true;
		while (true) {
			if (Ecosysteme.simulate) {
				b = true;
				this.nextCycle();
			} else if (b) {
				b = false;
				grille.redessine();
			}
			try {
				Thread.sleep(this.vitesseSimulation);
			} catch (InterruptedException e) {
			}
		}
	}

}
