package darwin.nodeFilter;

public class DepthRange extends Filter {

	private int min, max;
	private Class<?> returnType;
	
	public DepthRange(Class<?> returnType, int min, int max) {
		this.min = min;
		this.max = max;
		this.returnType = returnType;
	}
	
	@Override
	public boolean accept(Filterable node) {
		return returnType.isAssignableFrom(node.getReturnType()) 
			&& node.getDepth(true) >= min 
			&& node.getDepth(true) <= max;
	}

}
