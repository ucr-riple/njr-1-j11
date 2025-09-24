package patterns.gof.behavioral.mediator;

import patterns.gof.helpers.Client;

public class MediatorClient extends Client {
	@Override
	public void main() {
		cleanOutput();
		
		PilotContactCenter center = new PilotContactCenter();
		
		Pilot anderson = new PilotAnderson(center);
		center.addPilot(anderson);
		Pilot smith = new PilotSmith(center);
		center.addPilot(smith);
		
		anderson.send("Anderson here. do you read me? over!");
		smith.send("Smith here. read you well Anderson. over and out!");
		
		super.main("Mediator");
	}
}