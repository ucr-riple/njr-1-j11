package darwin.common.math.nodeType;

public class ConstantNarrow extends Constant {

	@Override
	public double getMax() {
		return 1;
	}

	@Override
	public double getMin() {
		return -1;
	}

}
