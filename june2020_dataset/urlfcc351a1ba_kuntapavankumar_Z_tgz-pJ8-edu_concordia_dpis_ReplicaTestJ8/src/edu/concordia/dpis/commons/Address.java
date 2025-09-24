package edu.concordia.dpis.commons;

import java.io.Serializable;

// An address to identify a node
public class Address implements Serializable {

	private static final long serialVersionUID = 1L;

	// a unique id
	private String id;

	// ip/host name
	private String host;
	// port number
	private int port;

	public Address(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}
}
