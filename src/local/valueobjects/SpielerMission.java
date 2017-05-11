package local.valueobjects;

import java.util.List;

public class SpielerMission extends Mission {

	List<Spieler> spielerliste;
	Spieler spieler2;
	
	public SpielerMission(int id, Spieler spieler,Spieler spieler2,List<Spieler> spielerliste) {
		super(id, "Erobern Sie alle Länder von ", spieler); // +  spieler2,spieler);
		this.spielerliste = spielerliste;
		this.spieler2 = spieler2;
	}

	public boolean istAbgeschlossen() {
		if(spielerliste.contains(spieler2)){
			return false;
		}
		return true;
	}
	
	public void setSpieler2(Spieler spieler) {
		this.spieler2 = spieler;
		
	}

}
