package org.dclayer.application;

import java.io.IOException;

import org.dclayer.application.exception.ConnectionException;
import org.dclayer.crypto.key.KeyPair;

public class ApplicationInstanceBuilder {
	
	private Service service;
	
	private KeyPair addressKeyPair;
	private NetworkEndpointActionListener defaultNetworksOnReceiveListener = null;
	
	public ApplicationInstanceBuilder(Service service) {
		this.service = service;
	}
	
	/**
	 * Creates the {@link ApplicationInstance} and connects it to the service.
	 * @return the {@link ApplicationInstance} connected to the service.
	 * @throws IOException
	 */
	public ApplicationInstance connect() throws ConnectionException {
		
		// if we've got an address key pair, we should also have at least one address
		if(addressKeyPair != null) {
			if(defaultNetworksOnReceiveListener == null) {
				throw new RuntimeException("since you specified an address keypair, you might want your application instance to also join some networks. try ApplicationInstanceBuilder.joinDefaultNetworks().");
			}
		}
		
		return new ApplicationInstance(
				service.getInetAddress(),
				service.getPort(),
				addressKeyPair,
				defaultNetworksOnReceiveListener);
		
	}
	
	//
	
	public ApplicationInstanceBuilder addressKeyPair(KeyPair addressKeyPair) {
		this.addressKeyPair = addressKeyPair;
		return this;
	}
	
	public ApplicationInstanceBuilder joinDefaultNetworks(NetworkEndpointActionListener defaultNetworksOnReceiveListener) {
		this.defaultNetworksOnReceiveListener = defaultNetworksOnReceiveListener;
		return this;
	}
	
}
