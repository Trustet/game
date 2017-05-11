package local.domain;

import java.io.IOException;
import java.util.List;

import local.domain.Kriegsverwaltung.phasen;
import local.domain.exceptions.*;
import local.valueobjects.*;


public class Spielfeld {
	
	public Spielerverwaltung spielerVw;
	public Weltverwaltung weltVw;
	public Kriegsverwaltung kriegsVw;
	public Missionsverwaltung missionVw;
	public Einheitenkartenverwaltung einheitenVw;
	public phasen Phase;
	
	
	/**
	 * Konstruktor erstellt die Verwaltungen, so dass sie sich untereinander kennen
	 * @throws IOException 
	 */
	public Spielfeld(){
		
		this.spielerVw = new Spielerverwaltung();
		this.weltVw = new Weltverwaltung();
		this.missionVw = new Missionsverwaltung();
		this.einheitenVw = new Einheitenkartenverwaltung();
		this.kriegsVw = new Kriegsverwaltung(spielerVw, weltVw, missionVw);
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
	public void laenderAufteilen() {		
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
	public List<Land> moeglicheAngriffsziele(Land land) {
		return kriegsVw.moeglicheAngriffsziele(land);
	}
	public AngriffRueckgabe befreiungsAktion(Angriff angriff) {
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
	public List<Land> eigeneAngriffsLaender(Spieler spieler){
		return weltVw.eigeneAngriffsLaender(spieler);
	}
	public boolean landExistiert(String land) throws LandExistiertNichtException{
		return weltVw.landExistiert(land);
	}
	public boolean istGegner(String land,Spieler spieler) throws KeinGegnerException{
		return weltVw.istGegner(land, spieler);
	}
	public List<Land> moeglicheVerschiebeZiele(Land land, Spieler spieler){
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
	public List<Land> eigeneVerschiebeLaender(Spieler spieler){
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
	public void missionsListeErstellen() throws IOException{
		missionVw.missionsListeErstellen(weltVw.getLaenderListe(), weltVw.getKontinentenListe(), spielerVw.getSpielerList());
	}
	public List<String> willkommenNachricht(){
		return kriegsVw.willkommenNachricht();
	}
	public List<Land> getLaenderListe(){
		return weltVw.getLaenderListe();
	}
	public void speicherSpieler() throws IOException{
		spielerVw.speicherSpieler();
	}
	public void speicherLaender() throws IOException{
		weltVw.speicherLaender();
	}
	public void spielSpeichern(String datei) throws IOException{
		kriegsVw.spielSpeichern(datei);
	}
//	public void spielLaden(String datei) throws IOException, SpielerExistiertBereitsException{
//		kriegsVw.spielLaden(datei);
//	}

	public Einheitenkarten einheitenKarteZiehen(Spieler spieler) {
		return einheitenVw.karteNehmen(spieler);	
	}
	public boolean missionIstAbgeschlossen(Mission mission){
		return mission.istAbgeschlossen();
	}
	
	public boolean landZumAngreifen(Spieler spieler) throws KeinLandZumAngreifenException{
		return kriegsVw.landZumAngreifen(spieler);
	}
	public boolean landZumAngreifen(Spieler spieler, Land land) throws KeinLandZumAngreifenException{
		return kriegsVw.landZumAngreifen(spieler, land);
	}
	
	public boolean spielerRaus(Spieler spieler){
		return kriegsVw.spielerRaus(spieler);
	}

	public int kartenEinloesen(Spieler spieler) {
		return einheitenVw.einheitenkartenEinloesen(spieler);
	}
	public void spielLaden(String datei) throws IOException, SpielerExistiertBereitsException{
		kriegsVw.spielLaden(datei);
	}
	public void laenderErstellen() throws IOException{
		weltVw.laenderErstellen();
	}
	public void laenderverbindungenUndKontinenteErstellen(){
		weltVw.erstellen();
	}
	public Mission getSpielerMission(Spieler spieler){
		return missionVw.getSpielerMission(spieler);
	}
	public List<Mission> getMissionsListe(){
		return missionVw.getMissionsListe();
	}

}


