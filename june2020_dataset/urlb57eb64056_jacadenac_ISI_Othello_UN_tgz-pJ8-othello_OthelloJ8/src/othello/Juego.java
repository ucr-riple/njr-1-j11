package othello;

import java.awt.EventQueue;

public class Juego extends Thread{

	public void run() {
		jugar();
	}
	
	private void jugar(){
		EventQueue.invokeLater(new UIJuego());
	}
	
}
