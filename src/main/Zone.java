package main;

import main.Execeptions.*;
import java.util.ArrayList;
import main.Animaux.Animal;
import main.Animaux.Oiseaux.*;
import main.Animaux.Insectes.*;
import main.Animaux.Mammiferes.*;
import main.TypeZones.TypeZone;
import interfaces.Carnivore;
import interfaces.Herbivore;

public class Zone {
	private double eau;
	private double temperature;
	private ArrayList<Animal> animaux = new ArrayList<Animal>();
	private ArrayList<Vegetal> vegeteaux = new ArrayList<Vegetal>();
	private ArrayList<Oiseau> oiseaux = new ArrayList<Oiseau>();
	private ArrayList<Insecte> insectes = new ArrayList<Insecte>();
	private ArrayList<Mammifere> mammiferes = new ArrayList<Mammifere>();
	private ArrayList<Carnivore> carnivores = new ArrayList<Carnivore>();
	private ArrayList<Herbivore> herbivores = new ArrayList<Herbivore>();
	private TypeZone typeZone;
	private Ecosysteme ecosysteme;
	private final int x, y;

	public Zone(int x, int y, double eau, double temperature, Ecosysteme ecosysteme) {
		super();
		this.x = x;
		this.y = y;
		this.eau = eau;
		this.temperature = temperature;
		this.ecosysteme = ecosysteme;
	}

	public double getEau() {
		return this.eau;
	}

	public double getTemperature() {
		return this.temperature;
	}

	public ArrayList<Animal> getAnimaux() {
		ArrayList<Animal> animaux_copie = new ArrayList<Animal>();
		animaux_copie.addAll(this.animaux);
		return animaux_copie;
	}

	public Animal getAnimal(int i) {
		return this.animaux.get(i);
	}

	public ArrayList<Oiseau> getOiseaux() {
		ArrayList<Oiseau> oiseaux_copie = new ArrayList<Oiseau>();
		oiseaux_copie.addAll(this.oiseaux);
		return oiseaux_copie;
	}

	public Animal getOiseau(int i) {
		return this.oiseaux.get(i);
	}

	public ArrayList<Insecte> getInsectes() {
		ArrayList<Insecte> insectes_copie = new ArrayList<Insecte>();
		insectes_copie.addAll(this.insectes);
		return insectes_copie;
	}

	public Animal getInsecte(int i) {
		return this.insectes.get(i);
	}

	public ArrayList<Mammifere> getMammiferes() {
		ArrayList<Mammifere> mammiferes_copie = new ArrayList<Mammifere>();
		mammiferes_copie.addAll(this.mammiferes);
		return mammiferes_copie;
	}

	public Animal getMammifere(int i) {
		return this.mammiferes.get(i);
	}

	public ArrayList<Carnivore> getCarnivores() {
		ArrayList<Carnivore> carnivores_copie = new ArrayList<Carnivore>();
		carnivores_copie.addAll(this.carnivores);
		return carnivores_copie;
	}

	public Carnivore getCarnivore(int i) {
		return this.carnivores.get(i);
	}

	public ArrayList<Herbivore> getHerbivores() {
		ArrayList<Herbivore> herbivores_copie = new ArrayList<Herbivore>();
		herbivores_copie.addAll(this.herbivores);
		return herbivores_copie;
	}

	public Herbivore getHerbivore(int i) {
		return this.herbivores.get(i);
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

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getNbOiseau() {
		return this.oiseaux.size();
	}

	public int getNbInsecte() {
		return this.insectes.size();
	}

	public int getNbMammifere() {
		return this.mammiferes.size();
	}

	private void setEau(double eau) {
		this.eau = Math.round(eau * 100.0) / 100.0;
	}

	private void setTemperature(double temperature) {
		this.temperature = Math.round(temperature * 100.0) / 100.0;
	}

	public void setTypeZone(TypeZone typeZone) {
		this.typeZone = typeZone;
	}

	public void changerEau(double eau) throws ChangerEauException {
		if (this.getEau() + eau < 0)
			throw new ChangerEauException(
					"zone[" + this.getX() + "][" + this.getY() + "] zone.eau: " + this.getEau() + ", eau: " + eau);
		else
			this.setEau(this.getEau() + eau);
	}

	public void changerTemperature(double temperature) throws ChangerTemperatureException {
		if (this.getTemperature() + temperature < -273.15)
			throw new ChangerTemperatureException("zone[" + this.getX() + "][" + this.getY() + "] zone.temperature: "
					+ this.getTemperature() + ", temperature: " + temperature);
		else
			this.setTemperature(this.getTemperature() + temperature);
	}

	public void addAnimal(Animal animal) {
		this.animaux.add(animal);
		if (animal instanceof Oiseau)
			this.oiseaux.add((Oiseau) animal);
		else if (animal instanceof Insecte)
			this.insectes.add((Insecte) animal);
		else
			this.mammiferes.add((Mammifere) animal);
		if (animal instanceof Carnivore)
			addCarnivore((Carnivore) animal);
		else
			addHerbivore((Herbivore) animal);
	}

	public void addCarnivore(Carnivore carnivore) {
		this.carnivores.add(carnivore);
	}

	public void addHerbivore(Herbivore herbivore) {
		this.herbivores.add(herbivore);
	}

	public void removeAnimal(Animal animal) {
		this.animaux.remove(animal);
		if (animal instanceof Oiseau)
			this.oiseaux.remove((Oiseau) animal);
		else if (animal instanceof Insecte)
			this.insectes.remove((Insecte) animal);
		else
			this.mammiferes.remove((Mammifere) animal);
		if (animal instanceof Carnivore)
			this.carnivores.remove((Carnivore) animal);
		else
			this.herbivores.remove((Herbivore) animal);
	}

	public void removeAnimal(int i) throws RemoveEntityException {
		if (i < 0 || i >= this.animaux.size())
			throw new RemoveEntityException("animaux size: " + this.animaux.size() + "pos: " + i);
		else {
			Animal animal = this.animaux.get(i);
			if (animal instanceof Oiseau)
				this.oiseaux.remove((Oiseau) animal);
			else if (animal instanceof Insecte)
				this.insectes.remove((Insecte) animal);
			else if (animal instanceof Mammifere)
				this.mammiferes.remove((Mammifere) animal);
			this.animaux.remove(i);
		}
	}

	public void removeOiseau(Oiseau oiseau) {
		this.oiseaux.remove(oiseau);
		this.animaux.remove((Animal) oiseau);
	}

	public void removeOiseau(int i) {
		Animal animal = this.oiseaux.get(i);
		this.oiseaux.remove(i);
		this.animaux.remove(animal);
	}

	public void removeInsecte(Insecte insecte) {
		this.insectes.remove(insecte);
		this.animaux.remove((Animal) insecte);
	}

	public void removeInsecte(int i) {
		Animal animal = this.insectes.get(i);
		this.insectes.remove(i);
		this.animaux.remove(animal);
	}

	public void removeMammifere(Mammifere mammifere) {
		this.mammiferes.remove(mammifere);
		this.animaux.remove((Animal) mammifere);
	}

	public void removeMammifere(int i) {
		Animal animal = this.mammiferes.get(i);
		this.mammiferes.remove(i);
		this.animaux.remove(animal);
	}
	
	public void removeCarnivore(Carnivore carnivore) {
		this.carnivores.remove(carnivore);
		this.animaux.remove((Animal) carnivore);
	}

	public void removeCarnivore(int i) {
		Animal animal = (Animal) this.carnivores.get(i);
		this.carnivores.remove(i);
		this.animaux.remove(animal);
	}

	public void removeHerbivore(int i) {
		Animal animal = (Animal) this.herbivores.get(i);
		this.herbivores.remove(i);
		this.animaux.remove(animal);
	}
	
	public void removeHerbivore(Herbivore herbivore) {
		this.herbivores.remove(herbivore);
		this.animaux.remove((Animal) herbivore);
	}

	public void addVegetal(Vegetal vegetal) {
		this.vegeteaux.add(vegetal);
	}

	public void removeVegetal(Vegetal vegetal) {
		this.vegeteaux.remove(vegetal);
	}

	public void removeVegetal(int i) throws RemoveEntityException {
		if (i < 0 || i >= this.vegeteaux.size())
			throw new RemoveEntityException("animaux size: " + this.vegeteaux.size() + "pos: " + i);
		else
			this.vegeteaux.remove(i);
	}

	public void verifierTypeZone() {
		this.ecosysteme.getTypeZones().forEach((typeZone) -> {
			if (this.eau > typeZone.getEauMin() && this.eau < typeZone.getEauMax())
				this.setTypeZone(typeZone);
		});
	}

	@Override
	public String toString() {
		return "[e:" + eau + "," + " t:" + temperature + ",a:" + animaux.size() + ",v:" + vegeteaux.size() + ", n:"
				+ typeZone.getNomTypeZone() + "]";
	}
}
