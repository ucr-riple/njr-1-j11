package org.dclayer.net.component;


import org.dclayer.exception.net.buf.BufException;
import org.dclayer.exception.net.parse.NumberOutOfRangeException;
import org.dclayer.exception.net.parse.ParseException;
import org.dclayer.net.PacketComponent;
import org.dclayer.net.buf.ByteBuf;
import org.dclayer.net.componentinterface.NumComponentI;

/**
 * a flexible number, or, in other words, a data structure representing an integer,
 * self-describing its length
 */
public class FlexNum extends PacketComponent implements NumComponentI {
	
	/**
	 * the integer this {@link FlexNum} represents
	 */
	private long num;
	/**
	 * the amount of bytes this representation requires
	 */
	private int length = 1; // length is one byte if num is 0, which is the case initially
	
	/**
	 * the minimum value for the integer this {@link FlexNum} represents
	 */
	private long minValue;
	/**
	 * the maximum value for the integer this {@link FlexNum} represents
	 */
	private long maxValue;
	/**
	 * true if this {@link FlexNum} used the minValue and maxValue values
	 */
	private boolean hasMinMax = false;
	
	/**
	 * creates a new, empty {@link FlexNum}
	 */
	public FlexNum() {
		this(0);
	}
	
	/**
	 * creates a new {@link FlexNum} representing the given integer
	 * @param num the integer to represent
	 */
	public FlexNum(long num) {
		setNum(num);
	}
	
	/**
	 * creates a new, emtpy {@link FlexNum}, using the specified minValue and maxValue values
	 * @param minValue the minimum value for the integer this {@link FlexNum} represents
	 * @param maxValue the maximum value for the integer this {@link FlexNum} represents
	 */
	public FlexNum(long minValue, long maxValue) {
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.hasMinMax = true;
	}

	@Override
	public void read(ByteBuf byteBuf) throws ParseException, BufException {
		long num = byteBuf.readFlexNum();
		if(hasMinMax && (num < minValue || num > maxValue)) {
			throw new NumberOutOfRangeException(num, minValue, maxValue);
		}
		setNum(num);
	}

	@Override
	public void write(ByteBuf byteBuf) throws BufException {
		byteBuf.writeFlexNum(num);
	}
	
	/**
	 * returns the integer this {@link FlexNum} represents
	 * @return the integer this {@link FlexNum} represents
	 */
	@Override
	public long getNum() {
		return num;
	}
	
	/**
	 * sets the integer this {@link FlexNum} represents. does not do
	 * anything if represented integer already equals given value.
	 * @param num the integer to represent
	 */
	@Override
	public void setNum(long num) {
		if(this.num == num) return;
		this.num = num;
		length = ByteBuf.getFlexNumLength(this.num);
	}

	@Override
	public int length() {
		return length;
	}

	@Override
	public String toString() {
		return String.format("FlexNum(num=%d, length=%d)", num, length);
	}

	@Override
	public PacketComponent[] getChildren() {
		return null;
	}
}