package edu.concordia.dpis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.concordia.dpis.commons.Message;
import edu.concordia.dpis.commons.ReliableMessage;
import edu.concordia.dpis.fifo.FIFO;

// A Handler, which possess with commands for each operation, can run the operation by executing the associated command for the requested operation
// Multicasts the message if it is supposed to be.
public class DefaultRequestHandler implements RequestHandler {

	// all the application commands
	private HashMap<String, Command> commands = new HashMap<String, Command>();

	// replies associated with the request message
	private HashMap<Integer, List<ReliableMessage>> replies = new HashMap<Integer, List<ReliableMessage>>();

	// get the operation name for this request
	@Override
	public String getOperationName(Message request) {
		return request.getActualMessage();
	}

	// get the arguments for this request
	@Override
	public List<Object> getArguments(Message request) {
		return request.getArguments();
	}

	// run the operation
	@Override
	public Object doOperation(final Message msg) {
		// request/reply message
		ReliableMessage rMsg = (ReliableMessage) msg;
		Object thisReturn = null;
		// is the message need to be multi casted
		if (msg.isMulticast()) {
			System.out.println("message " + rMsg.getSequenceNumber()
					+ " must be multicasted");
			try {
				// make an entry for the replies for this sequence number
				if (replies.get(rMsg.getSequenceNumber()) == null) {
					List<ReliableMessage> r = new ArrayList<ReliableMessage>();
					replies.put(rMsg.getSequenceNumber(), r);
				}
				// multicast
				FIFO.INSTANCE.multicast(msg);
				// get the right command which can perform the operation
				final Command command = commands.get(getOperationName(msg));
				// it is the application configurer responsiblity to attach all
				// the commands necessary to run an operation
				if (command == null) {
					throw new UnsupportedOperationException();
				}
				// execute the command, on behalf of this replica
				thisReturn = command.execute(msg.getArguments());
				System.out
						.println("giving some time for others to reply back to the leader.");
				Thread.sleep(3000);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			ReliableMessage thisResultMsg = new ReliableMessage("SUCCESS", msg
					.getToAddress().getHost(), msg.getToAddress().getPort());
			thisResultMsg.addArgument(thisReturn);
			// add this replica's result to the replies
			synchronized (replies.get(rMsg.getSequenceNumber())) {
				replies.get(rMsg.getSequenceNumber()).add(thisResultMsg);
			}
			// find the best solution out of all the replies from different
			// nodes
			Object response = getAResponse(replies
					.get(rMsg.getSequenceNumber()));
			System.out.println("replying to message "
					+ rMsg.getSequenceNumber() + response);
			return response;
		}
		// if the message is a reply to the previous request multicasted, save
		// the reply
		if (msg.isReply()) {
			System.out.println("got a reply for sequencenumber:"
					+ rMsg.getSequenceNumber());
			synchronized (replies.get(rMsg.getSequenceNumber())) {
				replies.get(rMsg.getSequenceNumber()).add(rMsg);
			}
		} else {
			// just have to run this operation only on this replica
			final Command command = commands.get(getOperationName(msg));
			return command.execute(msg.getArguments());
		}
		return "OK";
	}

	// finds the best possible solution out of all the replies
	private Object getAResponse(List<ReliableMessage> list) {

		System.out.println("I got a total of " + list.size() + " messages");
		HashMap<Object, Integer> results = new HashMap<Object, Integer>();
		for (Message msg : list) {
			System.out.println("Reply:" + msg.getArguments().get(0));
			if ("SUCCESS".equalsIgnoreCase(msg.getActualMessage())) {
				Object result = msg.getArguments().get(0);
				if (results.get(result) != null) {
					int i = results.get(result);
					results.put(result, ++i);
				} else {
					results.put(result, 1);
				}
			}
		}
		Object result = null;
		int max = 0;
		for (Object obj : results.keySet()) {
			if (results.get(obj) > max) {
				max = results.get(obj);
				result = obj;
			}
		}
		return result;
	}

	// add a command to handle a specific operation
	public void addCommand(String operationName, Command command) {
		this.commands.put(operationName, command);
	}
}
