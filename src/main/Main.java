package main;

import java.util.ArrayList;

import main.TypeZones.Desert;
import main.TypeZones.Foret;
import main.TypeZones.Plaine;
import main.TypeZones.Riviere;
import main.TypeZones.TypeZone;

public class Main {

	public static void main(String[] args) {
		
		ArrayList<TypeZone> typeZones = new ArrayList<TypeZone>();
		typeZones.add(new Riviere());
		typeZones.add(new Foret());
		typeZones.add(new Plaine());
		typeZones.add(new Desert());
		Ecosysteme e = new Ecosysteme(typeZones);
		e.simulation();
	}

}
