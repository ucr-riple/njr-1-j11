package threads.currentcurrent;

import java.util.Random;

public class Producer extends Thread {
	private Buffer buffer;

	public Producer(Buffer buffer) {
		this.buffer = buffer;
	}

	public void run() {
		Random rand = new Random();
		while(true){
			int num=rand.nextInt();
			buffer.produce(num);
		}
	}
}
