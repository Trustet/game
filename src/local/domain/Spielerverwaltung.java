package local.domain;
import java.util.List;
import java.util.Vector;

import local.valueobjects.Spieler;

public class Spielerverwaltung {

//private Spieler[] spieler = new Spieler[6];
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

	
}
