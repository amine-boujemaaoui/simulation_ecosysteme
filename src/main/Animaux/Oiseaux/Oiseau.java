package main.Animaux.Oiseaux;

import java.awt.Image;

import javax.swing.ImageIcon;

import interfaces.Manger;
import main.Zone;
import main.Animaux.Animal;
import main.Execeptions.MangerException;
import main.Execeptions.ReproduireException;
import main.TypeZones.TypeZone;

public abstract class Oiseau extends Animal implements Manger {
	public static final Image icon = new ImageIcon(System.getProperty("user.dir")+"/src/assets/animals/oiseau.png").getImage();

	public Oiseau(Zone zone_actuel, double eauRequise, int ageMax, int tauxDeReproduction, int ageMinReproduction,
			TypeZone zoneFavorable) {
		super(zone_actuel, eauRequise, ageMax, tauxDeReproduction, ageMinReproduction, zoneFavorable);
	}

	public void manger() throws MangerException {
		Zone z = this.getZone_actuel();
		if (z.getNbInsecte() > 0) {
			for (int i = 0; i < r.nextInt(2); i++) {
				if (z.getNbInsecte() == 0)
					break;
				else if (r.nextInt(100) > 80)
					z.removeInsecte(r.nextInt(z.getNbInsecte()));
			}
			this.setNbCyclesSansManger(0);
		} else
			this.augmenterNbCyclesSansManger();
	}

	@Override
	public void seReproduire() throws ReproduireException {
	}
}
