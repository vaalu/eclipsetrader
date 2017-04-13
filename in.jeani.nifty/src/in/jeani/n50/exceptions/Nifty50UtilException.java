/**
 * 
 */
package in.jeani.n50.exceptions;

/**
 * @author mohanavelp
 *
 */
public class Nifty50UtilException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public Nifty50UtilException() {
	}

	/**
	 * @param message
	 */
	public Nifty50UtilException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public Nifty50UtilException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public Nifty50UtilException(String message, Throwable cause) {
		super(message, cause);
	}

	public static Nifty50UtilException get(Throwable e) {
		return new Nifty50UtilException(e);
	}
	
	public static Nifty50UtilException get(String msg, Throwable e) {
		return new Nifty50UtilException(msg, e);
	}
}
