package local.valueobjects;

public class Land {
	private String name;
	private Spieler besitzer;
	private int einheiten;
	

	public Land(String name, Spieler besitzer, int einheiten) {
		this.name = name;
		this.besitzer = besitzer;
		this.einheiten = einheiten;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Spieler getBesitzer() {
		return besitzer;
	}

	public void setBesitzer(Spieler besitzer) {
		this.besitzer = besitzer;
	}

	public int getEinheiten() {
		return einheiten;
	}

	public void setEinheiten(int einheiten) {
		this.einheiten = einheiten;
	}
}
