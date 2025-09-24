package ori.mas.influences;

import ori.mas.core.Actor;
import ori.mas.core.Body;

import ori.ogapi.util.OperatorPlus;
import java.util.Comparator;

public class ChangeBodyPropertyInfluence extends PhysicalInfluence {

	public ChangeBodyPropertyInfluence(String property, Object value) {
		super();
		_property = property;
		_value = value;
	}

	public ChangeBodyPropertyInfluence(Actor source, String property, Object value) {
		super(source);
		_property = property;
		_value = value;
	}

	public ChangeBodyPropertyInfluence(Actor source, Body target, String property, Object value) {
		super(source,target);
		_property = property;
		_value = value;
	}

	public ChangeBodyPropertyInfluence(Actor source,
	                                   Body target,
		                               String property,
		                               Object value,
		                               OperatorPlus op) {
		super(source,target);
		_property = property;
		_value = value;
		_operator = op;
	}

	public ChangeBodyPropertyInfluence(Actor source, 
	                                   Body target, 
		                               String property,
		                               Object value, 
		                               Comparator cmp) {
		super(source,target);
		_property = property;
		_value = value;
		_comparator = cmp;
	}

	public ChangeBodyPropertyInfluence(Actor source, 
	                                   Body target, 
	                                   String property, 
	                                   Object value, 
	                                   OperatorPlus op, 
	                                   Comparator cmp) {
		super(source,target);
		_property = property;
		_value = value;
		_operator = op;
		_comparator = cmp;
	}

	public String property() {
		return _property;
	}

	public Object value() {
		return _value;
	}

	public void setValue(Object v) {
		_value = v;
	}

	public OperatorPlus operator() {
		return _operator;
	}

	public Comparator comparator() {
		return _comparator;
	}

	private String _property;
	private Object _value;
	private OperatorPlus _operator = null;
	private Comparator _comparator = null;

};

