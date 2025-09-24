package Defs;

import java.util.*;

import Ideology.*;
import Questing.Knowledge.KnowledgeType;

public enum SK_ implements KnowledgeType {
	WEALTHYSHIRES,
	POPULOUSSHIRES,
	GRANDIOSESHIRES,
	SEXYSHIRES,
	THREATENINGSHIRES,
	LEARNEDSHIRES,
	SKILLYSHIRES,
	HOLYSHIRES,
	RIGHTEOUSSHIRES,
	FRIENDLYSHIRES, // allegiance: shires with our orderfolk in them
	FAMOUSSHIRES; //legacy
	

	public static Map<Value, SK_> valToSK = new HashMap<Value, SK_>();
	public static Map<SK_, Value> skToVal = new HashMap<SK_, Value>();
	static {
		mapAdd(Values.MIGHT, SK_.THREATENINGSHIRES);
		mapAdd(Values.WEALTH, SK_.WEALTHYSHIRES);
		mapAdd(Values.INFLUENCE, SK_.POPULOUSSHIRES);
		mapAdd(Values.KNOWLEDGE, SK_.LEARNEDSHIRES);
		mapAdd(Values.EXPERTISE, SK_.LEARNEDSHIRES);
		mapAdd(Values.SPIRITUALITY, SK_.HOLYSHIRES);
		mapAdd(Values.ALLEGIANCE, SK_.FRIENDLYSHIRES);
		mapAdd(Values.LEGACY, SK_.FAMOUSSHIRES);
		mapAdd(Values.RIGHTEOUSNESS, SK_.RIGHTEOUSSHIRES);
		mapAdd(Values.BEAUTY, SK_.GRANDIOSESHIRES);
		mapAdd(Values.COPULATION, SK_.SEXYSHIRES);
		mapAdd(Values.HARMONY, SK_.THREATENINGSHIRES);
	}
	
	private static void mapAdd(Value v, SK_ sk) {
		valToSK.put(v, sk);
		skToVal.put(sk, v);
	}

}
