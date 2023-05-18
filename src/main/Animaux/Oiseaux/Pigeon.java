package main.Animaux.Oiseaux;

import interfaces.Vole;
import main.Zone;
import main.Animaux.Animal;
import main.Execeptions.ReproduireException;
import main.Execeptions.VolerException;
import main.TypeZones.Desert;
import main.TypeZones.Plaine;

public class Pigeon extends Oiseau implements Vole {

	public Pigeon(Zone zone_actuel) {
		super(zone_actuel, 0.06, 6, 30, 1, new Plaine(), 40);
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
				animal.getZone_actuel().addAnimal((Animal) new Pigeon(animal.getZone_actuel()));
			}
		}
	}

	@Override
	public void seDeplacer(int x, int y) throws VolerException {
		if (x >= this.getZone_actuel().getEcosysteme().getNbZonesH() || x < 0
				|| y >= this.getZone_actuel().getEcosysteme().getNbZonesL() || y < 0)
			throw new VolerException("ERREUR: tentative de deplacement en dehors de la grille");
		else
		if (!(!(this.getZone_actuel().getTypeZone() instanceof Desert)
				&& this.getZone_actuel().getEcosysteme().getZone(x, y).getTypeZone() instanceof Desert)
				|| (this.getZone_actuel().getTypeZone() instanceof Desert)
						&& !(this.getZone_actuel().getEcosysteme().getZone(x, y).getTypeZone() instanceof Desert))
				this.getZone_actuel().getEcosysteme().deplacerAnimal(this, x, y);
	}

	@Override
	public Animal getNewAnimal() {
		return new Pigeon(null);
	}
	
}
