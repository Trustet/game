package local.domain;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

import local.persistence.FilePersistenceManager;
import local.valueobjects.*;

public class Missionsverwaltung {
	public List<MissionAlt> missionsListe = new Vector<MissionAlt>();
	private FilePersistenceManager pm = new FilePersistenceManager();
	/**
	 * Erstellt die Missionsliste
	 * @throws IOException 
	 */
	public void missionsListeErstellen() throws IOException{
		pm.lesekanalOeffnen("Missionen.txt");
		MissionAlt mission;
		do{
			mission = pm.ladeMission();
			if(mission != null){	
				missionsListe.add(mission);
			}
		}while(mission != null);
		pm.close();
//		missionsListe.add(new MissionAlt("Befreien Sie Nordamerika und Afrika!",null));
//		missionsListe.add(new MissionAlt("Befreien Sie Nordamerika und Australien!",null));
//		missionsListe.add(new MissionAlt("Befreien Sie 24 L\u00E4nder Ihrer Wahl!",null));
//		missionsListe.add(new MissionAlt("Befreien Sie 18 L\u00E4nder und setzen Sie in jedes Land mindestens 2 Armeen!",null));
//		missionsListe.add(new MissionAlt("Befreien Sie Europa, S\u00FCdamerika und einen dritten Kontinent Ihrer Wahl!",null));
//		missionsListe.add(new MissionAlt("Befreien Sie Europa, Australien und einen dritten Kontinent Ihrer Wahl!",null));
//		missionsListe.add(new MissionAlt("Befreien Sie Asien und S\u00FCdamerika!",null));
//		missionsListe.add(new MissionAlt("Befreien Sie Afrika und Asien!",null));
//		missionsListe.add(new MissionAlt("Befreien Sie alle L\u00E4nder von den roten Armeen!",null));
	}
	public void missionenVerteilen(List<Spieler> spielerListe){
		List<MissionAlt> speicher = new Vector<MissionAlt>();
		int random;
		for(MissionAlt m : this.missionsListe){
			speicher.add(m);
		}
		for(Spieler s : spielerListe){
			random = (int)(Math.random() * speicher.size());
			for(MissionAlt m : this.missionsListe){
				if(m.getBeschreibung().equals(speicher.get(random).getBeschreibung())){
					m.setMissionSpieler(s);
				}
			}
			speicher.remove(random);
		}
		
	}
	public String missionAusgeben(Spieler spieler){
		String ausgabe = "";
		for(MissionAlt m : this.missionsListe){
			if(m.getMissionSpieler() != null && m.getMissionSpieler().equals(spieler)){
				ausgabe = m.getBeschreibung();
			}
		}
		return ausgabe;
	}
	public List<MissionAlt> getMissionsListe(){
		return this.missionsListe;
	}
}
