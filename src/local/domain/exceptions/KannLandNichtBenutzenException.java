package local.domain.exceptions;

public class KannLandNichtBenutzenException extends Exception{
	public KannLandNichtBenutzenException(String land, String text) {
		super("Das Land " + land + text + "\nBitte waehle ein anderes Land aus");
	}
}
