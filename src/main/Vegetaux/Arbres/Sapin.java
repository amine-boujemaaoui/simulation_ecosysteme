package main.Vegetaux.Arbres;

import main.Zone;
import main.Execeptions.PropagerException;
import main.TypeZones.Foret;

public class Sapin extends Arbre {

	public Sapin(Zone zone_actuel) {
		super(zone_actuel, 0.04, 50, 1, 1, new Foret());
	}

	@Override
	public void sePropager() throws PropagerException {
	}
}
