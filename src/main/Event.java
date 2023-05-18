package main;

import main.Animaux.Animal;
import main.Execeptions.ChangerEauException;
import main.Execeptions.ChangerTemperatureException;
import main.Vegetaux.Vegetal;

public class Event {
	private Ecosysteme ecosysteme;
	private int cycle;
	private int x;
	private int y;
	private Animal animal;
	private int nbAnimal;
	private Vegetal vegetal;
	private int nbVegetal;
	private double eau;
	private double temperature;

	public Event(Ecosysteme ecosysteme, int cycle, int x, int y, Animal animal, int nbAnimal, Vegetal vegetal,
			int nbVegetal, double eau, double temperature) {
		super();
		this.cycle = cycle;
		this.x = x;
		this.y = y;
		this.animal = animal;
		this.nbAnimal = nbAnimal;
		this.vegetal = vegetal;
		this.nbVegetal = nbVegetal;
		this.eau = eau;
		this.temperature = temperature;
		this.ecosysteme = ecosysteme;
	}

	public int getCycle() {
		return cycle;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Animal getAnimal() {
		return animal;
	}

	public int getNbAnimal() {
		return nbAnimal;
	}

	public Vegetal getVegetal() {
		return vegetal;
	}

	public int getNbVegetal() {
		return nbVegetal;
	}

	public double getTemperature() {
		return temperature;
	}

	public double getEau() {
		return eau;
	}

	public void setEcosysteme(Ecosysteme e) {
		this.ecosysteme = e;
	}

	public boolean checkEvent(int cycle) {
		Zone z = ecosysteme.getZone(x, y);
		if (this.cycle == cycle) {
			System.out.println("EVENT !");
			if (animal != null) {
				for (int i = 0; i <= nbAnimal; i++) {
					Animal new_animal = animal.getNewAnimal();
					new_animal.setZone_actuel(z);
					z.addAnimal(new_animal);
				}
			}
			if (vegetal != null)
				for (int i = 0; i <= nbVegetal; i++) {
					Vegetal new_vegetal = vegetal.getNewVegetal();
					new_vegetal.setZone_actuel(z);
					z.addVegetal(new_vegetal);
				}
			try {
				z.changerEau(eau);
			} catch (ChangerEauException e) {
			}
			try {
				z.changerTemperature(temperature);
			} catch (ChangerTemperatureException e) {
			}

			return true;
		}
		return false;
	}

}
