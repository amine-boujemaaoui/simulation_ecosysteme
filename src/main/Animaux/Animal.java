package main.Animaux;

import java.util.Random;

import interfaces.Boir;
import interfaces.ReproductionAnimal;
import main.Zone;
import main.Execeptions.BoirException;
import main.Execeptions.ChangerEauException;
import main.Execeptions.MangerException;
import main.Execeptions.ReproduireException;
import main.Execeptions.SeDeplacerException;
import main.Execeptions.VolerException;
import main.TypeZones.TypeZone;

public abstract class Animal implements Boir, ReproductionAnimal {
	public Random r = new Random();
	private Zone zone_actuel;
	private double eauRequise;
	private boolean dejaReproduiCeCycle;
	private final int ageMax;
	private int age;
	private int nbCyclesSansEau;
	private int nbCyclesSansManger;
	private final int tauxDeReproduction;
	private final int ageMinReproduction;
	private TypeZone zoneFavorable;

	public Animal(Zone zone_actuel, double eauRequise, int ageMax, int tauxDeReproduction, int ageMinReproduction,
			TypeZone zoneFavorable) {
		super();
		this.zone_actuel = zone_actuel;
		this.eauRequise = eauRequise;
		this.dejaReproduiCeCycle = false;
		this.ageMax = ageMax;
		this.age = 0;
		this.nbCyclesSansEau = 0;
		this.nbCyclesSansManger = 0;
		this.tauxDeReproduction = tauxDeReproduction;
		this.ageMinReproduction = ageMinReproduction;
		this.zoneFavorable = zoneFavorable;
	}

	@Override
	public void boir() throws BoirException {
		if (this.getEauRequise() > this.zone_actuel.getEau()) {
			this.augmenterNbCyclesSansEau();
			throw new BoirException("ERREUR: pas assez d'eau");
		} else
			try {
				this.zone_actuel.changerEau(-this.getEauRequise());
				this.nbCyclesSansEau = 0;
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

	public int getAgeMax() {
		return ageMax;
	}

	public int getAge() {
		return age;
	}

	public void vieillir() {
		this.age++;
	}

	public int getNbCyclesSansEau() {
		return nbCyclesSansEau;
	}

	public void augmenterNbCyclesSansEau() {
		this.nbCyclesSansEau += 1;
	}

	public void reduireNbCyclesSansEau() {
		if (this.nbCyclesSansEau - 1 < 0)
			this.nbCyclesSansEau = 0;
		else
			this.nbCyclesSansEau -= 1;
	}

	public int getTauxDeReproduction() {
		return tauxDeReproduction;
	}

	public int getAgeMinReproduction() {
		return ageMinReproduction;
	}

	public TypeZone getZoneFavorable() {
		return zoneFavorable;
	}

	public void seDeplacer(int x, int y) throws SeDeplacerException, VolerException {
	}

	public void seReproduire() throws ReproduireException {
	}

	public abstract void manger() throws MangerException;

	public int getNbCyclesSansManger() {
		return nbCyclesSansManger;
	}

	public void setNbCyclesSansManger(int nbCyclesSansManger) {
		this.nbCyclesSansManger = nbCyclesSansManger;
	}

	public void augmenterNbCyclesSansManger() {
		this.nbCyclesSansManger += 1;
	}
}
