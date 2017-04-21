package local.domain;

import java.util.List;

import local.domain.Kriegsverwaltung.phasen;
import local.domain.exceptions.SpielerExistiertBereitsException;
import local.valueobjects.*;

public class Spielfeld {
	
	public Spielerverwaltung spielerVw;
	public Weltverwaltung weltVw;
	public Kriegsverwaltung kriegsVw;
	public phasen Phase;
	
	
	/**
	 * Konstruktor erstellt die Verwaltungen, so dass sie sich untereinander kennen
	 */
	public Spielfeld() {
		
		this.spielerVw = new Spielerverwaltung();
		this.weltVw = new Weltverwaltung();
		this.kriegsVw = new Kriegsverwaltung(spielerVw, weltVw);
	}
	
	/**
	 * erstellt Spieler
	 * @param name
	 */
	public void erstelleSpieler(String name) throws SpielerExistiertBereitsException {
		spielerVw.neuerSpieler(name);
	}
	

	/**
	 * @param anzahlSpieler
	 */
	public void laenderAufteilen(int anzahlSpieler) {		
		List<Spieler> spielerListe = spielerVw.getSpielerList();
		weltVw.laenderAufteilen(spielerListe);		
	}
	
	/**
	 * @param spieler
	 * @return int
	 */
	public int bekommtEinheiten(Spieler spieler) {
		return kriegsVw.bekommtEinheiten(spieler);
	}
	
	/**
	 * @param spieler
	 * @return List<Land>
	 */
	public List<Land> besitztLaender(Spieler spieler) {
		return weltVw.besitztLaender(spieler);
	}
	
	public phasen getTurn(){
		return kriegsVw.getTurn();
	}
	public void nextTurn(){
		kriegsVw.nextTurn();
	}
	
	public Spieler getAktiverSpieler(){
		return spielerVw.getAktiverSpieler();
	}
	public void naechsterSpieler(){
		spielerVw.naechsterSpieler();
	}
	public String eigeneNachbarn(String landString, Spieler spieler){
		return kriegsVw.eigeneNachbarn(landString, spieler);
	}
	public Land stringToLand(String angriffsLandString) {
		return weltVw.stringToLand(angriffsLandString);
	}
	
}


