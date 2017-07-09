package local.domain.exceptions;

public class KeinNachbarlandException extends Exception {
	/**
	 * Exception, wenn das ausgewählte Land kein Nachbarland ist
	 * @param land
	 */
	public KeinNachbarlandException(String land) {
		super("Das Land " + land +  " ist kein Nachbarland");
	}
}
