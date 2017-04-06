package local.valueobjects;

public class Spieler {
	private String name;
	
	public Spieler(String name){
		this.name = name;
	}
	public String getName(){
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Spieler) {
			Spieler andererSpieler = (Spieler) obj;
			return this.name.equals(andererSpieler.getName());
		}
		return false;
	}
}
