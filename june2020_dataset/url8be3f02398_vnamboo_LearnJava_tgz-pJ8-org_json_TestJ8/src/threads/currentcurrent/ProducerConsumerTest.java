package threads.currentcurrent;

public class ProducerConsumerTest {
	public static void main(String[] args) {
		Buffer buffer = new Buffer();
		Producer p = new Producer(buffer);
		Consumer c = new Consumer(buffer);
		
		p.start();
		c.start();
		
	}
}
