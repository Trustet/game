package local.domain.exceptions;

public class KeinGegnerException extends Exception{
	public KeinGegnerException(String land) {
		super("Das Land " + land +  " gehoert dir");
	}
}
