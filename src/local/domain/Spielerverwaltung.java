package local.domain;
import local.valueobjects.Spieler;

public class Spielerverwaltung {

private Spieler[] spieler = new Spieler[6];
private int anzahlSpieler = 0;

	public Spieler getSpieler(int index) {
		return this.spieler[index-1];
	}

	public void neuerSpieler(String name){
		spieler[anzahlSpieler] = new Spieler(name);
		anzahlSpieler++;
	}
	
	public String zeigeName(int index){
		return spieler[index-1].getName();
	}

	
}
