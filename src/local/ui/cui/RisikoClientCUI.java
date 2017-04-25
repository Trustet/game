
package local.ui.cui;

import java.util.List;

import local.domain.Spielfeld;
import local.domain.Kriegsverwaltung.phasen;
import local.domain.exceptions.KannLandNichtBenutzenException;
import local.domain.exceptions.KeinNachbarlandException;
import local.domain.exceptions.NichtGenugEinheitenException;
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
		cui.spielStarten();	

		//führt für jeden Spieler die Funktionen aus
		for(Spieler spieler : sp.getSpielerList()) {	
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
				//Zum testen erstmal deaktiviert
				//System.out.println("Angriff");
				//cui.angriffsPhase(spieler);
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
//		for(Land land : sp.besitztLaender(spieler))
//		{
//			System.out.print(land.getName() + " |");
//		}
		System.out.println(sp.eigeneLaenderListe(spieler));
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
		Land aLand = null;
		Land vLand = null;
		boolean genugEinheiten = false;
		boolean gegnerNachbar = false;
		boolean erneutAngreifen = false;
		boolean phaseBeendet = false;
		do{
			do{
				System.out.println(spieler.getName() + "Mit welchem Land möchtest du angreifen?");
				angriffsLandString = IO.readString();
				if(sp.stringToLand(angriffsLandString) != null){
					if(sp.stringToLand(angriffsLandString).getBesitzer().equals(spieler)){
						if(sp.stringToLand(angriffsLandString).getEinheiten() > 1){
							System.out.println(sp.moeglicheAngriffsziele(angriffsLandString, spieler));
							genugEinheiten = true;
							aLand = sp.stringToLand(angriffsLandString);
						}else{
							System.out.println("Das Land hat nicht genug Einheiten");
						}
					}else{
						System.out.println("Dieses Land gehoert nicht dir");
					}
				}else{
					System.out.println("Dieses Land existiert nicht");
				}
			}while(genugEinheiten == false);
			System.out.println("\n");
			do{
				//ToDo Man kann noch eigene L�nder angreifen
				System.out.println("\nWelches Land willst du angreifen?");
				verteidigungsLandString = IO.readString();
				if(sp.stringToLand(verteidigungsLandString) != null){
					vLand = sp.stringToLand(verteidigungsLandString);
					if(vLand.equals(spieler)){
						System.out.println("Das Land geh�rt dir selber");
					}else{
						gegnerNachbar = true;
					}
				}else{
					System.out.println("Das Land existiert nicht");
				}
			}while(gegnerNachbar == false);
			do{
				erneutAngreifen = false;
				List<String> eroberung = sp.befreiungsAktion(angriffsLandString, verteidigungsLandString);
				System.out.println(eroberung.get(1));
				if(eroberung.get(0) != null){
					genugEinheiten = false;
					do{
						System.out.println("Wie viele Einheiten moechtest du auf " + verteidigungsLandString + " setzen?");
						System.out.println(aLand.getEinheiten() - 1 + " Einheiten kannst du setzen");
						int einheiten = IO.readInt();
						if(einheiten < aLand.getEinheiten() && einheiten > 0){
							sp.eroberungBesetzen(aLand, vLand, einheiten); 
							System.out.println("Das Land " + vLand.getName() + " hat " + vLand.getEinheiten() + " Einheiten");
							System.out.println("Das Land " + aLand.getName() + " hat " + aLand.getEinheiten() + " Einheiten");
							genugEinheiten = true;
						}else{
							System.out.println("Bitte gebe eine Korrekte Zahl ein");
						}
							
					}while(!genugEinheiten);
				}else if(aLand.getEinheiten() <= 2){
					System.out.println("Du kannst mit diesem land nicht weiter angreifen");
				}else{
					System.out.println("Moechtest du weiter angreifen? Ja/Nein");
					String selberAngriff = IO.readString();
					if(selberAngriff.equals("Ja"))
					{
						erneutAngreifen = true;
					}
				}
			}while(erneutAngreifen == true);
			System.out.println("Moechtest du mit einem weiteren Land angreifen?Ja/Nein");
			String antwort = IO.readString();
			if(!antwort.equals("Ja")){
				phaseBeendet = true;
			}
		}while(!phaseBeendet);
	}

	
	/**
	 * spielt die Verschiebenphase durch
	 * @param spieler
	 */
	public void verschieben(Spieler spieler){
		int einheiten;
		Land erstesLand = null;
		Land zweitesLand = null;
		String wahlLand;
		boolean kannLandBenutzen = false;
		boolean genugEinheiten = false;
		String zielLand;
		
				System.out.println("Von welchem Land moechtest du Einheiten verschieben?");
				//Zeigt alle Länder an, die benutzt werden können
				for(Land land : sp.besitztLaender(spieler))
				{
					System.out.print(land.getName() + " |");
				}
				System.out.println();
				//Läuft so lange, bis das erste Land korrekt ausgewählt wird
				do{
						wahlLand = IO.readString();
						try{
							kannLandBenutzen = sp.landWaehlen(wahlLand,spieler);
							kannLandBenutzen = sp.checkEinheiten(wahlLand,1);
						}catch(KannLandNichtBenutzenException lene ){
							System.out.println(lene.getMessage());
						}catch(NichtGenugEinheitenException ngee){
							System.out.println(ngee.getMessage());
							kannLandBenutzen = false;
							
						}
					}while(kannLandBenutzen == false);
					erstesLand = sp.stringToLand(wahlLand);
					kannLandBenutzen = false;
					//So lange, bis ein korrektes Zielland gewählt wird
					do{
						System.out.println(sp.eigeneNachbarn(wahlLand, spieler));
						zielLand = IO.readString();
						//Überprüft ob das Land existiert und dem Spieler gehört
						try{
							kannLandBenutzen = sp.landWaehlen(zielLand, spieler);
							kannLandBenutzen = sp.istNachbar(erstesLand, sp.stringToLand(zielLand), spieler);
						}catch(KannLandNichtBenutzenException klnbe){
							System.out.println(klnbe.getMessage());
						}catch(KeinNachbarlandException kne){
							System.out.println(kne.getMessage());
							kannLandBenutzen = false;
						}
					}while(kannLandBenutzen == false);
					zweitesLand = sp.stringToLand(zielLand);
					System.out.println(wahlLand + " hat " +  erstesLand.getEinheiten() + " Einheiten");
					System.out.println(zielLand + " hat " + zweitesLand.getEinheiten() + " Einheiten");
					do{
						
						System.out.println("Wie viele Einheiten moechtest du verschieben?");
						einheiten = IO.readInt();
						try{
							genugEinheiten = sp.checkEinheiten(wahlLand, einheiten);
						}catch(NichtGenugEinheitenException ngee){
							System.out.println(ngee.getMessage());
						}
					}while(genugEinheiten == false);
					sp.einheitenPositionieren(einheiten, zweitesLand);
					sp.einheitenPositionieren(-einheiten, erstesLand);
					System.out.println("Das Land " + zweitesLand.getName() + " hat " + zweitesLand.getEinheiten() + " Einheiten");
					System.out.println("Das Land " + erstesLand.getName() + " hat " + erstesLand.getEinheiten() + " Einheiten");
	}
}

