package main.Vegetaux.Arbres;

import java.awt.Image;

import javax.swing.ImageIcon;

import main.Zone;
import main.TypeZones.TypeZone;
import main.Vegetaux.Vegetal;

public abstract class Arbre extends Vegetal {
	public static final Image icon = new ImageIcon("src/assets/vegetals/arbre.png").getImage();

	public Arbre(Zone zone_actuel, double eauRequise, int ageMax, int tauxDeReproduction,
			int ageMinReproduction, TypeZone zoneFavorable) {
		super(zone_actuel, eauRequise, ageMax, tauxDeReproduction, ageMinReproduction, zoneFavorable);
	}

}