package Interfaces;

import main.Animal;
import main.Execeptions.MangerException;

public interface Carnivore {
	void manger(Animal animal) throws MangerException;
}