package M1.client_server;

import M2.Configuration;

public class ClientServerConfiguration extends Configuration {

	public ClientServerConfiguration(String name) {
		super(name);
	}
	
	
	public void build() {

		// Components
		ClientComponent client = new ClientComponent("Client", this);
		ServerComponent server = new ServerComponent("Server", this);
		
		// Connector
		RPCConnector RPC = new RPCConnector("RPC", this);
		
		// Attachments
		this.addLink("ClientToRPC", RPC.getRoleFrom("ClientCaller"), client.getPortF("SendRequest"));
		this.addLink("RPCToClient", RPC.getRoleTo("ClientCalled"), client.getPortR("ReceiveAnswer"));
		this.addLink("ServerToRPC", RPC.getRoleFrom("ServerCaller"), server.getPortF("AnswerRequest"));
		this.addLink("RPCToServer", RPC.getRoleTo("ServerCalled"), server.getPortR("ReceiveRequest"));
	}

}
