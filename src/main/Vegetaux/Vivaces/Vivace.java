package main.Vegetaux.Vivaces;

import javax.swing.ImageIcon;

import main.Zone;
import main.TypeZones.TypeZone;
import main.Vegetaux.Vegetal;

public abstract class Vivace extends Vegetal {
	
	public static final ImageIcon icon = new ImageIcon("src/assets/vegetals/vivace.png");

	public Vivace(Zone zone_actuel, double eauRequise, int ageMax, int tauxDeReproduction,
			int ageMinReproduction, TypeZone zoneFavorable, int nbMinDansZoneFavorableForInit) {
		super(zone_actuel, eauRequise, ageMax, tauxDeReproduction, ageMinReproduction, zoneFavorable, nbMinDansZoneFavorableForInit);
	}

}