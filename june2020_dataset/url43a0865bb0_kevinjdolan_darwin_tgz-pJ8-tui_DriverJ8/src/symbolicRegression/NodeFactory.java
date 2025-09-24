package symbolicRegression;

import java.util.ArrayList;
import java.util.List;
import darwin.problemObject.NodeType;
import symbolicRegression.nodeType.*;
import darwin.common.math.nodeType.*;

public class NodeFactory extends darwin.problemObject.DefaultNodeFactory {

	private ArrayList<NodeType> available;
	private Class<?>[] returnTypes;
	
	public NodeFactory() {
		available = new ArrayList<NodeType>();
		
		available.add(new Add());
		available.add(new ConstantMedium());
		available.add(new ConstantNarrow());
		available.add(new ConstantWide());
		available.add(new Divide());
		available.add(new Multiply());
		available.add(new Subtract());
		available.add(new Variable());
		
		returnTypes = new Class<?>[] {Double.class};
	}
	
	@Override
	public List<NodeType> getAvailableNodes() {
		return available;
	}

	@Override
	public Class<?>[] getReturnTypes() {
		return returnTypes;
	}

}
