package darwin.nodeFilter;


/**
 * Accept all nodes that match the given return type
 * @author Kevin Dolan
 */
public class ReturnType extends Filter {

	private Class<?> returnType;
	
	public ReturnType(Class<?> returnType) {
		this.returnType = returnType;
	}
	
	@Override
	public boolean accept(Filterable node) {
		return returnType.isAssignableFrom(node.getReturnType());
	}
}
