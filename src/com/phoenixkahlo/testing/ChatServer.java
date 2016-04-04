package com.phoenixkahlo.testing;

import java.net.Socket;

import com.phoenixkahlo.networking.ServerConnection;
import com.phoenixkahlo.networking.Waiter;

public class ChatServer extends ServerConnection<ChatClient, ChatServer> {

	public ChatServer(Socket socket) {
		super(socket, new ChatCoder());
		start();
	}
	
	public static void main(String[] args) {
		new Waiter(ChatServer::new, 4018).start();
		System.out.println("waiting");
	}

}
