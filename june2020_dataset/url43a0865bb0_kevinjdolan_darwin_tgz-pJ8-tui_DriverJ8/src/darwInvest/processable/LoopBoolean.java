package darwInvest.processable;

import darwInvest.environment.StrategyParameters;
import darwin.population.Node;
import darwin.problemObject.Processable;

public class LoopBoolean extends Processable<Double>{

	public final int COUNT_TRUE = 0;
	public final int COUNT_FALSE = 1;
	
	private int which;
	
	public LoopBoolean(int which) {
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
		int count = 0;
		int number = 0;
		
		for(long time = beginTime; time <= endTime; time += timeStep) {
			loop.time = time;
			boolean value = (Boolean) iter.getValue(loop);
			if((which == COUNT_TRUE && value) || (which == COUNT_FALSE && !value))
				count++;
			number++;
		}
		
		return ((double) count) / number; 
		
	}
	
	@Override
	public Processable<Double> clone() {
		return new LoopBoolean(which);
	}
	
	@Override
	protected boolean equalsValue(Processable<?> other) {
		LoopBoolean con = (LoopBoolean) other;
		return con.getWhich() == which;
	}

	public double getWhich() {
		return which;
	}
	
	public String toString() {
		return "LoopBoolean(" + which + ")";
	}
}
