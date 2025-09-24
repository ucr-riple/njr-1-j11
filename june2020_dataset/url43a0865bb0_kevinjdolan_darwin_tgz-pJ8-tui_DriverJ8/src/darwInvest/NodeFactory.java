package darwInvest;

import java.util.ArrayList;
import java.util.List;
import darwin.common.logic.nodeType.*;
import darwin.common.math.nodeType.*;
import darwin.problemObject.NodeType;
import darwInvest.nodeType.*;

public class NodeFactory extends darwin.problemObject.DefaultNodeFactory {

	private ArrayList<NodeType> available;
	private Class<?>[] returnTypes;
	
	public NodeFactory() {
		available = new ArrayList<NodeType>();
		
		available.add(new Add());
		available.add(new And());
		available.add(new ConstantMedium());
		available.add(new ConstantNarrow());
		available.add(new ConstantWide());
		available.add(new Cosine());
		available.add(new Divide());
		available.add(new EqualTo());
		available.add(new NewsScore());
		available.add(new GetPrice());
		available.add(new GreaterThan());
		available.add(new GreaterThanOrEqualTo());
		available.add(new IfElseBoolean());
		available.add(new IfElseDouble());
		available.add(new LessThan());
		available.add(new LessThanOrEqualTo());
		available.add(new Log());
		available.add(new Multiply());
		available.add(new Or());
		available.add(new Sine());
		available.add(new Subtract());
		available.add(new Tangent());
		available.add(new TrueFalse());
		
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
