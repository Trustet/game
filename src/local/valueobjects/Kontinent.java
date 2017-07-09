package local.valueobjects;

import java.util.List;

public class Kontinent {

	private List<Land> laender;
	private String name;

	public Kontinent(String name, List<Land> laender) {
		this.setName(name);
		this.laender = laender;
	}


	public String toString() {
		return name;
	}


	/**
	 * Gibt die Länder des Kontinents aus
	 * @return
	 */
	public List<Land> getLaender() {
		return laender;
	}

	/**
	 * Setzt die Länder des Kontinents
	 * @param laender
	 */
	public void setLaender(List<Land> laender) {
		this.laender = laender;
	}

	/**
	 *  Gibt den Namen des Kontinents aus 
	 * * @return String
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setzt den Namen des Kontinents
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
}
