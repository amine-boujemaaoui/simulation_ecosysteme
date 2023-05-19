package main;

import main.Execeptions.*;
import java.util.ArrayList;
import main.Vegetaux.Vegetal;
import main.Vegetaux.Arbres.*;
import main.Vegetaux.Vivaces.*;
import main.Animaux.Animal;
import main.Animaux.Oiseaux.*;
import main.Animaux.Insectes.*;
import main.Animaux.Mammiferes.*;
import main.TypeZones.TypeZone;
import main.TypeZones.Desert;
import main.TypeZones.Riviere;

public class Zone {
	private double eau;
	private double temperature;
	private ArrayList<Animal> animaux = new ArrayList<Animal>();
	private ArrayList<Vegetal> vegetaux = new ArrayList<Vegetal>();
	private ArrayList<Oiseau> oiseaux = new ArrayList<Oiseau>();
	private ArrayList<Insecte> insectes = new ArrayList<Insecte>();
	private ArrayList<Mammifere> mammiferes = new ArrayList<Mammifere>();
	private ArrayList<Arbre> arbres = new ArrayList<Arbre>();
	private ArrayList<Vivace> vivaces = new ArrayList<Vivace>();
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

	public ArrayList<Vegetal> getVegetaux() {
		ArrayList<Vegetal> vegetaux_copie = new ArrayList<Vegetal>();
		vegetaux_copie.addAll(this.vegetaux);
		return vegetaux_copie;
	}

	public Vegetal getVegetal(int i) {
		return this.vegetaux.get(i);
	}

	public ArrayList<Arbre> getArbres() {
		ArrayList<Arbre> arbres_copie = new ArrayList<Arbre>();
		arbres_copie.addAll(this.arbres);
		return arbres_copie;
	}

	public Arbre getArbre(int i) {
		return this.arbres.get(i);
	}

	public ArrayList<Vivace> getVivaces() {
		ArrayList<Vivace> vivaces_copie = new ArrayList<Vivace>();
		vivaces_copie.addAll(this.vivaces);
		return vivaces_copie;
	}

	public Vivace getVivaces(int i) {
		return this.vivaces.get(i);
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

	public int getNbAnimaux() {
		return this.animaux.size();
	}

	public int getNbVegetaux() {
		return this.vegetaux.size();
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

	public int getNbArbre() {
		return this.arbres.size();
	}

	public int getNbVivace() {
		return this.vivaces.size();
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
		if (this.animaux.size() < this.ecosysteme.getNbMaxEntiteParZone()) {
			this.animaux.add(animal);
			if (animal instanceof Oiseau)
				this.oiseaux.add((Oiseau) animal);
			else if (animal instanceof Insecte)
				this.insectes.add((Insecte) animal);
			else
				this.mammiferes.add((Mammifere) animal);
		}
	}

	public void removeAnimal(Animal animal) {
		this.animaux.remove(animal);
		if (animal instanceof Oiseau)
			this.oiseaux.remove((Oiseau) animal);
		else if (animal instanceof Insecte)
			this.insectes.remove((Insecte) animal);
		else
			this.mammiferes.remove((Mammifere) animal);
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

	public void addVegetal(Vegetal vegetal) {
		if (this.vegetaux.size() < this.ecosysteme.getNbMaxEntiteParZone()) {
			this.vegetaux.add(vegetal);
			if (vegetal instanceof Arbre)
				this.arbres.add((Arbre) vegetal);
			else
				this.vivaces.add((Vivace) vegetal);
		}
	}

	public void removeVegetal(Vegetal vegetal) {
		this.vegetaux.remove(vegetal);
		if (vegetal instanceof Arbre)
			this.arbres.remove((Arbre) vegetal);
		else
			this.vivaces.remove((Vivace) vegetal);

	}

	public void removeVegetal(int i) throws RemoveEntityException {
		if (i < 0 || i >= this.vegetaux.size())
			throw new RemoveEntityException("animaux size: " + this.vegetaux.size() + "pos: " + i);
		else {
			Vegetal vegetal = this.vegetaux.get(i);
			if (vegetal instanceof Arbre)
				this.arbres.remove((Arbre) vegetal);
			else
				this.vivaces.remove((Vivace) vegetal);
			this.vegetaux.remove(i);
		}
	}

	public void removeArbre(Arbre arbre) {
		this.arbres.remove(arbre);
		this.vegetaux.remove(arbre);
	}

	public void removeArbre(int i) {
		Arbre arbre = this.arbres.get(i);
		this.arbres.remove(i);
		this.vegetaux.remove(arbre);
	}

	public void removeVivace(Vivace vivace) {
		this.vivaces.remove(vivace);
		this.vegetaux.remove(vivace);
	}

	public void removeVivace(int i) {
		Vivace vivace = this.vivaces.get(i);
		this.vivaces.remove(i);
		this.vegetaux.remove(vivace);
	}

	// TODO modifier la methode verifier typezone pour utiliser la temperature
	public void verifierTypeZone() {
		TypeZone riviere = new Riviere();
		this.ecosysteme.getTypeZones().forEach((typeZone) -> {
			if (this.eau >= typeZone.getEauMin()
					&& this.eau <= typeZone.getEauMax()/* && this.getNbVegetaux() >= typeZone.getNbVegetauxMin() */) {
				this.setTypeZone(typeZone);
				this.setTemperature(40 / this.eau * 200);
			}

			if (!(this.getTypeZone() instanceof Desert) && this.getNbArbre() > 30 && this.eau + 1 < riviere.getEauMin()) {
				this.eau += 1;
			}

		});
	}

	@Override
	public String toString() {
		return "[e:" + eau + "," + " t:" + temperature + ",a:" + animaux.size() + ",v:" + vegetaux.size() + ", n:"
				+ typeZone.getNomTypeZone() + "]";
	}

}
