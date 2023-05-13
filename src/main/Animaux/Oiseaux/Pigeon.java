package main.Animaux.Oiseaux;

import interfaces.Carnivore;
import main.Zone;
import main.Animaux.Animal;
import main.Execeptions.MangerException;
import main.Execeptions.ReproduireException;

public class Pigeon extends Oiseau implements Carnivore {

	public Pigeon(Zone zone_actuel) {
		super(zone_actuel, 6, 5);
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
			else {
				this.setDejaReproduiCecycle(true);
				animal.setDejaReproduiCecycle(true);
				animal.getZone_actuel().addAnimal(new Pigeon(animal.getZone_actuel()));
			}
		}
	}

	@Override
	public void manger() throws MangerException {
		for (int i = 0; i < r.nextInt(15); i++)
			this.getZone_actuel().removeInsecte(0);
	}

}
