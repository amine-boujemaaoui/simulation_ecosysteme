package main.Animaux.Mammiferes.Herbivore;

import interfaces.Marche;
import main.Zone;
import main.Animaux.Animal;
import main.Execeptions.MangerException;
import main.Execeptions.ReproduireException;
import main.Execeptions.SeDeplacerException;
import main.TypeZones.Desert;
import main.TypeZones.Plaine;

public class Vache extends Herbivore implements Marche {

	public Vache(Zone zone_actuel) {
		super(zone_actuel, 0.08, 17, 35, 3, new Plaine(), 10);
	}

	@Override
	public void seReproduire() throws ReproduireException {
		if (this.getZone_actuel().getNbMammifere() > 0) {
			Animal animal = this.getZone_actuel().getMammifere(r.nextInt(this.getZone_actuel().getNbMammifere()));
			if (!(animal instanceof Vache))
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
				animal.getZone_actuel().addAnimal((Animal) new Vache(animal.getZone_actuel()));
			}
		}
	}

	@Override
	public void manger() throws MangerException {
		Zone z = this.getZone_actuel();
		if (z.getNbVivace() > 0) {
			for (int i = 0; i < r.nextInt(6); i++) {
				if (z.getNbVivace() == 0)
					break;
				else
					z.removeVivace(r.nextInt(z.getNbVivace()));
			}
			this.setNbCyclesSansManger(0);
		} else
			this.augmenterNbCyclesSansManger();
	}

	@Override
	public void seDeplacer(int x, int y) throws SeDeplacerException {
		if (x >= this.getZone_actuel().getEcosysteme().getNbZonesH() || x < 0
				|| y >= this.getZone_actuel().getEcosysteme().getNbZonesL() || y < 0)
			throw new SeDeplacerException("ERREUR: tentative de deplacement en dehors de la grille");
		else
			if (!(!(this.getZone_actuel().getTypeZone() instanceof Desert) && this.getZone_actuel().getEcosysteme().getZone(x, y).getTypeZone() instanceof Desert))
				this.getZone_actuel().getEcosysteme().deplacerAnimal(this, x, y);
	}
	
	@Override
	public Animal getNewAnimal() {
		return new Vache(null);
	}

}
