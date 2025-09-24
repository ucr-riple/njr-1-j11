package core;

import java.awt.Point;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import logica.ExploradorMovimientos;

public class Tablero {
	
	public static int TABLERO_LARGO = 10;
	public static int TABLERO_ANCHO = 10;
	
	private Map<Point, EstadoCasilla> tablero;
	private static int casillasjugablesIniciales;
	
	public enum TipoTablero {
		CLASICO,
		OCTOGONAL,
		PERSONALIZADO;
	}
	private TipoTablero tipoTablero;
	
	public Tablero() {
		tablero = new HashMap<Point, EstadoCasilla>(TABLERO_LARGO * TABLERO_ANCHO);
		this.tipoTablero = TipoTablero.CLASICO;
		setCasillasjugablesIniciales(0);
		inicializar();
	}
	
	public Tablero(int largo, int ancho, TipoTablero tipoTablero) {
		TABLERO_LARGO = largo;
		TABLERO_ANCHO = ancho;
		this.tipoTablero = tipoTablero;
		setCasillasjugablesIniciales(0);
		tablero = new HashMap<Point, EstadoCasilla>(TABLERO_LARGO * TABLERO_ANCHO);
		inicializar();
	}
	
	public Tablero(Map<Point, EstadoCasilla> tablero, TipoTablero tipoTablero) {
		super();
		this.tipoTablero = tipoTablero;
		setCasillasjugablesIniciales(0);
		if (tipoTablero == null) {
			this.tipoTablero = TipoTablero.PERSONALIZADO;
		}
		this.tablero = new HashMap<Point, EstadoCasilla>(tablero.size());
		for(Point point : tablero.keySet()) {
			this.tablero.put(new Point(point), tablero.get(point));
			if(tablero.get(point) == EstadoCasilla.EMPTY){
				setCasillasjugablesIniciales(getCasillasjugablesIniciales() + 1);
			}
		}
		
	}

	public void inicializar() {
		setCasillasjugablesIniciales(0);
		Point point = new Point();
		for (point.x = 0; point.x < TABLERO_LARGO; point.x++) {
			for (point.y = 0; point.y < TABLERO_ANCHO; point.y++) {
				if(point.x == 0 || point.x == TABLERO_LARGO-1 || point.y == 0 || point.y == TABLERO_ANCHO-1){
					tablero.put(new Point(point), EstadoCasilla.WALL);
				}
				else {
					tablero.put(new Point(point), EstadoCasilla.EMPTY);
					setCasillasjugablesIniciales(getCasillasjugablesIniciales() + 1);
				}
			}
		}
		tablero.put(new Point((int)(Tablero.TABLERO_LARGO/2)-1, (int)(Tablero.TABLERO_LARGO/2)-1), EstadoCasilla.WHITE);
		tablero.put(new Point((int)(Tablero.TABLERO_LARGO/2)-1, (int)(Tablero.TABLERO_LARGO/2)), EstadoCasilla.BLACK);
		tablero.put(new Point((int)(Tablero.TABLERO_LARGO/2), (int)(Tablero.TABLERO_LARGO/2)-1), EstadoCasilla.BLACK);
		tablero.put(new Point((int)(Tablero.TABLERO_LARGO/2), (int)(Tablero.TABLERO_LARGO/2)), EstadoCasilla.WHITE);
	}
	
	public Map<Point, EstadoCasilla> obtenerTablero(){
		return tablero;
	}
	
	public EstadoCasilla obtenerEstadoCasilla(Point point){
		return tablero.get(point);
	}
	
	public Set<Point> obtenerCasillas(EstadoCasilla estado){
		Set<Point> points = new HashSet<Point>();
		for (Point point : tablero.keySet()){
			if (tablero.get(point) == estado){
				points.add(point);
			}
		}
		return points;
	}
	
	public boolean estaLleno(){
		for (Point point : tablero.keySet()){
			if (tablero.get(point) == EstadoCasilla.EMPTY){
				return false;
			}
		}
		return true;
	}
	
	public int contar(EstadoCasilla estado) {
		int contar = 0;
		for (Point point : tablero.keySet()) {
			if (tablero.get(point) == estado) {
				contar++;
			}
		}
		return contar;
	}
	
	public Set<Point> obtenerMovidasPosibles(Jugador jugador) {
		return ExploradorMovimientos.explorar(this, jugador.color());
	}
	
	public void marcarMovidasPosibles(Set<Point> movidasPosibles) {
		for (Point point : movidasPosibles) {
			tablero.put(point, EstadoCasilla.PSSBL);
		}
	}

	public void actualizarEstadoCasilla(Point punto, EstadoCasilla estadoCasilla) {
		tablero.put(punto, estadoCasilla);
	}
	
	public void desmarcarMovidasPosibles() {
		for (Point point : tablero.keySet()) {
			if (tablero.get(point) == EstadoCasilla.PSSBL) {
				tablero.put(point, EstadoCasilla.EMPTY);
			}
		}
	}
	
	public void marcarEstado(Set<Point> points, EstadoCasilla estado) {
		for (Point point : points) {
			tablero.put(point, estado);
		}
	}
	
	public Set<Point> hacerMovimiento(Point movida, EstadoCasilla estado) {
		tablero.put(movida, estado);
		Set<Point> casillasCambiadas = ExploradorMovimientos.casillasParaLlenar(this, movida);
		marcarEstado(casillasCambiadas, estado);
		casillasCambiadas.add(movida);
		return casillasCambiadas;
	}
	
	public TipoTablero obtenerTipoTablero(){
		return tipoTablero;
	}

	@Override
	public Tablero clone() {
		return new Tablero(this.tablero, null);
	}
	
	public static final TipoTablero stringToTipoTablero(String stringtipoTablero){
		TipoTablero temp = TipoTablero.PERSONALIZADO;
		if (stringtipoTablero.equals("CLASICO")) {
			temp = TipoTablero.CLASICO;
		}
		if (stringtipoTablero.equals("OCTOGONAL")) {
			temp = TipoTablero.OCTOGONAL;
		}if (stringtipoTablero.equals("PERSONALIZADO")) {
			temp = TipoTablero.PERSONALIZADO;
		}
		return temp;
	}

	public static int getCasillasjugablesIniciales() {
		return casillasjugablesIniciales;
	}

	public static void setCasillasjugablesIniciales(
			int casillasjugablesIniciales) {
		Tablero.casillasjugablesIniciales = casillasjugablesIniciales;
	}

}
