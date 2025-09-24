package logica;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import javax.swing.JFileChooser;

import logica.ia.buscadores.BuscadorAbstracto;
import logica.ia.evaluacion.Evaluacion;
import logica.ia.evaluacion.EvaluacionEstrategica;
import logica.ia.buscadores.NegaMax;

import core.EstadoCasilla;
import core.Jugador;
import core.Tablero;
import core.Tablero.TipoTablero;

public final class Controlador {
	
	private Tablero tablero;
	public enum Turno {
		NEGRAS,
		BLANCAS;
	}
	private Jugador jugadorBlanco;
	private Jugador jugadorNegro;
	private Turno turno;
	public static final int PROFUNDIDAD_POR_DEFECTO = 1;
	private static int profundidad = PROFUNDIDAD_POR_DEFECTO;
	/* 0: todos bien , 1: uno no puede mover , 2: ninguno puede mover */
	private final short PUEDEMOVER = 0, NOPUEDEMOVER = 2;
	private short puedeMover = PUEDEMOVER;
	private boolean juegoTerminado;
	private static final File directorio = new File("setup/generations");
	public static int esquinasNegro;
	public static int esquinasBlanco;
	
	public Controlador() {
		super();
		inicializar();
		this.turno = Turno.NEGRAS;
	}
	
	public void inicializar() {
		this.tablero = 	new Tablero();
		tablero.inicializar();
		inicializarJugadores();
		juegoTerminado=false;
	}
	
	public void inicializar(int largoTablero, int anchoTablero, TipoTablero tipoTablero) {
		this.tablero = 	new Tablero(largoTablero, anchoTablero, tipoTablero);
		tablero.inicializar();
		inicializarJugadores();
		juegoTerminado=false;
	}
	
	public void inicializar(Map<Point, EstadoCasilla> tablero, EstadoCasilla colorJugador, TipoTablero tipoTablero) {	
		this.tablero = 	new Tablero(tablero, tipoTablero);
		inicializarJugadores();
		if (colorJugador == EstadoCasilla.WHITE) {
			turno = Turno.BLANCAS;
		} 		
		juegoTerminado=false;
	}
	
	private void inicializarJugadores(){		
		int[][] estrategias = leerEstrategias();
		
		int[] estrategiaNegro = {estrategias[0][0],estrategias[0][1],estrategias[0][2],estrategias[0][3]};
		int[] estrategiaBlanco = {estrategias[1][0],estrategias[1][1],estrategias[1][2],estrategias[1][3]};
		
		//int[] estrategiaBlanco = {1000,1,1,1};
		//int[] estrategiaBlanco = {1,1000,1,1};
		//int[] estrategiaBlanco = {1,1,1000,1};
		//int[] estrategiaBlanco = {1,1,1000,1};
			
		/*System.out.print("Estrategia Negro = ");
		System.out.println("["+estrategias[0][0]+","+estrategias[0][1]+","+estrategias[0][2]+","+estrategias[0][3]+"]");
		
		System.out.print("Estrategia Blanco = ");
		System.out.println("["+estrategias[1][0]+","+estrategias[1][1]+","+estrategias[1][2]+","+estrategias[1][3]+"]");*/
		
		jugadorNegro = new Jugador(EstadoCasilla.BLACK, 0, estrategiaNegro);
		jugadorBlanco = new Jugador(EstadoCasilla.WHITE, 0, estrategiaBlanco);
		turno = Turno.NEGRAS;
		puedeMover = PUEDEMOVER;
	}
	
	public void setEstrategiaJugadores(int[] estrategiaNegro, int[] estrategiaBlanco){
		jugadorBlanco.setEstrategia(estrategiaBlanco);
		jugadorNegro.setEstrategia(estrategiaNegro);
	}
	
	public Set<Point> hacerMovimiento(Point movida) {
		Set<Point> puntos;
		if (this.turno == Turno.NEGRAS) {
			puntos = tablero.hacerMovimiento(movida, jugadorNegro.color());
			jugadorNegro.setPuntaje(tablero.contar(EstadoCasilla.BLACK));
		} else {
			puntos = tablero.hacerMovimiento(movida, jugadorBlanco.color());
			jugadorBlanco.setPuntaje(tablero.contar(EstadoCasilla.WHITE));
		}
		
		porcentajeEsquinas(tablero, tablero.obtenerTipoTablero(), jugadorActual().color());
		//porcentajeCasillasCentrales(tablero, jugadorActual().color());
		
		/**/

				 	/*EvaluacionEstrategica funcionEvaluacion = new EvaluacionEstrategica(jugadorActual().getEstrategia());
					double fx = funcionEvaluacion.evaluar(tablero, tablero.obtenerTipoTablero(), jugadorActual().color());
					System.out.println(jugadorActual().color()+" -> "+ fx + " = "
							+jugadorActual().getEstrategia()[0] + "*"+funcionEvaluacion.getX1()+" + "
							+jugadorActual().getEstrategia()[1] + "*"+funcionEvaluacion.getX2()+" - "
							+jugadorActual().getEstrategia()[2] + "*"+funcionEvaluacion.getX3()+" + "
							+jugadorActual().getEstrategia()[3] + "*"+funcionEvaluacion.getX4()
							);*/
		
		
		//System.out.println("Esquinas de Negro = " + esquinasNegro);
		//System.out.println("Esquinas de Blanco = " + esquinasBlanco);
		/*System.out.println("Centrales de Negro = " + centralesNegro);
		System.out.println("Centrales de Blanco = " + centralesBlanco);*/
		return puntos;
	}
	
	/* este método utiliza NegaMax y algoritmos genéticos.*/
	public Point evaluarMovida() {
		BuscadorAbstracto buscador;
		buscador = new NegaMax();
		Evaluacion funcionEvaluacion; // = new EvaluacionEstrategica(estrategia, tablero);
		Point punto;
		if (this.turno == Turno.NEGRAS) {
			funcionEvaluacion = new EvaluacionEstrategica(jugadorNegro.getEstrategia());
			punto = buscador.busquedaSimple(tablero, tablero.obtenerTipoTablero(), jugadorNegro.color(), 
					profundidad, funcionEvaluacion).obtenerPunto();
		} else {
			funcionEvaluacion = new EvaluacionEstrategica(jugadorBlanco.getEstrategia());
			punto = buscador.busquedaSimple(tablero, tablero.obtenerTipoTablero(), jugadorBlanco.color(), 
					profundidad, funcionEvaluacion).obtenerPunto();
		}
		return punto;
	}
	
	public Set<Point> marcarMovidasPosibles() {
		Set<Point> movidas;
		if (this.turno == Turno.NEGRAS) {
			movidas = tablero.obtenerMovidasPosibles(jugadorNegro);
		} else {
			movidas = tablero.obtenerMovidasPosibles(jugadorBlanco);
		}
		tablero.marcarMovidasPosibles(movidas);
		puedeMover = movidas.isEmpty() ? ++puedeMover : PUEDEMOVER;
		return movidas;
	}
	
	public void desmarcarMovidasPosibles() {
		tablero.desmarcarMovidasPosibles();
	}
	
	public void actualizarEstadoCasilla(Point punto, EstadoCasilla estadoCasilla) {
		tablero.actualizarEstadoCasilla(punto, estadoCasilla);
	}

	public int obtenerPuntajeNegro() {
		jugadorNegro.setPuntaje(tablero.contar(EstadoCasilla.BLACK));
		return jugadorNegro.getPuntaje();
	}
	
	public int obtenerPuntajeBlanco() {
		jugadorBlanco.setPuntaje(tablero.contar(EstadoCasilla.WHITE));
		return jugadorBlanco.getPuntaje();
	}
	
	public Jugador obtenerGanador() {
		return jugadorNegro.getPuntaje() < jugadorBlanco.getPuntaje() ? jugadorBlanco : jugadorNegro;
	}
	
	public boolean esEmpate() {
		return jugadorNegro.getPuntaje() == jugadorBlanco.getPuntaje();
	}
	
	public boolean finDelJuego() {	
		if(tablero.estaLleno() || verificarPuntajeEnCero() || puedeMover == NOPUEDEMOVER){
			juegoTerminado = true;
		}
		return juegoTerminado;
	}
	
	private boolean verificarPuntajeEnCero() {
		return obtenerPuntajeNegro() == 0 || obtenerPuntajeBlanco() == 0;
	}
	
	public void cambiarTurno() {
		if (turno ==  Turno.BLANCAS) {
			turno = Turno.NEGRAS;
		} else {
			turno = Turno.BLANCAS;
		}
	}
	
	public Jugador jugadorActual() {
		if (turno ==  Turno.BLANCAS) {
			return jugadorBlanco;
		} else {
			return jugadorNegro;
		}
	}

	public Tablero obtenerTableroLogica(){
		return tablero;
	}
	
	private static class soporteControlador {
		private static final Controlador INSTANCE = new Controlador();
	}
	
	public static Controlador obtenerInstancia() {
		return soporteControlador.INSTANCE;
	}
	
	private static int[][] leerEstrategias() {
	    int[][] estrategias = new int[2][4];
		JFileChooser fileChooser = new JFileChooser();
		String nombreFichero = "dosMejores.txt";
		File fichero = new File(directorio.getPath(),nombreFichero);
		fileChooser.setCurrentDirectory(fichero);		
		try {
			BufferedReader readerPenultimaLinea = new BufferedReader(new FileReader(fichero));
			BufferedReader reader = new BufferedReader(new FileReader(fichero));
			
			String linea = reader.readLine();
			String lineaFinal = null;
			String penultimaLinea = null;// = readerPenultimaLinea.readLine();
			
			while ((linea = reader.readLine())  != null){
				penultimaLinea = readerPenultimaLinea.readLine();	
				lineaFinal = linea;
			}
			String[] dimension1 = penultimaLinea.split(",");
			String[] dimension2 = lineaFinal.split(",");
			for (int i=0 ; i<dimension1.length ; i++) {			
				estrategias[0][i]=Integer.parseInt(dimension1[i]);
				estrategias[1][i]=Integer.parseInt(dimension2[i]);
			}
			reader.close();
			readerPenultimaLinea.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return estrategias;
	}
		
	/************************************************************************************************************************/
		
	private double porcentajeEsquinas(Tablero tablero, TipoTablero tipoTablero, EstadoCasilla colorJugador) {
		
		/*esquinasNegro=0;
		esquinasBlanco=0;*/
		
		int totalEsquinas;
		int esquinasJugador=0;
		int esquinasRival=0;
		EstadoCasilla colorRival = colorJugador == EstadoCasilla.BLACK ? EstadoCasilla.WHITE : EstadoCasilla.BLACK;
		List<Point> ubicacionEsquinas;// = detectarEsquinas(TipoTablero.CLASICO);		
		totalEsquinas = 4;
		if (tipoTablero  == TipoTablero.OCTOGONAL) {
			totalEsquinas = 8;
		} else if (tipoTablero  == TipoTablero.PERSONALIZADO){ //si es personalizado debe detectar número de esquinas
			totalEsquinas = detectarEsquinas(tipoTablero).size();
		}
		ubicacionEsquinas = detectarEsquinas(tipoTablero);
		ListIterator<Point> iterador = ubicacionEsquinas.listIterator();
		while( iterador.hasNext() ) {
		    Point punto = (Point) iterador.next();
			if (tablero.obtenerTablero().get(punto) == colorJugador){
				esquinasJugador++;
			}else if (tablero.obtenerTablero().get(punto) == colorRival) {
				esquinasRival++;
			}
		}
		if( colorJugador == EstadoCasilla.BLACK ){
			esquinasNegro = esquinasJugador;
			esquinasBlanco = esquinasRival;
		} else {
			esquinasNegro = esquinasRival;
			esquinasBlanco = esquinasJugador;
		}
		return (double)(esquinasJugador-esquinasRival)/(double)totalEsquinas;
	}

	private List<Point> detectarEsquinas(TipoTablero tipoTablero) {
		List<Point> esquinas = null;
		if (tipoTablero == TipoTablero.CLASICO) {
			esquinas = new ArrayList<Point>(4);
			esquinas.add(new Point(1, 1));
			esquinas.add(new Point(Tablero.TABLERO_LARGO-2, 1));
			esquinas.add(new Point(1, Tablero.TABLERO_ANCHO-2));
			esquinas.add(new Point(Tablero.TABLERO_LARGO-2, Tablero.TABLERO_ANCHO-2));
		} if (tipoTablero == TipoTablero.OCTOGONAL) {
			esquinas = new ArrayList<Point>(8);
			esquinas.add(new Point( 0, (int)(Tablero.TABLERO_LARGO/2)-1) );
			esquinas.add(new Point( 0, (int)(Tablero.TABLERO_LARGO/2))   );
			esquinas.add(new Point( (int)((Tablero.TABLERO_ANCHO/2)-1), 0) );
			esquinas.add(new Point( (int)((Tablero.TABLERO_ANCHO/2)), 0) );
			esquinas.add(new Point( (int)((Tablero.TABLERO_ANCHO/2)-1), Tablero.TABLERO_LARGO-1) );
			esquinas.add(new Point( (int)((Tablero.TABLERO_ANCHO/2)), Tablero.TABLERO_LARGO-1) );
			esquinas.add(new Point( Tablero.TABLERO_LARGO-1, (int)(Tablero.TABLERO_LARGO/2)-1) );
			esquinas.add(new Point( Tablero.TABLERO_LARGO-1, (int)(Tablero.TABLERO_LARGO/2))   );
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
	
	
	
	
	/************************************************************************************************************************/
	
	
	
	
	
	
		


}
