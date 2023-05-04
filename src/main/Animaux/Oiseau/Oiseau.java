package main.Animaux.Oiseau;

import interfaces.Voler;
import main.Animaux.Animal;
import main.Execeptions.VolerException;

public abstract class Oiseau extends Animal implements Voler {
	
	@Override
	public void seDeplacer(int x, int y) throws VolerException {

	}
}
