package com.phoenixkahlo.networking;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * A thread that encapsulates a ServerSocket to wait on a certain port for connections, and then
 * passes any Sockets it receives to a ConnectionFactory.
 * @author <a href="mailto:kahlo.phoenix@gmail.com">Phoenix Kahlo</a>
 * @see com.phoenixkahlo.networking.ConnectionFactory ConnectionFactory
 */
public class Waiter extends Thread {

	private ConnectionFactory connectionFactory;
	private ServerSocket serverSocket;
	
	private volatile boolean shouldContinueRunning = true;

	/**
	 * Creates a new Waiter on the given port with the given ConnectionFactory, but does not wait for connections until
	 * run is called.
	 * @param connectionFactory The ConnectionFactory to call upon when clients are accepted
	 * @param port The port to create the ServerSocket on
	 * @throws RuntimeException If fails to bind to the given port
	 * @see com.phoenixkahlo.networking.ConnectionFactory ConnectionFactory
	 */
	public Waiter(ConnectionFactory connectionFactory, int port) throws RuntimeException {
		super("Waiter thread on port " + port);
		this.connectionFactory = connectionFactory;
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			throw new RuntimeException("Waiter failed to bind to port " + port);
		}
	}

	/**
	 * Ends any thread that is waiting on connections (if the thread is running) and ensures that this Waiter
	 * will not accept any more clients.
	 */
	public void terminate() {
		shouldContinueRunning = false;
		interrupt();
	}
	
	@Override
	public void run() {
		while (shouldContinueRunning) {
			try {
				connectionFactory.createConnection(serverSocket.accept());
			} catch (IOException e) {
				System.err.println("Failed to accept socket");
				e.printStackTrace();
			}
		}
	}

}
