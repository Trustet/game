//� \u00DF
//� \u00FC
//� \u00DC
//� \u00E4
//� \u00C4
//� \u00F6
//� \u006D
package local.ui.cui;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

import local.domain.Spielfeld;
import local.domain.Kriegsverwaltung.phasen;
import local.domain.exceptions.*;
import local.valueobjects.*;


public class RisikoClientCUI {
	
	//Domain-Komponente, welche die Verwaltungen verwaltet
	private Spielfeld sp;
	//private  phasen Phase;
	//private boolean gewonnen = false;
	private boolean startPhase;
	
	public RisikoClientCUI() throws IOException{
		sp = new Spielfeld();
	}
	/**
	 * Main-Methode der CUI
	 * @param args
	 * @throws IOException 
	 * @throws SpielerExistiertBereitsException 
	 */
	public static void main(String[] args) throws IOException, SpielerExistiertBereitsException {
		
		RisikoClientCUI cui = new RisikoClientCUI();
		cui.spielStarten(cui);	
		cui.spielen(cui);
		
	}

	public void spielen(RisikoClientCUI cui) throws IOException, SpielerExistiertBereitsException{
		//Phasenablauf
				boolean gewonnen = false;
				sp.speicherSpieler();
				sp.speicherLaender();
				do{
					Spieler spieler = sp.getAktiverSpieler();
					
					//Zum testen
//					Mission ms = new LaenderMission(spieler,3,3,sp.getLaenderListe());
//					System.out.println(ms.getBeschreibung());
					switch(sp.getTurn()){
					case VERTEILEN:
						cui.einheitenVerteilen(spieler, cui);
						sp.nextTurn();
						break;
					case ANGRIFF:
						boolean kannAngreifen = false;
						try{
							kannAngreifen = sp.landZumAngreifen(spieler);
						}catch (KeinLandZumAngreifenException klzae){
							System.out.println(klzae.getMessage());
						}
						if(kannAngreifen){
							cui.angreifen(spieler, cui);
						}	
						sp.nextTurn();
//						Zum testen
//						System.out.println("In welcher datei soll das Spiel gespeichert werden?");
//						String antwort = IO.readString();
//						sp.spielSpeichern(antwort);
						
						break;
					case VERSCHIEBEN:
						cui.verschieben(spieler, cui);
						sp.nextTurn();
						//TODO Wenn spieler erobert wurde, aus allem rausnehmen
						sp.naechsterSpieler();
						sp.benutzteLaenderLoeschen();
						break;	
					}	
					//zum testen
//					gewonnen = ms.istAbgeschlossen();
				}while(!gewonnen);
				System.out.println("Du hast gewonnen");
	}
	
	/**
	 * startet das Spiel
	 * @throws IOException 
	 */
	private void spielStarten(RisikoClientCUI cui) throws IOException	{
//		List<String> willkommen = sp.willkommenNachricht();
//		for(String s : willkommen){
//			System.out.print(s);
//			try {
//			    Thread.sleep(300);
//			} catch(InterruptedException ex) {
//			    Thread.currentThread().interrupt();
//			}
//		}
//		System.out.println("");
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
		sp.missionsListeErstellen();
		sp.missionenVerteilen();
		//TODO hier ist von den Spielregeln noch ein Fehler der Anzahl von zu verteilender Einheiten
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
	 * @param Spieler
	 */
	public void spielerstandAusgeben(Spieler spieler, RisikoClientCUI cui) {
		System.out.println("\n" + spieler.getName() +" besitzt die L\u00E4nder: ");
		System.out.println(cui.eigeneLaender(spieler));
		System.out.println("\nund bekommt " + sp.bekommtEinheiten(spieler) + " Einheiten\n");
		if(startPhase){
			System.out.println(spieler.getName() + " du hast die Mission: \n" + sp.missionAusgeben(spieler) + "\n\n");
		}
	}

	/**
	 * Spieler kann Einheiten auf eigene Länder verteilen
	 * @param Spieler
	 */
	public void einheitenVerteilen(Spieler spieler,RisikoClientCUI cui)	{
		int einheitenAnzahl = sp.bekommtEinheiten(spieler);
		String landString;
		Land land;
		int einheiten;
		boolean genugEinheiten = false;
		boolean kannLandBenutzen = false;
		
		//TODO Karten einlösen aufrufen
		if(startPhase != true)
		{
		System.out.println("\n" + sp.getAktiverSpieler().getName() + " ist jetzt dran.");
		System.out.println("Phase: " + sp.getTurn());
		}
		
		cui.spielerstandAusgeben(spieler,cui);
		
		while(einheitenAnzahl > 0) {
			genugEinheiten = false;
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
					genugEinheiten = sp.checkEinheitenVerteilen(einheiten,einheitenAnzahl,spieler);
				}catch(KannEinheitenNichtVerschiebenException cev){
					System.out.println(cev.getMessage());
				}
			}while(!genugEinheiten);
			sp.einheitenPositionieren(einheiten, land);
			System.out.println(land.getName() + " hat jetzt " + land.getEinheiten() + " Einheiten.\n\n");
			einheitenAnzahl -= einheiten;
		}
	}


	/**
	 * Angriffphase
	 * @param Spieler
	 */
	public void angreifen(Spieler spieler, RisikoClientCUI cui) {
		Land aLand = null;
		Land vLand = null;
		boolean genugEinheiten = false;
		boolean phaseBeendet = false;
		String weiterangreifen;
		
		System.out.println("Phase: " + sp.getTurn());
		//TODO falls kein Land zum angreifen, fehlermeldung und weiter
		aLand = cui.angriffslandAbfrage(cui, spieler, genugEinheiten);
		
		do{
			System.out.println(sp.moeglicheAngriffsziele(aLand));
			sp.landBenutzen(aLand);
			vLand = cui.verteidigendesLandAbfrage(spieler, aLand);
			cui.angriffAusgabeUndErneutAngreifenAbfrage(aLand, vLand, genugEinheiten);
				
			System.out.println("Möchtest du mit einem weiteren Land angreifen?Ja/Nein");
			weiterangreifen = IO.readString();
			if(!weiterangreifen.equalsIgnoreCase("Ja")){
				phaseBeendet = true;
			}
		}while(!phaseBeendet);
	}
	
	//Ab hier Unterfunktionen für die Angriffphase

	
	private Land angriffslandAbfrage(RisikoClientCUI cui, Spieler spieler, boolean genugEinheiten){
		String angriffsLandString;
		Land aLand;
		
		do{ 	
			System.out.println(spieler.getName() + " mit welchem Land m\u00F6chtest du angreifen?");
			System.out.println(cui.laenderZumAngreifen(spieler));
			angriffsLandString = IO.readString();
				/* Bis ein Land eingegeben wird, das dem Spieler gehoert
				 * und genug Einheiten hat.*/	
			try{
				sp.landWaehlen(angriffsLandString,spieler);
				genugEinheiten = sp.checkEinheiten(angriffsLandString,1);
				sp.landZumAngreifen(spieler, sp.stringToLand(angriffsLandString));
			}catch(KeinLandZumAngreifenException klzae){
				System.out.println(klzae.getMessage());
			}catch(KannLandNichtBenutzenException lene ){
				System.out.println(lene.getMessage());
			}catch(NichtGenugEinheitenException ngee){
				System.out.println(ngee.getMessage());
			}
		}while(!genugEinheiten);
		
		aLand = sp.stringToLand(angriffsLandString);
		
		return aLand;
	}
	
	private Land verteidigendesLandAbfrage(Spieler spieler, Land aLand){
		String verteidigungsLandString;
		Land vLand = new Land("Platzhalter", new Spieler("PLatzhalter"), 0,"Platzhalter");
		boolean gegnerNachbar = false;
		
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
		
		return vLand;
	}
	
	private void angriffAusgabeUndErneutAngreifenAbfrage(Land aLand, Land vLand, boolean genugEinheiten){
		boolean erneutAngreifen;
		Angriff angriff;
		AngriffRueckgabe angriffRueckgabe;
		do {
			erneutAngreifen = false;	
			
			angriff = new Angriff(aLand, vLand);
			angriffRueckgabe = sp.befreiungsAktion(angriff);
			
			System.out.print(aLand.getBesitzer().getName() + " hat ");
			
			if(angriffRueckgabe.getWuerfelAngreifer().size() == 2)
			{
				System.out.print(angriffRueckgabe.getWuerfelAngreifer().get(0) + " und " + angriffRueckgabe.getWuerfelAngreifer().get(1));
			} else if (angriffRueckgabe.getWuerfelAngreifer().size() == 3)
			{
				System.out.print(angriffRueckgabe.getWuerfelAngreifer().get(0) + ", " + angriffRueckgabe.getWuerfelAngreifer().get(1) + " und " + angriffRueckgabe.getWuerfelAngreifer().get(2));
			}
				System.out.print(" gew�rfelt.\n");
			
			System.out.print(vLand.getBesitzer().getName() + " hat ");
				
			if(angriffRueckgabe.getWuerfelVerteidiger().size() == 1)
			{
				System.out.print(" eine " + angriffRueckgabe.getWuerfelVerteidiger().get(0));
			} else if (angriffRueckgabe.getWuerfelVerteidiger().size() == 2)
			{
				System.out.print(angriffRueckgabe.getWuerfelVerteidiger().get(0) + " und " + angriffRueckgabe.getWuerfelAngreifer().get(1));
			}
			
			System.out.print(" gew�rfelt.\n");
				
			if(angriffRueckgabe.isErobert() != true){
					
				if (angriffRueckgabe.getVerlusteVerteidiger() < angriffRueckgabe.getVerlusteAngreifer()){
						System.out.println(vLand.getBesitzer().getName() + " hat gewonnen." );
						System.out.println(aLand.getBesitzer().getName() + " hat " + angriffRueckgabe.getVerlusteAngreifer() + " verloren.");
						
				}else if(angriffRueckgabe.getVerlusteVerteidiger() > angriffRueckgabe.getVerlusteAngreifer()){
						System.out.println(aLand.getBesitzer().getName() + " hat gewonnen." );
						System.out.println(vLand.getBesitzer().getName() + " hat " + angriffRueckgabe.getVerlusteVerteidiger() + " verloren.");
						
				}else if(angriffRueckgabe.getVerlusteVerteidiger() == angriffRueckgabe.getVerlusteAngreifer()){
						System.out.println("Ihr habt unentschieden gespielt, beide verlieren eine Einheit." );
				}
				
				if (aLand.getEinheiten() < 2){
					System.out.println("Du kannst mit diesem Land nicht weiter angreifen.");
					
				} else {
					System.out.println("M\u00F6chtest du weiter angreifen? Ja/Nein");
					String selberAngriff = IO.readString();
					if(selberAngriff.equalsIgnoreCase("Ja"))
					{
						erneutAngreifen = true;
					}
				}
			} else {
				System.out.println(aLand.getBesitzer().getName() + " hat das Land erobert." );
				genugEinheiten = false;
					if(aLand.getEinheiten() == 2) {
						System.out.println("Eine Einheit wird auf " + vLand.getName() + " gesetzt.");
						sp.eroberungBesetzen(aLand, vLand, 1); 
						System.out.println(sp.einheitenAusgabe(aLand, vLand));
						genugEinheiten = true;
					} else {
						do{
						System.out.println("Wie viele Einheiten m\u00F6chtest du auf " + vLand.getName() + " setzen?");
						System.out.println(aLand.getEinheiten() - 1 + " Einheiten kannst du setzen");
						int einheiten = IO.readInt();
						if(einheiten < aLand.getEinheiten() && einheiten > 0){
							sp.eroberungBesetzen(aLand, vLand, einheiten); 
							System.out.println(sp.einheitenAusgabe(aLand, vLand));
							genugEinheiten = true;
						}else{
							System.out.println("Bitte gebe eine Korrekte Zahl ein");
						}
					}while(!genugEinheiten);
						
						
				}
			}
		}while(erneutAngreifen);
	}
	
	/**
	 * Verschiebenphase
	 * @param Spieler
	 */
	public void verschieben(Spieler spieler,RisikoClientCUI cui){
		int einheiten;
		Land erstesLand = null;
		Land zweitesLand = null;
		String wahlLand;
		boolean kannLandBenutzen = false;
		boolean genugEinheiten = false;
		String zielLand;
		Einheitenkarten gezogeneKarte;
		
		System.out.println("Phase: " + sp.getTurn());
		System.out.println("\nM\u00F6chtest du Einheiten verschieben? Ja/Nein");
		String antwort = IO.readString();
		
		if(antwort.equalsIgnoreCase("Ja")){
			
				System.out.println("Von welchem Land m\u00F6chtest du Einheiten verschieben?");
				//Zeigt alle Länder an, die benutzt werden können
				System.out.println(cui.laenderZumVerschieben(spieler));
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
							sp.landWaehlen(zielLand, spieler);
							kannLandBenutzen = sp.istNachbar(erstesLand, sp.stringToLand(zielLand), spieler);
						}catch(KannLandNichtBenutzenException klnbe){
							System.out.println(klnbe.getMessage());
						}catch(KeinNachbarlandException kne){
							System.out.println(kne.getMessage());
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
		
		//Einheiten Karte ziehen
		gezogeneKarte = sp.einheitenKarteZiehen(spieler);
		System.out.println("Du hast die Karte " + gezogeneKarte.getKartenwert() + " gezogen.");
		System.out.println("Du besitzt nun die Karten :");
		for(Einheitenkarten k : spieler.getEinheitenkarten())
		{
			System.out.print(" " + k.getKartenwert());
		}
	}
	public String laenderZumVerschieben(Spieler spieler){
		String ausgabe;
		String puffer;
		List<Land> laenderListe = sp.eigeneVerschiebeLaender(spieler);			
		ausgabe = "\n        Land            |   Einheiten   \n------------------------|---------------\n";
		for(Land l : laenderListe) {
				puffer = l.getName();
				while(puffer.length() < 24){
					puffer += " ";
				}
				puffer += "|";
				while(puffer.length() < 30){
					puffer += " ";
				}
				ausgabe += puffer + l.getEinheiten() + "\n";
			}
		return ausgabe;
	}
	public String laenderZumAngreifen(Spieler spieler){
		List<Land> laenderListe = sp.eigeneAngriffsLaender(spieler);
		String ausgabe;
		String puffer;
					
		ausgabe = "\n        Land            |   Einheiten   \n------------------------|---------------\n";
		for(Land land : laenderListe) {
			if(spieler.equals(land.getBesitzer()) && land.getEinheiten() > 1) {
				puffer = land.getName();
				while(puffer.length() < 24){
					puffer += " ";
				}
				puffer += "|";
				while(puffer.length() < 30){
					puffer += " ";
				}
				ausgabe += puffer + land.getEinheiten() + "\n";
			}
		}
		return ausgabe;
	}
	public String eigeneLaender(Spieler spieler){
		String ausgabe;
		String puffer;
					
		ausgabe = "\n        Land            |   Einheiten   \n------------------------|---------------\n";
		for(Land land : sp.getLaenderListe()) {
			if(spieler.equals(land.getBesitzer())) {
				puffer = land.getName();
				while(puffer.length() < 24){
					puffer += " ";
				}
				puffer += "|";
				while(puffer.length() < 30){
					puffer += " ";
				}
				ausgabe += puffer + land.getEinheiten() + "\n";
			}
		}
		return ausgabe;
	}
	public String moeglicheAngriffszieleAusgabe(Land land) {
		List<Land> ziele = new Vector<Land>();
		ziele = sp.moeglicheAngriffsziele(land);
		String ausgabe;
		String puffer;
		Spieler spieler = land.getBesitzer();
		ausgabe = "\n        Land            |   Einheiten   \n------------------------|---------------\n";
			for(Land l : ziele){
				puffer = l.getName();
				while(puffer.length() < 24){
					puffer += " ";
				}
				puffer += "|";
				while(puffer.length() < 30){
					puffer += " ";
				}
				ausgabe += puffer + l.getEinheiten() + "\n";
			}
		return ausgabe;
	}
}

