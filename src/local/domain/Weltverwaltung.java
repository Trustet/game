package local.domain;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

import local.domain.exceptions.KeinGegnerException;
import local.domain.exceptions.KeinNachbarlandException;
import local.domain.exceptions.LandExistiertNichtException;
import local.persistence.FilePersistenceManager;
import local.valueobjects.*;

public class Weltverwaltung {

	private static int laenderAnzahl = 42;
	private boolean[][] laenderAufteilung;
	private List<Land> laenderListe = new Vector<Land>();
	private List<Kontinent> kontinentenListe = new Vector<Kontinent>();
	private FilePersistenceManager pm = new FilePersistenceManager();

	/**
	 * @param spielerVw
	 * @throws IOException 
	 */
	public Weltverwaltung() {
		laenderAufteilung = new boolean[laenderAnzahl][laenderAnzahl];
		
		for(int spalte = 0; spalte < laenderAnzahl;spalte++) {
			for(int zeile = 0; zeile < laenderAnzahl;zeile++) {
				laenderAufteilung[spalte][zeile] = false;
			}
		}
	}

	public void erstellen(){
		this.verbindungenErstellen();
		this.kontinenteErstellen();
	}
	/**
	 * Teilt die Länder auf
	 * @param anzahlSpieler
	 * @param spielerVw
	 * @param weltVw
	 */

		public void laenderAufteilen(List<Spieler> spielerListe){
			List<Land> laenderWahl = new Vector<Land>();
			for(Land ls :this.getLaenderListe()){
				laenderWahl.add(ls);
			}
			int random;
			while(laenderWahl.size() > 0){
				for(Spieler s : spielerListe){
					if(laenderWahl.size() > 0){
						random = (int)(Math.random() * laenderWahl.size());
						String land = laenderWahl.get(random).getName();
						for(Land l : this.getLaenderListe()){
							if(l.getName().equals(land)){
								l.setBesitzer(s);
							}
						}
						laenderWahl.remove(random);
					}
				}
			}
		}
		//TEST SPIELER 1 kriegt ganz EUROPA zu Beginn
		/*
		for(int x = 0;x < 7;x++)
		{
			this.getLaenderListe().get(x).setBesitzer(spielerListe.get(0));
		}
		*/
	//}

	public void speicherLaender() throws IOException{
		pm.schreibkanalOeffnen("saveLaender.txt");
		for(Land l : laenderListe){
			pm.speichereWelt(l);
		}
		pm.close();
	}
	
	/**
	 * erstellt Länder
	 */
	public void laenderErstellen()	throws IOException{
		pm.lesekanalOeffnen("./loading/Welt.txt");
		Land land;
		do{
				land = pm.ladeLand();
			if(land != null){	
				laenderListe.add(land);
			}
		}while(land != null);
		pm.close();
//		Spieler leer = new Spieler("Unbekannt");
//		//42
//		//Europa 7
//		laenderListe.add(new Land("Island",leer,1,"is"));
//		laenderListe.add(new Land("Skandinavien",leer,1,"sk"));
//		laenderListe.add(new Land("Ukraine",leer,1,"uk"));
//		laenderListe.add(new Land("Nord-Europa",leer,1,"ne"));
//		laenderListe.add(new Land("Sud-Europa",leer,1,"se"));
//		laenderListe.add(new Land("West-Europa",leer,1,"we"));
//		laenderListe.add(new Land("Grossbritannien",leer,1,"gr"));
//		
//		//Asien 12
//		laenderListe.add(new Land("Ural",leer,1,"ur"));
//		laenderListe.add(new Land("Sibirien",leer,1,"si"));
//		laenderListe.add(new Land("Jakutien",leer,1,"ja"));
//		laenderListe.add(new Land("Kamtschatka",leer,1,"ka"));
//		laenderListe.add(new Land("Irtusk",leer,1,"ir"));
//		laenderListe.add(new Land("Mongolei",leer,1,"mo"));
//		laenderListe.add(new Land("Japan",leer,1,"ja"));
//		laenderListe.add(new Land("Afghanistan",leer,1,"af"));
//		laenderListe.add(new Land("China",leer,1,"ch"));
//		laenderListe.add(new Land("Mittlerer Osten",leer,1,"mo"));
//		laenderListe.add(new Land("Indien",leer,1,"in"));
//		laenderListe.add(new Land("Siam",leer,1,"si"));
//		
//		//Australien 4
//		laenderListe.add(new Land("Neu-Guinea",leer,1,"ng"));
//		laenderListe.add(new Land("Indonesien",leer,1,"in"));
//		laenderListe.add(new Land("Ostaustralien",leer,1,"os"));
//		laenderListe.add(new Land("Westaustralien",leer,1,"we"));
//		
//		//Afrika 6
//		laenderListe.add(new Land("Ägypten",leer,1,"äg"));
//		laenderListe.add(new Land("Ostafrika",leer,1,"oa"));
//		laenderListe.add(new Land("Kongo",leer,1,"ko"));
//		laenderListe.add(new Land("Südafrika",leer,1,"sü"));
//		laenderListe.add(new Land("Nordwestafrika",leer,1,"no"));
//		laenderListe.add(new Land("Madagaskar",leer,1,"ma"));
//		
//		//Südamerika 4
//		laenderListe.add(new Land("Argentinien",leer,1,"ar"));
//		laenderListe.add(new Land("Peru",leer,1,"pe"));
//		laenderListe.add(new Land("Brasilien",leer,1,"br"));
//		laenderListe.add(new Land("Venezuela",leer,1,"ve"));
//		
//		//Nordamerika 9
//		laenderListe.add(new Land("Mittelamerika",leer,1,"ma"));
//		laenderListe.add(new Land("Oststaaten",leer,1,"os"));
//		laenderListe.add(new Land("Weststaaten",leer,1,"ws"));
//		laenderListe.add(new Land("Alberta",leer,1,"al"));
//		laenderListe.add(new Land("Ontario",leer,1,"on"));
//		laenderListe.add(new Land("Quebec",leer,1,"qu"));
//		laenderListe.add(new Land("Alaska",leer,1,"al"));
//		laenderListe.add(new Land("Nordwest-Territorium",leer,1,"nt"));
//		laenderListe.add(new Land("Grönland",leer,1,"gl"));
	}
	
	/**
	 * erstellt Verbindungen zwischen den Ländern
	 */
	private void verbindungenErstellen() {
		verbindungEinfuegen(0,new int[] {1,6,41});
		verbindungEinfuegen(1,new int[] {0,2,3,6});		
		verbindungEinfuegen(2,new int[] {1,3,4,7,14,16});		
		verbindungEinfuegen(3,new int[] {1,2,4,5,6});		
		verbindungEinfuegen(4,new int[] {2,3,5,16,23,27});		
		verbindungEinfuegen(5,new int[] {3,4,6,27});		
		verbindungEinfuegen(6,new int[] {0,1,3,5});	
		
		verbindungEinfuegen(7,new int[] {2,8,14,15});		
		verbindungEinfuegen(8,new int[] {7,9,11,12,15});		
		verbindungEinfuegen(9,new int[] {8,10,11});		
		verbindungEinfuegen(10,new int[] {9,11,12,13,39});		
		verbindungEinfuegen(11,new int[] {8,9,10,12});	
		verbindungEinfuegen(12,new int[] {8,10,11,13,15});		
		verbindungEinfuegen(13,new int[] {10,12});		
		verbindungEinfuegen(14,new int[] {7,15,16,17,2});		
		verbindungEinfuegen(15,new int[] {7,8,12,14,17,18});		
		verbindungEinfuegen(16,new int[] {2,4,14,17,23,24});		
		verbindungEinfuegen(17,new int[] {14,15,16,18});		
		verbindungEinfuegen(18,new int[] {15,17,20});
		
		verbindungEinfuegen(19,new int[] {20,21,22});		
		verbindungEinfuegen(20,new int[] {18,19,21,22});		
		verbindungEinfuegen(21,new int[] {19,22});
		verbindungEinfuegen(22,new int[] {19,20,21});	
		
		verbindungEinfuegen(23,new int[] {4,16,24,27});		
		verbindungEinfuegen(24,new int[] {16,23,25,26,27,28});		
		verbindungEinfuegen(25,new int[] {24,26,27});		
		verbindungEinfuegen(26,new int[] {24,25,28});		
		verbindungEinfuegen(27,new int[] {4,5,23,24,25,31});		
		verbindungEinfuegen(28,new int[] {24,26});
		
		verbindungEinfuegen(29,new int[] {30,31});		
		verbindungEinfuegen(30,new int[] {29,31,32});		
		verbindungEinfuegen(31,new int[] {27,29,30,32});	
		verbindungEinfuegen(32,new int[] {30,31,33});
		
		verbindungEinfuegen(33,new int[] {32,34,35});		
		verbindungEinfuegen(34,new int[] {33,35,37,38});		
		verbindungEinfuegen(35,new int[] {33,34,36,37});		
		verbindungEinfuegen(36,new int[] {35,37,39,40});		
		verbindungEinfuegen(37,new int[] {34,35,36,38,40,41});		
		verbindungEinfuegen(38,new int[] {34,37,41});		
		verbindungEinfuegen(39,new int[] {10,36,40});		
		verbindungEinfuegen(40,new int[] {36,37,39,41});		
		verbindungEinfuegen(41,new int[] {0,37,38,40});
	}
	
	/**
	 * Erstellt alle Kontinente
	 */
	public void kontinenteErstellen() {
		List<Land> europa = new Vector<Land>();
		
		for(int i = 0;i < 7;i++) {
			europa.add(laenderListe.get(i));
		}
	
		kontinentenListe.add(new Kontinent("Europa",europa));
		List<Land> asien = new Vector<Land>();
		
		for (int i=7;i < 19;i++){
			asien.add(laenderListe.get(i));
		}
		kontinentenListe.add(new Kontinent("Asien",asien));
		List<Land> australien= new Vector<Land>();
		for (int i=19;i < 23;i++){
			australien.add(laenderListe.get(i));
		}
		kontinentenListe.add(new Kontinent("Australien",australien));
		List<Land> afrika = new Vector<Land>();
		for (int i=23;i < 29;i++){
			afrika.add(laenderListe.get(i));
		}
		kontinentenListe.add(new Kontinent("Afrika",afrika));
		List<Land> suedamerika = new Vector<Land>();
		for (int i=29;i < 33;i++){
			suedamerika.add(laenderListe.get(i));
		}
		kontinentenListe.add(new Kontinent("Suedamerika",suedamerika));
		List<Land> nordamerika = new Vector<Land>();
		for (int i=33;i < 42;i++){
			nordamerika.add(laenderListe.get(i));
		}
		kontinentenListe.add(new Kontinent("Nordamerika",nordamerika));
	}
	
	
	/**
	 * @param indexLand1
	 * @param nachbarlaender
	 */
	public void verbindungEinfuegen(int indexLand1,int[] nachbarlaender) {
		for(int nachbarland : nachbarlaender) {
			laenderAufteilung[indexLand1][nachbarland] = true;
			laenderAufteilung[nachbarland][indexLand1] = true;
		}
	}
	
	/**
	 * @param land
	 * @return
	 */
	public int indexVonLand(Land land) {
		for(int i = 0;i < laenderListe.size();i++) {
			if(laenderListe.get(i).equals(land)) {
				return i;
			}
		}
		//fehlermeldung
		return 9999;
	}
	
	/**
	 * @param index
	 * @return Land
	 */
	public Land landVonIndex(int index) {
		return laenderListe.get(index);
	}
	
	/**
	 * @param land
	 * @return
	 */
	public List<Land> getNachbarLaender(Land land) {
		int index = indexVonLand(land);
		List<Land> nachbarLaender = new Vector<Land>();
		
		for (int i = 0;i < laenderAufteilung[index].length;i++) {
			if(laenderAufteilung[index][i] == true) {
				nachbarLaender.add(laenderListe.get(i));
			}
		}
		return nachbarLaender;			
	}
	
	/**
	 * @param indexLand1
	 * @param indexLand2
	 * @return boolean
	 */
	public boolean istNachbarland(int indexLand1, int indexLand2) {
		if (laenderAufteilung[indexLand1][indexLand2] == true) {
			return true;
		}
		return false;
	}
	
	/**
	 * @return List<Land>
	 */
	public List<Land> getLaenderListe() {
		return laenderListe;
	}

	/**
	 * @param laenderListe
	 */
	public void setLaenderListe(List<Land> laenderListe) {
		this.laenderListe = laenderListe;
	}

	/**
	 * @return boolean[][]
	 */
	public boolean[][] getLaenderAufteilung() {
		return laenderAufteilung;
	}

	/**
	 * @param laenderAufteilung
	 */
	public void setLaenderAufteilung(boolean[][] laenderAufteilung) {
		this.laenderAufteilung = laenderAufteilung;
	}

	/**
	 * @return List<Kontinent>
	 */
	public List<Kontinent> getKontinentenListe() {
		return kontinentenListe;
	}



	/**
	 * @param angriffsLandString
	 * @return Land
	 */
	public Land stringToLand(String angriffsLandString) {
		for(Land land : laenderListe) {
			if(land.getName().equals(angriffsLandString)) {
				return land;
			}
		}
		return null;
	}
	
	/**
	 * @param spieler
	 * @return List<Land>
	 */
	public List<Land> besitztLaender(Spieler spieler) {
		List<Land> laender = new Vector<Land>();
		
		for(Land land : this.getLaenderListe()) {
			if(spieler.equals(land.getBesitzer())) {
					laender.add(land);
				}
		}
		return laender;
	}
	
	
	/**
	 * Listet alle Länder auf mit denen man angreifen kann
	 * @param spieler
	 * @return String
	 */
	public List<Land> eigeneAngriffsLaender(Spieler spieler){
		List<Land> rueckgabeLaender = new Vector<Land>();
		for(Land land : this.getLaenderListe()) {
			if(spieler.equals(land.getBesitzer()) && land.getEinheiten() > 1) {
				rueckgabeLaender.add(land);
			}
		}
		return rueckgabeLaender;
	}

	/**
	 * Überprüft ob ein Land existiert
	 * @param land
	 * @return boolean
	 * @throws LandExistiertNichtException
	 */
	public boolean landExistiert(String land) throws LandExistiertNichtException{
		if(this.stringToLand(land) == null){
			throw new LandExistiertNichtException(land);
		}else{
			return true;
		}
		
	}
	/**
	 * Überprüft ob ein Land dem Gegner gehört
	 * @param land
	 * @param spieler
	 * @return boolean
	 * @throws KeinGegnerException
	 */
	public boolean istGegner(String land,Spieler spieler) throws KeinGegnerException{
		if(this.stringToLand(land).getBesitzer().equals(spieler)){
			throw new KeinGegnerException(land);
		}else{
			return true;
		}
		
	}
	/**
	 * Listet alle Länder auf, auf die man verschieben kann
	 * @param spieler
	 * @param land
	 * @return String
	 */
	public List<Land> eigeneVerschiebeLaender(Spieler spieler, List<Land> land){
		List<Land> rueckgabeLaender = new Vector<Land>();
					
		for(Land l : this.getLaenderListe()) {
			if(spieler.equals(l.getBesitzer()) && l.getEinheiten() > 1 && !land.contains(l)) {
				rueckgabeLaender.add(l);
				}
			}
		return rueckgabeLaender;
	}
	/**
	 * Gibt die Namen und Einheiten von zwei Länder aus
	 * @param erstesLand
	 * @param zweitesLand
	 * @return String
	 */
	public String einheitenAusgabe(Land erstesLand, Land zweitesLand){
		String ausgabe = "Das Land " + erstesLand.getName() + " hat " + erstesLand.getEinheiten() + " Einheiten \n Das Land " + zweitesLand.getName() + " hat " + zweitesLand.getEinheiten() + " Einheiten";
		return ausgabe;
	}
}
