package interfaces;

import main.Animaux.Animal;
import main.Execeptions.ReproduireException;

public interface ReproductionAnimal {
	void seReproduire(Animal animal) throws ReproduireException;
}
