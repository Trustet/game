package local.domain;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;

import local.domain.exceptions.KannEinheitenNichtVerschiebenException;
import local.domain.exceptions.KannLandNichtBenutzenException;
import local.domain.exceptions.KeinNachbarlandException;
import local.domain.exceptions.LandBereitsBenutztException;
import local.domain.exceptions.NichtGenugEinheitenException;
import local.domain.exceptions.SpielerExistiertBereitsException;
import local.persistence.FilePersistenceManager;
import local.valueobjects.Angriff;
import local.valueobjects.AngriffRueckgabe;
import local.valueobjects.Einheitenkarten;
import local.valueobjects.Kontinent;
import local.valueobjects.Land;
import local.valueobjects.Mission;
import local.valueobjects.Spieler;

public class Kriegsverwaltung {

private Spielerverwaltung spielerVw;
private Weltverwaltung weltVw;
private Missionsverwaltung missionVw;
private phasen Phase;
private List<Land> benutzteLaender = new Vector<Land>();
private FilePersistenceManager pm = new FilePersistenceManager();
private int startphaseZaehler = 1;
	
	/**
	 * Konstruktor Kriegsverwaltung
	 * @param spielerVw
	 * @param weltVw
	 */
	public Kriegsverwaltung(Spielerverwaltung spielerVw, Weltverwaltung weltVw, Missionsverwaltung missionVw) {
		this.spielerVw = spielerVw;
		this.weltVw = weltVw;
		this.missionVw = missionVw;
		Phase = phasen.VERTEILEN;
	}

	/**
	 * Gibt Nachbarlaender die angegriffen werden können zurück
	 * @param land
	 * @param spieler
	 * @return Vector<Land>
	 */
	public List<Land> moeglicheAngriffsziele(Land land) {
		List<Land> nachbarLaender = this.weltVw.getNachbarLaender(land);	
		Spieler spieler = land.getBesitzer();
		List<Land> rueckgabe = new Vector<Land>();
		for(Land l : nachbarLaender){
			if(!l.getBesitzer().equals(spieler)){
				rueckgabe.add(l);
			}
		}
		return rueckgabe;
	}
	
	/**
	 * Überprüft ob die beiden Länder Nachbarn sind
	 * @param wahlLand
	 * @param landZiel
	 * @param spieler
	 * @return boolean
	 * @throws KeinNachbarlandException
	 */
	public boolean istNachbar(Land wahlLand, Land landZiel, Spieler spieler) throws KeinNachbarlandException{
		List<Land> nachbarLaender = this.weltVw.getNachbarLaender(wahlLand);
		for(Land l : nachbarLaender){
			if(l.getName().equals(landZiel.getName())){
				return true;
			}
		}
		throw new KeinNachbarlandException(wahlLand.getName());
	}
	
	/**
	 * Würfelt mehrfach und gibt Liste mit Ergebnissen zurück
	 * @param anzahl
	 * @return Vector<Integer>
	 */
	public List<Integer> wuerfeln(int anzahl) {
		List<Integer> ergebnisse = new Vector<Integer>();
				
		for(int i = 0;i < anzahl;i++) {
			ergebnisse.add((int)(Math.random() * 6) + 1);
		}
		System.out.println( ergebnisse );
		Comparator<Integer> comparator = Collections.reverseOrder();
		Collections.sort(ergebnisse, comparator);
		System.out.println( ergebnisse );
		//jetzt ist die Stelle 0 die höchste Zahl und die Stelle 1 die zweithöchste
		return ergebnisse;
	}
	
	/**
	 * 
	 * @param angriff
	 * @return
	 * @throws KeinNachbarlandException
	 */
	public AngriffRueckgabe befreiungsAktion(Angriff angriff) throws KeinNachbarlandException {
		istNachbar(angriff.getAngriffsland(), angriff.getVerteidigungsland(), null);
		
		Land angreifendesLand = angriff.getAngriffsland();
		Land verteidigendesLand = angriff.getVerteidigungsland();
		int angreiferEinheiten = angreifendesLand.getEinheiten();
		int verteidigerEinheiten = verteidigendesLand.getEinheiten();
		int angreifendeEinheiten;
		int verteidigendeEinheiten;
		List<Integer> wuerfeAngreifer;
		List<Integer> wuerfeVerteidiger;
		List<Integer> verluste = new Vector<Integer>();
		AngriffRueckgabe rueckgabe;
		boolean erobert = false;

		if(angreiferEinheiten < 4) {
			angreifendeEinheiten = angreiferEinheiten - 1;
		} else {
			angreifendeEinheiten = 3;
		}
		
		if(verteidigerEinheiten < 2) {
			verteidigendeEinheiten = verteidigerEinheiten;
		} else {
			verteidigendeEinheiten = 2;
		}
		
		wuerfeAngreifer = wuerfeln(angreifendeEinheiten);
		wuerfeVerteidiger = wuerfeln(verteidigendeEinheiten);
		
		if(wuerfeVerteidiger.size() == 1)	{
			if(wuerfeVerteidiger.get(0) < wuerfeAngreifer.get(0))	{
				//Angreifer gewonnen
				verluste.add(0); //Verluste die der Angreifer macht
				verluste.add(1); //Verluste die der Verteidiger macht
			} else if(wuerfeVerteidiger.get(0) >= wuerfeAngreifer.get(0))	{
				//Verteidiger gewonnen
				verluste.add(1); //Verluste die der Angreifer macht
				verluste.add(0); //Verluste die der Verteidiger macht
			}
		} else if(wuerfeVerteidiger.size() == 2)	{
			if(wuerfeAngreifer.size() >= 2) {
				if((wuerfeVerteidiger.get(0) < wuerfeAngreifer.get(0) && (wuerfeVerteidiger.get(1) < wuerfeAngreifer.get(1))))	{
					//Angreifer gewonnen
					verluste.add(0); //Verluste die der Angreifer macht
					verluste.add(2); //Verluste die der Verteidiger macht
				} else if((wuerfeVerteidiger.get(0) >= wuerfeAngreifer.get(0) && (wuerfeVerteidiger.get(1) >= wuerfeAngreifer.get(1))))	{
					//Verteidiger gewonnen
					verluste.add(2); //Verluste die der Angreifer macht
					verluste.add(0); //Verluste die der Verteidiger macht
				} else	{
					verluste.add(1);
					verluste.add(1);
				}
			} else {
				if(wuerfeVerteidiger.get(0) < wuerfeAngreifer.get(0)) {
					//Angreifer gewonnen
					verluste.add(0); //Verluste die der Angreifer macht
					verluste.add(1); //Verluste die der Verteidiger macht
				} else if(wuerfeVerteidiger.get(0) >= wuerfeAngreifer.get(0))	{
					//Verteidiger gewonnen
					verluste.add(1); //Verluste die der Angreifer macht
					verluste.add(0); //Verluste die der Verteidiger macht
				}
			}
		}
		
		//verluste ist ein Vector mit den Angaben: AngreiferVerlust / VerteidigerVerlust
		angreifendesLand.setEinheiten(angreifendesLand.getEinheiten() - verluste.get(0));
		verteidigendesLand.setEinheiten(verteidigendesLand.getEinheiten() - verluste.get(1));

		if(verteidigendesLand.getEinheiten() == 0) {
			erobert = true;
			verteidigendesLand.setBesitzer(angreifendesLand.getBesitzer());
			angreifendesLand.setEinheiten(angreifendesLand.getEinheiten() - 1);
			verteidigendesLand.setEinheiten(0);
		}
		
		rueckgabe = new AngriffRueckgabe(verluste.get(1), verluste.get(0), wuerfeVerteidiger, wuerfeAngreifer, erobert);
		return rueckgabe;
	}
	
	/**
	 * Setzt eine gewisse Anzahl an Einheiten auf ein Land
	 * @param anzahl
	 * @param land
	 */
	public void einheitenPositionieren(int anzahl, Land land) {
		land.setEinheiten(land.getEinheiten() + anzahl);
	}
	
	/**
	 * Bestimmt die Anzahl an Einheiten, die der Spieler bekommt
	 * @param spieler
	 * @return int
	 */
	public int bekommtEinheiten(Spieler spieler) {
		int anzahl = 1;
		int einheiten = weltVw.besitztLaender(spieler).size() / 3;
		
		if (einheiten < 3) {
			einheiten = 3;
		}
			for (Kontinent k : weltVw.getKontinentenListe()){
				for (int i = 1;i < k.getLaender().size();i++){
					
					if(k.getLaender().get(i).getBesitzer() == k.getLaender().get(i-1).getBesitzer()) {
						anzahl++;
					}
					
					if (anzahl == k.getLaender().size()){
						if (k.getName() == "Europa"){
							einheiten+=5;	
						}else if(k.getName() =="Asien"){
							einheiten+=7;
						}else if(k.getName() == "Afrika"){
							einheiten+=3;
						}else if(k.getName() == "Suedamerika"){
							einheiten+=2;
						}else if(k.getName() == "Nordamerika"){
							einheiten+=5;
						}
					}
				}
			}
			return einheiten;
		}
	
	public int checkAnfangseinheiten(){
//		int einheiten = 0;
//		switch(spielerVw.getSpielerList().size()){
//		case 2:
//			einheiten = 35;
//			break;
//		case 3:
//			einheiten = 30;
//			break;
//		case 4:
//			einheiten = 30;
//			break;
//		case 5:
//			einheiten = 25;
//			break;
//		case 6:
//			einheiten = 25;
//			break;
//		}
		return 3;	//TODO: hier 25?
	}
	
	/**
	 * Setzt die nächste Phase
	 */
	public void nextTurn(){
		switch(Phase){
			case STARTPHASE:
				startphaseZaehler++;
				if(startphaseZaehler > spielerVw.getSpielerList().size()){
				Phase = phasen.ANGRIFF;
				}
				spielerVw.naechsterSpieler();
				break;
			case VERSCHIEBEN:
				Phase = phasen.VERTEILEN;
				spielerVw.naechsterSpieler();
				break;
			case ANGRIFF:
				Phase = phasen.VERSCHIEBEN;
				break;
			case VERTEILEN:
				Phase = phasen.ANGRIFF;
				break;
		}
	}
	
	/**
	 * Phasen Enum
	 */
	public enum phasen{
		STARTPHASE,VERTEILEN,ANGRIFF,VERSCHIEBEN
	}
	
	/**
	 * Gibt die aktuelle Phase zurück
	 * @return Phase
	 */
	public phasen getTurn(){
		return Phase;
	}
	
	/**
	 * 
	 * @param phase
	 */
	public void setTurn(String phase){
		switch(phase){
		case "STARTPHASE":
			this.Phase = phasen.STARTPHASE;
			break;
		case "VERSCHIEBEN":
			this.Phase = phasen.VERSCHIEBEN;
			break;
		case "ANGRIFF":
			this.Phase = phasen.ANGRIFF;
			break;
		case "VERTEILEN":
			this.Phase = phasen.VERTEILEN;
			break;
		}
	}
	
	/**
	 * Gibt den Spieler zurück
	 * @param spieler
	 * @return Spieler
	 */
	public Spieler nextSpieler(Spieler spieler){
		return spieler;
	}
	
	/**
	 * Besetzt das eroberte Land
	 * @param aLand
	 * @param vLand
	 * @param einheiten
	 */
	public void eroberungBesetzen(Land aLand, Land vLand, int einheiten){
		this.einheitenPositionieren(einheiten, vLand);
		this.einheitenPositionieren(-einheiten, aLand);
	}
	
	/**
	 * Überprüft ob das Land existiert und dem Spieler gehört
	 * @param land
	 * @param spieler
	 * @return boolean
	 * @throws KannLandNichtBenutzenException
	 */
	public boolean landWaehlen(String land, Spieler spieler) throws KannLandNichtBenutzenException{
		if(!weltVw.stringToLand(land).getBesitzer().equals(spieler)){
			throw new KannLandNichtBenutzenException();	
		}else{
			return true;
		}
	}
	
	/**
	 * Überprüft ob die eingegebene Einheitenzahl größer ist, als die Einheitenzahl auf dem Land
	 * @param land
	 * @param einheiten
	 * @return boolean
	 * @throws NichtGenugEinheitenException
	 */
	public boolean checkEinheiten(String land, int einheiten) throws NichtGenugEinheitenException{
		int landEinheiten = weltVw.stringToLand(land).getEinheiten();
		
		if(landEinheiten < 2 || landEinheiten <= einheiten){
			throw new NichtGenugEinheitenException(einheiten);
		}else{
			return true;
		}
	}
	
	/**
	 * Gibt alle eigenen NAchbarländer als Tabelle zurück
	 * @param land
	 * @param spieler
	 * @return String
	 */
	public List<Land> moeglicheVerschiebeZiele(Land land, Spieler spieler) {
		List<Land> nachbarLaender = this.weltVw.getNachbarLaender(land);
		List<Land> rueckgabe = new Vector<Land>();
		
		for(Land l : nachbarLaender) {
			if(spieler.equals(l.getBesitzer())) {
				rueckgabe.add(l);
			}
		}
		return rueckgabe;
	}
	
	/**
	 * Gibt zurück, ob das übergebene Land in der Runde für einen Angriff benutzt wurde
	 * @param land
	 * @return boolean
	 * @throws LandBereitsBenutztException
	 */
	public boolean benutzeLaender(Land land) throws LandBereitsBenutztException {
		if(benutzteLaender.contains(land)){
			throw new LandBereitsBenutztException(land.getName());
		}else{
			return true;
		}
	}
	
	/**
	 * Fügt der liste der benutzten Länder das übergebene Land zu
	 * @param land
	 */
	public void landBenutzen(Land land) {
		benutzteLaender.add(land);
	}
	
	/**
	 * Löscht die Liste, der Länder, die in einer Runde für einen Angriff benutzt wurden
	 */
	public void benutzteLaenderLoeschen() {
		benutzteLaender.clear();
	}
	
	/**
	 * Gibt die Liste der Länder zurück, die für einen Angriff benutzt wurden
	 * @return List<Land>
	 */
	public List<Land> getBenutzteLaenderListe() {
		return benutzteLaender;
	}
	
	/**
	 * Überprüft ob der Spieler genug Einheiten zum verschieben hat
	 * @param einheiten
	 * @param spieler
	 * @return boolean
	 * @throws KannEinheitenNichtVerschiebenException
	 */
	public boolean checkEinheitenVerteilen(int einheiten,int veinheiten, Spieler spieler) throws KannEinheitenNichtVerschiebenException{
		if(einheiten > veinheiten){
			throw new KannEinheitenNichtVerschiebenException(true);
		}else if(einheiten < 1){
			throw new KannEinheitenNichtVerschiebenException(false);
		}else{
			return true;
		}
	}
	
	/**
	 * 
	 * @param spieler
	 * @return
	 */
	public List<Land> getSpielerLaender(Spieler spieler){
		List<Land> rueckgabeLaender = new Vector<Land>();
		for(Land l : weltVw.getLaenderListe()){
			if(l.getBesitzer().equals(spieler)){
				rueckgabeLaender.add(l);
			}
		}
		return rueckgabeLaender;
	}
	
	/**
	 * 
	 * @param datei
	 * @throws IOException
	 */
	public void spielSpeichern(String datei) throws IOException{
		pm.schreibkanalOeffnen(datei);
		pm.spielSpeichern(weltVw.getLaenderListe(), spielerVw.getSpielerList(), Phase + "", spielerVw.getAktiverSpielerNummer(), missionVw.getMissionsListe());
		pm.close();
	}
	
	/**
	 * 
	 * @param datei
	 * @throws IOException
	 * @throws SpielerExistiertBereitsException
	 */
	public void spielLaden(String datei) throws IOException, SpielerExistiertBereitsException {
		pm.lesekanalOeffnen(datei);
		String phase = pm.spielstandLaden();
		String spieler = "";
		String spieler2 = "";
		Spieler spielerS2 = null;
		String land = "";
		String kuerzel = "";
		String karte = "";
		int id = 0;
		boolean istSpielerMission = false;
		int einheiten = 0;
		
		switch(phase){
		case "ANGRIFF":
			Phase = phasen.ANGRIFF;
		case "Verschieben":
			Phase = phasen.VERSCHIEBEN;
		case "VERTEILEN":
			Phase = phasen.VERTEILEN;
		}
		
		do{
			spieler = pm.spielstandLaden();
			if(spieler.length() != 0){
				spielerVw.neuerSpieler(spieler);
			}
		}while(spieler.length() != 0);
		
		do{
			land = pm.spielstandLaden();
			if(land.length() != 0){
				spieler = pm.spielstandLaden();
				einheiten = Integer.parseInt(pm.spielstandLaden());
				kuerzel = pm.spielstandLaden();
				int fahneX = Integer.parseInt(pm.spielstandLaden());
				int fahneY = Integer.parseInt(pm.spielstandLaden());
				for(Spieler s : spielerVw.getSpielerList()){
					if(s.getName().equals(spieler)){
						weltVw.getLaenderListe().add(new Land(land,s,einheiten,kuerzel, fahneX, fahneY));
					}
				}
			}	
		}while(land.length() != 0);
		
		int spielerNummer = Integer.parseInt(pm.spielstandLaden());
		spielerVw.setAktiverSpieler(spielerNummer);

		do{
			spieler = pm.spielstandLaden();
			istSpielerMission = Boolean.parseBoolean(pm.spielstandLaden());
			if(istSpielerMission)
			{
				spieler2 = pm.spielstandLaden();
				for(Spieler s : spielerVw.getSpielerList()){
					if(s.equals(spieler2)){
						spielerS2 = s;
					}
				}
			}
			id = Integer.parseInt(pm.spielstandLaden());
			for(Spieler s : spielerVw.getSpielerList()){
				if(s.getName().equals(spieler)){
					missionVw.missionLaden(weltVw.getLaenderListe(), weltVw.getKontinentenListe(), spielerVw.getSpielerList(),s,spielerS2,id);
				}
			}
		}while(spieler.length() != 0);
		
		for(Spieler s: spielerVw.getSpielerList()) {
			do{
				karte = pm.spielstandLaden();
				Einheitenkarten einheitenkarte = new Einheitenkarten(karte);
				s.getEinheitenkarten().add(einheitenkarte);
			}while(karte.length() != 0);
		}
		pm.close();
	}
	
	/**
	 * 
	 * @param spieler
	 * @return
	 */
	public boolean spielerRaus(Spieler spieler) {
		for(Land l : weltVw.getLaenderListe()) {
			if(l.getBesitzer().equals(spieler)) {
				return false;
			}
		}
		spielerVw.getSpielerList().remove(spieler);
		return true;
	}
	
	/**
	 * 
	 * @return
	 */
	public Mission getMissionVonAktivemSpieler()
	{
		for(Mission m: missionVw.getMissionsListe()) {
			if(m.getSpieler().equals(spielerVw.getAktiverSpieler())) {
				return m;
			}
		}
		return null;
	}
}
