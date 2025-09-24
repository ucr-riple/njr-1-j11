package br.odb.open688.app.net;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

import br.odb.open688.app.Open688Server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class HTTPStatusTicker implements Runnable {

	static class RequestHandler implements HttpHandler {

		private Open688Server server;

		public RequestHandler(Open688Server openSubServer) {
			super();

			this.server = openSubServer;
		}

		public void handle(HttpExchange t) throws IOException {
			String response = server.getStatus();
			t.sendResponseHeaders(200, response.length());
			t.setAttribute("Access-Control-Allow-Origin", "*");
			OutputStream os = t.getResponseBody();
			os.write(response.getBytes());
			os.close();
		}
	}

	volatile ServerSocket server;
	volatile Socket socket;
	volatile DataOutputStream out;
	private Open688Server openSubServer;

	public HTTPStatusTicker(Open688Server openSubServer) {
		this.openSubServer = openSubServer;
	}

	@Override
	public void run() {
		HttpServer server;
		try {
			server = HttpServer.create(new InetSocketAddress(8000), 0);
			server.createContext("/test", new RequestHandler(openSubServer));
			server.setExecutor(null); // creates a default executor
			server.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
