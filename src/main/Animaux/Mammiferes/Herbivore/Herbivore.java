package main.Animaux.Mammiferes.Herbivore;

import interfaces.Manger;
import main.Zone;
import main.Animaux.Mammiferes.Mammifere;
import main.Execeptions.MangerException;
import main.TypeZones.TypeZone;

public class Herbivore extends Mammifere implements Manger{

	public Herbivore(Zone zone_actuel, double eauRequise, int ageMax, int tauxDeReproduction, int ageMinReproduction, TypeZone zoneFavorable) {
		super(zone_actuel, eauRequise, ageMax, tauxDeReproduction, ageMinReproduction, zoneFavorable);
	}

	@Override
	public void manger() throws MangerException {
	}
}
