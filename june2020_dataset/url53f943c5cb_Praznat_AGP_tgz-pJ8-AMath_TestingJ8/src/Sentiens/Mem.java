package Sentiens;

import Defs.*;



public class Mem implements Misc {
	public static final Mem[] Defs = MemDefs(); //use instead of AGPmain.TheRealm.getMemDefs...?
	protected P_ prestige;
	protected String name;

	public Mem(String n, P_ p) {
		name = n;
		prestige = p;   //Ideology.P(p);
	}
	public P_ getPrestige() {return prestige;}   //Ideology.unP(prestige);}
	public String getName() {return name;}
	
	public static Mem[] MemDefs() {
		Mem[] Defs = new Mem[M_.values().length];
		Defs[M_.BIDASKSPRD.ordinal()] = new Mem("Bid-Offer Spread",P_.TRADEP);
		Defs[M_.STMOMENTUM.ordinal()] = new Mem("Momentum-following",P_.TRADEP);
		Defs[M_.LTMOMENTUM.ordinal()] = new Mem("Bubble-riding",P_.TRADEP);
		Defs[M_.RISKPREMIUM.ordinal()] = new Mem("Discount Rate",P_.INVESTP);

		Defs[M_.ARM.ordinal()] = new Mem("Armor", P_.ARMORYP);
		Defs[M_.MIS.ordinal()] = new Mem("Missile Arms", P_.ARMORYP);
		Defs[M_.PRC.ordinal()] = new Mem("Piercing Arms", P_.ARMORYP);
		Defs[M_.CAV.ordinal()] = new Mem("Cavalry", P_.ARMORYP);

		Defs[M_.PYRAMIDALITY.ordinal()] = new Mem("Pyramidality",P_.SANCP);
		Defs[M_.LEADERSHIP.ordinal()] = new Mem("Leadership",P_.SANCP);
		Defs[M_.MERITOCRACITY.ordinal()] = new Mem("Meritocracy",P_.SANCP);
		Defs[M_.INDIVIDUALITY.ordinal()] = new Mem("Individuality",P_.SANCP);
		Defs[M_.EMPIRICISM.ordinal()] = new Mem("Empiricism",P_.SANCP);
		Defs[M_.JOVIALITY.ordinal()] = new Mem("Joyousness",P_.SANCP);
		Defs[M_.ROMANTICNESS.ordinal()] = new Mem("Romanticness",P_.SANCP);
		Defs[M_.PROFANITY.ordinal()] = new Mem("Profanity",P_.SANCP);
		Defs[M_.RESPENV.ordinal()] = new Mem("Love of Nature",P_.SANCP);
		Defs[M_.BLOODLUST.ordinal()] = new Mem("Bloodlust",P_.SANCP);
		Defs[M_.MADNESS.ordinal()] = new Mem("Madness",P_.SANCP);
		Defs[M_.HUMILITY.ordinal()] = new Mem("Humility",P_.SANCP);
		Defs[M_.PATIENCE.ordinal()] = new Mem("Patience",P_.SANCP);
		Defs[M_.STRICTNESS.ordinal()] = new Mem("Dogma",P_.SANCP);
		Defs[M_.SUPERST.ordinal()] = new Mem("Superstition",P_.SANCP);
		Defs[M_.CONFIDENCE.ordinal()] = new Mem("Optimism",P_.SANCP);
		Defs[M_.MIERTE.ordinal()] = new Mem("Fear of Death",P_.SANCP);
		Defs[M_.PROMISCUITY.ordinal()] = new Mem("Promiscuity",P_.SANCP);
		Defs[M_.PARANOIA.ordinal()] = new Mem("Paranoia",P_.SANCP);
		

		return Defs;
	}
	
}
