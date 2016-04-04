package com.phoenixkahlo.testing;

import java.net.Socket;
import java.util.Scanner;

import com.phoenixkahlo.networking.ReplyClientConnection;

public class QAClient extends ReplyClientConnection<QAClient, QAServer> {

	public QAClient() throws Exception {
		super(new Socket("localhost", 4018), new QACoder());
		start();
		Scanner scanner = new Scanner(System.in);
		String question = scanner.nextLine();
		scanner.close();
		QAQuestion sendable = new QAQuestion(question);
		System.out.println("about to send");
		sendAndAwait(sendable, reply -> System.out.println("reply received"));
		System.out.println("sent");
	}

	public static void main(String[] args) throws Exception {
		new QAClient();
	}
	
}
