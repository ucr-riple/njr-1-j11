package org.dclayer.net.network.slot;

import java.util.LinkedList;
import java.util.List;

import org.dclayer.crypto.challenge.CryptoChallenge;
import org.dclayer.datastructure.map.slotmap.Slot;
import org.dclayer.meta.HierarchicalLevel;
import org.dclayer.meta.Log;
import org.dclayer.net.address.Address;
import org.dclayer.net.applicationchannel.ApplicationChannel;
import org.dclayer.net.interservice.InterserviceChannel;
import org.dclayer.net.network.NetworkNode;


public class AddressSlot extends Slot<Address> implements HierarchicalLevel {
	
	private HierarchicalLevel parentHierarchicalLevel;
	private boolean remote = false;
	
	private Address address;
	private CryptoChallenge inCryptoChallenge;
	private CryptoChallenge trustedSwitchOutCryptoChallenge;
	
	private byte connectionBase = InterserviceChannel.CONNECTIONBASE_STRANGER;
	private byte maxAllowedOutConnectionBase = InterserviceChannel.CONNECTIONBASE_STRANGER;
	
	private List<NetworkSlot> networkSlots = new LinkedList<>();
	private List<NetworkNode> networkNodesToJoin = new LinkedList<>();
	
	public AddressSlot(HierarchicalLevel parentHierarchicalLevel, boolean remote, int slotId, Address address) {
		super(slotId);
		this.parentHierarchicalLevel = parentHierarchicalLevel;
		this.remote = remote;
		this.address = address;
	}
	
	public Address getAddress() {
		return address;
	}
	
	public void setInCryptoChallenge(CryptoChallenge inCryptoChallenge) {
		this.inCryptoChallenge = inCryptoChallenge;
	}
	
	public CryptoChallenge getInCryptoChallenge() {
		return inCryptoChallenge;
	}
	
	public void setTrustedSwitchOutCryptoChallenge(CryptoChallenge trustedSwitchOutCryptoChallenge) {
		this.trustedSwitchOutCryptoChallenge = trustedSwitchOutCryptoChallenge;
	}
	
	public CryptoChallenge getTrustedSwitchOutCryptoChallenge() {
		return trustedSwitchOutCryptoChallenge;
	}
	
	public void setOutConnectionBase(byte outConnectionBase) {
		if(outConnectionBase > this.maxAllowedOutConnectionBase) {
			Log.msg(this, "limiting new outgoing connection base %d down to maximum allowed outgoing connection base %d", outConnectionBase, this.maxAllowedOutConnectionBase);
			outConnectionBase = this.maxAllowedOutConnectionBase;
		}
		setConnectionBase(outConnectionBase);
	}
	
	public void setConnectionBase(byte connectionBase) {
		Log.msg(this, "setting connection base from %d to %d", this.connectionBase, connectionBase);
		this.connectionBase = connectionBase;
	}
	
	public byte getConnectionBase() {
		return connectionBase;
	}
	
	public void setMaxAllowedOutConnectionBase(byte maxAllowedOutConnectionBase) {
		Log.debug(this, "updating maximum allowed outgoing connection base from %d to %d", this.maxAllowedOutConnectionBase, maxAllowedOutConnectionBase);
		this.maxAllowedOutConnectionBase = maxAllowedOutConnectionBase;
	}
	
	public byte getMaxAllowedOutConnectionBase() {
		return maxAllowedOutConnectionBase;
	}
	
	public void addNetworkSlot(NetworkSlot networkSlot) {
		networkSlots.add(networkSlot);
	}
	
	public boolean removeNetworkSlot(NetworkSlot networkSlot) {
		return networkSlots.remove(networkSlot);
	}
	
	public List<NetworkSlot> getNetworkSlots() {
		return networkSlots;
	}
	
	public void addNetworkNodeToJoin(NetworkNode networkNode) {
		networkNodesToJoin.add(networkNode);
	}
	
	public List<NetworkNode> popNetworkNodesToJoin() {
		List<NetworkNode> networkNodesToJoin = this.networkNodesToJoin;
		this.networkNodesToJoin = null;
		return networkNodesToJoin;
	}

	@Override
	public Address getSearchObject() {
		return address;
	}
	
	@Override
	public String contentToString() {
		return String.format("%s, %s, %d network slots", remote ? "remote" : "local", address, networkSlots.size());
	}

	@Override
	public HierarchicalLevel getParentHierarchicalLevel() {
		return parentHierarchicalLevel;
	}
	
}
