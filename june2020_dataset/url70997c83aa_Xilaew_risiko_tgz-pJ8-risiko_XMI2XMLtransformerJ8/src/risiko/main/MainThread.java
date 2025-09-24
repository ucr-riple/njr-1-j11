package risiko.main;

import java.io.IOException;
import java.net.Socket;

import risiko.Engine;

public class MainThread implements Runnable {
	Socket cs;
	Engine engine;

	MainThread(Socket cs, Engine engine) {
		this.cs = cs;
		this.engine = engine;
	}

	@Override
	public void run() {
		try {
			engine.getBoard(cs.getOutputStream());
			engine.getState(cs.getOutputStream());
		} catch (IOException e) {
		} finally {
			if (cs!=null){
				try {
					cs.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
