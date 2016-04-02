package com.phoenixkahlo.networking;

/**
 * An exception thrown when the data read from an InputStream is not valid, and would not occur from
 * a properly functioning server/client.
 * @author <a href="mailto:kahlo.phoenix@gmail.com">Phoenix Kahlo</a>
 */
public class BadDataException extends Exception {

	private static final long serialVersionUID = -1161307127861060278L;

	public BadDataException() {}
	
	public BadDataException(String message) {
		super(message);
	}
	
}
