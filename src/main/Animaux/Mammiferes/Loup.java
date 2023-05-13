package main.Animaux.Mammiferes;

import interfaces.Carnivore;
import interfaces.Marche;
import main.Zone;
import main.Animaux.Animal;
import main.Execeptions.MangerException;
import main.Execeptions.ReproduireException;
import main.Execeptions.SeDeplacerException;
import main.TypeZones.Foret;

public class Loup extends Mammifere implements Carnivore, Marche {

	public Loup(Zone zone_actuel) {
		super(zone_actuel, 0.3, 10, 15, 2, new Foret());
	}

	@Override
	public void seReproduire() throws ReproduireException {
		if (this.getZone_actuel().getNbMammifere() > 0) {
			Animal animal = this.getZone_actuel().getMammifere(r.nextInt(this.getZone_actuel().getNbMammifere()));
			if (!(animal instanceof Loup))
				throw new ReproduireException(this.getClass() + " x " + animal.getClass());
			else if (this.getZone_actuel() != animal.getZone_actuel())
				throw new ReproduireException("different zones");
			else if (this.getZone_actuel().getEau() < this.getEauRequise())
				throw new ReproduireException("sechress");
			else if (!(animal.getAge() >= animal.getAgeMinReproduction()))
				throw new ReproduireException("tentative de reproduction avec un nouveau-né");
			else {
				this.setDejaReproduiCecycle(true);
				animal.setDejaReproduiCecycle(true);
				Carnivore loup  = new Loup(animal.getZone_actuel());
				animal.getZone_actuel().addAnimal((Animal) loup);
				animal.getZone_actuel().addCarnivore(loup);
			}
		}
	}
	
	@Override
	public void manger() throws MangerException {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void seDeplacer(int x, int y) throws SeDeplacerException {
		if (x >= this.getZone_actuel().getEcosysteme().getNbZonesH() || x < 0
				|| y >= this.getZone_actuel().getEcosysteme().getNbZonesL() || y < 0)
			throw new SeDeplacerException("ERREUR: tentative de deplacement en dehors de la grille");
		else
			this.getZone_actuel().getEcosysteme().deplacerAnimal(this, x, y);
	}
	
}
