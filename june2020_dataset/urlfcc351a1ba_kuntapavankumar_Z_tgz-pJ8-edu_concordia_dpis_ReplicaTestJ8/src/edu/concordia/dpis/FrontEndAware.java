package edu.concordia.dpis;

import edu.concordia.dpis.commons.Address;

// classes which want to know about the front end info must implement this interface
public interface FrontEndAware {

	void setFrontEndAddress(Address address);
}
