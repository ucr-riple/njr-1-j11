import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;

import nl.rug.peerbox.logic.messaging.Message;
import nl.rug.peerbox.logic.messaging.Message.Key;
import nl.rug.peerbox.middleware.PrettyPrinter;

public class MessagePrinter extends PrettyPrinter {

		@Override
		public String printPayload(byte[] payload) {
			Message message = null;
			try {
				ByteArrayInputStream bais = new ByteArrayInputStream(payload);
				ObjectInputStream is = new ObjectInputStream(bais);
				Object o = is.readObject();
				if (o instanceof Message) {
					message = (Message) o;
				}
			} catch (Exception e) {
			}
			
			if (message != null) {
				return "NOPB(" + message.get(Key.Command) + ")";
			}
			return null;
		}
		
	}