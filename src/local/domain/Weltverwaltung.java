package local.domain;
import java.util.List;
import java.util.Vector;

import local.valueobjects.*;
public class Weltverwaltung {

	private static int laenderAnzahl = 7;
	private boolean[][] laenderAufteilung;
	private Spielerverwaltung spielerVw;
	private List<Land> laenderListe = new Vector<Land>();
	
	
	public Weltverwaltung(Spielerverwaltung spielerVw) {
		laenderAufteilung = new boolean[laenderAnzahl][laenderAnzahl];
		
		this.spielerVw = spielerVw;
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
		for(int i = 0;i < weltVw.getLaenderListe().size();i = i+anzahlSpieler)
		{
			for(int j = 0;j < anzahlSpieler;j++)
			{
				if(counter < weltVw.getLaenderListe().size())
				{
					weltVw.getLaenderListe().get(counter).setBesitzer(spielerVw.getSpielerList().get(j));
					counter++;
				}
			}
		}
	}
	
	private void laenderErstellen()
	{
<<<<<<< HEAD
		laenderListe.add(new Land("Island",new Spieler("unbekannt"),0));
		laenderListe.add(new Land("Skandinavien",new Spieler("unbekannt"),0));
		laenderListe.add(new Land("Ukraine",new Spieler("unbekannt"),0));
		laenderListe.add(new Land("Nord Europa",new Spieler("unbekannt"),0));
		laenderListe.add(new Land("Sud Europa",new Spieler("unbekannt"),0));
		laenderListe.add(new Land("West Europa",new Spieler("unbekannt"),0));
		laenderListe.add(new Land("Gro�britannien",new Spieler("unbekannt"),0));
=======
		laenderListe[0] = new Land("Island",new Spieler("unbekannt"),1);
		laenderListe[1] = new Land("Skandinavien",new Spieler("unbekannt"),1);
		laenderListe[2] = new Land("Ukraine",new Spieler("unbekannt"),1);
		laenderListe[3] = new Land("Nord Europa",new Spieler("unbekannt"),1);
		laenderListe[4] = new Land("Sud Europa",new Spieler("unbekannt"),1);
		laenderListe[5] = new Land("West Europa",new Spieler("unbekannt"),1);
		laenderListe[6] = new Land("Gro\u00dfbritannien",new Spieler("unbekannt"),1);
>>>>>>> origin/master
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
	
	public void verbindungEinfuegen(int indexLand1,int indexLand2)
	{

			laenderAufteilung[indexLand1][indexLand2] = true;
			laenderAufteilung[indexLand2][indexLand1] = true;
	}

	public List<Land> getLaenderListe() {
		return laenderListe;
	}

	public void setLaenderListe(List<Land> laenderListe) {
		this.laenderListe = laenderListe;
	}

	public boolean[][] getLaenderAufteilung() {
		return laenderAufteilung;
	}

	public void setLaenderAufteilung(boolean[][] laenderAufteilung) {
		this.laenderAufteilung = laenderAufteilung;
	}
	
}
