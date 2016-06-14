package Client.gui;


public class ActionCardException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4440879297778628799L;

	public ActionCardException(String message) {
		super(message);
	}
	
	public ActionCardException() {
		super("Du darfst diese Karte jetzt nicht benutzen!");
	}
}
