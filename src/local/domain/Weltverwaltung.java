package local.domain;
import local.valueobjects.*;
public class Weltverwaltung {

	private static int laenderAnzahl = 7;
	private boolean[][] laenderAufteilung;
	private Land[] laenderListe = new Land[laenderAnzahl];
	
	
	public Weltverwaltung() {
		laenderAufteilung = new boolean[laenderAnzahl][laenderAnzahl];
		
		for(int spalte = 0; spalte < laenderAnzahl;spalte++)
		{
			for(int zeile = 0; zeile < laenderAnzahl;zeile++)
			{
				laenderAufteilung[spalte][zeile] = false;
			}
		}
		this.laenderErstellen();
		this.verbindungenErstellen();
	}
	
	public void laenderAufteilen(int anzahlSpieler, Spielerverwaltung spielerVw, Weltverwaltung weltVw)
	{
		int counter = 0;
		for(int i = 0;i < weltVw.getLaenderListe().length;i = i+anzahlSpieler)
		{
			for(int j = 0;j < anzahlSpieler;j++)
			{
				if(counter < weltVw.getLaenderListe().length)
				{
					weltVw.getLaenderListe()[counter].setBesitzer(spielerVw.getSpielerList().get(j));
					counter++;
				}
			}
		}
	}
	
	private void laenderErstellen()
	{
		laenderListe[0] = new Land("Island",new Spieler("unbekannt"),1);
		laenderListe[1] = new Land("Skandinavien",new Spieler("unbekannt"),1);
		laenderListe[2] = new Land("Ukraine",new Spieler("unbekannt"),1);
		laenderListe[3] = new Land("Nord Europa",new Spieler("unbekannt"),1);
		laenderListe[4] = new Land("Sud Europa",new Spieler("unbekannt"),1);
		laenderListe[5] = new Land("West Europa",new Spieler("unbekannt"),1);
		laenderListe[6] = new Land("Gro\u00dfbritannien",new Spieler("unbekannt"),1);
	}
	
	private void verbindungenErstellen()
	{
		verbindungEinfuegen(0,1);
		verbindungEinfuegen(0,6);
		
		verbindungEinfuegen(1,2);
		verbindungEinfuegen(1,3);
		verbindungEinfuegen(1,6);
		
		verbindungEinfuegen(2,3);
		verbindungEinfuegen(2,4);
		
		verbindungEinfuegen(3,4);
		verbindungEinfuegen(3,5);
		verbindungEinfuegen(3,6);
		
		verbindungEinfuegen(4,5);
		
		verbindungEinfuegen(5,6);
	}
	
	public boolean verbindungEinfuegen(int indexLand1,int indexLand2)
	{
		if (indexLand1 != -1 && indexLand2 != -1)
		{
			laenderAufteilung[indexLand1][indexLand2] = true;
			laenderAufteilung[indexLand2][indexLand1] = true;
			return true;
		}
		return false;
	}
	
	private int laenderIndexSuchen(Land land)
	{
		int index = -1;
		int i = 0;
		
		while( index < 0 && i < laenderListe.length)
		{
			if(laenderListe[i].equals(land))
			{
				index = i;
			}
			i++;
		}
		
		
		return index;
	}

	public Land[] getLaenderListe() {
		return laenderListe;
	}

	public void setLaenderListe(Land[] laenderListe) {
		this.laenderListe = laenderListe;
	}

	public boolean[][] getLaenderAufteilung() {
		return laenderAufteilung;
	}

	public void setLaenderAufteilung(boolean[][] laenderAufteilung) {
		this.laenderAufteilung = laenderAufteilung;
	}
	
}
