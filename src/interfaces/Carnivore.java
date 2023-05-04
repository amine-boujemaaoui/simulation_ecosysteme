package interfaces;

import main.Animaux.Animal;
import main.Execeptions.MangerException;

public interface Carnivore {
	void manger(Animal animal) throws MangerException;
}