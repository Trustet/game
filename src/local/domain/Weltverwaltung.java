package local.domain;
import java.util.List;
import java.util.Vector;

import local.valueobjects.*;
public class Weltverwaltung {

	private static int laenderAnzahl = 42;
	private boolean[][] laenderAufteilung;
	private Spielerverwaltung spielerVw;
	private List<Land> laenderListe = new Vector<Land>();
	private List<Kontinent> kontinentenListe = new Vector<Kontinent>();

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
		this.kontinenteErstellen();
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
		//Europa 7
		laenderListe.add(new Land("Island",new Spieler("unbekannt"),1,"is"));
		laenderListe.add(new Land("Skandinavien",new Spieler("unbekannt"),1,"sk"));
		laenderListe.add(new Land("Ukraine",new Spieler("unbekannt"),1,"uk"));
		laenderListe.add(new Land("Nord-Europa",new Spieler("unbekannt"),1,"ne"));
		laenderListe.add(new Land("Sud-Europa",new Spieler("unbekannt"),1,"se"));
		laenderListe.add(new Land("West-Europa",new Spieler("unbekannt"),1,"we"));
		laenderListe.add(new Land("Großbritannien",new Spieler("unbekannt"),1,"gr"));
		
		//Asien 12
		laenderListe.add(new Land("Ural",new Spieler("unbekannt"),1,"ur"));
		laenderListe.add(new Land("Sibirien",new Spieler("unbekannt"),1,"si"));
		laenderListe.add(new Land("Jakutien",new Spieler("unbekannt"),1,"ja"));
		laenderListe.add(new Land("Kamtschatka",new Spieler("unbekannt"),1,"ka"));
		laenderListe.add(new Land("Irtusk",new Spieler("unbekannt"),1,"ir"));
		laenderListe.add(new Land("Mongolei",new Spieler("unbekannt"),1,"mo"));
		laenderListe.add(new Land("Japan",new Spieler("unbekannt"),1,"ja"));
		laenderListe.add(new Land("Afghanistan",new Spieler("unbekannt"),1,"af"));
		laenderListe.add(new Land("China",new Spieler("unbekannt"),1,"ch"));
		laenderListe.add(new Land("Mittlerer Osten",new Spieler("unbekannt"),1,"mo"));
		laenderListe.add(new Land("Indien",new Spieler("unbekannt"),1,"in"));
		laenderListe.add(new Land("Siam",new Spieler("unbekannt"),1,"si"));
		
		//Australien 4
		laenderListe.add(new Land("Neu-Guinea",new Spieler("unbekannt"),1,"ng"));
		laenderListe.add(new Land("Indonesien",new Spieler("unbekannt"),1,"in"));
		laenderListe.add(new Land("Ostaustralien",new Spieler("unbekannt"),1,"os"));
		laenderListe.add(new Land("Westaustralien",new Spieler("unbekannt"),1,"we"));
		
		//Afrika 6
		laenderListe.add(new Land("Ägypten",new Spieler("unbekannt"),1,"äg"));
		laenderListe.add(new Land("Ostafrika",new Spieler("unbekannt"),1,"oa"));
		laenderListe.add(new Land("Kongo",new Spieler("unbekannt"),1,"ko"));
		laenderListe.add(new Land("Südafrika",new Spieler("unbekannt"),1,"sü"));
		laenderListe.add(new Land("Nordwestafrika",new Spieler("unbekannt"),1,"no"));
		laenderListe.add(new Land("Madagaskar",new Spieler("unbekannt"),1,"ma"));
		
		//Südamerika 4
		laenderListe.add(new Land("Argentinien",new Spieler("unbekannt"),1,"ar"));
		laenderListe.add(new Land("Peru",new Spieler("unbekannt"),1,"pe"));
		laenderListe.add(new Land("Brasilien",new Spieler("unbekannt"),1,"br"));
		laenderListe.add(new Land("Venezuela",new Spieler("unbekannt"),1,"ve"));
		
		//Nordamerika 9
		laenderListe.add(new Land("Mittelamerika",new Spieler("unbekannt"),1,"ma"));
		laenderListe.add(new Land("Oststaaten",new Spieler("unbekannt"),1,"os"));
		laenderListe.add(new Land("Weststaaten",new Spieler("unbekannt"),1,"ws"));
		laenderListe.add(new Land("Alberta",new Spieler("unbekannt"),1,"al"));
		laenderListe.add(new Land("Ontario",new Spieler("unbekannt"),1,"on"));
		laenderListe.add(new Land("Quebec",new Spieler("unbekannt"),1,"qu"));
		laenderListe.add(new Land("Alaska",new Spieler("unbekannt"),1,"al"));
		laenderListe.add(new Land("Nordwest-Territorium",new Spieler("unbekannt"),1,"nt"));
		laenderListe.add(new Land("Grönland",new Spieler("unbekannt"),1,"gl"));
	}
	
	private void verbindungenErstellen()
	{
		verbindungEinfuegen(0,new int[] {1,6,41});
		verbindungEinfuegen(1,new int[] {0,2,3,6});		
		verbindungEinfuegen(2,new int[] {1,3,4,7,14,16});		
		verbindungEinfuegen(3,new int[] {1,2,4,5,6});		
		verbindungEinfuegen(4,new int[] {2,3,5,16,23,27});		
		verbindungEinfuegen(5,new int[] {3,4,6,27});		
		verbindungEinfuegen(6,new int[] {0,1,3,5});	
		
		verbindungEinfuegen(7,new int[] {2,8,14,15});		
		verbindungEinfuegen(8,new int[] {7,9,11,12,15});		
		verbindungEinfuegen(9,new int[] {8,10,11});		
		verbindungEinfuegen(10,new int[] {9,11,12,13,39});		
		verbindungEinfuegen(11,new int[] {8,9,10,12});	
		verbindungEinfuegen(12,new int[] {8,10,11,13,15});		
		verbindungEinfuegen(13,new int[] {10,12});		
		verbindungEinfuegen(14,new int[] {7,15,16,17,2});		
		verbindungEinfuegen(15,new int[] {7,8,12,14,17,18});		
		verbindungEinfuegen(16,new int[] {2,4,14,17,23,24});		
		verbindungEinfuegen(17,new int[] {14,15,16,18});		
		verbindungEinfuegen(18,new int[] {15,17,20});
		
		verbindungEinfuegen(19,new int[] {20,21,22});		
		verbindungEinfuegen(20,new int[] {18,19,21,22});		
		verbindungEinfuegen(21,new int[] {19,22});
		verbindungEinfuegen(22,new int[] {19,20,21});	
		
		verbindungEinfuegen(23,new int[] {4,16,24,27});		
		verbindungEinfuegen(24,new int[] {16,23,25,26,27,28});		
		verbindungEinfuegen(25,new int[] {24,26,27});		
		verbindungEinfuegen(26,new int[] {24,25,28});		
		verbindungEinfuegen(27,new int[] {4,5,23,24,25,31});		
		verbindungEinfuegen(28,new int[] {24,26});
		
		verbindungEinfuegen(29,new int[] {30,31});		
		verbindungEinfuegen(30,new int[] {29,31,32});		
		verbindungEinfuegen(31,new int[] {27,29,30,32});	
		verbindungEinfuegen(32,new int[] {30,31,33});
		
		verbindungEinfuegen(33,new int[] {32,34,35});		
		verbindungEinfuegen(34,new int[] {33,35,37,38});		
		verbindungEinfuegen(35,new int[] {33,34,36,37});		
		verbindungEinfuegen(36,new int[] {35,37,39,40});		
		verbindungEinfuegen(37,new int[] {34,35,36,38,40,41});		
		verbindungEinfuegen(38,new int[] {34,37,41});		
		verbindungEinfuegen(39,new int[] {10,36,40});		
		verbindungEinfuegen(40,new int[] {36,37,39,41});		
		verbindungEinfuegen(41,new int[] {0,37,38,40});
	}
	
	public void kontinenteErstellen()
	{
		List<Land> eu = new Vector<Land>();
		
		for(int i = 0;i < 7;i++)
		{
			eu.add(laenderListe.get(i));
		}
		kontinentenListe.add(new Kontinent("Europa",eu));
	}
	
	public void verbindungEinfuegen(int indexLand1,int[] nachbarlaender)
	{
		for(int nachbarland : nachbarlaender)
		{
			laenderAufteilung[indexLand1][nachbarland] = true;
			laenderAufteilung[nachbarland][indexLand1] = true;
		}
	}

	public boolean istNachbarland(int indexLand1, int indexLand2)
	{
		if (laenderAufteilung[indexLand1][indexLand2] == true)
		{
			return true;
		}
		return false;
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


	public List<Kontinent> getKontinentenListe() {
		return kontinentenListe;
	}
	
	
}
