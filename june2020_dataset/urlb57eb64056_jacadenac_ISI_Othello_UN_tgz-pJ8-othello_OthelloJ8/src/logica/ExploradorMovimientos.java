package logica;

import java.awt.Point;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import core.Tablero;
import core.EstadoCasilla;



public class ExploradorMovimientos {
	/**
	* Dada una posición de partida - semilla - comprueba si hay posibilidad 
	* de encontrar un resultado deseado mirando hacia alguna dirección.
	* 
	* @param tablero - el tablero sobre el cual se realiza la búsqueda
	* @param semilla - empezamos a buscar desde la semilla
	* @param direccion - qué dirección debemos buscar
	* @return si la búsqueda en esta dirección es propensa a tener resultados interesantes
	*/
	private static boolean  deberiaBuscar(final Tablero tablero, final Point semilla, final Direccion direccion){

			Point nextPoint = direccion.siguiente(semilla);
			return puntoEsValido(nextPoint, tablero) ? tablero.obtenerEstadoCasilla(nextPoint) == tablero.obtenerEstadoCasilla(semilla).opposite() : false;

	}
	
	/**
	* Comprueba si el punto dado es un punto válido en el tablero.
	* es válido si se encuentra dentro de los límites del tablero.
	*
	* @ param punto - el punto a comprobar
	* @ return si el punto es válido
	*/
	private static boolean puntoEsValido(Point punto, Tablero tablero) {
		return punto.x >= 0 && punto.x < Tablero.TABLERO_LARGO 
				&& punto.y >= 0 && punto.y < Tablero.TABLERO_ANCHO 
				&& tablero.obtenerEstadoCasilla(punto) != EstadoCasilla.WALL ;
	}
	
	/**
	* Encuentra todos los puntos posibles donde un jugador con el 
	* estado dado puede hacer su próximo movimiento
	* 
	* @param tablero - es el tablero a examinar
	* @param estado - color del jugador
	* @return posibles movimientos del jugador
	*/
	
	public static Set<Point> explorar(final Tablero tablero, final EstadoCasilla estado){
		Set<Point> movimientosPosibles = new HashSet<Point>();
		Set<Point> EstadoPuntos = tablero.obtenerCasillas(estado);
		for (Point semilla : EstadoPuntos) {
			for (Direccion direccion : Direccion.values()){
				if (deberiaBuscar(tablero, semilla, direccion)){
					Point nextPoint = direccion.siguiente(semilla);
					nextPoint = direccion.siguiente(nextPoint);
					while (puntoEsValido(nextPoint, tablero)){
						if (tablero.obtenerEstadoCasilla(nextPoint) == estado) {
							break;
						} else if (tablero.obtenerEstadoCasilla(nextPoint) == EstadoCasilla.EMPTY){
							movimientosPosibles.add(nextPoint);
							break;
						}
						nextPoint = direccion.siguiente(nextPoint);
					}
				}
			}
		}
		return movimientosPosibles;
	}
	
	/**
	* Dada una posición inicial - semilla - encontrar todos los puntos en el tablero
	* que deben ser llenados o tiene su color/estado cambiado.
	* 
	* @param tablero - es el tablero a examinar
	* @param semilla - es la posición inicial
	* @return los puntos que se necesitan para cambiar el estado
	*/
	public static Set<Point> casillasParaLlenar(final Tablero tablero, final Point semilla) {
		Set<Point> listaDeLlenado = new HashSet<Point>();
		EstadoCasilla estadoSemilla = tablero.obtenerEstadoCasilla(semilla);
		for (Direccion direccion : Direccion.values()) {
			if(deberiaBuscar(tablero, semilla, direccion)) {
				Point nextPoint = direccion.siguiente(semilla);
				LinkedList<Point> listaTemp = new LinkedList<Point>();
				while (puntoEsValido(nextPoint, tablero)) {
					EstadoCasilla estadoSiguiente = tablero.obtenerEstadoCasilla(nextPoint);
					if (estadoSiguiente == estadoSemilla.opposite()) {
						listaTemp.add(nextPoint);
					} else if (estadoSiguiente == estadoSemilla) {
						listaDeLlenado.addAll(listaTemp);
						break;
					} else if (estadoSiguiente == EstadoCasilla.EMPTY) {
						break;
					}
					nextPoint = direccion.siguiente(nextPoint);
				}
			}
		}
		return listaDeLlenado;
	}
	
	
}
