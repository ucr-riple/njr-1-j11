package mp1401.examples.misterx.model.gameitems.impl;

import java.awt.Point;

public class UnknownCityImpl extends CityImpl {
	
	private static final long serialVersionUID = 4979230641782144846L;
	
	private static final String UNKNOWN = "<Unknown City>";

	public UnknownCityImpl() {
		super(UNKNOWN, new Point());
	}

}
