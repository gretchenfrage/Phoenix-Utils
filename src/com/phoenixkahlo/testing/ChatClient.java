package com.phoenixkahlo.testing;

import java.net.Socket;
import java.util.Scanner;

import com.phoenixkahlo.networking.ClientConnection;

public class ChatClient extends ClientConnection<ChatClient, ChatServer> {

	public ChatClient() throws Exception {
		super(new Socket("localhost", 4018), new ChatCoder());
		start();
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		Runtime.getRuntime().addShutdownHook(new Thread(scanner::close));
		while (true) {
			send(new ChatMessage(scanner.nextLine()));
		}
	}
	
	public static void main(String[] args) throws Exception {
		new ChatClient();
	}

}
