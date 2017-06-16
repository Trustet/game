package local.domain.exceptions;

public class KannEinheitenNichtVerschiebenException extends Exception{
//	public KannEinheitenNichtVerschiebenException( String text) {
//		super("Du kannst " + text);
//	}

	public KannEinheitenNichtVerschiebenException(boolean zuViele) {
		super("Du kannst nicht " + (zuViele? "so viele " : " so wenige " + "Einheiten verschieben"));
	}
}
