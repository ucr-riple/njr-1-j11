package threads.prodconsumer;

public class Consumer extends Thread {
	private Buffer buffer;

	public Consumer(Buffer buffer) {
		this.buffer = buffer;
	}
	
	public void run(){
		while(true){
			buffer.consume();
		}
	}
}
