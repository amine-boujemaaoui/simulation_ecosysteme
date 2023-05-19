package main;

import java.util.ArrayList;
import main.TypeZones.Desert;
import main.TypeZones.Foret;
import main.TypeZones.Plaine;
import main.TypeZones.Riviere;
import main.TypeZones.TypeZone;
import main.Animaux.*;
import main.Animaux.Insectes.*;
import main.Animaux.Mammiferes.Carnivores.*;
import main.Animaux.Mammiferes.Herbivore.*;
import main.Animaux.Oiseaux.*;
import main.Vegetaux.*;
import main.Vegetaux.Arbres.*;
import main.Vegetaux.Vivaces.*;

public class Main {

	public static void main(String[] args) {

		ArrayList<TypeZone> typeZones = new ArrayList<TypeZone>();
		typeZones.add(new Riviere());
		typeZones.add(new Foret());
		typeZones.add(new Plaine());
		typeZones.add(new Desert());

		ArrayList<Animal> typeAnimaux = new ArrayList<Animal>();
		typeAnimaux.add(new Abeille(null));
		typeAnimaux.add(new Fourmi(null));
		typeAnimaux.add(new Loup(null));
		typeAnimaux.add(new Vache(null));
		typeAnimaux.add(new Corbeau(null));
		typeAnimaux.add(new Pigeon(null));

		ArrayList<Vegetal> typeVegetaux = new ArrayList<Vegetal>();
		typeVegetaux.add(new Chene(null));
		typeVegetaux.add(new Sapin(null));
		typeVegetaux.add(new Arbuste(null));
		typeVegetaux.add(new Absinthe(null));
		typeVegetaux.add(new Artichaut(null));
		typeVegetaux.add(new Fougere(null));

		ArrayList<Event> events = new ArrayList<Event>();
		events.add(new Event(null, 150, 12, 8, new Fourmi(null), 400, new Absinthe(null), 800, 100, 30));
		events.add(new Event(null, 150, 12, 8, new Pigeon(null), 40, new Artichaut(null), 400, 100, 30));

		Ecosysteme e = new Ecosysteme("map1.txt", typeZones, typeAnimaux, typeVegetaux, events, 1000);
		e.simulation();

	}

}
