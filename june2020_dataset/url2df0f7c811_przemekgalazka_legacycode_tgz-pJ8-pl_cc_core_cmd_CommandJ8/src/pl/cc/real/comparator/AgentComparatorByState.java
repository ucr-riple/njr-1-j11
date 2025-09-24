package pl.cc.real.comparator;

import java.util.Comparator;

import org.apache.log4j.Logger;

import pl.cc.real.RealAgent;


/**
 * Komparator dla obiektďż˝w RealAgent
 * Porďż˝wnujemy po: 	- nie zapauzowany/zapauzowany
 * 
 * @since Nov 10, 2008
 */
public class AgentComparatorByState implements Comparator<RealAgent> {
	static final Logger log = Logger.getLogger(AgentComparatorByName.class);

	public int compare(RealAgent o1, RealAgent o2) {
		RealAgent a1 = (RealAgent) o1;
		RealAgent a2 = (RealAgent) o2;

		if (a1.isPaused()==a2.isPaused()){
			return 0;
		} else if (a1.isPaused()){
			return 1;
		} else {
			return -1;
		}
	}

}