package darwInvest.processable;

import java.util.Collection;
import java.util.List;
import java.util.TreeMap;

import darwInvest.environment.StrategyParameters;
import darwin.population.Node;
import darwin.problemObject.Processable;

public class NewsScore extends Processable<Double>{

	@Override
	public Double getValue(Object parameters, Node[] childNodes) {
		StrategyParameters now = (StrategyParameters) parameters;
		TreeMap<Long,List<Double>> cache = now.cache;
		
		double value = (Double) childNodes[0].getValue(parameters);
		long time1 = now.getTime(value);
		
		value = (Double) childNodes[1].getValue(parameters);
		long time2 = now.getTime(value);
		
		if(time2 < time1) {
			long temp = time1;
			time1 = time2;
			time2 = temp;
		}
		
		Collection<List<Double>> lists =
			cache.subMap(time1, true, time2, true).values();
		
		double count = 0;
		double total = 0;
		for(List<Double> list : lists) {
			for(Double score : list) {
				count++;
				total += score;
			}
		}
		
		if(count == 0)
			return 0.;
		else
			return total / count;
	}
	
	@Override
	public Processable<Double> clone() {
		return new NewsScore();
	}
}
