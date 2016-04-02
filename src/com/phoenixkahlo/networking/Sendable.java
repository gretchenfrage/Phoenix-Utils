package com.phoenixkahlo.networking;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Can be sent across streams through a SendableCoder and invoked based on which side of the network it is on.
 * <p>
 * Is expected to have constructor {@code public Sendable(InputStream in) throws IOException},
 * which will be accessed by a SendableCoder through reflection.
 * @author <a href="mailto:kahlo.phoenix@gmail.com">Phoenix Kahlo</a>
 * @param <A> The class of client that is expected to be invoked with on the client end.
 * @param <B> The class of server that is expected to be invoked with on the server end.
 * @see com.phoenixkahlo.networking.SendableCoder
 */
public interface Sendable<A, B> {
	
	/**
	 * Writes all the necessary data of this object to the OutputStream to be rebuilt on the other side.
	 * Is expected to me symmetrical to the constructor {@code public Sendable(InputStream in) throws IOException}.
	 * @param out the OutputStream to write to
	 * @throws IOException if out throws an IOException
	 */
	void write(OutputStream out) throws IOException;
	
	/**
	 * Is invoked when received on the client end.
	 * @param connection The ServerConnection that has received this object.
	 */
	void effectClient(A connection);
	
	/**
	 * Is invoked when received on the server end.
	 * @param connection The ClientConnection that has received this object.
	 */
	void effectServer(B connection);
	
}
