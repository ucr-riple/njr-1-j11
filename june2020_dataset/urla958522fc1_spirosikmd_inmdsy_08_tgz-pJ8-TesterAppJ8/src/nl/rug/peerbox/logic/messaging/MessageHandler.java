package nl.rug.peerbox.logic.messaging;

import java.util.HashMap;
import java.util.Map;

import nl.rug.peerbox.logic.Context;
import nl.rug.peerbox.logic.Peer;
import nl.rug.peerbox.logic.messaging.Message.Command;
import nl.rug.peerbox.logic.messaging.Message.Key;

import org.apache.log4j.Logger;

public abstract class MessageHandler {

	private static Map<Object, MessageHandler> handlers = new HashMap<Object, MessageHandler>();
	private static final Logger logger = Logger.getLogger(MessageHandler.class);

	static {
		registerHandler(new ListMessageHandler(), Command.List);
		registerHandler(new CreatedMessageHandler(), Command.Created);
		registerHandler(new DeletedMessageHandler(), Command.Deleted);
		registerHandler(new ByeByeMessageHandler(), Command.ByeBye);
		registerHandler(new JoinMessageHandler(), Command.Join);
		registerHandler(new RefreshMessageHandler(), Command.Refresh);
	
	}

	static void registerHandler(final MessageHandler handler,
			final Object forCommand) {
		logger.info("Register message handler for the command "
				+ forCommand.getClass().getSimpleName() + " " + forCommand);
		handlers.put(forCommand, handler);
	}


	public static void process(final Message message, final Context ctx) throws UnsupportedCommandException {
		Object command = message.get(Key.Command);
		if (command != Message.NULLOBJ) {
			if (handlers.containsKey(command)) {
				logger.debug("Process command "
						+ command.getClass().getSimpleName() + " " + command);
				handlers.get(command).handle(message, ctx);
			} else {
				throw new UnsupportedCommandException(command);
			}
		} else {
			logger.debug("No command key found");
		}
	}

	abstract void handle(final Message message, final Context ctx);

}
