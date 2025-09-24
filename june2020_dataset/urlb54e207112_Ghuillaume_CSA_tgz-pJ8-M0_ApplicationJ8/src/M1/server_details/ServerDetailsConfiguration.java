package M1.server_details;

import M2.Configuration;

public class ServerDetailsConfiguration extends Configuration {

	public ServerDetailsConfiguration(String name) {
		super(name);
		
		this.addPort(new AnswerRequestConf("AnswerRequestPortConfig", this));
		this.addPort(new ReceiveRequestConf("ReceiveRequestPortConfig", this));
	}

	public void build() {

		// Component
		new ConnexionManagerComponent("ConnexionManager", this);
	}
}
