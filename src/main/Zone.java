package main;

//IMPORTS 
//============================================================================
import java.util.ArrayList;
import java.awt.Color;
import java.util.LinkedList;
import main.Animaux.Animal;
import main.Execeptions.*;
import main.Grille.Disque;
import main.TypeZones.TypeZone;

public class Zone {
	// ATTRIBUTES
	// =======================================================================
	private double eau;
	private double temperature;
	private ArrayList<Animal> animaux = new ArrayList<Animal>();
	private ArrayList<Vegetal> vegeteaux = new ArrayList<Vegetal>();
	private TypeZone typeZone;
	private Ecosysteme ecosysteme;

	// CONSTRUCTORS
	// =======================================================================

	public Zone(double eau, double temperature, Ecosysteme ecosysteme) {
		super();
		this.eau = eau;
		this.temperature = temperature;
		this.ecosysteme = ecosysteme;
	}

	// GETTERS
	// =======================================================================
	public double getEau() {
		return this.eau;
	}

	public double getTemperature() {
		return this.temperature;
	}

	public ArrayList<Animal> getAnimaux() {
		ArrayList<Animal> animaux_copie = new ArrayList<Animal>();
		this.animaux.forEach((n) -> animaux_copie.add(n));
		return animaux_copie;
	}

	public Animal getAnimal(int i) {
		return this.animaux.get(i);
	}

	public ArrayList<Vegetal> getVegeteaux() {
		ArrayList<Vegetal> vegeteaux_copie = new ArrayList<Vegetal>();
		this.vegeteaux.forEach((n) -> vegeteaux_copie.add(n));
		return vegeteaux_copie;
	}

	public Vegetal getVegetal(int i) {
		return this.vegeteaux.get(i);
	}

	public TypeZone getTypeZone() {
		return typeZone;
	}
	
	public Ecosysteme getEcosysteme() {
		return ecosysteme;
	}

	// SETTERS
	// =======================================================================
	private void setEau(double eau) {
		this.eau = eau;
	}

	private void setTemperature(double temperature) {
		this.temperature = temperature;
	}

	// TODO definir setTypeZone en private des que verifierTypeZone est implementer
	public void setTypeZone(TypeZone typeZone) {
		this.typeZone = typeZone;
	}

	// METHODS
	// =======================================================================
	public void changerEau(double eau) throws ChangerEauException {
		if (eau < 0 || eau < this.getEau())
			throw new ChangerEauException("ERREUR: changement du niveau d'eau");
		else
			this.setEau(eau);
	}

	public void changerTemperature(double temperature) throws ChangerTemperatureException {
		if (temperature < -273.15)
			throw new ChangerTemperatureException("ERREUR: changement de la temperature");
		else
			this.setTemperature(temperature);
	}

	public void addAnimal(Animal animal) {
		this.animaux.add(animal);
	}
	
	public void removeAnimal(Animal animal) {
		this.animaux.remove(animal);
	}

	public void addVegetal(Vegetal vegetal) {
		this.vegeteaux.add(vegetal);
	}
	
	public void removeVegetal(Vegetal vegetal) {
		this.vegeteaux.remove(vegetal);
	}

	public void verifierTypeZone() {
		this.ecosysteme.getTypeZones().forEach((typeZone) -> {
			if(this.eau > typeZone.getEauMin() && this.eau < typeZone.getEauMax()) 
				this.setTypeZone(typeZone);
		});
	}

	@Override
	public String toString() {
		return "[e:" + eau + "," + " t:" + temperature + ",a:" + animaux.size() + ",v:" + vegeteaux.size() + ", n:" + typeZone.getNomTypeZone() +"]";
	}

	// EOF
	// =======================================================================
}
