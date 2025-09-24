package edu.concordia.dpis;

@Deprecated
public class MulticastServerMain {

	public static void main(String[] args) {
		//anyone replica who wants to be the leader will have to invoke this thread on port 4446
		new Thread(new MulticastServer(4446)).start();  

	}

}
