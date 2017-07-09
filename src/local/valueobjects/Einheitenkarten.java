package local.valueobjects;

public class Einheitenkarten {

	private String kartenwert;

	/**
	 * @param kartenwert
	 */
	public Einheitenkarten(String kartenwert) {
		this.kartenwert = kartenwert;
	}

	/**
	 * Gibt den Kartenwert aus
	 * @return
	 */
	public String getKartenwert() {
		return kartenwert;
	}

	/**
	 * Setzt den Kartenwert
	 * @param kartenwert
	 */
	public void setKartenwert(String kartenwert) {
		this.kartenwert = kartenwert;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Einheitenkarten) {
			Einheitenkarten andereKarte = (Einheitenkarten) obj;
			return this.kartenwert == andereKarte.kartenwert;
		}
		return false;
	}
	
}
