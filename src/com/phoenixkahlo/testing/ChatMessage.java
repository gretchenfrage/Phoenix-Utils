package com.phoenixkahlo.testing;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.phoenixkahlo.networking.Sendable;
import com.phoenixkahlo.utils.StreamUtils;

public class ChatMessage implements Sendable<ChatClient, ChatServer> {

	private String message;
	
	public ChatMessage(String message) {
		this.message = message;
	}
	
	public ChatMessage(InputStream in) throws IOException {
		message = StreamUtils.readString(in);
	}
	
	@Override
	public void write(OutputStream out) throws IOException {
		StreamUtils.writeString(message, out);
	}

	@Override
	public void effectClient(ChatClient connection) {
		System.out.println(message);
	}

	@Override
	public void effectServer(ChatServer connection) {
		System.out.println(message);
	}

}
