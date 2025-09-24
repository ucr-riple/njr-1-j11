package ori.mas.influences;

import ori.mas.core.Actor;
import ori.mas.core.Body;

import ori.ogapi.util.OperatorPlus;
import java.util.Comparator;

public class ChangeBodyIntegerPropertyInfluence extends ChangeBodyPropertyInfluence {

	public ChangeBodyIntegerPropertyInfluence(Actor source, Body target, String property, Integer value) {
		super(source,target,property,value,OperatorPlus.INTEGER,INTEGER_COMPARATOR);
	}

	public static final Comparator<Integer> INTEGER_COMPARATOR = 
		new Comparator<Integer>() {
			@Override
			public int compare(Integer i1, Integer i2) {
				return (i1.intValue() - i2.intValue());
			}
		};


};

