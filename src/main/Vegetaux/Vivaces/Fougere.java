package main.Vegetaux.Vivaces;

import main.Zone;
import main.Execeptions.PropagerException;
import main.TypeZones.Foret;
import main.Vegetaux.Vegetal;

public class Fougere extends Vivace {

	public Fougere(Zone zone_actuel) {
		super(zone_actuel, 0.009, 4, 10, 3, new Foret(), 80);
	}

	@Override
	public void sePropager(int x, int y) throws PropagerException {
		if (x >= this.getZone_actuel().getEcosysteme().getNbZonesH() || x < 0
				|| y >= this.getZone_actuel().getEcosysteme().getNbZonesL() || y < 0)
			throw new PropagerException("ERREUR: tentative de propagation en dehors de la grille");
		else
			this.getZone_actuel().getEcosysteme().propagerVegetal(new Fougere(this.getZone_actuel()), x, y);
	}
	
	@Override
	public Vegetal getNewVegetal() {
		return new Fougere(null);
	}
}
