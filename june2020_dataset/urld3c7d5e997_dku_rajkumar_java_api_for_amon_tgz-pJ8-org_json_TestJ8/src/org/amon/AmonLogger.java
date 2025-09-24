package org.amon;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.ProtocolException;
import java.net.Proxy;
import java.net.URL;

public class AmonLogger {

	private int responseCode;
	private AmonBuilder amonNotice;	

	private void addingProperties(final HttpURLConnection connection) throws ProtocolException {
		connection.setDoOutput(true);
		connection.setRequestProperty("Content-type", "application/json");
		connection.setRequestMethod("POST");
	}
	
	private Proxy getConnectionProxy(){
		Proxy proxy = null;
		String proxyHost = this.amonNotice.getAmonProxyHost();
		if(proxyHost != null && !"".equals(proxyHost.trim())){
			String proxyPort = this.amonNotice.getAmonProxyPort();
			if(proxyPort == null || "".equals(proxyPort.trim())) proxyPort = "80";
			proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost.trim(), Integer.parseInt(proxyPort)));
			}
		return proxy;
		}
	
	private HttpURLConnection createConnection() throws IOException {
		HttpURLConnection connection = null;
		Proxy proxy = getConnectionProxy();
		if(proxy != null){
			connection = (HttpURLConnection) new URL(amonNotice.getAmonServerUrl()).openConnection(proxy);
			}else{
				connection = (HttpURLConnection) new URL(amonNotice.getAmonServerUrl()).openConnection();
				}
		return connection;
		}

	public  AmonLogger(final AmonBuilder notice) {
		try {
			this.amonNotice = notice;
			final HttpURLConnection toHoptoad = createConnection();
			addingProperties(toHoptoad);
			String toPost = new AmonStreamBuilder(notice).toString();
			System.out.println(toPost);
			send(toPost, toHoptoad);
		} catch (final Exception e) {
			System.out.println(amonNotice.toString());
			e.printStackTrace();
		}
	}

	private void send(final String yaml, final HttpURLConnection connection) throws IOException {
		final OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
		writer.write(yaml);
		writer.close();
		responseCode = connection.getResponseCode();
	}

	public int getResponseCode() {
		return responseCode;
	}
}
