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
	public void missionsListeErstellen(List<Land> laenderListe, List<Kontinent> kontinentenListe, List<Spieler> spielerListe) throws IOException{
		//Befreien Sie Nordamerika und Afrika
		List<Kontinent> mission1Kontinente = new Vector<Kontinent>();
		mission1Kontinente.add(kontinentenListe.get(4));
		mission1Kontinente.add(kontinentenListe.get(6));
		List<Land> mission1Laender = new Vector<Land>();
		mission1Laender.addAll(mission1Kontinente.get(0).getLaender());
		mission1Laender.addAll(mission1Kontinente.get(1).getLaender());
		missionsListe.add(new KontinentenMission(null,mission1Kontinente,mission1Laender));
		//Befreien Sie Nordamerika und Australien
		List<Kontinent> mission2Kontinente = new Vector<Kontinent>();
		mission2Kontinente.add(kontinentenListe.get(3));
		mission2Kontinente.add(kontinentenListe.get(6));
		List<Land> mission2Laender = new Vector<Land>();
		mission2Laender.addAll(mission2Kontinente.get(0).getLaender());
		mission2Laender.addAll(mission2Kontinente.get(1).getLaender());
		missionsListe.add(new KontinentenMission(null,mission2Kontinente,mission2Laender));
		//Befreien Sie Asien und Südamerika
		List<Kontinent> mission3Kontinente = new Vector<Kontinent>();
		mission3Kontinente.add(kontinentenListe.get(2));
		mission3Kontinente.add(kontinentenListe.get(5));
		List<Land> mission3Laender = new Vector<Land>();
		mission3Laender.addAll(mission3Kontinente.get(0).getLaender());
		mission3Laender.addAll(mission3Kontinente.get(1).getLaender());
		missionsListe.add(new KontinentenMission(null,mission3Kontinente,mission3Laender));
		//Befreien Sie Afrika und Asien
		List<Kontinent> mission4Kontinente = new Vector<Kontinent>();
		mission4Kontinente.add(kontinentenListe.get(2));
		mission4Kontinente.add(kontinentenListe.get(4));
		List<Land> mission4Laender = new Vector<Land>();
		mission4Laender.addAll(mission4Kontinente.get(0).getLaender());
		mission4Laender.addAll(mission4Kontinente.get(1).getLaender());
		missionsListe.add(new KontinentenMission(null,mission4Kontinente,mission4Laender));
		//Befreien Sie 24 Laender Ihrer Wahl
		//TODO hier müssen Länder des Spielers noch immer mit übergeben werden?
		missionsListe.add(new LaenderMission(null, 24, 1, null));
		//Befreien Sie 18 Laender und setzen Sie in jedes Land mindestens 2 Armeen
		//TODO hier müssen Länder des Spielers noch immer mit übergeben werden?
		missionsListe.add(new LaenderMission(null, 18, 2, null));
		//Befreien Sie alle Länder von den roten Armeen
		missionsListe.add(new SpielerMission(null, null));
	}
	public void missionenVerteilen(List<Spieler> spielerListe){
		List<Mission> speicher = new Vector<Mission>();
		int random;
		for(Mission m : this.missionsListe){
			speicher.add(m);
		}
		for(Spieler s : spielerListe){
			random = (int)(Math.random() * speicher.size());
			for(Mission m : this.missionsListe){
				if(m.getBeschreibung().equals(speicher.get(random).getBeschreibung())){
					m.setSpieler(s);
				}
			}
			speicher.remove(random);
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
}
