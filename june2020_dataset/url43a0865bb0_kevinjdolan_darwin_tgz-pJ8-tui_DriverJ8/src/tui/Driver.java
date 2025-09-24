package tui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import darwin.control.Darwin;
import darwin.control.Parameters;
import darwin.fitness.multiobjective.*;
import darwin.geneticOperation.*;
import darwin.initialization.*;
import darwin.migration.Uniform;
import darwin.population.Individual;
import darwin.population.Population;
import darwin.problemObject.*;
import darwin.selection.FitnessProportional;
import darwin.work.threadPool.ThreadPool;
import darwin.problemObject.Output;

public class Driver {

	public static void main(String[] args) {
		
		Parameters parameters = new Parameters();
		
		//DarwinSheet
		DarwinSheet DARWIN_SHEET = new glass.DarwinSheet();
		
		BufferedImage img1 = null;
		try {
		    img1 = ImageIO.read(new File("mona.jpg"));
		} catch (IOException e) {}
		
		parameters.environments = new Object[] {img1};
				
		//Manually Edit Settings
		int NUMBER_GENERATIONS = 2000;
		parameters.initialDepth = 4;
		parameters.populationSize = 100;
		parameters.migrationProbability = 0.1;
		parameters.migrationFactor = 0.01;
		parameters.migration = new Uniform();
		parameters.selection = new FitnessProportional();
		parameters.nodeFactory = DARWIN_SHEET.getNodeFactory();
		parameters.fitnessAnalyzer = DARWIN_SHEET.getFitnessAnalyzer();
		parameters.operations = new GeneticOperation[] {
			new Reproduction(),
			new SubtreeCrossover(),
			new Swap(),
			new SubtreeMutation(new Grow(parameters.nodeFactory)),
			new ConstantMutation(),
			new NodeReplacementMutation(parameters.nodeFactory),
			new HoistMutation(),
			new ShrinkMutation(parameters.nodeFactory)
		};
		parameters.operationProbabilities = 
			new double[] { 0.1, 0.7, 0.1, 0.02, 0.02, 0.02, 0.02, 0.02 };
		parameters.initializer = new RampedHalfAndHalf();
		parameters.jobRunner = new ThreadPool(6);
		parameters.elite = 1;
		parameters.maxSize = 1000;
		parameters.functionSelectionProbability = 0.8;
		parameters.eventListener = new txtListener(parameters);
		parameters.multiobjective = new Pareto(parameters.populationSize);
		
		//Instantiate Darwin
		System.out.println("Starting Darwin");
		Darwin darwin = new Darwin(parameters);
		System.out.println("Darwin Started");
		
		darwin.executeGenerations(NUMBER_GENERATIONS);
		
		System.out.println("Outputting the best individual of each population.");
		
		Output out = DARWIN_SHEET.getOutput();
		Individual[] individuals = new Individual[parameters.environments.length];
		for(int i = 0; i < individuals.length; i++) {
			Collection<Integer> best = darwin.getFitness().getBestOfGeneration(i, 1);
			Population pop = darwin.getPopulation(i);
			for(Integer good : best) 
				individuals[i] = pop.getIndividual(good);
		}
		createAndShowGUI(out, individuals, parameters.environments);
		
		System.out.println("Killing the threads");
		
		((ThreadPool) parameters.jobRunner).killThreads();
		
		System.out.println("Processing complete.");
	}
	
	private static void createAndShowGUI(Output output, Individual[] individuals, Object[] environments) {
        //Create and set up the window.
        JFrame frame = new JFrame("Best Individual Outputs");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane tabs = new JTabbedPane();
        tabs.setPreferredSize(new Dimension(800,600));
        for(int i = 0; i < individuals.length; i++) {
        	JComponent comp = output.getOutput(individuals[i], environments[i]);
        	JScrollPane scroll = new JScrollPane(comp);
        	scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        	scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        	tabs.addTab("Population "+i, scroll);
        }
        
        frame.getContentPane().add(tabs);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }



}
