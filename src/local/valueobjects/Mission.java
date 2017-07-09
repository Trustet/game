package local.valueobjects;

import java.util.List;
import java.util.Vector;

public abstract class Mission{

	protected String beschreibung;
	protected Spieler spieler;
	protected Spieler spieler2;
	private int id;
	private String art;

	/**
	 * @param id
	 * @param beschreibung
	 * @param spieler
	 * @param art
	 */
	public Mission(int id, String beschreibung, Spieler spieler, String art) {
		this.beschreibung = beschreibung;
		this.spieler = spieler;
		this.id = id;
		this.art = art;
	}
	
	public String getArt() {
		return art;
	}

	public void setArt(String art) {
		this.art = art;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
	
	public Spieler getSpieler2() {
		return spieler2;
	}
	
	public void setSpieler2(Spieler spieler) {
		this.spieler2 = spieler;
		
	}
}
