package local.domain;

import java.util.Collections;
import java.util.List;
import java.util.Vector;

import local.valueobjects.*;

public class Einheitenkartenverwaltung {

	private List<Einheitenkarten> kartenstapel;
	private int kartenEingeloest;
	
	
	public Einheitenkartenverwaltung() {
		kartenstapelErstellen();
	}

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
	
	public Einheitenkarten karteNehmen(Spieler spieler)
	{
		Einheitenkarten karte = kartenstapel.remove(0);
		spieler.karteNehmen(karte);
//		kartenstapel.remove(0);
		
		return karte;
	}
	
	public boolean spielerkartenAuswerten(Spieler spieler)
	{
		/*
		Bitvektor für Soldat/Kanone/Pferd: 
			100
			010
			001
			OR: 111
			
			111
			111
			111
			OR: 111
			*/
		int soldat = 0;
		int pferd = 0;
		int panzer = 0;
		int joker = 0;
		List<Einheitenkarten> kartenZumAbgeben = new Vector<Einheitenkarten>();
		Einheitenkarten soldatKarte = new Einheitenkarten("Soldat");
		Einheitenkarten pferdKarte = new Einheitenkarten("Pferd");
		Einheitenkarten panzerKarte = new Einheitenkarten("Panzer");
		Einheitenkarten jokerKarte = new Einheitenkarten("Joker");
		
		for(Einheitenkarten k : spieler.getEinheitenkarten())
		{
			if(k.getKartenwert().equals("Soldat")){
				soldat++;
			}else if(k.getKartenwert().equals("Pferd")){
				pferd++;
			}else if(k.getKartenwert().equals("Panzer")){
				panzer++;
			}else if(k.getKartenwert().equals("Joker")){
				joker++;
			}
		}
	
		if(soldat >= 3){
			kartenZumAbgeben.add(soldatKarte);
			kartenZumAbgeben.add(soldatKarte);
			kartenZumAbgeben.add(soldatKarte);
		}else if(pferd >= 3){
			kartenZumAbgeben.add(pferdKarte);
			kartenZumAbgeben.add(pferdKarte);
			kartenZumAbgeben.add(pferdKarte);
		}else if(panzer >= 3){
			kartenZumAbgeben.add(panzerKarte);
			kartenZumAbgeben.add(panzerKarte);
			kartenZumAbgeben.add(panzerKarte);
		}else if(soldat >= 1 && pferd >= 1 && panzer >= 1){
			kartenZumAbgeben.add(panzerKarte);
			kartenZumAbgeben.add(pferdKarte);
			kartenZumAbgeben.add(soldatKarte);
		}else if((panzer + joker) >=3){
			if(joker != 2)
			{
				kartenZumAbgeben.add(panzerKarte);
				kartenZumAbgeben.add(panzerKarte);
				kartenZumAbgeben.add(jokerKarte);
			}
			kartenZumAbgeben.add(panzerKarte);
			kartenZumAbgeben.add(jokerKarte);
			kartenZumAbgeben.add(jokerKarte);
		}else if((pferd + joker) >=3){
			if(joker != 2)
			{
				kartenZumAbgeben.add(pferdKarte);
				kartenZumAbgeben.add(pferdKarte);
				kartenZumAbgeben.add(jokerKarte);
			}
			kartenZumAbgeben.add(pferdKarte);
			kartenZumAbgeben.add(jokerKarte);
			kartenZumAbgeben.add(jokerKarte);
		}else if((soldat + joker) >=3){ 
			if(joker != 2)
			{
				kartenZumAbgeben.add(soldatKarte);
				kartenZumAbgeben.add(soldatKarte);
				kartenZumAbgeben.add(jokerKarte);
			}
			kartenZumAbgeben.add(soldatKarte);
			kartenZumAbgeben.add(jokerKarte);
			kartenZumAbgeben.add(jokerKarte);
		}else if(soldat >= 1 && pferd >= 1 && joker >= 1){
			kartenZumAbgeben.add(jokerKarte);
			kartenZumAbgeben.add(pferdKarte);
			kartenZumAbgeben.add(soldatKarte);
		}else if(soldat >= 1 && joker >= 1 && panzer >= 1){
			kartenZumAbgeben.add(panzerKarte);
			kartenZumAbgeben.add(jokerKarte);
			kartenZumAbgeben.add(soldatKarte);
		}else if(joker >= 1 && pferd >= 1 && panzer >= 1){
			kartenZumAbgeben.add(panzerKarte);
			kartenZumAbgeben.add(pferdKarte);
			kartenZumAbgeben.add(jokerKarte);
		}else if(joker == 2 && panzer >= 1){
			kartenZumAbgeben.add(panzerKarte);
			kartenZumAbgeben.add(jokerKarte);
			kartenZumAbgeben.add(jokerKarte);
		}else if(joker == 2 && pferd >= 1){
			kartenZumAbgeben.add(pferdKarte);
			kartenZumAbgeben.add(pferdKarte);
			kartenZumAbgeben.add(jokerKarte);
		}else if(joker == 2 && soldat >= 1){
			kartenZumAbgeben.add(soldatKarte);
			kartenZumAbgeben.add(soldatKarte);
			kartenZumAbgeben.add(jokerKarte);
		}else{
			return false;	
		}
		//TODO tauscht bisher automatisch ein, sobald mÃ¶glich
		einheitenKartenVonSpielerEntfernen(spieler, kartenZumAbgeben);
		return true;
	}
	
	private void einheitenKartenVonSpielerEntfernen(Spieler spieler, List<Einheitenkarten> benutzteKarten)
	{
		List<Einheitenkarten> kartenListe = spieler.getEinheitenkarten();
		for (Einheitenkarten karte: benutzteKarten) {
			kartenListe.remove(karte);
		}

//		for(Einheitenkarten karteAbgeben : benutzteKarten)
//		{
//			for(int i = 0; i < spieler.getEinheitenkarten().size();i++)
//			{
//				if(spieler.getEinheitenkarten().get(i).equals(karteAbgeben))
//					{
//						spieler.getEinheitenkarten().remove(i);
//						break;
//					}
//			}
//		}
	}
	
	public int einheitenkartenEinloesen(Spieler spieler)
	{
		if(spielerkartenAuswerten(spieler))
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
		return 0;
	}
}
