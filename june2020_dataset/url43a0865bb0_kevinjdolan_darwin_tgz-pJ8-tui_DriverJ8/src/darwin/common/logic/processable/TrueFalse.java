package darwin.common.logic.processable;

import darwin.population.Node;
import darwin.problemObject.Processable;

public class TrueFalse extends Processable<Boolean>{

	private boolean value;
	
	public TrueFalse(boolean value) {
		this.value = value;
	}
	
	@Override
	public Boolean getValue(Object parameters, Node[] childNodes) {
		return value;
	}
	
	@Override
	public Processable<Boolean> clone() {
		return new TrueFalse(value);
	}
	
	@Override
	protected boolean equalsValue(Processable<?> other) {
		TrueFalse con = (TrueFalse) other;
		return con.getValue() == value;		
	}

	public boolean getValue() {
		return value;
	}
	
	public String toString() {
		return "Constant(" + value + ")";
	}
}
