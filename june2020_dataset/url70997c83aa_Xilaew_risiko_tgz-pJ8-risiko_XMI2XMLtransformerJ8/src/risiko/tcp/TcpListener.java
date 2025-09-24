/**
 * 
 */
package risiko.tcp;

import java.nio.channels.SelectionKey;


/**
 * @author xilaew
 *
 */
public interface TcpListener {

	public void handleIncomming(String message, SelectionKey key);

	public void handleOutgoing(String message, SelectionKey key);

}
