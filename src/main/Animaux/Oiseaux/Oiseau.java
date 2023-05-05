package main.Animaux.Oiseaux;

import interfaces.ReproductionAnimal;
import interfaces.Voler;
import main.Zone;
import main.Animaux.Animal;
import main.Execeptions.VolerException;

public abstract class Oiseau extends Animal implements Voler, ReproductionAnimal {
	
	public Oiseau(Zone zone_actuel, int nbMortParCycle) {
		super(zone_actuel, 0.2, nbMortParCycle);
	}

	@Override
	public void seDeplacer(int x, int y) throws VolerException {
		if(x > this.getZone_actuel().getEcosysteme().getNbZonesH() || x < 0 || y > this.getZone_actuel().getEcosysteme().getNbZonesL() || y<0)
			throw new VolerException("ERREUR: tentative de deplacement en dehors de la grille");
		else
			this.getZone_actuel().getEcosysteme().deplacerAnimal(this, x, y);
	}
}
