package local.valueobjects;

public class Kontinent {

	private Land[] laender;

	public Kontinent(Land[] laender) {
		this.laender = laender;
	}

	public Land[] getLaender() {
		return laender;
	}

	public void setLaender(Land[] laender) {
		this.laender = laender;
	}
	
}
