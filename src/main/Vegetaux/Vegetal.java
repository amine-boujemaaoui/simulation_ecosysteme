package main.Vegetaux;

import java.util.Random;

import interfaces.Boir;
import interfaces.ReproductionVegetal;
import main.Zone;
import main.Execeptions.BoirException;
import main.Execeptions.ChangerEauException;
import main.TypeZones.TypeZone;

public abstract class Vegetal implements Boir, ReproductionVegetal {
	public Random r = new Random();
	private Zone zone_actuel;
	private double eauRequise;
	private boolean dejaReproduiCeCycle;
	private final int ageMax;
	private int age;
	private int nbCyclesSansEau;
	private final int tauxDeReproduction;
	private final int ageMinReproduction;
	private TypeZone zoneFavorable;
	private double minEauRequise;
	private double maxEauRequise;
	private double minTemperatureRequise;
	private double maxTemperatureRequise;

	public Vegetal(Zone zone_actuel, double eauRequise, double minEauRequise, double maxEauRequise, double minTemperatureRequise, double maxTemperatureRequise, int ageMax, int tauxDeReproduction, int ageMinReproduction,
			TypeZone zoneFavorable) {
		super();
		this.zone_actuel = zone_actuel;
		this.eauRequise = eauRequise;
		this.dejaReproduiCeCycle = false;
		this.ageMax = ageMax;
		this.age = 0;
		this.nbCyclesSansEau = 0;
		this.tauxDeReproduction = tauxDeReproduction;
		this.ageMinReproduction = ageMinReproduction;
		this.zoneFavorable = zoneFavorable;
		this.setMinEauRequise(minEauRequise);
		this.setMaxEauRequise(maxEauRequise);
		this.setMinTemperatureRequise(minTemperatureRequise);
		this.setMaxTemperatureRequise(maxTemperatureRequise);
	}

	@Override
	public void boir() throws BoirException {
		if (this.getEauRequise() > this.zone_actuel.getEau()) {
			this.augmenterNbCyclesSansEau();
			throw new BoirException("ERREUR: pas assez d'eau");
		} else
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
		if (!((this.nbCyclesSansEau + 1) < 0))
			this.nbCyclesSansEau += 1;
	}

	public void reduireNbCyclesSansEau() {
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

	public double getMinEauRequise() {
		return minEauRequise;
	}

	public void setMinEauRequise(double minEauRequise) {
		this.minEauRequise = minEauRequise;
	}

	public double getMaxEauRequise() {
		return maxEauRequise;
	}

	public void setMaxEauRequise(double maxEauRequise) {
		this.maxEauRequise = maxEauRequise;
	}

	public double getMinTemperatureRequise() {
		return minTemperatureRequise;
	}

	public void setMinTemperatureRequise(double minTemperatureRequise) {
		this.minTemperatureRequise = minTemperatureRequise;
	}

	public double getMaxTemperatureRequise() {
		return maxTemperatureRequise;
	}

	public void setMaxTemperatureRequise(double maxTemperatureRequise) {
		this.maxTemperatureRequise = maxTemperatureRequise;
	}
}
