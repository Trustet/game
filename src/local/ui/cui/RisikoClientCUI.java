package local.ui.cui;

import local.domain.Spielfeld;

public class RisikoClientCUI {

	/**
	 * Main-Methode der CUI
	 * @param args
	 */
	public static void main(String[] args) {
		RisikoClientCUI cui = new RisikoClientCUI();
		
		cui.spielStarten();	
	}
	
	private void spielStarten()
	{
		String name = "";
		int anzahlSpieler = 0;
		Spielfeld sp = new Spielfeld();
		
		//endlose Schleife bis richtige Eingabe um Spieler zu erstellen
		while(true)
		{
			//anzahlSpieler der Spieler einlesen
			System.out.println("Wie viele Spieler spielen mit? (2-6)");
			anzahlSpieler = IO.readInt();
			//Bei Eingaben zwischen 2 und 6, werden die neuen Spieler mit eingegebenen Namen erstellt
			if(anzahlSpieler > 1 && anzahlSpieler < 7 )
			{
				for(int i = 1;i <= anzahlSpieler;i++)
				{
					System.out.println("Hallo Spieler " + i + ", wie heisst du?");
					name = IO.readString();
					sp.erstelleSpieler(name);
				}
				//springt aus der Endlos-Schleife
				break;
			} else {
				//Ausgabe bei falscher Eingabe
				System.out.println("Falsche Eingabe.");
			}
			
		}	
		
		//Ausgabe, wer nun alles mitspielt (Uberprufung, ob Eingabe erfolgreich war)
		System.out.println("Los gehts.\n Es spielen mit:");
		for(int i = 1;i <= anzahlSpieler;i++)
		{
			System.out.println(sp.zeigeName(i));
		}
		
		sp.laenderAufteilen(anzahlSpieler);
		
		//Laender ausgeben
		for(int i = 0;i < sp.weltVw.getLaenderListe().length;i++)
		{
			System.out.println(sp.weltVw.getLaenderListe()[i].toString());
		}
		System.out.println("");		
		for(int i = 0;i < sp.weltVw.getLaenderAufteilung().length;i++)
		{
			for(int j = 0; j < sp.weltVw.getLaenderAufteilung()[i].length;j++)
			{
				System.out.print(sp.weltVw.getLaenderAufteilung()[i][j] + " ");
			}
			System.out.println("");
		}
	}
}
