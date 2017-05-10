package local.domain;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

import local.domain.exceptions.SpielerExistiertBereitsException;
import local.persistence.FilePersistenceManager;
import local.valueobjects.Land;
import local.valueobjects.Spieler;

public class Spielerverwaltung {

	private List<Spieler> spielerListe = new Vector<Spieler>();
	private int spielerNummer = 0;
	private FilePersistenceManager pm = new FilePersistenceManager();
	
	public void speicherSpieler() throws IOException{
		pm.schreibkanalOeffnen("saveSpieler.txt");
		for(Spieler s : spielerListe){
			pm.speichereSpieler(s);
		}
		pm.close();
	}
	/**
	 * Gibt einen bestimmten Spieler aus der Liste zurück
	 * @param index
	 * @return Spieler
	 */
	public Spieler getSpieler(int index) {
		return this.spielerListe.get(index-1);
	}
	
	/**
	 * Gibt den SPieler zurück, der am Zug ist
	 * @return Spieler
	 */
	public Spieler getAktiverSpieler(){
		return this.spielerListe.get(spielerNummer);
	}
	
	public int getAktiverSpielerNummer(){
		return spielerNummer;
	}
	
	/**
	 * Setzt den neuenSpieler für die nächste Runde
	 */
	public void naechsterSpieler(){
		if(spielerNummer < this.spielerListe.size()-1){
			spielerNummer++;
		}else{
			spielerNummer = 0;
		}
	}
	/**
	 * Fügt einen neuen Spieler zu
	 * @param name
	 * @throws SpielerExistiertBereitsException
	 */
	public void neuerSpieler(String name) throws SpielerExistiertBereitsException {
		Spieler neuerSpieler = new Spieler(name);
		if (spielerListe.contains(neuerSpieler)) {
			throw new SpielerExistiertBereitsException(name);
		}
		spielerListe.add(neuerSpieler);
	}
	

	/**
	 * Gibt die SPielerliste zurück
	 * @return List<Spieler>
	 */
	public List<Spieler> getSpielerList() {
		return spielerListe;
	}
	
	public void setAktiverSpieler(int spieler){
		this.spielerNummer = spieler;
	}
	

}
