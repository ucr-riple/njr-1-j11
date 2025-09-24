package nl.rug.peerbox.logic.messaging;

import nl.rug.peerbox.logic.Context;
import nl.rug.peerbox.logic.Peer;
import nl.rug.peerbox.logic.filesystem.PeerboxFile;
import nl.rug.peerbox.logic.messaging.Message.Key;

public class ByeByeMessageHandler extends MessageHandler {

	@Override
	void handle(Message message, Context ctx) {
		
		Object obj = message.get(Key.Peer);
		if (obj != Message.NULLOBJ && obj instanceof Peer) {
			Peer peer = (Peer) obj;
			ctx.peerLeft(peer);
		}

	}

}
