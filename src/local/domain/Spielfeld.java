package local.domain;

import java.util.List;

import local.domain.Kriegsverwaltung.phasen;
import local.domain.exceptions.KannLandNichtBenutzenException;
import local.domain.exceptions.KeinGegnerException;
import local.domain.exceptions.KeinNachbarlandException;
import local.domain.exceptions.LandBereitsBenutztException;
import local.domain.exceptions.LandExistiertNichtException;
import local.domain.exceptions.NichtGenugEinheitenException;
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
	public void einheitenPositionieren(int anzahl, Land land) {
		kriegsVw.einheitenPositionieren(anzahl, land);
	}
	public String moeglicheAngriffsziele(String landString, Spieler spieler) {
		return kriegsVw.moeglicheAngriffsziele(landString, spieler);
	}
	public List<String> befreiungsAktion(String angreifendesLandString, String verteidigendesLandString) {
		return kriegsVw.befreiungsAktion(angreifendesLandString, verteidigendesLandString);
	}
	public List<Spieler> getSpielerList() {
		return spielerVw.getSpielerList();
	}
	public boolean istNachbar(Land wahlLand, Land landZiel, Spieler spieler) throws KeinNachbarlandException{
		return kriegsVw.istNachbar(wahlLand ,landZiel, spieler);
	}
	public void eroberungBesetzen(Land aLand, Land vLand, int einheiten){
		kriegsVw.eroberungBesetzen(aLand,vLand, einheiten);
	}
	public boolean landWaehlen(String land, Spieler spieler) throws KannLandNichtBenutzenException{
		return kriegsVw.landWaehlen(land,spieler);
	}
	public boolean checkEinheiten(String land, int einheiten) throws NichtGenugEinheitenException{
		return kriegsVw.checkEinheiten(land,einheiten);
	}
	public String eigeneLaenderListe(Spieler spieler){
		return weltVw.eigeneLaenderListe(spieler);
	}
	public String weltAnsicht(){
		return weltVw.weltAnsicht(getSpielerList());
	}
	public String eigeneAngriffsLaender(Spieler spieler){
		return weltVw.eigeneAngriffsLaender(spieler);
	}
	public boolean landExistiert(String land) throws LandExistiertNichtException{
		return weltVw.landExistiert(land);
	}
	public boolean istGegner(String land,Spieler spieler) throws KeinGegnerException{
		return weltVw.istGegner(land, spieler);
	}
	public String moeglicheVerschiebeZiele(Land land, Spieler spieler){
		return kriegsVw.moeglicheVerschiebeZiele(land, spieler);
	}
	public boolean benutzeLaender(Land land) throws LandBereitsBenutztException{
		return kriegsVw.benutzeLaender(land);
	}
	public void landBenutzen(Land land){
		kriegsVw.landBenutzen(land);
	}
	public void benutzteLaenderLoeschen(){
		kriegsVw.benutzteLaenderLoeschen();
	}
	public String eigeneVerschiebeLaender(Spieler spieler){
		return weltVw.eigeneVerschiebeLaender(spieler, kriegsVw.getBenutzteLaenderListe());
	}
}


