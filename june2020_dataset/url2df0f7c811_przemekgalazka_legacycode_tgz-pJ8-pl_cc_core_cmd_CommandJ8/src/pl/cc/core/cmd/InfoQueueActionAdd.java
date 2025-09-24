package pl.cc.core.cmd;

import pl.cc.real.RealQueue;

/**
 * Zalogowanie agenta do kolejki
 * 
 * @since Jan 17, 2008
 */
public class InfoQueueActionAdd extends Command {
	RealQueue queue;
	String action;

	//+INFO Queue 'bluzeczka', Action 'add'
	//+INFO Queue 'noga-wka', Action 'add', Penalty '0', Paused
	private InfoQueueActionAdd(String orginalLine, RealQueue queue){
		super(orginalLine);
		this.queue=queue;
	}
	
	public static Command factoryInt(String s){
		String [] names = {"queue","action","penalty", "paused"};
		boolean [] required = {true, true, true, false};
		String [] v = getValuesINFO(s, names, required);
		if (v==null) return null;
		if(! v[1].toLowerCase().equals("add")) return null;
		
		RealQueue queue = new RealQueue(v[0],"0",v[2]); 
		if (v[3]!=null) {
			queue.setPaused(new Boolean(v[3]));
		}		
		
		return new InfoQueueActionAdd(
				s,
				queue
		);
	}

	@Override
	public int getType() {
		return CMD_QUEUE_ACTION_ADD;
	}

	public RealQueue getQueue() {
		return queue;
	}


	
}
