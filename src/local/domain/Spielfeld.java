package local.domain;

import java.util.List;

import local.domain.Kriegsverwaltung.phasen;
import local.domain.exceptions.*;
import local.valueobjects.*;

/**
 * @author yannik
 *
 */
public class Spielfeld {
	
	public Spielerverwaltung spielerVw;
	public Weltverwaltung weltVw;
	public Kriegsverwaltung kriegsVw;
	public Missionsverwaltung missionVw;
	public phasen Phase;
	
	
	/**
	 * Konstruktor erstellt die Verwaltungen, so dass sie sich untereinander kennen
	 */
	public Spielfeld() {
		
		this.spielerVw = new Spielerverwaltung();
		this.weltVw = new Weltverwaltung();
		this.kriegsVw = new Kriegsverwaltung(spielerVw, weltVw);
		this.missionVw = new Missionsverwaltung();
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
	
	/**
	 * Leitet die Phase aus der Kriegsverwaltung weiter
	 * @return phasen
	 */
	public phasen getTurn(){
		return kriegsVw.getTurn();
	}
	/**
	 * Ruft nextTurn in der KriegsVerwaltung auf
	 */
	public void nextTurn(){
		kriegsVw.nextTurn();
	}
	
	/**
	 * Leitet Spieler aus der Spielerverwaltung weiter
	 * @return Spieler
	 */
	public Spieler getAktiverSpieler(){
		return spielerVw.getAktiverSpieler();
	}
	/**
	 * Ruft nächsterSpieler in der Spielerverwaltung auf 
	 */
	public void naechsterSpieler(){
		spielerVw.naechsterSpieler();
	}
	/**
	 * Leitet die Ausgabe von eigeneNachbarn aus der KriegsVerwaltung weiter
	 * @param landString
	 * @param spieler
	 * @return String
	 */
	public String eigeneNachbarn(String landString, Spieler spieler){
		return kriegsVw.eigeneNachbarn(landString, spieler);
	}
	
	/**
	 * Leitet das Land aus stringToLand weiter
	 * @param angriffsLandString
	 * @return Land
	 */
	public Land stringToLand(String angriffsLandString) {
		return weltVw.stringToLand(angriffsLandString);
	}
	public void einheitenPositionieren(int anzahl, Land land) {
		kriegsVw.einheitenPositionieren(anzahl, land);
	}
	public String moeglicheAngriffsziele(String landString, Spieler spieler) {
		return kriegsVw.moeglicheAngriffsziele(landString, spieler);
	}
	public List<String> befreiungsAktion(Angriff angriff) {
		return kriegsVw.befreiungsAktion(angriff);
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
//	public String weltAnsicht(){
//		return weltVw.weltAnsicht(getSpielerList());
//	}
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
	public boolean checkEinheitenVerteilen(int einheiten,int veinheiten, Spieler spieler) throws KannEinheitenNichtVerschiebenException{
		return kriegsVw.checkEinheitenVerteilen(einheiten, veinheiten ,spieler);
	}
	public String einheitenAusgabe(Land erstesLand, Land zweitesLand){
		return weltVw.einheitenAusgabe(erstesLand, zweitesLand);
	}
	public void missionenVerteilen(){
		missionVw.missionenVerteilen(spielerVw.getSpielerList());
	}
	public String missionAusgeben(Spieler spieler){
		return missionVw.missionAusgeben(spieler);
	}
	public void missionsListeErstellen(){
		missionVw.missionsListeErstellen();
	}
	public List<String> willkommenNachricht(){
		return kriegsVw.willkommenNachricht();
	}
}


