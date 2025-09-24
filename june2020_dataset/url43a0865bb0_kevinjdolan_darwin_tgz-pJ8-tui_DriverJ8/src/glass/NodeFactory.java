package glass;

import glass.nodeType.*;
import java.util.ArrayList;
import java.util.List;
import darwin.problemObject.NodeType;
import darwin.common.Unit;
import darwin.common.math.nodeType.*;

public class NodeFactory extends darwin.problemObject.DefaultNodeFactory {

	private ArrayList<NodeType> available;
	private Class<?>[] returnTypes;
	
	public NodeFactory() {
		available = new ArrayList<NodeType>();
		
		available.add(new ConstantProportion());
		available.add(new Color());
		available.add(new Point());
		available.add(new DrawCircle());
		available.add(new DrawTriangle());
		available.add(new Terminal());
		available.add(new Split());
		
		returnTypes = new Class<?>[] {Unit.class};
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