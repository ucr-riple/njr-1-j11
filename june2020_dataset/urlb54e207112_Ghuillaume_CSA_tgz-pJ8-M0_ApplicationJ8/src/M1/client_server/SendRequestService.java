package M1.client_server;

import M0.Trace;
import M2.Composant;
import M2.ServiceFourni;

public class SendRequestService extends ServiceFourni {

	public SendRequestService(String name, Composant parent) {
		super(name, parent);
	}

	public void run(String message) {
		Trace.printMessage("Client wants to send : " + message);
		((SendRequestPort) this.parent.getPortF("SendRequest")).activate(message);
	}

}
