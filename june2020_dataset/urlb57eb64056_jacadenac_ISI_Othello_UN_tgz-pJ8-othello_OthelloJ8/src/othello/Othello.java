package othello;

/* 
 * Variante del Juego Othello
 * 
 * @author Alejandro
 * @author Jefferson
 */

public class Othello {
	
	public static void main(String[] args) {
		Juego othello = new Juego();
		othello.start();
		
		Entrenamiento entrenamientoOthello = new Entrenamiento();
		entrenamientoOthello.start();
	}
	
}
