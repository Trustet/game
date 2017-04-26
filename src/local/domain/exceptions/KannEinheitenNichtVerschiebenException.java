package local.domain.exceptions;

public class KannEinheitenNichtVerschiebenException extends Exception{
	public KannEinheitenNichtVerschiebenException( String text) {
		super("Du kannst " + text);
	}
}
