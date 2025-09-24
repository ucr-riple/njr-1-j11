package edu.concordia.dpis;

@Deprecated
public class MulticastReceiverReplica2 {

	public static void main(String[] args) {
		//anyone replica who wants to be the replica other than leader will have to invoke this thread on port 4446
		(new Thread(new MulticastReceiver(4446))).start();

	}

}
