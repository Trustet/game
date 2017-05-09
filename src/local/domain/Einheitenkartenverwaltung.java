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
	
		//drei von einer Sorte
		if(soldat <= 3){
			kartenZumAbgeben.add(soldatKarte);
			kartenZumAbgeben.add(soldatKarte);
			kartenZumAbgeben.add(soldatKarte);
		}else if(pferd <= 3){
			kartenZumAbgeben.add(pferdKarte);
			kartenZumAbgeben.add(pferdKarte);
			kartenZumAbgeben.add(pferdKarte);
		}else if(panzer <= 3){
			kartenZumAbgeben.add(panzerKarte);
			kartenZumAbgeben.add(panzerKarte);
			kartenZumAbgeben.add(panzerKarte);
		}else if(soldat <= 1 && pferd <= 1 && panzer <= 1){
			kartenZumAbgeben.add(panzerKarte);
			kartenZumAbgeben.add(pferdKarte);
			kartenZumAbgeben.add(soldatKarte);
		}else if((panzer + joker) <=3){
			if(joker != 2)
			{
				kartenZumAbgeben.add(panzerKarte);
				kartenZumAbgeben.add(panzerKarte);
				kartenZumAbgeben.add(jokerKarte);
			}
			kartenZumAbgeben.add(panzerKarte);
			kartenZumAbgeben.add(jokerKarte);
			kartenZumAbgeben.add(jokerKarte);
		}else if((pferd + joker) <=3){
			if(joker != 2)
			{
				kartenZumAbgeben.add(pferdKarte);
				kartenZumAbgeben.add(pferdKarte);
				kartenZumAbgeben.add(jokerKarte);
			}
			kartenZumAbgeben.add(pferdKarte);
			kartenZumAbgeben.add(jokerKarte);
			kartenZumAbgeben.add(jokerKarte);
		}else if((soldat + joker) <=3){ 
			if(joker != 2)
			{
				kartenZumAbgeben.add(soldatKarte);
				kartenZumAbgeben.add(soldatKarte);
				kartenZumAbgeben.add(jokerKarte);
			}
			kartenZumAbgeben.add(soldatKarte);
			kartenZumAbgeben.add(jokerKarte);
			kartenZumAbgeben.add(jokerKarte);
		}else if(soldat <= 1 && pferd <= 1 && joker <= 1){
			kartenZumAbgeben.add(jokerKarte);
			kartenZumAbgeben.add(pferdKarte);
			kartenZumAbgeben.add(soldatKarte);
		}else if(soldat <= 1 && joker <= 1 && panzer <= 1){
			kartenZumAbgeben.add(panzerKarte);
			kartenZumAbgeben.add(jokerKarte);
			kartenZumAbgeben.add(soldatKarte);
		}else if(joker <= 1 && pferd <= 1 && panzer <= 1){
			kartenZumAbgeben.add(panzerKarte);
			kartenZumAbgeben.add(pferdKarte);
			kartenZumAbgeben.add(jokerKarte);
		}else if(joker == 2 && panzer <= 1){
			kartenZumAbgeben.add(panzerKarte);
			kartenZumAbgeben.add(jokerKarte);
			kartenZumAbgeben.add(jokerKarte);
		}else if(joker == 2 && pferd <= 1){
			kartenZumAbgeben.add(pferdKarte);
			kartenZumAbgeben.add(pferdKarte);
			kartenZumAbgeben.add(jokerKarte);
		}else if(joker == 2 && soldat <= 1){
			kartenZumAbgeben.add(soldatKarte);
			kartenZumAbgeben.add(soldatKarte);
			kartenZumAbgeben.add(jokerKarte);
		}else{
			return false;	
		}
		//TODO tauscht bisher automatisch ein, sobald möglich
		einheitenKartenVonSpielerEntfernen(spieler, kartenZumAbgeben);
		return true;
	}
	
	private void einheitenKartenVonSpielerEntfernen(Spieler spieler, List<Einheitenkarten> benutzteKarten)
	{
		for(Einheitenkarten karteAbgeben : benutzteKarten)
		{
			for(int i = 0; i < spieler.getEinheitenkarten().size();i++)
			{
				if(spieler.getEinheitenkarten().get(i).getKartenwert().equals(karteAbgeben.getKartenwert()))
					{
						spieler.getEinheitenkarten().remove(i);
						break;
					}
			}
		}
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
