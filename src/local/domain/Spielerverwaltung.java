package local.domain;
import java.util.List;
import java.util.Vector;

import local.valueobjects.Land;
import local.valueobjects.Spieler;

public class Spielerverwaltung {

	private Weltverwaltung weltVw;
	private List<Spieler> spielerListe = new Vector<Spieler>();
	private Kriegsverwaltung kriegsVw;

	/**
	 * @param index
	 * @return Spieler
	 */
	public Spieler getSpieler(int index) {
		return this.spielerListe.get(index-1);
	}
	
	/**
	 * @param name
	 */
	public void neuerSpieler(String name){
		spielerListe.add(new Spieler(name));
	}
	
	/**
	 * @param index
	 * @return String
	 */
	public String zeigeName(int index){
		return spielerListe.get(index-1).getName();
	}

	/**
	 * @return List<Spieler>
	 */
	public List<Spieler> getSpielerList() {
		return spielerListe;
	}

	/**
	 * @param weltVw
	 * @param kriegsVw
	 */
	public void setVerwaltung(Weltverwaltung weltVw, Kriegsverwaltung kriegsVw) {
		this.weltVw = weltVw;
		this.kriegsVw = kriegsVw;
	}

	/**
	 * @param spieler
	 * @return List<Land>
	 */
	public List<Land> besitztLaender(Spieler spieler) {
		List<Land> laender = new Vector<Land>();
		
		for(Land land : weltVw.getLaenderListe()) {
			if(spieler.equals(land.getBesitzer())) {
					laender.add(land);
				}
		}
		return laender;
	}

	/**
	 * @param spieler
	 * @return int
	 */
	public int bekommtEinheiten(Spieler spieler) {
		int einheiten = 0;
		
		einheiten = besitztLaender(spieler).size()/3;
		if(einheiten < 3) {
			einheiten = 3;
		}
		return einheiten;
	}
	
}
