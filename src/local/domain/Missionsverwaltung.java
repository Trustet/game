package local.domain;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

import local.persistence.FilePersistenceManager;
import local.valueobjects.*;

public class Missionsverwaltung {
	public List<Mission> missionsListe = new Vector<Mission>();
	private FilePersistenceManager pm = new FilePersistenceManager();
	/**
	 * Erstellt die Missionsliste
	 * @param spielerListe 
	 * @param kontinentenListe 
	 * @param laenderListe 
	 * @throws IOException 
	 */
	public void missionsListeErstellen(List<Land> laenderListe, List<Kontinent> kontinentenListe, List<Spieler> spielerListe) {
//		missionsListe.add(new LaenderMission(1,null,3,2,laenderListe));
		
//		missionsListe.add(new LaenderMission(2,null,3,2,laenderListe));
		Spieler platzhalterSpieler = new Spieler("Platzhalter"); //weil er sonst nullpointer bei abfrage auf nicht verwendete missionen hat
		//Befreien Sie Nordamerika und Afrika
		List<Kontinent> mission1Kontinente = new Vector<Kontinent>();
		mission1Kontinente.add(kontinentenListe.get(3));
		mission1Kontinente.add(kontinentenListe.get(5));
		missionsListe.add(new KontinentenMission(1,platzhalterSpieler,mission1Kontinente));
		//Befreien Sie Nordamerika und Australien
		List<Kontinent> mission2Kontinente = new Vector<Kontinent>();
		mission2Kontinente.add(kontinentenListe.get(2));
		mission2Kontinente.add(kontinentenListe.get(5));
		missionsListe.add(new KontinentenMission(2,platzhalterSpieler,mission2Kontinente));
		//Befreien Sie Asien und Südamerika
		List<Kontinent> mission3Kontinente = new Vector<Kontinent>();
		mission3Kontinente.add(kontinentenListe.get(1));
		mission3Kontinente.add(kontinentenListe.get(4));
		missionsListe.add(new KontinentenMission(3,platzhalterSpieler,mission3Kontinente));
		//Befreien Sie Afrika und Asien
		List<Kontinent> mission4Kontinente = new Vector<Kontinent>();
		mission4Kontinente.add(kontinentenListe.get(1));
		mission4Kontinente.add(kontinentenListe.get(3));
		missionsListe.add(new KontinentenMission(4,platzhalterSpieler,mission4Kontinente));
		
		List<Kontinent> mission5Kontinente = new Vector<Kontinent>();
		mission5Kontinente.add(kontinentenListe.get(1));
		mission5Kontinente.add(kontinentenListe.get(3));
		missionsListe.add(new KontinentenMission(5,platzhalterSpieler,mission5Kontinente));
		
		List<Kontinent> mission6Kontinente = new Vector<Kontinent>();
		mission6Kontinente.add(kontinentenListe.get(1));
		mission6Kontinente.add(kontinentenListe.get(3));
		missionsListe.add(new KontinentenMission(6,platzhalterSpieler,mission6Kontinente));
//		Befreien Sie 24 Laender Ihrer Wahl
		missionsListe.add(new LaenderMission(7,platzhalterSpieler, 24, 1, laenderListe));
//		Befreien Sie 18 Laender und setzen Sie in jedes Land mindestens 2 Armeen
		missionsListe.add(new LaenderMission(8,platzhalterSpieler, 18, 2, laenderListe));
//		Befreien Sie alle Länder von den roten Armeen
		missionsListe.add(new SpielerMission(9,platzhalterSpieler,platzhalterSpieler,spielerListe));
		missionsListe.add(new SpielerMission(10,platzhalterSpieler,platzhalterSpieler,spielerListe));
		//Missionen abspeichern
	}
		public void missionenVerteilen(List<Spieler> spielerListe){
		List<Mission> speicher= new Vector<Mission>();
		for(Mission m : this.missionsListe){
			speicher.add(m);
		}
		
		for(Spieler s : spielerListe){
			Mission spielerMission = null;
			int random = (int)(Math.random() * speicher.size());
			for(Mission m : this.missionsListe){
				if(m.getId() == speicher.get(random).getId()){
					spielerMission = m;
				}
			}
					if(spielerMission instanceof LaenderMission){
						spielerMission.setSpieler(s);
						
						//Länder des Spielers setzen
						//m.setLaender(/*TODO Länder des Spielers*/null);
					} else if(spielerMission instanceof SpielerMission) {

							boolean gegnerGefunden = false;
							do{
								int random2 = (int)(Math.random() * spielerListe.size());
								System.out.println(spielerListe.get(random2).getName());
								if(!s.equals(spielerListe.get(random2))){
									spielerMission.setSpieler2(spielerListe.get(random2));
									gegnerGefunden = true;
								}
							}while(!gegnerGefunden);
							System.out.println("Draussen");
//						} while(gegner==false);
						spielerMission.setSpieler(s);
						System.out.println(spielerMission.getBeschreibung());
						((SpielerMission) spielerMission).resetBeschreibung();
						
					} else if(spielerMission instanceof KontinentenMission){
						spielerMission.setSpieler(s);
					}
					speicher.remove(random);
					
				}
//			}
//		}
		
	}
	public String missionAusgeben(Spieler spieler){
		String ausgabe = "";
		for(Mission m : this.missionsListe){
			if(m.getSpieler() != null && m.getSpieler().equals(spieler)){
				ausgabe = m.getBeschreibung();
			}
		}
		return ausgabe;
	}
	public List<Mission> getMissionsListe(){
		return this.missionsListe;
	}
	public Mission getSpielerMission(Spieler spieler){
		for(Mission m : missionsListe){
			if(m.getSpieler().equals(spieler)){
				return m;
			}
		}
		return null;
	}
	
	public void missionLaden(List<Land> laenderListe, List<Kontinent> kontinentenListe, List<Spieler> spielerListe, Spieler spieler, Spieler spieler2, int id) {
		if(id == 1) {
			//Befreien Sie Nordamerika und Afrika
			List<Kontinent> mission1Kontinente = new Vector<Kontinent>();
			mission1Kontinente.add(kontinentenListe.get(3));
			mission1Kontinente.add(kontinentenListe.get(5));
			missionsListe.add(new KontinentenMission(1,spieler,mission1Kontinente));
		} else if(id == 2) {
			//Befreien Sie Nordamerika und Australien
			List<Kontinent> mission2Kontinente = new Vector<Kontinent>();
			mission2Kontinente.add(kontinentenListe.get(2));
			mission2Kontinente.add(kontinentenListe.get(5));
			missionsListe.add(new KontinentenMission(2,spieler,mission2Kontinente));
		} else if(id == 3) {
			//Befreien Sie Asien und Südamerika
			List<Kontinent> mission3Kontinente = new Vector<Kontinent>();
			mission3Kontinente.add(kontinentenListe.get(1));
			mission3Kontinente.add(kontinentenListe.get(4));
			missionsListe.add(new KontinentenMission(3,spieler,mission3Kontinente));
		} else if(id == 4) {
			//Befreien Sie Afrika und Asien
			List<Kontinent> mission4Kontinente = new Vector<Kontinent>();
			mission4Kontinente.add(kontinentenListe.get(1));
			mission4Kontinente.add(kontinentenListe.get(3));
			missionsListe.add(new KontinentenMission(4,spieler,mission4Kontinente));
		} else if(id == 5) {
			List<Kontinent> mission5Kontinente = new Vector<Kontinent>();
			mission5Kontinente.add(kontinentenListe.get(1));
			mission5Kontinente.add(kontinentenListe.get(3));
			missionsListe.add(new KontinentenMission(5,spieler,mission5Kontinente));
		} else if(id == 6) {
			List<Kontinent> mission6Kontinente = new Vector<Kontinent>();
			mission6Kontinente.add(kontinentenListe.get(1));
			mission6Kontinente.add(kontinentenListe.get(3));
			missionsListe.add(new KontinentenMission(6,spieler,mission6Kontinente));
		} else if(id == 7) {
			//Befreien Sie 24 Laender Ihrer Wahl
			missionsListe.add(new LaenderMission(7,spieler, 24, 1, laenderListe));
		} else if(id == 8) {
			//Befreien Sie 18 Laender und setzen Sie in jedes Land mindestens 2 Armeen
			missionsListe.add(new LaenderMission(8,spieler, 18, 2, laenderListe));
		} else if(id == 9) {
			//Befreien Sie alle Länder von den roten Armeen
			missionsListe.add(new SpielerMission(9,spieler,spieler2,spielerListe));
		} else if(id == 10) {
			missionsListe.add(new SpielerMission(10,spieler,spieler2,spielerListe));
		}
	}
}
