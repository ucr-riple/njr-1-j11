package patterns.gof.behavioral.mediator;

import java.util.ArrayList;
import java.util.List;

public class PilotContactCenter extends Mediator {
	private List<Pilot> pilots = new ArrayList<Pilot>();
	
	public void addPilot(Pilot pilot) {
		pilots.add(pilot);
	}
	
	public void removePilot(Pilot pilot) {
		pilots.remove(pilot);
	}
 
    @Override
    public void send(String message, Pilot pilot) {
        for (Pilot p : pilots) {
        	if (!p.equals(pilot)) {
        		p.notify(message);
        	}
        }
    }
}
