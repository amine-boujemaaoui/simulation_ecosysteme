package main.Animaux.Insectes;

import interfaces.Carnivore;
import interfaces.ReproductionAnimal;
import main.Zone;
import main.Animaux.Animal;
import main.Execeptions.MangerException;
import main.Execeptions.ReproduireException;

public class Abeille extends Insecte implements ReproductionAnimal, Carnivore {

	public Abeille(Zone zone_actuel) {
		super(zone_actuel, 3, 20);
	}

	@Override
	public void seReproduire() throws ReproduireException {
		if (this.getZone_actuel().getNbInsecte() > 0) {
			Animal animal = this.getZone_actuel().getInsecte(r.nextInt(this.getZone_actuel().getNbInsecte()));
			if (!(animal instanceof Abeille))
				throw new ReproduireException(this.getClass() + " x " + animal.getClass());
			else if (this.getZone_actuel() != animal.getZone_actuel())
				throw new ReproduireException("different zones");
			else if (this.getZone_actuel().getEau() < this.getEauRequise())
				throw new ReproduireException("sechress");
			else {
				this.setDejaReproduiCecycle(true);
				animal.setDejaReproduiCecycle(true);
				animal.getZone_actuel().addAnimal(new Abeille(animal.getZone_actuel()));
			}
		}
	}

	@Override
	public void manger() throws MangerException {
		// TODO Auto-generated method stub
	}
}
