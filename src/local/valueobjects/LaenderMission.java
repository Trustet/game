package local.valueobjects;
import java.util.List;
public class LaenderMission extends Mission {

	private int anzahlLaender = 0;
	private int anzahlEinheiten = 0;	
	private Spieler spieler;
	
	public LaenderMission(Spieler spieler, int anzahlLaender, int anzahlEinheiten, List<Land> laender) {
		super("Erobern Sie " + anzahlLaender + " und besetzen Sie jedes mit " + anzahlEinheiten + ".", spieler);
		this.anzahlLaender = anzahlLaender;
		this.anzahlEinheiten = anzahlEinheiten;
		this.spieler = spieler;
		this.laender = laender;
	}

	public boolean istAbgeschlossen() {
		int counter = 0;
		for(Land l : laender){
			if(l.getBesitzer().equals(spieler) && (l.getEinheiten() >= anzahlEinheiten)){
				counter++;
			}
		}
		if(counter >= anzahlLaender){
			return true;
		}
		
		return false;
	}

}
