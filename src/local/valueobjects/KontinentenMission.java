package local.valueobjects;

import java.util.List;

public class KontinentenMission extends Mission {

	private Spieler spieler;
	private List<Kontinent> kontinente;
	private List<Land> laender;
	public KontinentenMission(int id, Spieler spieler, List<Kontinent> kontinente, List<Land> laender) {
		super(id, "Erobern Sie folgene Kontinente : " +  kontinente.toString() ,spieler);

		this.spieler = spieler;
		this.laender = laender;
		this.kontinente = kontinente;
	}

	public boolean istAbgeschlossen() {
		int counter = 0;
		int anzahlLaender = laender.size();
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
