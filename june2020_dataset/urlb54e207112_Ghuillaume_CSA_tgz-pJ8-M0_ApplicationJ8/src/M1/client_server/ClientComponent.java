package M1.client_server;

import M2.Composant;
import M2.Configuration;

public class ClientComponent extends Composant {

	public ClientComponent(String name, Configuration config) {
		super(name, config);
		
		this.addPort(new SendRequestPort("SendRequest", this));
		this.addPort(new ReceiveAnswerPort("ReceiveAnswer", this));
		this.addService(new SendRequestService("SendRequestService", this));
	}

}
