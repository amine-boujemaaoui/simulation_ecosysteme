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
	private int nbZonesH; // nombre de zones en hauteur
	private int nbZonesL; // nombre de zones en largeur
	private Grille grille; // objet grille pour l'affichage de la fenetre graphique
	private Zone[][] zones; // tableau de zones de la simulation
	private ArrayList<TypeZone> typeZones = new ArrayList<TypeZone>(); // liste des type de zones de la simulation
	private ArrayList<Animal> typeAnimaux = new ArrayList<Animal>(); // liste des type d'animaux de la simulation
	private ArrayList<Vegetal> typeVegetaux = new ArrayList<Vegetal>(); // liste des type de vegetaux de la simulation
	private ArrayList<Event> events = new ArrayList<Event>(); // liste des evenements a venir de la simulation
	private ArrayList<Event> pastEvents = new ArrayList<Event>(); // liste des evenements passe de la simulation
	private int cycle; // cycle actuel de la simulation
	public static boolean simulate = false; // si la simulation est em cours ou arreter
	private int vitesseSimulation; // vitess de simulation [50ms, 100ms, 150ms, 200ms, 250ms, 300ms]
	private int nbMaxEntiteParZone; // nombre max d'entite par zone
	private final int maxNbCasesL = 27, maxNbCasesH = 14; // taille max en hauteur et largeur de la map de la simulation
	private int[][] defaultZones; // tableau des identifiant de type de zone de la map a l'initialisation
	private Random r = new Random();

	/**
	 * Ecosysteme class constructor.
	 * 
	 * @param map                 The name of the map file that contains the default
	 *                            zones information.
	 * @param defaultTypeZones    The default TypeZones for the grid generation.
	 * @param defaultTypeAnimaux  The deefault animals type for the initial random
	 *                            generation of animals.
	 * @param defaultTypeVegetaux The deefault vegetals type for the initial random
	 *                            generation of vegetals.
	 * @param defaultEvents       The list of events that wil happen during the
	 *                            simulation.
	 * @param nbMaxEntiteParZone  The maximum number of entity per zone.
	 *
	 */
	public Ecosysteme(String map, ArrayList<TypeZone> defaultTypeZones, ArrayList<Animal> defaultTypeAnimaux,
			ArrayList<Vegetal> defaultTypeVegetaux, ArrayList<Event> defaultEvents, int nbMaxEntiteParZone) {
		super();
		chargerdefaultZones(map); // on charge la map donner en parametre dans l'attribut this.defaultZones
		this.grille = new Grille(nbZonesL, nbZonesH, 64, true, this);
		this.zones = new Zone[nbZonesH][nbZonesL]; // avec la methode chargerdefaultZones(map) on a reccupere nbZonesH
													// et nbZonesL
		this.cycle = 0;
		this.vitesseSimulation = 100;
		this.nbMaxEntiteParZone = nbMaxEntiteParZone;

		if (defaultTypeZones.size() > 0) // si la taille de la liste des type de zone est > 0
			this.typeZones.addAll(defaultTypeZones); // on les ajoutes au type de zone de l'ecosysteme
		if (defaultTypeAnimaux.size() > 0) // si la taille de la liste des type d'animaux est > 0
			this.typeAnimaux.addAll(defaultTypeAnimaux); // on les ajoutes au type d'animaux de l'ecosysteme
		if (defaultTypeVegetaux.size() > 0) // si la taille de la liste des type de vegetaux est > 0
			this.typeVegetaux.addAll(defaultTypeVegetaux); // on les ajoutes au type de vegetaux de l'ecosysteme
		if (defaultEvents.size() > 0) // si la taille de la liste des events est > 0
			defaultEvents.forEach((event) -> {
				event.setEcosysteme(this); // on ratache l'event a cet ecosysteme
				this.pastEvents.add(event); // on l'ajoute au event passe pour plus tard les traiter
			});

	}

	/**
	 * This function reads a map file and stores the default zones in a 2D array.
	 * 
	 * @param map The name of the map file that contains the default zones
	 *            information.
	 */
	public void chargerdefaultZones(String map) {
		try {
			// on reccuper le fichier txt donner en parametre pour charger les type de zones
			// par defaut
			InputStream is = getClass().getResourceAsStream("/maps/" + map);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			int[][] tempDefaultZones = new int[maxNbCasesH][maxNbCasesL];
			String line = br.readLine();
			this.nbZonesL = line.length() / 2;
			this.nbZonesL += (line.length() % 2 == 0) ? 0 : 1; // si le nombre de colonne est pair ou non, on ajoute 1
																// ou 0
			int col = 0, row = 0;
			while (line != null) {
				while (col < nbZonesL) {
					// on cree un tableau de int dont les valeur sont les chiffre de la ligne actuel
					// qui son separer par un espace
					String idDefaultZones[] = line.split(" ");
					int id = Integer.parseInt(idDefaultZones[col]);
					// on met le int reccuperer dans le tableau temporaire
					tempDefaultZones[row][col] = id;
					col++;
				}
				if (col == nbZonesL) {
					col = 0;
					row++;
				}
				line = br.readLine();
			}
			// le nombre de case en hauteur est le nombre de fois ou on a lus une ligne dans
			// le fichier
			this.nbZonesH = row;
			br.close();
			// on initialise le tableau des identifiant des zones par defaut
			this.defaultZones = new int[nbZonesH][nbZonesL];
			for (int i = 0; i < nbZonesH; i++)
				for (int j = 0; j < nbZonesL; j++)
					// on complete le tableau des identifiant des zones par defaut avec le tabelau
					// temporaire
					this.defaultZones[i][j] = tempDefaultZones[i][j];
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
		}
	}

	/**
	 * This function returns a random integer within a specified range.
	 * 
	 * @param min The minimum value that the method should return.
	 * @param max The maximum value that the method can return (exclusive).
	 * @return The method is returning an integer value that is randomly generated
	 *         within the specified bounds of `min` and `max`.
	 */
	public int nextIntBounds(int min, int max) {
		return r.nextInt(max - min) + min;
	}

	/**
	 * The function initializes the simulation by setting certain variables and
	 * creating zones with default settings, events, and random numbers of animals
	 * and plants.
	 */
	public void initSimulation() {
		Ecosysteme.simulate = false; // la simulation est mise en pause par defaut
		this.zones = new Zone[nbZonesH][nbZonesL]; // on reinitialise le tableau de zone pour relancer la simulation
		this.cycle = 0; // on reinitialise le cycle de la simulation
		initdefaultZones(); // reinitialisation des type de zones par defaut en fonction du tableau des
							// identifiant des type de zone par defaut
		initEvents(); // on redeplace les events passe dans la lise des events a venir
		initRandomNbAnimaux(); // reinitialisation d'un certain nombre d'animaux de different type en fonction
								// de leur zone favorable
		initRandomNbVegetaux(); // reinitialisation d'un certain nombre de vegetaux de different type en
								// fonction
								// de leur zone favorable
	}

	/**
	 * This function returns a copy of an ArrayList of TypeZone objects.
	 * 
	 * @return An ArrayList of TypeZone objects is being returned. The method
	 *         creates a copy of the original ArrayList called "typeZones_copie" and
	 *         adds all the elements of the original ArrayList "typeZones" to it.
	 *         The copy is then returned.
	 */
	public ArrayList<TypeZone> getTypeZones() {
		ArrayList<TypeZone> typeZones_copie = new ArrayList<TypeZone>();
		typeZones_copie.addAll(typeZones);
		return typeZones_copie;
	}

	/**
	 * This Java function removes an event from a list of events.
	 * 
	 * @param e The parameter "e" is an object of the class "Event" that is being
	 *          passed to the method "removeEvent". The method removes this
	 *          particular event object from the list of events stored in the
	 *          current object.
	 */
	public void removeEvent(Event e) {
		this.events.remove(e);
	}

	/**
	 * This function returns the value of the variable nbZonesH.
	 * 
	 * @return The method is returning an integer value which represents the number
	 *         of horizontal zones.
	 */
	public int getNbZonesH() {
		return nbZonesH;
	}

	/**
	 * This function returns the value of the variable nbZonesL.
	 * 
	 * @return The method is returning an integer value which represents the number
	 *         of zones in the L direction.
	 */
	public int getNbZonesL() {
		return nbZonesL;
	}

	public Zone getZone(int i, int j) {
		return zones[i][j];
	}

	/**
	 * This Java function returns the zone for a given position in a 2D array.
	 * 
	 * @param j The column index of the zone in the 2D array of zones.
	 * @param i The row index of the zone in the 2D array of zones.
	 * @return The method `getZoneForGrille` is returning a `Zone` object. The
	 *         specific `Zone` object being returned is determined by the `i` and
	 *         `j` parameters passed to the method, which are used to access the
	 *         `zones` array.
	 */
	public Zone getZoneForGrille(int j, int i) {
		return zones[i][j];
	}

	/**
	 * The function returns a Grille object.
	 * 
	 * @return The method is returning an object of type Grille.
	 */
	public Grille getGrille() {
		return grille;
	}

	/**
	 * The function returns the value of the variable "cycle".
	 * 
	 * @return The method `getCycle()` is returning an integer value which is the
	 *         value of the variable `cycle`.
	 */
	public int getCycle() {
		return cycle;
	}

	/**
	 * This function returns the maximum number of entities allowed per zone.
	 * 
	 * @return The method is returning an integer value which represents the maximum
	 *         number of entities allowed per zone.
	 */
	public int getNbMaxEntiteParZone() {
		return nbMaxEntiteParZone;
	}

	/**
	 * This function sets the maximum number of entities per zone in a Java program.
	 * 
	 * @param nbMaxEntiteParZone This parameter represents the maximum number of
	 *                           entities that can be present in a single zone. The
	 *                           method sets the value of this parameter.
	 */
	public void setNbMaxEntiteParZone(int nbMaxEntiteParZone) {
		this.nbMaxEntiteParZone = nbMaxEntiteParZone;
	}

	/**
	 * This function increases the speed of a simulation by 50 units, up to a
	 * maximum of 300 units.
	 */
	public void augmenterVistesseSimulation() {
		if (this.vitesseSimulation + 50 <= 300)
			this.vitesseSimulation += 50;
	}

	/**
	 * The function decreases the simulation speed by 50 if it is greater than or
	 * equal to 50.
	 */
	public void diminuerVistesseSimulation() {
		if (this.vitesseSimulation - 50 >= 50)
			this.vitesseSimulation -= 50;
	}

	/**
	 * This Java function returns the speed of the simulation.
	 * 
	 * @return The method is returning an integer value, which is the current speed
	 *         of the simulation.
	 */
	public int getVitesseSimulation() {
		return this.vitesseSimulation;
	}

	/**
	 * The function moves an animal to a new zone if the new zone is not a river.
	 * 
	 * @param animal The animal object that needs to be moved to a new zone.
	 * @param x      The x-coordinate of the destination zone where the animal is
	 *               being moved to.
	 * @param y      The "y" parameter in the "deplacerAnimal" method is an integer
	 *               representing the y-coordinate of the destination zone where the
	 *               animal is being moved to.
	 */
	public void deplacerAnimal(Animal animal, int x, int y) {
		// si la zone ou l'animal va se deplacer n'est pas une riviere alors on le
		// deplace et on redefinis ca zone actuel
		if (!(this.zones[x][y].getTypeZone() instanceof Riviere)) {
			animal.getZone_actuel().removeAnimal(animal);
			this.zones[x][y].addAnimal(animal);
			animal.setZone_actuel(zones[x][y]);
		}
	}

	/**
	 * The function adds a given vegetal to a specific zone on the map if the zone
	 * is not a river.
	 * 
	 * @param vegetal The vegetal object that needs to be propagated in the
	 *                specified zone.
	 * @param x       The x-coordinate of the zone where the vegetal is being
	 *                propagated.
	 * @param y       The parameter "y" represents the vertical coordinate of the
	 *                zone where the vegetal will be propagated.
	 */
	public void propagerVegetal(Vegetal vegetal, int x, int y) {
		// si la zone ou le vegetal va se deplacer n'est pas une riviere alors on
		// ajoute un nouveau vegetal et on redefinis ca zone actuel
		if (!(this.zones[x][y].getTypeZone() instanceof Riviere)) {
			this.zones[x][y].addVegetal(vegetal);
			vegetal.setZone_actuel(zones[x][y]);
		}
	}

	/**
	 * This function redraws a grid with different types of animals and plants
	 * represented by colored discs or icons.
	 */
	public void redessine() {
		int taillePaquets = 32, tailleRestant = 0; // on definit une taille maximal de l'icon en fonction du nombre
													// d'entite
		grille.setCycle(cycle); // on actualise la valeur du cycle actuel
		// pour chuaque zones
		for (int i = 0; i < nbZonesH; i++) {
			for (int j = 0; j < nbZonesL; j++) {
				Zone z = zones[i][j];
				grille.resetlDisques(i, j); // on reinitialise la liste des disques
				// on definit l'icon de fond en fonction du type de zone
				grille.iconFond(i, j, z.getTypeZone().getTypeZoneIcon());
				// on definis la taille des icons en fonction du nombre d'animaux
				int[] nbAnimaux = { z.getNbInsecte(), z.getNbOiseau(), z.getNbMammifere() };
				// on definis les icones des type d'animaux
				ImageIcon[] animauxIcons = { Insecte.icon, Oiseau.icon, Mammifere.icon };
				// on definis la taille des icons en fonction du nombre de vegetaux
				int[] nbVegetaux = { z.getNbVivace(), z.getNbArbre() };
				// on definis les icones des type de vegetaux
				ImageIcon[] vegetauxIcons = { Vivace.icon, Arbre.icon };

				// Pour chaque type d'animaux, on calcule un nombre de paquet et un reste puis
				// on ajoute le nombre de paquet fois l'icone du type d'animal et une icone de
				// la taille du reste
				for (int l = 0; l < 3; l++) {
					for (int k = 0; k < nbAnimaux[l] / taillePaquets; k++) {
						tailleRestant = (nbAnimaux[l] > 0) ? 0 : taillePaquets / 2;
						grille.addDisqueAnimaux(animauxIcons[l], i, j, taillePaquets, Color.BLUE);
					}
					grille.addDisqueAnimaux(animauxIcons[l], i, j, nbAnimaux[l] % taillePaquets + tailleRestant,
							Color.BLUE);
				}

				// Pour chaque type de vegetaux, on calcule un nombre de paquet et un reste puis
				// on ajoute le nombre de paquet fois l'icone du type de vegetal et une icone de
				// la taille du reste
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
		// on redessine la grille a l'ecran
		grille.redessine();
	}

	/**
	 * This function initializes default zones with random values for water and
	 * temperature within specified ranges.
	 */
	public void initdefaultZones() {
		// pour chaque zones
		for (int i = 0; i < nbZonesH; i++) {
			for (int j = 0; j < nbZonesL; j++) {
				double minEau, maxEau, minTemperature, maxTemperature;
				// on recupere le niveau d'eau minimal/maximal et la temperature minimal/maximal
				// en fonction du type de la zone actuel
				minEau = this.typeZones.get(this.defaultZones[i][j]).getEauMin();
				maxEau = this.typeZones.get(this.defaultZones[i][j]).getEauMax();
				minTemperature = this.typeZones.get(this.defaultZones[i][j]).getTemperatureMin();
				maxTemperature = this.typeZones.get(this.defaultZones[i][j]).getTemperatureMin();
				// on genere des nombre aleatoire avec comme interval les valeur determiner
				// juste avant
				double eau = Math.round((r.nextDouble() * (maxEau - minEau) + minEau) * 100.0) / 100.0;
				double temperature = Math
						.round((r.nextDouble() * (maxTemperature - minTemperature) + minTemperature) * 100.0) / 100.0;
				// on cree une nouvel zone avec comme parametre les valeur generer aleatoirement
				// jsute avant
				zones[i][j] = new Zone(i, j, eau, temperature, this);
				// on definis le type de zone de la zone actuel comme celle du type de zone par
				// defaut a cette position dans la grille des type de zones par defaut
				zones[i][j].setTypeZone(this.typeZones.get(this.defaultZones[i][j]));
			}
		}
	}

	/**
	 * The function initializes events by adding all past events and clearing the
	 * list of past events.
	 */
	public void initEvents() {
		events.addAll(pastEvents);
		pastEvents.clear();
	}

	/**
	 * This function initializes a random number of animals in each zone based on
	 * the type of zone and the maximum number of animals allowed in that zone.
	 */
	private void initRandomNbAnimaux() {
		// pour chaque zone
		for (int i = 0; i < nbZonesH; i++) {
			for (int j = 0; j < nbZonesL; j++) {
				Zone z = zones[i][j];
				// on definit qu'il y a 35% de chance que cette zone soit peuple
				if (r.nextInt(100) <= 35) {
					// si la zone actuel est une riviere ou un desert, on passe a la zone suivante
					if (z.getTypeZone() instanceof Riviere || z.getTypeZone() instanceof Desert)
						continue;
					// pour chaque type d'animal different de l'ecosysteme
					this.typeAnimaux.forEach((typeAnimal) -> {
						int nbMax;
						// si le type de zone actuel est le meme que la zone favorable de l'animal
						if (z.getTypeZone().getClass() == typeAnimal.getZoneFavorable().getClass())
							// on definit la max de cet animal comme celui definis par ca classe
							nbMax = typeAnimal.getNbMinDansZoneFavorableForInit();
						else
							// sinon, le meme diviser par 4
							nbMax = typeAnimal.getNbMinDansZoneFavorableForInit() / 4;
						for (int k = 0; k < nextIntBounds(nbMax / 2, nbMax); k++) {
							// on cree ce type d'animal entre nbMax / 2 et nbMax fois en redefinisant leur
							// zone actuel
							Animal animal = typeAnimal.getNewAnimal();
							animal.setZone_actuel(z);
							z.addAnimal(animal);
						}

					});
				}
			}
		}
	}

	/**
	 * This function initializes a random number of vegetation in each zone based on
	 * certain conditions.
	 */
	private void initRandomNbVegetaux() {
		// meme principe que initRandomNbAnimaux()
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

	/**
	 * This function randomly moves animals within a given zone, taking into account
	 * their preferred habitat and avoiding certain types of zones.
	 * 
	 * @param z The parameter "z" is an object of the class "Zone", which represents
	 *          a specific zone in the simulation. The method "deplacementAleatoire"
	 *          takes all the animals present in this zone and randomly moves them
	 *          to a neighboring zone that is favorable to their habitat. If the
	 *          animal is already in
	 */
	public void deplacementAleatoire(Zone z) {
		// pour chaque animal de la zone z
		z.getAnimaux().forEach((animal) -> {
			if (!animal.isDejaTraiterCeCycle()) {
				// si l'animal n'est pas dans ca zone favorablea
				if (!(z.getTypeZone().getClass() == animal.getZoneFavorable().getClass())) {
					int x = 0, y = 0, rx, ry, ax = z.getX(), ay = z.getY(); // on reccupere la position de la zone
																			// actuel
					boolean END = true, HG = true, HM = true, HD = true, MG = true, MM = true, MD = true, BG = true,
							BM = true, BD = true; // on definit des indicateur de verification, par exemple HG
													// signifiant en
													// Haut a Gauche de la zone actuel, et un indicateur pour
													// determiner si
													// une zone favorable a ete trouver (END)
					while (END && (HG || HM || HD || MG || MM || MD || BG || BM || BD)) {
						rx = nextIntBounds(-1, 2);
						ry = nextIntBounds(-1, 2);
						// on genere deux variable aleatoirement valant -1, 0 ou 1
						if (rx == -1 && ry == -1)
							HG = false; // on redefinis l'indicateur de postion commme verifier
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
						// si on reste bien a l'interieur de la grille et que l'on a trouver une zone
						// dont le type est le meme que la zone favorable de l'animal, on calcule x et y
						// pour ce deplacer dans cette zone
						if (ax + rx >= 0 && ax + rx < nbZonesH && ay + ry >= 0 && ay + ry < nbZonesL
								&& zones[ax + rx][ay + ry].getTypeZone().getClass() == animal.getZoneFavorable()
										.getClass()) {
							x = ax + rx;
							y = ay + ry;
							END = false;
						}
					}
					try {
						if (END) // si la zone favorable n'a pas ete trouver
							// si la zone ou on ce trouve est pas un desert et qu on veux ce deplacer vers
							// un desert, on ne se deplace pas
							if (x >= 0 && x < nbZonesH && y >= 0 && y < nbZonesL
									&& !(!(animal.getZone_actuel().getTypeZone() instanceof Desert)
											&& zones[x][y].getTypeZone() instanceof Desert)
									&& !(zones[x][y].getTypeZone() instanceof Riviere))
								animal.seDeplacer(x, y); // on se deplace
							else {
								// sinon, on se deplace vers une case aleatoirement
								x = ax + nextIntBounds(-1, 2);
								y = ay + nextIntBounds(-1, 2);
								if (x >= 0 && x < nbZonesH && y >= 0 && y < nbZonesL
										&& !(!(animal.getZone_actuel().getTypeZone() instanceof Desert)
												&& zones[x][y].getTypeZone() instanceof Desert)
										&& !(zones[x][y].getTypeZone() instanceof Riviere))
									animal.seDeplacer(x, y);
								animal.setDejaTraiterCeCycle(true);
							}
					} catch (VolerException | SeDeplacerException e) {
					}
				} else if (r.nextInt(100) < 10) { // si l'animal est bien dans ca zone favorable, il y a quand meme
													// 10% de
													// chance qu'il ce deplace
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
				animal.setDejaTraiterCeCycle(true);
			}
		});
	}

	/**
	 * The function iterates through the insects, birds, and mammals in a given zone
	 * and attempts to reproduce them based on certain conditions.
	 * 
	 * @param z a Zone object, representing a specific zone in the simulation.
	 */
	public void reproduction(Zone z) {
		// pour chaque insecte de la zone z
		z.getInsectes().forEach((insecte) -> {
			if (!insecte.isDejaTraiterCeCycle()) {
				if (z.getNbInsecte() > 0) {
					try {
						// si l'insecte a deppaser son age minimum de reproduction
						// et qu'il ne c'est pas deja reproduit ce cycle
						// et qu'il est bien dans ca zone favorable
						if (insecte.getAge() >= insecte.getAgeMinReproduction() && !insecte.isDejaReproduiCecycle()
								&& r.nextInt(100) <= insecte.getTauxDeReproduction()
								&& insecte.getZoneFavorable().getClass() == z.getTypeZone().getClass())
							insecte.seReproduire(); // il se reproduit
					} catch (ReproduireException e) {
					}
				}
			}
		});
		// de meme pour les oiseaux
		z.getOiseaux().forEach((oiseau) -> {
			if (!oiseau.isDejaTraiterCeCycle()) {
				if (z.getNbOiseau() > 0) {
					try {
						if (oiseau.getAge() >= oiseau.getAgeMinReproduction() && !oiseau.isDejaReproduiCecycle()
								&& oiseau.getNbCyclesSansEau() == 0 && oiseau.getNbCyclesSansManger() == 0
								&& r.nextInt(100) <= oiseau.getTauxDeReproduction())
							oiseau.seReproduire();
					} catch (ReproduireException e) {
					}
				}
			}
		});
		// de meme pour les mammifere
		z.getMammiferes().forEach((mammifere) -> {
			if (!mammifere.isDejaTraiterCeCycle()) {
				if (z.getNbMammifere() > 0) {
					try {
						if (mammifere.getAge() >= mammifere.getAgeMinReproduction()
								&& !mammifere.isDejaReproduiCecycle() && mammifere.getNbCyclesSansEau() == 0
								&& mammifere.getNbCyclesSansManger() == 0
								&& r.nextInt(100) <= mammifere.getTauxDeReproduction())
							mammifere.seReproduire();
					} catch (ReproduireException e) {
					}
				}
			}
		});

	}

	/**
	 * This function propagates vegetation to neighboring zones based on certain
	 * conditions.
	 * 
	 * @param z The parameter "z" is an object of the class "Zone" which represents
	 *          a zone in a simulation. The method "propagation" takes this object
	 *          as a parameter and performs certain actions on the vegetation
	 *          present in this zone.
	 */
	public void propagation(Zone z) {
		// meme principe que la methode de deplacement des animaux a la differance que
		// l'on cree un nouveau vegetal a la postionde destination et que les vegetaux
		// peuvent se propager dans les zones non favorable voisine aux zones favorable
		z.getVegetaux().forEach((vegetal) -> {
			if (!vegetal.isDejaTraiterCeCycle()) {
				if (z.getNbVegetaux() > 0) {
					try {
						if (vegetal.getAge() >= vegetal.getAgeMinPropagation()
								&& vegetal.getZoneFavorable().getClass() == z.getTypeZone().getClass()
								&& r.nextInt(100) < vegetal.getTauxDePropagation()) {
							int x = 0, y = 0, rx, ry, vx = z.getX(), vy = z.getY();
							boolean END = true, HG = true, HM = true, HD = true, MG = true, MM = true, MD = true,
									BG = true, BM = true, BD = true;
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
				vegetal.setDejaTraiterCeCycle(true);
			}
		});
	}

	/**
	 * The function updates the age and removes animals and plants that have reached
	 * their maximum age or have gone without food or water for too long.
	 * 
	 * @param z a Zone object that represents a specific area in the simulation
	 *          containing both animals and plants.
	 */
	public void vieillissement(Zone z) {
		// pour chauque animal de la zone z
		z.getAnimaux().forEach((animal) -> {
			if (!animal.isDejaTraiterCeCycle()) {
				// si l'animal a atteint son age maximal
				// ou si il n'a pas bu depuis au moins 4 cycle
				// ou si il n'a pas manger depuis au moins 20 cycle
				if (animal.getAge() == animal.getAgeMax() || animal.getNbCyclesSansEau() >= 3
						|| animal.getNbCyclesSansManger() >= 10) {
					z.removeAnimal(animal); // il meure
				} else {
					// sinon, on redefinis qu'il ne c'est pas deja reproduit pour le cycle suivant
					animal.setDejaReproduiCecycle(false);
					// et il viellis d'un cycle
					animal.vieillir();
				}
			}
		});
		// de meme pour les vegetaux
		z.getVegetaux().forEach((vegetal) -> {
			if (!vegetal.isDejaTraiterCeCycle()) {
				if (vegetal.getAge() == vegetal.getAgeMax()
						|| vegetal.getZone_actuel().getTypeZone() instanceof Desert) {
					z.removeVegetal(vegetal);
				} else
					vegetal.vieillir();
			}
		});
	}

	/**
	 * The function performs feeding and watering actions for all animals and plants
	 * in a given zone.
	 * 
	 * @param z The parameter "z" is an object of the class "Zone", which represents
	 *          a zone in a zoo where animals and plants are kept. The method
	 *          "nourrissage" takes this zone as input and performs feeding
	 *          operations on all the animals and plants present in the zone.
	 */
	public void nourrissage(Zone z) {
		// pour chaque animal de la zone z
		z.getAnimaux().forEach((animal) -> {
			if (!animal.isDejaTraiterCeCycle()) {
				try {
					animal.boir(); // on le fait boir
					animal.manger(); // et manger
				} catch (BoirException e) {
				} catch (MangerException e) {
				}
			}
		});
		// pour chaque vegetal de la zone z
		z.getVegetaux().forEach((vegetal) -> {
			if (!vegetal.isDejaTraiterCeCycle()) {
				try {
					vegetal.boir(); // on le fait boir
				} catch (BoirException e) {
				}
			}
		});
	}

	/**
	 * This function evolves the environment of a given zone by verifying its type
	 * and changing its temperature within a certain range.
	 * 
	 * @param z The parameter "z" is an object of the class "Zone".
	 */
	public void evolutionEnvironement(Zone z) {
		// on verifie le type de zone de la zone z
		z.verifierTypeZone();
		try {
			// on fait evoluer ca temperature de -4 a +5 de celle actuel
			z.changerTemperature(nextIntBounds(-4, 5));
		} catch (ChangerTemperatureException e) {
		}
	}

	/**
	 * The function updates the simulation environment by iterating through each
	 * zone and performing various actions such as evolution, aging, reproduction,
	 * and feeding.
	 */
	public void nextCycle() {
		// a chaque cycle
		int i, j;
		for (i = 0; i < nbZonesH; i++) {
			for (j = 0; j < nbZonesL; j++) {
				Zone z = zones[i][j];
				this.evolutionEnvironement(z); // on reverifie l'environement
				this.vieillissement(z); // les etre vivant villeilssent
				this.propagation(z); // les vegetaux se propage
				this.reproduction(z); // les animaux se reproduisent
				this.nourrissage(z); // les etre vivant se nourisse en eau et autres etre vivant
				this.deplacementAleatoire(z); // les aninaux se deplace
			}
		}
		// on verifie si un evenement doit arriver
		this.checkEvents();
		// on passe au cycle suivant
		this.cycle++;
		// on redessine la grille avec le nouvel etat de l'ecosysteme
		this.redessine();
	}

	public void resetDejaTraiter() {
		// a chaque cycle
		int i, j;
		for (i = 0; i < nbZonesH; i++) {
			for (j = 0; j < nbZonesL; j++) {
				Zone z = zones[i][j];
				z.getAnimaux().forEach((animal) -> animal.setDejaTraiterCeCycle(false));
				z.getVegetaux().forEach((vegetal) -> vegetal.setDejaTraiterCeCycle(false));
			}
		}
	}

	/**
	 * This function adds an event to a list of events with various parameters such
	 * as cycle, coordinates, animal and vegetal information, water and temperature
	 * levels.
	 * 
	 * @param cycle       The cycle at which the event occurred. It refer to a
	 *                    specific iteration in the simulation.
	 * @param x           The x-coordinate of the location where the event occurred.
	 * @param y           The "y" parameter in the "addEvent" method is an integer
	 *                    representing the y-coordinate of the location where the
	 *                    event occurred.
	 * @param animal      The animal parameter is an object of the Animal class,
	 *                    which represents an animal in the simulation. It may
	 *                    contain information such as the animal's species, age,
	 *                    gender, and other characteristics.
	 * @param nbAnimal    nbAnimal stands for the number of animals of the specified
	 *                    type (represented by the "animal" parameter) present at
	 *                    the specified location (x,y) during the event.
	 * @param vegetal     The "vegetal" parameter is an object of the class
	 *                    "Vegetal", which likely represents a type of plant or
	 *                    vegetation in the simulation. It is used to specify the
	 *                    type of vegetation present at the location where the event
	 *                    is being added.
	 * @param nbVegetal   nbVegetal refers to the number of instances of the Vegetal
	 *                    object present in the event being added.
	 * @param eau         The "eau" parameter is not included in the method
	 *                    signature, so it is not a parameter for this method.
	 * @param temperature The temperature parameter is a double value representing
	 *                    the temperature at which the event occurred. It could be
	 *                    the temperature of the environment or the temperature of a
	 *                    specific location where the event took place.
	 */
	public void addEvent(int cycle, int x, int y, Animal animal, int nbAnimal, Vegetal vegetal, int nbVegetal,
			double eau, double temperature) {
		events.add(new Event(this, cycle, x, y, animal, nbAnimal, vegetal, nbVegetal, temperature, temperature));

	}

	/**
	 * The function checks events and moves them from the events list to the
	 * pastEvents list if they have already occurred.
	 */
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

	/**
	 * This function runs a simulation indefinitely, updating the state of the
	 * ecosystem and redrawing it at a given speed.
	 */
	public void simulation() {
		initSimulation();
		this.redessine();
		boolean b = true;
		while (true) {
			if (Ecosysteme.simulate) {
				b = true;
				this.nextCycle();
				resetDejaTraiter();
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
