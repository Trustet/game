package local.valueobjects;

import java.util.List;

public class KontinentenMission extends Mission {

	private int anzahlLaender = 0;
	private int anzahlEinheiten = 0;	
	private Spieler spieler;
	private List<Land> laender;
	
	public KontinentenMission(Spieler spieler, List<Kontinent> kontinente, List<Land> laender) {
		super("Erobern Sie folgene Kontinente : " +  kontinente.toString() ,spieler);
		this.anzahlLaender = anzahlLaender;
		this.anzahlEinheiten = anzahlEinheiten;
		this.spieler = spieler;
		this.laender = laender;
	}

	public boolean istAbgeschlossen() {
		int counter = 0;
		for(Land l : laender){
			if(l.getBesitzer().equals(spieler)){
				counter++;
			}
		}
		if(counter >= anzahlLaender){
			return true;
		}
		
		return false;
	}

}
