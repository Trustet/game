package local.domain;

import local.valueobjects.Spieler;
public class Spielfeld {
	
	Spielerverwaltung spielerVw = new Spielerverwaltung();
	
	public void erstelleSpieler(String name){
		spielerVw.neuerSpieler(name);
	}
	public String zeigeName(int index){
		return spielerVw.zeigeName(index);
	}
}
