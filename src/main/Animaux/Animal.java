package main.Animaux;

import interfaces.Boir;
import interfaces.ReproductionAnimal;
import main.Zone;
import main.Execeptions.BoirException;
import main.Execeptions.ChangerEauException;

public abstract class Animal implements Boir, ReproductionAnimal{
	private Zone zone_actuel;
	private double eauRequise;
	private boolean dejaReproduiCeCycle;
	private int nbMortParCycle;

	public Animal(Zone zone_actuel, double eauRequise, int nbMortParCycle) {
		super();
		this.zone_actuel = zone_actuel;
		this.eauRequise = eauRequise;
		this.dejaReproduiCeCycle = false;
		this.nbMortParCycle = nbMortParCycle;
	}

	@Override
	public void boir() throws BoirException {
		if (this.getEauRequise() > this.zone_actuel.getEau())
			throw new BoirException("ERREUR: pas assez d'eau");
		else
			try {
				this.zone_actuel.changerEau(-this.getEauRequise());
			} catch (ChangerEauException e) {
				e.printStackTrace();
			}
	}

	public Zone getZone_actuel() {
		return zone_actuel;
	}

	public void setZone_actuel(Zone zone_actuel) {
		this.zone_actuel = zone_actuel;
	}

	public double getEauRequise() {
		return eauRequise;
	}

	public void setEauRequise(double eauRequise) {
		this.eauRequise = eauRequise;
	}

	public boolean isDejaReproduiCecycle() {
		return dejaReproduiCeCycle;
	}

	public void setDejaReproduiCecycle(boolean dejaReproduiCeCycle) {
		this.dejaReproduiCeCycle = dejaReproduiCeCycle;
	}

	public int getNbMortParCycle() {
		return nbMortParCycle;
	}

	public void setNbMortParCycle(int nbMortParCycle) {
		this.nbMortParCycle = nbMortParCycle;
	}

}
