package local.domain.exceptions;

public class LandBereitsBenutztException extends Exception{
	/**
	 * Exception, wenn das ausgewählte Land bereits zum angreifen benutzt wurde
	 * @param land
	 */
	public LandBereitsBenutztException(String land) {
		super("Das Land " + land +  " wurde bereits zum angreifen benutzt");
	}
}
