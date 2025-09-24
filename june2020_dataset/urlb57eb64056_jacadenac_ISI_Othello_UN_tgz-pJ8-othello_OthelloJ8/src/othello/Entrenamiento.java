package othello;

import org.jgap.InvalidConfigurationException;

import logica.ia.algoritmosGeneticos.Estrategia;

public class Entrenamiento extends Thread{
	
	public void run() {
		entrenar();
	    System.out.println("Terminó de entrenar:"+this.getName());
	}
	
	private void entrenar(){	
		try {
			Estrategia.calcularMejorEstrategia();
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}
}
