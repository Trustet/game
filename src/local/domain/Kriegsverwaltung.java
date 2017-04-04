package local.domain;

import java.util.Collections;
import java.util.List;
import java.util.Vector;

import local.valueobjects.*;

public class Kriegsverwaltung {

Spielerverwaltung spielerVw;
Weltverwaltung weltVw;
	
	public Kriegsverwaltung(Spielerverwaltung spielerVw, Weltverwaltung weltVw) {
	this.spielerVw = spielerVw;
	this.weltVw = weltVw;
}

	public List<Land> moeglicheAngriffsziele(Land land, Spieler spieler)
	{
		List<Land> nachbarLaender = this.weltVw.getNachbarLaender(land);
		List<Land> angriffsZiele = new Vector<Land>();
		
		for(Land l : nachbarLaender)
		{
			if(!l.getBesitzer().equals(spieler))
			{
				angriffsZiele.add(l);
			}
		}
		
		return angriffsZiele;
	}
	
	public List<Integer> wuerfeln(int anzahl)
	{
		List<Integer> ergebnisse = new Vector<Integer>();
				
		for(int i = 0;i < anzahl;i++)
		{
			ergebnisse.add((int)(Math.random() * 6) + 1);
		}
		
		Collections.sort(ergebnisse);
		
		return ergebnisse;
	}
	
	
	public List<Integer> befreiungsAktion(Land angreifendesLand, Land verteidigendesLand)
	{
		int angreiferEinheiten = angreifendesLand.getEinheiten();
		int verteidigerEinheiten = verteidigendesLand.getEinheiten();
		int angreifendeEinheiten;
		int verteidigendeEinheiten;
		List<Integer> wuerfeAngreifer;
		List<Integer> wuerfeVerteidiger;
		List<Integer> verluste = new Vector<Integer>();
		
		if(angreiferEinheiten < 4)
		{
			angreifendeEinheiten = angreiferEinheiten - 1;
		}
		else
		{
			angreifendeEinheiten = 3;
		}
		
		if(verteidigerEinheiten < 2)
		{
			verteidigendeEinheiten = verteidigerEinheiten - 1;
		}
		else
		{
			verteidigendeEinheiten = 3;
		}
		
		wuerfeAngreifer = wuerfeln(angreifendeEinheiten);
		wuerfeVerteidiger = wuerfeln(verteidigendeEinheiten);
		
		if((wuerfeVerteidiger.size() == 1) && (wuerfeVerteidiger.get(0) < wuerfeAngreifer.get(1)))
		{
			 verluste.add(0);
			 verluste.add(1);
		}
		else if((wuerfeVerteidiger.size() == 2) && (wuerfeVerteidiger.get(0) < wuerfeAngreifer.get(1)) && (wuerfeVerteidiger.get(1) < wuerfeAngreifer.get(2)))
		{
			 verluste.add(0);
			 verluste.add(2);
		}
		else if(((wuerfeVerteidiger.get(0) < wuerfeAngreifer.get(1)) && (wuerfeVerteidiger.get(1) > wuerfeAngreifer.get(2))) && ((wuerfeVerteidiger.get(0) > wuerfeAngreifer.get(1)) && (wuerfeVerteidiger.get(1) < wuerfeAngreifer.get(2))))
		{
			 verluste.add(1);
			 verluste.add(1);	
		}
		else if((wuerfeVerteidiger.size() == 1) && (wuerfeVerteidiger.get(0) >= wuerfeAngreifer.get(1)))
		{
			 verluste.add(1);
			 verluste.add(0);
		}
		else if((wuerfeVerteidiger.size() == 2) && (wuerfeVerteidiger.get(0) >= wuerfeAngreifer.get(1)) && (wuerfeVerteidiger.get(1) >= wuerfeAngreifer.get(2)))
		{
			 verluste.add(2);
			 verluste.add(0);
		}
		//AngreiferVerlust / VerteidigerVerlust
		return verluste;
	}
		
	public void einheitenPositionieren(int anzahl, Land land)
	{
		land.setEinheiten(land.getEinheiten() + anzahl);
	}
}
