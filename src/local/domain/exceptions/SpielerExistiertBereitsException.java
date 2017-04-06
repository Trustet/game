package local.domain.exceptions;

public class SpielerExistiertBereitsException extends Exception {

	public SpielerExistiertBereitsException(String name) {
		super("Spieler mit Name " + name + " existiert bereits.");
	}
}
