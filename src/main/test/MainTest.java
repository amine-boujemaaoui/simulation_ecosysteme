package main.test;

import java.util.ArrayList;
import main.Ecosysteme;
import main.TypeZones.TypeZone;

public class MainTest {
	public static void main(String[] args) {
		Ecosysteme e = new Ecosysteme(15, 15, new ArrayList<TypeZone>());
		e.simulation();
	}
}
