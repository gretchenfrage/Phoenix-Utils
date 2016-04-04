package com.phoenixkahlo.networking;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * Represents a client's Sendable-based connection to a server. When run, uses a SendableCoder to decode Sendables
 * from a Socket, and then invokes them with itself.
 * @author <a href="mailto:kahlo.phoenix@gmail.com">Phoenix Kahlo</a>
 * @param <A> The class of clients that the Sendables are generic to. <b>Any subclass of ClientConnection
 * must extend ClientConnection with a type argument A that the subclass can be cast to. The compiler will not
 * detect if this is not the case, and if this is not the case, unchecked casts will occur incorrectly.</b>
 * @param <B> The class of servers that the Sendables are generic to.
 */
public class ClientConnection<A, B> extends Thread {

	private Socket socket;
	private SendableCoder<A, B> coder;
	
	/**
	 * Constructs the ClientConnection with the given arguments, but does not start it.
	 * @param socket the socket that is connection to the client.
	 * @param coder the SendableCoder with which to encode and decode Sendables.
	 */
	public ClientConnection(Socket socket, SendableCoder<A, B> coder) {
		this.socket = socket;
		this.coder = coder;
		Thread.currentThread().setUncaughtExceptionHandler(new UncaughtExceptionHandler() {

			@Override
			public void uncaughtException(Thread t, Throwable e) {
				e.printStackTrace();
				disconnect();
			}
			
		});
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		try {
			InputStream in = socket.getInputStream();
			while (true) {
				read(in).effectClient((A) this);
			}
		} catch (IOException | BadDataException e) {
			System.out.print("Disconnecting " + this + " on account of exception: ");
			e.printStackTrace(System.out);
			disconnect();
		}
	}
	
	/**
	 * Reads the next Sendable from the InputStream. Is called from run(), and is seperated for
	 * the purpose of overriding.
	 * @param in the InputStream from which to read
	 * @return the next Sendable from the InputStream
	 * @throws IOException if the InputStream throws an IOException
	 * @throws BadDataException if the SendableCoder throws a BadDataException
	 */
	protected Sendable<A, B> read(InputStream in) throws IOException, BadDataException {
		return coder.read(in);
	}
	
	/**
	 * Sends the Sendable to the server
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
	 * Can be called externally, and will be called when the socket throws an IOException
	 * or when the SendableCoder throws a BadDataException.
	 */
	public void disconnect() {
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String toString() {
		return "ClientConnection to " + socket;
	}
	
}
