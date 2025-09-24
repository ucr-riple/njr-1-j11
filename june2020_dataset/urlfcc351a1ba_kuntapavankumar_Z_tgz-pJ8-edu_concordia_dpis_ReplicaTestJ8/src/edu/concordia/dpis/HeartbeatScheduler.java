package edu.concordia.dpis;

import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

// Runs a task periodically with the ScheduledThreadPoolExecutor.
// The task is to identify the failed nodes
public abstract class HeartbeatScheduler {

	public void start() {
		new ScheduledThreadPoolExecutor(1).scheduleWithFixedDelay(
				new HeartBeatTask(), 10, 30, TimeUnit.SECONDS);
		System.out.println("HeartbeatScheduler is up and running");
	}

	class HeartBeatTask implements Runnable {
		@Override
		public void run() {
			// all the nodes whose alivenss need to be checked
			List<Node> nodes = getNodes();
			for (Node node : nodes) {
				// check if the node is alive
				boolean isAlive = node.isAlive();
				// if the node is not alive
				if (!isAlive) {
					// do something a node is failed
					onFailedNode(node);
				} else {
					System.out.println("Node [" + node.getAddress().getHost()
							+ "," + node.getAddress().getPort() + "] is alive");
				}
			}
		}
	}

	// all nodes to check for aliveness
	protected abstract List<Node> getNodes();

	// a node failed
	protected abstract void onFailedNode(Node node);

	// is the node a leader
	protected abstract boolean isLeader(String id);

}