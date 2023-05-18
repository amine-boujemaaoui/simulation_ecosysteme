package main.TypeZones;

import java.awt.Color;
import javax.swing.ImageIcon;

public abstract class TypeZone {
	private double eauMin;
	private double eauMax;
	private double temperatureMin;
	private double temperatureMax;
	private final int id_typeZone;
	public static int last_id_typeZone = 0;
	private String nomTypeZone;
	private final Color c;
	private ImageIcon typeZoneIcon;
	private final int nbVegetauxMin;

	public TypeZone(double eauMin, double eauMax, double temperatureMin, double temperatureMax, int nbVegetauxMin, String nomTypeZone, Color c, String iconPath) {
		super();
		this.eauMin = eauMin;
		this.eauMax = eauMax;
		this.temperatureMin = temperatureMin;
		this.temperatureMax = temperatureMax;
		this.nbVegetauxMin = nbVegetauxMin;
		this.id_typeZone = TypeZone.last_id_typeZone;
		TypeZone.last_id_typeZone++;
		this.nomTypeZone = nomTypeZone;
		this.c = c;
		this.typeZoneIcon = new ImageIcon(iconPath);
	}

	public double getEauMin() {
		return eauMin;
	}

	public double getEauMax() {
		return eauMax;
	}

	public double getTemperatureMin() {
		return temperatureMin;
	}

	public double getTemperatureMax() {
		return temperatureMax;
	}

	public int getId_typeZone() {
		return id_typeZone;
	}

	public String getNomTypeZone() {
		return nomTypeZone;
	}

	public Color getC() {
		return c;
	}

	@Override
	public String toString() {
		return "TypeZone :\n" + "  - eauMin = " + eauMin + ",\n" + "  - eauMax = " + eauMax + ",\n"
				+ "  - temperatureMin = " + temperatureMin + ",\n" + "  - temperatureMax = " + temperatureMax + ",\n"
				+ "  - id_typeZone = " + id_typeZone + ",\n" + "  - nomTypeZone = " + nomTypeZone + ",\n";
	}

	public ImageIcon getTypeZoneIcon() {
		return typeZoneIcon;
	}

	public int getNbVegetauxMin() {
		return nbVegetauxMin;
	}
}
