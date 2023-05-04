package interfaces;

import main.Animaux.Animal;
import main.Execeptions.ReproduireException;

public interface ReproductionAnimal {
	Animal seReproduire(Animal animal) throws ReproduireException;
}
