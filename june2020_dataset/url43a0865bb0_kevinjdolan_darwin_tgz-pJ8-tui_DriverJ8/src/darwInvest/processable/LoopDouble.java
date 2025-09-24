package darwInvest.processable;

import darwInvest.environment.StrategyParameters;
import darwin.population.Node;
import darwin.problemObject.Processable;

public class LoopDouble extends Processable<Double>{

	public final int SUM = 0;
	public final int PRODUCT = 1;
	public final int MAX = 2;
	public final int MIN = 3;
	public final int AVG = 4;
	
	private int which;
	
	public LoopDouble(int which) {
		this.which = which;
	}
	
	@Override
	public Double getValue(Object parameters, Node[] childNodes) {
		StrategyParameters now = (StrategyParameters) parameters;
		
		Node iter = childNodes[0];
		
		double lookback = (Double) childNodes[1].getValue(parameters);
		double offset = (Double) childNodes[2].getValue(parameters);
		double step = now.restrict((Double) childNodes[3].getValue(parameters));
		step = Math.max(step, 0.01);
		
		double end = offset;
		double begin = offset + lookback * (1-offset);
		
		long endTime = now.getTime(end);
		long beginTime = now.getTime(begin);
		
		long timeRange = endTime - beginTime;
		long timeStep = (long) (step * timeRange);
		
		StrategyParameters loop = now.clone();
		
		double acc = 0;
		if(which == MAX)
			acc = Double.MIN_VALUE;
		else if(which == MIN)
			acc = Double.MAX_VALUE;
		int number = 0;
		
		for(long time = beginTime; time <= endTime; time += timeStep) {
			loop.time = time;
			double value = (Double) iter.getValue(loop);
			if(which == SUM)
				acc += value;
			else if(which == PRODUCT)
				acc *= value;
			else if(which == MAX)
				acc = Math.max(value, acc);
			else if(which == MIN)
				acc = Math.min(value, acc);
			else
				acc += value;
			number++;
		}
		
		if(which == AVG)
			acc /= (double) number;
		
		return acc;
	}
	
	@Override
	public Processable<Double> clone() {
		return new LoopDouble(which);
	}
	
	@Override
	protected boolean equalsValue(Processable<?> other) {
		LoopDouble con = (LoopDouble) other;
		return con.getWhich() == which;
	}

	public double getWhich() {
		return which;
	}
	
	public String toString() {
		return "LoopBoolean(" + which + ")";
	}
}
