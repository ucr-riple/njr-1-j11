package logica;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import core.EstadoCasilla;
import core.Tablero;
import core.Tablero.TipoTablero;

public class JuegoTorneo extends Thread{
	
	private Controlador controlador = new Controlador();
	private Set<Point> movidasPosibles;
	private FileFilter miFiltro = (new FileFilter() {
		
		@Override
		public String getDescription() {
			return "*.oth";
		}
		
		@Override
		public boolean accept(File arg0) {
			String name = arg0.getName();
			if(arg0.isDirectory() || name.endsWith(".oth")){
				return true;
			}
			return false;
		}
	});
	
	public JuegoTorneo(int[] estrategia, int[] estrategiaRival, TipoTablero tipoTablero, int tamTablero) {
		super();
		this.controlador.inicializar();
		definirTipoTablero(tamTablero, tipoTablero);
		controlador.setEstrategiaJugadores(estrategia, estrategiaRival);
				/*System.out.print("Estrategia1 = ");
				for(int i=0;i<4;i++){System.out.print(estrategia[i]+", ");}
				System.out.println("");
				System.out.print("Estrategia2 = ");
				for(int i=0;i<4;i++){System.out.print(estrategiaRival[i]+", ");}
				System.out.println("");*/
		run();
	}

	@Override
	public void run() {
		if ( controlador.finDelJuego()) {	
			juegoTerminado();
		}if (!controlador.finDelJuego()) {
			movidasPosibles = marcarMovidasPosibles();
			if (movidasPosibles.isEmpty()) {
				controlador.cambiarTurno();
				run();
			} else {
				movidasPosibles = marcarMovidasPosibles();	
				if(!movidasPosibles.isEmpty()){
					hacerMovida();
				} else {
					juegoTerminado();
				}
			}	
		}
	}
	
	private Set<Point> marcarMovidasPosibles() {
		Set<Point> movidas = controlador.marcarMovidasPosibles();
		controlador.desmarcarMovidasPosibles();
		return movidas;
	}
	
	private void hacerMovida() {
		/**///System.out.println("entró a jugar PC"+controlador.jugadorActual().color().toString());
		Point movidaComputador = controlador.evaluarMovida();
		controlador.hacerMovimiento(movidaComputador);
		controlador.cambiarTurno();
		run();
	}
	
	@SuppressWarnings("deprecation")
	private void juegoTerminado() {
		/*if (!controlador.esEmpate()) {
			colorJugadorGanador = controlador.obtenerGanador().color() == EstadoCasilla.BLACK ? "Negras" : "Blancas";
			estrategiaGanador = controlador.obtenerGanador().getEstrategia();
		}*/
		this.stop();
	}
	

	public int[] getGanador() {
		if (!controlador.esEmpate()) {
			/**///String colorJugadorGanador = controlador.obtenerGanador().color() == EstadoCasilla.BLACK ? "Negras" : "Blancas";
			/**///System.out.println("Ganó "+colorJugadorGanador+"!!!");
			return controlador.obtenerGanador().getEstrategia();
		}
		return null;
	}
	
	public void definirTipoTablero(int tamanio, TipoTablero tipoTablero){	
		String nombreFichero = "Octogonal10x10.oth";
		File directorio = new File("setup/boards");
		if ( tipoTablero == TipoTablero.OCTOGONAL ) {
			JFileChooser fileChooser = new JFileChooser();
			nombreFichero = "Octogonal10x10.oth";
			if (tamanio == 12) {
				nombreFichero = "Octogonal12x12.oth";
			} else if (tamanio == 14) {
				nombreFichero = "Octogonal14x14.oth";
			} else if (tamanio == 16) {
				nombreFichero = "Octogonal16x16.oth";
			}
			File fichero = new File(directorio.getPath(),nombreFichero);
			fileChooser.setCurrentDirectory(fichero);		
			fileChooser.setFileFilter(miFiltro);
			try {
				leerArchivo(fichero, tipoTablero);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		}
		
		if ( tipoTablero == TipoTablero.CLASICO || tipoTablero == null) {
			controlador.inicializar(tamanio, tamanio, tipoTablero);
		}
		
		if ( tipoTablero == TipoTablero.PERSONALIZADO ){
			//controlador.inicializar(tamanio, tamanio, tipoTablero);
			nombreFichero = "Personalizado.oth";
			File fichero = new File(directorio.getPath(),nombreFichero);
			try {
				leerArchivo(fichero, TipoTablero.OCTOGONAL);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		}
		//run();
	}
	
	private void leerArchivo(final File fichero, TipoTablero tipoTablero) throws FileNotFoundException {
		BufferedReader reader;
		int largoTablero=14;
		int anchoTablero=14;
		Map<Point, EstadoCasilla> crearTablero = new HashMap<Point, EstadoCasilla>();
		EstadoCasilla colorJugador = EstadoCasilla.BLACK;
		boolean esTamańo=true;
		
		try {
		    int casillasJugablesIniciales=0;
			reader = new BufferedReader(new FileReader(fichero));
			String linea = reader.readLine();
			while (linea != null)
			{
				if (linea.toCharArray()[0] == '-') {
					casillasJugablesIniciales = Integer.parseInt(linea.substring(1, linea.length()));
				} else {
					if(esTamańo){
						String[] dimension = linea.split (",");
						largoTablero = Integer.parseInt(dimension[0]);
						anchoTablero = Integer.parseInt(dimension[1]);
						colorJugador = EstadoCasilla.obtenerEstado(dimension[2]);
						tipoTablero = Tablero.stringToTipoTablero(dimension[3]);
						esTamańo = false;
						
				   } else {
					    String[] campos = linea.split (",");
					    
					    Point punto = new Point(Integer.parseInt(campos[0]),Integer.parseInt(campos[1]));
					    crearTablero.put(punto, EstadoCasilla.obtenerEstado(campos[2]));
				   }
				}
			   linea = reader.readLine();
			}
			controlador.inicializar(crearTablero,colorJugador, tipoTablero);
			Tablero.setCasillasjugablesIniciales(casillasJugablesIniciales);
			Tablero.TABLERO_LARGO = largoTablero;
			Tablero.TABLERO_ANCHO = anchoTablero;
			reader.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
