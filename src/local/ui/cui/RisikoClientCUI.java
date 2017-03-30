
package local.ui.cui;

import local.domain.Spielfeld;
import local.valueobjects.Kontinent;
import local.valueobjects.Land;
import local.valueobjects.Spieler;

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
		/*
		 * ddsasdadasda
		 * asdasdasdasdas
		 * asdasdasda
		 * dasdasdasdasda
		 */
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
		
		//teilt die Länder auf die Spieler auf
		sp.laenderAufteilen(anzahlSpieler);
		
		for(Spieler spieler : sp.spielerVw.getSpielerList())
		spielerstandAusgeben(spieler, sp);
		
		/*
		 * Test-Ausgaben
		 */
		/*
		//Laender ausgeben

		for(Land land : sp.weltVw.getLaenderListe()){
			System.out.println(land);
		}
		
		//Kontinente ausgeben
		
		for(Kontinent kontinent: sp.weltVw.getKontinentenListe())
		{
			System.out.println(kontinent);
		}
		
		//Adjazenzmatrix ausgeben
		System.out.println("");		
		for(int i = 0;i < sp.weltVw.getLaenderAufteilung().length;i++)
		{
			for(int j = 0; j < sp.weltVw.getLaenderAufteilung()[i].length;j++)
			{
				System.out.print(sp.weltVw.getLaenderAufteilung()[i][j] + " ");
			}
			System.out.println("");
		}
		*/
	}
	
	//Zeigt an welcher Spieler an der Reihe ist und welche Länder er besitzt und Einheiten er bekommt
	public void spielerstandAusgeben(Spieler spieler, Spielfeld sp)
	{
		
				System.out.println("\n" + spieler.getName() +" besitzt die Länder: ");
				for(Land land : sp.spielerVw.besitztLaender(spieler))
				{
					System.out.print(land.getName() + " |");
				}
				System.out.println("\nund bekommt " + sp.spielerVw.bekommtEinheiten(spieler) + " Einheiten\n");
				
	}
}

