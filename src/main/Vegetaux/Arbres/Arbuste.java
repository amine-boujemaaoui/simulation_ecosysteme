package main.Vegetaux.Arbres;

import main.Zone;
import main.Execeptions.PropagerException;
import main.TypeZones.Plaine;
import main.Vegetaux.Vegetal;

public class Arbuste extends Arbre {

	public Arbuste(Zone zone_actuel) {
		super(zone_actuel, 0.002, 20, 10, 7, new Plaine(), 6);
	}

	@Override
	public void sePropager(int x, int y) throws PropagerException {
		if (x >= this.getZone_actuel().getEcosysteme().getNbZonesH() || x < 0
				|| y >= this.getZone_actuel().getEcosysteme().getNbZonesL() || y < 0)
			throw new PropagerException("ERREUR: tentative de propagation en dehors de la grille");
		else {
			this.getZone_actuel().getEcosysteme().propagerVegetal(new Arbuste(this.getZone_actuel()), x, y);
		}
	}
	
	@Override
	public Vegetal getNewVegetal() {
		return new Arbuste(null);
	}
}

