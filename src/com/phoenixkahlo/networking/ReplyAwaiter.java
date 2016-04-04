package com.phoenixkahlo.networking;

/**
 * Waits for a Reply to a Repliable, and is invoked if and when a Reply is received.
 * @author <a href="mailto:kahlo.phoenix@gmail.com">Phoenix Kahlo</a>
 * @param <A> The class of client that is expected to be invoked with on the client end.
 * @param <B> The class of server that is expected to be invoked with on the server end.
 */
public interface ReplyAwaiter<A, B> {
	
	/**
	 * Invoked when a Reply is received to the Repliable this is waiting on. Since
	 * the connection will not continue reading until this method returns, it is
	 * advisable to launch a new Thread in the case of long operations.
	 */
	void invoke(Reply<A, B> reply);
	
}