package M1.client_server;

import M2.Configuration;
import M2.Connecteur;

public class RPCConnector extends Connecteur{

	public RPCConnector(String name, Configuration config) {
		super(name, config);
		
		config.addConnector(this);
		this.addConnectedRoles(new ClientCallerRole("ClientCaller", this), new ServerCalledRole("ServerCalled", this));
		this.addConnectedRoles(new ServerCallerRole("ServerCaller", this), new ClientCalledRole("ClientCalled", this));
	}

}
