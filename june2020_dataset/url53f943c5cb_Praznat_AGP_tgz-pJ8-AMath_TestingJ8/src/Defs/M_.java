package Defs;


public enum M_ {
	NULL,
	
	PRAYSTYLE,

	BIDASKSPRD,  //also how far away to initially price when haggling
	STMOMENTUM, // 0-7 reversion 8-15 momentum (also how quickly to converge when haggling)
	LTMOMENTUM, // 0-7 reversion 8-15 momentum
	RISKPREMIUM,
	INVORTRD,
	TECHNICAL,
	FLOW,

	CONST1,
	CONST2,
	CONST3,
	CONST4,
	CONST5,
	CONST6,

	ARM,
	MIS,
	PRC,
	CAV,
	PYRAMIDALITY,
	LEADERSHIP,
	MERITOCRACITY,



	INDIVIDUALITY,
	EMPIRICISM,
	JOVIALITY,
	/** smaller number means shorter temper */
	TEMPER,   //used to decide when to explode in amygdala
	ROMANTICNESS,
	PROFANITY,
	RESPENV,
	BLOODLUST,
	WANDERLUST,
	MADNESS, //innovation, creativity, randomness in behavior
	HUMILITY,
	GREED,
	VANITY,
	EXTROVERSION,
	PATIENCE,
	PARANOIA,
	STRICTNESS,
	DOGMA,
	SUPERST, // add/subtract this amount to secular prestige amt if ELE sanc prest >/< EU's
	CONFIDENCE,
	MIERTE,
	NOTHING_IMPORTANT,
	OCD, //neatness, like short vs long hair...
	PROMISCUITY;
	
	//All the above
	public static int length() {return values().length;}
}
