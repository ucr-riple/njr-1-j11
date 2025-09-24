package pl.cc.core.cmd;

import pl.cc.real.RealCall;
import pl.cc.real.RealQueue;

public class EventQueueJoin extends Command {
	RealQueue queue;
	RealCall call;
	
	//EVENT [Queue], Action [Join], Queue [nogawka], Count [1], Callerid [978], Uniqueid [1197546652.1243], CreationTime [2009-07-03 13:21:01]
	private EventQueueJoin(String orginalLine, RealQueue queue, RealCall call) {
		super(orginalLine);
		this.queue = queue;
		this.call = call;
	}

	public static Command factoryInt(String s){
		String [] names = {"event","action","queue","count","callerid","uniqueid","CreationTime"};
		boolean [] required = {true,true,true,      true,true,true,					  false};		
		String [] values = getValues(s, names, required); 
		if (values==null) return null;
		if (!values[0].toLowerCase().equals("queue")) return null;
		if (!values[1].toLowerCase().equals("join")) return null;
		
		RealCall call = new RealCall(values[4],values[5]); 
		if (values[6]!=null){
			call.setCreationTimeAsString(values[6]);
		}
		
		
		return new EventQueueJoin(
				s,
				new RealQueue(values[2],values[3]),
				call
				);
	}

	
	@Override
	public int getType() {
		return CMD_EVENT_QUEUE_JOIN;
	}

	public RealQueue getQueue() {
		return queue;
	}

	public RealCall getCall() {
		return call;
	}

}
