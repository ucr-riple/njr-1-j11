package logica.ia.evaluacion;

import core.EstadoCasilla;
import core.Tablero;
import core.Tablero.TipoTablero;

/**
 * Interfaz de la función de evaluación. Cada método de evaluación debe implementar esta
 * 
 * @author Alejandro
 */

public interface Evaluacion {
	public double evaluar(final Tablero tablero, TipoTablero tipoTablero, final EstadoCasilla colorJugador);
}
