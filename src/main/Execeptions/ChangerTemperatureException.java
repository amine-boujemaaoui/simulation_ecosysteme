package main.Execeptions;

public class ChangerTemperatureException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public ChangerTemperatureException(String message) {
		super(message);
	}
}
