package core;

public class Jugador {
	private EstadoCasilla color;
	private int puntaje; 
	private int[] estrategia;
	
	public Jugador(EstadoCasilla color) {
		super();
		this.color = color;
		this.puntaje = 0;
		this.estrategia = null; //cargar estrategia desde un archivo
	}
	
	public Jugador(EstadoCasilla color, int puntaje, int[] estrategia) {
		super();
		this.color = color;
		this.puntaje = puntaje;
		this.estrategia = estrategia;
	}

	public EstadoCasilla color() {
		return color;
	}

	public int getPuntaje() {
		return puntaje;
	}

	public void setPuntaje(int puntaje) {
		this.puntaje = puntaje;
	}

	public int[] getEstrategia() {
		return estrategia;
	}

	public void setEstrategia(int[] estrategia) {
		this.estrategia = estrategia;
	}
	
	
	
}
