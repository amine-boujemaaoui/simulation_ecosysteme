package main.Animaux.Insectes;

import interfaces.Marche;
import main.Zone;
import main.Animaux.Animal;
import main.Execeptions.ReproduireException;
import main.Execeptions.SeDeplacerException;
import main.TypeZones.Plaine;

public class Fourmi extends Insecte implements Marche {

	public Fourmi(Zone zone_actuel) {
		super(zone_actuel, 0.002, 2, 60, 0, new Plaine());
	}

	@Override
	public void seReproduire() throws ReproduireException {
		if (this.getZone_actuel().getNbInsecte() > 0) {
			Animal animal = this.getZone_actuel().getInsecte(r.nextInt(this.getZone_actuel().getNbInsecte()));
			if (!(animal instanceof Fourmi))
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
				animal.getZone_actuel().addAnimal((Animal) new Fourmi(animal.getZone_actuel()));
			}
		}
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
