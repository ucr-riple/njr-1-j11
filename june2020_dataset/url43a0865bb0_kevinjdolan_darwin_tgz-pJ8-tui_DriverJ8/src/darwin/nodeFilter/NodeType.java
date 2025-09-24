package darwin.nodeFilter;


/**
 * Accept all nodes that match the given node type
 * @author Kevin Dolan
 */
public class NodeType extends Filter {

	private NodeType nodeType;
	
	public NodeType(NodeType nodeType) {
		this.nodeType = nodeType;
	}
	
	@Override
	public boolean accept(Filterable node) {
		return nodeType.equals(node.getNodeType());
	}
}
