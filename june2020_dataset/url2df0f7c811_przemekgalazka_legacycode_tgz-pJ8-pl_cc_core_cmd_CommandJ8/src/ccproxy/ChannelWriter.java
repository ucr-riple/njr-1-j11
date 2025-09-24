package ccproxy;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.LinkedList;


public class ChannelWriter {

	Selector selector;
	LinkedList<String> listToSend;
	SocketChannel channel;
	boolean writePaused=false;
	ByteBuffer b;
	
	public ChannelWriter(SocketChannel channel, Selector selector) {
		listToSend=new LinkedList<String>();
		this.channel=channel;
		b = ByteBuffer.allocateDirect(5000);
		this.selector=selector;
	}

	
	public void println(String s) {
		synchronized (this){
		if (s!=null){	
		s=s+"\r\n";
		//System.out.println("l"+s.length());
		if (writePaused){
			listToSend.addLast(s);
			//System.out.println("_________ add "+s.length());
			return;
		}
		}
		writePaused=false;
		do {
			if (s!=null){
			b.clear();
			b.put(s.getBytes());
			b.flip();
			}
			
			try {
			int wrt=channel.write(b);
			if (s!=null && b.remaining()!=0){
				//System.out.println("______________________"+wrt+"!="+s.getBytes().length);
				writePaused=true;
				channel.register(selector, SelectionKey.OP_READ|SelectionKey.OP_WRITE);
				selector.wakeup();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			try {
				channel.socket().close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		//listToSend.add(s);
		 if (!listToSend.isEmpty() & !writePaused){
			 s=listToSend.removeFirst();
			 //System.out.println("_________ resume "+s.length());
		 }
		}
		while (!writePaused && !listToSend.isEmpty());
		}
	}


	public void close() {
		try {
			channel.close();
			selector.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
