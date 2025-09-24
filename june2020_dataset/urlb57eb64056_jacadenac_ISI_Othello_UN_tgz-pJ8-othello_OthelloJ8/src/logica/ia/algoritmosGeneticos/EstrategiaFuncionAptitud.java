package logica.ia.algoritmosGeneticos;

import logica.Torneo;

import org.jgap.FitnessFunction;
import org.jgap.IChromosome;

import core.Tablero.TipoTablero;

public class EstrategiaFuncionAptitud extends FitnessFunction{
	private static final long serialVersionUID = 1L;
	private static IChromosome[] estrategiasCompetidoras;
	
	public EstrategiaFuncionAptitud(IChromosome[] poblacion) {
		estrategiasCompetidoras = poblacion;
	}

	public EstrategiaFuncionAptitud() {
		estrategiasCompetidoras = null;
	}

	/**
	 * El método evaluate es el metodo que se debe sobrecargar para que
	 * devuelva el valor de aptitud asociado al cromosoma que se recibe por
	 * parámetro.
	 * 
	 * @param cromosoma
	 * El cromosoma a evaluar
	 * 
	 * @return El valor de aptitud de ese cromosoma
	 * @author Alejandro
	 */
	@Override
	protected double evaluate(IChromosome cromosoma) {
		double fitness=0;
		int[] estrategia1 = obtenerValoresCromosoma(cromosoma);
		
		if (estrategiasCompetidoras == null) {
			System.out.println("No hay competidores!");
		} else {
			int[][] competidores = new int[estrategiasCompetidoras.length][5]; //4 es la id
			for(int i=0;i<estrategiasCompetidoras.length;i++){
				competidores[i][4]=i;
				for(int j=0;j<4;j++){
					competidores[i][j]=getPesoDeGen(estrategiasCompetidoras[i], j);
				}
			}
			
			Torneo torneo = new Torneo(estrategia1, competidores, 10);
						
			fitness = (torneo.getCantidadVictorias());
			if(torneo.getCantidadVictorias() == competidores.length){
				fitness += 10;
			}
			if(fitness == 0){
				fitness = 1;
			}
		}
		
		return fitness;
	}

	private int[] obtenerValoresCromosoma(IChromosome cromosoma) {
		int[] valores = new int[4];
		for (int i=0; i<4; i++){
			valores[i]=getPesoDeGen(cromosoma, i);
		}
		return valores;
	}

	public static int getPesoPuntosGen(IChromosome cromosoma) {
		return getPesoDeGen(cromosoma, 0);
	}

	public static int getPesoEsquinasGen(IChromosome cromosoma) {
		return getPesoDeGen(cromosoma, 1);
	}

	public static int getPesoLibertadesRivalGen(IChromosome cromosoma) {
		return getPesoDeGen(cromosoma, 2);
	}

	public static int getPesoCasillasCentralesGen(IChromosome cromosoma) {
		return getPesoDeGen(cromosoma, 3);
	}

	private static int getPesoDeGen(IChromosome cromosoma, int i) {
		Integer peso = (Integer) cromosoma.getGene(i).getAllele();
		return peso;
	}

	public static IChromosome[] getEstrategiasCompetidoras() {
		return estrategiasCompetidoras;
	}

	public static void setEstrategiasCompetidoras(IChromosome[] estrategiasCompetidoras) {
		EstrategiaFuncionAptitud.estrategiasCompetidoras = estrategiasCompetidoras;
	}

	
	
}
