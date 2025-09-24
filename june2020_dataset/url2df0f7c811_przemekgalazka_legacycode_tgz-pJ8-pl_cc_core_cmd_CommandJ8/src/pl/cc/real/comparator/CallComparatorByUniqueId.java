package pl.cc.real.comparator;

import java.util.Comparator;

import org.apache.log4j.Logger;

import pl.cc.real.RealCall;


public class CallComparatorByUniqueId implements Comparator<RealCall> {
	static final Logger log = Logger.getLogger(CallComparatorByUniqueId.class);

	public int compare(RealCall o1, RealCall o2) {
		RealCall a1 = (RealCall) o1;
		RealCall a2 = (RealCall) o2;
		// log.debug(a1.getName().toLowerCase()+" | "+
		// a2.getName().toLowerCase()+ " | "+
		// a1.getName().toLowerCase().compareTo(a2.getName().toLowerCase()));
		//return a1.getName().toLowerCase().compareTo(a2.getName().toLowerCase());
		return a1.getUniqueID().toLowerCase().compareTo(a2.getUniqueID().toLowerCase());
	}

}
