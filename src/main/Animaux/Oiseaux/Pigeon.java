package main.Animaux.Oiseaux;

import interfaces.Herbivore;
import interfaces.Vole;
import main.Zone;
import main.Animaux.Animal;
import main.Execeptions.MangerException;
import main.Execeptions.ReproduireException;
import main.Execeptions.VolerException;
import main.TypeZones.Plaine;

public class Pigeon extends Oiseau implements Herbivore, Vole {

	public Pigeon(Zone zone_actuel) {
		super(zone_actuel, 0.06, 5, 15, 2, new Plaine());
	}

	@Override
	public void seReproduire() throws ReproduireException {
		if (this.getZone_actuel().getNbOiseau() > 0) {
			Animal animal = this.getZone_actuel().getOiseau(r.nextInt(this.getZone_actuel().getNbOiseau()));
			if (!(animal instanceof Pigeon))
				throw new ReproduireException(this.getClass() + " x " + animal.getClass());
			else if (this.getZone_actuel() != animal.getZone_actuel())
				throw new ReproduireException("different zones");
			else if (this.getZone_actuel().getEau() < this.getEauRequise())
				throw new ReproduireException("sechress");
			else if (!(animal.getAge() >= animal.getAgeMinReproduction()))
				throw new ReproduireException("tentative de reproduction avec un nouveau-né");
			else if (animal.getZoneFavorable().getClass() != this.getZone_actuel().getTypeZone().getClass())
				throw new ReproduireException("environement non favorable");
			else {
				this.setDejaReproduiCecycle(true);
				animal.setDejaReproduiCecycle(true);
				Herbivore pigeon  = new Pigeon(animal.getZone_actuel());
				animal.getZone_actuel().addAnimal((Animal) pigeon);
				animal.getZone_actuel().addHerbivore(pigeon);
			}
		}
	}

	@Override
	public void manger() throws MangerException {
		for (int i = 0; i < r.nextInt(15); i++)
			this.getZone_actuel().removeInsecte(0);
	}
	

	@Override
	public void seDeplacer(int x, int y) throws VolerException {
		if (x >= this.getZone_actuel().getEcosysteme().getNbZonesH() || x < 0
				|| y >= this.getZone_actuel().getEcosysteme().getNbZonesL() || y < 0)
			throw new VolerException("ERREUR: tentative de deplacement en dehors de la grille");
		else
			this.getZone_actuel().getEcosysteme().deplacerAnimal(this, x, y);
	}

}
