package org.dclayer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.dclayer.PreLinkCommunicationManager.Result;
import org.dclayer.crypto.Crypto;
import org.dclayer.crypto.key.Key;
import org.dclayer.crypto.key.KeyPair;
import org.dclayer.exception.net.buf.BufException;
import org.dclayer.exception.net.parse.ParseException;
import org.dclayer.listener.net.NetworkInstanceListener;
import org.dclayer.listener.net.OnReceiveListener;
import org.dclayer.meta.HierarchicalLevel;
import org.dclayer.meta.Log;
import org.dclayer.net.Data;
import org.dclayer.net.a2s.ApplicationConnection;
import org.dclayer.net.a2s.ApplicationConnectionActionListener;
import org.dclayer.net.address.Address;
import org.dclayer.net.applicationchannel.ApplicationChannel;
import org.dclayer.net.buf.DataByteBuf;
import org.dclayer.net.crisp.CrispMessageReceiver;
import org.dclayer.net.crisp.CrispPacket;
import org.dclayer.net.crisp.message.NeighborRequestCrispMessageI;
import org.dclayer.net.interservice.InterserviceChannel;
import org.dclayer.net.interservice.InterserviceChannelActionListener;
import org.dclayer.net.interservice.InterservicePolicy;
import org.dclayer.net.link.Link;
import org.dclayer.net.link.Link.Status;
import org.dclayer.net.link.LinkSendInterface;
import org.dclayer.net.link.OnLinkActionListener;
import org.dclayer.net.link.channel.data.DataChannel;
import org.dclayer.net.lla.CachedLLA;
import org.dclayer.net.lla.LLA;
import org.dclayer.net.lla.cache.LLACache;
import org.dclayer.net.lla.database.LLADatabase;
import org.dclayer.net.lla.priority.LLAPriority;
import org.dclayer.net.lla.priority.LLAPriorityAspect;
import org.dclayer.net.network.ApplicationNetworkInstance;
import org.dclayer.net.network.NetworkInstance;
import org.dclayer.net.network.NetworkInstanceCollection;
import org.dclayer.net.network.NetworkNode;
import org.dclayer.net.network.NetworkType;
import org.dclayer.net.network.component.NetworkPacket;
import org.dclayer.net.network.component.NetworkPayload;
import org.dclayer.net.network.routing.RouteQuality;
import org.dclayer.net.network.routing.RoutingTable;
import org.dclayer.net.network.slot.NetworkSlot;
import org.dclayer.net.socket.DatagramSocket;
import org.dclayer.net.socket.StreamSocket;

public class DCLService implements CrispMessageReceiver<NetworkInstance>, OnReceiveListener, NetworkInstanceListener, ApplicationConnectionActionListener, LinkSendInterface<CachedLLA>, OnLinkActionListener<CachedLLA>, InterserviceChannelActionListener, HierarchicalLevel {
	
	/**
	 * local DatagramSocket, used for Service-to-Service communication
	 */
	private DatagramSocket s2sDatagramSocket;
	/**
	 * local StreamSocket, used for Application-to-Service communication
	 */
	private StreamSocket a2sStreamSocket;
	
	private KeyPair linkCryptoInitializationKeyPair;
	
	private LLACache llaCache = new LLACache();
	private LLADatabase llaDatabase;
	
	private Address localAddress;
	
	private NetworkInstanceCollection networkInstanceCollection = new NetworkInstanceCollection();
	
	private PreLinkCommunicationManager preLinkCommunicationManager = new PreLinkCommunicationManager(this);
	
	private ConnectionManager connectionManager;
	
	private List<InterserviceChannel> interserviceChannels = new LinkedList<>();
	private List<NetworkNode> networkNodes = new LinkedList<>();
	
	private DataByteBuf networkPayloadDataByteBuf = new DataByteBuf();
	private CrispPacket inCrispPacket = new CrispPacket();
	
	private HashMap<LLA, AtomicInteger> localLLAReports = new HashMap<>();
	private LLA localLLA;
	
	public DCLService(DatagramSocket s2sDatagramSocket, StreamSocket a2sStreamSocket, LLADatabase llaDatabase) throws IOException {
		
		this.llaDatabase = llaDatabase;
		
		Log.debug(this, "generating link crypto initialization keypair...");
		this.linkCryptoInitializationKeyPair = Crypto.generateLinkCryptoInitRSAKeyPair();
		Log.debug(this, "done, public key sha1: %s (%d bits)", Crypto.sha1(linkCryptoInitializationKeyPair.getPublicKey().toData()), linkCryptoInitializationKeyPair.getPublicKey().getNumBits());
		
		Log.debug(this, "generating address RSA keypair...");
		KeyPair addressKeyPair = Crypto.generateAddressRSAKeyPair();
		Log.debug(this, "done, public key sha1: %s (%d bits)", Crypto.sha1(addressKeyPair.getPublicKey().toData()), addressKeyPair.getPublicKey().getNumBits());
		this.localAddress = new Address<>(addressKeyPair, new NetworkInstanceCollection());
		
		onAddress(localAddress);
		
		this.s2sDatagramSocket = s2sDatagramSocket;
		this.a2sStreamSocket = a2sStreamSocket;
		
		s2sDatagramSocket.setOnReceiveListener(this);
		a2sStreamSocket.setApplicationConnectionActionListener(this);
		
		this.connectionManager = new ConnectionManager(this, llaDatabase);
		llaCache.setCachedLLAStatusListener(connectionManager);
		
	}
	
	public Address getServiceAddress() {
		return localAddress;
	}
	
	public LLACache getLLACache() {
		return llaCache;
	}
	
	/**
	 * @deprecated Only for use with JUnit test cases
	 */
	@Deprecated
	public ConnectionManager getConnectionManager() {
		return connectionManager;
	}
	
	public void storeLLAs(List<LLA> llas) {
		llaDatabase.store(llas);
	}
	
	public void storeLLAs(List<LLA> llas, CachedLLA originCachedLLA) {
		// TODO remember originCachedLLA for NAT traversal
		storeLLAs(llas);
	}
	
	public List<LLA> getRandomConnectedLLAs(int limit) {
		return connectionManager.getRandomConnectedLLAs(limit);
	}
	
	@Override
	public Data getServiceIgnoreData() {
		return preLinkCommunicationManager.getCurrentIgnoreData();
	}
	
	public void join(NetworkType networkType) {
		
		final NetworkPayload inNetworkPayload = networkType.makeInNetworkPayload(null);
		
		NetworkInstance networkInstance = new NetworkInstance(this, networkType, localAddress, true) {
			@Override
			public synchronized boolean onForward(NetworkPacket networkPacket) {
				Log.msg(this, "received NetworkPacket: %s", networkPacket.represent(true));
				inNetworkPayload.setReadDataComponent(networkPacket.getDataComponent());
				DCLService.this.onForward(inNetworkPayload, this);
				return true;
			}

			@Override
			public void neighborRequest(Key senderPublicKey, String actionIdentifier, LLA senderLLA, boolean response, Data ignoreData) {
				onNeighborRequest(this, senderPublicKey, actionIdentifier, senderLLA, response);
			}
		};
		
		localAddress.getNetworkInstanceCollection().addNetworkInstance(networkInstance);
		onNetworkInstance(networkInstance);
		
		Log.msg(this, "joined network: %s", networkInstance);
		
	}
	
	private void onForward(NetworkPayload networkPayload, NetworkInstance networkInstance) {
		
		try {
			
			networkPayload.read();
			
		} catch (ParseException e) {
			
			Log.exception(this, e);
			return;
			
		} catch (BufException e) {
			
			Log.exception(this, e);
			return;
			
		}
		
		if(networkPayload.isDestinedForService()) {
			
			Log.debug(this, "received network payload destined for service: %s", networkPayload.represent(true));
			onServiceNetworkPayload(networkPayload, networkInstance);
			
		} else {
			
			Log.debug(this, "service received network payload that is not destined for service, ignoring: ", networkPayload.represent(true));
			// TODO
			
		}
		
	}
	
	@Override
	public void onReadyChange(InterserviceChannel interserviceChannel, boolean ready) {
		if(ready) {
			CachedLLA cachedLLA = interserviceChannel.getCachedLLA();
			cachedLLA.setStatus(CachedLLA.CONNECTED);
			Log.debug(this, "interservice channel to LLA %s ready", cachedLLA);
		}
	}
	
	@Override
	public synchronized void onNewRemoteNetworkNode(InterserviceChannel interserviceChannel, NetworkNode remoteNetworkNode, NetworkSlot localNetworkSlot) {
		
		// TODO also check if we should maybe join that network
		
		// just use the first network instance with the same network type (as they are all connected anyways)
		NetworkInstance localNetworkInstance = networkInstanceCollection.findFirst(remoteNetworkNode.getNetworkType());
		
		if(localNetworkInstance != null) {
			
			RoutingTable routingTable = localNetworkInstance.getRoutingTable();
			boolean added = routingTable.add(remoteNetworkNode);
			
			if(added) {
				
				Log.debug(this, "added %s to routing table for %s (and all other local network instances with network type %s)", remoteNetworkNode, localNetworkInstance, localNetworkInstance.getNetworkType());
				
				for(NetworkInstance networkInstance : networkInstanceCollection.findAll(remoteNetworkNode.getNetworkType())) {
					
					RouteQuality routeQuality = networkInstance.getNetworkType().getRouteQuality(networkInstance.getScaledAddress(), remoteNetworkNode.getScaledAddress());
					if(routeQuality == null) continue;
					
					LLAPriorityAspect llaPriorityAspect = interserviceChannel.getCachedLLA().addLLAPriorityAspect(LLAPriority.Type.ROUTING, networkInstance, remoteNetworkNode.getScaledAddress().toString());
					
					llaPriorityAspect.setLock(routeQuality.critical);
					llaPriorityAspect.update(routeQuality.quality);
					
					networkInstance.addLLAPriorityAspect(llaPriorityAspect);
					remoteNetworkNode.addLLAPriorityAspect(llaPriorityAspect);
					
					// TODO: drop LLAPriorityAspects when network instance is removed!
					
				}
				
			}
			
		}
		
	}
	
	@Override
	public synchronized void onRemoveRemoteNetworkNode(InterserviceChannel interserviceChannel, NetworkNode remoteNetworkNode) {
		
		NetworkInstance localNetworkInstance = networkInstanceCollection.findFirst(remoteNetworkNode.getNetworkType());
		
		if(localNetworkInstance != null) {
			
			RoutingTable routingTable = localNetworkInstance.getRoutingTable();
			boolean removed = routingTable.remove(remoteNetworkNode);
			
			if(removed) {
				
				Log.debug(this, "removed %s from routing table for %s", remoteNetworkNode, localNetworkInstance);
				
				remoteNetworkNode.dropLLAPriorityAspects();
				
			}
			
		}
		
	}
	
	@Override
	public void onInterserviceChannelClosed(InterserviceChannel interserviceChannel) {
		interserviceChannel.getCachedLLA().setStatus(CachedLLA.DISCONNECTED);
		// TODO
	}
	
	@Override
	public void onLocalLLAReport(InterserviceChannel interserviceChannel, LLA oldLocalLLA, LLA newLocalLLA) {
		synchronized(localLLAReports) {
			
			if(oldLocalLLA != null) {
				AtomicInteger i = localLLAReports.get(oldLocalLLA);
				if(i.decrementAndGet() <= 0) {
					localLLAReports.remove(oldLocalLLA);
				}
			}
			
			AtomicInteger i = localLLAReports.get(newLocalLLA);
			if(i == null) {
				i = new AtomicInteger(0);
				localLLAReports.put(newLocalLLA, i);
			}
			i.incrementAndGet();
			
			int maxCount = 0;
			LLA maxLLA = null;
			for(Map.Entry<LLA, AtomicInteger> entry : localLLAReports.entrySet()) {
				if(entry.getValue().get() > maxCount) {
					maxCount = entry.getValue().get();
					maxLLA = entry.getKey();
				}
			}
			
			boolean notify = localLLA == null;
			localLLA = maxLLA;
			if(notify) localLLAReports.notify();
			
			Log.debug(this, "local LLA is %s (map updated due to transition from %s to %s by remote %s: %s)", localLLA, oldLocalLLA, newLocalLLA, interserviceChannel.getCachedLLA(), localLLAReports.toString());
			
		}
	}

	@Override
	public LLA getLocalLLA(boolean wait) {
		if(localLLA == null && wait) {
			synchronized(localLLAReports) {
				if(localLLA == null) {
					Log.debug(this, "local LLA not known yet, waiting");
					try {
						localLLAReports.wait();
					} catch (InterruptedException e) {}
					Log.debug(this, "local LLA known, returning: %s", localLLA);
				}
			}
		}
		return localLLA;
	}
	
	@Override
	public ApplicationConnection onApplicationConnection(Socket socket) {
		return new ApplicationConnection(this, this, socket);
	}
	
	@Override
	public synchronized void onAddress(Address asymmetricKeyPairAddress) {
		// do nothing (add AddressSlots when adding NetworkInstances)
	}
	
	@Override
	public synchronized void onNetworkInstance(NetworkInstance networkInstance) {
		Log.msg(this, "onNetworkInstance: %s", networkInstance);
		networkInstanceCollection.addNetworkInstance(networkInstance);
		for(InterserviceChannel interserviceChannel : interserviceChannels) {
			interserviceChannel.joinNetwork(networkInstance);
		}
	}

	@Override
	public synchronized void onServiceNetworkPayload(NetworkPayload networkPayload, NetworkInstance networkInstance) {
		
		networkPayloadDataByteBuf.setData(networkPayload.getPayloadData());
		
		try {
			
			inCrispPacket.read(networkPayloadDataByteBuf);
			
		} catch (ParseException e) {
			Log.exception(this, e);
			return;
		} catch (BufException e) {
			Log.exception(this, e);
			return;
		}
		
		Log.debug(this, "crisp packet received: %s", inCrispPacket.represent(true));
		
		inCrispPacket.getCrispMessage().callOnReceiveMethod(this, networkInstance);
		
	}

	@Override
	public void onReceiveNeighborRequestCrispMessage(NeighborRequestCrispMessageI neighborRequestCrispMessage, NetworkInstance networkInstance) {
		
		// TODO verify senderPublicKey!
		
		Key senderPublicKey = neighborRequestCrispMessage.getPublicKey();
		String actionIdentifier = neighborRequestCrispMessage.getActionIdentifier();
		LLA senderLLA = neighborRequestCrispMessage.getSenderLLA();
		boolean response = neighborRequestCrispMessage.isResponse();
		Data ignoreData = response ? null : neighborRequestCrispMessage.getIgnoreDataComponent().getData();
		
		networkInstance.neighborRequest(senderPublicKey, actionIdentifier, senderLLA, response, ignoreData);
		
	}
	
	//
	
	public InterservicePolicy makeDefaultInterservicePolicy() {
		return new InterservicePolicy();
	}
	
	//
	
	@Override
	public InterservicePolicy addDefaultIncomingApplicationChannelInterservicePolicyRules(InterservicePolicy interservicePolicy, NetworkInstance networkInstance, ApplicationChannel applicationChannel, LLA remoteLLA) {
		// TODO restrict
		return interservicePolicy.allowIncomingApplicationChannel(applicationChannel);
	}
	
	@Override
	public InterservicePolicy addDefaultOutgoingApplicationChannelInterservicePolicyRules(InterservicePolicy interservicePolicy, ApplicationChannel applicationChannel) {
		// TODO restrict
		return interservicePolicy.requestApplicationChannel(applicationChannel);
	}
	
	//
	
	public void connect(CachedLLA cachedLLA) {
		connectionManager.connect(cachedLLA);
	}
	
	@Override
	public synchronized void connect(LLA lla, InterservicePolicy interservicePolicy) {
		
		CachedLLA cachedLLA = getLLACache().getCachedLLA(lla, true);
		if(!cachedLLA.disconnected()) return; // do not connect if we're connected/connecting already
		
		cachedLLA.setInterservicePolicy(interservicePolicy);
		
		connect(cachedLLA);
		
	}
	
	public void connect(LLA lla) {
		connect(lla, makeDefaultInterservicePolicy());
	}
	
	@Override
	public synchronized void prepareForIncomingApplicationChannel(LLA lla, ApplicationNetworkInstance applicationNetworkInstance, ApplicationChannel applicationChannel, Data punchData) {
		
		CachedLLA cachedLLA = getLLACache().getCachedLLA(lla, true);
		Log.debug(this, "preparing for incoming application channel from %s", cachedLLA);
		
		InterservicePolicy interservicePolicy = cachedLLA.getInterservicePolicy();
		if(interservicePolicy == null) {
			interservicePolicy = makeDefaultInterservicePolicy();
			cachedLLA.setInterservicePolicy(interservicePolicy);
		}
		addDefaultIncomingApplicationChannelInterservicePolicyRules(interservicePolicy, applicationNetworkInstance, applicationChannel, lla);
		
		if(cachedLLA.disconnected()) {
			connectionManager.punch(cachedLLA, punchData);
		}
		
	}
	
	@Override
	public synchronized void initiateApplicationChannel(LLA lla, ApplicationNetworkInstance applicationNetworkInstance, ApplicationChannel applicationChannel) {
		
		CachedLLA cachedLLA = getLLACache().getCachedLLA(lla, true);
		Log.msg(this, "initiating application channel to %s", cachedLLA);
		
		InterserviceChannel interserviceChannel = cachedLLA.getInterserviceChannel();
		if(interserviceChannel == null) {
			
			Log.debug(this, "adding interservice policy to cached lla for application channel initiation to %s, connecting", cachedLLA);
			
			InterservicePolicy interservicePolicy = cachedLLA.getInterservicePolicy();
			if(interservicePolicy == null) {
				interservicePolicy = makeDefaultInterservicePolicy();
				cachedLLA.setInterservicePolicy(interservicePolicy);
			}
			addDefaultOutgoingApplicationChannelInterservicePolicyRules(interservicePolicy, applicationChannel);
			
			connect(cachedLLA);
			
		} else {

			Log.debug(this, "notifying interservice channel to initiate application channel to %s", cachedLLA);
			interserviceChannel.openApplicationChannel(applicationChannel);
			
		}
		
	}
	
	//
	
	public void onNeighborRequest(NetworkInstance networkInstance, Key senderPublicKey, String actionIdentifier, LLA senderLLA, boolean response) {
		
		Log.debug(this, "ignoring neighbor request for service's address: actionIdentifier=%s senderLLA=%s", actionIdentifier, senderLLA);
		
	}
	
	@Override
	public void onReceiveS2S(InetSocketAddress inetSocketAddress, Data data) {
		
		InetAddress inetAddress = inetSocketAddress.getAddress();
		int port = inetSocketAddress.getPort();
		
		Link link;
		
		CachedLLA cachedLLA = llaCache.getIPPortCachedLLA(inetAddress, port, false);
		
		if(cachedLLA == null || cachedLLA.disconnected()) {
			
			// we're being connected to
			
			Result result = preLinkCommunicationManager.permit(inetAddress, port, data);
			
			if(result != null) {
				
				if(result.done) {
					cachedLLA = llaCache.getIPPortCachedLLA(inetAddress, port, true);
					cachedLLA.setStatus(CachedLLA.CONNECTING_PRELINK);
					cachedLLA.setFirstLinkPacketPrefixData(result.firstLinkPacketPrefixData);
				}
			
				send(inetSocketAddress, result.echoData);
				
			}
				
			return;
			
		}
		
		link = cachedLLA.getLink();
		if(link == null) {
			
			if(cachedLLA.getFirstLinkPacketPrefixData() == null) {
				
				if(cachedLLA.disconnected()) {
					return;
				}
			
				// we're connecting via pre-link communication
				
				if(cachedLLA.getPunchData() != null && cachedLLA.getPunchData().equals(data)) {
					// we've received the same data as we sent
					// either the remote is echoing our data, or we're trying to connect to ourselves
					Log.debug(this, "seems like I'm talking to myself, aborting connection to %s", cachedLLA);
					cachedLLA.setPunchData(null);
					cachedLLA.setStatus(CachedLLA.DISCONNECTED);
					return;
				}
				
				Result result = preLinkCommunicationManager.echo(data);
				if(result.done) {
					cachedLLA.setLink(link = new Link<CachedLLA>(this, this, cachedLLA, this));
					cachedLLA.setStatus(CachedLLA.CONNECTING_LINK);
					cachedLLA.setPunchData(null);
					link.connect(result.firstLinkPacketPrefixData);
					return;
				}
				
				send(inetSocketAddress, result.echoData);
				return;
				
			} else {
				
				// we're being connected to and pre-link communication is already completed
				
				Data prefixData = cachedLLA.getFirstLinkPacketPrefixData();
				if(prefixData.equals(0, data, 0, prefixData.length())) {
				
					// the first link packet is prefixed with the expected data
					// -> create the link and feed it the rest (the latter happens at the bottom of this method)
					link = new Link<CachedLLA>(this, this, cachedLLA, this);
					cachedLLA.setLink(link);
					cachedLLA.setFirstLinkPacketPrefixData(null);
					cachedLLA.setStatus(CachedLLA.CONNECTING_LINK);
					data.relativeReset(prefixData.length());
					
				} else {
					
					// the remote most likely didn't get our last confirmation packet.
					// -> repeat the pre-link communication
					Result result = preLinkCommunicationManager.permit(inetAddress, port, data);
					send(inetSocketAddress, result.echoData);
					return;
					
				}
				
			}
			
		}
		
		// normal operation
		link.onReceive(data);
		
	}

	@Override
	public void sendLinkPacket(CachedLLA cachedLLA, Data data) {
		send(cachedLLA, data);
	}
	
	public void send(CachedLLA cachedLLA, Data data) {
		send(cachedLLA.getLLA().getSocketAddress(), data);
	}
	
	private void send(SocketAddress inetSocketAddress, Data data) {
		try {
			s2sDatagramSocket.send(inetSocketAddress, data);
		} catch (IOException e) {
			Log.exception(this, e, "Exception while sending link packet to %s", inetSocketAddress);
			return;
		}
	}

	@Override
	public DataChannel onOpenChannelRequest(CachedLLA cachedLLA, long channelId, String protocol) {
		
		switch(protocol) {
		case "org.dclayer.interservice": {
			
			cachedLLA.setStatus(CachedLLA.CONNECTING_CHANNEL);
			
			if(cachedLLA.getInterservicePolicy() == null) {
				cachedLLA.setInterservicePolicy(makeDefaultInterservicePolicy());
			}
			
			InterserviceChannel interserviceChannel = new InterserviceChannel(this, this, cachedLLA, channelId, protocol);
			
			synchronized(this) {
				interserviceChannels.add(interserviceChannel);
				for(NetworkNode networkNode : networkInstanceCollection) {
					interserviceChannel.joinNetwork(networkNode);
				}
				cachedLLA.setInterserviceChannel(interserviceChannel);
			}
			
			return interserviceChannel;
			
		}
		}
		
		return null;
		
	}

	@Override
	public void onLinkStatusChange(CachedLLA cachedLLA, Status oldStatus, Status newStatus) {
		Link link = cachedLLA.getLink();
		if(newStatus == Link.Status.Connected && link.isInitiator()) {
			Log.debug(this, "link %s is connected, opening interservice channel", link);
			link.openChannel("org.dclayer.interservice");
		}
	}

	@Override
	public KeyPair getLinkCryptoInitializationKeyPair() {
		return linkCryptoInitializationKeyPair;
	}

	@Override
	public HierarchicalLevel getParentHierarchicalLevel() {
		return null;
	}
	
	@Override
	public String toString() {
		return "DCLService";
	}

}
