package main.test;

import main.Ecosysteme;
import main.Execeptions.ChangerEauException;
import main.Execeptions.ChangerTemperatureException;

public class MainTest_Ecosysteme {

	public static void main(String[] args) throws ChangerEauException, ChangerTemperatureException {
		Ecosysteme e = new Ecosysteme(5, 6);
		e.simulation();
	}

}
