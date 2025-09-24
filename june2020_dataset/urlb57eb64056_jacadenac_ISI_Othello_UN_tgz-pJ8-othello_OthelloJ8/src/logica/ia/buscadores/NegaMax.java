package logica.ia.buscadores;

import java.awt.Point;
import java.util.Set;

import core.EstadoCasilla;
import core.Tablero;
import core.Tablero.TipoTablero;
import logica.ExploradorMovimientos;
import logica.ia.evaluacion.Evaluacion;

/**
 * <p>Negamax search is a slightly variant formulation of minimax search
 * that relies on the zero-sum property of a two-player game.</p>
 * <p>By definition the value of a position to player A in such a game is
 * the negation of the value to player B. Thus, the player on move looks
 * for a move that maximizes the negation of the value of the position
 * resulting from the move: this successor position must by definition
 * have been valued by the opponent. </p>
 * <p>The reasoning of the previous sentence works regardless of whether
 * A or B is on move. This means that a single computation can be used to
 * value all positions</p>
 * <p>This is a coding simplification over minimax, which requires that A
 * select the move with the maximum-valued successor while B selects the
 * move with the minimum-valued successor.</p>
 * 
 */

public class NegaMax extends BuscadorAbstracto implements /*Buscador,*/ BuscadorSimple {

	@Override
	public ResultadoBusqueda busquedaSimple(Tablero tablero, TipoTablero tipoTablero,  EstadoCasilla colorJugador, int profundidad, Evaluacion evfuncion) {
		
		EstadoCasilla colorOponente;
		if (colorJugador == EstadoCasilla.BLACK){
			colorOponente = EstadoCasilla.WHITE;
		} else {
			colorOponente = EstadoCasilla.BLACK;
		}
		
		if (profundidad <= 0 || esEstadoFinal(tablero)) {
			return new ResultadoBusqueda(null, evfuncion.evaluar(tablero, tipoTablero, colorJugador));
		} else { /* hay más para revisar */
			Set<Point> movidasPosibles = ExploradorMovimientos.explorar(tablero, colorJugador);
			ResultadoBusqueda mejor = new ResultadoBusqueda(null, Integer.MIN_VALUE);
			if (movidasPosibles.isEmpty()) { /* se pierde turno - revisar siguiente jugador */
				movidasPosibles = ExploradorMovimientos.explorar(tablero, colorOponente);
				if (movidasPosibles.isEmpty()) { /* fin del juego - żexiste un ganador? */
					switch (Integer.signum(tablero.contar(colorJugador) - tablero.contar(colorOponente))){
						case -1:
							mejor = new ResultadoBusqueda(null, Integer.MIN_VALUE);
							break;
						case 0:
							mejor = new ResultadoBusqueda(null, 0);
							break;
						case 1:
							mejor = new ResultadoBusqueda(null, Integer.MAX_VALUE);
							break;
					}
				} else { /* El juego continua - no hay movidas por revisar */
					mejor = busquedaSimple(tablero, tipoTablero, colorOponente, profundidad-1, evfuncion).negado();
				}
			} else { /* revisar el puntaje de cada movida */
				for (Point siguienteMovimientoPosible : movidasPosibles) {
					Tablero subTablero = tablero.clone();
					subTablero.hacerMovimiento(siguienteMovimientoPosible, colorJugador);
					double puntaje = busquedaSimple(subTablero, tipoTablero, colorOponente, profundidad-1, evfuncion).negado().obtenerPuntaje();
					if (mejor.obtenerPuntaje() < puntaje) {
						/* asignar el mejor puntaje y la movida correspondiente */
						mejor = new ResultadoBusqueda(siguienteMovimientoPosible, puntaje);
					}
				}
			}
			
			/**/Tablero subTablero2 = tablero.clone();
			/**///subTablero2.hacerMovimiento(mejor, colorJugador);
			evfuncion.evaluar(subTablero2, tipoTablero, colorJugador);
			
			
			return mejor;
		}
	} 
	
}
