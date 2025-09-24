package M1.server_details;

import M0.Trace;
import M2.Composant;
import M2.Configuration;
import M2.Port;

public class ConnexionManagerComponent extends Composant {

	public ConnexionManagerComponent(String name, Configuration config) {
		super(name, config);
		
		this.addPort(new ExternalSocketInPort("ExternalSocketIn", this));
		this.addPort(new ExternalSocketOutPort("ExternalSocketOut", this));
	}
	
	@Override
	public void run(Port sender, String message) {
		Trace.printMessage("Message received by server");
		
		String response = "";
		if(message.contains("hello")) {
			response = "Hi !";
		}
		else {
			response = "Please say hello";
		}
		
		this.getPortF("ExternalSocketOut").activate(response);
	}

}
