package local.valueobjects;

public class LaenderMission extends Mission {

	private int anzahlLaender = 0;
	private int anzahlEinheiten = 0;	
	
	public LaenderMission(Spieler spieler, int anzahlLaender, int anzahlEinheiten) {
		super("Erobern Sie " + anzahlLaender + " und besetzen Sie jedes mit " + anzahlEinheiten + ".", spieler);
		this.anzahlLaender = anzahlLaender;
		this.anzahlEinheiten = anzahlEinheiten;
	}


	@Override
	public boolean istAbgeschlossen() {
		// TODO Auto-generated method stub
		
		// TODO: Logik (Hat der Spieler tatsächlich die Mission erfüllt?)
		
		return false;
	}

}
