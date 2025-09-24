package M0;

import java.io.FileNotFoundException;

import M1.client_server.ClientServerConfiguration;
import M1.server_details.ServerDetailsConfiguration;

public class Application {

	public static void main(String[] args) throws FileNotFoundException {
		
		// MAIN CONFIGURATION
		ClientServerConfiguration mainConfig = new ClientServerConfiguration("Client-Server-Config");
		mainConfig.build();
		
		
		// SERVER DETAILS CONFIGURATION
		ServerDetailsConfiguration serverConfig = new ServerDetailsConfiguration("Server-details-Config");
		serverConfig.setParent(mainConfig.getComposant("Server"));
		mainConfig.getComposant("Server").setSubconf(serverConfig);
		serverConfig.build();

		
		// Bindings
		M2.Binding serverToSubConf = new M2.Binding("ServerToSubConf");
		serverToSubConf.bind(serverConfig.getPortR("ReceiveRequestPortConfig"), mainConfig.getComposant("Server").getPortR("ReceiveRequest"));
		mainConfig.addLink(serverToSubConf);
		serverConfig.addLink(serverToSubConf);
		
		M2.Binding SubConfToServer = new M2.Binding("SubConfToServer");
		SubConfToServer.bind(serverConfig.getPortF("AnswerRequestPortConfig"), mainConfig.getComposant("Server").getPortF("AnswerRequest"));
		mainConfig.addLink(SubConfToServer);
		serverConfig.addLink(SubConfToServer);
		
		serverConfig.addLink("CommIn", serverConfig.getPortR("ReceiveRequestPortConfig"), serverConfig.getComposant("ConnexionManager").getPortR("ExternalSocketIn"));
		serverConfig.addLink("CommOut", serverConfig.getPortF("AnswerRequestPortConfig"), serverConfig.getComposant("ConnexionManager").getPortF("ExternalSocketOut"));
		
		
		// RUNNING THE APPLICATION
		mainConfig.getComposant("Client").getServiceF("SendRequestService").activate("hello");
	}

}
