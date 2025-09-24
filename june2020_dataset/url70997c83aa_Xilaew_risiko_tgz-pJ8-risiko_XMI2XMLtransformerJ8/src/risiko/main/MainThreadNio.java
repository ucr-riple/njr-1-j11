package risiko.main;

import java.io.IOException;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class MainThreadNio implements Runnable {
	
	Selector sel;
	

	@Override
	public void run() {
		try {
			sel = Selector.open();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub

	}
	
	public boolean registerClient(SocketChannel sc){
//		sc.register(this.sel, sc.);
		return false;
	}

}
