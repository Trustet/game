package local.domain;

import java.util.Collections;
import java.util.List;
import java.util.Vector;

import local.valueobjects.*;

public class Einheitenkartenverwaltung {

	private List<Einheitenkarten> kartenstapel;
	private int kartenEingeloest;
	
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
	
	public boolean spielerkartenAuswerten(Spieler spieler)
	{
		for(Einheitenkarten k : spieler.getEinheitenkarten())
		{
			//TODO auswertung
		}
		return false;		
	}
	
	public int einheitenkartenEinloesen()
	{
		int einheiten = 0;
		switch(kartenEingeloest){
		case 1: einheiten = 4;
				break;
		case 2: einheiten = 6;
				break;
		case 3: einheiten = 8;
				break;
		case 4: einheiten = 10;
				break;
		case 5: einheiten = 12;
				break;
		case 6: einheiten = 15;
				break;
		}
		if(kartenEingeloest > 6)
		{
			einheiten = 15 + (kartenEingeloest - 6) * 5;
		}
		
		kartenEingeloest++;
		
		return einheiten;
	}
}
