package darwInvest.processable;

import darwInvest.environment.StrategyParameters;
import darwin.population.Node;
import darwin.problemObject.Processable;

public class GetPrice extends Processable<Double>{

	private int which;
	
	public GetPrice(int which) {
		this.which = which;
	}
	
	@Override
	public Double getValue(Object parameters, Node[] childNodes) {
		StrategyParameters now = (StrategyParameters) parameters;
		
		double value = (Double) childNodes[0].getValue(parameters);
		long time = now.getTime(value);
		
		double data = now.ticker.getData(time, which);
		return now.ticker.normalizeData(which, data);
	}
	
	@Override
	public Processable<Double> clone() {
		return new GetPrice(which);
	}
	
	@Override
	protected boolean equalsValue(Processable<?> other) {
		GetPrice con = (GetPrice) other;
		return con.getWhich() == which;
	}

	public double getWhich() {
		return which;
	}
	
	public String toString() {
		return "GetPrice(" + which + ")";
	}
}
