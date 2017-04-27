package local.domain.exceptions;

public class NichtGenugEinheitenException extends Exception{
//	public NichtGenugEinheitenException(Land land, int erforderlicheEinheiten, int verfuegbareEinheiten) {
	public NichtGenugEinheitenException(String land, String text) {
		super("Das Land " + land + text );
	}
}
