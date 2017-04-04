package local.domain;
import java.util.List;
import java.util.Vector;

import local.valueobjects.Land;
import local.valueobjects.Spieler;

public class Spielerverwaltung {

	private Weltverwaltung weltVw;
	private List<Spieler> spielerListe = new Vector<Spieler>();
	private Kriegsverwaltung kriegsVw;

	public Spieler getSpieler(int index) {
		return this.spielerListe.get(index-1);
	}

	public void neuerSpieler(String name){
		spielerListe.add(new Spieler(name));
	}
	
	public String zeigeName(int index){
		return spielerListe.get(index-1).getName();
	}

	public List<Spieler> getSpielerList() {
		return spielerListe;
	}

	public void setVerwaltung(Weltverwaltung weltVw, Kriegsverwaltung kriegsVw) {

		this.weltVw = weltVw;
		this.kriegsVw = kriegsVw;
	}

	public List<Land> besitztLaender(Spieler spieler)
	{
		List<Land> laender = new Vector<Land>();
		
		for(Land land : weltVw.getLaenderListe())
		{
			if(spieler.equals(land.getBesitzer()))
				{
					laender.add(land);
				}
		}
		return laender;
	}

	public int bekommtEinheiten(Spieler spieler) {
		int einheiten = 0;
		
		einheiten = besitztLaender(spieler).size()/3;
		if(einheiten < 3)
		{
			einheiten = 3;
		}
		
		return einheiten;
	}
	
}
