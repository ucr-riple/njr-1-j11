package darwin.nodeFilter;

public class HeightRange extends Filter {

	private int min, max;
	private Class<?> returnType;
	
	public HeightRange(Class<?> returnType, int min, int max) {
		this.min = min;
		this.max = max;
		this.returnType = returnType;
	}
	
	@Override
	public boolean accept(Filterable node) {
		return returnType.isAssignableFrom(node.getReturnType()) 
			&& node.getHeight(true) >= min 
			&& node.getHeight(true) <= max;
	}

}
