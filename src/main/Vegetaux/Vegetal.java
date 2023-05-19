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
	private boolean dejaPropagerCeCycle;
	private final int ageMax;
	private int age;
	private int nbCycleTypeZoneNonFavorable;
	private final int tauxDePropagation;
	private final int ageMinPropagation;
	private TypeZone zoneFavorable;
	private int nbMinDansZoneFavorableForInit;
	private boolean dejaTraiterCeCycle = false;
	/*
	 * private double minEauRequise; private double maxEauRequise; private double
	 * minTemperatureRequise; private double maxTemperatureRequise;
	 */

	public Vegetal(Zone zone_actuel, double eauRequise, /*
														 * double minEauRequise, double maxEauRequise, double
														 * minTemperatureRequise, double maxTemperatureRequise,
														 */ int ageMax, int tauxDePropagation, int ageMinPropagation,
			TypeZone zoneFavorable, int nbMinDansZoneFavorableForInit) {
		super();
		this.zone_actuel = zone_actuel;
		this.eauRequise = eauRequise;
		this.dejaPropagerCeCycle = false;
		this.ageMax = ageMax;
		this.age = 0;
		this.nbCycleTypeZoneNonFavorable = 0;
		this.tauxDePropagation = tauxDePropagation;
		this.ageMinPropagation = ageMinPropagation;
		this.zoneFavorable = zoneFavorable;
		this.nbMinDansZoneFavorableForInit = nbMinDansZoneFavorableForInit;
		/*
		 * this.setMinEauRequise(minEauRequise); this.setMaxEauRequise(maxEauRequise);
		 * this.setMinTemperatureRequise(minTemperatureRequise);
		 * this.setMaxTemperatureRequise(maxTemperatureRequise);
		 */
	}

	@Override
	public void boir() throws BoirException {
		if (this.getEauRequise() > this.zone_actuel.getEau()) {
			this.augmenterNbCycleTypeZoneNonFavorable();
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

	public boolean isDejaPropagerCecycle() {
		return dejaPropagerCeCycle;
	}

	public void setDejaPropagerCecycle(boolean dejaPropagerCeCycle) {
		this.dejaPropagerCeCycle = dejaPropagerCeCycle;
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

	public int getNbCycleTypeZoneNonFavorable() {
		return nbCycleTypeZoneNonFavorable;
	}

	public void setNbCycleTypeZoneNonFavorable(int nbCycleTypeZoneNonFavorable) {
		this.nbCycleTypeZoneNonFavorable = nbCycleTypeZoneNonFavorable;
	}

	public void augmenterNbCycleTypeZoneNonFavorable() {
		if (!((this.nbCycleTypeZoneNonFavorable + 1) < 0))
			this.nbCycleTypeZoneNonFavorable += 1;
	}

	public void reduireNbCycleTypeZoneNonFavorable() {
		this.nbCycleTypeZoneNonFavorable -= 1;
	}

	public int getTauxDePropagation() {
		return tauxDePropagation;
	}

	public int getAgeMinPropagation() {
		return ageMinPropagation;
	}

	public TypeZone getZoneFavorable() {
		return zoneFavorable;
	}

	public int getNbMinDansZoneFavorableForInit() {
		return nbMinDansZoneFavorableForInit;
	}

	public abstract Vegetal getNewVegetal();

	public boolean isDejaTraiterCeCycle() {
		return dejaTraiterCeCycle;
	}

	public void setDejaTraiterCeCycle(boolean dejaTraiterCeCycle) {
		this.dejaTraiterCeCycle = dejaTraiterCeCycle;
	}

	/*
	 * public double getMinEauRequise() { return minEauRequise; }
	 * 
	 * public void setMinEauRequise(double minEauRequise) { this.minEauRequise =
	 * minEauRequise; }
	 * 
	 * public double getMaxEauRequise() { return maxEauRequise; }
	 * 
	 * public void setMaxEauRequise(double maxEauRequise) { this.maxEauRequise =
	 * maxEauRequise; }
	 * 
	 * public double getMinTemperatureRequise() { return minTemperatureRequise; }
	 * 
	 * public void setMinTemperatureRequise(double minTemperatureRequise) {
	 * this.minTemperatureRequise = minTemperatureRequise; }
	 * 
	 * public double getMaxTemperatureRequise() { return maxTemperatureRequise; }
	 * 
	 * public void setMaxTemperatureRequise(double maxTemperatureRequise) {
	 * this.maxTemperatureRequise = maxTemperatureRequise; }
	 */
}
