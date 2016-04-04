package com.phoenixkahlo.testing;

import com.phoenixkahlo.networking.SendableCoder;

public class ChatCoder extends SendableCoder<ChatClient, ChatServer> {
	
	public ChatCoder() {
		register(1, ChatMessage.class);
	}

}
