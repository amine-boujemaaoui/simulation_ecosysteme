package main.Vegetaux.Vivaces;

import main.Zone;
import main.Execeptions.PropagerException;
import main.TypeZones.Plaine;
import main.Vegetaux.Vegetal;

public class Artichaut extends Vivace {

	public Artichaut(Zone zone_actuel) {
		super(zone_actuel, 0.0009,     5,      25,                 3,                  new Plaine(),  170);
		//    zone_actuel, eauRequise, ageMax, tauxDeReproduction, ageMinReproduction, zoneFavorable, nbMinDansZoneFavorableForInit
	}

	@Override
	public void sePropager(int x, int y) throws PropagerException {
		if (x >= this.getZone_actuel().getEcosysteme().getNbZonesH() || x < 0
				|| y >= this.getZone_actuel().getEcosysteme().getNbZonesL() || y < 0)
			throw new PropagerException("ERREUR: tentative de propagation en dehors de la grille");
		else
			this.getZone_actuel().getEcosysteme().propagerVegetal(new Artichaut(this.getZone_actuel()), x, y);
	}
	
	@Override
	public Vegetal getNewVegetal() {
		return new Artichaut(null);
	}
}
