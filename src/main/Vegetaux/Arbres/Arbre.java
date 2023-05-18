package main.Vegetaux.Arbres;

import main.Zone;
import main.TypeZones.TypeZone;
import main.Vegetaux.Vegetal;
import javax.swing.ImageIcon;

public abstract class Arbre extends Vegetal {
	
	public static final ImageIcon icon = new ImageIcon("src/assets/vegetals/arbre.png");

	public Arbre(Zone zone_actuel, double eauRequise, int ageMax, int tauxDeReproduction,
			int ageMinReproduction, TypeZone zoneFavorable, int nbMinDansZoneFavorableForInit) {
		super(zone_actuel, eauRequise, ageMax, tauxDeReproduction, ageMinReproduction, zoneFavorable, nbMinDansZoneFavorableForInit);
	}

}