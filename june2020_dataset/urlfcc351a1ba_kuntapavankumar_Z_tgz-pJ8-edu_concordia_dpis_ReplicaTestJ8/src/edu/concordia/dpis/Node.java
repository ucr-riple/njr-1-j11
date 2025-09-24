package edu.concordia.dpis;

import edu.concordia.dpis.commons.Address;
import edu.concordia.dpis.commons.DeadNodeException;

// A Node which is identified by an address and will be part of a distributed system, which can act accordingly 
// on elections, when new leader gets elected.
public interface Node {

	// get this node address
	Address getAddress();

	// get the leader's name this node knows
	String getLeaderName() throws DeadNodeException;

	// same node/other node let this node know about the newleader
	void newLeader(String name) throws DeadNodeException;

	// same node/other node let this node know about the election to be
	// conducted
	MessageType election(String name) throws DeadNodeException;

	// is this node active
	boolean isAlive();

}