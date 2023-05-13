package main.Animaux.Oiseaux;

import main.Zone;
import main.Animaux.Animal;
import main.TypeZones.TypeZone;

public abstract class Oiseau extends Animal {

	public Oiseau(Zone zone_actuel, double eauRequise, int ageMax, int tauxDeReproduction, int ageMinReproduction, TypeZone zoneFavorable) {
		super(zone_actuel, eauRequise, ageMax, tauxDeReproduction, ageMinReproduction, zoneFavorable);
	}
}
