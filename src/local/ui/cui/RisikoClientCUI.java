
package local.ui.cui;

import java.util.List;

import local.domain.Spielfeld;
import local.domain.exceptions.SpielerExistiertBereitsException;
import local.valueobjects.*;

<<<<<<< HEAD
public class RisikoClientCUI {
	// ich mag datvi
=======
public class RisikoClientCUI {
>>>>>>> origin/master
	//Domain-Komponente, welche die Verwaltungen verwaltet
	Spielfeld sp = new Spielfeld();

	/**
	 * Main-Methode der CUI
	 * @param args
	 */
	public static void main(String[] args) {
		RisikoClientCUI cui = new RisikoClientCUI();
		String weiterAngreifen;
		cui.spielStarten();	

		//führt für jeden Spieler die Funktionen aus
		for(Spieler spieler : cui.sp.spielerVw.getSpielerList()) {	
			cui.spielerstandAusgeben(spieler);
			cui.einheitenVerteilen(spieler);

		}

		for(Spieler spieler : cui.sp.spielerVw.getSpielerList()) {
			System.out.println("\n" + spieler.getName() + " ist nun dran!\n");
			cui.angriffsPhase(spieler);

			System.out.println("Willst du weiter angreifen? Ja / Nein");
			weiterAngreifen = IO.readString();
			if(weiterAngreifen == "Ja")
			{
				cui.angriffsPhase(spieler);
			}
			else
			{
				System.out.println("\nDann kannst du nun noch deine Einheiten verschieben.\n");
				//Einheiten verschieben
			}
		}
	}

	/**
	 * startet das Spiel
	 */
	private void spielStarten()	{
		String name = "";
		int anzahlSpieler = 0;

		//endlose Schleife bis richtige Eingabe um Spieler zu erstellen
		while(true)	{
			//anzahlSpieler der Spieler einlesen
			System.out.println("Wie viele Spieler spielen mit? (2-6)");
			anzahlSpieler = IO.readInt();
			//Bei Eingaben zwischen 2 und 6, werden die neuen Spieler mit eingegebenen Namen erstellt
			if(anzahlSpieler > 1 && anzahlSpieler < 7 )	{
				//				for(int i = 1;i <= anzahlSpieler;i++) {
				int aktiveSpieler = 0;
				do {
					System.out.println("Hallo Spieler " + (aktiveSpieler+1) + ", wie heisst du?");
					name = IO.readString();
					try {
						sp.erstelleSpieler(name);
						aktiveSpieler++;
					} catch (SpielerExistiertBereitsException sebe) {
						String message = sebe.getMessage();
						System.out.println(message);
						System.out.println("Bitte wählen Sie einen anderen Namen!");
					}
					//				}
				} while (aktiveSpieler < anzahlSpieler);
				//springt aus der Endlos-Schleife
				break;
			} else {
				//Ausgabe bei falscher Eingabe
				System.out.println("Falsche Eingabe.");
			}
		}	

		sp.laenderAufteilen(anzahlSpieler);

	}

	/**
	 * Zeigt an welcher Spieler an der Reihe ist und welche Länder er besitzt und Einheiten er bekommt
	 * @param spieler
	 */
	public void spielerstandAusgeben(Spieler spieler) {
		System.out.println("\n" + spieler.getName() +" besitzt die Länder: ");
		for(Land land : sp.besitztLaender(spieler))
		{
			System.out.print(land.getName() + " |");
		}
		System.out.println("\nund bekommt " + sp.bekommtEinheiten(spieler) + " Einheiten\n");		
	}

	/**
	 * Spieler kann Einheiten auf eigene Länder verteilen
	 * @param spieler
	 */
	public void einheitenVerteilen(Spieler spieler)	{
		int einheitenAnzahl = sp.bekommtEinheiten(spieler);
		String landString;
		Land land;
		int einheiten;

		while(einheitenAnzahl > 0) {
			System.out.println("Auf welches Land möchtest du Einheiten setzen?");
			landString = IO.readString();
			land = sp.weltVw.stringToLand(landString);
			//ToDo Überprüfung ob gültiges land und spieler auch besitzer
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


	/**
	 * spielt die Angriffsphase durch
	 * @param spieler
	 */
	public void angriffsPhase(Spieler spieler) {
		String angriffsLandString;
		String verteidigungsLandString;

		System.out.println("Mit welchem Land möchtest du angreifen?");
		angriffsLandString = IO.readString();
		System.out.println(sp.kriegsVw.moeglicheAngriffsziele(angriffsLandString, spieler));

		System.out.println("\n");
		System.out.println("\nWelches Land willst du angreifen?");
		verteidigungsLandString = IO.readString();
		angreifen(angriffsLandString,verteidigungsLandString);
	}

	public void angreifen(String angriffsLandString, String verteidigungsLandString)
	{
		String selberAngriff;
		System.out.println(sp.kriegsVw.befreiungsAktion(angriffsLandString, verteidigungsLandString));
		System.out.println("Willst du den selben Angriff erneut durchführen? Ja / Nein");
		selberAngriff = IO.readString();
		if(selberAngriff == "Ja")
		{
			angreifen(angriffsLandString, verteidigungsLandString);
		}
	}
}

