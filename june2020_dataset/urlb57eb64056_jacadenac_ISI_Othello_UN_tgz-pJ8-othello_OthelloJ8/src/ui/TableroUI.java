package ui;

import core.EstadoCasilla;
import core.Jugador;
import core.Tablero;
import core.Tablero.TipoTablero;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;

import ui.FabricaImagenCasilla.TipoCasilla;
import utilidades.Transform;
import othello.Othello;


public final class TableroUI extends JFrame {
	private static final long serialVersionUID = 1L;
	public static final int HUM_HUM = 0;
	public static final int HUM_ROB = 1;
	public static final int ROB_ROB = 2;
	
	public static int largoTablero = Tablero.TABLERO_LARGO;
	public static int anchoTablero = Tablero.TABLERO_LARGO;

	private final Image LOGO = new ImageIcon(getClass().getResource("/ui/imagenes/logo.jpg")).getImage();
	private static int oponentes = HUM_HUM;
	private static Jugador humano = new Jugador(EstadoCasilla.BLACK);
	private List<ComponenteImagen> casillas;
	private JPanel tablero;
	private JLabel estadisticasBlanco;
	private JLabel estadisticasNegro;
	private JLabel mostrarTurno;
	private JMenuItem juegoNuevo;
	private JMenuItem guardarJuego;
	private JMenuItem abrirJuego;
	private JRadioButtonMenuItem[] tipoTableroButton;
	private JRadioButtonMenuItem[] tamTableroButton;
	private Tablero tableroLogica;
	private JPanel barraEstado;
	private JPanel barraBoton;
	private Container pane = this.getContentPane();
	public JButton boton;
	TipoTablero tipoTablero;

	
	public TableroUI() throws HeadlessException {
		casillas = new ArrayList<ComponenteImagen>(largoTablero*anchoTablero);
		tipoTablero=TipoTablero.CLASICO;
		iniciarComponentes();
		this.pack();
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle(Othello.class.getSimpleName());
		this.setIconImage(LOGO);
		this.setResizable(false);
	}
	
	public TableroUI(int largo, int ancho, Tablero tableroLogica, TipoTablero tipoTablero) throws HeadlessException {
		largoTablero = largo;
		anchoTablero = ancho;
		this.tableroLogica = tableroLogica;
		this.tipoTablero=tipoTablero;
		casillas = new ArrayList<ComponenteImagen>(tableroLogica.obtenerTablero().size());
		iniciarComponentes();
		this.pack();
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle(Othello.class.getSimpleName());
		this.setIconImage(LOGO);
		this.setResizable(false);
	}
	
	
	private void iniciarComponentes(){
		pane.setLayout(new GridBagLayout());
		GridBagConstraints constrains = new GridBagConstraints();
		ButtonGroup buttongroup = new ButtonGroup();
		JRadioButtonMenuItem radiobutton;
		
		/* Dibujar la barra de menú*/
		JMenuBar barraMenu = new JMenuBar();
		
		/* Ańadir menú e ítems*/
		
		/*
		 *  ARCHIVO
		 */
		JMenu menu = new JMenu("Archivo");
		barraMenu.add(menu);
		JMenuItem exit, about;
		
		/* -------- Juego Nuevo -------*/
		juegoNuevo = new JMenuItem("Juego Nuevo");
		menu.add(juegoNuevo);
		
		/* -------- Abrir Juego --------*/
		abrirJuego = new JMenuItem("Abrir");
		menu.add(abrirJuego);
		
		/* -------- Guardar Juego ------*/
		guardarJuego = new JMenuItem("Guardar");
		menu.add(guardarJuego);
		
		/* ------ Salir del juego -----*/
		exit = new JMenuItem("Salir");
		menu.add(exit);
		exit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent evt){System.exit(0);}
		});
		
		/*
		 *  EDICION
		 */
		menu = new JMenu("Edición");
		barraMenu.add(menu);
		
		/* ------ Elegir Color -----*/
		JMenu submenu = new JMenu("Color del Jugador");
		buttongroup = new ButtonGroup();
		radiobutton = new JRadioButtonMenuItem("Negro");
		radiobutton.setSelected(true);
		radiobutton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				humano = new Jugador(EstadoCasilla.BLACK);
			}
		}); 
		buttongroup.add(radiobutton);
		submenu.add(radiobutton);
		radiobutton = new JRadioButtonMenuItem("Blanco");
		radiobutton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				humano = new Jugador(EstadoCasilla.WHITE);
			}
		});
		buttongroup.add(radiobutton);
		submenu.add(radiobutton);
		menu.add(submenu);
		
		/* ------ Definir Oponentes -----*/
		submenu = new JMenu("Definir Oponentes");
		buttongroup = new ButtonGroup();
		radiobutton = new JRadioButtonMenuItem("Humano vs Humano");
		radiobutton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				oponentes = HUM_HUM;
			}
		});
		radiobutton.setSelected(true);
		buttongroup.add(radiobutton);
		submenu.add(radiobutton);
		
		radiobutton = new JRadioButtonMenuItem("Humano vs Robot");
		radiobutton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				oponentes = HUM_ROB;
			}
		});
		buttongroup.add(radiobutton);
		submenu.add(radiobutton);
		
		radiobutton = new JRadioButtonMenuItem("Robot vs Robot");
		radiobutton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				oponentes = ROB_ROB;
			}
		});
		buttongroup.add(radiobutton);
		submenu.add(radiobutton);
		menu.add(submenu);
		
		/* ------ Elegir Tablero -----*/
		submenu = new JMenu("Elegir tablero");
		buttongroup = new ButtonGroup();
		
		tipoTableroButton = new JRadioButtonMenuItem[3];
		tipoTableroButton[0] = new JRadioButtonMenuItem("CLASICO");
		buttongroup.add(tipoTableroButton[0] );
		submenu.add(tipoTableroButton[0] );
		if (tipoTablero == TipoTablero.CLASICO) {
			tipoTableroButton[0].setSelected(true);
		}
		
		tipoTableroButton[1]  = new JRadioButtonMenuItem("OCTOGONAL");
		buttongroup.add(tipoTableroButton[1] );
		submenu.add(tipoTableroButton[1] );
		if (tipoTablero == TipoTablero.OCTOGONAL) {
			tipoTableroButton[1].setSelected(true);
		}
		
		tipoTableroButton[2]  = new JRadioButtonMenuItem("PERSONALIZADO");
		buttongroup.add(tipoTableroButton[2] );
		submenu.add(tipoTableroButton[2]);
		if (tipoTablero == TipoTablero.PERSONALIZADO) {
			tipoTableroButton[2].setSelected(true);
		}
		
		menu.add(submenu);
		
		/* ------ Dimensiones -----*/
		submenu = new JMenu("Dimensiones");
		buttongroup = new ButtonGroup();
		
		tamTableroButton = new JRadioButtonMenuItem[4];
		tamTableroButton [0] = new JRadioButtonMenuItem("10 x 10");
		buttongroup.add(tamTableroButton [0] );
		submenu.add(tamTableroButton [0] );
		if (largoTablero == 10 && anchoTablero == 10) {
			tamTableroButton [0].setSelected(true);
		}
		
		tamTableroButton [1]  = new JRadioButtonMenuItem("12 x 12");
		buttongroup.add(tamTableroButton [1] );
		submenu.add(tamTableroButton [1] );
		if (largoTablero == 12 && anchoTablero == 12) {
			tamTableroButton [1].setSelected(true);
		}
		
		tamTableroButton [2]  = new JRadioButtonMenuItem("14 x 14");
		buttongroup.add(tamTableroButton [2] );
		submenu.add(tamTableroButton [2]);
		if (largoTablero == 14 && anchoTablero == 14) {
			tamTableroButton [2].setSelected(true);
		}
		
		tamTableroButton [3]  = new JRadioButtonMenuItem("16 x 16");
		buttongroup.add(tamTableroButton [3] );
		submenu.add(tamTableroButton [3]);
		if (largoTablero == 16 && anchoTablero == 16) {
			tamTableroButton [3].setSelected(true);
		}
		
		menu.add(submenu);
		
		/*
		 *  AYUDA
		 */
		menu = new JMenu("Ayuda");
		barraMenu.add(menu);
		
		about = new JMenuItem("Acerca de Othello");
		menu.add(about);
		about.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(tablero, "Othello es un juego de estrategia abtracta",
					      "About Othello", JOptionPane.INFORMATION_MESSAGE);
				
			}
		});
		
		constrains.anchor = GridBagConstraints.PAGE_START;
		constrains.fill = GridBagConstraints.HORIZONTAL;
		constrains.gridwidth = 3;
		constrains.gridx = 0;
		constrains.gridy = 1;
		pane.add(barraMenu, constrains);
		constrains.gridwidth = 0;
		
		/* Pintar las casillas */	
		tablero = new JPanel (new GridLayout(largoTablero, anchoTablero));
		ComponenteImagen casillaImagen;
		
		for ( int fila = 0; fila < largoTablero; fila++){
			for (int columna = 0; columna < anchoTablero; columna++){
				switch(tableroLogica.obtenerTablero().get(new Point(fila, columna))){
					case EMPTY:
						casillaImagen = FabricaImagenCasilla.construirCasilla(TipoCasilla.EMPTY);
						break;
					case BLACK:
						casillaImagen = FabricaImagenCasilla.construirCasilla(TipoCasilla.BLACK);
						break;
					case WHITE:
						casillaImagen = FabricaImagenCasilla.construirCasilla(TipoCasilla.WHITE);
						break;
					case WALL:
						casillaImagen = FabricaImagenCasilla.construirCasilla(TipoCasilla.WALL);
						break;
					default:
						casillaImagen = FabricaImagenCasilla.construirCasilla(TipoCasilla.EMPTY);
						break;
				}
				tablero.add(casillaImagen);
				casillas.add(casillaImagen);
			}
		}	
		
		constrains.anchor = GridBagConstraints.CENTER;
		constrains.fill = GridBagConstraints.NONE;
		constrains.gridx = 1;
		constrains.gridy = 2;
		pane.add(tablero, constrains);
		
		/* Pintar la barra de estado */
		estadisticasBlanco = new JLabel("Blancas: ");
		estadisticasBlanco.setBorder(BorderFactory.createEtchedBorder());
		estadisticasBlanco.setBackground(Color.WHITE);
		estadisticasBlanco.setFont(estadisticasBlanco.getFont().deriveFont(Font.PLAIN));
		estadisticasBlanco.setHorizontalAlignment(JLabel.LEFT);
		
		mostrarTurno = new JLabel("ĄEl juego ha iniciado!");
		mostrarTurno.setBorder(BorderFactory.createEtchedBorder());
		mostrarTurno.setBackground(Color.WHITE);
		mostrarTurno.setFont(estadisticasBlanco.getFont().deriveFont(Font.PLAIN));
		mostrarTurno.setHorizontalAlignment(JLabel.CENTER);
		
		estadisticasNegro = new JLabel("Negras: ");
		estadisticasNegro.setBorder(BorderFactory.createEtchedBorder());
		estadisticasNegro.setBackground(Color.WHITE);
		estadisticasNegro.setFont(estadisticasBlanco.getFont().deriveFont(Font.PLAIN));
		estadisticasNegro.setHorizontalAlignment(JLabel.RIGHT);
		
		barraEstado = new JPanel(new GridLayout());
		barraEstado.add(estadisticasBlanco);
		barraEstado.add(mostrarTurno);
		barraEstado.add(estadisticasNegro);
		constrains.anchor = GridBagConstraints.PAGE_END;
		constrains.fill = GridBagConstraints.HORIZONTAL;
		constrains.weightx = 1;
		constrains.gridx = 1;
		constrains.gridy = 4;
		pane.add(barraEstado, constrains);

		boton = new JButton("Salir de Edición");
		boton.setBorder(BorderFactory.createEtchedBorder());
		boton.setSize(20, 50);
		//boton.setBackground(new Color(0, 60, 0));
		barraBoton = new JPanel(new GridLayout());
		barraBoton.add(boton);
		constrains = new GridBagConstraints();
		constrains.fill = GridBagConstraints.NONE;
		constrains.weightx = 300;
		constrains.weighty = 100;
		constrains.gridx = 1;
		constrains.gridy = 20;
		pane.add(barraBoton, constrains);
		mostrarBarraEstado();
		
	}
	
	public void mostrarBarraEstado(){
		barraBoton.setVisible(false);
		barraEstado.setVisible(true);
	}
	
	public void mostrarBarraBoton(){
		barraEstado.setVisible(false);
		barraBoton.setVisible(true);
	}
	

	
	public void definirCasilla(Point point, TipoCasilla tipoCasilla) {
		ComponenteImagen imgcomp = FabricaImagenCasilla.construirCasilla(tipoCasilla);
		int index = Transform.pointToIndex(point);
		casillas.set(index, imgcomp);
		tablero.remove(index);
		tablero.add(imgcomp, index);
		tablero.updateUI();
	}
	
	public void marcarMovidasPosibles(Collection<Point> movidasPosibles, TipoCasilla color){
		for (Point pssblPoint : movidasPosibles) {
			definirCasilla(pssblPoint, color);
			tablero.updateUI();
		}
		tablero.revalidate();
	}
	
	public void desmarcarMovidasPosibles(Collection<Point> movidasPosibles){
		for (Point pssblPoint : movidasPosibles) {
			definirCasilla(pssblPoint, TipoCasilla.EMPTY);	
			tablero.updateUI();
		}
		tablero.revalidate();
	}
	
	public void rellenar(Collection<Point> puntosConRelleno, TipoCasilla color){
		for (Point aRellenar : puntosConRelleno) {
			definirCasilla(aRellenar, color);
			tablero.updateUI();
		}
		tablero.revalidate();
	}
		
	public void actualizarPuntaje(int estadisticasNegro, int estadisticasBlanco) {
		this.estadisticasNegro.setText("Negras: " + estadisticasNegro);
		this.estadisticasBlanco.setText("Blancas: " + estadisticasBlanco);
	}
	
	public void actualizarTurno(String jugador){
		jugador = jugador == "BLACK" ? "Negras" : "Blancas";
		this.mostrarTurno.setText("Le toca a " + jugador);
	}
	
	public void declararEmpate(){
		this.mostrarTurno.setFont(mostrarTurno.getFont().deriveFont(Font.BOLD));
		this.mostrarTurno.setText("Empate !?");
		JOptionPane.showMessageDialog(this, "ĄĄEmpate!! ", "ĄBuena Partida!", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public void declararGanador(String nombreGanador){
		this.mostrarTurno.setFont(mostrarTurno.getFont().deriveFont(Font.BOLD));
		this.mostrarTurno.setText("ĄĄ" + nombreGanador + " gana!!");
	}
	
	public void notificarTurnoPerdido(Jugador jugador){
		/*String jug = jugador == Jugador.BLACK ? "Negras" : "Blancas";
		JOptionPane.showMessageDialog(this, "No hay movidas disponibles, " + jug
			    + " pierde su turno", "ĄBien Jugado!", JOptionPane.INFORMATION_MESSAGE);*/
		String jug = jugador.color() == EstadoCasilla.BLACK ? "Negras" : "Blancas";
		JOptionPane.showMessageDialog(this, "No hay movidas disponibles, " + jug
			    + " pierde su turno", "ĄBien Jugado!", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public void notificarVictoria(Jugador jugador) {
		/*String ganador = jugador == Jugador.BLACK ? "Negras" : "Blancas";
		JOptionPane.showMessageDialog(this, "ĄĄ" + ganador + " gana!! ", "ĄBuena Partida!", JOptionPane.INFORMATION_MESSAGE);*/
		String ganador = jugador.color() == EstadoCasilla.BLACK ? "Negras" : "Blancas";
		JOptionPane.showMessageDialog(this, "ĄĄ" + ganador + " gana!! ", "ĄBuena Partida!", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public List<ComponenteImagen> obtenerCasillas() {
		return casillas;
	}
	
	public JMenuItem obtenerItemNuevoJuego(){
		return juegoNuevo;
	}
	
	public JMenuItem obtenerItemGuardarJuego(){
		return guardarJuego;
	}
	
	public JMenuItem obtenerItemAbrirJuego(){
		return abrirJuego;
	}
	
	public JRadioButtonMenuItem[] obtenerTipoTableroButton(){
		return tipoTableroButton;
	}
	public JRadioButtonMenuItem[] obtenerTamTableroButton(){
		return tamTableroButton;
	}
		
	public JButton obtenerBoton(){
		return boton;
	}
	
	public Jugador obtenerJugadorSeleccionado(){
		return humano;
	}
	
	public int obtenerOponentes(){
		return oponentes;
	}

}
