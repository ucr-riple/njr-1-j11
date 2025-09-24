package pl.cc.real.comparator;

import java.util.Comparator;

import org.apache.log4j.Logger;

import pl.cc.real.RealAgent;


public class AgentComparatorByName implements Comparator<RealAgent> {
	static final Logger log = Logger.getLogger(AgentComparatorByName.class);

	public int compare(RealAgent o1, RealAgent o2) {
		RealAgent a1 = (RealAgent) o1;
		RealAgent a2 = (RealAgent) o2;
		// log.debug(a1.getName().toLowerCase()+" | "+
		// a2.getName().toLowerCase()+ " | "+
		// a1.getName().toLowerCase().compareTo(a2.getName().toLowerCase()));
		return a1.getName().toLowerCase().compareTo(a2.getName().toLowerCase());
	}

}
