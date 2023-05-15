package main.Vegetaux.Arbres;

import main.Zone;
import main.Execeptions.PropagerException;
import main.TypeZones.Foret;

public class Sapin extends Arbre {

	public Sapin(Zone zone_actuel) {
		super(zone_actuel, 0.004, 28, 7, 9, new Foret());
	}

	@Override
	public void sePropager(int x, int y) throws PropagerException {
		if (x >= this.getZone_actuel().getEcosysteme().getNbZonesH() || x < 0
				|| y >= this.getZone_actuel().getEcosysteme().getNbZonesL() || y < 0)
			throw new PropagerException("ERREUR: tentative de propagation en dehors de la grille");
		else
			this.getZone_actuel().getEcosysteme().propagerVegetal(new Sapin(this.getZone_actuel()), x, y);
	}
}
