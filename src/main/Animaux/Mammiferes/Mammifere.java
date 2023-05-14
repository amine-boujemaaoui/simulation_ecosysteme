package main.Animaux.Mammiferes;

import java.awt.Image;

import javax.swing.ImageIcon;

import main.Zone;
import main.Animaux.Animal;
import main.Execeptions.ReproduireException;
import main.TypeZones.TypeZone;

public abstract class Mammifere extends Animal {
	public static final Image  icon = new ImageIcon("src/assets/animals/mammifere.png").getImage();

	public Mammifere(Zone zone_actuel, double eauRequise, int ageMax, int tauxDeReproduction, int ageMinReproduction,
			TypeZone zoneFavorable) {
		super(zone_actuel, eauRequise, ageMax, tauxDeReproduction, ageMinReproduction, zoneFavorable);
	}

	@Override
	public void seReproduire() throws ReproduireException {
	}
}
