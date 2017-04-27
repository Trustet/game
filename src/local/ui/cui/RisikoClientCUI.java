//� \u00DF
//� \u00FC
//� \u00DC
//� \u00E4
//� \u00C4
//� \u00F6
//� \u006D
package local.ui.cui;

import java.util.List;

import local.domain.Spielfeld;
import local.domain.Kriegsverwaltung.phasen;
import local.domain.exceptions.*;
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
				sp.benutzteLaenderLoeschen();
				break;	
			}	
		}while(!gewonnen);
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
						System.out.println("Bitte w\u00E4hlen Sie einen anderen Namen!");
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
		System.out.println("\n" + spieler.getName() +" besitzt die L\u00E4nder: ");
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
		boolean genugEinheiten = false;
		boolean kannLandBenutzen = false;
		
		if(startPhase != true)
		{
		System.out.println("\n" + sp.getAktiverSpieler().getName() + " ist jetzt dran.");
		System.out.println("Phase: " + sp.getTurn());
		}
		
		cui.spielerstandAusgeben(spieler);
		
		while(einheitenAnzahl > 0) {
			do{
				System.out.println("Auf welches Land m\u00F6chtest du Einheiten setzen?");
				landString = IO.readString();
				try{
					kannLandBenutzen = sp.landWaehlen(landString,spieler);
				}catch(KannLandNichtBenutzenException lene ){
					System.out.println(lene.getMessage());
				}
			}while(!kannLandBenutzen);
			land = sp.stringToLand(landString);
			System.out.println("Wie viele Einheiten m\u00F6chtest du auf " + land.getName() + " setzen?");
			do{
				System.out.println("Du kannst " + einheitenAnzahl + " Einheiten setzen");
				einheiten = IO.readInt();
				try{
					genugEinheiten = sp.checkEinheitenVerteilen(einheiten,spieler);
				}catch(KannEinheitenNichtVerschiebenException cev){
					System.out.println(cev.getMessage());
				}
			}while(!genugEinheiten);
			sp.einheitenPositionieren(einheiten, land);
			System.out.println(land.getName() + " hat jetzt " + land.getEinheiten() + " Einheiten.");
			einheitenAnzahl -= einheiten;
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
				System.out.println(spieler.getName() + " mit welchem Land m\u00F6chtest du angreifen?");
				System.out.println(sp.eigeneAngriffsLaender(spieler));
				angriffsLandString = IO.readString();
					/* Bis ein Land eingegeben wird, das dem Spieler gehoert
					 * und genug Einheiten hat.*/	
				try{
					genugEinheiten = sp.landWaehlen(angriffsLandString,spieler);
					genugEinheiten = sp.checkEinheiten(angriffsLandString,1);
				}catch(KannLandNichtBenutzenException lene ){
					System.out.println(lene.getMessage());
				}catch(NichtGenugEinheitenException ngee){
					System.out.println(ngee.getMessage());
				}
			}while(!genugEinheiten);
			
			System.out.println(sp.moeglicheAngriffsziele(angriffsLandString, spieler));
			aLand = sp.stringToLand(angriffsLandString);
			sp.landBenutzen(aLand);
			
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
			}while(!gegnerNachbar);
			
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
							System.out.println(sp.einheitenAusgabe(aLand, vLand));
							genugEinheiten = true;
							//...
						} else {
							System.out.println("Wie viele Einheiten m\u00F6chtest du auf " + verteidigungsLandString + " setzen?");
							System.out.println(aLand.getEinheiten() - 1 + " Einheiten kannst du setzen");
							int einheiten = IO.readInt();
							if(einheiten < aLand.getEinheiten() && einheiten > 0){
								sp.eroberungBesetzen(aLand, vLand, einheiten); 
								System.out.println(sp.einheitenAusgabe(aLand, vLand));
								genugEinheiten = true;
							}else{
								System.out.println("Bitte gebe eine Korrekte Zahl ein");
							}
						}
							
					}while(!genugEinheiten);
				}else if(aLand.getEinheiten() < 2){
					System.out.println("Du kannst mit diesem Land nicht weiter angreifen.");
				}else{
					System.out.println("M\u00F6chtest du weiter angreifen? Ja/Nein");
					String selberAngriff = IO.readString();
					if(selberAngriff.equals("Ja"))
					{
						erneutAngreifen = true;
					}
				}
			}while(erneutAngreifen);
			
			System.out.println("M\u00F6chtest du mit einem weiteren Land angreifen?Ja/Nein");
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
		System.out.println("\nM\u00F6chtest du Einheiten verschieben? Ja/Nein");
		String antwort = IO.readString();
		
		if(antwort.equals("Ja")){
			
				System.out.println("Von welchem Land m\u00F6chtest du Einheiten verschieben?");
				//Zeigt alle Länder an, die benutzt werden können
				System.out.println(sp.eigeneVerschiebeLaender(spieler));
				//Läuft so lange, bis das erste Land korrekt ausgewählt wird
				do{
						wahlLand = IO.readString();
						try{
							sp.landWaehlen(wahlLand,spieler);
							sp.benutzeLaender(sp.stringToLand(wahlLand));
							kannLandBenutzen = sp.checkEinheiten(wahlLand,1);
						}catch(KannLandNichtBenutzenException lene ){
							System.out.println(lene.getMessage());
						}catch(NichtGenugEinheitenException ngee){
							System.out.println(ngee.getMessage());					
						}catch(LandBereitsBenutztException lbbe){
							System.out.println(lbbe.getMessage());
						}
					}while(!kannLandBenutzen);
				
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
					}while(!kannLandBenutzen);
					
					zweitesLand = sp.stringToLand(zielLand);
					System.out.println(sp.einheitenAusgabe(erstesLand, zweitesLand));
					// Solange, bis zu einer korrekten Eingabe einer m�glichen Anzahl an Einheiten
					do{
						System.out.println("Wie viele Einheiten m\u00F6chtest du verschieben?");
						einheiten = IO.readInt();
						
						try{
							genugEinheiten = sp.checkEinheiten(wahlLand, einheiten);
						}catch(NichtGenugEinheitenException ngee){
							System.out.println(ngee.getMessage());
						}
					}while(!genugEinheiten);
					
					sp.einheitenPositionieren(einheiten, zweitesLand);
					sp.einheitenPositionieren(-einheiten, erstesLand);
					System.out.println(sp.einheitenAusgabe(erstesLand, zweitesLand));
		}
	}
}

