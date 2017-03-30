package local.domain;

public class Spielfeld {
	
	public Spielerverwaltung spielerVw;
	public Weltverwaltung weltVw;
	
	
	public Spielfeld() {
		this.spielerVw = new Spielerverwaltung();
		this.weltVw = new Weltverwaltung(spielerVw);
		spielerVw.setWeltverwaltung(weltVw);
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
}
