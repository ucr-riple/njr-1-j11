package pl.cc.real.comparator;

import java.util.ArrayList;
import java.util.Comparator;

import pl.cc.real.RealAgent;

/**
 * Uniwersalny komparator do agentďż˝w - umoďż˝liwia koďż˝ystanie z innych komparatorďż˝w
 */
public class AgentComparator implements Comparator<RealAgent> {
	public static  final AgentComparator CONNECTED_STATE_NAME; // odebrana rozmowa | paused/unpaused | nazwa
	public static  final AgentComparator NAME; //  nazwa
	public static  final AgentComparator ID; //  id
	
	private ArrayList<Comparator<RealAgent>> comparatorList = new ArrayList<Comparator<RealAgent>>();
	
	public void addComparator(Comparator<RealAgent> comparator){
		comparatorList.add(comparator);
	}
	
	static {
		ID = new AgentComparator();
		ID.addComparator(new AgentComparatorById());
		
		CONNECTED_STATE_NAME = new AgentComparator();
		CONNECTED_STATE_NAME.addComparator(new AgentComparatorByState());
		CONNECTED_STATE_NAME.addComparator(new AgentComparatorByCall());
		CONNECTED_STATE_NAME.addComparator(new AgentComparatorByName());
		
		NAME = new AgentComparator();
		NAME.addComparator(new AgentComparatorByName());
	}
	
	public int compare(RealAgent o1, RealAgent o2) {
		for (Comparator<RealAgent> c :  comparatorList){
			int cmp = c.compare(o1, o2);
			if (cmp!=0) return cmp;
		}
		return 0;
	}

}
