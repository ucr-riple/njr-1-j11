package pl.cc.core.cmd;

import pl.cc.real.RealQueue;

public class InfoQueue extends Command {
	RealQueue queue;
	
	//+INFO Queue 'rekawek', Count '0'
	//+INFO Queue 'fakro', Count '0', Predefined 'false'
	//+INFO Queue 'fakro', Count '0', Predefined 'false', CustomType 'ivr', Paused 'true'
	
	private InfoQueue(String orginalLine, RealQueue queue){
		super(orginalLine);
		this.queue=queue;
	}
	
	public static Command factoryInt(String s){
		String [] names = {"queue","count","predefined","customType","paused", "wrapup"};
		boolean [] required = {true, true, false, false, false, false};
		String [] values = getValuesINFO(s, names, required); 
		if (values!=null){
			Boolean predefined, paused;
			String customType = values[3];
			int wrapup;
			if (values[2]!=null) {
				predefined = new Boolean(values[2]);
			} else {
				predefined = false;
			}
			if (values[4]!=null) {
				paused = new Boolean(values[4]);
			} else {
				paused = false;
			}
			if (values[5]!=null) {
				wrapup=new Integer(values[5]).intValue();
			}else{
				wrapup=0;
			}
			
			RealQueue queue = new RealQueue(values[0],values[1], predefined);
			queue.setPaused(paused);
			queue.setWrapup(wrapup);
			InfoQueue iq = new InfoQueue(s, queue);
			if (customType!=null){
				iq.queue.setCustomType(customType);
			}
			return iq;
		} else {
			return null;
		}
	}

	@Override
	public int getType() {
		return CMD_INFO_QUEUE;
	}

	public RealQueue getQueue() {
		return queue;
	}
	
	@Override
	public String serializeToString(){
		if (queue==null){
			return null;
		} else {
			StringBuffer b = new StringBuffer();
			b.append("+INFO Queue '"+queue.getName()+"'");
			b.append(", Count '"+queue.getWaitCount()+"'");
			b.append(", Predefined '"+queue.isPredefined()+"'");
			if (queue.getCustomType()!=null){
				b.append(", CustomType '"+queue.getCustomType()+"'");
			}
			return new String(b);
		}
	}
}
