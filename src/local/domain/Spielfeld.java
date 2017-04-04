package local.domain;

import java.util.List;

import local.valueobjects.*;

public class Spielfeld {
	
	public Spielerverwaltung spielerVw;
	public Weltverwaltung weltVw;
	public Kriegsverwaltung kriegsVw;
	
	public Spielfeld() {
		this.spielerVw = new Spielerverwaltung();
		this.weltVw = new Weltverwaltung(spielerVw);
		this.kriegsVw = new Kriegsverwaltung(spielerVw, weltVw);
		spielerVw.setVerwaltung(weltVw, kriegsVw);
		weltVw.setVerwaltung(kriegsVw);
	}
	public void erstelleSpieler(String name){
		spielerVw.neuerSpieler(name);
	}
	public String zeigeName(int index){
		return spielerVw.zeigeName(index);
	}
	
	public void laenderAufteilen(int anzahlSpieler)
	{
		weltVw.laenderAufteilen(anzahlSpieler, spielerVw, weltVw);			
	}
	
	public List<Land> moeglicheAngriffe(Land land,Spieler spieler)
	{
		return kriegsVw.moeglicheAngriffsziele(land, spieler);
	}
}
