package othello;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.JFileChooser;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.filechooser.FileFilter;

import core.EstadoCasilla;
import core.Tablero;
import core.Tablero.TipoTablero;

import logica.Controlador;

import ui.ComponenteImagen;
import ui.FabricaImagenCasilla.TipoCasilla;
import ui.TableroUI;

import utilidades.Transform;

public class UIJuego implements Runnable{

	private Controlador controlador = Controlador.obtenerInstancia();
	private TableroUI tableroUI;
	private Set<Point> movidasPosibles;
	private boolean estaModificandoTablero;
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
	private static final File directorio = new File("setup/saves");

	
	
	public UIJuego() {
		super();
		this.controlador.inicializar();
		controlador.inicializar(10, 10, TipoTablero.CLASICO);
		iniciarTableroUI();
	}
	
	private void iniciarTableroUI(){		
		this.estaModificandoTablero = false;
		TipoTablero tipoTablero = controlador.obtenerTableroLogica().obtenerTipoTablero();
		tableroUI = new TableroUI(Tablero.TABLERO_LARGO,Tablero.TABLERO_ANCHO, controlador.obtenerTableroLogica(), tipoTablero);
		tableroUI.setVisible(true);
		
		tableroUI.obtenerItemNuevoJuego().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
					definirTipoTablero(obtenerTamTableroElegido(), obtenerTipoTableroElegido());
			}
		});
		
		tableroUI.obtenerItemAbrirJuego().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {				
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(directorio);	
				fileChooser.setFileFilter(miFiltro);
				int seleccion = fileChooser.showOpenDialog(tableroUI);
				if (seleccion == JFileChooser.APPROVE_OPTION)
				{
				   File fichero = fileChooser.getSelectedFile();
				   try {
					   tableroUI.dispose();
					   leerArchivo(fichero, null);
					   tableroUI = new TableroUI(Tablero.TABLERO_LARGO,Tablero.TABLERO_ANCHO, controlador.obtenerTableroLogica(), null);
					   iniciarTableroUI();
					   run();
				   } catch (FileNotFoundException e1) {
					   e1.printStackTrace();
				   }
				}
			}
		});
		
		tableroUI.obtenerItemGuardarJuego().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {				
				JFileChooser fileChooser = new JFileChooser();
				directorio.mkdirs();
				fileChooser.setCurrentDirectory(directorio);		
				fileChooser.setFileFilter(miFiltro);
				int seleccion = fileChooser.showSaveDialog(tableroUI);
				if (seleccion == JFileChooser.APPROVE_OPTION)
				{
				   String path = fileChooser.getSelectedFile().getAbsolutePath();
				   String extension =  miFiltro.getDescription().substring(miFiltro.getDescription().length()-4);
				   if (!path.substring(path.length()-4).equals(extension)){
					   path = path + extension;
				   }
				   File fichero = new File(path);
				   escribirArchivo(fichero);			   
				}
			}
		});

		for (final JRadioButtonMenuItem tipoTableroButton : tableroUI.obtenerTipoTableroButton()) {
			tipoTableroButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					definirTipoTablero(obtenerTamTableroElegido(), obtenerTipoTableroElegido());
				}
			});
		}
		
		for (final JRadioButtonMenuItem tamTableroButton : tableroUI.obtenerTamTableroButton()) {
			tamTableroButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					definirTipoTablero(obtenerTamTableroElegido(), obtenerTipoTableroElegido());
				}
			});
		}		
	}
	
	@Override
	public void run() {

		if ( controlador.finDelJuego()) {	
			juegoTerminado();
		}
		if (!controlador.finDelJuego() && estaModificandoTablero) {
			tableroUI.obtenerBoton().addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					estaModificandoTablero = false;
					//guardarConfiguraciónEnUnArchivo (opcional)  tableroUI.obtener.item -> Archivo -> Guardar addActionListener
					tableroUI.mostrarBarraEstado();
					run();
				}
			});
		ActualizarListeners();
		}
		
		if (!controlador.finDelJuego() && !estaModificandoTablero) {
			movidasPosibles = marcarMovidasPosibles();
			if (movidasPosibles.isEmpty()) {
				pasar();
				run();
			}
			
			if (controlador.jugadorActual().color() != tableroUI.obtenerJugadorSeleccionado().color()
					&& tableroUI.obtenerOponentes() == TableroUI.HUM_ROB
					&& !controlador.finDelJuego()) {		
				tableroUI.desmarcarMovidasPosibles(movidasPosibles);
				Point movidaComputador = controlador.evaluarMovida();
				hacerMovida(movidaComputador);
				despuesDeMovida();
			}
				
			if (tableroUI.obtenerOponentes() == TableroUI.ROB_ROB && !controlador.finDelJuego()) {		
				tableroUI.desmarcarMovidasPosibles(movidasPosibles);
				Point movidaComputador = controlador.evaluarMovida();
				hacerMovida(movidaComputador);
				//System.out.println("entró a jugar PC"+controlador.jugadorActual().color().toString());
				despuesDeMovida();
			}
		}
	}
	
	private Set<Point> marcarMovidasPosibles() {
		Set<Point> movidas = controlador.marcarMovidasPosibles();
		controlador.desmarcarMovidasPosibles();
		if (!movidas.isEmpty()){
			TipoCasilla color = controlador.jugadorActual().color() == EstadoCasilla.WHITE
					? TipoCasilla.PSSBLWHT : TipoCasilla.PSSBLBLK;
			tableroUI.marcarMovidasPosibles(movidas, color);
		}
		ActualizarListeners();	
		return movidas;
	}

	private void ActualizarListeners() {
		for (ComponenteImagen compImg : tableroUI.obtenerCasillas()){
			if (compImg.getMouseListeners().length != 0){
				continue;
			}
			compImg.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent evt){
					if(estaModificandoTablero){
						aSucedidoClickEditar(evt.getComponent(), evt);
					}
					else{
						aSucedidoClick(evt.getComponent());
					}
				}
			});
		}
		
	}

	private void aSucedidoClick(Component compImg) {
		int index = tableroUI.obtenerCasillas().indexOf(compImg);
		Point movidaSeleccionada = Transform.indexToPoint(index);
		if (movidasPosibles.contains(movidaSeleccionada)) {
			tableroUI.desmarcarMovidasPosibles(movidasPosibles);
			controlador.actualizarEstadoCasilla(movidaSeleccionada, controlador.jugadorActual().color());
			hacerMovida(movidaSeleccionada);
			despuesDeMovida();
		}
		
	}
	
	private void aSucedidoClickEditar(Component compImg, MouseEvent evt){
		int index = tableroUI.obtenerCasillas().indexOf(compImg);
		Point movidaSeleccionada = Transform.indexToPoint(index);
		if ((evt.getModifiers() & InputEvent.BUTTON1_MASK) == InputEvent.BUTTON1_MASK) {
			tableroUI.definirCasilla(movidaSeleccionada, TipoCasilla.WALL);
			controlador.actualizarEstadoCasilla(movidaSeleccionada, EstadoCasilla.WALL);
		} else {
			tableroUI.definirCasilla(movidaSeleccionada, TipoCasilla.EMPTY);
			controlador.actualizarEstadoCasilla(movidaSeleccionada, EstadoCasilla.EMPTY);
		}
		run();
	}
	
	private void hacerMovida(Point movida) {
		TipoCasilla color = controlador.jugadorActual().color() == EstadoCasilla.WHITE
				? TipoCasilla.WHITE : TipoCasilla.BLACK;
		Set<Point> casillasPorCambiar = controlador.hacerMovimiento(movida);	
		tableroUI.rellenar(casillasPorCambiar, color);
	}
	
	private void despuesDeMovida() {
		actualizarEstadisticas();
		cambiarTurno();
		run();
	}
	
	private void pasar() {
		turnoPerdido();
		actualizarEstadisticas();
	}

	private void turnoPerdido() {
		if(tableroUI.obtenerOponentes() != TableroUI.ROB_ROB){
			tableroUI.notificarTurnoPerdido(controlador.jugadorActual());
		}
		cambiarTurno();
	}

	private void cambiarTurno() {
		controlador.cambiarTurno();
		tableroUI.actualizarTurno(controlador.jugadorActual().toString());
	}

	private void actualizarEstadisticas() {
		tableroUI.actualizarPuntaje(controlador.obtenerPuntajeNegro(), controlador.obtenerPuntajeBlanco());
	}

	private void juegoTerminado() {
		actualizarEstadisticas();
		if (controlador.esEmpate()) {
			tableroUI.declararEmpate();
		} else {
			String ganador = controlador.obtenerGanador().color() == EstadoCasilla.BLACK ? "Negras" : "Blancas";
			tableroUI.declararGanador(ganador);
			tableroUI.notificarVictoria(controlador.obtenerGanador());
		}
	}
	
	public TipoTablero obtenerTipoTableroElegido(){
		if (tableroUI.obtenerTipoTableroButton()[0].isSelected()) {
			return TipoTablero.CLASICO;
		} else if (tableroUI.obtenerTipoTableroButton()[1].isSelected()) {
			return TipoTablero.OCTOGONAL;
		} else {
			return TipoTablero.PERSONALIZADO;
		}
	}
	
	public int obtenerTamTableroElegido(){
		if (tableroUI.obtenerTamTableroButton()[0].isSelected()) {
			return 10;
		} else if (tableroUI.obtenerTamTableroButton()[1].isSelected()) {
			return 12;
		} else if (tableroUI.obtenerTamTableroButton()[2].isSelected()) {
			return 14;
		}else {
			return 16;
		}
	}
	
	public void definirTipoTablero(int tamanio, TipoTablero tipoTablero){
		tableroUI.dispose();
		
		if ( tipoTablero == TipoTablero.OCTOGONAL ) {
			JFileChooser fileChooser = new JFileChooser();
			String nombreFichero = "Octogonal10x10.oth";
			if (tamanio == 12) {
				nombreFichero = "Octogonal12x12.oth";
			} else if (tamanio == 14) {
				nombreFichero = "Octogonal14x14.oth";
			} else if (tamanio == 16) {
				nombreFichero = "Octogonal16x16.oth";
			}
			File directorio = new File("setup/boards");
			File fichero = new File(directorio.getPath(),nombreFichero);
			fileChooser.setCurrentDirectory(fichero);		
			fileChooser.setFileFilter(miFiltro);
			try {
				leerArchivo(fichero, tipoTablero);
				tableroUI = new TableroUI(Tablero.TABLERO_LARGO, Tablero.TABLERO_ANCHO, controlador.obtenerTableroLogica(), tipoTablero);
				iniciarTableroUI();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		}
		
		if ( tipoTablero == TipoTablero.CLASICO || tipoTablero == null) {
			controlador.inicializar(tamanio, tamanio, tipoTablero);
			tableroUI = new TableroUI(Tablero.TABLERO_LARGO, Tablero.TABLERO_ANCHO, controlador.obtenerTableroLogica(), tipoTablero);
			iniciarTableroUI();
		}
		
		if ( tipoTablero == TipoTablero.PERSONALIZADO ){
			controlador.inicializar(tamanio, tamanio, tipoTablero);
			tableroUI = new TableroUI(Tablero.TABLERO_LARGO, Tablero.TABLERO_ANCHO, controlador.obtenerTableroLogica(), tipoTablero);
			iniciarTableroUI();
			estaModificandoTablero = true;
			tableroUI.mostrarBarraBoton();	
		}
		
		run();
	}
	
	private void escribirArchivo(File fichero) {
		PrintWriter writer;
		try {
			int casillasJugablesIniciales=0;
			writer = new PrintWriter(fichero);
			Map<Point, EstadoCasilla> tableroActual = controlador.obtenerTableroLogica().obtenerTablero();
			writer.println(TableroUI.largoTablero + "," + TableroUI.anchoTablero + "," + controlador.jugadorActual().color()
					+ "," + obtenerTipoTableroElegido());
			for(Point point : tableroActual.keySet()) {
				writer.println(point.x + "," + point.y + "," + tableroActual.get(point).toString());
				if(tableroActual.get(point) != EstadoCasilla.WALL){
					casillasJugablesIniciales++;
				}
			}
			writer.println("-"+casillasJugablesIniciales);
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
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
