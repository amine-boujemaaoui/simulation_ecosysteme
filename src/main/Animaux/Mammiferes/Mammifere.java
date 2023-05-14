package main.Animaux.Mammiferes;

import main.Zone;
import main.Animaux.Animal;
import main.Execeptions.ReproduireException;
import main.TypeZones.TypeZone;

public abstract class Mammifere extends Animal {

	public Mammifere(Zone zone_actuel, double eauRequise, int ageMax, int tauxDeReproduction, int ageMinReproduction, TypeZone zoneFavorable) {
		super(zone_actuel, eauRequise, ageMax, tauxDeReproduction, ageMinReproduction, zoneFavorable);
	}

	@Override
	public void seReproduire() throws ReproduireException {
	}
}
