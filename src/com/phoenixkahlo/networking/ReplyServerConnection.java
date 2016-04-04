package com.phoenixkahlo.networking;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.phoenixkahlo.utils.StreamUtils;

/**
 * A ServerConnection that allows for replies.
 * @author <a href="mailto:kahlo.phoenix@gmail.com">Phoenix Kahlo</a>
 * @param <A> The class of clients that the Sendables are generic to.
 * @param <B> The class of servers that the Sendables are generic to. <b>Any subclass of ServerConnection
 * must extend ServerConnection with a type argument B that the subclass can be cast to. The compiler will not
 * detect if this is not the case, and if this is not the case, unchecked casts will occur incorrectly.</b>
 * @see com.phoenixkahlo.networking.Repliable
 * @see com.phoenixkahlo.networking.Reply
 */
public class ReplyServerConnection<A, B> extends ServerConnection<A, B> {

	/**
	 * The reply ID of all the replies that are being waited for, and the Awaiter that are waiting for them
	 */
	private Map<String, ReplyAwaiter<A, B>> awaiting = new HashMap<String, ReplyAwaiter<A, B>>();
	
	public ReplyServerConnection(Socket socket, SendableCoder<A, B> coder) {
		super(socket, coder);
	}

	@Override
	protected Sendable<A, B> read(InputStream in) throws IOException, BadDataException {
		Sendable<A, B> sendable = super.read(in);
		if (sendable instanceof Reply) {
			Reply<A, B> reply = (Reply<A, B>) sendable;
			ReplyAwaiter<A, B> awaiter = awaiting.get(reply.getReplyID());
			if (awaiter != null) awaiter.invoke(reply);
		}
		return super.read(in);
	}
	
	/**
	 * Sends the Repliable and waits for a reply, activating the Thread when the reply is received.
	 */
	public void sendAndAwait(Repliable<A, B> repliable, ReplyAwaiter<A, B> awaiter) {
		String replyID = createReplyID();
		repliable.setReplyID(replyID);
		awaiting.put(replyID, awaiter);
		send(repliable);
	}
	
	/**
	 * Sends the Reply to the Repliable, activating whatever Thread is waiting for it on the other side 
	 * of the connection.
	 */
	public void sendReplyTo(Reply<A, B> reply, Repliable<A, B> repliable) {
		reply.setReplyID(repliable.getReplyID());
		send(reply);
	}
	
	/**
	 * Generates a random reply ID, in the form of a random 100 byte string. Since the probability
	 * of two of these being equal is 256^-200, the generated ID is assumed to be unique.
	 */
	public static String createReplyID() {
		byte[] bytes = new byte[100];
		new Random().nextBytes(bytes);
		return StreamUtils.bytesToString(bytes);
	}

}
