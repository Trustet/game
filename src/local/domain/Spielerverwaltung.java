package local.domain;
import java.util.List;
import java.util.Vector;

import local.domain.exceptions.SpielerExistiertBereitsException;
import local.valueobjects.Land;
import local.valueobjects.Spieler;

public class Spielerverwaltung {

	private List<Spieler> spielerListe = new Vector<Spieler>();
	private int spielerNummer = 0;
	/**
	 * @param index
	 * @return Spieler
	 */
	public Spieler getSpieler(int index) {
		return this.spielerListe.get(index-1);
	}
	
	public Spieler getAktiverSpieler(){
		return this.spielerListe.get(spielerNummer);
	}
	
	public void naechsterSpieler(){
		if(spielerNummer < this.spielerListe.size()-1){
			spielerNummer++;
		}else{
			spielerNummer = 0;
		}
	}
	/**
	 * @param name
	 */
	public void neuerSpieler(String name) throws SpielerExistiertBereitsException {
		Spieler neuerSpieler = new Spieler(name);
		if (spielerListe.contains(neuerSpieler)) {
			throw new SpielerExistiertBereitsException(name);
		}
		spielerListe.add(neuerSpieler);
	}
	

	/**
	 * @return List<Spieler>
	 */
	public List<Spieler> getSpielerList() {
		return spielerListe;
	}

}
