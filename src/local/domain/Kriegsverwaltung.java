package local.domain;

import java.util.Collections;
import java.util.List;
import java.util.Vector;

import local.valueobjects.*;

public class Kriegsverwaltung {

private Spielerverwaltung spielerVw;
private Weltverwaltung weltVw;
	
	/**
	 * Konstruktor Kriegsverwaltung
	 * @param spielerVw
	 * @param weltVw
	 */
	public Kriegsverwaltung(Spielerverwaltung spielerVw, Weltverwaltung weltVw) {
	this.spielerVw = spielerVw;
	this.weltVw = weltVw;
	}

	/**
	 * gibt Nachbarlaender die angegriffen werden können zurück
	 * @param land
	 * @param spieler
	 * @return Vector<Land>
	 */
	public String moeglicheAngriffsziele(String landString, Spieler spieler) {
		Land land = weltVw.stringToLand(landString);
		List<Land> nachbarLaender = this.weltVw.getNachbarLaender(land);
		String nachbarLaenderString = "\nDu kannst mit " + landString + " folgende Länder angreifen: ";
		
		for(Land l : nachbarLaender) {
			if(!l.getBesitzer().equals(spieler)) {
				nachbarLaenderString += l.getName() + " | ";
			}
		}	
		
		return nachbarLaenderString;	
	}
	
	/**
	 * würfelt mehrfach und gibt Liste mit Ergebnissen zurück
	 * @param anzahl
	 * @return Vector<Integer>
	 */
	public List<Integer> wuerfeln(int anzahl) {
		List<Integer> ergebnisse = new Vector<Integer>();
				
		for(int i = 0;i < anzahl;i++) {
			ergebnisse.add((int)(Math.random() * 6) + 1);
		}
		Collections.sort(ergebnisse);
		
		return ergebnisse;
	}
	
	/**
	 * greift mit einem Land ein anderes an und gibt Verluste zurück
	 * @param angreifendesLand
	 * @param verteidigendesLand
	 * @return Vector<Integer> Verluste
	 */
	public String befreiungsAktion(String angreifendesLandString, String verteidigendesLandString) {
		Land angreifendesLand = weltVw.stringToLand(angreifendesLandString);
		Land verteidigendesLand = weltVw.stringToLand(verteidigendesLandString);
		int angreiferEinheiten = angreifendesLand.getEinheiten();
		int verteidigerEinheiten = verteidigendesLand.getEinheiten();
		int angreifendeEinheiten;
		int verteidigendeEinheiten;
		List<Integer> wuerfeAngreifer;
		List<Integer> wuerfeVerteidiger;
		List<Integer> verluste = new Vector<Integer>();
		String ausgabeString = "";
		
		if(angreiferEinheiten < 4) {
			angreifendeEinheiten = angreiferEinheiten - 1;
		} else {
			angreifendeEinheiten = 3;
		}
		
		if(verteidigerEinheiten < 2) {
			verteidigendeEinheiten = verteidigerEinheiten;
		} else {
			verteidigendeEinheiten = 2;
		}
		
		wuerfeAngreifer = wuerfeln(angreifendeEinheiten);
		wuerfeVerteidiger = wuerfeln(verteidigendeEinheiten);
		
		if((wuerfeVerteidiger.size() == 1) && (wuerfeVerteidiger.get(0) < wuerfeAngreifer.get(1))) {
			 verluste.add(0);
			 verluste.add(1);
		} else if((wuerfeVerteidiger.size() == 2) && (wuerfeVerteidiger.get(0) < wuerfeAngreifer.get(1)) && (wuerfeVerteidiger.get(1) < wuerfeAngreifer.get(2))) {
			 verluste.add(0);
			 verluste.add(2);
		} else if(((wuerfeVerteidiger.get(0) < wuerfeAngreifer.get(1)) && (wuerfeVerteidiger.get(1) > wuerfeAngreifer.get(2))) && ((wuerfeVerteidiger.get(0) > wuerfeAngreifer.get(1)) && (wuerfeVerteidiger.get(1) < wuerfeAngreifer.get(2)))) {
			 verluste.add(1);
			 verluste.add(1);	
		} else if((wuerfeVerteidiger.size() == 1) && (wuerfeVerteidiger.get(0) >= wuerfeAngreifer.get(1))) {
			 verluste.add(1);
			 verluste.add(0);
		} else if((wuerfeVerteidiger.size() == 2) && (wuerfeVerteidiger.get(0) >= wuerfeAngreifer.get(1)) && (wuerfeVerteidiger.get(1) >= wuerfeAngreifer.get(2))) {
			 verluste.add(2);
			 verluste.add(0);
		}
		//verluste ist ein Vector mit den Angaben: AngreiferVerlust / VerteidigerVerlust
		angreifendesLand.setEinheiten(angreiferEinheiten - verluste.get(0));
		verteidigendesLand.setEinheiten(verteidigerEinheiten - verluste.get(1));

		if(verteidigendesLand.getEinheiten() == 0) {
			ausgabeString += "Land erobert! " + verteidigendesLandString + " gehört jetzt " + angreifendesLand.getBesitzer().getName();
			verteidigendesLand.setBesitzer(angreifendesLand.getBesitzer());
			angreifendesLand.setEinheiten(angreifendesLand.getEinheiten() - 1);
			verteidigendesLand.setEinheiten(verteidigendesLand.getEinheiten() - 1);
		} else if(verluste.get(0) < verluste.get(1)) {
			ausgabeString += angreifendesLand.getBesitzer().getName() + " hat gewonnen ";
		} else if(verluste.get(0) == verluste.get(1)) {
			ausgabeString += "Unentschieden! Beide verlieren eine Einheit. ";
		} else if(verluste.get(0) > verluste.get(1)) {
			ausgabeString += verteidigendesLand.getBesitzer().getName() + " hat gewonnen ";
		}
		
		ausgabeString += "\n" + angreifendesLandString + " hat nun noch " + angreifendesLand.getEinheiten() + " und " + verteidigendesLandString + " hat nun noch " + verteidigendesLand.getEinheiten();
				
		return ausgabeString;
	}
	
	/**
	 * setzt eine gewisse Anzahl an Einheiten auf ein Land
	 * @param anzahl
	 * @param land
	 */
	public void einheitenPositionieren(int anzahl, Land land) {
		land.setEinheiten(land.getEinheiten() + anzahl);
	}
}
