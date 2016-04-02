package com.phoenixkahlo.networking;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * Represents a server's Sendable-based connection to a client. When run, uses a SendableCoder to decode Sendables
 * from a Socket, and then invokes them with itself.
 * @author <a href="mailto:kahlo.phoenix@gmail.com">Phoenix Kahlo</a>
 * @param <A> The class of clients that the Sendables are generic to
 * @param <B> The class of servers that the Sendables are generic to (this should be whatever non-abstract class extends this)
 */
public class ServerConnection<A, B extends ServerConnection<A, B>> extends Thread {

	private Socket socket;
	private SendableCoder<A, B> coder;
	
	/**
	 * Constructs the ServerConnection with the variables, but does not start it.
	 * @param socket the socket that is connection to the client.
	 * @param coder the SendableCoder with which to encode and decode Sendables.
	 */
	public ServerConnection(Socket socket, SendableCoder<A, B> coder) {
		this.socket = socket;
		this.coder = coder;
	}
	
	@Override
	public void run() {
		try {
			InputStream in = socket.getInputStream();
			while (true) {
				coder.read(in).effectServer(this);
			}
		} catch (IOException | BadDataException e) {
			System.out.print("Disconnecting " + this + " on account of exception: ");
			e.printStackTrace(System.out);
			disconnect();
		}
	}
	
	/**
	 * Sends the Sendable to the client
	 * @param sendable the Sendable to send
	 */
	public void send(Sendable<A, B> sendable) {
		try {
			coder.write(socket.getOutputStream(), sendable);
		} catch (IOException e) {
			System.out.print("Disconnecting " + this + " on account of exception: ");
			e.printStackTrace(System.out);
			disconnect();
		}
	}
	
	/**
	 * Disconnects the encapsulated socket and ends this connection.
	 * If any additional cleanup is necessary, such as removing this object from a list,
	 * this method should be overridden and called with super.
	 * Can be called externally, or will be called when the socket throws an IOException,
	 * or when the SendableCoder throws a BadDataException.
	 */
	public void disconnect() {
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
