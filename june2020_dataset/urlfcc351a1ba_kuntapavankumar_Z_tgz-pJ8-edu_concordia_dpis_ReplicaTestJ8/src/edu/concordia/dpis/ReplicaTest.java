package edu.concordia.dpis;

import java.net.UnknownHostException;

@Deprecated
public class ReplicaTest {

	public static void main(String[] args) throws UnknownHostException {
		start();
	}

	private static void start() throws UnknownHostException {
		new Thread(new Runnable() {

			@Override
			public void run() {
				LeaderTest leaderTest = new LeaderTest();
				try {
					leaderTest.start();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}

			}
		}).start();

		new Thread(new Runnable() {

			@Override
			public void run() {
				Replica2Test replica2Test = new Replica2Test();
				try {
					replica2Test.start();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}

			}
		}).start();

		new Thread(new Runnable() {

			@Override
			public void run() {
				Replica3Test replica3Test = new Replica3Test();
				try {
					replica3Test.start();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
			}
		}).start();

		new Thread(new Runnable() {

			@Override
			public void run() {
				Replica4Test replica4Test = new Replica4Test();
				try {
					replica4Test.start();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
			}
		}).start();

	}
}
