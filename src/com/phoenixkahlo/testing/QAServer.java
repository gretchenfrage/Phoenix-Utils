package com.phoenixkahlo.testing;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import com.phoenixkahlo.networking.BadDataException;
import com.phoenixkahlo.networking.ReplyServerConnection;
import com.phoenixkahlo.networking.Sendable;
import com.phoenixkahlo.networking.Waiter;

public class QAServer extends ReplyServerConnection<QAClient, QAServer> {

	public QAServer(Socket socket) {
		super(socket, new QACoder());
		start();
	}
	
	public static void main(String[] args) {
		new Waiter(QAServer::new, 4018).start();
		System.out.println("waiting");
	}

	@Override
	protected Sendable<QAClient, QAServer> read(InputStream in) throws IOException, BadDataException {
		Sendable<QAClient, QAServer> sendable = super.read(in);
		System.out.println("Read " + sendable);
		return sendable;
	}

}
