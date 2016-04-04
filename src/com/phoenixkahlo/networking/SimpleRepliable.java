package com.phoenixkahlo.networking;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.phoenixkahlo.utils.StreamUtils;

/**
 * A simple implementation of Repliable.
 * @author <a href="mailto:kahlo.phoenix@gmail.com">Phoenix Kahlo</a>
 * @param <A> The class of client that is expected to be invoked with on the client end.
 * @param <B> The class of server that is expected to be invoked with on the server end.
 */
public abstract class SimpleRepliable<A, B> implements Repliable<A, B> {

	private String replyID;
	
	/**
	 * Constructs with a null replyID
	 */
	public SimpleRepliable() {}
	
	public SimpleRepliable(InputStream in) throws IOException {
		replyID = StreamUtils.readString(in);
	}
	
	@Override
	public void write(OutputStream out) throws IOException {
		StreamUtils.writeString(replyID, out);
	}
	
	@Override
	public void setReplyID(String id) {
		replyID = id;
	}

	@Override
	public String getReplyID() {
		return replyID;
	}

}
