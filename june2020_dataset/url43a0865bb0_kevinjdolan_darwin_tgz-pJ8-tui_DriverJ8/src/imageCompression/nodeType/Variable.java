package imageCompression.nodeType;

import darwin.problemObject.NodeType;
import darwin.problemObject.Processable;

public class Variable extends NodeType {

	@Override
	public Class<?>[] getChildTypes() {
		return new Class<?>[] {};
	}

	@Override
	public Processable<?> getProcessable() {
		int value;
		if(Math.random() < 0.5)
			value = imageCompression.processable.Variable.X;
		else
			value = imageCompression.processable.Variable.Y;
		
		return new imageCompression.processable.Variable(value);
	}

	@Override
	public Class<?> getReturnType() {
		return Double.class;
	}

}
