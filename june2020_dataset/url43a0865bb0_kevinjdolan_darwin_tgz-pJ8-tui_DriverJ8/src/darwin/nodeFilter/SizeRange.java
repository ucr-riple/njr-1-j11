package darwin.nodeFilter;

public class SizeRange extends Filter {

	private int min, max;
	private Class<?> returnType;
	
	public SizeRange(Class<?> returnType, int min, int max) {
		this.min = min;
		this.max = max;
		this.returnType = returnType;
	}
	
	@Override
	public boolean accept(Filterable node) {
		return returnType.isAssignableFrom(node.getReturnType()) 
			&& node.getSize(true) >= min 
			&& node.getSize(true) <= max;
	}

}
