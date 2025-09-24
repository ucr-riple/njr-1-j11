package M1.client_server;

import M0.Trace;
import M2.Composant;
import M2.Configuration;
import M2.Port;

public class ServerComponent extends Composant {

	public ServerComponent(String name, Configuration config) {
		super(name, config);
		
		this.addPort(new AnswerRequestPort("AnswerRequest", this));
		this.addPort(new ReceiveRequestPort("ReceiveRequest", this));
	}

	@Override
	public void run(Port sender, String message) {
		String ack = "ACK";
		Trace.printMessage("Server wants to send ack: " + ack);
		((AnswerRequestPort) this.getPortF("AnswerRequest")).activate(ack);
	}

}
