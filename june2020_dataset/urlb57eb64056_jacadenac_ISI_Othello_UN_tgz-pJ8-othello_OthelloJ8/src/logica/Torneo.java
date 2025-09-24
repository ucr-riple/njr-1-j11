package logica;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JFileChooser;

import core.Tablero.TipoTablero;

public class Torneo {

	private int cantidadVictorias;
	public static int numeroTorneosCreados;
	public String datosTorneo;
	private static final File directorio = new File("setup/tournament");
	
	public Torneo(int[] estrategia, int[][] estrategiasCompetidoras, int tamTablero) {
		super();
		leerParametros();
		datosTorneo="";
		numeroTorneosCreados++;
		cantidadVictorias=0;
		iniciarTorneo(estrategia, estrategiasCompetidoras, tamTablero);
	}

	private void iniciarTorneo(int[] estrategia1, int[][] estrategiasCompetidoras, int tamTablero) {
		datosTorneo += "\r\n------------------TORNEO "+numeroTorneosCreados+"----------------- \r\n";
		datosTorneo += "\r\nTotal partidas jugadas: "+estrategiasCompetidoras.length+"\r\n\r\n";
		JuegoTorneo juego;
		for ( int i=0 ; i<estrategiasCompetidoras.length; i++){
			datosTorneo += "*Juego número "+(i+1)+":\r\n";
			int[] estrategia2 = new int[4];
			for( int j=0 ; j<estrategiasCompetidoras[i].length-1 ; j++){
				estrategia2[j] = estrategiasCompetidoras[i][j];
			}
			
			datosTorneo += "     Estrategia 1 = [ ";
			for(int j=0;j<4;j++){
				datosTorneo += ""+estrategia1[j];
				if (j<3) datosTorneo += ", ";
				else datosTorneo += " ]\r\n"; 
			}
			datosTorneo += "     Estrategia 2 = [ ";
			for(int j=0;j<4;j++){
				datosTorneo += ""+estrategia2[j];
				if (j<3) datosTorneo += ", ";
				else datosTorneo += " ]\r\n"; 
			}
			
			//alterna el color del jugador con estrategia1 de acuerdo al número del torneo
			if (numeroTorneosCreados%2 == 0 && numeroTorneosCreados%3 != 0) {
				juego = new JuegoTorneo(estrategia1, estrategia2, TipoTablero.OCTOGONAL, tamTablero);
			} else if (numeroTorneosCreados%3 == 0) {
				juego = new JuegoTorneo(estrategia1, estrategia2, TipoTablero.PERSONALIZADO, tamTablero);
			}  else {
				juego = new JuegoTorneo(estrategia2, estrategia1, TipoTablero.CLASICO, tamTablero);
			}
			juego.start();
			if(estrategia1 == juego.getGanador()){
				datosTorneo += "     Ganó estrategia 1\r\n";
				//System.out.println("Ganó estrategia 1");
				cantidadVictorias++;
			}else if(estrategia2 == juego.getGanador()){
				datosTorneo += "     Ganó estrategia 2\r\n";
				//System.out.println("Ganó estrategia 2");
			}else if(juego.getGanador() == null){
				datosTorneo += "No Ganó ninguan estrategia\r\n";
				//System.out.println("No Ganó ninguan estrategia");
			}
		}
		datosTorneo += "\r\n     Estrategia 1 obtuvo " + getCantidadVictorias() + " victorias de " 
					   + estrategiasCompetidoras.length + ".\r\n\r\n";
		guardarDatosTorneo(datosTorneo,"Torneos.txt");
		datosTorneo = ""+numeroTorneosCreados;
		guardarDatosTorneo(datosTorneo,"parametrosIniciales.txt");
		
	}
	
	public int getCantidadVictorias() {
		return cantidadVictorias;
	}
	
	public void guardarDatosTorneo(String datos, String nombreArchivo){
		String nombreFichero = nombreArchivo;
		if (!directorio.exists()) {
            directorio.mkdirs();
        }
		try {
			boolean bandera = true;
			if(nombreFichero == "parametrosIniciales.txt"){
				bandera = false;
			}
			FileWriter fichero = new FileWriter(directorio.getPath()+"/"+nombreFichero, bandera);
			PrintWriter writer;
			writer = new PrintWriter(fichero);
			writer.println(datos);
			writer.close();
		} catch (IOException e) {
			System.out.println("No se ha podido escribir en el archivo torneo.txt");
		}
	}
	
	public String getDatosTorneo(){
		return datosTorneo;
	}
	
	private static void leerParametros() {
		JFileChooser fileChooser = new JFileChooser();
		String nombreFichero = "parametrosIniciales.txt";
		File fichero = new File(directorio.getPath(),nombreFichero);
		fileChooser.setCurrentDirectory(fichero);		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(fichero));
			String linea = reader.readLine();
			while ((linea = reader.readLine())  != null){
				numeroTorneosCreados=Integer.parseInt(linea);
			}		
			reader.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
