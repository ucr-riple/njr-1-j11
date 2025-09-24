package edu.concordia.dpis;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import edu.concordia.dpis.commons.Address;
import edu.concordia.dpis.commons.DeadNodeException;
import edu.concordia.dpis.commons.Message;
import edu.concordia.dpis.commons.ReliableMessage;
import edu.concordia.dpis.commons.TimeoutException;
import edu.concordia.dpis.fifo.FIFO;
import edu.concordia.dpis.messenger.UDPClient;
import edu.concordia.dpis.messenger.UDPServer;

/**
 * A Replica is a Distributed {@link Node}, being a UDP Server can reply to the
 * requests delegating the operation to the actual Implementation with the help
 * of a request handler. A Replica periodically checks for the aliveness of the
 * other nodes it is supposed to know, if it detects a node failure and it being
 * a leader an election would be started immediately notifying every other node.
 * 
 * @see Node
 * @see UDPServer
 * @since 1.0
 * @author Aliasgar
 * @author Pavan
 * @author Yanal
 * @author Ravindranath
 * 
 */
public class Replica extends UDPServer implements Node, FrontEndAware {

	private static final String SUCCESS = "SUCCESS";

	private static final int MULTICAST_PORT = 3000;

	private static final String MULTICAST_IP = "230.0.0.1";

	// this handler can handle all the application related requests
	private RequestHandler requestHandler;

	// this replica's address:id,host,port
	private Address address;

	// the leader in the group
	private String leaderName;

	// all the nodes this replica is supposed to know
	private List<Node> nodes = new ArrayList<Node>();

	// front end system's address:host,port
	private Address frontEndAddress;

	// listens to the group messages
	private MulticastListener multicastListener;

	// true if this replica is already conducting an election
	private boolean isElectionInProgress;

	// constructor
	public Replica(int port, int replicaId, Address frontEndAddress)
			throws UnknownHostException {
		this(port, false, replicaId, frontEndAddress);
		// initialize group listener
		multicastListener = getMulticastListener();
		// subscribe to the group
		multicastListener.joinGroup();
	}

	// returns a new MulticastListener on fixed port and ip
	private MulticastListener getMulticastListener() {
		return new MulticastListener(MULTICAST_PORT, MULTICAST_IP) {
			@Override
			public Message onMessage(ReliableMessage msg) {
				ReliableMessage reply;
				System.out.println("Got a multicast message "
						+ msg.getActualMessage());
				// messages which arrived on multicastsocket are not going to be
				// multicasted again
				msg.setMulticast(false);
				// process this message
				String str = getReplyMessage(msg);
				// prepare a new reply
				reply = new ReliableMessage(SUCCESS, msg.getToAddress()
						.getHost(), msg.getToAddress().getPort());
				// actual reply
				reply.addArgument(str);
				// indicate this is the reply message to a request
				reply.setReply(true);
				// if this message is supposed to be returned to the source
				reply.setReplyToThisMessage(msg.isReplyToThisMessage());
				// set this messages sequence number same as the request's
				// sequence number
				reply.setSequenceNumber(msg.getSequenceNumber());
				return reply;
			}
		};
	}

	// constructor
	// use this when this replica is supposed to be the leader
	public Replica(int port, boolean isLeader, int replicaId,
			Address frontEndAddress) throws UnknownHostException {
		super(port);
		this.address = new Address(InetAddress.getLocalHost().getHostAddress(),
				port);
		this.address.setId(replicaId + "");
		this.frontEndAddress = frontEndAddress;
		// if this replica is going to be the leader
		if (isLeader) {
			this.leaderName = replicaId + "";
			// now let the front end know that you are the leader
			notifyFrontEndTheNewLeader(this.address);
		}
		System.out
				.println("Replica initialized, start the replica by calling start() method.");
	}

	// start's the scheduler which periodically checks node failures
	public void startFailureDetection() {
		// a new scheduler
		new HeartbeatScheduler() {

			@Override
			protected boolean isLeader(String id) {
				System.out.println("isLeader[" + leaderName + "," + id + "]");
				System.out.println("isLeader:" + getLeaderName().equals(id));
				return getLeaderName().equals(id);
			}

			// oops, there's a node not responding , check if it is the leader ,
			// if yes shoot the election immediately
			protected void onFailedNode(Node node) {
				System.out.println("Now the leader is " + getLeaderName());
				System.out.println("Node deployed on"
						+ node.getAddress().getHost() + " and on port"
						+ node.getAddress().getPort() + " is not responding");
				if (getLeaderName() == null
						|| isLeader(node.getAddress().getId())) {
					System.out.println("Replica " + address.getId()
							+ " found leader failure");
					election(address.getId());
				}
			};

			// all the nodes this replica is supposed to know
			public List<Node> getNodes() {
				System.out.println("Heartbeatscheduler initiated by ["
						+ address.getHost() + "," + address.getPort() + "]");
				return nodes;
			};
		}.start();
	}

	// a generic method to handle all the requests from udpserver or
	// internally(through multicastlistner)
	@Override
	protected String getReplyMessage(Message request) {
		System.out.println("request received :" + request.getActualMessage());
		// in case the request is election, process it here
		if ("election".equalsIgnoreCase(request.getActualMessage())) {
			return election((String) request.getArguments().get(0)).toString();
			// in case the request is newLeader, process it here
		} else if ("newLeader".equalsIgnoreCase(request.getActualMessage())) {
			newLeader((String) request.getArguments().get(0));
			return SUCCESS;
		}
		// for all other requests, request handler can process
		return requestHandler.doOperation(request).toString();
	}

	// returns the leader this replica knows
	@Override
	public String getLeaderName() {
		return leaderName;
	}

	@Override
	/**
	 * if this replica is declared as the leader, then it is this replica's 
	 * responsibility to let other nodes know about it being the new leader 
	 * in effect immediately.
	 */
	public void newLeader(final String name) {
		int i = 0;
		for (Node node : nodes) {
			if (i >= 3) {
				break;
			}
			if (node.isAlive()) {
				i++;
			}
		}
		System.out.println("Active replicas:" + i + 1);
		if ((i + 1) < 3) {
			notifyFrontEndTheNewLeader(new Address("", 0));
			System.out
					.println("Leader cannot be elected because there must be atleast 3 replicas alive");
			return;
		}

		// update leadername in this replica
		this.leaderName = name;
		System.out.println("Now, the leader is " + name);
		// if this replica itself is the leader
		if (leaderName.equals(this.address.getId())) {
			// unsubscribe from listening to group messages
			if (multicastListener != null) {
				multicastListener.leaveGroup();
				multicastListener = null;
			}
			// let the front end know about this replica being the new leader
			notifyFrontEndTheNewLeader(this.address);
			// let the other nodes know about the new leader
			multicastNewLeader();
		} else {
			// if this replica is not the leader, it must be subscribed to the
			// group messages
			if (this.multicastListener == null) {
				this.multicastListener = getMulticastListener();
				this.multicastListener.joinGroup();
			}
		}
	}

	// send the new leader message to all the nodes listening
	private void multicastNewLeader() {
		System.out.println("Sending a multicast message about the newleader");
		// prepare a new leader message
		ReliableMessage leaderMsg = new ReliableMessage("newLeader",
				this.address.getHost(), this.address.getPort());
		// no need to reply for this message
		leaderMsg.setReplyToThisMessage(false);
		// leader name
		leaderMsg.addArgument(this.leaderName);
		try {
			FIFO.INSTANCE.multicast(leaderMsg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// let the front end know about the new leader
	private void notifyFrontEndTheNewLeader(Address address) {

		if (frontEndAddress != null) {
			ReliableMessage leaderMsg = new ReliableMessage("leaderInfo",
					frontEndAddress.getHost(), frontEndAddress.getPort());
			leaderMsg.addArgument(address);
			int attempts = 0;
			// give 3 attempts
			while (attempts < 3) {
				try {
					// send the leader message
					Message replyMsg = new UDPClient().send(leaderMsg, 2000);
					// if got an ok then it is fine job is done
					if ("OK".equals(replyMsg.getActualMessage()))
						break;
				} catch (TimeoutException e1) {
					e1.printStackTrace();
				}
				attempts++;
			}
		} else {
			System.out
					.println("Front end Info not available to tell about the new leader");
		}
	}

	/**
	 * Every process is assigned with a unique number to distinguish the highest
	 * and lowest among them. Every process is aware of every other process ID
	 * in the system. An election is initiated when one process (p4) identifies
	 * that the leader (p7) is down (not responding), p4 sends an ELECTION
	 * message to every other process whose number is above 7 this process
	 * number. If none of the processes respond within the timeout period the
	 * election initiator process wins and becomes the new leader and sends the
	 * COORDINATOR message to every other process. If one of the higher
	 * processes number (p5 or p6) replies, then they takes over and p4's work
	 * is done. When p5 or p6 receives an ELECTION message from one of its lower
	 * numbered processes they send an OK message to the sender p4. The receiver
	 * (p5 and p6) holds an election if it is not conducting one. As it goes on
	 * p5 gives up and the one remaining process (p6) is the new group leader.
	 * P6 announces to every other process that the new leader is in effect
	 * immediately.
	 */
	@Override
	public MessageType election(String replicaId) {
		System.out.println("election is going to be started by " + replicaId);
		// if the election is started by a lower id.
		if (Integer.parseInt(this.address.getId()) >= Integer
				.parseInt(replicaId) && !isElectionInProgress) {
			leaderName = null;
			new Thread(new Runnable() {
				@Override
				public void run() {
					for (final Node node : nodes) {
						if (leaderName == null) {
							try {
								// get all nodes whose id's are greater than
								// this replica's id
								if (Integer.parseInt(node.getAddress().getId()) > Integer
										.parseInt(address.getId())) {
									System.out
											.println("sent election message to node deployed on "
													+ node.getAddress()
															.getHost()
													+ " and on port "
													+ node.getAddress()
															.getPort());
									// send an election message
									MessageType mType = node.election(address
											.getId());
									// incase the node replied with a
									// COORDINATOR message, that it being the
									// leader
									if (MessageType.COORDINATOR.equals(mType)) {
										System.out
												.println("node deployed on "
														+ node.getAddress()
																.getHost()
														+ " and on port "
														+ node.getAddress()
																.getPort()
														+ " replied with a COORDINATOR message");
										// update the new leader info in this
										// replica
										newLeader(node.getAddress().getId());
										// or if the node replied with an OK
										// message, meaning it takes care of the
										// next step in election
									} else if (MessageType.OK.equals(mType)) {
										System.out.println("node deployed on "
												+ node.getAddress().getHost()
												+ " and on port"
												+ node.getAddress().getPort()
												+ " replied with a OK message");
										try {
											// and this replica calms down and
											// waits for the leader emerge
											System.out
													.println("will wait for some time to let some one inform me about the new leader");
											Thread.sleep((long) (3000 + (1000 * Math
													.random())));
										} catch (InterruptedException e) {
											// expect the leader is
											// available by this time
										}
									}
								}
							} catch (DeadNodeException e) {
								System.out.println(e.getMessage());
							}
						}
					}
					// no leader found
					if (leaderName == null) {
						System.out.println("electing myself as the new leader");
						// making this replica itself as the new leader
						newLeader(address.getId());
						// end of election
						isElectionInProgress = false;
					}
				}
			}).start();
		} else {
			System.out.println("Election is already in progress");
		}
		return MessageType.OK;
	}

	// returns this replica's address
	@Override
	public Address getAddress() {
		return this.address;
	}

	// is this replica alive
	@Override
	public boolean isAlive() {
		return true;
	}

	// set the request handler, to customize for the application
	public void setRequestHandler(RequestHandler requestHandler) {
		this.requestHandler = requestHandler;
	}

	// set the front end address
	@Override
	public void setFrontEndAddress(Address address) {
		this.frontEndAddress = address;
	}

	// node this replica is supposed to know about
	public Replica addNode(Node node) {
		this.nodes.add(node);
		return this;
	}
}
