package pl.cc.events.real;

import pl.cc.real.RealCall;


public interface RealQueueListener {
	
	public void onWaitCountChange(int waitCount);
	public void onQueueLeave(RealCall call);
	// zmieniła się ilość rozmawiających agentów
	public void onTalkingAgentCountChange(int talkingCount);
	// zmieniła się ilość wolnych agentów
	public void onFreeAgentCountChange(int freeCount);
	
}
