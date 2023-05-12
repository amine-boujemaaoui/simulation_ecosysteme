package main.Animaux.Insectes;

import interfaces.Deplacer;
import interfaces.ReproductionAnimal;
import main.Zone;
import main.Animaux.Animal;
import main.Execeptions.SeDeplacerException;

public abstract class Insecte extends Animal implements Deplacer, ReproductionAnimal {
	
	public Insecte(Zone zone_actuel, int ageMax, int tauxDeReproduction) {
		super(zone_actuel, 0.001, ageMax, tauxDeReproduction);
	}

	@Override
	public void seDeplacer(int x, int y) throws SeDeplacerException {
		if(x > this.getZone_actuel().getEcosysteme().getNbZonesH() || x < 0 || y > this.getZone_actuel().getEcosysteme().getNbZonesL() || y<0)
			throw new SeDeplacerException("ERREUR: tentative de deplacement en dehors de la grille");
		else
			this.getZone_actuel().getEcosysteme().deplacerAnimal(this, x, y);
	}
}
