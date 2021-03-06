package local.valueobjects;

import java.util.List;

public class AngriffRueckgabe {
	private int verlusteVerteidiger;
	private int verlusteAngreifer;
	private List<Integer> wuerfelVerteidiger;
	private List<Integer> wuerfelAngreifer;
	private boolean erobert;

	public AngriffRueckgabe(int verlusteVerteidiger, int verlusteAngreifer, List<Integer> wuerfelVerteidiger,
			List<Integer> wuerfelAngreifer, boolean erobert) {
		this.verlusteVerteidiger = verlusteVerteidiger;
		this.verlusteAngreifer = verlusteAngreifer;
		this.wuerfelVerteidiger = wuerfelVerteidiger;
		this.wuerfelAngreifer = wuerfelAngreifer;
		this.erobert = erobert;
	}
	
	/**
	 * Gibt aus wer gewonnen hat oder ob es unentschieden ausgeht
	 * @return
	 */
	public String hatGewonnen()	{
		if (verlusteVerteidiger < verlusteAngreifer){
			return "V";
	}	else if(verlusteVerteidiger > verlusteAngreifer){
			return "A";
	}	else	{
			return "U";
	}
	}
	
	/**
	 * Gibt die Anzahl der Verluste des Verteidigers aus
	 * @return int
	 */
	public int getVerlusteVerteidiger() {
		return verlusteVerteidiger;
	}
	public void setVerlusteVerteidiger(int verlusteVerteidiger) {
		this.verlusteVerteidiger = verlusteVerteidiger;
	}
	/**
	 * Gibt die Anzahl der Verluste des Angreifers aus
	 * @return int
	 */
	public int getVerlusteAngreifer() {
		return verlusteAngreifer;
	}
	/**
	 * Setzt die Anzahl der Verluste des Angreifers
	 * @param verlusteAngreifer
	 */
	public void setVerlusteAngreifer(int verlusteAngreifer) {
		this.verlusteAngreifer = verlusteAngreifer;
	}
	public List<Integer> getWuerfelVerteidiger() {
		return wuerfelVerteidiger;
	}
	public void setWuerfelVerteidiger(List<Integer> wuerfelVerteidiger) {
		this.wuerfelVerteidiger = wuerfelVerteidiger;
	}
	public List<Integer> getWuerfelAngreifer() {
		return wuerfelAngreifer;
	}
	public void setWuerfelAngreifer(List<Integer> wuerfelAngreifer) {
		this.wuerfelAngreifer = wuerfelAngreifer;
	}
	public boolean isErobert() {
		return erobert;
	}
	/**
	 * Zeigt an ob ein Land erobert ist
	 * @param erobert
	 */
	public void setErobert(boolean erobert) {
		this.erobert = erobert;
	}
	
}
