package nl.rug.peerbox.logic.messaging;

import nl.rug.peerbox.logic.Context;
import nl.rug.peerbox.logic.filesystem.PeerboxFile;
import nl.rug.peerbox.logic.messaging.Message.Key;

public class CreatedMessageHandler extends MessageHandler {

	@Override
	void handle(Message message, Context ctx) {
		
		Object o = message.get(Key.File);
		if (o instanceof PeerboxFile) {
			PeerboxFile file = (PeerboxFile)o;
			ctx.getVirtualFilesystem().addFile(file);
		}

	}

}
