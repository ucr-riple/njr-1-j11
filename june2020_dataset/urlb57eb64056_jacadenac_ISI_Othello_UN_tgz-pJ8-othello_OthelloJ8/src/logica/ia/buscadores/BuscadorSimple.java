package logica.ia.buscadores;

import core.EstadoCasilla;
import core.Tablero;
import core.Tablero.TipoTablero;
import logica.ia.evaluacion.Evaluacion;

interface BuscadorSimple {
	ResultadoBusqueda busquedaSimple(final Tablero tablero, final TipoTablero tipoTablero, final EstadoCasilla colorJugador,
			final int profundidad, final Evaluacion funcion);
	
}
