package main.Vegetaux.Arbres;

import main.Zone;
import main.Execeptions.PropagerException;
import main.TypeZones.Foret;
import main.Vegetaux.Vegetal;

public class Sapin extends Arbre {

	public Sapin(Zone zone_actuel) {
		super(zone_actuel, 0.004,      24,     10,                  12,                 new Foret(),   40);
		//	  zone_actuel, eauRequise, ageMax, tauxDeReproduction, ageMinReproduction, zoneFavorable, nbMinDansZoneFavorableForInit
	}

	@Override
	public void sePropager(int x, int y) throws PropagerException {
		if (x >= this.getZone_actuel().getEcosysteme().getNbZonesH() || x < 0
				|| y >= this.getZone_actuel().getEcosysteme().getNbZonesL() || y < 0)
			throw new PropagerException("ERREUR: tentative de propagation en dehors de la grille");
		else
			this.getZone_actuel().getEcosysteme().propagerVegetal(new Sapin(this.getZone_actuel()), x, y);
	}
	
	@Override
	public Vegetal getNewVegetal() {
		return new Sapin(null);
	}
}
