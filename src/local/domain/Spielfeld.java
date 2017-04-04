package local.domain;

import java.util.List;

import local.valueobjects.*;

public class Spielfeld {
	
	public Spielerverwaltung spielerVw;
	public Weltverwaltung weltVw;
	public Kriegsverwaltung kriegsVw;
	
	/**
	 * Konstruktor erstellt die Verwaltungen, so dass sie sich untereinander kennen
	 */
	public Spielfeld() {
		this.spielerVw = new Spielerverwaltung();
		this.weltVw = new Weltverwaltung(spielerVw);
		this.kriegsVw = new Kriegsverwaltung(spielerVw, weltVw);
		spielerVw.setVerwaltung(weltVw, kriegsVw);
		weltVw.setVerwaltung(kriegsVw);
	}
	
	/**
	 * erstellt Spieler
	 * @param name
	 */
	public void erstelleSpieler(String name) {
		spielerVw.neuerSpieler(name);
	}
	
	/**
	 * @param index
	 * @return String
	 */
	public String zeigeName(int index) {
		return spielerVw.zeigeName(index);
	}
	
	/**
	 * @param anzahlSpieler
	 */
	public void laenderAufteilen(int anzahlSpieler) {
		weltVw.laenderAufteilen(anzahlSpieler, spielerVw, weltVw);			
	}
	
	/**
	 * @param land
	 * @param spieler
	 * @return List<Land>
	 */
	public List<Land> moeglicheAngriffe(Land land,Spieler spieler) {
		return kriegsVw.moeglicheAngriffsziele(land, spieler);
	}
}
