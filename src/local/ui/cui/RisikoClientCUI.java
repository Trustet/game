
package local.ui.cui;

import java.util.List;

import local.domain.Spielfeld;
import local.domain.Kriegsverwaltung.phasen;
import local.domain.exceptions.SpielerExistiertBereitsException;
import local.valueobjects.*;


public class RisikoClientCUI {

	//Domain-Komponente, welche die Verwaltungen verwaltet
	static Spielfeld sp = new Spielfeld();
	public static phasen Phase;
	private static boolean gewonnen = false;

	/**
	 * Main-Methode der CUI
	 * @param args
	 */
	public static void main(String[] args)  {
		
		RisikoClientCUI cui = new RisikoClientCUI();
		String weiterAngreifen;
		cui.spielStarten();	

		//führt für jeden Spieler die Funktionen aus
		for(Spieler spieler : cui.sp.getSpielerList()) {	
			cui.spielerstandAusgeben(spieler);
			cui.einheitenVerteilen(spieler);

		}
		do{
		Spieler spieler = sp.getAktiverSpieler();
//		for(Spieler spieler : cui.sp.spielerVw.getSpielerList()) {
//			System.out.println("\n" + spieler.getName() + " ist nun dran!\n");
			//cui.angriffsPhase(spieler);
//			int enden = 1;
//			do{
//				System.out.println("Der name vom ersten Spieler lautet " + sp.getAktiverSpieler().getName());
//				sp.naechsterSpieler();
//				System.out.println("Weiter = 0");
//				enden = IO.readInt();
//			}while(enden == 0);
		
			//Testtext
			System.out.println(sp.getTurn() + "Aktuelle Phase");
			//
			switch(sp.getTurn()){
			
			case VERSCHIEBEN:
				System.out.println("Moechtest du Einheiten verschieben? Ja/Nein");
				String antwort = IO.readString();
				if(antwort.equals("Ja")){
					cui.verschieben(spieler);
				}
				sp.nextTurn();
				break;
			case ANGRIFF:
				System.out.println("Angriff");
				cui.angriffsPhase(spieler);
				sp.nextTurn();
				break;
			case VERTEILEN:
				System.out.println("Verteilen");
				sp.naechsterSpieler();
				sp.nextTurn();
				break;
				
			}
			
//			System.out.println("Willst du weiter angreifen? Ja / Nein");
//			weiterAngreifen = IO.readString();
//			if(weiterAngreifen.equals("Ja"))
//			{
//				cui.angriffsPhase(spieler);
//			}
//			else
//			{
//				System.out.println("\nDann kannst du nun noch deine Einheiten verschieben.\n");
//				//Einheiten verschieben
//			}
	}while(gewonnen == false);
}
//	}

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
			if(sp.stringToLand(landString) != null){
				land = sp.stringToLand(landString);
				if(land.getBesitzer().equals(spieler)){
					System.out.println("Wie viele Einheiten möchtest du auf " + land.getName() + " setzen?");
					einheiten = IO.readInt();
					if(einheiten <= einheitenAnzahl)
					{
						sp.einheitenPositionieren(einheiten, land);
						System.out.println(land.getName() + " hat jetzt " + land.getEinheiten() + " Einheiten.");
						einheitenAnzahl -= einheiten;
					} else {
						System.out.println("So viele Einheiten hast du gar nicht, du hast nur noch " + einheitenAnzahl + " Einheiten\n");	
					}
				}else{
					System.out.println("Das Land geh�rt nicht dir!");
				}
			}else{
				System.out.println("Dieses Land existiert nicht");
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

		System.out.println(spieler.getName() + "Mit welchem Land möchtest du angreifen?");
		angriffsLandString = IO.readString();
		System.out.println(sp.moeglicheAngriffsziele(angriffsLandString, spieler));

		System.out.println("\n");
		System.out.println("\nWelches Land willst du angreifen?");
		verteidigungsLandString = IO.readString();
		angreifen(angriffsLandString,verteidigungsLandString);
	}

	public void angreifen(String angriffsLandString, String verteidigungsLandString)
	{
		String selberAngriff;
		System.out.println(sp.befreiungsAktion(angriffsLandString, verteidigungsLandString));
		System.out.println("Willst du den selben Angriff erneut durchführen? Ja / Nein");
		selberAngriff = IO.readString();
		if(selberAngriff.equals("Ja"))
		{
			angreifen(angriffsLandString, verteidigungsLandString);
		}
	}
	/**
	 * spielt die Verschiebenphase durch
	 * @param spieler
	 */
	public void verschieben(Spieler spieler){
		Land erstesLand;
		String antwort;
		boolean kannVerschieben = false;
		boolean gehoertLand = false;
		boolean fertigMitVerschieben = false;
		String zielLand;
		
				System.out.println("Von welchem Land moechtest du Einheiten verschieben?");
				for(Land land : sp.besitztLaender(spieler))
				{
					System.out.print(land.getName() + " |");
				}
				System.out.println();
				do{
						antwort = IO.readString();
						if(sp.stringToLand(antwort) != null && sp.stringToLand(antwort).getBesitzer().equals(spieler)){
							gehoertLand = true;
						}else{
							System.out.println("Land gehoert nicht dir");
							System.out.println("Bitte gebe ein anderes Land ein");			
						}
					}while(gehoertLand == false);
					erstesLand = sp.stringToLand(antwort);
					if(erstesLand.getEinheiten() < 2){
						System.out.println("Das Land hat nur " + erstesLand.getEinheiten() + " Einheit, bitte waehle ein anderes Land");
						this.verschieben(spieler);
					}else{
						System.out.println(sp.eigeneNachbarn(antwort, spieler));
						zielLand = IO.readString();
						Land zweitesLand = sp.weltVw.stringToLand(zielLand);
						System.out.println(antwort + " hat " +  erstesLand.getEinheiten() + " Einheiten");
						System.out.println(zielLand + " hat " + zweitesLand.getEinheiten() + " Einheiten");
					}
	}
}

