package core;

/**
 * The state of a square - a point in the board.
 * The state can be:
 * <ul>
 * <li>Black - the square is colored black
 * <li>White - the square is colored white
 * <li>Pssbl - the square is a next possible move
 * <li>Empty - the square is empty.
 * </ul>
 * Each state is represented with a state-symbol.
 * */

public enum EstadoCasilla {
	BLACK('B'),
	WHITE('W'),
	PSSBL('P'),
	EMPTY('E'),
	WALL('M');
	private final char symbol;
	
	EstadoCasilla(char symbol) {
		this.symbol = symbol;
	}

	public char symbol() {
		return this.symbol;
	}

	public EstadoCasilla opposite() {
		return this == BLACK ? WHITE : BLACK;
	}

	@Override
	public String toString() {
		return String.valueOf(symbol);
	}
	
	public static EstadoCasilla obtenerEstado(String estado){
		EstadoCasilla estadoCasilla;
		switch(estado){
			case "B":
				estadoCasilla = BLACK;
				break;
			case "W":
				estadoCasilla =  WHITE;
				break;
			case "P":
				estadoCasilla =  PSSBL;
				break;
			case "M":
				estadoCasilla =  WALL;
				break;
			default:
				estadoCasilla =  EMPTY;
				break;
		}
		return estadoCasilla;
	}
	
}
