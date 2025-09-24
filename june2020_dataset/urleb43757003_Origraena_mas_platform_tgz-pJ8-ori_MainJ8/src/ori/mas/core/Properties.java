package ori.mas.core;

// property name => typed
// 		<type>:<name>[-min|-max]
public class Properties {

	public static final String SUFFIX_MIN = "-min";
	public static final String SUFFIX_MAX = "-max";

	/** Used for agent conceptor recognition... */
	// String
	public static final String SOUL       = "soul";

	// int
	public static final String HEALTH     = "health";
	public static final String HEALTH_MIN = HEALTH+SUFFIX_MIN;
	public static final String HEALTH_MAX = HEALTH+SUFFIX_MAX;
	// int
	public static final String FEED       = "feed";
	public static final String FEED_MIN   = FEED+SUFFIX_MIN;
	public static final String FEED_MAX   = FEED+SUFFIX_MAX;

	public static final String FEED_TICK  = "feed-tick"; // -1

	// Body
	public static final String TARGET     = "target";


	// Convenient static methods
	public static final Body getTarget(Body b) {
		return (Body)(b.get(TARGET));
	}
	public static final int getHealth(Body b) {
		try { return ((Integer)(b.get(HEALTH))).intValue(); }
		catch (Exception e) { return 0; }
	}
	public static final int getHealthMax(Body b) {
		try { return ((Integer)(b.get(HEALTH_MAX))).intValue(); }
		catch (Exception e) { return 0; }
	}
	public static final int getFeed(Body b) {
		try { return ((Integer)(b.get(FEED))).intValue(); }
		catch (Exception e) { return 0; }
	}
	public static final int getFeedMax(Body b) {
		try { return ((Integer)(b.get(FEED_MAX))).intValue(); }
		catch (Exception e) { return 0; }
	}
	public static final float healthRatio(Body b) {
		try {
		float healthMax = (float)(((Integer)(b.get(HEALTH_MAX))).intValue());
		float health = (float)(((Integer)(b.get(HEALTH))).intValue());
		return health / healthMax;
		}
		catch (Exception e) { return 0.f; }
	}
	public static final float feedRatio(Body b) {
		try {
		float feedMax = (float)(((Integer)(b.get(FEED_MAX))).intValue());
		float feed = (float)(((Integer)(b.get(FEED))).intValue());
		return feed / feedMax;
		}
		catch (Exception e) { return 0.f; }
	}

	private Properties() { }

};

