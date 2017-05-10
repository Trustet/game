package local.domain;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;

import java.util.List;
import java.util.Vector;

import local.domain.exceptions.*;
import local.persistence.FilePersistenceManager;
import local.valueobjects.*;


public class Kriegsverwaltung {

private Spielerverwaltung spielerVw;
private Weltverwaltung weltVw;
private Missionsverwaltung missionVw;
private phasen Phase;
//public List<Mission> missionsListe = new Vector<Mission>();
private List<Land> benutzteLaender = new Vector<Land>();
private FilePersistenceManager pm = new FilePersistenceManager();
	
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
		Collections.sort(ergebnisse);
		
		return ergebnisse;
	}
	
	/**
	 * Greift mit einem Land ein anderes an und gibt Verluste zurück
	 * @param angreifendesLand
	 * @param verteidigendesLand
	 * @return Vector<Integer> Verluste
	 */

//	public AttackResult befreiungsAktion(Attack attack) {
//		Attack -> Angreifer, Verteidiger, vielleicht noch wie viele Würfel
//		AttackResult -> AngreiferLand, VerteidigerLand, Gewinner / Verluste 
	public AngriffRueckgabe befreiungsAktion(Angriff angriff) {
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
		//TODO irgendein Fall wurde hier noch nicht beachtet, jedes 10. mal oder so kommt eine Exception
		if((wuerfeVerteidiger.size() == 1) && (wuerfeVerteidiger.get(0) < wuerfeAngreifer.get(1))) {
			 verluste.add(0);
			 verluste.add(1);
		} else if((wuerfeVerteidiger.size() == 2) && (wuerfeVerteidiger.get(0) < wuerfeAngreifer.get(1)) && (wuerfeVerteidiger.get(1) < wuerfeAngreifer.get(2))) {
			 verluste.add(0);
			 verluste.add(2);
		} else if((wuerfeVerteidiger.size() == 2) && (((wuerfeVerteidiger.get(0) < wuerfeAngreifer.get(1)) && (wuerfeVerteidiger.get(1) >= wuerfeAngreifer.get(2))) || ((wuerfeVerteidiger.get(0) >= wuerfeAngreifer.get(1)) && (wuerfeVerteidiger.get(1) < wuerfeAngreifer.get(2))))) {
			 verluste.add(1);
			 verluste.add(1);	
		} else if((wuerfeVerteidiger.size() == 1) && (wuerfeVerteidiger.get(0) >= wuerfeAngreifer.get(1))) {
			 verluste.add(1);
			 verluste.add(0);
		} else if((wuerfeVerteidiger.size() == 2) && (wuerfeVerteidiger.get(0) >= wuerfeAngreifer.get(1)) && (wuerfeVerteidiger.get(1) >= wuerfeAngreifer.get(2))) {
			 verluste.add(2);
			 verluste.add(0);
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
		int einheiten = 0;
		int anzahl = 1;
		Spieler speicher;
		
		einheiten = weltVw.besitztLaender(spieler).size()/3;
		if (einheiten < 3) {
			einheiten = 3;
		}
			for (Kontinent k : weltVw.getKontinentenListe()){
				for (int i=1;i<k.getLaender().size();i++){
					
					if(k.getLaender().get(i).getBesitzer() == k.getLaender().get(i-1).getBesitzer())
					{
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
	
	/**
	 * Setzt die nächste Phase
	 */
	public void nextTurn(){
		switch(Phase){
			case VERSCHIEBEN:
				Phase = phasen.VERTEILEN;
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
		VERTEILEN,ANGRIFF,VERSCHIEBEN
	}
	

	/**
	 * Gibt die aktuelle Phase zurück
	 * @return Phase
	 */
	public phasen getTurn(){
		return Phase;
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
		if(weltVw.stringToLand(land) == null){
			throw new KannLandNichtBenutzenException(land," existiert nicht");
		}else if(!weltVw.stringToLand(land).getBesitzer().equals(spieler)){
			throw new KannLandNichtBenutzenException(land," geh\u00F6rt dir nicht");	
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
		if(weltVw.stringToLand(land).getEinheiten() < 2){
			throw new NichtGenugEinheitenException(land, " hat zu wenig Einheiten.");
		}else if(weltVw.stringToLand(land).getEinheiten() <= einheiten){
			throw new NichtGenugEinheitenException(land, " hat nicht so viele Einheiten.");
		}else if(einheiten < 1){
			throw new NichtGenugEinheitenException(land, " kann nicht so wenig Einheiten verschicken.");
		}else{
			return true;
		}
	}
	
//	/**
//	 * Erstellt die Missionsliste
//	 */
//	public void missionsListeErstellen()
//	{
//		missionsListe.add(new Mission("Befreien Sie Nordamerika und Afrika!",null));
//		missionsListe.add(new Mission("Befreien Sie Nordamerika und Australien!",null));
//		missionsListe.add(new Mission("Befreien Sie 24 L\u00E4nder Ihrer Wahl!",null));
//		missionsListe.add(new Mission("Befreien Sie 18 L\u00E4nder und setzen Sie in jedes Land mindestens 2 Armeen!",null));
//		missionsListe.add(new Mission("Befreien Sie Europa, S\u00FCdamerika und einen dritten Kontinent Ihrer Wahl!",null));
//		missionsListe.add(new Mission("Befreien Sie Europa, Australien und einen dritten Kontinent Ihrer Wahl!",null));
//		missionsListe.add(new Mission("Befreien Sie Asien und S\u00FCdamerika!",null));
//		missionsListe.add(new Mission("Befreien Sie Afrika und Asien!",null));
//		missionsListe.add(new Mission("Befreien Sie alle L\u00E4nder von den roten Armeen!",null));
//
//	}
	/**
	 * Gibt alle eigenen NAchbarländer als Tabelle zurück
	 * @param land
	 * @param spieler
	 * @return String
	 */
	public List<Land> moeglicheVerschiebeZiele(Land land, Spieler spieler){
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
	public boolean benutzeLaender(Land land) throws LandBereitsBenutztException{
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
	public void landBenutzen(Land land){
		benutzteLaender.add(land);
	}
	
	/**
	 * Löscht die Liste, der Länder, die in einer Runde für einen Angriff benutzt wurden
	 */
	public void benutzteLaenderLoeschen(){
		benutzteLaender.clear();
	}
	
	/**
	 * Gibt die Liste der Länder zurück, die für einen Angriff benutzt wurden
	 * @return List<Land>
	 */
	public List<Land> getBenutzteLaenderListe(){
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
			throw new KannEinheitenNichtVerschiebenException("nicht so viele Einheiten verschieben");
		}else if(einheiten < 1){
			throw new KannEinheitenNichtVerschiebenException("nicht so wenig Einheiten verschieben");
		}else{
			return true;
		}
	}
//	public void missionenVerteilen(){
//		List<Mission> speicher = new Vector<Mission>();
//		int random;
//		for(Mission m : this.missionsListe){
//			speicher.add(m);
//		}
//		for(Spieler s : spielerVw.getSpielerList()){
//			random = (int)(Math.random() * speicher.size());
//			for(Mission m : this.missionsListe){
//				if(m.getBeschreibung().equals(speicher.get(random).getBeschreibung())){
//					m.setMissionSpieler(s);
//				}
//			}
//			speicher.remove(random);
//		}
//		
//	}
//	public String missionAusgeben(Spieler spieler){
//		String ausgabe = "";
//		for(Mission m : this.missionsListe){
//			if(m.getMissionSpieler() != null && m.getMissionSpieler().equals(spieler)){
//				ausgabe = m.getBeschreibung();
//			}
//		}
//		return ausgabe;
//	}
	public List<String> willkommenNachricht(){
		List<String> willkommen = new Vector<String>();
		willkommen.add("W");
		willkommen.add("I");
		willkommen.add("L");
		willkommen.add("L");
		willkommen.add("K");
		willkommen.add("O");
		willkommen.add("M");
		willkommen.add("M");
		willkommen.add("E");
		willkommen.add("N");
		willkommen.add(" ");
		willkommen.add("B");
		willkommen.add("E");
		willkommen.add("I");
		willkommen.add(" ");
		willkommen.add("R");
		willkommen.add("I");
		willkommen.add("S");
		willkommen.add("I");
		willkommen.add("K");
		willkommen.add("O");
		return willkommen;
	}
	
	public List<Land> getSpielerLaender(Spieler spieler){
		List<Land> rueckgabeLaender = new Vector<Land>();
		for(Land l : weltVw.getLaenderListe()){
			if(l.getBesitzer().equals(spieler)){
				rueckgabeLaender.add(l);
			}
		}
		return rueckgabeLaender;
	}
	public void spielSpeichern(String datei) throws IOException{
		pm.schreibkanalOeffnen(datei);
		pm.spielSpeichern(weltVw.getLaenderListe(), spielerVw.getSpielerList(), Phase+"", spielerVw.getAktiverSpielerNummer(),missionVw.getMissionsListe() );
		pm.close();
	}
	public void spielLaden(String datei) throws IOException, SpielerExistiertBereitsException {
		pm.lesekanalOeffnen(datei);
		String phase = pm.spielstandLaden();
		String spieler = "";
		String land = "";
		String kuerzel = "";
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
				for(Spieler s : spielerVw.getSpielerList()){
					if(s.getName().equals(spieler)){
						weltVw.getLaenderListe().add(new Land(land,s,einheiten,kuerzel));
					}
				}
			}	
		}while(land.length() != 0);
		int spielerNummer = Integer.parseInt(pm.spielstandLaden());
		spielerVw.setAktiverSpieler(spielerNummer);
		pm.close();
	}
	public boolean landZumAngreifen(Spieler spieler) throws KeinLandZumAngreifenException{
		List<Land> nachbarn = new Vector<Land>();
		for(Land l : weltVw.getLaenderListe()){
			if(l.getBesitzer().equals(spieler) && l.getEinheiten() > 1){
				nachbarn = this.moeglicheAngriffsziele(l);
				if(nachbarn.size() > 0){
					return true;
				}
			}
		}
		throw new KeinLandZumAngreifenException("Du hast kein Land mit dem du angreifen kannst");
	}
	public boolean landZumAngreifen(Spieler spieler,Land land) throws KeinLandZumAngreifenException{
		List<Land> nachbarn = new Vector<Land>();
		nachbarn = this.moeglicheAngriffsziele(land);
			if(nachbarn.size() > 0){
				return true;
			}
		throw new KeinLandZumAngreifenException("Dieses Land hat keine feindlichen Nachbarn");
	}
	
	public boolean spielerRaus(Spieler spieler){
		for(Land l : weltVw.getLaenderListe()){
			if(l.getBesitzer().equals(spieler)){
				return false;
			}
		}
		System.out.println("Hier bin ich");
		spielerVw.getSpielerList().remove(spieler);
		return true;
	}

}
