package pl.cc.real.comparator;

import java.util.Comparator;

import org.apache.log4j.Logger;

import pl.cc.real.RealAgent;

/**
 * Porównujemy agentów po ID
 *
 * @since 2009-07-11
 */
public class AgentComparatorById implements Comparator<RealAgent> {
	static final Logger log = Logger.getLogger(AgentComparatorById.class);

	public int compare(RealAgent o1, RealAgent o2) {
		RealAgent a1 = (RealAgent) o1;
		RealAgent a2 = (RealAgent) o2;
		// log.debug(a1.getName().toLowerCase()+" | "+
		// a2.getName().toLowerCase()+ " | "+
		// a1.getName().toLowerCase().compareTo(a2.getName().toLowerCase()));
		return a1.getId().compareTo(a2.getId());
	}

}
