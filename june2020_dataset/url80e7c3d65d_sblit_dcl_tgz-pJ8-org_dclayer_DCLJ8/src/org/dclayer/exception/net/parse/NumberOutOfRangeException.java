package org.dclayer.exception.net.parse;

public class NumberOutOfRangeException extends ParseException {

	public NumberOutOfRangeException(long number, long minNumber, long maxNumber) {
		super(String.format("number %d is out of valid range (min %d, max %d)", number, minNumber, maxNumber));
	}
	
}
