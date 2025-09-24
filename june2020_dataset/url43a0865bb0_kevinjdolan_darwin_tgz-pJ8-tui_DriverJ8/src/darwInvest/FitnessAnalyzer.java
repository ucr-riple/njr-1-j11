package darwInvest;

import darwInvest.environment.Settings;
import darwInvest.evaluation.BalanceTracker;
import darwInvest.evaluation.StrategyExecutive;
import darwin.population.Individual;

public class FitnessAnalyzer extends darwin.problemObject.FitnessAnalyzer {

	private final double MAX_ROI = 3;
	
	@Override
	public double[] assessFitness(Individual individual, Object environment) {
		Settings settings = (Settings) environment;
		
		double total = 0;
		
		for(int i = 0; i < settings.numTrials; i++) {
			BalanceTracker tracker = new BalanceTracker(settings.initialBalance, settings.commission, 
				settings.minCommission, settings.bankruptcy);
			
			//Calculate the time periods
			long last = settings.testEnd - settings.testLength;
			long first = settings.testBeginning + settings.lookback;
			long diff = last - first;
			long offset = (long) (Math.random() * diff);
			long begin = first + offset;
			long end = begin + settings.testLength;
					
			//Do the execution, get a result
			StrategyExecutive executive = new StrategyExecutive(tracker, individual, begin, end, settings);
			executive.execute();
			double bal = tracker.getLiquidationValue();
			
			//Calculate the real test length
			long realBegin = settings.ticker.ceilingTime(begin);
			long realEnd = settings.ticker.floorTime(end);
			long realLength = realEnd - realBegin;
			
			//Calculate the interestingness values
			double sentimentProportion = tracker.getPositiveSentimentProportion();
			double timeProportion = tracker.getTimeIn() / (double) realLength;
			
			//Figure an ROI value
			double roi;
			//Make sure the strategy is interesting enough to accept!
			if( sentimentProportion > (1 - settings.cutoffSentimentProportion) && 
				sentimentProportion < settings.cutoffSentimentProportion &&
				timeProportion > (1 - settings.cutoffTimeProportion) &&
				timeProportion < settings.cutoffTimeProportion )
				roi = bal / settings.initialBalance;
			//Uninteresting...
			else
				roi = 0;
			
			//Don't go too crazy
			roi = Math.min(roi, MAX_ROI);
			
			total += roi;	
		}
		
		double average = total / settings.numTrials;
		
		return new double[] {average};
	}	

	@Override
	public darwin.problemObject.FitnessAnalyzer clone() {
		return new FitnessAnalyzer();
	}

	@Override
	public int getNumberMetrics() {
		return 1;
	}

	@Override
	public double standardize(int fitnessMetric, double fitness) {
		return MAX_ROI - fitness;
	}

}
