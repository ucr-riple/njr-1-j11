package org.dclayer.net.interservice;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.dclayer.crypto.key.Key;
import org.dclayer.net.address.Address;
import org.dclayer.net.applicationchannel.ApplicationChannel;
import org.dclayer.net.applicationchannel.ApplicationChannelTarget;
import org.dclayer.net.network.NetworkType;

public class InterservicePolicy {
	
	public static class ApplicationChannelEndpoints {
		
		private Address localAddress;
		private Address remoteAddress;
		
		public ApplicationChannelEndpoints(Address localAddress, Address remoteAddress) {
			this.localAddress = localAddress;
			this.remoteAddress = remoteAddress;
		}
		
		public Address getLocalAddress() {
			return localAddress;
		}
		
		public Address getRemoteAddress() {
			return remoteAddress;
		}
		
	}
	
	//
	
	private List<Key> remotePublicKeys;
	private List<NetworkType> networkTypes;
	private Map<ApplicationChannelTarget, ApplicationChannel> allowedIncomingApplicationChannels;
	private Map<Address, List<ApplicationChannel>> requestApplicationChannelsLocal;
	private Map<Address, List<ApplicationChannel>> requestApplicationChannelsRemote;
	
	public InterservicePolicy restrictRemotePublicKey(Key... remotePublicKeys) {
		if(this.remotePublicKeys == null) this.remotePublicKeys = new LinkedList<>();
		for(Key remotePublicKey : remotePublicKeys) {
			this.remotePublicKeys.add(remotePublicKey);
		}
		return this;
	}
	
	public InterservicePolicy restrictNetworkType(NetworkType... networkTypes) {
		if(this.networkTypes == null) this.networkTypes = new LinkedList<>();
		for(NetworkType networkType : networkTypes) {
			this.networkTypes.add(networkType);
		}
		return this;
	}

	public InterservicePolicy allowIncomingApplicationChannel(ApplicationChannel applicationChannel) {
		if(allowedIncomingApplicationChannels == null) allowedIncomingApplicationChannels = new HashMap<>();
		allowedIncomingApplicationChannels.put(applicationChannel.getApplicationChannelTarget(), applicationChannel);
		return this;
	}
	
	public InterservicePolicy requestApplicationChannel(ApplicationChannel applicationChannel) {
		
		if(requestApplicationChannelsLocal == null) requestApplicationChannelsLocal = new HashMap<>();
		if(requestApplicationChannelsRemote == null) requestApplicationChannelsRemote = new HashMap<>();
		
		Address localAddress = applicationChannel.getLocalAddress();
		Address remoteAddress = applicationChannel.getRemoteAddress();
		
		List<ApplicationChannel> applicationChannelsLocal = requestApplicationChannelsLocal.get(localAddress);
		if(applicationChannelsLocal == null) requestApplicationChannelsLocal.put(localAddress, applicationChannelsLocal = new LinkedList<ApplicationChannel>());
		applicationChannelsLocal.add(applicationChannel);
		
		List<ApplicationChannel> applicationChannelsRemote = requestApplicationChannelsRemote.get(remoteAddress);
		if(applicationChannelsRemote == null) requestApplicationChannelsRemote.put(remoteAddress, applicationChannelsRemote = new LinkedList<ApplicationChannel>());
		applicationChannelsRemote.add(applicationChannel);
		
		return this;
	}
	
	//
	
	public ApplicationChannel checkIncomingApplicationChannel(ApplicationChannelTarget applicationChannelTarget) {
		if(allowedIncomingApplicationChannels != null) {
			return allowedIncomingApplicationChannels.get(applicationChannelTarget);
		}
		return null;
	}
	
	//
	
	public List<Key> getRemotePublicKeys() {
		return remotePublicKeys;
	}
	
	public List<NetworkType> getNetworkTypes() {
		return networkTypes;
	}
	
	public Map<Address, List<ApplicationChannel>> getRequestApplicationChannelsLocal() {
		return requestApplicationChannelsLocal;
	}
	
	public List<ApplicationChannel> getRequestApplicationChannelsForLocalAddress(Address localAddress) {
		if(requestApplicationChannelsLocal == null) return null;
		return requestApplicationChannelsLocal.get(localAddress);
	}
	
	public List<ApplicationChannel> getRequestApplicationChannelsForRemoteAddress(Address remoteAddress) {
		if(requestApplicationChannelsRemote == null) return null;
		return requestApplicationChannelsRemote.get(remoteAddress);
	}
	
	//
	
	@Override
	public String toString() {
		
		StringBuilder stringBuilder = new StringBuilder("InterservicePolicy {\n");
		
		if(remotePublicKeys == null) {
			stringBuilder.append("\tnot restricting remote public keys\n");
		} else {
			stringBuilder.append("\trestricting remote public keys, allowed:\n");
			for(Key remotePublicKey : remotePublicKeys) {
				stringBuilder.append("\t\t");
				stringBuilder.append(remotePublicKey.toString());
				stringBuilder.append("\n");
			}
		}
		
		if(networkTypes == null) {
			stringBuilder.append("\tnot restricting network types\n");
		} else {
			stringBuilder.append("\trestricting network types, allowed:\n");
			for(NetworkType networkType : networkTypes) {
				stringBuilder.append("\t\t");
				stringBuilder.append(networkType.toString());
				stringBuilder.append("\n");
			}
		}
		
		if(allowedIncomingApplicationChannels == null) {
			stringBuilder.append("\tnot permitting any incoming application channel requests\n");
		} else {
			stringBuilder.append("\tpermitting incoming requests for application channels:\n");
			for(ApplicationChannel applicationChannel : allowedIncomingApplicationChannels.values()) {
				stringBuilder.append("\t\t");
				stringBuilder.append(applicationChannel.toString());
				stringBuilder.append("\n");
			}
		}
		
		if(requestApplicationChannelsLocal == null) {
			stringBuilder.append("\tnot requesting any application channels\n");
		} else {
			stringBuilder.append("\trequesting application channels:\n");
			for(Map.Entry<Address, List<ApplicationChannel>> entry : requestApplicationChannelsLocal.entrySet()) {
				stringBuilder.append("\t\t");
				stringBuilder.append(entry.getValue().size());
				stringBuilder.append(" from ");
				stringBuilder.append(entry.getKey().toString());
				stringBuilder.append(":\n");
				for(ApplicationChannel applicationChannel : entry.getValue()) {
					stringBuilder.append("\t\t\t");
					stringBuilder.append(applicationChannel.toString());
					stringBuilder.append("\n");
				}
			}
		}
		
		stringBuilder.append("}");
		return stringBuilder.toString();
		
	}

}
