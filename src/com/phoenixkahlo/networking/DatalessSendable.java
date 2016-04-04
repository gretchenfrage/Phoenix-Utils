package com.phoenixkahlo.networking;

import java.io.OutputStream;

/**
 * Used for the nicer creation of Sendables that don't write data (or read data) to streams,
 * particularly singleton Sendables.
 * @author <a href="mailto:kahlo.phoenix@gmail.com">Phoenix Kahlo</a>
 * @param <A> The class of client that is expected to be invoked with on the client end.
 * @param <B> The class of server that is expected to be invoked with on the server end.
 */
public abstract class DatalessSendable<A, B> implements Sendable<A, B> {

	/**
	 * Writes no data.
	 * @param out the OutputStream that will remain uneffected.
	 */
	public void write(OutputStream out) {}
	
}
