package local.domain;
import java.util.List;
import java.util.Vector;

import local.valueobjects.Spieler;

public class Spielerverwaltung {

	private Weltverwaltung weltVw;
	private List<Spieler> spielerListe = new Vector<Spieler>();

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

	public void setWeltverwaltung(Weltverwaltung weltVw) {

		this.weltVw = weltVw;
	}

	
}
