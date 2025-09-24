package main.interfaces;

import java.util.Date;

import main.implementations.order.Order;

/**
 * Builder interface for orders
 */
public interface OrderBuilder {
	
	/**
	 * Default values:
	 */
	public static final String DEFAULT_INSTRUMENT = "BHP";
	public static final String DEFAULT_DATE = "20130205";
	public static final String DEFAULT_TIME = "00:00";
	public static final String DEFAULT_RECORD_TYPE = "ENTER";
	public static final double DEFAULT_PRICE = 0;
	public static final int DEFAULT_VOLUME = 3000;
	public static final String DEFAULT_UNDISCLOSED_VOLUME = "0";
	public static final double DEFAULT_VALUE = 113250;
	public static final String DEFAULT_QUALIFIERS = "";
	public static final String DEFAULT_TRANSACTIONID = "";
	public static final String DEFAULT_BIDID = "";
	public static final String DEFAULT_ASKID = "";
	public static final String DEFAULT_BID_OR_ASK = "B";
	public static final Date DEFAULT_ENTRY_TIME = null;
	public static final String DEFAULT_OLD_PRICE = "0";
	public static final String DEFAULT_OLD_VOLUME = "0";
	public static final String DEFAULT_BUYER_BROKER_ID = "266";
	public static final String DEFAULT_SELLER_BROKER_ID = "";
	
	/**
	 * Receives all the required identifiers for a order to be created.
	 * 
	 * @return - the created order with the appropriate type.
	 */
	public Order build();
	
}
