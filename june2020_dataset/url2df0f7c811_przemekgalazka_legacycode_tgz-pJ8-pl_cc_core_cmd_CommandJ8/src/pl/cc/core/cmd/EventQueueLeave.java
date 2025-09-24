package pl.cc.core.cmd;

import pl.cc.real.RealCall;
import pl.cc.real.RealQueue;

public class EventQueueLeave extends Command {
	RealQueue queue;
	RealCall call;

	//EVENT [Queue], Action [Leave], Queue [nogawka], Count [1], Callerid [978], Uniqueid [1197546652.1243]
	private EventQueueLeave(String orginalLine, RealQueue queue, RealCall call) {
		super(orginalLine);
		this.queue = queue;
		this.call = call;
	}

	public static Command factoryInt(String s){
		String [] names = {"event","action","queue","count","callerid","uniqueid"};
		String [] values = getValues(s, names); 
		if (values==null) return null;
		if (!values[0].toLowerCase().equals("queue")) return null;
		if (!values[1].toLowerCase().equals("leave")) return null;
		
		return new EventQueueLeave(
				s,
				new RealQueue(values[2],values[3]),
				new RealCall(values[4],values[5])
				);
	}

	
	@Override
	public int getType() {
		return CMD_EVENT_QUEUE_LEAVE;
	}

	public RealQueue getQueue() {
		return queue;
	}

	public RealCall getCall() {
		return call;
	}

}
