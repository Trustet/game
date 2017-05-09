package local.valueobjects;

import java.util.List;
import java.util.Vector;

public abstract class Mission {

	private String beschreibung;
	private Spieler spieler;
	
	//TODO Unterklassen erstellen Ländermission etc.
	public Mission(String beschreibung, Spieler spieler) {
		super();
		this.beschreibung = beschreibung;
		this.spieler = spieler;
	}
	//TODO diese Funktion in den Unterklassen überschreiben
	public abstract boolean istAbgeschlossen();
	
	public String getBeschreibung() {
		return beschreibung;
	}

	public Spieler getSpieler() {
		return spieler;
	}
}
