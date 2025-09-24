package agent;

import java.util.Vector;

import agent.data.Kit;


/**
 * Monitor that protects the shared Kits. Unused in v0.
 * @author Daniel Paje
 */
public class KitProducerConsumerMonitor extends Object {
	// Since there can only be 3 kits on the stand at any given time.
	private final int N = 3;
	private int count = 0;
	private final Vector<Kit> theData;

	synchronized public void insert(Kit data) {
		while (count == 4) {
			try {
				System.out.println("\tFull, waiting");
				wait(5000); // Full, wait to add
			} catch (InterruptedException ex) {
			}
			;
		}

		insert_item(data);
		count++;
		if (count == 1) {
			System.out.println("\tNot Empty, notify");
			notify(); // Not empty, notify a
						// waiting consumer
		}
	}

	synchronized public Kit remove() {
		Kit data;
		while (count == 0) {
			try {
				System.out.println("\tEmpty, waiting");
				wait(5000); // Empty, wait to consume
			} catch (InterruptedException ex) {
			}
		}
		;

		data = remove_item();
		count--;
		if (count == N - 1) {
			System.out.println("\tNot full, notify");
			notify(); // Not full, notify a
						// waiting producer
		}
		return data;
	}

	synchronized int numKits() {
		return theData.size();
	}

	private void insert_item(Kit data) {
		theData.addElement(data);
	}

	private Kit remove_item() {
		Kit data = theData.firstElement();
		theData.removeElementAt(0);
		return data;
	}

	public KitProducerConsumerMonitor() {
		theData = new Vector<Kit>();
	}
}
