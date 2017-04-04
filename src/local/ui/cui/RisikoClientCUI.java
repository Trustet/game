
package local.ui.cui;

import java.util.List;

import local.domain.Spielfeld;
import local.valueobjects.*;

public class RisikoClientCUI {


	Spielfeld sp = new Spielfeld();
	
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
		{	
		spielerstandAusgeben(spieler);
		einheitenVerteilen(spieler);
		angriffsPhase(spieler);
		}
		
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
	public void spielerstandAusgeben(Spieler spieler)
	{
		
			System.out.println("\n" + spieler.getName() +" besitzt die Länder: ");
			for(Land land : sp.spielerVw.besitztLaender(spieler))
			{
				System.out.print(land.getName() + " |");
			}
			System.out.println("\nund bekommt " + sp.spielerVw.bekommtEinheiten(spieler) + " Einheiten\n");		
	}
	
	public void einheitenVerteilen(Spieler spieler)
	{
		int einheitenAnzahl = sp.spielerVw.bekommtEinheiten(spieler);
		String landString;
		Land land;
		int einheiten;
		
		while(einheitenAnzahl > 0)
		{
			System.out.println("Auf welches Land möchtest du Einheiten setzen?");
			landString = IO.readString();
			land = sp.weltVw.stringToLand(landString);
			//todo Überprüfung ob gültiges land und spieler besitzer
			System.out.println("Wie viele Einheiten möchtest du auf " + land.getName() + " setzen?");
			einheiten = IO.readInt();
			if(einheiten <= einheitenAnzahl)
			{
				sp.kriegsVw.einheitenPositionieren(einheiten, land);
				System.out.println(land.getName() + " hat jetzt " + land.getEinheiten() + " Einheiten.");
				einheitenAnzahl -= einheiten;
			} else {
				System.out.println("So viele Einheiten hast du gar nicht, du hast nur noch " + einheitenAnzahl + " Einheiten\n");	
			}
		}
	}
	
	public void angriffsPhase(Spieler spieler)
	{
			String angriffsLandString;
			Land angriffsLand;
			List<Land> angreifbareLaender;
			String verteidigungsLandString;
			Land verteidigungsLand;
			int angreiferVerluste;
			int verteidigerVerluste;
			System.out.println("Mit welchem Land möchtest du angreifen?");
			angriffsLandString = IO.readString();
			angriffsLand = sp.weltVw.stringToLand(angriffsLandString);
			angreifbareLaender = sp.moeglicheAngriffe(angriffsLand, spieler);
			System.out.println("\nDu kannst mit " + angriffsLandString + " folgende Länder angreifen: ");
			for(Land land : angreifbareLaender)
			{
				System.out.print(land.getName() + " |");
			}
			System.out.println("\n");
			System.out.println("\nWelches Land willst du angreifen? ");
			verteidigungsLandString = IO.readString();
			verteidigungsLand = sp.weltVw.stringToLand(verteidigungsLandString);
			angreiferVerluste = sp.kriegsVw.befreiungsAktion(angriffsLand, verteidigungsLand).get(0);
			verteidigerVerluste = sp.kriegsVw.befreiungsAktion(angriffsLand, verteidigungsLand).get(1);
			angriffsLand.setEinheiten(angriffsLand.getEinheiten() - angreiferVerluste);
			verteidigungsLand.setEinheiten(verteidigungsLand.getEinheiten() - verteidigerVerluste);
			System.out.println("\n" + angriffsLand.getName() + " hat nun noch " + angriffsLand.getEinheiten());
			System.out.println("\n" + verteidigungsLand.getName() + " hat nun noch " + verteidigungsLand.getEinheiten());
	}
}

