package main.test;

import main.*;
import main.TypeZones.*;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import main.Grille.GrilleNature;

public class MainTest {
	public static void main(String[] args) {

		TypeZone typeZone_Foret = new Foret();
		TypeZone typeZone_Plaine = new Plaine();
		System.out.println("typeZone_Foret_id = " + typeZone_Foret.getId_typeZone() + "\ntypeZone_Plaine_id = "
				+ typeZone_Plaine.getId_typeZone());

		ArrayList<Zone> zones = new ArrayList<Zone>();

		Random r = new Random();
		for (int i = 0; i < 30; i++) {
			if (r.nextBoolean()) {
				zones.add(new Zone(15.2, 8.7));
				zones.get(i).setTypeZone(typeZone_Foret);
			} else {
				zones.add(new Zone(5.6, 14.2));
				zones.get(i).setTypeZone(typeZone_Plaine);
			}
		}

		zones.forEach((z) -> {
			for (int i = 0; i < r.nextInt(21); i++)
				z.addAnimal(new Animal());
			for (int i = 0; i < r.nextInt(21); i++)
				z.addVegetal(new Vegetal());
		});

		// AFFICHAGE GRAPHIQUE
		int nbCasesL = 5, nbCasesH = 6, temp = 0;
		GrilleNature grille = new GrilleNature(nbCasesL, nbCasesH, 80);

		for (int i = 0; i < nbCasesL; i++) {
			for (int j = 0; j < nbCasesH; j++) {
				if (zones.get(i * nbCasesH + j).getTypeZone().getNomTypeZone().equals("Foret"))
					grille.colorieFond(i, j, Color.GREEN);
				else
					grille.colorieFond(i, j, Color.YELLOW);
				grille.addDisque(i, j, zones.get(i * nbCasesH + j).getAnimaux().size() * 2, Color.BLUE);
				grille.addDisque(i, j, zones.get(i * nbCasesH + j).getVegeteaux().size() * 2, Color.RED);
				System.out.print(zones.get(i * nbCasesH + j).toString());
			}
			System.out.print("\n");
		}
		grille.redessine();
	}
}
