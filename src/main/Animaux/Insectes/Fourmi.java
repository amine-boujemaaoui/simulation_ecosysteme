package main.Animaux.Insectes;

import interfaces.Carnivore;
import interfaces.Deplacer;
import interfaces.ReproductionAnimal;
import main.Zone;
import main.Animaux.Animal;
import main.Animaux.Oiseaux.Pigeon;
import main.Execeptions.MangerException;
import main.Execeptions.ReproduireException;
import main.Execeptions.SeDeplacerException;

public class Fourmi extends Animal implements Deplacer, ReproductionAnimal, Carnivore {

	public Fourmi(Zone zone_actuel) {
		super(zone_actuel, 0.2, 40);
	}

	@Override
	public void seDeplacer(int x, int y) throws SeDeplacerException {
		if (x > this.getZone_actuel().getEcosysteme().getNbZonesH() || x < 0
				|| y > this.getZone_actuel().getEcosysteme().getNbZonesL() || y < 0)
			throw new SeDeplacerException("ERREUR: tentative de deplacement en dehors de la grille");
		else
			this.getZone_actuel().getEcosysteme().deplacerAnimal(this, x, y);
	}

	@Override
	public void seReproduire(Animal animal) throws ReproduireException {
		if (!(animal instanceof Fourmi))
			throw new ReproduireException(this.getClass() + " x " + animal.getClass());
		else if (this.getZone_actuel() != animal.getZone_actuel())
			throw new ReproduireException("different zones");
		else if (this.getZone_actuel().getEau() < this.getEauRequise())
			throw new ReproduireException("sechress");
		else {
			this.setDejaReproduiCecycle(true);
			animal.setDejaReproduiCecycle(true);
			animal.getZone_actuel().addAnimal(new Pigeon(animal.getZone_actuel()));
		}
	}

	@Override
	public void manger(Animal animal) throws MangerException {
		// TODO Auto-generated method stub
		
	}
}
