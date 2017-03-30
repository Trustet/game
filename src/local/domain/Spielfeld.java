package local.domain;

public class Spielfeld {
	
	public Spielerverwaltung spielerVw = new Spielerverwaltung();
	public Laenderverwaltung laenderVw = new Laenderverwaltung();
	public Weltverwaltung weltVw = new Weltverwaltung();
	
	public void erstelleSpieler(String name){
		spielerVw.neuerSpieler(name);
	}
	public String zeigeName(int index){
		return spielerVw.zeigeName(index);
	}
	
	public void laenderAufteilen(int anzahlSpieler)
	{
		laenderVw.laenderAufteilen(anzahlSpieler, spielerVw, weltVw);
				
				
	}
}
