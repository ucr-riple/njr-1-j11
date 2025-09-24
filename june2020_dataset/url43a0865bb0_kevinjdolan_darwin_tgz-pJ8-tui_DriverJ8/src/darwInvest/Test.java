package darwInvest;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TreeMap;
import tui.txtListener;
import darwInvest.data.DataLoader;
import darwInvest.data.NewsEvent;
import darwInvest.data.index.TickerIndex;
import darwInvest.data.utility.DefaultSerializer;
import darwInvest.environment.Settings;
import darwInvest.evaluation.BalanceTracker;
import darwInvest.evaluation.StrategyExecutive;
import darwInvest.news.NewsAnalyzer;
import darwInvest.news.NewsTrainer;
import darwInvest.news.tfidf.TfidfTrainer;
import darwin.control.Darwin;
import darwin.control.Parameters;
import darwin.fitness.multiobjective.Pareto;
import darwin.geneticOperation.ConstantMutation;
import darwin.geneticOperation.GeneticOperation;
import darwin.geneticOperation.HoistMutation;
import darwin.geneticOperation.NodeReplacementMutation;
import darwin.geneticOperation.Reproduction;
import darwin.geneticOperation.ShrinkMutation;
import darwin.geneticOperation.SubtreeCrossover;
import darwin.geneticOperation.SubtreeMutation;
import darwin.geneticOperation.Swap;
import darwin.initialization.Grow;
import darwin.initialization.RampedHalfAndHalf;
import darwin.migration.Uniform;
import darwin.population.Individual;
import darwin.population.Population;
import darwin.problemObject.DarwinSheet;
import darwin.selection.FitnessProportional;
import darwin.work.threadPool.ThreadPool;

/**
 * The test class contains the final run-for-the-money test of
 * the DarwInvest system
 * @author Kevin Dolan
 */
public class Test {

	@SuppressWarnings("unchecked")
	public static void main(String[] args){
		
		DarwinSheet DARWIN_SHEET = new darwInvest.DarwinSheet();
		int NUMBER_GENERATIONS = 100;
		NewsTrainer trainer = new TfidfTrainer();
		long forecast = 1209600000L;
		String[] tickers = new String[] {"C", "PFE", "RSH"};
		String FILENAME = "test.csv";
		
		TickerIndex[] indexes = DataLoader.getDataSources(new DefaultSerializer());
		TickerIndex index = indexes[3];
		
		Parameters parameters = new Parameters();
		parameters.initialDepth = 10;
		parameters.populationSize = 2000;
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
		parameters.maxSize = 500;
		parameters.functionSelectionProbability = 0.65;
		parameters.eventListener = new txtListener(parameters);
		parameters.multiobjective = new Pareto(parameters.populationSize);
		
		Settings[] settings = new Settings[tickers.length];
		
		for(int i = 0; i < settings.length; i++) {
			Settings subSettings = new Settings();
			subSettings.initialBalance = 10000;
			subSettings.commission = 0;
			subSettings.minCommission = 10;
			subSettings.bankruptcy = 1000;
			subSettings.lookback = 2419200000L;
			subSettings.numTrials = 3;
			subSettings.testLength = 1 * 2419200000L;
			subSettings.threshold = 1000;
			subSettings.maxSentiment = 2000;
			subSettings.maxSentimentBuildup = 4000;
			subSettings.cutoffSentimentProportion = 0.8;
			subSettings.cutoffTimeProportion = 0.8;
			subSettings.ticker = index.getTickerFile(tickers[i]);
			
			settings[i] = subSettings;
		}
		
		parameters.environments = new Object[] {
			settings[0],
			settings[1],
			settings[2]
		};
		
		long[] testStarts = new long[10];
		
		GregorianCalendar cal = new GregorianCalendar();
		
		cal.set(2008, Calendar.OCTOBER, 1);
		testStarts[0] = cal.getTime().getTime();
		
		cal.set(2008, Calendar.NOVEMBER, 1);
		testStarts[1] = cal.getTime().getTime();
		
		cal.set(2008, Calendar.DECEMBER, 1);
		testStarts[2] = cal.getTime().getTime();
		
		cal.set(2009, Calendar.JANUARY, 1);
		testStarts[3] = cal.getTime().getTime();
		
		cal.set(2009, Calendar.FEBRUARY, 1);
		testStarts[4] = cal.getTime().getTime();
		
		cal.set(2009, Calendar.MARCH, 1);
		testStarts[5] = cal.getTime().getTime();
		
		cal.set(2009, Calendar.APRIL, 1);
		testStarts[6] = cal.getTime().getTime();
		
		cal.set(2009, Calendar.MAY, 1);
		testStarts[7] = cal.getTime().getTime();
		
		cal.set(2009, Calendar.JUNE, 1);
		testStarts[8] = cal.getTime().getTime();
		
		cal.set(2009, Calendar.JULY, 1);
		testStarts[9] = cal.getTime().getTime();
		
		String output = "Test Beginning";
		for(int i = 0; i < tickers.length; i++) {
			for(int j = 0; j < 5; j++)
				output += "," + tickers[i];
		}
		output += "\n";
		
		for(int i = 0; i < testStarts.length; i++) {
			long start = testStarts[i];
			long end = start + 1 * 2419200000L;
			
			long gpStart = start - (long)(31556926000L);
			long gpEnd = start; 
			
			for(int j = 0; j < tickers.length; j++) {
				settings[j].testBeginning = gpStart;
				settings[j].testEnd = gpEnd;
			}
			
			cal.set(2000, Calendar.JANUARY, 1);
			long newsStart = cal.getTime().getTime();
			long newsEnd = gpStart;
			
			System.out.println("Training news from " + (new Date(newsStart)) 
					+ " to " + (new Date(newsEnd)) + "...");
			NewsAnalyzer[] analyzers = new NewsAnalyzer[tickers.length];
			for(int j = 0; j < tickers.length; j++) 
				analyzers[j] = trainer.train(settings[j].ticker,  
					newsStart, newsEnd);
			
			System.out.println("Gathering news from the training period...");
			List<NewsEvent>[] events = (List<NewsEvent>[]) new List<?>[tickers.length];
			for(int j = 0; j < tickers.length; j++) 
				events[j] = settings[j].ticker
					.getNews(gpStart, gpEnd);
			
			System.out.println("Caching news scores for the training period (" 
				+ (new Date(gpStart)) + " to " + (new Date(gpEnd)) + ")...");
			TreeMap<Long,List<Double>>[] cache = 
				(TreeMap<Long,List<Double>>[]) new TreeMap<?,?>[tickers.length];
			for(int j = 0; j < tickers.length; j++) {
				cache[j] = new TreeMap<Long,List<Double>>();
				for(NewsEvent event : events[j]) {
					double score = analyzers[j].score(event, forecast);
					
					List<Double> list = cache[j].get(event.getDate());
					if(list == null) {
						list = new ArrayList<Double>();
						cache[j].put(event.getDate(), list);
					}
					list.add(score);
				}
				settings[j].cache = cache[j];
			}
			
			System.out.println("Starting Darwin...");
			Darwin darwin = new Darwin(parameters);
			
			System.out.println("Running " + NUMBER_GENERATIONS + " generations...");
			darwin.executeGenerations(NUMBER_GENERATIONS);
			
			System.out.println("Running performance test from " 
				+ (new Date(start)) + " to " + (new Date(end)));
			
			output += (new Date(start));
			for(int j = 0; j < tickers.length; j++) {
				System.out.println("Processing " + tickers[j]);
				
				System.out.println("Retraining news from " + (new Date(newsStart)) 
						+ " to " + (new Date(gpEnd)) + "...");
				analyzers[j] = trainer.train(settings[j].ticker,  
					newsStart, newsEnd);
				
				System.out.println("Gathering news from the test period...");
				events[j] = settings[j].ticker.getNews(start, end);
				System.out.println(events[j].size() + " news stories.");
				settings[j].testBeginning = start;
				settings[j].testEnd = end;
				
				System.out.println("Caching news scores for the test period (" 
					+ (new Date(start)) + " to " + (new Date(end)) + ")...");
				cache[j] = new TreeMap<Long,List<Double>>();
				for(NewsEvent event : events[j]) {
					double score = analyzers[j].score(event, forecast);
					
					List<Double> list = cache[j].get(event.getDate());
					if(list == null) {
						list = new ArrayList<Double>();
						cache[j].put(event.getDate(), list);
					}
					list.add(score);
				}
				settings[j].cache = cache[j];
				
				System.out.println("Testing the fitness of the 10 best individuals of the population.");
				
				Collection<Integer> best = darwin.getFitness().getBestOfGeneration(j, 5);
				Population pop = darwin.getPopulation(j);
				for(Integer good : best) {
					Individual individual = pop.getIndividual(good);
				
					BalanceTracker tracker = new BalanceTracker(settings[j].initialBalance, 
						settings[j].commission, settings[j].minCommission, settings[j].bankruptcy);
					StrategyExecutive executive = new StrategyExecutive(tracker, individual, 
						start, end, settings[j]);
					executive.execute();
					
					double roi =  tracker.getLiquidationValue() / settings[j].initialBalance;
					
					System.out.println("Individual ROI: " + roi);
					output += "," + roi;
				}
			}
			output += "\n";		
		}
		
		System.out.println("Outputting the file...");
		try {
			File outFile = new File(FILENAME);
			BufferedWriter  out = new BufferedWriter(new FileWriter(outFile));
			out.write(output);
			out.close();
		} catch (IOException e) {}
		
		System.out.println("All done!");
	
	}

}
