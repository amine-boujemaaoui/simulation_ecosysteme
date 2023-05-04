package interfaces;

import main.Vegetal;
import main.Execeptions.ReproduireException;

public interface ReproductionVegetal {
	Vegetal seReproduire() throws ReproduireException;
}