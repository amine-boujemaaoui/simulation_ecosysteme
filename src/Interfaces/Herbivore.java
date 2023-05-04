package Interfaces;

import main.Vivace;
import main.Execeptions.MangerException;

public interface Herbivore {
	void manger(Vivace vivace) throws MangerException;
}
