package local.valueobjects;

public class MissionAlt {

	private String beschreibung;
	private boolean abgeschlossen;
	private Spieler spieler;

	public MissionAlt(String beschreibung, Spieler spieler) {
		this.beschreibung = beschreibung;
		this.spieler = spieler;
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
	public Spieler getMissionSpieler(){
		return this.spieler;
	}
	public void setMissionSpieler(Spieler spieler){
		this.spieler = spieler;
	}
	
}
