package local.domain.exceptions;

public class KannLandNichtBenutzenException extends Exception{
	/**
	 * Exception, wenn 
	 * @param land
	 * @param text
	 */
	public KannLandNichtBenutzenException(String land, String text) {
		super("Das Land " + land + text + "\nBitte w\u00E4hle ein anderes Land aus");
	}
}
