package pl.cc.core.cmd;

import pl.cc.real.RealQueue;

/**
 * Wylogoanie agenta z kolejki
 * 
 * @since Mar 5, 2008
 */
public class InfoQueueActionRemove extends Command {
	RealQueue queue;
	String action;

	//+INFO Queue 'bluzeczka', Action 'remove'
	private InfoQueueActionRemove(String orginalLine, RealQueue queue){
		super(orginalLine);
		this.queue=queue;
	}
	
	public static Command factoryInt(String s){
		String [] names = {"queue","action"};
		String [] v = getValuesINFO(s, names); 
		if (v==null) return null;
		if(! v[1].toLowerCase().equals("remove")) return null;
		return new InfoQueueActionRemove(
				s,
				new RealQueue(v[0],"0")
		);
	}

	@Override
	public int getType() {
		return CMD_QUEUE_ACTION_REMOVE;
	}

	public RealQueue getQueue() {
		return queue;
	}
	
}
