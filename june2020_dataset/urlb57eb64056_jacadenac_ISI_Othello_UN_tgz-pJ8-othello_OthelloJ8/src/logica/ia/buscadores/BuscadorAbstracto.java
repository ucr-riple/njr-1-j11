package logica.ia.buscadores;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import logica.ia.evaluacion.Evaluacion;
import core.EstadoCasilla;
import core.Jugador;
import core.Tablero;
import core.Tablero.TipoTablero;

/**
 * La clase buscadorAbstracto, proporciona una opción de "Búsqueda" al azar.
 * Implementa métodos auxiliares básicos como el mínimo y el máximo y esEstadoFinal
 * 
 * @author Alejandro
 */

public abstract class BuscadorAbstracto implements /*Buscador,*/ BuscadorSimple {

	/*
	@Override
	public abstract ResultadoBusqueda busqueda(final Tablero tablero, final Jugador jugador, int alfa,
			int beta, final int produndidad, final Evaluacion funcion);
	*/
	@Override
	public abstract ResultadoBusqueda busquedaSimple(final Tablero tablero, TipoTablero tipoTablero, final EstadoCasilla colorJugador,
			final int profundidad, final Evaluacion funcion);
	
	protected int maximo(int a, int b) {
		return Math.max(a, b);
	}
	
	protected int minimo(int a, int b) {
		return Math.min(a, b);
	}
	
	protected Point eleccionAleatoria(Tablero tablero, Jugador jugador) {
		List<Point> movidasPosibles = new ArrayList<Point>(tablero.obtenerMovidasPosibles(jugador));
		int historial = (int) Math.random() * (movidasPosibles.size() - 1);
		return movidasPosibles.get(historial);
	}
	
	/**
	 * El juego termina si: <br/>
	 * <ol>
	 * <li> el tablero está lleno</li>
	 * <li> un jugador tiene puntaje = 0</li>
	 * <li> ninguno tiene una siguiente movida válida</li>
	 * </ol>
	 * 
	 * @param tablero - el tablero a examinar.
	 * @return si el juego está terminado
	 */
	protected boolean esEstadoFinal(final Tablero tablero) {
		return tablero.estaLleno() 
				|| tablero.contar(EstadoCasilla.BLACK) == 0
				|| tablero.contar(EstadoCasilla.WHITE) == 0;
	}
	
}
