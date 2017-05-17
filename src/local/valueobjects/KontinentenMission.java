package local.valueobjects;

import java.util.List;
import java.util.Vector;

public class KontinentenMission extends Mission {

	private List<Kontinent> kontinente;
	
	public KontinentenMission(int id, Spieler spieler, List<Kontinent> kontinente) {
		super(id, "Erobern Sie folgene Kontinente : " +  kontinente.toString() ,spieler);
		this.kontinente = kontinente;
	}

	public boolean istAbgeschlossen() {
		
		List<Land> laender = new Vector<>();
		
		for(Kontinent k : kontinente){
			laender.addAll(k.getLaender());
		}
		for(Land l : laender){
			if(! l.getBesitzer().equals(spieler)){
				return false;
			}
		}
		return true;
	}

}
