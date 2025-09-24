package logica.ia.buscadores;

import java.awt.Point;

/**
 * Un resultado de búsqueda consiste en el mapeo de un movimiento al 
 * resultado obtenido por el método de evaluación que se eligió.
 * 
 * Alternativamente, podríamos mantener una tabla hash movidas/llaves 
 * a resultados/valores, pero que en realidad no necesitamos almacenar 
 * más el resultado de la búsqueda con el mejor movimiento, al mismo 
 * tiempo. Ese mapa tendría siempre un tamańo de 1, pues si fuéramos 
 * a insertar una nueva asignación no necesitaríamos todas las anteriores. 
 */

public class ResultadoBusqueda {
	
	private Point punto;
	private double puntaje;
	
	public double obtenerPuntaje() {
		return puntaje;
	}
	
	public Point obtenerPunto() {
		return punto;
	}
	
	public ResultadoBusqueda negado() {
		return new ResultadoBusqueda(punto, -puntaje);
	}
	
	public ResultadoBusqueda(Point punto, double puntaje) {
		this.punto = punto;
		this.puntaje = puntaje;
	}
}
