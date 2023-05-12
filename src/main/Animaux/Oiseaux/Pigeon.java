package main.Animaux.Oiseaux;

import interfaces.Herbivore;
import main.Vivace;
import main.Zone;
import main.Animaux.Animal;
import main.Execeptions.MangerException;
import main.Execeptions.ReproduireException;

public class Pigeon extends Oiseau implements Herbivore {

	public Pigeon(Zone zone_actuel) {
		super(zone_actuel, 6, 5);
	}

	@Override
	public void seReproduire(Animal animal) throws ReproduireException {
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

	@Override
	public void manger(Vivace vivace) throws MangerException {
		// TODO Auto-generated method stub
		
	}

}
