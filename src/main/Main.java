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
		events.add(new Event(null, 70, 2, 8, new Fourmi(null), 400, new Absinthe(null), 400, 100, 30));
		events.add(new Event(null, 70, 3, 8, new Fourmi(null), 400, new Absinthe(null), 400, 100, 30));
		events.add(new Event(null, 70, 2, 9, new Fourmi(null), 400, new Absinthe(null), 400, 100, 30));
		events.add(new Event(null, 70, 3, 9, new Fourmi(null), 400, new Absinthe(null), 400, 100, 30));
		events.add(new Event(null, 70, 2, 11, new Guepard(null), 200, null, 0, 100, 30));
		events.add(new Event(null, 400, 5, 1, null, 0, new Chene(null), 200, 100, 30));
		events.add(new Event(null, 400, 1, 5, null, 0, new Chene(null), 200, 100, 30));
		events.add(new Event(null, 75, 3, 8, new Pigeon(null), 400, null, 0, 100, 30));
		events.add(new Event(null, 75, 4, 8, new Pigeon(null), 400, null, 0, 100, 30));
		events.add(new Event(null, 75, 3, 9, new Pigeon(null), 400, null, 0, 100, 30));
		events.add(new Event(null, 75, 4, 9, new Pigeon(null), 400, null, 0, 100, 30));
		events.add(new Event(null, 75, 7, 1, null, 0, new Absinthe(null), 100, 100, 30));

		Ecosysteme e = new Ecosysteme("map1.txt", typeZones, typeAnimaux, typeVegetaux, events, 6000);
		e.simulation();

	}

}
