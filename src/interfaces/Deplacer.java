package interfaces;

import main.Execeptions.SeDeplacerException;

public interface Deplacer {
	void seDeplacer(int x, int y) throws SeDeplacerException;
}