package darwin.nodeFilter;

/**
 * Accept all nodes that match the given signature
 * @author Kevin Dolan
 */
public class Signature extends Filter {

	private Class<?> returnType;
	private Class<?>[] childTypes;
	
	public Signature(Class<?> returnType, Class<?>[] childTypes) {
		this.returnType = returnType;
		this.childTypes = childTypes;
	}
	
	@Override
	public boolean accept(Filterable node) {
		boolean good = returnType.isAssignableFrom(node.getReturnType());
		good = good && childTypes.length == node.getArity();
		for(int i = 0; good && i < childTypes.length; i++)
			good = good && node.getChildType(i).isAssignableFrom(childTypes[i]);
		return good;
	}
}
