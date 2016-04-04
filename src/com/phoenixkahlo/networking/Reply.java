package com.phoenixkahlo.networking;

/**
 * Represents a reply to a Repliable, so long as both sides of the connection support replies.
 * @author <a href="mailto:kahlo.phoenix@gmail.com">Phoenix Kahlo</a>
 * @param <A> The class of client that is expected to be invoked with on the client end.
 * @param <B> The class of server that is expected to be invoked with on the server end.
 */
public interface Reply<A, B> extends Sendable<A, B> {

	/**
	 * Called to set a reply ID before being sent
	 * @param id the reply ID
	 */
	void setReplyID(String id);
	
	/**
	 * Called to get the reply ID after being received
	 * @param id
	 */
	String getReplyID();
	
}
