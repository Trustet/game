package local.valueobjects;

import java.util.List;

public class Kontinent {

	private List<Land> laender;
	private String name;

	public Kontinent(String name, List<Land> laender) {
		this.setName(name);
		this.laender = laender;
	}


	public String toString() {
		return "Kontinent [name=" + name + ", laender=" + laender.toString() + "]";
	}


	public List<Land> getLaender() {
		return laender;
	}

	public void setLaender(List<Land> laender) {
		this.laender = laender;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
