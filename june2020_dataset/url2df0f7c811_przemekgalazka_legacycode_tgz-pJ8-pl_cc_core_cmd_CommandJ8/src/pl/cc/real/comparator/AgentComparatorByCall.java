package pl.cc.real.comparator;

import java.util.Comparator;

import org.apache.log4j.Logger;

import pl.cc.real.RealAgent;


/**
 * Komparator dla obiektów RealAgent
 * Porównujemy po: agent posiada połączenie telefoniczne (onAgentConnect) 
 *
 * @since Nov 16, 2008
  */
public class AgentComparatorByCall implements Comparator<RealAgent> {
	static final Logger log = Logger.getLogger(AgentComparatorByCall.class);

	public int compare(RealAgent o1, RealAgent o2) {
		RealAgent a1 = (RealAgent) o1;
		RealAgent a2 = (RealAgent) o2;

		if (
				(a1.getConnectedCall()==null) && (a2.getConnectedCall()==null) ||
				(a1.getConnectedCall()!=null) && (a2.getConnectedCall()!=null)
		)
		{
			return 0;
		} else if (a1.getConnectedCall()==null){
			return 1;
		} else {
			return -1;
		}
	}
		


}