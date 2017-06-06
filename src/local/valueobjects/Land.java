package local.valueobjects;


public class Land {
	private String name;
	private Spieler besitzer;
	private int einheiten;
	private String kurzel;
	private int fahneX;
	private int fahneY;
	

	public Land(String name, Spieler besitzer, int einheiten, String kurzel, int fahneX, int fahneY) {
		this.name = name;
		this.besitzer = besitzer;
		this.einheiten = einheiten;
		this.kurzel = kurzel;
		this.fahneX = fahneX;
		this.fahneY = fahneY;
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
	public String getKuerzel(){
		return kurzel;
	}
	
	public void setFahne(int x, int y){
		this.fahneX = x;
		this.fahneY = y;
	}
	
	public int getFahneX(){
		return fahneX;
	}
	
	public int getFahneY(){
		return fahneY;
	}
	
	
}
