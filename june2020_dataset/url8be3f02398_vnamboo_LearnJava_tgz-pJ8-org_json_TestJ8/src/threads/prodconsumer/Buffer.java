package threads.prodconsumer;

public class Buffer {

	private int data;
	private boolean empty;

	public Buffer() {
		this.empty = true;
	}

	public synchronized void produce(int dataItem) {
		while (!this.empty) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		this.data = dataItem;
		this.empty = false;
		this.notify();
		System.out.println("Produced: " + dataItem);
	}

	public synchronized int consume() {
		while (this.empty) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		this.empty = true;
		this.notify();
		System.out.println("Consumed data: " + data);
		return data;
	}
}
