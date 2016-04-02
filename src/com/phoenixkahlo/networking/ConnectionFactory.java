package com.phoenixkahlo.networking;
import java.net.Socket;

/**
 * Used by a Waiter to produce connections to clients.
 * @author <a href="mailto:kahlo.phoenix@gmail.com">Phoenix Kahlo</a>
 * @see com.phoenixkahlo.networking.Waiter Waiter
 */
public interface ConnectionFactory {

	/**
	 * Called by any Waiter that references this ConnectionFactory to produce a connection upon accepting a Socket.
	 * @param socket The socket that the Waiter has accepted from its ServerSocket
	 * @see com.phoenixkahlo.networking.Waiter Waiter
	 */
	void createConnection(Socket socket);

}
