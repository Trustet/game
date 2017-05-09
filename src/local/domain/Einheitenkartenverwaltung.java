package local.domain;

import java.util.Collections;
import java.util.List;
import java.util.Vector;

import local.valueobjects.*;

public class Einheitenkartenverwaltung {

	private List<Einheitenkarten> kartenstapel;
	
	public List<Einheitenkarten> kartenstapelErstellen()
	{
		kartenstapel = new Vector<Einheitenkarten>();
		for(int i = 0;i < 14;i++)
		{
			kartenstapel.add(new Einheitenkarten("Soldat"));
			kartenstapel.add(new Einheitenkarten("Pferd"));
			kartenstapel.add(new Einheitenkarten("Panzer"));
		}
		
		kartenstapel.add(new Einheitenkarten("Joker"));
		kartenstapel.add(new Einheitenkarten("Joker"));
		
		Collections.shuffle(kartenstapel);
		
		return kartenstapel;
	}
	
	public void karteNehmen(Spieler spieler)
	{
		Einheitenkarten karte = kartenstapel.get(0);
		spieler.karteNehmen(karte);
		kartenstapel.remove(0);
	}
	
	public void spielerkartenAuswerten(Spieler spieler)
	{
		for(Einheitenkarten k : spieler.getEinheitenkarten())
		{
			//TODO auswertung
		}
			
	}
}
