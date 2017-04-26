
package local.ui.cui;

import java.util.List;

import local.domain.Spielfeld;
import local.domain.Kriegsverwaltung.phasen;
import local.domain.exceptions.KannLandNichtBenutzenException;
import local.domain.exceptions.KeinGegnerException;
import local.domain.exceptions.KeinNachbarlandException;
import local.domain.exceptions.LandExistiertNichtException;
import local.domain.exceptions.NichtGenugEinheitenException;
import local.domain.exceptions.SpielerExistiertBereitsException;
import local.valueobjects.*;


public class RisikoClientCUI {
	
	//Domain-Komponente, welche die Verwaltungen verwaltet
	static Spielfeld sp = new Spielfeld();
	public static phasen Phase;
	private static boolean gewonnen = false;
	private boolean startPhase;

	/**
	 * Main-Methode der CUI
	 * @param args
	 */
	public static void main(String[] args)  {
		
		RisikoClientCUI cui = new RisikoClientCUI();
		//TODO Anfangsphase auf Verteilen setzen
		cui.spielStarten(cui);	
		
		//Phasenablauf
		do{
			Spieler spieler = sp.getAktiverSpieler();
			
			switch(sp.getTurn()){
			case VERTEILEN:
				cui.einheitenVerteilen(spieler, cui);
				sp.nextTurn();
				break;
			case ANGRIFF:
				cui.angreifen(spieler);
				sp.nextTurn();
				break;
			case VERSCHIEBEN:
				cui.verschieben(spieler);
				sp.nextTurn();
				sp.naechsterSpieler();
				break;	
			}	
		}while(gewonnen == false);
	}

	/**
	 * startet das Spiel
	 */
	private void spielStarten(RisikoClientCUI cui)	{
		String name = "";
		int anzahlSpieler = 0;
		int aktiveSpieler = 0;
		
		startPhase = true;
		//endlose Schleife bis richtige Eingabe um Spieler zu erstellen
		while(true)	{
			//anzahlSpieler der Spieler einlesen
			System.out.println("Wie viele Spieler spielen mit? (2-6)");
			anzahlSpieler = IO.readInt();
			
			//Bei Eingaben zwischen 2 und 6, werden die neuen Spieler mit eingegebenen Namen erstellt
			if(anzahlSpieler > 1 && anzahlSpieler < 7 )	{

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
				} while (aktiveSpieler < anzahlSpieler);
				
				break;
			} else {
				//Ausgabe bei falscher Eingabe
				System.out.println("Falsche Eingabe.");
			}
		}	

		sp.laenderAufteilen(anzahlSpieler);
		
		//verteilen der Einheiten am Anfang für jeden Spieler
				for(Spieler spieler : sp.getSpielerList()) {
					System.out.println(spieler.getName() + " ist dran und darf nun seine ersten Einheiten verteilen.");
					cui.einheitenVerteilen(spieler, cui);
					sp.naechsterSpieler();
				}
				
		startPhase = false;
	}

	/**
	 * Zeigt an welcher Spieler an der Reihe ist und welche Länder er besitzt und Einheiten er bekommt
	 * @param spieler
	 */
	public void spielerstandAusgeben(Spieler spieler) {
		System.out.println("\n" + spieler.getName() +" besitzt die Länder: ");
		System.out.println(sp.eigeneLaenderListe(spieler));
		System.out.println("\nund bekommt " + sp.bekommtEinheiten(spieler) + " Einheiten\n");		
	}

	/**
	 * Spieler kann Einheiten auf eigene Länder verteilen
	 * @param spieler
	 */
	public void einheitenVerteilen(Spieler spieler,RisikoClientCUI cui)	{
		int einheitenAnzahl = sp.bekommtEinheiten(spieler);
		String landString;
		Land land;
		int einheiten;
		
		if(startPhase != true)
		{
		System.out.println("\n" + sp.getAktiverSpieler().getName() + " ist jetzt dran.");
		System.out.println("Phase: " + sp.getTurn());
		}
		
		cui.spielerstandAusgeben(spieler);
		
		while(einheitenAnzahl > 0) {
			System.out.println("Auf welches Land möchtest du Einheiten setzen?");
			landString = IO.readString();
			//TODO Exceptions statt else
			if(sp.stringToLand(landString) != null) {
				land = sp.stringToLand(landString);
				if(land.getBesitzer().equals(spieler)) {
					System.out.println("Wie viele Einheiten möchtest du auf " + land.getName() + " setzen?");
					System.out.println("Du kannst " + einheitenAnzahl + " Einheiten setzen");
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
					System.out.println("Das Land gehört nicht dir!");
				}
			}else{
				System.out.println("Dieses Land existiert nicht");
			}
		}
	}


	/**
	 * Angriffphase
	 * @param spieler
	 */
	public void angreifen(Spieler spieler) {
		String angriffsLandString;
		String verteidigungsLandString;
		Land aLand = null;
		Land vLand = null;
		boolean genugEinheiten = false;
		boolean gegnerNachbar = false;
		boolean erneutAngreifen = false;
		boolean phaseBeendet = false;
		List<String> eroberung;
		String weiterangreifen;
		
		System.out.println("Phase: " + sp.getTurn());
		
		do{
			do{
				System.out.println(spieler.getName() + " mit welchem Land möchtest du angreifen?");
				System.out.println(sp.eigeneAngriffsLaender(spieler));
				angriffsLandString = IO.readString();
				try{
					genugEinheiten = sp.landWaehlen(angriffsLandString,spieler);
					genugEinheiten = sp.checkEinheiten(angriffsLandString,1);
				}catch(KannLandNichtBenutzenException lene ){
					System.out.println(lene.getMessage());
				}catch(NichtGenugEinheitenException ngee){
					System.out.println(ngee.getMessage());
				}
			}while(genugEinheiten == false);
			
			System.out.println(sp.moeglicheAngriffsziele(angriffsLandString, spieler));
			aLand = sp.stringToLand(angriffsLandString);
			
			do{
				System.out.println("\nWelches Land willst du angreifen?");
				verteidigungsLandString = IO.readString();
				try{
					sp.landExistiert(verteidigungsLandString);
					vLand = sp.stringToLand(verteidigungsLandString);
					sp.istNachbar(aLand,vLand,spieler);
					gegnerNachbar = sp.istGegner(verteidigungsLandString,spieler);
				}catch(LandExistiertNichtException lene ){
					System.out.println(lene.getMessage());
				}catch(KeinNachbarlandException ngee){
					System.out.println(ngee.getMessage());
				}catch(KeinGegnerException kge){
					System.out.println(kge.getMessage());
				}
			}while(gegnerNachbar == false);
			
			do{
				erneutAngreifen = false;
				eroberung = sp.befreiungsAktion(angriffsLandString, verteidigungsLandString);
				System.out.println(eroberung.get(1));
				
				if(eroberung.get(0) != null){
					genugEinheiten = false;
					do{
						if(aLand.getEinheiten() == 2) {
							System.out.println("Eine Einheit wird auf " + verteidigungsLandString + " gesetzt.");
							sp.eroberungBesetzen(aLand, vLand, 1); 
							System.out.println("Das Land " + vLand.getName() + " hat " + vLand.getEinheiten() + " Einheiten");
							System.out.println("Das Land " + aLand.getName() + " hat " + aLand.getEinheiten() + " Einheiten");
							genugEinheiten = true;
							//...
						} else {
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
						}
							
					}while(!genugEinheiten);
				}else if(aLand.getEinheiten() < 2){
					System.out.println("Du kannst mit diesem Land nicht weiter angreifen.");
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
			weiterangreifen = IO.readString();
			if(!weiterangreifen.equals("Ja")){
				phaseBeendet = true;
			}
		}while(!phaseBeendet);
	}

	
	/**
	 * Verschiebenphase
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
		
		System.out.println("Phase: " + sp.getTurn());
		System.out.println("\nMoechtest du Einheiten verschieben? Ja/Nein");
		String antwort = IO.readString();
		
		if(antwort.equals("Ja")){
			
				System.out.println("Von welchem Land moechtest du Einheiten verschieben?");
				//Zeigt alle Länder an, die benutzt werden können
				System.out.println(sp.eigeneAngriffsLaender(spieler));
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
						System.out.println(sp.moeglicheVerschiebeZiele(erstesLand, spieler));
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
}

