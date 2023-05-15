package main.Vegetaux.Vivaces;

import main.Zone;
import main.Execeptions.PropagerException;
import main.TypeZones.Plaine;

public class Absinthe extends Vivace {

	public Absinthe(Zone zone_actuel) {
		super(zone_actuel, 0.0098, 6, 20, 2, new Plaine());
	}

	@Override
	public void sePropager(int x, int y) throws PropagerException {
		if (x >= this.getZone_actuel().getEcosysteme().getNbZonesH() || x < 0
				|| y >= this.getZone_actuel().getEcosysteme().getNbZonesL() || y < 0)
			throw new PropagerException("ERREUR: tentative de propagation en dehors de la grille");
		else
			this.getZone_actuel().getEcosysteme().propagerVegetal(new Absinthe(this.getZone_actuel()), x, y);
	}
}
