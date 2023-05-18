package main.Animaux.Mammiferes;

import javax.swing.ImageIcon;

import main.Zone;
import main.Animaux.Animal;
import main.Execeptions.ReproduireException;
import main.TypeZones.TypeZone;

public abstract class Mammifere extends Animal {

	public static final ImageIcon icon = new ImageIcon(
			System.getProperty("user.dir") + "/src/assets/animals/mammifere.png");

	public Mammifere(Zone zone_actuel, double eauRequise, int ageMax, int tauxDeReproduction, int ageMinReproduction,
			TypeZone zoneFavorable, int nbMinDansZoneFavorableForInit) {
		super(zone_actuel, eauRequise, ageMax, tauxDeReproduction, ageMinReproduction, zoneFavorable,
				nbMinDansZoneFavorableForInit);
	}

	@Override
	public void seReproduire() throws ReproduireException {
	}
}
