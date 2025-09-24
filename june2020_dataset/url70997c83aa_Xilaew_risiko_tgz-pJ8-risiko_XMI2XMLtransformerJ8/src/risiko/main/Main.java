package risiko.main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import risiko.Engine;

public class Main {
	static Engine engine = new Engine();
	
	Main () throws IOException, RuntimeException{
		try {
			System.out.println("default.board");
			
			System.out.println(getClass().getResourceAsStream("default.board"));
			System.out.println("main.risiko.default.board");
			System.out.println(getClass().getResourceAsStream("main.risiko.default.board"));
			System.out.println("default.board");
			System.out.println(getClass().getResourceAsStream("default.board"));
			System.out.println("default.board");
			System.out.println(getClass().getResourceAsStream("default.board"));
			System.out.println("default.board");
			System.out.println(getClass().getResourceAsStream("default.board"));
			engine.setBoard(getClass().getResourceAsStream("default.board"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {
		ServerSocket ss = new ServerSocket(8021);
		new Main();
		Socket cs;
		while ((cs=ss.accept()) != null ){
			new Thread (new MainThread(cs,engine)).start();
		}
		ss.close();
	}

}
