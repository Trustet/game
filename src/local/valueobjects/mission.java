package local.valueobjects;

public class Mission {

	private String beschreibung;
	private boolean abgeschlossen;

	public Mission(String beschreibung) {
		this.beschreibung = beschreibung;
		this.abgeschlossen = false;
	}

	public String getBeschreibung() {
		return beschreibung;
	}

	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}

	public boolean isAbgeschlossen() {
		return abgeschlossen;
	}

	public void setAbgeschlossen(boolean abgeschlossen) {
		this.abgeschlossen = abgeschlossen;
	}
	
}
