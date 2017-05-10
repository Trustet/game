package local.valueobjects;
import java.util.List;
import java.util.Vector;
public class LaenderMission extends Mission {

	private int anzahlLaender = 0;
	private int anzahlEinheiten = 0;	
	
	public LaenderMission(int id, Spieler spieler, int anzahlLaender, int anzahlEinheiten, List<Land> laender) {
		super(id,"Erobern Sie " + anzahlLaender + " und besetzen Sie jedes mit " + anzahlEinheiten + ".", spieler);
		this.anzahlLaender = anzahlLaender;
		this.anzahlEinheiten = anzahlEinheiten;
		this.laender = laender;
	}

	public boolean istAbgeschlossen() {
		int counter = 0;
		
		for(Land l : laender){
			if(l.getBesitzer().equals(this.spieler)){
				counter++;
			}
		}
		if(counter >= anzahlLaender){
			return true;
		}
		
		return false;
	}
}
