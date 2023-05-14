package interfaces;

import main.Execeptions.SeDeplacerException;

public interface Marche {
	void seDeplacer(int x, int y) throws SeDeplacerException;
}