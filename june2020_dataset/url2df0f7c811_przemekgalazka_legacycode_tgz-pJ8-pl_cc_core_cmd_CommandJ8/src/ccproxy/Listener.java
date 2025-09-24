package ccproxy;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import org.apache.log4j.Logger;

public class Listener extends Thread {
	static Logger log = Logger.getLogger(Listener.class);
	ServerSocketChannel serverChan=null;
	GadajAsterisk polaczenieAsterisk;
	AgentConnectionList agenci;
	DBConnection dbConn;
	Parametry p;

	public Listener(String port, Parametry param) {
		super();
		this.p = param;


		try {
			serverChan = ServerSocketChannel.open();
			serverChan.configureBlocking(true);
			serverChan.socket().bind(new InetSocketAddress(new Integer(port).intValue()));

		} catch (IOException e) {
			log.error("Could not listen on port: "+port);
			System.exit(-1);
		}
	}

	public void run() {
		setName("T-TCPListener");
		AgentConnection agent;

		while (true) {
			try {
				SocketChannel sChannel = serverChan.accept();
				sChannel.configureBlocking(false);
				sChannel.socket().setTcpNoDelay(true);
				sChannel.socket().setSendBufferSize(8192);
				agent = new AgentConnection(sChannel, p);

				agent.start();

			} catch (IOException e) {
				e.printStackTrace();
				log.error("Accept failed.");
				System.exit(1);
			}
		}
	}
}
