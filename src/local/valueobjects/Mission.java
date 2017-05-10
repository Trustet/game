package local.valueobjects;

import java.util.List;
import java.util.Vector;

public abstract class Mission{

	private String beschreibung;
	private Spieler spieler;
	protected List<Land> laender;
	private int id;

	public Mission(int id, String beschreibung, Spieler spieler) {
		this.beschreibung = beschreibung;
		this.spieler = spieler;
		this.id = id;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Land> getLaender() {
		return laender;
	}

	public void setLaender(List<Land> laender) {
		this.laender = laender;
	}

	public abstract boolean istAbgeschlossen();
	
	public String getBeschreibung() {
		return beschreibung;
	}

	public Spieler getSpieler() {
		return spieler;
	}

	public void setSpieler(Spieler spieler) {
		this.spieler = spieler;
	}

	
}
