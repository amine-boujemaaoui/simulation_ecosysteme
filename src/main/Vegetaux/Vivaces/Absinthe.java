package main.Vegetaux.Vivaces;

import main.Zone;
import main.Execeptions.PropagerException;
import main.TypeZones.Plaine;
import main.Vegetaux.Vegetal;

public class Absinthe extends Vivace {

	public Absinthe(Zone zone_actuel) { 
		super(zone_actuel, 0.00098,    3,      36,                 1,                  new Plaine(),  200);
		//	  zone_actuel, eauRequise, ageMax, tauxDeReproduction, ageMinReproduction, zoneFavorable, nbMinDansZoneFavorableForInit
	}

	@Override
	public void sePropager(int x, int y) throws PropagerException {
		if (x >= this.getZone_actuel().getEcosysteme().getNbZonesH() || x < 0
				|| y >= this.getZone_actuel().getEcosysteme().getNbZonesL() || y < 0)
			throw new PropagerException("ERREUR: tentative de propagation en dehors de la grille");
		else
			this.getZone_actuel().getEcosysteme().propagerVegetal(new Absinthe(this.getZone_actuel()), x, y);
	}
	
	@Override
	public Vegetal getNewVegetal() {
		return new Absinthe(null);
	}
}

