package com.phoenixkahlo.networking;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.phoenixkahlo.utils.StreamUtils;

public abstract class SimpleReply<A, B> implements Reply<A, B> {

	private String replyID;
	
	/**
	 * Constructs with a null replyID
	 */
	public SimpleReply() {}
	
	public SimpleReply(InputStream in) throws IOException {
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
