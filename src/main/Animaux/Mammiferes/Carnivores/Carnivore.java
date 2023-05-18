package main.Animaux.Mammiferes.Carnivores;

import interfaces.Manger;
import main.Zone;
import main.Animaux.Mammiferes.Mammifere;
import main.Execeptions.MangerException;
import main.TypeZones.TypeZone;

public abstract class Carnivore extends Mammifere implements Manger{

	public Carnivore(Zone zone_actuel, double eauRequise, int ageMax, int tauxDeReproduction, int ageMinReproduction, TypeZone zoneFavorable, int nbMinDansZoneFavorableForInit) {
		super(zone_actuel, eauRequise, ageMax, tauxDeReproduction, ageMinReproduction, zoneFavorable, nbMinDansZoneFavorableForInit);
	}

	@Override
	public void manger() throws MangerException {
	}

}
