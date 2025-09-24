package logica.ia.algoritmosGeneticos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;

import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.GeneticOperator;
import org.jgap.Genotype;
import org.jgap.Population;
import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;
import org.jgap.data.DataTreeBuilder;
import org.jgap.data.IDataCreators;
import org.jgap.impl.DefaultConfiguration;
import org.jgap.impl.IntegerGene;
import org.jgap.xml.XMLDocumentBuilder;
import org.jgap.xml.XMLManager;
import org.w3c.dom.Document;

/**
 * @autores: Alejandro Cadena, Jefferson Cortes
 */

public class Estrategia {

	/**
	 *  Número total de veces que permitira evolucionar la población
	 */
	private static final int MAX_EVOLUCIONES_PERMITIDAS = 3000;
	private static final int RANGO_GEN = 1000;
	private static final File directorio = new File("setup/generations");
	public static int numeroEvolucion=0;
	public static String cadena="";
	private static String datosGeneraciones="";
	public static int numeroCromosomasCreados=0;
	public static Configuration conf = new DefaultConfiguration();

	/**
	 * Calcula utilizando algoritmos genéticos la solución al problema
	 * y la la retorna.
	 * @throws InvalidConfigurationException 
	 */
	
	@SuppressWarnings("deprecation")
	public static void calcularMejorEstrategia() throws InvalidConfigurationException{
		leerParametros();
		
		// se crea una configuracion con valores predeterminados
		// -------------------------------------------------------------------
			/*Configuration conf = new DefaultConfiguration();*/
		// Se indica en la conficuracion que el elemento más apto siempre pase
		// a la próxima generacion
		// -------------------------------------------------------------------
		conf.setPreservFittestIndividual(true);	
		// Se crea la funcion de aptitud y se setea en la configuración
		// -------------------------------------------------------------------
		FitnessFunction myFunc = new EstrategiaFuncionAptitud(/*poblacion.getChromosomes()*/);
		conf.setFitnessFunction(myFunc);
		

		// Se define el operador genético: cómo será la reproducción
		// -------------------------------------------------------------------	
		@SuppressWarnings("serial")
		GeneticOperator operadorGeneticoMutacion = new GeneticOperator() {
			@Override @SuppressWarnings("rawtypes")
			public void operate(Population actual, List nueva) {
				nueva = mutarYCruzar(actual, nueva);
			}
		};
		//Ańade el Operador Genético de mutación creado anteriormente
		conf.addGeneticOperator(operadorGeneticoMutacion);
		//conf.setNaturalSelector(null);
		/*operadorGenetico.operate(poblacionActual, cromosomasSeleccionados);*/
		
		// Ahora se indica cómo serán los cromosomas: en este caso tendrán 4
		// genes (uno para cada coeficiente de la EvaluaciónEstrategica)
		// con un valor entero que representa el peso de la variable que 
		// acompańará.
		// Se debe crear un cromosoma de ejemplo y cargarlo en la configuración.
		// Cada gen tendrá un valor máximo y mínimo que debe setearse.
		// -------------------------------------------------------------------
		int pesoGenAnterior=(int)Math.random()*RANGO_GEN;
		
		Gene[] sampleGenes = new Gene[4];
		sampleGenes[0] = new IntegerGene(conf, 0, pesoGenAnterior); // Importancia puntos
		pesoGenAnterior=(int)Math.random()*(RANGO_GEN-pesoGenAnterior);
		sampleGenes[1] = new IntegerGene(conf, 0, pesoGenAnterior); // Importancia esquinas
		pesoGenAnterior=(int)Math.random()*(RANGO_GEN-pesoGenAnterior);
		sampleGenes[2] = new IntegerGene(conf, 0, RANGO_GEN); // Importancia libertades rival
		pesoGenAnterior=(int)Math.random()*(pesoGenAnterior);
		sampleGenes[3] = new IntegerGene(conf, 0, RANGO_GEN); // Importancia casillas centrales
		
		IChromosome sampleChromosome = new Chromosome(conf, sampleGenes);
		conf.setSampleChromosome(sampleChromosome);
		// Por ultimo se debe indicar el tamańo de la poblacion en la
		// configuración
		// -------------------------------------------------------------------
		conf.setPopulationSize(cantidadCompetidores());
		/**///conf.setPopulationSize(16);
		
		// El framework permite obtener la población inicial de archivos xml
		// pero para este caso se crea una poblacion aleatoria, para ello se utiliza
		// el metodo randomInitialGenotype que devuelve la población random creada.
		/**/Genotype poblacion = obtenerPoblacion(conf);
		//Genotype poblacion = Genotype.randomInitialGenotype(conf);		
		EstrategiaFuncionAptitud.setEstrategiasCompetidoras(poblacion.getChromosomes());
		
		
		// La población debe evolucionar para obtener resultados más aptos
		// -------------------------------------------------------------------
		long tiempoComienzo = System.currentTimeMillis();
		
		for (int i = 0; i < MAX_EVOLUCIONES_PERMITIDAS; i++){
			datosGeneraciones = "\r\n------------------ GENERACIÓN "+numeroEvolucion+" ----------------- \r\n";
			System.out.println("\n------------------GENERACIÓN "+numeroEvolucion+"----------------- \n");
			IChromosome[] cromosomas = poblacion.getPopulation().toChromosomes();
			datosGeneraciones += "\r\n"+poblacionToString(cromosomas)+"\r\n";
			
			//Obtiene los dos individuos con mejor aptitud
			@SuppressWarnings("unchecked")
			List<IChromosome> mejores2 = poblacion.getFittestChromosomes(2);
			
			int[] mejor1 = obtenerValoresCromosoma((IChromosome) mejores2.get(0));
			int[] mejor2 = obtenerValoresCromosoma((IChromosome) mejores2.get(1));
			
			String dosMejoresCromosomas = "\r\n";
			dosMejoresCromosomas +=  mejor1[0]+","+mejor1[1]+","+mejor1[2]+","+mejor1[3]+"\r\n"+
									 mejor2[0]+","+mejor2[1]+","+mejor2[2]+","+mejor2[3];
			guardarDatosEvolucion(dosMejoresCromosomas,"dosMejores.txt");
			
			//Evoluciona la población
			poblacion.evolve();
			
			//Actualiza las nuevas estrategias competidoras en la función de aptitud
			EstrategiaFuncionAptitud.setEstrategiasCompetidoras(poblacion.getChromosomes());
			
			datosGeneraciones += "Mejores:\r\n"+ dosMejoresCromosomas+"\r\n\r\n";
			guardarDatosEvolucion(datosGeneraciones,"evolucionGeneraciones.txt");
			numeroEvolucion++;
			
			datosGeneraciones = numeroEvolucion+","+numeroCromosomasCreados;
			guardarDatosEvolucion(datosGeneraciones,"parametrosIniciales.txt");
		}
		long tiempoFin = System.currentTimeMillis();
		cadena += "Tiempo total de evolución: "+ (tiempoFin - tiempoComienzo) + " ms";
		// Una vez que la poblacion evoluciono es necesario obtener el cromosoma
		// más apto para mostrarlo como solución al problema planteado para ello
		// se utiliza el método getFittestChromosome
		IChromosome cromosomaMasApto = poblacion.getFittestChromosome();
		cadena+="\n"+"El cromosoma más apto encontrado tiene un valor de aptitud de: " + cromosomaMasApto.getFitnessValue();
		cadena+="\n"+"Y esta formado por la siguiente distribución de pesos: ";
		cadena+="\n"+"\t"+ EstrategiaFuncionAptitud.getPesoPuntosGen(cromosomaMasApto) + " Importancia de centrarse en obtener más puntos que el rival";
		cadena+="\n"+"\t"+ EstrategiaFuncionAptitud.getPesoEsquinasGen(cromosomaMasApto) + " Importancia de obtener las esquinas";
		cadena+="\n"+"\t"+ EstrategiaFuncionAptitud.getPesoLibertadesRivalGen(cromosomaMasApto) + "Importancia de dejar sin jugadas al rival";
		cadena+="\n"+"\t"+ EstrategiaFuncionAptitud.getPesoCasillasCentralesGen(cromosomaMasApto) + "Importancia de obtener las casillas centrales";
		cadena+="\n"+"Para obtener un peso total de+"+RANGO_GEN; 
		
	}
	
	// -------------------------------------------------------------------
	// Este metodo permite guardar en un xml la última población calculada
	// -------------------------------------------------------------------
	
	public static void guardarPoblacion(Genotype Poblacion) throws Exception {
		DataTreeBuilder builder = DataTreeBuilder.getInstance();
		IDataCreators doc2 = builder.representGenotypeAsDocument(Poblacion);
		// Create XML document from generated tree
		XMLDocumentBuilder docbuilder = new XMLDocumentBuilder();
		Document xmlDoc = (Document) docbuilder.buildDocument(doc2);
		XMLManager.writeFile(xmlDoc, new File("PoblacionEstrategia.xml"));
		
	}
	
	// -------------------------------------------------------------------
	// Este metodo permite obtiene la población a partir de un archivo
	// -------------------------------------------------------------------
	
	public static Genotype obtenerPoblacion(Configuration conf) throws InvalidConfigurationException{
		int[][] estrategiasCompetidoras = leerPoblacion();//obtenerCompetidores();
		Chromosome[] chromosomas= new Chromosome[cantidadCompetidores()];
		for(int i=0 ; i<chromosomas.length ; i++){
			Gene[] genes = new Gene[4];
			for(int j=0 ; j<genes.length ; j++){
				genes[j] = new IntegerGene(conf, 0, RANGO_GEN);
				genes[j].setAllele((Integer)(estrategiasCompetidoras[i][j]));
			}		
			chromosomas[i] = new Chromosome(conf, genes);	
			/**/int[] cromosoma = obtenerValoresCromosoma(chromosomas[i]);
			/**///System.out.println(cromosoma[0]+","+cromosoma[1]+","+cromosoma[2]+","+cromosoma[3]);
		}
		Population population = new Population(conf,chromosomas);
		return new Genotype(conf,population);
	}
	
	public static int cantidadCompetidores(){
		return leerPoblacion().length;
	}
	
	private static int[] obtenerValoresCromosoma(IChromosome cromosoma) {
		int[] valores = new int[4];
		for (int i=0; i<4; i++){
			valores[i]=getPesoDeGen(cromosoma, i);
		}
		return valores;
	}
	
	private static int getPesoDeGen(IChromosome cromosoma, int i) {
		Integer peso = (Integer) cromosoma.getGene(i).getAllele();
		return peso;
	}
	
	private static int[][] leerPoblacion() {
		    int[][] poblacion = new int[17][5];  //tamańo población inicial -> 15
			JFileChooser fileChooser = new JFileChooser();
			//String nombreFichero = "poblacionInicial.txt";
			String nombreFichero = "ultimaGeneracion.txt";
			File fichero = new File(directorio.getPath(),nombreFichero);
			fileChooser.setCurrentDirectory(fichero);		
			try {
				BufferedReader reader = new BufferedReader(new FileReader(fichero));
				String linea = reader.readLine();
				int i = 0;
				while (linea != null) {
					String[] dimension = linea.split ("-");
					poblacion[i][4] = Integer.parseInt(dimension[0]);
					dimension = dimension[1].split(",");
					for (int j=0 ; j<dimension.length ; j++) {				
						poblacion[i][j]=Integer.parseInt(dimension[j]);
					}
					linea = reader.readLine();
					i++;
				}
				reader.close();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return poblacion;
	}
	
	public static String poblacionToString(IChromosome[] cromosomas){
		String poblacion = "";
		String nombreFichero = "ultimaGeneracion.txt";
		if (!directorio.exists()) {
            directorio.mkdirs();
        }
		try {
			FileWriter fichero = new FileWriter(directorio.getPath()+"/"+nombreFichero, false);
			PrintWriter writer;
			writer = new PrintWriter(fichero);
			for (int i = 0; i < 16; i++) {
				int[] cromosoma = obtenerValoresCromosoma(cromosomas[i]);
				poblacion += numeroCromosomasCreados+"-"+cromosoma[0]+","+cromosoma[1]+","+cromosoma[2]+","+cromosoma[3];
				if(i != cromosomas.length-1){
					//poblacion += "\r\n";
				}
				numeroCromosomasCreados++;
			}
			writer.println(poblacion);
			writer.close();
		} catch (IOException e) {
			System.out.println("No se ha podido escribir en el archivo torneo.txt");
		}
		return poblacion;
	}
	
	public static void guardarDatosEvolucion(String datos, String nombreArchivo){
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
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List mutarYCruzar(Population actual, List nueva){
			List<IChromosome> cromosomasActuales = actual.getChromosomes();
			List<IChromosome> temporal = new ArrayList<IChromosome>();
			List<IChromosome> cromosomasSinRepetidos = new ArrayList<IChromosome>();
			
			//obtenemos el mejor de la población actual y lo almacenamos temporalmente:
			IChromosome cromosomaMasApto = actual.determineFittestChromosome();
			int AleloActual;
			int AleloNuevo=0;
			int valorAleatorio;  
			int signo = (int)Math.signum((Math.random()*2)-1);
			
			int i=0;
			for(IChromosome cromosoma: cromosomasActuales){
				Gene[] genes = new Gene[4];
				int pesoGenAnterior=(int)Math.random()*RANGO_GEN;
				for(int j=0 ; j<genes.length ; j++){
					try {
						genes[j] = new IntegerGene(conf, 0, pesoGenAnterior);
						pesoGenAnterior=(int)Math.random()*(RANGO_GEN-pesoGenAnterior);
					} catch (InvalidConfigurationException e) {
						System.out.println("Error al crear los Genes en GeneticOperator");
					}
					AleloActual = (Integer)cromosoma.getGenes()[j].getAllele();
					//si encuentra individuos repetidos, realiza mutaciones
					if( (i+1) < cromosomasActuales.size()){
						for(int k = i+1; k < cromosomasActuales.size();k++){
							if(cromosoma.equals(cromosomasActuales.get(k))){
								/**///System.out.println("Entró mutar repetidos!");
								valorAleatorio = (int) Math.floor(Math.random()*(0-90+1)+90); // Valor entre 10 y 100, ambos incluidos.
								if (AleloActual  + (signo*valorAleatorio) < 0 || AleloActual  + (signo*valorAleatorio) > RANGO_GEN) { 
									AleloNuevo = AleloActual +(-1)*(signo*valorAleatorio);
								} else {
									AleloNuevo = AleloActual +(-1)*(signo*valorAleatorio);
								}
							}
						}
					}
					genes[j].setAllele(AleloNuevo);
				}
				try {
					IChromosome cromosomaNuevo = new Chromosome(conf, genes);
					cromosomasSinRepetidos.add(cromosomaNuevo);
					//System.out.println("size="+temporal.size());
				} catch (InvalidConfigurationException e) {
					System.out.println("Error al ańadir a Cromosoma temporal en GeneticOperator");
				}
				i++;
			}
			
			i=0;
			for(IChromosome cromosoma: cromosomasSinRepetidos){
				Gene[] genes = new Gene[4];
				int pesoGenAnterior=(int)Math.random()*RANGO_GEN;
				for(int j=0 ; j<genes.length ; j++){
					try {
						genes[j] = new IntegerGene(conf, 0, RANGO_GEN-pesoGenAnterior);
						pesoGenAnterior=(int)Math.random()*(RANGO_GEN-pesoGenAnterior);
					} catch (InvalidConfigurationException e) {
						System.out.println("Error al crear los Genes en GeneticOperator");
					}
					AleloActual = (Integer)cromosoma.getGenes()[j].getAllele();
	
					//Si el cromosoma más apto se encuentra repetido, realiza mutaciones pequeńas sobre ellos
					//Si no, realizará mutaciones grandes a unos y cruzes con otros
					if(cromosoma.equals(cromosomaMasApto)){
						/**///System.out.println("Entró a mutar en primer grado!");
						valorAleatorio = (int) Math.floor(Math.random()*(10-130+1)+130); // Valor entre 0 y 80, ambos incluidos.
						if (AleloActual  + (signo*valorAleatorio) < 0 || AleloActual  + (signo*valorAleatorio) > RANGO_GEN) { 
							AleloNuevo = AleloActual +(-1)*(signo*valorAleatorio);
						} else {
							AleloNuevo = AleloActual +(-1)*(signo*valorAleatorio);
						}
					} else if( i%2 == 0 ){
						/**///System.out.println("Entró a mutar en segundo grado!");
						valorAleatorio = (int) Math.floor(Math.random()*(100-600+1)+600); //Valor entre 100 y 600, ambos incluidos.
						if (AleloActual  + (signo*valorAleatorio) < 0 || AleloActual  + (signo*valorAleatorio) > RANGO_GEN) { 
							AleloNuevo = AleloActual +(-1)*(signo*valorAleatorio);
						} else {
							AleloNuevo = AleloActual +(-1)*(signo*valorAleatorio);
						}
					} else {
						if( j%2 == 0 && (i+1) < cromosomasActuales.size() && j<cromosomasActuales.get(i+1).getGenes().length){
							/**///System.out.println("Entró a cruzar!");
							int AleloSiguiente = (Integer)cromosomasActuales.get(i+1).getGenes()[j+1].getAllele();
							AleloNuevo = AleloSiguiente;
						} else {
							AleloNuevo = AleloActual;
						}
					}
					genes[j].setAllele(AleloNuevo);
				}		
				try {
					IChromosome cromosomaNuevo = new Chromosome(conf, genes);
					temporal.add(cromosomaNuevo);
					//System.out.println("size="+temporal.size());
				} catch (InvalidConfigurationException e) {
					System.out.println("Error al ańadir a Cromosoma temporal en GeneticOperator");
				}
				i++;
			}
			int cantPoblacionAMutar = (int)(conf.getPopulationSize()/**0.9*/);
			nueva.add(cromosomaMasApto);
			for(int j=1; j < cantPoblacionAMutar; j++){
				valorAleatorio = (int) Math.random()*temporal.size();
				if(temporal !=null){
					nueva.add(temporal.get(valorAleatorio));
					temporal.remove(valorAleatorio);
				}
			}
			return nueva;
	}

	private static void leerParametros() {
		JFileChooser fileChooser = new JFileChooser();
		String nombreFichero = "parametrosIniciales.txt";
		File fichero = new File(directorio.getPath(),nombreFichero);
		fileChooser.setCurrentDirectory(fichero);		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(fichero));
			String linea = reader.readLine();
			String[] dimensiones = linea.split(",");
			numeroEvolucion=Integer.parseInt(dimensiones[0]);
			numeroCromosomasCreados=Integer.parseInt(dimensiones[1]);
			reader.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}