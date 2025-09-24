package logica.ia.evaluacion;

import core.EstadoCasilla;
import core.Tablero;
import core.Tablero.TipoTablero;

import java.awt.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import logica.ExploradorMovimientos;

public class EvaluacionEstrategica implements Evaluacion{
	private int A,B,C,D;
	private double x1;
	private double x2;
	private double x3;
	private double x4;

	public EvaluacionEstrategica(int[] estrategia) {
		A = estrategia[0];
		B = estrategia[1];
		C = estrategia[2];
		D = estrategia[3];
	}

	@Override
	public double evaluar(Tablero tablero, TipoTablero tipoTablero, EstadoCasilla colorJugador) {
		x1 = porcentajePuntos(tablero, colorJugador);
		x2 = porcentajeEsquinas(tablero, tipoTablero, colorJugador);
		x3 = porcentajeJugadasPosiblesRival(tablero, colorJugador);
		x4 = porcentajeCasillasCentrales(tablero, colorJugador);
		return ((double)(A)*x1)+((double)(B)*x2)+((double)(C)*x3)+((double)(D)*x4);
		
	}
	
	private double porcentajePuntos(Tablero tablero, EstadoCasilla colorJugador) {
		EstadoCasilla colorRival = colorJugador == EstadoCasilla.BLACK ? EstadoCasilla.WHITE : EstadoCasilla.BLACK;
		int puntosJugador=0;
		int puntosRival=0;
		
		puntosJugador = tablero.contar(colorJugador);
		puntosRival = tablero.contar(colorRival);
		int totalCasillasJugables = Tablero.getCasillasjugablesIniciales();
		
		return (double)(puntosJugador - puntosRival)/(double)totalCasillasJugables;
	}
	
	private double porcentajeEsquinas(Tablero tablero, TipoTablero tipoTablero, EstadoCasilla colorJugador) {	
		int totalEsquinas;
		int esquinasJugador=0;
		int esquinasRival=0;
		EstadoCasilla colorRival = colorJugador == EstadoCasilla.BLACK ? EstadoCasilla.WHITE : EstadoCasilla.BLACK;
		List<Point> ubicacionEsquinas = detectarEsquinas(tablero, tipoTablero);
		
		totalEsquinas = 4;
		if (tipoTablero  == TipoTablero.OCTOGONAL) {
			totalEsquinas = ubicacionEsquinas.size();
		} else if (tipoTablero  == TipoTablero.PERSONALIZADO){
			totalEsquinas = ubicacionEsquinas.size();
		}
		
		ListIterator<Point> iterador = ubicacionEsquinas.listIterator();
		while( iterador.hasNext() ) {
		       Point punto = (Point) iterador.next();
				if (tablero.obtenerTablero().get(punto) == colorJugador){
					esquinasJugador++;
				}else if (tablero.obtenerTablero().get(punto) == colorRival) {
					esquinasRival++;
				}
		       
		}
		/*System.out.println(colorJugador +" =" + esquinasJugador);
		System.out.println(colorRival +" =" + esquinasRival);*/
		return (double)(esquinasJugador-esquinasRival)/(double)totalEsquinas;
	}
	

	private double porcentajeJugadasPosiblesRival(Tablero tablero, EstadoCasilla colorJugador) {
		EstadoCasilla colorRival = colorJugador == EstadoCasilla.BLACK ? EstadoCasilla.WHITE : EstadoCasilla.BLACK;
		int movidasPosiblesRival=0;
		
		Set<Point> movidasPosibles = ExploradorMovimientos.explorar(tablero, colorJugador);
		if (movidasPosibles.isEmpty()) { /* se pierde turno - revisar siguiente jugador */
			movidasPosibles = ExploradorMovimientos.explorar(tablero, colorRival);
			if (movidasPosibles.isEmpty()) { // fin del juego
					return 0;
			} else {
				movidasPosiblesRival = movidasPosibles.size();
			}
		} else { /* revisar el puntaje después de una movida */
			for (Point siguienteMovimientoPosible : movidasPosibles) {
				Tablero subTablero = tablero.clone();
				if (siguienteMovimientoPosible != null) {
					subTablero.hacerMovimiento(siguienteMovimientoPosible, colorJugador);
					movidasPosibles = ExploradorMovimientos.explorar(subTablero, colorRival);
					if (movidasPosibles.isEmpty()) { // fin del juego
						return 0;
					} else if(movidasPosibles.size() > movidasPosiblesRival){/*toma el peor de los casos (la mayor cantidad de libertades para el oponente)*/
						movidasPosiblesRival = movidasPosibles.size();
					}
				}
				
			}
		}
		return -(double)movidasPosiblesRival/(double)Tablero.getCasillasjugablesIniciales();  /*  /(double)totalCasillasJugables */
	}

	private double porcentajeCasillasCentrales(Tablero tablero, EstadoCasilla colorJugador) {
		int totalCasillasCentrales = 4;
		int casillasCentralesJugador=0;
		int casillasCentralesRival=0;
		EstadoCasilla colorRival = colorJugador == EstadoCasilla.BLACK ? EstadoCasilla.WHITE : EstadoCasilla.BLACK;
		List<Point> centrales = new ArrayList<Point>(4);
		centrales.add(new Point((int)(Tablero.TABLERO_LARGO/2)-1, (int)(Tablero.TABLERO_LARGO/2)-1));
		centrales.add(new Point((int)(Tablero.TABLERO_LARGO/2)-1, (int)(Tablero.TABLERO_LARGO/2)));
		centrales.add(new Point((int)(Tablero.TABLERO_LARGO/2), (int)(Tablero.TABLERO_LARGO/2)-1));
		centrales.add(new Point((int)(Tablero.TABLERO_LARGO/2), (int)(Tablero.TABLERO_LARGO/2)));
		ListIterator<Point> iterador = centrales.listIterator();
		while( iterador.hasNext() ) {
		       Point punto = (Point) iterador.next();
				if (tablero.obtenerTablero().get(punto) == colorJugador){
					casillasCentralesJugador++;
				}else if (tablero.obtenerTablero().get(punto) == colorRival) {
					casillasCentralesRival++;
				}
		}
		
		/*System.out.println(colorJugador +" = " + casillasCentralesJugador);
		System.out.println(colorRival +" = " + casillasCentralesRival);
		System.out.println((double)(casillasCentralesJugador-casillasCentralesRival)/(double)totalCasillasCentrales);*/
		return (double)(casillasCentralesJugador-casillasCentralesRival)/(double)totalCasillasCentrales;
	}
	

	private List<Point> detectarEsquinas(Tablero tablero, TipoTablero tipoTablero) {
		List<Point> esquinas = null;
		if (tipoTablero == TipoTablero.CLASICO) {
			esquinas = new ArrayList<Point>(4);
			esquinas.add(new Point(1, 1));
			esquinas.add(new Point(Tablero.TABLERO_LARGO-2, 1));
			esquinas.add(new Point(1, Tablero.TABLERO_ANCHO-2));
			esquinas.add(new Point(Tablero.TABLERO_LARGO-2, Tablero.TABLERO_ANCHO-2));
		} if (tipoTablero == TipoTablero.OCTOGONAL) {
			/*esquinas = new ArrayList<Point>(8);
			esquinas.add(new Point( 0, (int)(Tablero.TABLERO_LARGO/2)-1) );
			esquinas.add(new Point( 0, (int)(Tablero.TABLERO_LARGO/2))   );
			esquinas.add(new Point( (int)((Tablero.TABLERO_ANCHO/2)-1), 0) );
			esquinas.add(new Point( (int)((Tablero.TABLERO_ANCHO/2)), 0) );
			esquinas.add(new Point( (int)((Tablero.TABLERO_ANCHO/2)-1), Tablero.TABLERO_LARGO-1) );
			esquinas.add(new Point( (int)((Tablero.TABLERO_ANCHO/2)), Tablero.TABLERO_LARGO-1) );
			esquinas.add(new Point( Tablero.TABLERO_LARGO-1, (int)(Tablero.TABLERO_LARGO/2)-1) );
			esquinas.add(new Point( Tablero.TABLERO_LARGO-1, (int)(Tablero.TABLERO_LARGO/2))   );*/
			esquinas = new ArrayList<Point>();
			Map<Point, EstadoCasilla> tableroMap = tablero.obtenerTablero();
			for (int x = 0; x < Tablero.TABLERO_LARGO; x++) {
				for (int y = 0; y < Tablero.TABLERO_ANCHO; y++) {
					if(tableroMap.get(new Point(x,y)) != EstadoCasilla.WALL){
						if(esEsquina(new Point(x,y),tablero)){
							esquinas.add(new Point(x,y));
						}
					}
				}
			}
		} if (tipoTablero == TipoTablero.PERSONALIZADO) { //si es personalizado debe detectar número de esquinas
			esquinas = new ArrayList<Point>();
			Map<Point, EstadoCasilla> tableroMap = tablero.obtenerTablero();
			for (int x = 0; x < Tablero.TABLERO_LARGO; x++) {
				for (int y = 0; y < Tablero.TABLERO_ANCHO; y++) {
					if(tableroMap.get(new Point(x,y)) != EstadoCasilla.WALL){
						if(esEsquina(new Point(x,y),tablero)){
							esquinas.add(new Point(x,y));
						}
					}
				}
			}
		}
		return esquinas;
	}
	
	private boolean esEsquina(Point semilla, Tablero tablero) {
		boolean esquina=false;
		int x = semilla.x;
		int y = semilla.y;
		if( !puntoEsValido(new Point(x-1,y), tablero ) && !puntoEsValido(new Point(x,y-1), tablero ) && !puntoEsValido(new Point(x-1,y-1), tablero )){
			esquina = true;
		}
		if( !puntoEsValido(new Point(x-1,y), tablero ) && !puntoEsValido(new Point(x-1,y+1), tablero ) && !puntoEsValido(new Point(x,y+1), tablero )){
			esquina = true;
		}
		if( !puntoEsValido(new Point(x,y+1), tablero ) && !puntoEsValido(new Point(x+1,y+1), tablero ) && !puntoEsValido(new Point(x+1,y), tablero )){
			esquina = true;
		}
		if( !puntoEsValido(new Point(x,y-1), tablero ) && !puntoEsValido(new Point(x+1,y-1), tablero ) && !puntoEsValido(new Point(x+1,y), tablero )){
			esquina = true;
		}
		return esquina;
	}
	
	private boolean puntoEsValido(Point punto, Tablero tablero) {
		return punto.x >= 0 && punto.x < Tablero.TABLERO_LARGO 
				&& punto.y >= 0 && punto.y < Tablero.TABLERO_ANCHO
				&& tablero.obtenerEstadoCasilla(punto) != EstadoCasilla.WALL;
	}

	public double getX1(){
		return x1;
	}
	public double getX2(){
		return x2;
	}
	public double getX3(){
		return x3;
	}
	public double getX4(){
		return x4;
	}

}
