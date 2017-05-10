package local.valueobjects;

import java.util.List;

public class SpielerMission extends Mission {

	private Spieler spieler;
	
	public SpielerMission(Spieler spieler,List<Land> laenderListeAndererSpieler) {
		super("Erobern Sie alle Länder von " +  laenderListeAndererSpieler.get(0).getBesitzer().getName(),spieler);
		this.spieler = spieler; 
		this.laender = laenderListeAndererSpieler;
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