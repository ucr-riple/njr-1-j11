package darwin.common.math.processable;

import darwin.population.Node;
import darwin.problemObject.Processable;

public class Constant extends Processable<Double>{

	private double value;
	
	public Constant(double value) {
		this.value = value;
	}
	
	@Override
	public Double getValue(Object parameters, Node[] childNodes) {
		return value;
	}
	
	@Override
	public Processable<Double> clone() {
		return new Constant(value);
	}
	
	@Override
	protected boolean equalsValue(Processable<?> other) {
		Constant con = (Constant) other;
		return con.getValue() == value;		
	}

	public double getValue() {
		return value;
	}
	
	public String toString() {
		return "Constant(" + value + ")";
	}
}
