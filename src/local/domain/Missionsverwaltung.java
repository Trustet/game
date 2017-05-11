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
		List<Land> mission1Laender = new Vector<Land>();
		mission1Laender.addAll(mission1Kontinente.get(0).getLaender());
		mission1Laender.addAll(mission1Kontinente.get(1).getLaender());
//		missionsListe.add(new KontinentenMission(1,platzhalterSpieler,mission1Kontinente,mission1Laender));
		//Befreien Sie Nordamerika und Australien
		List<Kontinent> mission2Kontinente = new Vector<Kontinent>();
		mission2Kontinente.add(kontinentenListe.get(2));
		mission2Kontinente.add(kontinentenListe.get(5));
		List<Land> mission2Laender = new Vector<Land>();
		mission2Laender.addAll(mission2Kontinente.get(0).getLaender());
		mission2Laender.addAll(mission2Kontinente.get(1).getLaender());
//		missionsListe.add(new KontinentenMission(2,platzhalterSpieler,mission2Kontinente,mission2Laender));
		//Befreien Sie Asien und Südamerika
		List<Kontinent> mission3Kontinente = new Vector<Kontinent>();
		mission3Kontinente.add(kontinentenListe.get(1));
		mission3Kontinente.add(kontinentenListe.get(4));
		List<Land> mission3Laender = new Vector<Land>();
		mission3Laender.addAll(mission3Kontinente.get(0).getLaender());
		mission3Laender.addAll(mission3Kontinente.get(1).getLaender());
//		missionsListe.add(new KontinentenMission(3,platzhalterSpieler,mission3Kontinente,mission3Laender));
		//Befreien Sie Afrika und Asien
		List<Kontinent> mission4Kontinente = new Vector<Kontinent>();
		mission4Kontinente.add(kontinentenListe.get(1));
		mission4Kontinente.add(kontinentenListe.get(3));
		List<Land> mission4Laender = new Vector<Land>();
		mission4Laender.addAll(mission4Kontinente.get(0).getLaender());
		mission4Laender.addAll(mission4Kontinente.get(1).getLaender());
//		missionsListe.add(new KontinentenMission(4,platzhalterSpieler,mission4Kontinente,mission4Laender));
//		Befreien Sie 24 Laender Ihrer Wahl
//		missionsListe.add(new LaenderMission(5,null, 24, 1, null));
//		Befreien Sie 18 Laender und setzen Sie in jedes Land mindestens 2 Armeen
//		missionsListe.add(new LaenderMission(6,null, 18, 2, null));
//		Befreien Sie alle Länder von den roten Armeen
		missionsListe.add(new SpielerMission(7,platzhalterSpieler, platzhalterSpieler, spielerListe));
		missionsListe.add(new SpielerMission(7,platzhalterSpieler, platzhalterSpieler, spielerListe));
		missionsListe.add(new SpielerMission(7,platzhalterSpieler, platzhalterSpieler, spielerListe));
	}
	public void missionenVerteilen(List<Spieler> spielerListe){
		List<Mission> speicher = new Vector<Mission>();
		int random;
		//Missionen abspeichern
		for(Mission m : this.missionsListe){
			speicher.add(m);
		}
		
		for(Spieler s : spielerListe){
			random = (int)(Math.random() * speicher.size());
			for(Mission m : this.missionsListe){
				if(m.getId() == speicher.get(random).getId()){
					
					if(m instanceof LaenderMission){
						m.setSpieler(s);
						//Länder des Spielers setzen
						m.setLaender(/*TODO Länder des Spielers*/null);
					} else if(m instanceof SpielerMission) {
						m.setSpieler(s);
						((SpielerMission) m).setSpieler2(spielerListe.get(1));
					} else if(m instanceof KontinentenMission){
						m.setSpieler(s);
					}
					speicher.remove(random);
					break;
				}
			}
		}
		
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
}
