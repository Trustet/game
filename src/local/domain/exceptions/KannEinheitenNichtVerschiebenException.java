package local.domain.exceptions;

public class KannEinheitenNichtVerschiebenException extends Exception{

	/**
	 * Exception, wenn zu viele oder zu wenig Einheiten zum verschieben angegeben werden
	 * @param zuViele
	 */
	public KannEinheitenNichtVerschiebenException(boolean zuViele) {
		super("Du kannst nicht " + (zuViele? "so viele " : " so wenige " + "Einheiten verschieben"));
	}
}
